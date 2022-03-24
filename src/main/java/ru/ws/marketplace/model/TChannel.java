package ru.ws.marketplace.model;

import lombok.Data;
import org.hibernate.annotations.ValueGenerationType;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import javax.sound.midi.Sequence;

@Data
@Entity
public class TChannel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name="nameChannel")
    private String name;
    @Column(name="descriptionChannel")
    private String description;
    @Column(name="linkChannel")
    private String link;
}
