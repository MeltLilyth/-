package com.yuri.Dao;

import com.yuri.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    //查询所有人员(不根据角色)
    public List<User> findUserAll()throws Exception;
    //数据分页
    public List<User> pagingUserData(@Param("start") int start,@Param("end") int end)throws Exception;
    //根据id查询
    public User findUserById(String userId)throws Exception;
    //根据姓名查询
    public List<User> findUserByName(String username)throws Exception;
    //更新人员的信息
    public void updateUser(User user)throws Exception;
    //新增人员
    public void addNewUser(User user)throws Exception;
    //删除人员
    public void deleteUser(String userId)throws Exception;
    //根据用户的手机号码查询用户
    public User findUserByPhone(@Param("phoneNumber") String phoneNumber)throws Exception;
    //根据用户的邮箱地址查询用户
    public User findUserByEmail(@Param("email") String email)throws Exception;
    //根据role返回用户集
    public List<User> findUserByRole(String role)throws Exception;
}
