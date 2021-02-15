package com.hrs.rest.controller;

import com.hrs.model.RoomModel;
import com.hrs.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/room")
public class RoomController {

    private final RoomService roomService;

    public RoomController( RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping(name = "Create New Room", produces = "application/json")
    @Operation(description = "Creates a new room specified with the unique doorNumber", parameters = {
            @Parameter(name = "roomModel", in = ParameterIn.QUERY, required = true, description = "RoomModel to create a new room with a unique doorNumber")
    })
    public ResponseEntity<RoomModel> createNew(@Valid @RequestBody RoomModel roomModel) {
        RoomModel aNew = roomService.createNew(roomModel);
        return ResponseEntity.ok(aNew);
    }

    @GetMapping(name = "Get Room By DoorNumber", value = "/doorNumber/{doorNumber}", produces = "application/json")
    @Operation(description = "Returns an existing room specified with the doorNumber", parameters = {
            @Parameter(name = "doorNumber", in = ParameterIn.QUERY, required = true, description = "Integer doorNumber of the room")
    })
    public ResponseEntity<RoomModel> getRoomByDoorNumber(@NotNull @PathVariable Integer doorNumber) {
        return ResponseEntity.ok(roomService.findByDoorNumber(doorNumber));
    }

}
