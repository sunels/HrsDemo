package com.hrs.jpa.repository;

import com.hrs.jpa.GuestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<GuestEntity, Long> {
    Optional<GuestEntity> findByEmail (String email);
}
