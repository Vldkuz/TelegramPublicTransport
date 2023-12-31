package Develop.Telegram.Runner;

import Develop.Telegram.RequestHandler;
import Develop.Telegram.SessionHolder.SessionHolder;
import Develop.Telegram.UserHolder.Session;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.Queue;

public class TelegramBot extends TelegramLongPollingBot {

  private final String keyTelegram = System.getenv("KEY_TELEGRAM");
  private final SessionHolder sessionHolder = new SessionHolder(System.getenv("KEY_YANDEX"),
      "Session", System.getenv("DATABASE_URL"), System.getenv("PORT_DB"), System.getenv("USER_DB"),
      System.getenv("USER_PASSWD"));

  @Override
  public String getBotUsername() {
    return "MainTransport_bot";
  }

  @Override
  public String getBotToken() {
    return keyTelegram;
  }

  @Override
  public void onUpdateReceived(Update update) {
    SendMessage sendMessage = new SendMessage();

    String chatId = String.valueOf(update.getMessage().getChatId());
    Session curSession = sessionHolder.get(chatId);
    RequestHandler requestHandler = new RequestHandler(curSession);
    Queue<SendMessage> answer = null;

    if (update.getMessage().hasText()) {
      String request = update.getMessage().getText();
      answer = requestHandler.getAnswer(request, curSession);
    }

    if (update.getMessage().hasLocation()) {
      Location locale = update.getMessage().getLocation();
      answer = requestHandler.getAnswer(locale);
    }

    sendMessageQueueString(chatId, answer);
  }
  public void sendMessageQueueString(String chatId, Queue<SendMessage> messageQueue) {
      if (messageQueue == null) {
          return;
      }

    while (!messageQueue.isEmpty()) {
      SendMessage sendMessage = messageQueue.remove();
      sendMessage.setChatId(chatId);
        if (sendMessage.getText() == null) {
            continue;
        }
      try {
        execute(sendMessage);
      } catch (TelegramApiException ignored) {
      }
    }
  }
}














