package ru.ws.marketplace.model;

import lombok.*;

import javax.persistence.*;
import java.util.Calendar;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
public class TUser {

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
    @Column(name = "link")
    private String link;
    @Column(name = "start_date")
    private Calendar startDate;
    @Column(name = "end_date")
    private Calendar endDate;
    @Column(name = "payment")
    private Integer payment;
    @Column(name = "channel_id")
    private Integer channelId;
    @Column(name = "channel_name")
    private String channelName;
    @Column(name = "status")
    private String status;

    public TUser() {
    }

}
