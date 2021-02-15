package com.hrs.jpa;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Collection;

@Entity
@Table(name = "guest")
public class GuestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto increment mysql
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName;
    @Basic
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;
    @OneToMany(mappedBy = "guest")
    private Collection<ParcelEntity> parcels;
    @OneToMany(mappedBy = "guest")
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GuestEntity entity = (GuestEntity) o;

        if (id != null ? !id.equals(entity.id) : entity.id != null) return false;
        if (fullName != null ? !fullName.equals(entity.fullName) : entity.fullName != null) return false;
        if (email != null ? !email.equals(entity.email) : entity.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    public Collection<ParcelEntity> getParcels() {
        return parcels;
    }

    public void setParcels(Collection<ParcelEntity> parcels) {
        this.parcels = parcels;
    }

    public Collection<ReservationEntity> getReservations() {
        return reservations;
    }

    public void setReservations(Collection<ReservationEntity> reservations) {
        this.reservations = reservations;
    }
}
