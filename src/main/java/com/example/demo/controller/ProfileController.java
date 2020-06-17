package com.example.demo.controller;


import com.example.demo.dto.PaginationDTO;
import com.example.demo.model.User;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    private String profile(HttpServletRequest request,
                           @PathVariable(name="action")String action,
                           Model model,
                           @RequestParam(name="page",defaultValue = "1")Integer page,
                           @RequestParam(name="size",defaultValue = "5")Integer size){    //获取浏览器的参数action
        User user=(User) request.getSession().getAttribute("user");

        if(user==null){
            return "redirect:/";
        }
        if ("questions".equals(action)) {  //当action=questions，即不为空指针时
            model.addAttribute("section", "questions");  //把questions放入section返回给前端
            model.addAttribute("sectionName", "我的提问");
        } else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }
        PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
        model.addAttribute("pagination",paginationDTO);
        return "profile";
    }
}
