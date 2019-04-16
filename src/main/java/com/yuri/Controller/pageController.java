package com.yuri.Controller;

import com.yuri.Service.EquipmentService;
import com.yuri.Service.LabService;
import com.yuri.Service.ProcessService;
import com.yuri.Service.UserService;
import com.yuri.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class pageController {
    @Resource
    private EquipmentService equipmentService;
    @Resource
    private UserService userService;
    @Resource
    private LabService labService;
    @Resource
    private ProcessService processService;

    @RequestMapping("/index.com")
    public String loadIndex(){
        return "index";
    }

    @RequestMapping("/forgotPass.com")
    public String loadForgetPass(){
        return "forgotPassword";
    }

    @RequestMapping("/result.com")
    public String loadResult(Model model) throws Exception {
        //正式版加载数据
        model.addAttribute("showList",equipmentService.loadEquipFirstPage());
        model.addAttribute("equipList",equipmentService.loadEquipFirstPage());
        model.addAttribute("labList",labService.findlabDataAll());
        model.addAttribute("userList",userService.loadUserFirstPage());
        model.addAttribute("serviceList",processService.updateProcessDaily());
        model.addAttribute("confirmList",processService.queryServiceByState("待确认"));
        model.addAttribute("serviceEquip",processService.queryServiceByState("未受理"));

        return "result";
    }

    @RequestMapping("/resetPass.com")
    public String resetPass(Model model, HttpServletRequest request)throws Exception{
        String userId = request.getParameter("userId");
        model.addAttribute("userId",userId);
        return "resetPass";
    }

    @RequestMapping("loginout.do")
    public String LoginOut(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session!=null){
            session.invalidate();
        }

        return "redirect:index.com";
    }
}
