package com.hrs.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GuestModel {
    public Long id;

    @NotEmpty
    @NotNull
    @Size(min = 2, max = 255)    @JsonProperty(value = "fullName")
    public String fullName;

    @NotEmpty
    @NotNull
    @Email
    @Size(min = 5, max = 255)
    @JsonProperty(value = "email")
    public String email;

    public Collection<Long> deliveredParcelIds;

    public Collection<Long> undeliveredParcelIds;

    public Collection<Long> reservationsIds;

}
