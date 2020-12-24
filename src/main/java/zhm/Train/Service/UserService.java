package zhm.Train.Service;

import zhm.Train.Utils.Type.RegisterType;
import zhm.Train.domain.Manager;
import zhm.Train.domain.Ticket;
import zhm.Train.domain.Train;
import zhm.Train.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    RegisterType Register(User user);

    Boolean Login(User user);

    Boolean CancelTicket(Integer TicketID);

    User getUserByAccount(User user);

    List<Ticket> getAllTicket(User user);

    List<Map<String, Object>> Booking(String time, String sour, String dest);

    Boolean ChangeTicket(Integer ticketID, String newTrain);

    User getUserByUsername(String username);

    Map<String, Object> changePower(Integer userID, boolean flag, Manager manager);

    Boolean CommitBooking(Integer trainId, String time, String sour, String dest, int userID);

    boolean printTicket(Integer ticketID);

    boolean cancelTicket(Integer ticketID);
}
