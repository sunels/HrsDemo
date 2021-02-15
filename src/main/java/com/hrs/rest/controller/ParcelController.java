package com.hrs.rest.controller;

import com.hrs.model.DeliverParcelRequest;
import com.hrs.model.ParcelModel;
import com.hrs.service.ParcelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

@RestController
@RequestMapping(value = "/parcel")
public class ParcelController {

    private final ParcelService parcelService;

    public ParcelController(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @PostMapping(name = "createNew", produces = "application/json")
    @Operation(description = "Creates a parcel for the guest as specified with guestId in the model. Sample Request :{" +
            "  \"guestId\": 1" +
            "}", parameters = {
            @Parameter(name = "parcelModel", in = ParameterIn.QUERY, required = true, description = "ParcelModel should include a guestId")
    })
    public ResponseEntity<ParcelModel> createNew(@RequestBody @Valid ParcelModel parcelModel) {
        ParcelModel aNew = parcelService.createNew(parcelModel);
        return ResponseEntity.ok(aNew);
    }

    @GetMapping(name = "getByGuestId", value = "/guestid/{guestId}", produces = "application/json")
    @Operation(description = "Returns all parcel for a guest, does not check delivered or not.Just returns all for simplicity", parameters = {
            @Parameter(name = "parcelModel", in = ParameterIn.QUERY, required = true, description = "ParcelModel should include a guestId")
    })
    public ResponseEntity<Set<ParcelModel>> getParcelsOfGuest(@NotNull @PathVariable Long guestId) {
        return ResponseEntity.ok(parcelService.findAllByGuestId(guestId));
    }

    @PutMapping(name = "Deliver Parcel to Guest", produces = "application/json")
    @Operation(description = "Deliver a parcel to the guest as specified with id.", parameters = {
            @Parameter(name = "deliverParcelRequest", in = ParameterIn.QUERY, required = true, description = "ParcelModel should include a guestId")
    })
    public ResponseEntity<ParcelModel> deliverParcel2Guest(@RequestBody @Valid DeliverParcelRequest deliverParcelRequest) {
        ParcelModel deliveredParcel = parcelService.deliverParcel2Guest(deliverParcelRequest);
        return ResponseEntity.ok(deliveredParcel);
    }

}
