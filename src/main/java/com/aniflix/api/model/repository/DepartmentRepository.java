package com.aniflix.api.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aniflix.api.model.entity.DepartmentEntity;
import com.google.common.base.Optional;

public interface DepartmentRepository extends PagingAndSortingRepository<DepartmentEntity, String> {

    Page<DepartmentEntity> findByActiveAndNameContainsIgnoreCase(int active, String text, Pageable pageable);
    Optional<DepartmentEntity> findByName(String name);
}
