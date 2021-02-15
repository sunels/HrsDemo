package com.hrs.rest.controller;

import com.hrs.model.GuestModel;
import com.hrs.service.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/guest")
@Validated
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping(name = "Create New Guest", produces = "application/json;charset=UTF-8")
    @Operation(description = "Creates a guest, email should be unique Sample Request: {" +
            "  \"fullName\": \"serkan sunel\"," +
            "  \"email\": \"serkan.sunel@gmail.com\"" +
            "}", parameters = {
            @Parameter(name = "guestModel", in = ParameterIn.QUERY, required = true, description = "Provide a guest with full name and unique email")
    })
    public ResponseEntity<GuestModel> createNew(@RequestBody@Valid GuestModel guestModel) {
        GuestModel aNew = guestService.createNew(guestModel);
        return ResponseEntity.ok(aNew);
    }

    @GetMapping(name = "Get Guest By email", value = "/email/{email}", produces = "application/json")
    @Operation(description = "Returns a guest specified with given email", parameters = {
            @Parameter(name = "email", in = ParameterIn.QUERY, required = true, description = "Email address of the guest")
    })
    public ResponseEntity<GuestModel> getGuest(@PathVariable @NotNull @Email(message = "Not a valid email") String email) {
        return ResponseEntity.ok(guestService.findByEmail(email));
    }

}
