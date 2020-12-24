package zhm.Train.DAO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import zhm.Train.domain.Manager;

@Repository
@Component
public interface ManagerDAO {

    @Select("select count(*) from Manager where username=#{Username} and passwd=#{Passwd}")
    Boolean CheckByManager(Manager manager);

    @Select("select * from Manager where username=#{Username} and passwd=#{Passwd}")
    Manager getManagerByAccount(Manager manager);

    @Insert("insert into manageTrainLog(TrainID, ManagerID, ChangeLoh) value(#{id}, #{id1}, #{s})")
    void addTrainLog(@Param("id") int id,@Param("id1") Integer id1,@Param("s") String s);
}
