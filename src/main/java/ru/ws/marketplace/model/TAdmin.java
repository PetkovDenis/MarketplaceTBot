package ru.ws.marketplace.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "admins")
@Builder
@AllArgsConstructor
public class TAdmin {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "chat_id")
    private Long chatId;
    @Column(name = "channel_name")
    private String channelName;
    @Column(name = "channel_id")
    private Integer channelId;

    public TAdmin() {
    }
}
