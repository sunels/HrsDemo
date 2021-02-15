package com.hrs.service;

import com.hrs.jpa.RoomEntity;
import com.hrs.jpa.repository.RoomRepository;
import com.hrs.model.RoomModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Slf4j
public class RoomService {
    private final RoomRepository roomRepository;
    private final EntityModelConvertor convertor;

    public RoomService(RoomRepository roomRepository, EntityModelConvertor convertor) {
        this.roomRepository = roomRepository;
        this.convertor = convertor;
    }

    @Transactional
    public RoomModel createNew(RoomModel roomModel) {
        RoomEntity entity = convertor.convertRoomModel2Entity(roomModel);
        RoomEntity savedEntity = roomRepository.save(entity);
        log.debug("Saved new room: {}", savedEntity);
        return convertor.convertRoomEntity2Model(savedEntity);
    }

    @Transactional
    public RoomModel findByDoorNumber(Integer doorNumber) {
        return roomRepository.findByDoorNumber(doorNumber)
                .map(e-> convertor.convertRoomEntity2Model(e))
                .orElseThrow(() -> new EntityNotFoundException("No room by door number" + doorNumber));
    }


}
