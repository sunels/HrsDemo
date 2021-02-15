package com.hrs.service;

import com.hrs.jpa.GuestEntity;
import com.hrs.jpa.ParcelEntity;
import com.hrs.jpa.ReservationEntity;
import com.hrs.jpa.RoomEntity;
import com.hrs.jpa.repository.GuestRepository;
import com.hrs.jpa.repository.ParcelRepository;
import com.hrs.jpa.repository.ReservationRepository;
import com.hrs.jpa.repository.RoomRepository;
import com.hrs.model.GuestModel;
import com.hrs.model.ParcelModel;
import com.hrs.model.ReservationModel;
import com.hrs.model.RoomModel;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.stream.Collectors;
/*
* Utility class for converting dto's to rest models
*/
@Component
public class EntityModelConvertor {
    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final RoomRepository roomRepository;

    private final ParcelRepository parcelRepository;

    public EntityModelConvertor(ReservationRepository reservationRepository, GuestRepository guestRepository,
                                RoomRepository roomRepository, ParcelRepository parcelRepository) {
        this.reservationRepository = reservationRepository;
        this.guestRepository = guestRepository;
        this.roomRepository = roomRepository;
        this.parcelRepository = parcelRepository;
    }

    public GuestEntity convertGuestModel2Entity(GuestModel guestModel) {
        if(guestModel.getId() == null){
            GuestEntity entity = new GuestEntity();
            entity.setEmail(guestModel.getEmail());
            entity.setFullName(guestModel.getFullName());
            return entity;
        }else{
            return guestRepository.findById(guestModel.getId())
                    .orElseThrow(()-> new EntityNotFoundException("NO guest with id:" + guestModel.getId()));
        }
    }

    public GuestModel convertGuestEntity2Model(GuestEntity savedEntity) {
        GuestModel model = new GuestModel();
        model.setId(savedEntity.getId());
        model.setEmail(savedEntity.getEmail());
        model.setFullName(savedEntity.getFullName());
        if(model.getReservationsIds() == null && savedEntity.getReservations() != null){
            model.setReservationsIds(savedEntity.getReservations().stream().map(e->e.getId())
                    .collect(Collectors.toSet()));
        }
        if(model.getUndeliveredParcelIds() == null && savedEntity.getParcels() != null){
            model.setUndeliveredParcelIds(savedEntity.getParcels().
                    stream().filter(p-> p.getIsDelivered() == Byte.valueOf("0")).
                    map(e-> e.getId())
                    .collect(Collectors.toSet()));
        }
        if(model.getDeliveredParcelIds() == null && savedEntity.getParcels() != null){
            model.setDeliveredParcelIds(savedEntity.getParcels().
                    stream().filter(p-> p.getIsDelivered() == Byte.valueOf("1")).
                    map(e-> e.getId())
                    .collect(Collectors.toSet()));
        }
        return model;
    }

    public ReservationEntity convertReservationModel2Entity(ReservationModel model) {
        if(model.getId() != null){
            return reservationRepository.findByGuestId(model.getId())
                    .orElseThrow(()-> new EntityNotFoundException());
        }else{
            ReservationEntity entity = new ReservationEntity();
            entity.setDateIn(model.getDateIn());
            entity.setDateOut(model.getDateOut());
            GuestModel guestModel = new GuestModel();
            guestModel.setId(model.getGuestId());
            entity.setGuest(convertGuestModel2Entity(guestModel));
            RoomModel roomModel = new RoomModel();
            roomModel.setId(model.getRoomId());
            entity.setRoom(convertRoomModel2Entity (roomModel));
            return entity;
        }
    }

    public ReservationModel convertReservationEntity2Model(ReservationEntity entity) {
        ReservationModel model = new ReservationModel();
        model.setId(entity.getId());
        if (model.getGuestId() == null && entity.getGuest() != null )
            model.setGuestId(entity.getGuest().getId());
        model.setDateIn(entity.getDateIn());
        model.setDateOut(entity.getDateOut());
        if (model.getRoomId() == null && entity.getRoom() != null )
            model.setRoomId(entity.getRoom().getId());
        return model;
    }

    public RoomModel convertRoomEntity2Model(RoomEntity entity) {
        RoomModel model = new RoomModel();
        model.setId(entity.getId());
        model.setDoorNumber(entity.getDoorNumber());
        model.setIsAvailable(entity.getIsAvailable());
        return model;
    }

    public RoomEntity convertRoomModel2Entity(RoomModel roomModel) {
        if (roomModel.getId() != null) {
            return roomRepository.findById(roomModel.getId())
                    .orElseThrow(() -> new EntityNotFoundException("No room with id" + roomModel.getId()));
        } else {
            RoomEntity entity = new RoomEntity();
            entity.setDoorNumber(roomModel.getDoorNumber());
            entity.setIsAvailable(roomModel.getIsAvailable());
            return entity;
        }
    }


    public ParcelEntity convertParcelModel2Entity(ParcelModel parcelModel) {
        if(parcelModel.getId() == null){
            ParcelEntity entity = new ParcelEntity();
            entity.setAcceptDate(Instant.now());
            entity.setDescription(parcelModel.getParcelDescription());
            entity.setIsDelivered(Byte.valueOf("0"));
            entity.setGuest(guestRepository.findById(parcelModel.getGuestId()).orElseThrow());
            return entity;
        }else{
            return parcelRepository.findById(parcelModel.getId())
                    .orElseThrow(()-> new EntityNotFoundException("NO parcel with id:" + parcelModel.getId()));
        }
    }

    public ParcelModel convertParcelEntity2Model(ParcelEntity savedEntity) {
        ParcelModel model = new ParcelModel();
        model.setId(savedEntity.getId());
        model.setIsDelivered(savedEntity.getIsDelivered());
        model.setGuestId(savedEntity.getGuest().getId());
        model.setParcelDescription(savedEntity.getDescription());
        model.setDeliveryDate(savedEntity.getDeliveryDate());
        model.setAcceptDate(savedEntity.getAcceptDate());
        return model;
    }

}
