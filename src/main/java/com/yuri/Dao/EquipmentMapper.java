package com.yuri.Dao;

import com.yuri.pojo.Equipment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EquipmentMapper {
    //获得所有的数据
    public List<Equipment> findEquipAll()throws Exception;
    //批量查询
    public List<Equipment> findEquipByPage(@Param("start")int start,@Param("end")int end)throws Exception;
    //根据id查询
    public Equipment findEquipById(String equipId)throws Exception;
    //根据设备名称模糊查询
    public List<Equipment> findEquipByName(String equipName)throws Exception;
    //名称查询分页
    public List<Equipment> pagingEquipByName(@Param("start") int start,@Param("end") int end,@Param("keyValue") String keyValue);
    //列出所在实验室的设备
    public List<Equipment> findEquipBylabName(String labName)throws Exception;
    //实验室查询分页
    public List<Equipment> pagingEquipBylabname(@Param("start") int start,@Param("end") int end,@Param("keyValue") String labName)throws Exception;
    //根据设备状态查询
    public List<Equipment> findEquipByStatus(String status)throws Exception;
    //状态查询分页
    public List<Equipment> pagingEquipByStatus(@Param("start") int start,@Param("end") int end,@Param("keyValue") String status)throws Exception;
    //更新设备信息
    public void updateEquipment(Equipment equipment)throws Exception;
    //增加新的设备
    public void addNewEquipment(Equipment equipment)throws Exception;
    //删除设备
    public void deleteEquipment(String equipId)throws Exception;
}
