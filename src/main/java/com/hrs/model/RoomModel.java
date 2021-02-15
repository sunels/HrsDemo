package com.hrs.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RoomModel {
    public Long id;

    public byte isAvailable;

    @NotNull
    @Min(value = 1, message = "DoorNumber should not be less than 1")
    @Max(value = 150, message = "DoorNumber should not be greater than 150")
    @JsonProperty(value = "doorNumber")
    public Integer doorNumber;

    public Collection<Long> reservationIds;

}
