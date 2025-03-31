package org.exp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ForwardedMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Avtomatik ID

    @Column(name = "message_id")
    private Integer messageId; // Forward qilingan xabar ID

    @Column(name = "user_id")
    private Long userId; // Xabarni olgan user ID

    public ForwardedMessage(Integer messageId, Long userId) {
        this.messageId = messageId;
        this.userId = userId;
    }
}
