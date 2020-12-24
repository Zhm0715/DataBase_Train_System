package zhm.Train.DAO;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import zhm.Train.domain.Manager;
import zhm.Train.domain.User;

@Repository
@Component
public interface UserDAO {

    @Select("select count(*) from UserT where username=#{Username} and passwd = #{Passwd}")
    Boolean CheckByUser(User user);

    @Select("select * from UserT where username=#{Username} and passwd=#{Passwd}")
    User getUserByAccount(User user);

    @Select("select count(*) from UserT where username=#{username}")
    Integer getUserCntByUsername(String username);

    @Insert("insert into UserT(username, passwd) value(#{Username}, #{Passwd})")
    void AddUser(User user);

    @Select("select * from UserT where username=#{username} limit 1")
    User getUserByUsername(String username);

    @Update("update UserT set BuyPower=#{flag} where id=#{userID}")
    void changePower(@Param("userID") Integer userID, @Param("flag") boolean flag);

    @Select("select * from UserT where id=#{id} limit 1")
    User getUserByID(Integer id);

    @Insert("insert into manageUserLog(UserID, ManagerID, ChangeLog) value(#{userID}, #{managerID}, #{s})")
    void ChangeUserLog(@Param("userID") Integer userID, @Param("managerID") Integer managerID, @Param("s") String s);
}
