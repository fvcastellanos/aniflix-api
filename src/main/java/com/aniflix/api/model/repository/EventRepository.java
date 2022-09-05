package com.aniflix.api.model.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aniflix.api.model.entity.EventEntity;

public interface EventRepository extends PagingAndSortingRepository<EventEntity, String> {
    
    @Query("select event from EventEntity event where event.active = :active and " +
        " upper(event.type) like upper(:type) and " +
        " upper(event.name) like upper(:text) ")
    Page<EventEntity> search(int active, String type, String text, Pageable pageable);

    Optional<EventEntity> findByNameIgnoreCaseAndType(String name, String type);
}
