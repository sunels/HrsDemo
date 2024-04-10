package com.hrs.service;

import com.hrs.jpa.GuestEntity;
import com.hrs.jpa.repository.GuestRepository;
import com.hrs.model.GuestModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Slf4j
public class GuestService {
    GuestRepository guestRepository;
    EntityModelConvertor convertor;

    @Autowired
    public GuestService(GuestRepository guestRepository, EntityModelConvertor convertor) {
        this.guestRepository = guestRepository;
        this.convertor = convertor;
    }

    @Transactional
    public GuestModel createNew(GuestModel guestModel) {
        GuestEntity entity = convertor.convertGuestModel2Entity(guestModel);
        GuestEntity savedEntity = guestRepository.save(entity);
        log.debug("saved new guest: {}", savedEntity);
        return convertor.convertGuestEntity2Model (savedEntity);
    }


    @Transactional
    public GuestModel findByEmail(String email) {
        return guestRepository.findByEmail(email)
                .map(e-> convertor.convertGuestEntity2Model(e))
                .orElseThrow(()-> new EntityNotFoundException ("No guest found by email: " + email));
    }
}
