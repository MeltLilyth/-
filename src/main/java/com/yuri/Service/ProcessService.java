package com.yuri.Service;

import com.yuri.Dao.EquipmentMapper;
import com.yuri.Dao.ServiceMapper;
import com.yuri.Dao.UserMapper;
import com.yuri.pojo.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class ProcessService {
    @Resource
    private ServiceMapper serviceMapper;
    @Resource
    private Tools tools;
    @Resource
    private EquipmentMapper equipmentMapper;
    @Resource
    private UserMapper userMapper;

    private PageBean<ServiceMessage> servicePageBean = null;

    //加载第一页数据
    public PageBean<ServiceMessage> loadFirstServicePage() throws Exception {
        servicePageBean = new PageBean<ServiceMessage>(10,serviceMapper.findServiceAll().size(),1);
        servicePageBean.setDataList(serviceMapper.pagingServiceAll(servicePageBean.getStartIndex(),servicePageBean.getPageSize()));
        servicePageBean.setKey("All");
        return servicePageBean;
    }

    //加载个人的维修任务
    public AcceptTask loadPersonalTask(String loginCode)throws Exception{
        if(loginCode.length() == 6 || loginCode.length() ==4){
            return new AcceptTask(loginCode,serviceMapper.findServiceByUserId(loginCode,"已受理"),serviceMapper.findServiceByUserId(loginCode,"已受理")==null? 0:1);
        }
        else{
            return new AcceptTask(userMapper.findUserByPhone(loginCode).getUserId(),serviceMapper.findServiceByUserId(loginCode,"已受理"),serviceMapper.findServiceByUserId(userMapper.findUserByPhone(loginCode).getUserId(),"已受理")==null? 0:1);
        }
    }

    //延长维修时间
    public AcceptTask delayFinishDate(String userId,String serviceId)throws Exception{
        ServiceMessage serviceMessage = serviceMapper.findServiceByServiceId(serviceId);
        serviceMessage.setFinishDate(tools.getFinishDate(serviceMessage.getFinishDate()));
        serviceMapper.updateServiceOperation(serviceMessage);

        return new AcceptTask(userId,serviceMapper.findServiceByUserId(userId,"已受理"),serviceMapper.findServiceByUserId(userId,"已受理")==null? 0:1);
    }

    //根据受理状态查询数据
    public PageBean<ServiceMessage> queryServiceByState(String state)throws Exception{
        if(state.equals("All")){
            return this.loadFirstServicePage();
        }
        servicePageBean = new PageBean<ServiceMessage>(10,serviceMapper.findServiceByStatus(state).size(),1);
        servicePageBean.setDataList(serviceMapper.pagingServiceByStatus(state,servicePageBean.getStartIndex(),servicePageBean.getPageSize()));
        servicePageBean.setKey(state);
        return servicePageBean;
    }

    //新增新的维修任务
    public PageBean<ServiceMessage> addNewService(String equipId)throws Exception{
        Equipment equipment = equipmentMapper.findEquipById(equipId);
        serviceMapper.addNewService(new ServiceMessage(tools.GetId(5),equipId,equipment.getEquipName()));
        return this.loadFirstServicePage();
    }

    //更新任务的相关信息
    public PageBean<ServiceMessage> updateService(String equipId,String userId)throws Exception{
        ServiceMessage serviceMessage = serviceMapper.findServiceByEquipIdAndStatus(equipId,"未受理");
        serviceMessage.setState("已受理");
        serviceMessage.setServiceDate(new Date());
        serviceMessage.setFinishDate(tools.getFinishDate(serviceMessage.getServiceDate()));
        serviceMessage.setUserId(userId);
        serviceMessage.setUsername(userMapper.findUserById(userId).getUsername());
        serviceMapper.updateServiceOperation(serviceMessage);

        Equipment equipment = equipmentMapper.findEquipById(equipId);
        equipment.setStatus("维护中");
        equipmentMapper.updateEquipment(equipment);

        return this.loadFirstServicePage();
    }

    //每一次登录页面时，都会后台实时更新维修进度
    public PageBean<ServiceMessage> updateProcessDaily()throws Exception{
        for(ServiceMessage serviceMessage:serviceMapper.findServiceAll()){
            if(serviceMessage.getState().equals("已受理")){
                String percent = tools.GetPercent(serviceMessage.getServiceDate(),serviceMessage.getFinishDate());
                serviceMessage.setProcess(percent);
                if(percent.equals("100%")){
                    serviceMessage.setState("待确认");
                }

                serviceMapper.updateServiceOperation(serviceMessage);
            }
        }

        return this.loadFirstServicePage();
    }

    //分页管理
    public PageBean<ServiceMessage> pagingServiceData(int currentNum,String keyValue)throws Exception{
        if(keyValue.equals("All")){
            servicePageBean = this.loadFirstServicePage();
        }
        else{
            servicePageBean = this.queryServiceByState(keyValue);
        }

        servicePageBean.setCurrentnNum(currentNum);
        servicePageBean.setStartIndex((currentNum-1)*servicePageBean.getPageSize());
        servicePageBean.setDataList(this.getResultList(servicePageBean.getStartIndex(),servicePageBean.getPageSize(),keyValue));

        return servicePageBean;
    }

    private List<ServiceMessage> getResultList(int start,int end,String keyValue)throws Exception{
        if(keyValue.equals("All")){
            return serviceMapper.pagingServiceAll(start,end);
        }
        else{
            return serviceMapper.pagingServiceByStatus(keyValue,start,end);
        }
    }

    //确认维修进程结束
    public PageBean<ServiceMessage> confirmProcess(String serviceId)throws Exception{
        ServiceMessage serviceMessage = serviceMapper.findServiceByServiceId(serviceId);
        serviceMessage.setState("维修结束");
        serviceMapper.updateServiceOperation(serviceMessage);

        Equipment equipment = equipmentMapper.findEquipById(serviceMessage.getEquipId());
        equipment.setStatus("可用");
        equipmentMapper.updateEquipment(equipment);

        return this.queryServiceByState("待确认");
    }
}
