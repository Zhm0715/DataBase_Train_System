package zhm.Train.Service.Imple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhm.Train.DAO.TicketDAO;
import zhm.Train.DAO.TrainDAO;
import zhm.Train.DAO.UserDAO;
import zhm.Train.Service.UserService;
import zhm.Train.Utils.Type.RegisterType;
import zhm.Train.domain.Manager;
import zhm.Train.domain.Ticket;
import zhm.Train.domain.Train;
import zhm.Train.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImple implements UserService{

    private UserDAO userDAO;

    private TrainDAO trainDAO;

    private TicketDAO ticketDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setTrainDAO(TrainDAO trainDAO) {
        this.trainDAO = trainDAO;
    }

    @Autowired
    public void setTicketDAO(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    @Override
    public RegisterType Register(User user) {
        if(user.equals(null) || user.getPasswd().equals(null) || user.getUsername().equals(null)){
            return RegisterType.AccountEmpty;
        }
        if(user.getPasswd().length() < 8){
            return RegisterType.PasswordTooShort;
        }
        Integer cnt = this.userDAO.getUserCntByUsername(user.getUsername());
        if(cnt > 0){
            return RegisterType.RepeatUsername;
        }
        this.userDAO.AddUser(user);
        return RegisterType.Success;
    }

    @Override
    public Boolean Login(User user) {
        return this.userDAO.CheckByUser(user);
    }

    @Override
    public Boolean CancelTicket(Integer TicketID) {
        Integer changeCnt = this.ticketDAO.CancelTicketByID(TicketID);
        if(changeCnt == 0) return false;
        return true;
    }

    // 这是已经确认有了
    @Override
    public User getUserByAccount(User user) {
        User u = this.userDAO.getUserByAccount(user);
        return u;
    }

    @Override
    public List<Ticket> getAllTicket(User user) {
        List<Ticket> ticketList = this.ticketDAO.getTicketListByUserID(user.getID());
        return ticketList;
    }

    @Override
    public List<Map<String, Object>> Booking(String time, String sour, String dest) {
        Integer sourStationID = this.trainDAO.getIDByName(sour);
        Integer destStationID = this.trainDAO.getIDByName(dest);
        if(sourStationID == null || destStationID == null){
            return null;
        }
        List<Integer> trainIDList = this.trainDAO.getTrainByBooking(time, sourStationID, destStationID);
        List<Train> trainList = new ArrayList<>();
        if(trainIDList.size() == 0){
            return null;
        }
        for(Integer i : trainIDList){
            trainList.add(this.trainDAO.getTrainByID(i));
        }
        // 列车名称 开始时间 结束时间 价格 余票数量
        List<Map<String, Object>> ans = new ArrayList<>();
        for(Train train : trainList){
            Map<String, Object> newmap = new HashMap<>();
            if(train.isIsdelete()){
                continue;
            }
            newmap.put("TrainID", train.getId());
            newmap.put("TrainName", train.getTrainName());
            newmap.put("BeginTime", this.trainDAO.getTimeByStationAndTrain(sourStationID, train.getId()));
            newmap.put("EndTime", this.trainDAO.getTimeByStationAndTrain(destStationID, train.getId()));
            newmap.put("price", train.getPrice());
            // 1. 得到所有座位 再进行操作
            List<Map<String, Object>> cnt = this.trainDAO.getAllSeatByTrainID(train.getId());
            int StationCnt = train.getStationCnt();
            int remain = 0;
            int beginPosID = this.trainDAO.getPassOrder(train.getId(), sourStationID);
            int endPosID = this.trainDAO.getPassOrder(train.getId(), destStationID);
            for(Map<String, Object> map : cnt){
                int NowSeat = (Integer) map.get("AvilableSeq");
                String SeatStr = Integer.toBinaryString(NowSeat);
                for(int i = SeatStr.length();i < StationCnt;++i){
                    SeatStr = "0" + SeatStr;
                }
                char[] status = SeatStr.toCharArray();
                boolean flag = true;
                // 站点排序从0开始
                for(int i = beginPosID;i <= endPosID;++i){
                    if(status[i] == '1'){
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    ++remain;
                }
            }
            newmap.put("TRemian", remain);
            if(remain > 0) {
                ans.add(newmap);
            }
        }
        return ans;
    }

    @Override
    public Boolean ChangeTicket(Integer ticketID, String newTrain) {
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userDAO.getUserByUsername(username);
    }

    @Override
    public Map<String, Object> changePower(Integer userID, boolean flag, Manager manager) {
        this.userDAO.changePower(userID, flag);
        User user = this.userDAO.getUserByID(userID);
        Map<String, Object> map = new HashMap<>();
        map.put("flag", user.getBuyPower());
        this.userDAO.ChangeUserLog(userID, manager.getID(), "Set User Power " + flag);
        return map;
    }

    @Override
    public Boolean CommitBooking(Integer trainId, String time, String sour, String dest, int userID) {
        Integer sourStationID = this.trainDAO.getIDByName(sour);
        Integer destStationID = this.trainDAO.getIDByName(dest);
        if(sourStationID == null || destStationID == null){
            return false;
        }
        Train train = this.trainDAO.getTrainByID(trainId);
        if(train.isIsdelete()){
            return false;
        }
        List<Map<String, Object>> cnt = this.trainDAO.getAllSeatByTrainID(train.getId());
        int StationCnt = train.getStationCnt();
        for(Map<String, Object> map : cnt){
            int NowSeat = (Integer) map.get("AvilableSeq");
            int version = (Integer) map.get("version");
            String SeatStr = Integer.toBinaryString(NowSeat);
            for(int i = SeatStr.length();i < StationCnt;++i){
                SeatStr = "0" + SeatStr;
            }
            char[] status = SeatStr.toCharArray();
            // 站点排序从0开始
            int beginPosID = this.trainDAO.getPassOrder(train.getId(), sourStationID);
            int endPosID = this.trainDAO.getPassOrder(train.getId(), destStationID);
            boolean flag = true;
            // 站点排序从0开始
            for(int i = beginPosID;i <= endPosID;++i){
                if(status[i] == '1'){
                    flag = false;
                    break;
                }
            }
            if(flag) {
                for (int i = beginPosID; i <= endPosID; ++i) {
                    status[i] = '1';
                }
                String newStr = String.valueOf(status);
                int UpdateCnt = this.trainDAO.commitBooking((Integer) map.get("id"), Integer.parseInt(newStr,2), version);
                if(UpdateCnt > 0){  // 大于0 修改成功
                    this.ticketDAO.AddTicket(userID, train.getId(), sourStationID, destStationID, (Integer) map.get("id"));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean printTicket(Integer ticketID) {
        this.ticketDAO.printTicketByID(ticketID);
        return true;
    }

    @Override
    public boolean cancelTicket(Integer ticketID) {
        // 删除订单
        System.out.println(ticketID);
        // 释放座位
        Map<String, Object> map = this.ticketDAO.getTicketByID(ticketID);
        System.out.println(map);
        int beginPos = (Integer) map.get("BeginPosID");
        int endpos = (Integer) map.get("EndPosID");
        int trainID = (Integer) map.get("TrainID");
        int SeatID = (Integer) map.get("SeatID");
        int beginPosID = this.trainDAO.getPassOrder(trainID, beginPos);
        int endPosID = this.trainDAO.getPassOrder(trainID, endpos);
        Map<String, Object> seat = this.trainDAO.getSeatByID(SeatID);
        System.out.println(seat);
        int NowSeat = (Integer) seat.get("AvilableSeq");
        int version = (Integer) seat.get("Version");
        String SeatStr = Integer.toBinaryString(NowSeat);
        char[] status = SeatStr.toCharArray();
        // 站点排序从0开始
        for(int i = beginPosID;i <= endPosID;++i){
            status[i] = '0';
        }
        String newStr = String.valueOf(status);
        System.out.println(newStr);
        System.out.println(Integer.parseInt(newStr, 2));
        System.out.println(version);
        int UpdateCnt = this.trainDAO.commitBooking((Integer) seat.get("id"), Integer.parseInt(newStr,2), version);
        System.out.println(UpdateCnt);
        if(UpdateCnt > 0){  // 大于0 修改成功
            this.ticketDAO.CancelTicketByID(ticketID);
            return true;
        }
        return false;
    }
}
