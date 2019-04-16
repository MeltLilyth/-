package com.yuri.Service;

import com.yuri.Dao.UserMapper;
import com.yuri.pojo.PageBean;
import com.yuri.pojo.Tools;
import com.yuri.pojo.User;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Component
public class UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private Tools tools;

    private PageBean<User> userPageBean = null;

    //返回用户所有的数据，根据role进行排列
    //页面中对用户的管理信息只能由admin进行操作和查阅
    public PageBean<User> loadUserFirstPage()throws Exception{
        userPageBean = new PageBean<User>(10,userMapper.findUserAll().size(),1);
        userPageBean.setDataList(userMapper.pagingUserData(userPageBean.getStartIndex(),userPageBean.getPageSize()));
        userPageBean.setKey("All");
        return userPageBean;
    }

    //根据role返回用户集
    public PageBean<User> queryUserByRole(String role)throws Exception{
        if(role.equals("All")){
            return this.loadUserFirstPage();
        }
        userPageBean = new PageBean<User>(10,userMapper.findUserByRole(role).size(),1);
        userPageBean.setKey(role);
        return userPageBean;
    }

    //根据用户邮箱搜寻用户
    public User queryUserByEmail(String email)throws Exception{
        return userMapper.findUserByEmail(email);
    }

    //根据姓名查询用户
    public PageBean<User> queryUserByName(String username)throws Exception{
        List<User> dataList = userMapper.findUserByName(username);
        userPageBean = new PageBean<User>(10,dataList.size(),1);
        userPageBean.setDataList(dataList);
        userPageBean.setKey(username);
        return userPageBean;
    }

    //条件查询
    public PageBean<User> queryUserByCondition(String keyValue)throws Exception{
        if(tools.checkString(keyValue)){
            userPageBean = new PageBean<User>(1,1,1);
            List<User> list = new ArrayList<User>();
            list.add(this.loadUserData(keyValue));
            userPageBean.setDataList(list);

            return userPageBean;
        }
        else
            return this.queryUserByName(keyValue);
    }

    //用户登录信息相关
    public User loadUserData(String loginCode) throws Exception {
        if(loginCode.length() == 11)
            return userMapper.findUserByPhone(loginCode);
        else
            return userMapper.findUserById(loginCode);
    }

    public User userLoadOperation(String code,String password)throws Exception{
        if(loadUserData(code)!=null){
            if(loadUserData(code).getPassword().equals(password)){
                return loadUserData(code);
            }
        }
        return null;
    }

    //通过admin的权限添加新的用户
    //添加新的用户的角色只能为维修工人或者设备管理员(不能为admin)
    //用户添加之后向新增用户发送email
    public PageBean<User> addNewUserByAdmin(User user)throws Exception{
        if(userMapper.findUserByEmail(user.getEmail())==null){
            user.setUserId(tools.GetId(6));
            userMapper.addNewUser(user);
        }
        return this.loadUserFirstPage();
    }

    //给用户邮箱发送邮件进行密码修改并激活
    //key值:0为找回密码，1为用户激活
    public void mailOperation(String email,int key)throws Exception{
        User user = userMapper.findUserByEmail(email);

        Properties prop = new Properties();
        prop.setProperty("mail.host","smtp.163.com");
        prop.setProperty("mail.transport.protocol","smtp");
        prop.setProperty("mail.smtp.auth","true");
        //使用javamail发送邮件的五个步骤
        //1.创建session
        Session session = Session.getInstance(prop);
        session.setDebug(true);
        //2.通过session得到transport对象
        Transport ts = session.getTransport();
        //3.设置相关信息，发件人的名称和邮箱授权码
        ts.connect("smtp.163.com","jorie1996","wrj1029");
        //4.创建邮件
        Message message = createMessageMail(session,user,key);
        //5.发送邮件
        ts.sendMessage(message,message.getAllRecipients());
        ts.close();
    }

    private MimeMessage createMessageMail(Session session, User user,int key)throws Exception{
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress("jorie1996@163.com"));
        //指明邮件的收件人
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(user.getEmail()));
        //编写邮件标题和内容
        if(key==0){
            message.setSubject("账号与密码找回");//邮件的标题
        }
        else{
            message.setSubject("新用户注册");
        }
        //正文
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(getContent(user,key),"text/html;charset=utf-8");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);

        return message;
    }

    private String getContent(User user,int key)throws Exception{
        StringBuffer stringBuffer = new StringBuffer();
        if(key == 0){
            stringBuffer.append("<!DOCTYPE>"+"<p>您好，您的用户账号为："+user.getUserId()+"</p><br>" +
                    "<p>如果您想要重置密码，请点击连接：</p><br>" +
                    "<a href='http://localhost:8080/resetPass.com?userId="+user.getUserId()+"'>重置密码</a><br>"+"<p>本邮件由系统发出，无需回复。感谢您的使用，祝您生活愉快。</p>");
        }
        else{
            stringBuffer.append("<!DOCTYPE>"+"<p>您好，管理员已经为您在实验设备管理系统中注册新用户</p><br>+" +
                    "<p>您的用户账号为"+user.getUserId()+"</p><br>"+
                    "<p>本邮件由系统发出，无需回复；非常感谢您使用该系统，祝您生活愉快</p>");
        }
        return stringBuffer.toString();
    }

    //更新用户自身信息--姓名，密码，手机号，邮箱地址
    public User updateUser(User user)throws Exception{
        userMapper.updateUser(user);
        return userMapper.findUserById(user.getUserId());
    }

    //变更用户角色
    public PageBean<User> updateUserByadmin(User user)throws Exception{
        userMapper.updateUser(user);
        return this.loadUserFirstPage();
    }

    //用户数据分页
    public PageBean<User> pagingUserData(int currentNum,String keyValue)throws Exception{
        if(keyValue.equals("All")){
            userPageBean = this.loadUserFirstPage();
        }
        else{
            if(keyValue.equals("管理员")||keyValue.equals("维修人员")||keyValue.equals("设备管理员")){
                userPageBean = this.queryUserByRole(keyValue);
            }
            else{
                userPageBean = this.queryUserByName(keyValue);
            }
        }

        userPageBean.setCurrentnNum(currentNum);
        userPageBean.setStartIndex((currentNum-1)*userPageBean.getPageSize());
        userPageBean.setDataList(userMapper.pagingUserData(userPageBean.getStartIndex(),userPageBean.getPageSize()));

        return userPageBean;
    }

    //删除用户--管理员才可以进行此操作
    public PageBean<User> delUser(String userId)throws Exception{
        userMapper.deleteUser(userId);
        return this.loadUserFirstPage();
    }

    //批量删除
    public PageBean<User> delGroup(String[] idList)throws Exception{
        for(String userId:idList){
            userMapper.deleteUser(userId);
        }
        return this.loadUserFirstPage();
    }
}
