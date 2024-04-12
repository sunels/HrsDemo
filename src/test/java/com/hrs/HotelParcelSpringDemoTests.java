package com.hrs;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@ActiveProfiles (value = "test")
class HotelParcelSpringDemoTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    @Qualifier("customJson")
    private ObjectMapper objectMapper;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ParcelRepository parcelRepository;

    @Autowired
    private ReservationRepository reservationRepository;


    @Test
    void contextLoads() {
    }

    @Test
    void defineGuest() throws Exception {
        defineNewGuest();
        Optional<GuestEntity> guestEntity = guestRepository.findByEmail("serkan.sunel@gmail.com");
        assertThat(guestEntity.get().getFullName()).isEqualTo("serkan sunel");
    }

    @Test
    void defineRoom() throws Exception {
        defineNewRoom();
        Optional<RoomEntity> roomEntityOptional = roomRepository.findById(1L);
        assertThat(roomEntityOptional.get().getId()).isEqualTo(1L);
        assertThat(roomEntityOptional.get().getDoorNumber()).isEqualTo(108L);
    }

    @Test
    void defineReservation() throws Exception {
        defineNewGuest();
        defineNewRoom();
        defineNewReservation();
        Optional<ReservationEntity> roomEntityOptional = reservationRepository.findById(1L);
        assertThat(roomEntityOptional.get().getRoom().getDoorNumber()).isEqualTo(108L);
        assertThat(roomEntityOptional.get().getGuest().getFullName()).isEqualTo("serkan sunel");
        assertThat(roomEntityOptional.get().getGuest().getEmail()).isEqualTo("serkan.sunel@gmail.com");
    }

    @Test
    void defineGuestThenDefineRoomThenDefineReservationThenAcceptParcel() throws Exception {
        defineNewGuest();
        defineNewRoom();
        defineNewReservation();
        acceptAParcelAsBoxForGuest();
        Optional<ReservationEntity> optionalReservationEntity = reservationRepository.findById(1L);
        assertThat(optionalReservationEntity.get().getRoom().getDoorNumber()).isEqualTo(108L);
        assertThat(optionalReservationEntity.get().getGuest().getFullName()).isEqualTo("serkan sunel");
        assertThat(optionalReservationEntity.get().getGuest().getEmail()).isEqualTo("serkan.sunel@gmail.com");
        Set<ParcelEntity> parcelEntities = parcelRepository.findAllByGuestId(optionalReservationEntity.get().getGuest().getId());
        assertThat(parcelEntities.size()).isEqualTo(1);
        assertThat(parcelEntities.stream().anyMatch(p->p.getDescription().
                equals("A yellow box for mr. smith with sku:111011"))).isEqualTo(true);

    }

    @Test
    void defineGuestThenDefineRoomThenDefineReservationThenAcceptParcelThenEndReservationThenRejectAParsel() throws Exception {
        defineNewGuest();
        defineNewRoom();
        defineNewReservation();
        acceptAParcelAsBoxForGuest();
        Optional<ReservationEntity> optionalReservationEntity = reservationRepository.findById(1L);
        assertThat(optionalReservationEntity.get().getRoom().getDoorNumber()).isEqualTo(108L);
        assertThat(optionalReservationEntity.get().getGuest().getFullName()).isEqualTo("serkan sunel");
        assertThat(optionalReservationEntity.get().getGuest().getEmail()).isEqualTo("serkan.sunel@gmail.com");
        Set<ParcelEntity> parcelEntities = parcelRepository.findAllByGuestId(optionalReservationEntity.get().getGuest().getId());
        assertThat(parcelEntities.size()).isEqualTo(1);
        assertThat(parcelEntities.stream().anyMatch(p->p.getDescription().
                equals("A yellow box for mr. smith with sku:111011"))).isEqualTo(true);
        endAReservation();
        rejectAParcelAsLetterForGuest();
    }

    private void defineNewGuest() throws Exception {
        GuestModel guestModel = new GuestModel();
        guestModel.setEmail("serkan.sunel@gmail.com");
        guestModel.setFullName("serkan sunel");
        mockMvc.perform(post("/guest")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(guestModel)))
                .andExpect(status().isOk());
    }

    private void defineNewRoom() throws Exception {
        RoomModel roomModel = new RoomModel();
        roomModel.setDoorNumber(108);
        roomModel.setIsAvailable(Byte.valueOf("0"));

        mockMvc.perform(post("/room")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(roomModel)))
                .andExpect(status().isOk());
    }

    private void defineNewReservation() throws Exception {
        ReservationModel reservationModel = new ReservationModel();
        reservationModel.setDateIn(Instant.now());
        reservationModel.setRoomId(1L);
        reservationModel.setGuestId(1L);
        mockMvc.perform(post("/reservation")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(reservationModel)))
                .andExpect(status().isOk());
    }

    private void endAReservation() throws Exception {
        ReservationModel reservationModel = new ReservationModel();
        reservationModel.setId(1L);
        mockMvc.perform(put("/reservation")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(reservationModel)))
                .andExpect(status().isOk());
    }

    private void acceptAParcelAsBoxForGuest() throws Exception {
        ParcelModel parcelModel = new ParcelModel();
        parcelModel.setGuestId(1L);
        parcelModel.setParcelDescription("A yellow box for mr. smith with sku:111011");
        mockMvc.perform(post("/parcel")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(parcelModel)))
                .andExpect(status().isOk());
    }

    private void acceptAParcelAsLetterForGuest() throws Exception {
        ParcelModel parcelModel = new ParcelModel();
        parcelModel.setGuestId(1L);
        parcelModel.setParcelDescription("A red letter for mr. smith from Mrs. Scarlet");
        mockMvc.perform(post("/parcel")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(parcelModel)))
                .andExpect(status().isOk());
    }

    private void rejectAParcelAsLetterForGuest() throws Exception {
        ParcelModel parcelModel = new ParcelModel();
        parcelModel.setGuestId(1L);
        parcelModel.setParcelDescription("A red letter for mr. smith from Mrs. Scarlet");
        mockMvc.perform(post("/parcel")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(parcelModel)))
                .andExpect(status().isBadRequest());
    }

}
