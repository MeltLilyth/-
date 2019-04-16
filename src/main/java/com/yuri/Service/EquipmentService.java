package com.yuri.Service;

import com.yuri.Dao.EquipmentMapper;
import com.yuri.pojo.Equipment;
import com.yuri.pojo.PageBean;
import com.yuri.pojo.Tools;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;

@Component
public class EquipmentService {
    @Resource
    private EquipmentMapper equipmentMapper;
    @Resource
    private Tools tools;

    private PageBean<Equipment> equipPageBean = null;

    //加载第一页数据
    public PageBean<Equipment> loadEquipFirstPage()throws Exception{
        equipPageBean = new PageBean<Equipment>(10,equipmentMapper.findEquipAll().size(),1);
        equipPageBean.setDataList(equipmentMapper.findEquipByPage(equipPageBean.getStartIndex(),equipPageBean.getStartIndex()+equipPageBean.getPageSize()));
        equipPageBean.setKey("all");
        return equipPageBean;
    }

    //条件查询数据
    public PageBean<Equipment> queryEquipByCondition(String keyValue)throws Exception{
        if(tools.checkString(keyValue)){
            return this.queryEquipById(keyValue);
        }
        else{
            if(keyValue.length()>6 && keyValue.substring(2,5).equals("实验室")){
                return this.queryEquipBuylabName(keyValue);
            }
            else{
                return this.queryEquipByEquipName(keyValue);
            }
        }
    }

    //根据实验室名称查询对应设备
    public PageBean<Equipment> queryEquipBuylabName(String labName)throws Exception{
        equipPageBean = new PageBean<Equipment>(10,equipmentMapper.findEquipBylabName(labName).size(),1);
        equipPageBean.setDataList(equipmentMapper.pagingEquipBylabname(0,equipPageBean.getPageSize(),labName));
        equipPageBean.setKey(labName);
        return equipPageBean;
    }

    //根据id查询设备
    public Equipment findEquipmentById(String equipId)throws Exception{
        return equipmentMapper.findEquipById(equipId);
    }

    //根据设备id查询对应设备
    public PageBean<Equipment> queryEquipById(String equipId)throws Exception{
        List<Equipment> dataList = new ArrayList<Equipment>();
        Equipment equipment = equipmentMapper.findEquipById(equipId);

        equipPageBean = new PageBean<Equipment>(0,dataList.size(),1);
        equipPageBean.setKey("id");
        if(equipment == null){
            equipPageBean.setDataList(null);
        }
        else{
            dataList.add(equipment);
            equipPageBean.setDataList(dataList);
        }

        return equipPageBean;
    }

    //根据设备名称查询对应设备
    public PageBean<Equipment> queryEquipByEquipName(String equipName)throws Exception{
        List<Equipment> dataList = equipmentMapper.findEquipByName(equipName);
        equipPageBean = new PageBean<Equipment>(10,dataList.size(),1);
        List<Equipment> demoList = equipmentMapper.pagingEquipByName(0,10,equipName);
        equipPageBean.setDataList(demoList);
        equipPageBean.setKey(equipName);
        return equipPageBean;
    }

    //根据设备状态查询对应设备
    public PageBean<Equipment> queryEquipByStatus(String status)throws Exception{
        List<Equipment> dataList = equipmentMapper.findEquipByStatus(status);
        equipPageBean = new PageBean<Equipment>(10,dataList.size(),1);
        equipPageBean.setDataList(equipmentMapper.pagingEquipByStatus(0,10,status));
        equipPageBean.setKey(status);
        return equipPageBean;
    }

    //改变status
    public PageBean<Equipment> changeEquipStatus(String equipId,String status)throws Exception{
        Equipment equipment = equipmentMapper.findEquipById(equipId);
        equipment.setStatus(status);
        equipmentMapper.updateEquipment(equipment);
        return this.loadEquipFirstPage();
    }

    //数据跳页
    public PageBean<Equipment> pagingEquipData(int currentNum,String keyValue)throws Exception{
        if(keyValue.equals("all")){
            equipPageBean = this.loadEquipFirstPage();
        }
        else{
            if(keyValue.length()>6 && keyValue.substring(2,5).equals("实验室")){
                equipPageBean = this.queryEquipBuylabName(keyValue);
            }
            else if(keyValue.equals("可用")||keyValue.equals("维护中")||keyValue.equals("报修")){
                equipPageBean= this.queryEquipByStatus(keyValue);
            }
            else{
                equipPageBean = this.queryEquipByEquipName(keyValue);
            }
        }
        equipPageBean.setCurrentnNum(currentNum);
        equipPageBean.setStartIndex((currentNum-1)*equipPageBean.getPageSize());
        equipPageBean.setDataList(pagingEquipData(equipPageBean.getStartIndex(),equipPageBean.getPageSize(),keyValue));

        return equipPageBean;
    }

    private List<Equipment> pagingEquipData(int start,int num ,String keyValue)throws Exception{
        if(keyValue.equals("all")){
            return equipmentMapper.findEquipByPage(start,num);
        }
        else{
            if(keyValue.length()>6 && keyValue.substring(2,5).equals("实验室")){
                return equipmentMapper.pagingEquipBylabname(start,num,keyValue);
            }
            else if(keyValue.equals("可用")||keyValue.equals("维护中")||keyValue.equals("报修")){
                return equipmentMapper.pagingEquipByStatus(start,num,keyValue);
            }
            else{
                return equipmentMapper.pagingEquipByName(start,num,keyValue);
            }
        }
    }

    //更新设备的信息
    public PageBean<Equipment> updateEquipmentOperation(Equipment equipment)throws Exception{
        equipmentMapper.updateEquipment(equipment);
        return this.loadEquipFirstPage();
    }

    //添加新设备
    public PageBean<Equipment> addNewEquipment(String equipName,String labName)throws Exception{
        Equipment equipment = new Equipment(tools.GetId(4),equipName,labName);
        equipmentMapper.addNewEquipment(equipment);
        return this.loadEquipFirstPage();
    }

    //删除
    public PageBean<Equipment> deleteEquip(String equipId)throws Exception{
        equipmentMapper.deleteEquipment(equipId);
        return this.loadEquipFirstPage();
    }

    //批量删除
    public PageBean<Equipment> deleteGroupEquip(String[] equipIdlist)throws Exception{
        for(String equipId:equipIdlist){
            equipmentMapper.deleteEquipment(equipId);
        }
        return this.loadEquipFirstPage();
    }
}
