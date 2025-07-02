package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NotificationTaskService {

    private final Logger logger = LoggerFactory.getLogger(NotificationTaskService.class);

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    NotificationTaskRepository notificationTaskRepository;

    public void addNotificationTask(long chatId, String message) {
        final String REGEX = "(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)";
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(message);
        if (matcher.matches()) {
            String dateTime = matcher.group(1);
            String message1 = matcher.group(3);
            NotificationTask task = new NotificationTask();
            try {
                task.setTimeDateNotification(LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
                task.setTextNotification(message1);
                task.setChatId(chatId);
                logger.error(task.toString());
                notificationTaskRepository.save(task);
            } catch (Exception e) {
                sendMessage(chatId, "Не верный формат данных \nправильный формат: dd.MM.yyyy HH:mm Сообщение");
            }
        } else sendMessage(chatId, "Не верный формат данных \nправильный формат: dd.MM.yyyy HH:mm Сообщение");

    }

    @Scheduled(cron = "0 0/1 * * * *")
    @Transactional
    public void sendMessageTask() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<NotificationTask> tasks = notificationTaskRepository.findByTimeDateNotification(now);
        tasks.forEach(task -> sendMessage(task.getChatId(), task.getTextNotification()));
        notificationTaskRepository.deleteByTimeDateNotification(now);
    }

    public void sendMessage(long chatId, String text) {
        try {
            telegramBot.execute(new SendMessage(chatId, text));
        } catch (Exception e) {
            logger.error("Ошибка при отправке сообщения: ", e);
        }
    }



}
