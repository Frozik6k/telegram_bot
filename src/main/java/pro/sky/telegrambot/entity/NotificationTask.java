
package pro.sky.telegrambot.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="notification_task")
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private long chatId;
    private String textNotification;
    private LocalDateTime timeDateNotification;

    public int getId() {
        return id;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getTextNotification() {
        return textNotification;
    }

    public void setTextNotification(String textNotification) {
        this.textNotification = textNotification;
    }

    public LocalDateTime getTimeDateNotification() {
        return timeDateNotification;
    }

    public void setTimeDateNotification(LocalDateTime timeDateNotiofication) {
        this.timeDateNotification = timeDateNotiofication;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "textNotification='" + textNotification + '\'' +
                ", chatId=" + chatId +
                ", timeDateNotification=" + timeDateNotification +
                '}';
    }
}