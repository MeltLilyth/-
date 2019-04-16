package com.yuri.Controller;

import com.yuri.Service.EquipmentService;
import com.yuri.Service.ProcessService;
import com.yuri.Service.UserService;
import com.yuri.pojo.Equipment;
import com.yuri.pojo.PageBean;
import com.yuri.pojo.Tools;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

@Controller
public class EquipController {
    @Resource
    private EquipmentService equipmentService;
    @Resource
    private ProcessService processService;
    @Resource
    private UserService userService;

    //更新某个设备的信息
    @RequestMapping("/updateEquip.do")
    public String updateEquipmentOperation(Model model,HttpServletRequest request)throws Exception{
        String equipId = request.getParameter("equipId");
        Equipment equipment = equipmentService.findEquipmentById(equipId);
        equipment.setEquipName(request.getParameter("equipName"));
        equipment.setLabName(request.getParameter("labName"));
        model.addAttribute("equipList",equipmentService.updateEquipmentOperation(equipment));
        return "result::equipResult";
    }


    //添加新的设备
    @RequestMapping("/addEquip.do")
    public String addNewEquipment(Model model, HttpServletRequest request)throws Exception{
        model.addAttribute("equipList",equipmentService.addNewEquipment(request.getParameter("equipName"),request.getParameter("labName")));
        return "result::equipResult";
    }

    //根据状态查询设备
    @RequestMapping("/tabEquip.do")
    public String SearchByStatus(Model model, HttpServletRequest request)throws Exception{
        String key = request.getParameter("keyValue");
        switch(key){
            case "tag1":
                model.addAttribute("equipList",equipmentService.loadEquipFirstPage());
                break;
            case "tag2":
                model.addAttribute("equipList",equipmentService.queryEquipByStatus("可用"));
                break;
            case "tag3":
                model.addAttribute("equipList",equipmentService.queryEquipByStatus("需要维护"));
                break;
            case "tag4":
                model.addAttribute("equipList",equipmentService.queryEquipByStatus("维护中"));
                break;
        }
        return "result::equipResult";
    }

    //数据跳页
    @RequestMapping("/skipEquipPage.do")
    public String skipPageNum(Model model,HttpServletRequest request)throws Exception{
        System.out.println(request.getParameter("keyValue"));
        model.addAttribute("equipList",equipmentService.pagingEquipData(Integer.parseInt(request.getParameter("currentPage")),request.getParameter("keyValue")));
        return "result::equipResult";
    }

    //指定设备为维修状态
    @RequestMapping("/repairEquip.do")
    public String changeEquipStatus(Model model,HttpServletRequest request)throws Exception{
        model.addAttribute("equipList", equipmentService.changeEquipStatus(request.getParameter("equipId"),"需要维护"));
        //添加新的维修任务
        model.addAttribute("serviceList",processService.addNewService(request.getParameter("equipId")));
        return "result::equipResult";
    }

    //根据条件查询设备
    @RequestMapping("/equipSearch.do")
    public String equipmentSearch(Model model,HttpServletRequest request)throws Exception{
        model.addAttribute("equipList",equipmentService.queryEquipByCondition(request.getParameter("relatedInformation")));
        return "result::equipResult";
    }

    //删除设备
    @RequestMapping("/deleteEquip.do")
    public String delEquipment(Model model,HttpServletRequest request)throws Exception{
        model.addAttribute("equipList",equipmentService.deleteEquip(request.getParameter("equipId")));
        return "result::equipResult";
    }

    //批量删除设备
    @RequestMapping("/deleteGroup.do")
    public String delEquipmentGroup(String[] arrays,Model model)throws Exception{
        model.addAttribute("equipList",equipmentService.deleteGroupEquip(arrays));
        return "result::equipResult";
    }

    @RequestMapping("/skipShowNum.do")
    public String skipShowNum(Model model,HttpServletRequest request)throws Exception{
        System.out.println(equipmentService.pagingEquipData(Integer.parseInt(request.getParameter("currentPage")),request.getParameter("keyValue")));
        model.addAttribute("showList",equipmentService.pagingEquipData(Integer.parseInt(request.getParameter("currentPage")),request.getParameter("keyValue")));
        model.addAttribute("user",userService.loadUserData(request.getParameter("userId")));
        return "result::showResult";
    }
}
