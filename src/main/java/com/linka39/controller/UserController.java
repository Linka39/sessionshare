package com.linka39.controller;

import com.linka39.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * @create 2020-01-12 下午 10:05
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @PostMapping(value = "/login")
    public ModelAndView login (HttpSession session, User user){
        ModelAndView mav=new ModelAndView();
        if("1111".equals(user.getPassword())){
            session.setAttribute("currentUser",user);
            mav.setViewName("main");
        }else{
            mav.addObject("errorInfo","用户名或者密码错误！");
            mav.setViewName("login");
        }
        return mav;
    }


}
