package com.aniflix.api.transformer;

import com.aniflix.api.domain.model.status.ActiveStatus;
import com.aniflix.api.domain.model.type.EventType;
import com.aniflix.api.domain.model.web.Event;
import com.aniflix.api.model.entity.EventEntity;
import com.aniflix.api.web.controller.EventController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class EventTransformer {
    
    public static final Event toView(EventEntity entity) {

        final var selfLink = linkTo(methodOn(EventController.class)
            .findById(entity.getId()))
            .withSelfRel();

        final var active = ActiveStatus.of(entity.getActive())
            .name();

        final var type = EventType.of(entity.getType())
            .name();

        final var event = new Event();
        event.setName(entity.getName());
        event.setActive(active);
        event.setType(type);
        event.add(selfLink);

        return event;
    }
}
