package com.aniflix.api.transformer;

import com.aniflix.api.domain.model.web.WorkerEvent;
import com.aniflix.api.model.entity.WorkerEventEntity;
import com.aniflix.api.web.controller.WorkerController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class WorkerEventTransformer {
    
    public static WorkerEvent toView(final String workerId, final WorkerEventEntity entity) {

        final var selfLink = linkTo(methodOn(WorkerController.class)
            .workerEventId(workerId, entity.getId()))
            .withSelfRel();

        final var view = new WorkerEvent();
        view.setEventDate(entity.getEventDate().toString());
        view.setEventId(entity.getEvent().getId());
        view.setEventName(entity.getEvent().getName());
        view.add(selfLink);

        return view;
    }
}
