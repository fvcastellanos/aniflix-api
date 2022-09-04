package com.aniflix.api.transformer;

import com.aniflix.api.domain.model.status.ActiveStatus;
import com.aniflix.api.domain.model.web.Department;
import com.aniflix.api.model.entity.DepartmentEntity;
import com.aniflix.api.web.controller.DepartmentController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class DepartmentTransformer {
    
    private DepartmentTransformer() {
    }

    public static Department toView(final DepartmentEntity entity) {

        final var selfLink = linkTo(methodOn(DepartmentController.class)
            .getById(entity.getId()))
            .withSelfRel();
           

        final String active = ActiveStatus.of(entity.getActive())
            .name();

        final var view = new Department();
        view.setActive(active);
        view.setName(entity.getName());
        view.add(selfLink);

        return view;
    }
}
