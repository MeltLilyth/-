package com.yuri.Dao;

import com.yuri.pojo.LabData;
import java.util.List;

public interface LabMapper {
    public List<LabData> findLabAll()throws Exception;
    public void addNewLab(LabData labData)throws Exception;
    public void delLabById(String labId)throws Exception;
}
