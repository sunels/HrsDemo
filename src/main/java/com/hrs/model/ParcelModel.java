package com.hrs.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParcelModel {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant acceptDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant deliveryDate;

    @NotNull(message = "parcelDescription can not be empty")
    @NotEmpty
    @Size(min = 10, max = 1024)
    private String parcelDescription;

    @NotNull
    private Long guestId;

    private Byte isDelivered;

}
