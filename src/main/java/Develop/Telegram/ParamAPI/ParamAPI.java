package Develop.Telegram.ParamAPI;

import Develop.API.APIExceptions.HTTPClientException;
import Develop.API.APIExceptions.ParserException;
import Develop.API.APIExceptions.ValidationException;
import Develop.API.APIObj.SheduleBetStation.SheduleBetStation;
import Develop.API.APIObj.SheduleStation.SheduleStation;
import Develop.API.APIServices.ParamBuilder;
import Develop.API.APIYandex;
import Develop.Telegram.UserHolder.Session;

import java.util.Queue;

public class ParamAPI {

    public static void getSheduleStation(String request, Session session, Queue<String> answer) {
        ParamBuilder param = new ParamBuilder();
        param.setStation(request);

        try {
            SheduleStation shedule = session.getApiUser().getSheduleStation(param);
            StringBuilder firstStr = new StringBuilder("");
            // Здесь нужно добавить просто шаблон, куда будет все подставляться из объекта api

            firstStr.append("тип станции:\t" + shedule.getStation().getStationTypeName());
            firstStr.append("\n");
            firstStr.append("название станции:\t" + shedule.getStation().getTitle());
            firstStr.append("\n");
            firstStr.append(
                    "тип транспорта:\t" + shedule.getStation().getTransportType() + "\n" + "\n");

            answer.add(firstStr.toString());

            for (int i = 0; i < shedule.getSchedule().size(); ++i) {
                StringBuilder ansPart = new StringBuilder();
                ansPart.append("рейс\t" + shedule.getSchedule().get(i).getThread().getTitle() + "\n");
                ansPart.append("номер рейса:\t" + shedule.getSchedule().get(i).getThread().getNumber() + "\n");
                ansPart.append("даты отъезда:\t" + shedule.getSchedule().get(i).getDays() + "\n");
                ansPart.append("время отправления:\t" + shedule.getSchedule().get(i).getDays() + "\n");
                ansPart.append("\n\n");

                answer.add(ansPart.toString());
            }
            session.getInfoHolder().pushStation(request);

        } catch (ParserException e) {
            answer.add("Произошла ошибка парсера");
        } catch (HTTPClientException e) {
            answer.add("Произошла ошибка клиента");
        } catch (ValidationException e) {
            answer.add("Произошла ошибка валидации");
        }
    }

    public static void getShedule(Session session, Queue<String> answer) {
        APIYandex api = session.getApiUser();

        ParamBuilder params = new ParamBuilder();
        params.setTo(session.getInfoHolder().getLastDestination());
        params.setFrom(session.getInfoHolder().getLastSource());

        try {
            SheduleBetStation shedule = api.getShedule(params);
            // Парсинг объекта shedule Ceмен :)
            // Понял тебя Владислав ☺
            StringBuilder startPart = new StringBuilder("");
            startPart.append(shedule.getSearch().getFrom().getTitle() + "(" + shedule.getSearch().getFrom().getCode() +
                    ")" + "\n\t->\n" + shedule.getSearch().getTo().getTitle() + "("
                    + shedule.getSearch().getTo().getCode() + ")");

            answer.add(String.valueOf(startPart));

            for (int i = 0; i < shedule.getSegments().size(); ++i) {
                StringBuilder endPart = new StringBuilder();
                endPart.append("Время отправления: " + shedule.getSegments().get(i).getDeparture() + "\n");
                endPart.append("Время прибытия: " + shedule.getSegments().get(i).getArrival() + "\n");
                endPart.append("Ваш маршрут " + shedule.getSegments().get(i).getThread().getTitle() + "\n");
                endPart.append("Тип транспорта " + shedule.getSegments().get(i).getThread().getTransportType() + "\n");
                endPart.append("Тип билета и его кашерность " + shedule.getSegments().get(i).getTicketsInfo().getPlaces().get(0).getName() + "\n" + shedule.getSegments().get(i).getTicketsInfo().getPlaces().get(0).getPrice().getWhole() + "\n");
            }


        } catch (HTTPClientException e) {
            answer.add("Произошла ошибка клиента");
        } catch (ParserException e) {
            answer.add("Произошла ошибка парсера");
        } catch (ValidationException e) {
            answer.add("Произошла ошибка валидации");
        }
    }

    public static boolean validateStation(String station, Session session) {
        ParamBuilder paramSt = new ParamBuilder();
        paramSt.setStation(station);
        try {
            session.getApiUser().getSheduleStation(paramSt);
        } catch (HTTPClientException | ParserException | ValidationException e) {
            return false;
        }
        return true;
    }
}