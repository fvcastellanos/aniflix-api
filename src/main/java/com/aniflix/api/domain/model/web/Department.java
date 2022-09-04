package com.aniflix.api.domain.model.web;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;

import com.aniflix.api.domain.model.status.ActiveStatus;
import com.aniflix.api.domain.model.validator.ValueOfEnum;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Department extends RepresentationModel<Department> {
    
    @NotEmpty
    @Size(max = 150)
    private String name;

    @ValueOfEnum(enumType = ActiveStatus.class, message = "Invalid type, allowed values: ACTIVE|INACTIVE")    
    private String active;
}
