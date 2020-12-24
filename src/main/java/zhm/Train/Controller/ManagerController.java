package zhm.Train.Controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zhm.Train.Service.ManagerService;
import zhm.Train.Service.TrainService;
import zhm.Train.Service.UserService;
import zhm.Train.domain.Manager;
import zhm.Train.domain.Train;
import zhm.Train.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/Manager")
public class ManagerController {

    private ManagerService managerService;

    private UserService userService;

    private TrainService trainService;

    @Autowired
    public void setManagerService(ManagerService managerService) {
        this.managerService = managerService;
    }

    @Autowired
    public void setTrainService(TrainService trainService) {
        this.trainService = trainService;
    }

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @RequestMapping("/AddTrainPage")
    public String AddTrainPage(){
        return "AddTrain.html";
    }

    @RequestMapping("/AddTrain")
    @ResponseBody
    public String AddTrain(Train train, Integer seatcnt, Integer StationCnt, String BeginPos, String EndPos,
                           String BeginTime, String EndTime, HttpServletRequest request){
        HttpSession session = request.getSession();
        Manager user = (Manager)session.getAttribute("Manager_SESSION");
        if(this.managerService.AddTrain(train, seatcnt, StationCnt, BeginPos, BeginTime, EndPos, EndTime, user)){
            return "Add Train SUCCESS!\n<a href=\"/zhm/Manager/Main\">Back To Pages</a>";
        }
        return "Add Train Fail!\n<a href=\"/zhm/Manager/Main\">Back To Pages</a>";
    }

    @RequestMapping("/ModifyUserPage")
    public String ModifyUserPage(){
        return "ModifyUser.html";
    }

    @RequestMapping("/GetUser")
    public void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = (String) request.getParameter("username");
        User user = this.userService.getUserByUsername(username);
        response.setContentType("text/javascript");
        System.out.println(JSON.toJSONString(user));
        response.getWriter().println(JSON.toJSONString(user));
    }

    @RequestMapping("/UserChange")
    public void UserChange(HttpServletResponse response, HttpServletRequest request) throws IOException {
        Integer userID = Integer.parseInt(request.getParameter("userID"));
        boolean flag = Boolean.parseBoolean(request.getParameter("flag"));
        HttpSession session = request.getSession();
        Manager user = (Manager)session.getAttribute("Manager_SESSION");
        System.out.println(userID);
        System.out.println(flag);
        Map<String, Object> map = this.userService.changePower(userID, flag, user);
        System.out.println(JSON.toJSONString(map));
        response.setContentType("text/javascript");
        response.getWriter().println(JSON.toJSONString(map));
    }
}
