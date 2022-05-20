package ru.ws.marketplace.model;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Component
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "messages")
public class TMessage {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "message_id")
    private Integer messageId;

    @Column(name = "message_text")
    private String messageText;

    @Column(name = "reply_message_text")
    private String replyMessageText;

    @Column(name = "create_date")
    private Date createDate;

}
