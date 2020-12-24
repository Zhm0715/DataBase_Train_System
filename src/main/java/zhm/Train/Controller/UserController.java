package zhm.Train.Controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zhm.Train.Service.TrainService;
import zhm.Train.Service.UserService;
import zhm.Train.domain.Ticket;
import zhm.Train.domain.Train;
import zhm.Train.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/User")
public class UserController {

    private UserService userService;

    private TrainService trainService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setTrainService(TrainService trainService) {
        this.trainService = trainService;
    }

    @RequestMapping(value="/BookingPage")
    public String BookingPage(){
        return "BookingPage.html";
    }

    @RequestMapping("/getTrainByBooking")
    public void getTrainByBooking(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String Time = (String) request.getParameter("time");
        String sour = (String) request.getParameter("sour");
        String dest = (String) request.getParameter("dest");
        List<Map<String, Object>> ans = this.userService.Booking(Time, sour, dest);
        String data = JSON.toJSONString(ans);
        response.getWriter().println(data);
    }

    @RequestMapping("/Booking")
    public void Booking(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String Time = (String) request.getParameter("time");
        String sour = (String) request.getParameter("sour");
        String dest = (String) request.getParameter("dest");
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("USER_SESSION");
        if(user.getBuyPower() == false){
            Map<String, Object> ans = new HashMap<>();
            ans.put("flag", false);
            response.setContentType("text/javascript");
            response.getWriter().println(JSON.toJSONString(ans));
            return ;
        }
        Integer trainId = Integer.parseInt(request.getParameter("TrainID"));
        boolean success = this.userService.CommitBooking(trainId, Time, sour, dest, user.getID());
        Map<String, Object> ans = new HashMap<>();
        ans.put("flag", success);
        response.setContentType("text/javascript");
        response.getWriter().println(JSON.toJSONString(ans));
    }

    @RequestMapping("/NowTicket")
    public String NowTicket(HttpServletRequest request,
                            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("USER_SESSION");
        if(user == null){
            return "ForbidAccess.html";
        }
        return "NowTicketPage.html";
    }

    @RequestMapping("/GetAllTicket")
    public void getAllTicket(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("USER_SESSION");
        List<Ticket> ticketList =  this.userService.getAllTicket(user);
        List<Map<String, Object>> ans = new ArrayList<>();
        for(Ticket ticket : ticketList){
            if(ticket.getCancelOrder() || ticket.getTicketPrint() || ticket.getChangeOrder()){
                continue;
            }
            Map<String, Object> data = new HashMap<>();
            data.put("ticketID", (Integer)ticket.getTOrderID());
            data.put("TrainName", this.trainService.getNameByID(ticket.getTrainID()));
            data.put("BeginPos", this.trainService.getStationNameByID(ticket.getBeginPosID()));
            data.put("EndPos", this.trainService.getStationNameByID(ticket.getEndPosID()));
            data.put("SeatID", ticket.getSeatID());
            data.put("BeginTime", this.trainService.getTimeByStationID(ticket.getBeginPosID(), ticket.getTrainID()));
            data.put("EndTime", this.trainService.getTimeByStationID(ticket.getEndPosID(), ticket.getTrainID()));
            data.put("price", this.trainService.getPrice(ticket.getTrainID()));
            ans.add(data);
        }
        response.setContentType("text/javascript");
        response.getWriter().println(JSON.toJSONString(ans));
    }

    // 打印车票(打印后不可退票)(跳转请求)
    @RequestMapping("/PrintTicket")
    @ResponseBody
    public String printTicket(Integer TicketID, HttpServletRequest request){
        Boolean success = this.trainService.printTicketByTicketID(TicketID);
        if(success){
            return "Print Ticket Success!";
        }
        return "Print Ticket Error, Please contact with manager!";
    }

    // 取消订单(ajax请求)
    @RequestMapping("/CancelTicket")
    public void CancelTicket(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, IOException {
        // 获取所有车票(未打印的车票、非退票的车票)
        response.setContentType("text/javascript");
        String TicketIDStr = request.getParameter("TicketID");
        Integer TicketID = Integer.parseInt(TicketIDStr);
        if(this.userService.CancelTicket(TicketID)){
            response.getWriter().println("True");
        }else{
            response.getWriter().println("False");
        }
    }

    // 改票服务 起始站 终点站不能变 只能变车次(重新预订)
    @RequestMapping("/ChangeTicket")
    @ResponseBody
    public String ChangeTicket(Integer TicketID, String NewTrain){
        // 获取新的列车
        Boolean success = this.userService.ChangeTicket(TicketID, NewTrain);
        if(success){
            return "改签成功";
        }
        return "改签失败";
    }

    @RequestMapping("printTicket")
    public void printTicket(HttpServletResponse response, HttpServletRequest request) throws IOException {
        Integer ticketID = Integer.parseInt(request.getParameter("ticketID"));
        boolean success = this.userService.printTicket(ticketID);
        if(success){
            Map<String, Object> ans = new HashMap<>();
            ans.put("flag", true);
            response.setContentType("text/javascript");
            response.getWriter().println(JSON.toJSONString(ans));
        }else{
            Map<String, Object> ans = new HashMap<>();
            ans.put("flag", false);
            response.setContentType("text/javascript");
            response.getWriter().println(JSON.toJSONString(ans));
        }
    }

    @RequestMapping("/cancelTicket")
    public void cancelTicket(HttpServletResponse response, HttpServletRequest request) throws IOException {
        Integer ticketID = Integer.parseInt(request.getParameter("ticketID"));
        boolean success = this.userService.cancelTicket(ticketID);
        if(success){
            Map<String, Object> ans = new HashMap<>();
            ans.put("flag", true);
            response.setContentType("text/javascript");
            response.getWriter().println(JSON.toJSONString(ans));
        }else{
            Map<String, Object> ans = new HashMap<>();
            ans.put("flag", false);
            response.setContentType("text/javascript");
            response.getWriter().println(JSON.toJSONString(ans));
        }
    }
}
