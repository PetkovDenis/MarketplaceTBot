package ru.ws.marketplace.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "channels")
@Builder
public class TChannel {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "category")
    private String category;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "link")
    private String link;
    @Column(name = "price")
    private Integer price;

    public TChannel(){}

    public TChannel(Long id, String category, String name, String description, String link, Integer price) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.description = description;
        this.link = link;
        this.price = price;
    }
}
