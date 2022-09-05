package com.aniflix.api.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.aniflix.api.domain.model.status.ActiveStatus;
import com.aniflix.api.domain.model.type.EventType;
import com.aniflix.api.domain.model.web.Event;
import com.aniflix.api.factory.BusinessExceptionFactory;
import com.aniflix.api.model.entity.EventEntity;
import com.aniflix.api.model.repository.EventRepository;

@Service
public class EventService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {

        this.eventRepository = eventRepository;
    }

    public Page<EventEntity> search(final int active, 
                                    final String text,
                                    final String type,
                                    final int page, 
                                    final int size) {

        LOGGER.info("search events for text: {} and type: {}", text, type);                                        

        var pageable = PageRequest.of(page, size);
        return eventRepository.search(active, type, text, pageable);
    }

    public EventEntity findById(final String id) {

        LOGGER.info("search event with id: {}", id);                                        

        return eventRepository.findById(id)
            .orElseThrow(() -> BusinessExceptionFactory.createBusinessException(HttpStatus.NOT_FOUND, "Event not found"));
    }

    public EventEntity add(final Event event) {

        LOGGER.info("add new event with name: {} and type: {}", event.getName(), event.getType());

        final var eventHolder = eventRepository.findByNameIgnoreCaseAndType(event.getName(), event.getType());

        if (eventHolder.isPresent()) {

            LOGGER.info("event with name: {} and type: {} already exists", event.getName(), event.getType());
            return eventHolder.get();
        }

        final var type = EventType.valueOf(event.getType())
            .value();

        var entity = EventEntity.builder()
            .id(UUID.randomUUID().toString())
            .name(event.getName())
            .type(type)
            .active(1)
            .build();

        return eventRepository.save(entity);
    }

    public EventEntity update(final String id, final Event event) {

        LOGGER.info("update event with name: {} and type: {}", event.getName(), event.getType());

        final var eventEntity = eventRepository.findById(id)
            .orElseThrow(() -> BusinessExceptionFactory.createBusinessException(HttpStatus.NOT_FOUND, "Event not found"));

        final var type = EventType.valueOf(event.getType())
            .value();

        final var active = ActiveStatus.valueOf(event.getActive())
            .value();

        eventEntity.setName(event.getName());
        eventEntity.setType(type);
        eventEntity.setActive(active);

        return eventRepository.save(eventEntity);
    }
}
