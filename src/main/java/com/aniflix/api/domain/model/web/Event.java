package com.aniflix.api.domain.model.web;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;

import com.aniflix.api.domain.model.status.ActiveStatus;
import com.aniflix.api.domain.model.type.EventType;
import com.aniflix.api.domain.model.validator.ValueOfEnum;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Event extends RepresentationModel<Event> {
    
    @NotBlank
    @Size(max = 150)
    private String name;

    @NotNull
    @ValueOfEnum(enumType = EventType.class, message = "Invalid type, allowed values: IN|OUT")    
    private String type;

    @ValueOfEnum(enumType = ActiveStatus.class, message = "Invalid status, allowed values: ACTIVE|INACTIVE")    
    private String active;
}
