package zhm.Train.Service.Imple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhm.Train.DAO.ManagerDAO;
import zhm.Train.DAO.TrainDAO;
import zhm.Train.Service.ManagerService;
import zhm.Train.domain.Manager;
import zhm.Train.domain.Train;

@Service
public class ManagerServiceImple implements ManagerService{

    private ManagerDAO managerDAO;

    private TrainDAO trainDAO;

    @Autowired
    public void setManagerDAO(ManagerDAO managerDAO) {
        this.managerDAO = managerDAO;
    }

    @Autowired
    public void setTrainDAO(TrainDAO trainDAO) {
        this.trainDAO = trainDAO;
    }

    @Override
    public Boolean Login(Manager manager) {
        return this.managerDAO.CheckByManager(manager);
    }

    @Override
    public Manager getManagerByAccount(Manager manager) {
        return this.managerDAO.getManagerByAccount(manager);
    }

    @Override
    public boolean AddTrain(Train train, Integer seatcnt, Integer stationCnt, String beginPos, String beginTime, String endPos, String endTime, Manager manager) {
        // 获取Station
        Integer sourID = this.trainDAO.getStationIDByName(beginPos);
        Integer destID = this.trainDAO.getStationIDByName(endPos);

        if(sourID == null || destID == null){
            return false;
        }

        train.setSourStationID(sourID);train.setDestStationID(destID);
        train.setBeginTime(beginTime.substring(0, 10));

        this.trainDAO.AddTrain(train);

        this.trainDAO.AddTrainStation(train.getId(), sourID, beginTime, 0);
        this.trainDAO.AddTrainStation(train.getId(), destID, endTime, train.getStationCnt() - 1);

        for(int i = 0;i < seatcnt;++i){
            this.trainDAO.AddSeat(train.getId());
        }
        this.managerDAO.addTrainLog(train.getId(), manager.getID(), "Add Train.");
        return true;
    }
}
