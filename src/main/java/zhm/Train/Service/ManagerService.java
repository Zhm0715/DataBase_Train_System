package zhm.Train.Service;

import zhm.Train.domain.Manager;
import zhm.Train.domain.Train;

public interface ManagerService {

    Boolean Login(Manager manager);

    Manager getManagerByAccount(Manager manager);

    boolean AddTrain(Train train, Integer seatcnt, Integer stationCnt, String beginPos, String beginTime, String endPos, String endTime, Manager manager);
}
