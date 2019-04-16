import com.yuri.Dao.EquipmentMapper;
import com.yuri.Dao.LabMapper;
import com.yuri.Dao.UserMapper;
import com.yuri.Service.EquipmentService;
import com.yuri.Service.ProcessService;
import com.yuri.pojo.Equipment;
import com.yuri.pojo.LabData;
import com.yuri.pojo.Tools;
import com.yuri.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.tools.Tool;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestCase {
    private String path = "applicationContext-service.xml";

    @Test
    public void Run1()throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(path);
        UserMapper userMapper = applicationContext.getBean("userMapper", UserMapper.class);
        userMapper.addNewUser(new User("1001","冷笑の游里","123456","男","12346653551","12334131",new Date(),"管理员"));
    }

    @Test
    public void Run2()throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(path);
        EquipmentMapper equipmentMapper = applicationContext.getBean("equipmentMapper",EquipmentMapper.class);
        Tools tools = applicationContext.getBean("tools",Tools.class);
        equipmentMapper.addNewEquipment(new Equipment(tools.GetId(4),"测试设备1","物理实验室2",new Date(),new Date()));
    }

    @Test
    public void Run3()throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(path);
        EquipmentMapper equipmentMapper = applicationContext.getBean("equipmentMapper",EquipmentMapper.class);
        Tools tools = applicationContext.getBean("tools",Tools.class);
        for(int i = 1;i<=20 ;i++){
            equipmentMapper.addNewEquipment(new Equipment(tools.GetId(4),"测试设备"+(i+1),"物理实验室2",new Date(),new Date()));
        }
    }
    @Test
    public void Run4()throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(path);
        LabMapper labMapper = applicationContext.getBean("labMapper",LabMapper.class);
        Tools tools = applicationContext.getBean("tools",Tools.class);
        for(int i =0;i<5;i++){
            labMapper.addNewLab(new LabData(tools.GetId(7),"物理实验室"+(i+1)));
        }
    }

    @Test
    public void Run5()throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(path);
        LabMapper labMapper = applicationContext.getBean("labMapper",LabMapper.class);
        List<LabData> list = labMapper.findLabAll();
        for(LabData labData:list){
            System.out.println(labData);
        }
    }

    @Test
    public void Run6()throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(path);
        EquipmentMapper equipmentMapper = applicationContext.getBean("equipmentMapper",EquipmentMapper.class);
        Tools tools = applicationContext.getBean("tools",Tools.class);

        equipmentMapper.addNewEquipment( new Equipment(tools.GetId(4),"测试设备29","物理实验室1"));
    }

    @Test
    public void Run8()throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(path);
        EquipmentMapper equipmentMapper = applicationContext.getBean("equipmentMapper",EquipmentMapper.class);

        System.out.println(equipmentMapper.findEquipByPage(10,20).size());
    }

    @Test
    public void Run7()throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(path);
        EquipmentService equipmentService = applicationContext.getBean("equipmentService",EquipmentService.class);
        System.out.println(equipmentService.pagingEquipData(2,"all"));

    }

    @Test
    public void Run9()throws Exception{
        String name = "物理实验室1";
        System.out.println(name.substring(2,5));
    }
    @Test
    public void Run10()throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(path);
        UserMapper userMapper = applicationContext.getBean("userMapper",UserMapper.class);
        Tools tools = applicationContext.getBean("tools",Tools.class);

        userMapper.addNewUser(new User(tools.GetId(6),"An","男","1221212","1221221","设备管理员"));
    }

    @Test
    public void Run11()throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(path);
        UserMapper userMapper = applicationContext.getBean("userMapper",UserMapper.class);
        Tools tools = applicationContext.getBean("tools",Tools.class);

        for(int i =0 ;i<20 ;i++){
            userMapper.addNewUser(new User(tools.GetId(6),"An"+i,"男","1221212",tools.GetId(10)+"@demo.com","维修人员"));
        }
    }

    @Test
    public void  run12()throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(path);
        UserMapper userMapper = applicationContext.getBean("userMapper",UserMapper.class);
        List<User> list = userMapper.findUserAll();
        for(User user:list){
            System.out.println(user);
        }
    }

    @Test
    public void run13()throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(path);
        UserMapper userMapper = applicationContext.getBean("userMapper",UserMapper.class);

        List<User> list = userMapper.pagingUserData(0,10);
        for(User user:list){
            System.out.println(user);
        }
    }

    @Test
    public void Run14()throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(path);
        UserMapper userMapper = applicationContext.getBean("userMapper",UserMapper.class);
        for(User user:userMapper.findUserByRole("维修人员")){
            System.out.println(user);
        }
    }

    @Test
    public void Run15()throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(path);
        UserMapper userMapper = applicationContext.getBean("userMapper",UserMapper.class);

        userMapper.deleteUser("009421");
    }

    @Test
    public void Run16()throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(path);
        ProcessService processService = applicationContext.getBean("processService",ProcessService.class);
        System.out.println(processService.updateProcessDaily());
    }

    @Test
    public void Run17()throws Exception{
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Tools().getFinishDate(new Date())));
    }

    @Test
    public void Run18()throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(path);
        ProcessService processService = applicationContext.getBean("processService",ProcessService.class);
        System.out.println(processService.loadPersonalTask("1001"));
    }
}
