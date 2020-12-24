package zhm.Train.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zhm.Train.Service.ManagerService;
import zhm.Train.Service.UserService;
import zhm.Train.Utils.Type.RegisterType;
import zhm.Train.domain.Manager;
import zhm.Train.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class mainController {

    @Autowired
    private UserService userService;

    @Autowired
    private ManagerService managerService;
    // 用户登录界面
    @RequestMapping("/UserLogin")
    public String UserLogin(){
        return "UserLogin.html";
    }

    // 用户登录验证
    @RequestMapping("/UserCheck")
    public String UserCheck(HttpServletRequest req, User user, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user_session = (User) session.getAttribute("USER_SESSION");
        if(user_session != null){
            return "UserPage.html";
        }
        if(user.getPasswd().equals(null) || user.getUsername().equals(null)){
            return "ForbidAccess.html";
        }
        if(this.userService.Login(user)){
            user = this.userService.getUserByAccount(user);
            session.setAttribute("USER_SESSION", user);
            return "UserPage.html";
        }
        return "LoginFail.html";
    }

    // 管理员登录页面
    @RequestMapping("/ManagerLogin")
    public String ManagerLogin(){
        return "ManagerLogin.html";
    }

    // 管理员登录验证
    @RequestMapping("/Manager/Main")
    public String ManagerCheck(HttpServletRequest req, Manager manager){
        HttpSession session = req.getSession();
        Manager manager_session = (Manager) session.getAttribute("Manager_SESSION");
        if(manager_session != null){
            return "ManagerPage.html";
        }
        if(manager.getPasswd().equals(null) || manager.getUsername().equals(null)){
            return "ForbidAccess.html";
        }
        if(this.managerService.Login(manager)){   // 登录成功
            manager = this.managerService.getManagerByAccount(manager);
            session.setAttribute("Manager_SESSION", manager);
            return "ManagerPage.html";
        }
        return "LoginFail.html";
    }

    // 注册页面
    @RequestMapping("/Register")
    public String Register(User user){
        return "Register.html";
    }

    @RequestMapping(value = "/RegisterCheck", produces = {MediaType.TEXT_HTML_VALUE})
    @ResponseBody
    public String RegisterCheck(User user){
        RegisterType type = this.userService.Register(user);
        StringBuffer sbHtml = new StringBuffer();
        sbHtml.append("<!doctype html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head></body>");
        // 验证数据格式
        switch(type){
            case RepeatUsername:
                sbHtml.append("<p>Username repeat, please try again.</p>");
                break;
            case PasswordTooShort:
                sbHtml.append("<p>Password length must be more than 8.</p>");
                break;
            case AccountEmpty:
                sbHtml.append("<p>Username or password is empty, please input again.</p>");
                break;
            case Success:
                sbHtml.append("<p>Success!</p>");
        }
        sbHtml.append("<a href=\"/zhm\">Back to home</a>");
        sbHtml.append("</body></html>");
        return sbHtml.toString();
    }

}
