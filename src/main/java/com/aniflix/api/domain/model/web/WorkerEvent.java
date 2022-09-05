package com.aniflix.api.domain.model.web;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;

import com.aniflix.api.domain.model.validator.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class WorkerEvent extends RepresentationModel<WorkerEvent> {
    
    @NotBlank
    @Size(max = 50)
    private String eventId;

    private String eventName;

    @Date
    @NotBlank
    private String eventDate;
}
