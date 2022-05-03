package ru.ws.marketplace.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "channels")
@Builder
@AllArgsConstructor
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
    @Column(name = "chat_id")
    private Long chatId;

    public TChannel() {
    }

}
