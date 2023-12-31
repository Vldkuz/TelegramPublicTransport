package Develop.Telegram.UserHolder;

import Develop.API.APIExceptions.ValidationException;
import Develop.API.APIYandex;

import java.util.concurrent.atomic.AtomicBoolean;

public class Session {

  private State state = State.start; // Характеризует одно из состояний пользователя в enum (Будет выгружаться в БД)
  private final InfoHolder infoHolder = new InfoHolder(); // В info будут лежать все данные по пользователю (Будет выгружаться в БД)
  final private APIYandex api; // Объект, которые предоставляет функции взаимодействия с API
  private int priority = 0; // Создается с максимальным приоритетом
  private AtomicBoolean blocked = new AtomicBoolean(true);

  public Session(String keyPointer) {
    try {
      this.api = new APIYandex(keyPointer);
    } catch (ValidationException e) {
      throw new RuntimeException("Ошибка в API ключе Яндекс");
    }
  }

  public InfoHolder getInfoHolder() {
    return infoHolder;
  }

  public State getState() {
    return state;
  }

  public synchronized void setState(State state) {
    this.state = state;
  }

  public APIYandex getApiUser() {
    return api;
  }

  public int getPriority() {
    return priority;
  }

  public void addPriority() {
    this.priority++;
  }

  public boolean getBlocked() {
    return blocked.get();
  }

  public void setBlocked(boolean value) {
    blocked.set(value);
  }
}


