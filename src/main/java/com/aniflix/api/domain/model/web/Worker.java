package com.aniflix.api.domain.model.web;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;

import com.aniflix.api.domain.model.web.common.CommonDepartment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Worker extends RepresentationModel<Worker> {
    
    @NotEmpty
    @Size(max = 150)
    private String name;

    @NotEmpty
    @Size(max = 50)
    private String cui;

    @Email
    @NotEmpty
    @Size(max = 300)
    private String email;

    private CommonDepartment department;

    private String active;
}
