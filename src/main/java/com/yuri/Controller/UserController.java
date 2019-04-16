package com.yuri.Controller;

import com.yuri.Service.ProcessService;
import com.yuri.Service.UserService;
import com.yuri.pojo.PageBean;
import com.yuri.pojo.Tools;
import com.yuri.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private ProcessService processService;
    @Resource
    private Tools tools;

    //通过邮箱重置密码
    @RequestMapping(value = "/retrieveMailbox.do")
    public String retrieveMailbox(String email, RedirectAttributes redirectAttributes) throws Exception {
        String returnUrl = "redirect:/forgotEmail.com";
        if(userService.queryUserByEmail(email)!=null){
            redirectAttributes.addFlashAttribute("retireveMessage","邮件已经发送至邮箱，如果需要重置密码请尽快登录邮箱并完成剩余操作");
            userService.mailOperation(email,0);
            returnUrl = "redirect:/index.com";
        }
        else{
            redirectAttributes.addFlashAttribute("retireveMessage","找不到您的预留邮箱");
        }
        return returnUrl;
    }

    //用户账户的登录
    //用户信息key:user
    //错误信息key:errorMessage
    @RequestMapping("/userLogin.do")
    public String loadUser(String loginCode,String password,Model model)throws Exception{
        String returnUrl = "";
        if(userService.userLoadOperation(loginCode,password)!=null){
            model.addAttribute("user",userService.userLoadOperation(loginCode,password));
            model.addAttribute("personalTask",processService.loadPersonalTask(loginCode));
            returnUrl = "forward:result.com";
        }
        else{
            model.addAttribute("errorMessage","用户名或密码错误");
            returnUrl = "redirect:index.com";
        }
        return returnUrl;
    }

    //更新用户信息(自身)--管理员不能对用户信息进行更改(除了删除)
    //更改的信息只有--用户名，密码，邮箱地址，手机号
    @RequestMapping("/updatePersonal.do")
    public String UpdateUserBySelf(HttpServletRequest request, Model model)throws Exception{
        String userId = request.getParameter("userId");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phone");

        User user = userService.loadUserData(userId);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);

        model.addAttribute("user",userService.updateUser(user));
        return "result::personalInformation";
    }

    //添加新用户
    @RequestMapping("/addNewUser.do")
    public String addNewUserOperation(Model model,HttpServletRequest request)throws Exception{
        if(userService.queryUserByEmail(request.getParameter("email"))!=null){
            model.addAttribute("addError","用户邮箱已存在");
            model.addAttribute("userList",userService.loadUserFirstPage());
            return "result::userResult";
        }
        else{
            model.addAttribute("userList",userService.addNewUserByAdmin(new User(request.getParameter("username"),request.getParameter("userSex"),request.getParameter("phone"),request.getParameter("email"),request.getParameter("role"))));
            User user = userService.queryUserByEmail(request.getParameter("email"));
            return "result::userResult";
        }
    }

    //更新用户信息(通过admin操作)
    @RequestMapping("/changeUserRole.do")
    public String changeUserRole(Model model,HttpServletRequest request)throws Exception{
        model.addAttribute("addError",null);

        User user = userService.loadUserData(request.getParameter("userId"));
        user.setRole(request.getParameter("role"));
        System.out.println(user);
        model.addAttribute("userList",userService.updateUserByadmin(user));
        return "result::userResult";
    }

    //信息跳页
    @RequestMapping("/nextUserPage.do")
    public String skipPage(Model model,HttpServletRequest request)throws Exception{
        model.addAttribute("userList",userService.pagingUserData(Integer.parseInt(request.getParameter("currentPage")),request.getParameter("keyValue")));
        return "result::userResult";
    }

    //根据用户角色返回数据
    @RequestMapping("/queryResult.do")
    public String queryResult(Model model,HttpServletRequest request)throws Exception{
        model.addAttribute("userList",userService.queryUserByRole(request.getParameter("role")));
        return "result::userResult";
    }

    //单点信息查询(通过工号，姓名查询)--输入信息为空时返回所有数据
    @RequestMapping("/conditionQuery.do")
    public String ConditionQuery(Model model,HttpServletRequest request)throws Exception{
        model.addAttribute("userList",userService.queryUserByCondition(request.getParameter("keyValue")));
        return "result::userResult";
    }

    //删除用户
    @RequestMapping("/delUser.do")
    public String deleteUser(Model model,HttpServletRequest request)throws Exception{
        model.addAttribute("userList",userService.delUser(request.getParameter("userId")));
        return "result::userResult";
    }

    //批量删除用户
    @RequestMapping("deleteUserGroup.do")
    public String deleteUserGroup(Model model,String[] arrays)throws Exception{
        model.addAttribute("userList",userService.delGroup(arrays));
        return "result::userResult";
    }
}
