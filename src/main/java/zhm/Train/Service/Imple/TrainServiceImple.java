package zhm.Train.Service.Imple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhm.Train.DAO.TicketDAO;
import zhm.Train.DAO.TrainDAO;
import zhm.Train.Service.TrainService;
import zhm.Train.domain.Train;

@Service
public class TrainServiceImple implements TrainService {

    private TrainDAO trainDAO;

    private TicketDAO ticketDAO;

    @Autowired
    public void setTrainDAO(TrainDAO trainDAO) {
        this.trainDAO = trainDAO;
    }

    @Autowired
    public void setTicketDAO(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    @Override
    public String getNameByID(int trainID) {
        String name = this.trainDAO.getNameByID(trainID);
        return name;
    }

    @Override
    public String getStationNameByID(int beginPosID) {
        String name = this.trainDAO.getStationNameByID(beginPosID);
        return name;
    }

    @Override
    public String getTimeByStationID(int beginPosID, int trainID) {
        String time = this.trainDAO.getTimeByStationAndTrain(beginPosID, trainID);
        return time;
    }

    @Override
    public Boolean printTicketByTicketID(Integer ticketID) {
        // 定义个触发器
        Integer cnt = this.ticketDAO.printTicketByID(ticketID);
        if(cnt > 0){
            return true;
        }
        return false;
    }

    @Override
    public double getPrice(int trainID) {
        Train train = this.trainDAO.getTrainByID(trainID);
        return train.getPrice();
    }
}
