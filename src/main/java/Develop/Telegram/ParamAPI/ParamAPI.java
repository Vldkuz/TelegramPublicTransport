package Develop.Telegram.ParamAPI;

import Develop.API.APIExceptions.HTTPClientException;
import Develop.API.APIExceptions.ParserException;
import Develop.API.APIExceptions.ValidationException;
import Develop.API.APIObj.SheduleStation.SheduleStation;
import Develop.API.APIServices.ParamBuilder;
import Develop.Telegram.UserHolder.Session;

import java.util.LinkedList;
import java.util.Queue;

public class ParamAPI {

    public static Queue<String> getDataSheduleStation(String request, Session session) {
        Queue<String> answer = new LinkedList<>();
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
                ansPart.append("даты отъезда:\t" + shedule.getSchedule().get(i).getDays() + "\n");
                ansPart.append("время отправления:\t" + shedule.getSchedule().get(i).getDays() + "\n");
                ansPart.append("\n\n");

                answer.add(ansPart.toString());
            }

        } catch (ParserException e) {
            answer.add("Произошла ошибка парсера");
        } catch (HTTPClientException e) {
            answer.add("Произошла ошибка клиента");
        } catch (ValidationException e) {
            answer.add("Произошла ошибка валидации");
        }

        return answer;

    }
}
//    public ParamAPI(String request, Session session, String nameOfFunction) {
//        param = new ParamBuilder();
//        try {
//            switch (nameOfFunction) {
//                case "waitDataSheduleStation":
//                    param.setStation(request);
//                    SheduleStation shedule = session.getApiUser().getSheduleStation(param);
//                case "waitDataShedule":
//
//                    SheduleBetStation shedule2 = session.getApiUser().getShedule(param);
//            }
//
//        } catch (HTTPClientException e) {
//            throw new RuntimeException(e);
//        } catch (ParserException e) {
//            throw new RuntimeException(e);
//        } catch (ValidationException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    public SheduleStation getParamAPIShedule() {
//        return shedule;
//    }
//
//    public String getStationTypeName() {
//        return shedule.getStation().getStationTypeName();
//    }
//
//    public String getTitle() {
//        return shedule.getStation().getTitle();
//    }
//
//    public String getTransportType() {
//        return shedule.getStation().getTransportType();
//    }
//
//    public List<String> getThreadTitle() {
//        List<String> title = new ArrayList<>();
//        for (int i = 0; i < 3; ++i) {
//            title.add(shedule.getSchedule().get(i).getThread().getTitle());
//        }
//        return title;
//    }
//
//    public List<String> getDays() {
//        List<String> title = new ArrayList<>();
//        for (int i = 0; i < 3; ++i) {
//            title.add(shedule.getSchedule().get(i).getDays());
//        }
//        return title;
//    }
//
//    public int getSheduleSize() {
//        return shedule.getSchedule().size();
//    }

//    public

//paramAPI.getParamAPIShedule().getSchedule().get(i).getThread().getTitle()