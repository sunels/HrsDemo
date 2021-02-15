package com.hrs.jpa;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "parcel")
public class ParcelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto increment mysql
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "description", nullable = false, length = 1024)
    private String description;

    @Basic
    @Column(name = "accept_date", nullable = false)
    private Instant acceptDate;
    @Basic
    @Column(name = "delivery_date", nullable = true)
    private Instant deliveryDate;
    @ManyToOne
    @JoinColumn(name = "guestid", referencedColumnName = "id", nullable = false)
    private GuestEntity guest;

    @Basic
    @Column(name = "is_delivered", nullable = false)
    private Byte isDelivered;

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

    public Instant getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Instant acceptDate) {
        this.acceptDate = acceptDate;
    }

    public Instant getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Instant deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParcelEntity that = (ParcelEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (acceptDate != null ? !acceptDate.equals(that.acceptDate) : that.acceptDate != null) return false;
        if (deliveryDate != null ? !deliveryDate.equals(that.deliveryDate) : that.deliveryDate != null) return false;
        if (guest != null ? !guest.equals(that.guest) : that.guest != null) return false;
        if (isDelivered != null ? !isDelivered.equals(that.isDelivered) : that.isDelivered != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (lastModifiedDate != null ? !lastModifiedDate.equals(that.lastModifiedDate) : that.lastModifiedDate != null)
            return false;
        return version != null ? version.equals(that.version) : that.version == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (acceptDate != null ? acceptDate.hashCode() : 0);
        result = 31 * result + (deliveryDate != null ? deliveryDate.hashCode() : 0);
        result = 31 * result + (guest != null ? guest.hashCode() : 0);
        result = 31 * result + (isDelivered != null ? isDelivered.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (lastModifiedDate != null ? lastModifiedDate.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    public GuestEntity getGuest() {
        return guest;
    }

    public void setGuest(GuestEntity guest) {
        this.guest = guest;
    }

    public Byte getIsDelivered() {
        return isDelivered;
    }

    public void setIsDelivered(Byte isDelivered) {
        this.isDelivered = isDelivered;
    }
}
