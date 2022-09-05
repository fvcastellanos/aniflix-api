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

import com.aniflix.api.domain.model.web.Worker;
import com.aniflix.api.service.WorkerService;
import com.aniflix.api.transformer.WorkerTransformer;

@RestController
@RequestMapping("/workers")
public class WorkerController extends ControllerBase {
    
    private final WorkerService workerService;

    @Autowired
    public WorkerController(final WorkerService workerService) {

        this.workerService = workerService;
    }

    @GetMapping
    public ResponseEntity<Page<Worker>> search(@RequestParam(defaultValue = "") final String text,
                                               @RequestParam(defaultValue = "1") final int active,
                                               @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
                                               @RequestParam(defaultValue = DEFAULT_SIZE) final int size) {

        final var workerPage = workerService.search(text, active, page, size);

        final var workers = workerPage.stream()
            .map(WorkerTransformer::toView)
            .collect(Collectors.toList());

        final var response = new PageImpl<>(workers, Pageable.ofSize(size), workerPage.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Worker> findById(@PathVariable @NotBlank final String id) {

        final var entity = workerService.findById(id);
        final var response = WorkerTransformer.toView(entity);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Worker> add(@RequestBody @Valid final Worker worker) {

        final var workerEntity = workerService.add(worker);
        final var response = WorkerTransformer.toView(workerEntity);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Worker> update(@PathVariable @NotBlank final String id, @RequestBody @Valid Worker worker) {

        final var workerEntity = workerService.update(id, worker);
        final var response = WorkerTransformer.toView(workerEntity);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
