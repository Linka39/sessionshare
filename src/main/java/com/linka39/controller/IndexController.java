package com.linka39.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @create 2020-01-13 下午 2:59
 */
@Controller
public class IndexController {
    /**
     * 网页根目录请求
     * @return
     */
    @RequestMapping("/")
    public ModelAndView root(){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    /**
     * 获取当前登录用户信息
     * @return
     */
    @RequestMapping("/getInfo")
    public ModelAndView getInfo(){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("info");
        return mav;
    }

}
