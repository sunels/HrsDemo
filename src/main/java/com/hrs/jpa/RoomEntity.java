package com.hrs.jpa;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Collection;

@Entity
@Table(name = "room")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto increment mysql
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "is_available", nullable = false)
    private Byte isAvailable;
    @Basic
    @Column(name = "door_number", unique = true, nullable = false)
    private Integer doorNumber;
    @OneToMany(mappedBy = "room")
    private Collection<ReservationEntity> reservations;

    @CreatedDate
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="UTC")
    private Instant createdAt;

    @LastModifiedDate
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="UTC")
    public Instant lastModifiedDate;

    @org.springframework.data.annotation.Version
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Byte isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Integer getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(Integer doorNumber) {
        this.doorNumber = doorNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomEntity entity = (RoomEntity) o;

        if (id != null ? !id.equals(entity.id) : entity.id != null) return false;
        if (isAvailable != null ? !isAvailable.equals(entity.isAvailable) : entity.isAvailable != null) return false;
        if (doorNumber != null ? !doorNumber.equals(entity.doorNumber) : entity.doorNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (isAvailable != null ? isAvailable.hashCode() : 0);
        result = 31 * result + (doorNumber != null ? doorNumber.hashCode() : 0);
        return result;
    }

    public Collection<ReservationEntity> getReservations() {
        return reservations;
    }

    public void setReservations(Collection<ReservationEntity> reservations) {
        this.reservations = reservations;
    }
}
