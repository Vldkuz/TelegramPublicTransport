package Develop.Telegram.UserHolder;

public enum State {
  start,
  waitDataShedule,
  waitDataSheduleStation,
  waitDataFollowList,
  waitDataNearStations,
  waitDataNearCity,
  waitDataInfoCarrier,
  waitRecentStations
}

/*
Состояния пользователя:

1 - Начальное состояние
2 - Состояние help

enum с waitData
3 - Cостояние введена команда расписания рейсов между станциями
4 - Состояние введена команда расписание рейсов по станции
5 - Состояние введена команда списка станций следования
6 - Состояние введена команда списка ближайщих станций
7 - Состояние введена команда ближайшего города
8 - Состояние введена команда информация о перевозчике

9 - Cостояние получаем данные расписания рейсов между станциями
10 - Состояние получаем данные расписание рейсов по станции
11 - Состояние получаем данные списка станций следования
12 - Состояние получаем данные списка ближайщих станций
13 - Состояние получаем данные ближайшего города
14 - Состояние получаем данные информация о перевозчике
 */
