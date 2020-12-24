package zhm.Train.Service;

import zhm.Train.domain.Train;

import java.util.List;

public interface TrainService {

    String getNameByID(int trainID);

    String getStationNameByID(int beginPosID);

    String getTimeByStationID(int beginPosID, int trainID);

    Boolean printTicketByTicketID(Integer ticketID);

    double getPrice(int trainID);
}
