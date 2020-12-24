package zhm.Train.DAO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import zhm.Train.domain.Ticket;


import java.util.List;
import java.util.Map;

@Repository
@Component
public interface TicketDAO {

    @Update("update TOrder set CancelOrder = true where TOrderID = #{TOrderID}")
    Integer CancelTicketByID(Integer ticketID);

    @Select("select * from TOrder where UserID = #{id}")
    List<Ticket> getTicketListByUserID(Integer id);

    // 定义触发器
    @Update("update TOrder set TicketPrint=true where TOrderID=#{ticketID}")
    Integer printTicketByID(Integer ticketID);

    @Insert("insert into TOrder(UserID, TrainID, BeginPosID, EndPosID, SeatID, CreateTime) value(#{userID}, #{id}, #{sourStationID}, #{destStationID}, #{SeatID}, now())")
    Integer AddTicket(@Param("userID") int userID, @Param("id") int id, @Param("sourStationID") Integer sourStationID, @Param("destStationID") Integer destStationID, @Param("SeatID") Integer SeatID);

    @Select("select * from TOrder where TOrderID=#{ticketID}")
    Map<String, Object> getTicketByID(Integer ticketID);
}
