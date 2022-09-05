package com.aniflix.api.web.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aniflix.api.domain.model.web.Event;
import com.aniflix.api.service.EventService;
import com.aniflix.api.transformer.EventTransformer;

@RestController
@RequestMapping("/events")
public class EventController extends ControllerBase {
    
    private final EventService eventService;

    @Autowired
    public EventController(final EventService eventService) {

        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<Page<Event>> search(@RequestParam(defaultValue = "") final String text,
                                              @RequestParam(defaultValue = "1") final int active,
                                              @RequestParam(defaultValue = "I") final String type,
                                              @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
                                              @RequestParam(defaultValue = DEFAULT_SIZE) final int size) {

        final var eventPage = eventService.search(active, text, type, page, size);

        final var events = eventPage.stream()
            .map(EventTransformer::toView)
            .collect(Collectors.toList());

        final var response = new PageImpl<>(events, Pageable.ofSize(size), eventPage.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> findById(@PathVariable @NotBlank final String id) {

        final var entity = eventService.findById(id);
        final var response = EventTransformer.toView(entity);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Event> add(@RequestBody @Valid final Event event) {

        final var entity = eventService.add(event);
        final var response = EventTransformer.toView(entity);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> update(@PathVariable @NotBlank final String id,
                                        @RequestBody @Valid Event event) {

        final var entity = eventService.update(id, event);
        final var response = EventTransformer.toView(entity);

        return new ResponseEntity<>(response, HttpStatus.OK);                                    
    }

}
