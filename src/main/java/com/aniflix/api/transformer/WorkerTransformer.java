package com.aniflix.api.transformer;

import com.aniflix.api.domain.model.status.ActiveStatus;
import com.aniflix.api.domain.model.web.Worker;
import com.aniflix.api.domain.model.web.common.CommonDepartment;
import com.aniflix.api.model.entity.WorkerEntity;
import com.aniflix.api.web.controller.WorkerController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class WorkerTransformer {
    
    public static Worker toView(WorkerEntity workerEntity) {

        final var selfLink = linkTo(methodOn(WorkerController.class)
            .findById(workerEntity.getId()))
            .withSelfRel();

        final var departmentEntity = workerEntity.getDepartment();

        final var department = new CommonDepartment();
        department.setId(departmentEntity.getId());
        department.setName(departmentEntity.getName());

        final var active = ActiveStatus.of(workerEntity.getActive())
            .name();

        final var view = new Worker();        

        view.setDepartment(department);
        view.setName(workerEntity.getName());
        view.setCui(workerEntity.getCui());
        view.setEmail(workerEntity.getEmail());
        view.setActive(active);
        view.add(selfLink);

        return view;
    }
}
