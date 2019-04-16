package com.yuri.Controller;

import com.yuri.Service.EquipmentService;
import com.yuri.Service.ProcessService;
import com.yuri.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ServiceController {
    @Resource
    private EquipmentService equipmentService;
    @Resource
    private ProcessService processService;
    @Resource
    private UserService userService;

    @RequestMapping("/nextServicePage.do")
    public String skipServiceNumPage(Model model, HttpServletRequest request)throws Exception{
        model.addAttribute("serviceList",processService.pagingServiceData(Integer.parseInt(request.getParameter("currentPage")),request.getParameter("keyValue")));
        return "result::serviceResult";
    }

    @RequestMapping("/loadServiceData.do")
    public String loadServiceDataByState(Model model,HttpServletRequest request)throws Exception{
        model.addAttribute("serviceList",processService.queryServiceByState(request.getParameter("keyValue")));
        return "result::serviceResult";
    }

    @RequestMapping("/confirmService.do")
    public String confirmResult(Model model,HttpServletRequest request)throws Exception{
        model.addAttribute("confirmList",processService.confirmProcess(request.getParameter("serviceId")));
        model.addAttribute("serviceList",processService.loadFirstServicePage());
        model.addAttribute("equipList",equipmentService.loadEquipFirstPage());

        return "result::confirmResult";
    }

    @RequestMapping("/acceptTask.do")
    public String acceptTask(HttpServletRequest request,Model model)throws Exception{
        model.addAttribute("serviceList",processService.updateService(request.getParameter("equipId"),request.getParameter("userId")));
        model.addAttribute("showList",equipmentService.loadEquipFirstPage());
        model.addAttribute("personalTask",processService.loadPersonalTask(request.getParameter("userId")));

        return "result::showResult";
    }

    @RequestMapping("/delayTime.do")
    public String delayTime(Model model,HttpServletRequest request)throws Exception{
        model.addAttribute("personalTask",processService.delayFinishDate(request.getParameter("userId"),request.getParameter("serviceId")));
        model.addAttribute("user",userService.loadUserData(request.getParameter("userId")));
        return "result::personalTaskBody";
    }
}

