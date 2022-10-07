package br.com.api.mockito.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;
    private String name;
    private String email;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String password;
}
