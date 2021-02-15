package com.hrs.jpa.repository;

import com.hrs.jpa.ParcelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ParcelRepository extends JpaRepository<ParcelEntity, Long> {
    Set<ParcelEntity> findAllByGuestId (Long guestId);
}
