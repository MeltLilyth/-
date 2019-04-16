package com.yuri.pojo;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.UUID;

@Component
public class Tools {
    //检查输入的字符串是否全为数字
    public boolean checkString(String test)throws Exception{
        try{
            System.out.println(Integer.parseInt(test));
            return true;
        }
        catch(NumberFormatException e){
            e.fillInStackTrace();
            return false;
        }
    }

    //4位设备,5位billid,6位用户,7位实验室
    public String GetId(int length)throws Exception{
        String itemId = "";
        int num = 0;
        String demo = UUID.randomUUID().toString();
        for(int i = 0;i<demo.length();i++){
            if(demo.charAt(i)>='0'&&demo.charAt(i)<='9'){
                itemId += demo.charAt(i);
                num++;
            }
            if(num == length)
                break;
        }

        return itemId;
    }

    //返回维修进度
    public String GetPercent(Date start, Date end){
        Date now = new Date();
        if(now.getTime()<=start.getTime()){
            return String.valueOf(0);
        }
        if(now.getTime()>=end.getTime()){
            return String.valueOf(100);
        }
        long num1 = start.getTime();
        long num2 = end.getTime();
        long num3 = now.getTime();

        DecimalFormat df = new DecimalFormat();
        String key_1 = df.format((float) ((num3-num1)%(num2-num1))/(24*60*60*1000));
        float key_2 = Float.parseFloat(key_1)*100;
        int key_3 = (int)key_2;
        return String.valueOf(key_3)+"%";
    }

    //延时七天
    public Date getFinishDate(Date date){
        long time = date.getTime();
        time += 2*24*60*60*1000;

        return new Date(time);
    }
}
