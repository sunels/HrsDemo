package com.hrs.service;

import com.hrs.jpa.ParcelEntity;
import com.hrs.jpa.repository.ParcelRepository;
import com.hrs.model.DeliverParcelRequest;
import com.hrs.model.GuestModel;
import com.hrs.model.ParcelModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ParcelService {
    private final ParcelRepository parcelRepository;
    private final EntityModelConvertor convertor;

    public ParcelService(ParcelRepository parcelRepository, EntityModelConvertor convertor) {
        this.parcelRepository = parcelRepository;
        this.convertor = convertor;
    }

    @Transactional
    public ParcelModel createNew(ParcelModel parcelModel) {
        checkParcelRequest(parcelModel);
        parcelModel.setAcceptDate(Instant.now());
        ParcelEntity entity = convertor.convertParcelModel2Entity(parcelModel);
        ParcelEntity save = parcelRepository.save(entity);
        log.debug("saved new parcel: {}", save);
        return convertor.convertParcelEntity2Model(save);
    }

    private void checkParcelRequest(ParcelModel parcelModel) {
        GuestModel guest = new GuestModel();
        guest.setId(parcelModel.getGuestId());
        Boolean result = convertor.convertGuestModel2Entity(guest).getReservations()
                .stream().anyMatch(r -> {
                    //maybe end date is not set ...vip guest or end date is not set somehow..
                    //If so assuming that guest in still hotel
                    boolean c1 = r.getDateIn() != null && r.getDateOut() == null;
                    //ıf we have an end date check enddate is not came yet..
                    boolean c2 = r.getDateOut() != null && Instant.now().isBefore(r.getDateOut());
                    log.debug("Checking reservation {}, results: {}, {}", r, c1, c2);
                    return c1 || c2;
                });
        if (!result) {
            throw new IllegalArgumentException("Guest not in the hotel, rejecting the parcel!");
        }
    }


    @Transactional
    public Set<ParcelModel> findAllByGuestId(Long guestId) {
        return parcelRepository.findAllByGuestId(guestId)
                .stream().map(e -> convertor.convertParcelEntity2Model(e))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Transactional
    public ParcelModel deliverParcel2Guest(DeliverParcelRequest deliverParcelRequest) {
        ParcelEntity entity = parcelRepository.findById(deliverParcelRequest.getId())
                .orElseThrow();
        if(entity.getIsDelivered() == Byte.valueOf("0")){
            entity.setIsDelivered(Byte.valueOf("1"));
            entity.setDeliveryDate(Instant.now());
            ParcelEntity updatedParsel = parcelRepository.save(entity);
            return convertor.convertParcelEntity2Model(updatedParsel);
        }else{
            throw new IllegalArgumentException("Parcel Already Delivered");
        }
    }
}
