package com.aniflix.api.domain.model.web.common;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CommonDepartment {
    
    private String id;

    @NotBlank
    @Size(max = 150)
    private String name;
}
