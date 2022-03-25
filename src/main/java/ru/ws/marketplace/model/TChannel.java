package ru.ws.marketplace.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class TChannel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "nameChannel")
    private String name;
    @Column(name = "descriptionChannel")
    private String description;
    @Column(name = "linkChannel")
    private String link;

}
