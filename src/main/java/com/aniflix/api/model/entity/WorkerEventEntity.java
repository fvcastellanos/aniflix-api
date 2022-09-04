package com.aniflix.api.model.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "worker_event")
public class WorkerEventEntity {
    
    @Id
    @Size(max = 50)
    private String id;

    @ManyToOne
    private WorkerEntity worker;

    @ManyToOne
    private EventEntity event;

    @NotNull
    @Column(name = "event_date")
    private Instant eventDate;
}
