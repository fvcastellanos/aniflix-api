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
import com.aniflix.api.domain.model.web.Department;
import com.aniflix.api.factory.BusinessExceptionFactory;
import com.aniflix.api.model.entity.DepartmentEntity;
import com.aniflix.api.model.repository.DepartmentRepository;

@Service
public class DepartmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);
    
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(final DepartmentRepository departmentRepository) {

        this.departmentRepository = departmentRepository;
    }

    public Page<DepartmentEntity> search(final String text, 
                                         final int active,
                                         final int page,
                                         final int size) {

        LOGGER.info("search departments for text: {}", text);
        final var pageable = PageRequest.of(page, size);

        return departmentRepository.findByActiveAndNameContainsIgnoreCase(active, text, pageable);
    }

    public DepartmentEntity getById(final String id) {

        LOGGER.info("retrieve department with id: {}", id);

        return departmentRepository.findById(id)
            .orElseThrow(() -> BusinessExceptionFactory.createBusinessException(HttpStatus.NOT_FOUND, 
                "No department with id: {} was found", id));
    }

    public DepartmentEntity add(final Department department) {

        LOGGER.info("add new department with name: {}", department.getName());

        final var name = department.getName()
            .toUpperCase();

        final var departmentHolder = departmentRepository.findByName(name);

        if (departmentHolder.isPresent()) {

            LOGGER.warn("Department with name: {} already exists", name);
            return departmentHolder.get();
        }

        var entity = DepartmentEntity.builder()
            .id(UUID.randomUUID().toString())
            .name(name)
            .active(1)
            .build();

        return departmentRepository.save(entity);
    }

    public DepartmentEntity update(final String id, final Department department) {

        LOGGER.info("update department with name: {}", department.getName());

        var departmentEntity = departmentRepository.findById(id)
            .orElseThrow(() -> BusinessExceptionFactory.createBusinessException(HttpStatus.NOT_FOUND, 
                "Department not found"));

        final var active = ActiveStatus.valueOf(department.getActive())
            .value();

        departmentEntity.setName(department.getName().toUpperCase());
        departmentEntity.setActive(active);
        departmentRepository.save(departmentEntity);
        
        return departmentEntity;
    }
}
