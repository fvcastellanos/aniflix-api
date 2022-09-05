package com.aniflix.api.model.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aniflix.api.model.entity.WorkerEntity;

public interface WorkerRepository extends PagingAndSortingRepository<WorkerEntity, String> {
    
    @Query("select worker from WorkerEntity worker where worker.active = :active and " + 
        " (upper(worker.name) like upper(:text) or upper(worker.cui) like upper(:text) or " +
        "   upper(worker.email) like upper(:text))")
    Page<WorkerEntity> search(int active, String text, Pageable pageable);

    Optional<WorkerEntity> findByCui(String cui);
    Optional<WorkerEntity> findByEmail(String email);
}
