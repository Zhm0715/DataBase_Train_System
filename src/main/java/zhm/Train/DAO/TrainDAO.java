package zhm.Train.DAO;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import zhm.Train.domain.Train;

import java.util.List;
import java.util.Map;

@Repository
@Component
public interface TrainDAO {

    @Select("select * from Seat where ID=#{SeatID} limit 1")
    Map<String, Object> getSeatByID(int SeatID);

    @Select("select TrainName from Train where id=#{trainID}")
    String getNameByID(int trainID);

    @Select("select PassTime from TrainStation where TrainID=#{trainID} and StationID=#{PosID}")
    String getTimeByStationAndTrain(@Param("PosID") int PosID, @Param("trainID") int trainID);

    @Select("select StationName from Station where id=#{PosID}")
    String getStationNameByID(int PosID);

    @Select("select * from Train where id=#{trainID}")
    Train getTrainByID(Integer trainID);

    @Insert("insert into Train(TrainName, sourStationID, destStationID, StationCnt, price, BeginTime) " +
            "value(#{TrainName}, #{sourStationID}, #{destStationID}, #{StationCnt}, #{price}, #{BeginTime})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    void AddTrain(Train train);

    @Insert("insert into Seat(TrainID) value(#{TrainID})")
    Integer AddSeat(Integer TrainID);

    @Select("call GetBookingTrain(#{time}, #{sour}, #{dest})")
    List<Integer> getTrainByBooking(@Param("time") String time,@Param("sour") Integer sour, @Param("dest") int dest);

    // limit1优化查询
    @Select("select id from Station  where StationName = #{sour} limit 1")
    Integer getIDByName(@Param("sour") String sour);

    @Select("select id from Station where StationName=#{beginPos} limit 1")
    Integer getStationIDByName(String beginPos);

    @Insert("insert into TrainStation(OrderID, TrainID, StationID, PassTime) value(#{order}, #{TrainID}, #{sourID}, #{beginTime})")
    void AddTrainStation(@Param("TrainID") Integer TrainID, @Param("sourID") Integer sourID, @Param("beginTime") String beginTime, @Param("order") Integer order);

    @Select("select *, version from Seat where TrainID=#{id}")
    List<Map<String, Object>> getAllSeatByTrainID(int id);

    @Update("update Seat set AvilableSeq=#{parseInt}, version=version+1 where id=#{id} and version=#{version}")
    Integer commitBooking(@Param("id") Integer id, @Param("parseInt") int parseInt, @Param("version") int version);

    @Select("select OrderID from TrainStation where TrainID=#{TrainID} and StationID=#{StationID}")
    Integer getPassOrder(@Param("TrainID") Integer TrainID, @Param("StationID") Integer StationID);
}
