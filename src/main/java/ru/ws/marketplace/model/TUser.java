package ru.ws.marketplace.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
@Builder
public class TUser {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long id;
    @Column(name = "first_name")
    public String firstName;
    @Column(name = "last_name")
    public String lastName;
    @Column(name = "chat_id")
    public Long chatId;
    @Column(name = "link")
    public String link;

    public TUser() {
    }

    public TUser(Long id, String firstName, String lastName, Long chatId, String link) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.chatId = chatId;
        this.link = link;
    }
}
