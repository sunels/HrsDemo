package com.hrs.service;

import com.hrs.jpa.ReservationEntity;
import com.hrs.jpa.RoomEntity;
import com.hrs.jpa.repository.ReservationRepository;
import com.hrs.model.ReservationEndRequest;
import com.hrs.model.ReservationModel;
import com.hrs.model.RoomModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;

@Service
@Slf4j
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final EntityModelConvertor convertor;

    public ReservationService(ReservationRepository reservationRepository, EntityModelConvertor convertor) {
        this.reservationRepository = reservationRepository;
        this.convertor = convertor;
    }

    @Transactional
    public ReservationModel createNew(ReservationModel reservationModel) {
        checkReservationRequestForCreate(reservationModel);
        reservationModel.setDateIn(Instant.now());
        ReservationEntity entity = convertor.convertReservationModel2Entity(reservationModel);
        entity.getRoom().setIsAvailable(Byte.valueOf("1"));
        ReservationEntity save = reservationRepository.save(entity);
        log.debug("Saved new reservation: {}", save);
        return convertor.convertReservationEntity2Model(save);
    }

    private void checkReservationRequestForCreate(ReservationModel reservationModel) {
        // There will be some other validations regarding the provided entity ids.
        // Room exists ? Guest exists ?  Guest already has a reservations etc..
        // Validations can be done in the related model class or in some other Spring Component also
        // For simplicity let it stay here for now..
        RoomModel room = new RoomModel();
        room.setId(reservationModel.getRoomId());
        if (convertor.convertRoomModel2Entity(room).getIsAvailable() == Byte.valueOf("1")) {
            throw new IllegalArgumentException("Room with id: {" + reservationModel.getRoomId() + "} is not available!");
        }
    }


    @Transactional
    public ReservationModel endReservation(ReservationEndRequest reservationEndRequest) {
        ReservationEntity reservationEntity = reservationRepository.findById(reservationEndRequest.getId())
                .orElseThrow();
        RoomEntity room = reservationEntity.getRoom();
        room.setIsAvailable(Byte.valueOf("0"));
        reservationEntity.setDateOut(Instant.now());
        ReservationEntity save = reservationRepository.save(reservationEntity);
        return convertor.convertReservationEntity2Model(save);
    }


}
