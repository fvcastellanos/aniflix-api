package com.aniflix.api.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.aniflix.api.domain.model.status.ActiveStatus;
import com.aniflix.api.domain.model.web.Worker;
import com.aniflix.api.factory.BusinessExceptionFactory;
import com.aniflix.api.model.entity.WorkerEntity;
import com.aniflix.api.model.repository.DepartmentRepository;
import com.aniflix.api.model.repository.WorkerRepository;

@Service
public class WorkerService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerService.class);

    private final WorkerRepository workerRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public WorkerService(final WorkerRepository workerRepository,
                         final DepartmentRepository departmentRepository) {

        this.workerRepository = workerRepository;
        this.departmentRepository = departmentRepository;
    }

    public Page<WorkerEntity> search(final String text, final int active, final int page, final int size) {

        LOGGER.info("search workers for text: {}", text);

        var pageable = PageRequest.of(page, size);
        return workerRepository.search(active, "%" + text + "%", pageable);
    }

    public WorkerEntity findById(final String id) {

        LOGGER.info("search worker with id: {}", id);

        return workerRepository.findById(id)
            .orElseThrow(() -> BusinessExceptionFactory.createBusinessException(HttpStatus.NOT_FOUND, "Worker not found"));
    }

    public WorkerEntity add(final Worker worker) {

        LOGGER.info("adding a new worker with name: {}", worker.getName());

        var departmentEntity = departmentRepository.findById(worker.getDepartment().getId())
            .orElseThrow(() -> BusinessExceptionFactory.createBusinessException(HttpStatus.UNPROCESSABLE_ENTITY, 
                "Department not found"));

        var cuiHolder = workerRepository.findByCui(worker.getCui());
        if (cuiHolder.isPresent()) {

            throw BusinessExceptionFactory.createBusinessException(HttpStatus.UNPROCESSABLE_ENTITY, "CUI already in use");
        }

        var emailHolder = workerRepository.findByEmail(worker.getEmail());
        if (emailHolder.isPresent()) {

            throw BusinessExceptionFactory.createBusinessException(HttpStatus.UNPROCESSABLE_ENTITY, "Email already in use");
        }

        var entity = WorkerEntity.builder()
            .id(UUID.randomUUID().toString())
            .department(departmentEntity)
            .name(worker.getName())
            .cui(worker.getCui())
            .email(worker.getEmail())
            .active(1)
            .build();

        return workerRepository.save(entity);
    }

    public WorkerEntity update(final String id, final Worker worker) {

        LOGGER.info("update worker with name: {}", worker.getName());

        var entity = workerRepository.findById(id)
            .orElseThrow(() -> BusinessExceptionFactory.createBusinessException(HttpStatus.NOT_FOUND, "Worker not found"));

        var departmentEntity = departmentRepository.findById(worker.getDepartment().getId())
            .orElseThrow(() -> BusinessExceptionFactory.createBusinessException(HttpStatus.UNPROCESSABLE_ENTITY, 
                "Department not found"));

        final var active = ActiveStatus.valueOf(worker.getActive())
            .value();

        entity.setCui(worker.getCui());
        entity.setEmail(worker.getEmail());
        entity.setName(worker.getName());
        entity.setDepartment(departmentEntity);
        entity.setActive(active);
                
        return workerRepository.save(entity);
    }
}
