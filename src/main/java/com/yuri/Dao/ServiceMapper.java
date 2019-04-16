package com.yuri.Dao;

import com.yuri.pojo.ServiceMessage;
import com.yuri.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ServiceMapper {
    public List<ServiceMessage> findServiceAll()throws Exception;
    public List<ServiceMessage> findServiceByStatus(String status)throws Exception;
    public List<ServiceMessage> pagingServiceAll(@Param("start") int start,@Param("end") int end)throws Exception;
    public List<ServiceMessage> pagingServiceByStatus(@Param("state")String state,@Param("start")int start,@Param("end")int end)throws Exception;
    public ServiceMessage findServiceByServiceId(String serviceId)throws Exception;
    public List<ServiceMessage> findServiceByEquipId(String equipId)throws Exception;
    public ServiceMessage findServiceByEquipIdAndStatus(@Param("equipId") String equipId,@Param("state") String state);
    public ServiceMessage findServiceByUserId(@Param("userId") String userId,@Param("state")String state)throws Exception;
    public void updateServiceOperation(ServiceMessage serviceMessage)throws Exception;
    public void updateServiceProcess(@Param("serviceId") String serviceId,@Param("process") String process)throws Exception;
    public void updateServiceStatus(@Param("status")String serviceId,@Param("serviceId") String status)throws Exception;
    public void updateServiceMember(@Param("serviceId")String serviceId,@Param("userId")String userId)throws Exception;
    public void addNewService(ServiceMessage serviceMessage)throws Exception;
}
