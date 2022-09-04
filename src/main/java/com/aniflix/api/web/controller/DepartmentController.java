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

import com.aniflix.api.domain.model.web.Department;
import com.aniflix.api.service.DepartmentService;
import com.aniflix.api.transformer.DepartmentTransformer;

@RestController
@RequestMapping("/departments")
public class DepartmentController extends ControllerBase {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(final DepartmentService departmentService) {

        this.departmentService = departmentService;
    }
    
    @GetMapping
    public ResponseEntity<Page<Department>> search(@RequestParam(defaultValue = "") final String text,
                                                   @RequestParam(defaultValue = "1") final int active,
                                                   @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
                                                   @RequestParam(defaultValue = DEFAULT_SIZE) final int size) {

        final var departmentsPage = departmentService.search(text, active, page, size);

        final var departments = departmentsPage.stream()
            .map(DepartmentTransformer::toView)
            .collect(Collectors.toList());

        var response = new PageImpl<>(departments, Pageable.ofSize(size), departmentsPage.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getById(@PathVariable @NotBlank final String id) {

        final var entity = departmentService.getById(id);
        var response = DepartmentTransformer.toView(entity);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Department> add(@Valid @RequestBody final Department department) {

        final var entity = departmentService.add(department);
        final var response = DepartmentTransformer.toView(entity);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> update(@PathVariable @NotBlank final String id,
                                             @RequestBody @Valid final Department department) {

        final var entity = departmentService.update(id, department);
        final var response = DepartmentTransformer.toView(entity);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
