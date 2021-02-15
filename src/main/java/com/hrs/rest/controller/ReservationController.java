package com.hrs.rest.controller;

import com.hrs.model.ReservationEndRequest;
import com.hrs.model.ReservationModel;
import com.hrs.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping(name = "Create New Reservation", produces = "application/json")
    @Operation(description = "Creates a new reservation specified with guestId, roomId and dateOut. Sample Request: {" +
            "  \"guestId\": 1," +
            "  \"roomId\": 2," +
            "  \"dateOut\": \"2021-02-16 15:25:26\"" +
            "}", parameters = {
            @Parameter(name = "reservationModel", in = ParameterIn.QUERY, required = true, description = "ReservationModel to create a new reservation")
    })
    public ResponseEntity<ReservationModel> createNew(@Valid @RequestBody ReservationModel reservationModel) {
        ReservationModel aNew = reservationService.createNew(reservationModel);
        return ResponseEntity.ok(aNew);
    }

    @PutMapping(name = "End a reservation", produces = "application/json")
    @Operation(description = "Ends a reservation specified with id. Sample Request: {" +
            "    \"id\": 1" +
            "}", parameters = {
            @Parameter(name = "reservationModel", in = ParameterIn.QUERY, required = true, description = "ReservationModel to end an existing reservation")
    })
    public ResponseEntity<ReservationModel> endReservation(@Valid @RequestBody ReservationEndRequest reservationModel) {
        ReservationModel aNew = reservationService.endReservation(reservationModel);
        return ResponseEntity.ok(aNew);
    }

}
