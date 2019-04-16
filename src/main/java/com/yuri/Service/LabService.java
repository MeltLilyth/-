package com.yuri.Service;

import com.yuri.Dao.LabMapper;
import com.yuri.pojo.LabData;
import com.yuri.pojo.Tools;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;


@Component
public class LabService {
    @Resource
    private LabMapper labMapper;
    @Resource
    private Tools tools;

    public List<LabData> findlabDataAll()throws Exception{
        return labMapper.findLabAll();
    }

    public void addNewLab(LabData labData)throws Exception{
        labData.setLabId(tools.GetId(7));
        labMapper.addNewLab(labData);
    }

    public void delLabById(String labId)throws Exception{
        labMapper.delLabById(labId);
    }
}
