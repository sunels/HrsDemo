package com.hrs.jpa;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "reservation")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto increment mysql
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "date_in", nullable = false)
    private Instant dateIn;
    @Basic
    @Column(name = "date_out")
    private Instant dateOut;
    @ManyToOne
    @JoinColumn(name = "guestid", referencedColumnName = "id", nullable = false)
    private GuestEntity guest;
    @ManyToOne
    @JoinColumn(name = "roomid", referencedColumnName = "id")
    private RoomEntity room;

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

    public Instant getDateIn() {
        return dateIn;
    }

    public void setDateIn(Instant dateIn) {
        this.dateIn = dateIn;
    }

    public Instant getDateOut() {
        return dateOut;
    }

    public void setDateOut(Instant dateOut) {
        this.dateOut = dateOut;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReservationEntity entity = (ReservationEntity) o;

        if (id != null ? !id.equals(entity.id) : entity.id != null) return false;
        if (dateIn != null ? !dateIn.equals(entity.dateIn) : entity.dateIn != null) return false;
        if (dateOut != null ? !dateOut.equals(entity.dateOut) : entity.dateOut != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dateIn != null ? dateIn.hashCode() : 0);
        result = 31 * result + (dateOut != null ? dateOut.hashCode() : 0);
        return result;
    }

    public GuestEntity getGuest() {
        return guest;
    }

    public void setGuest(GuestEntity guest) {
        this.guest = guest;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }
}
