package cn.edu.ctbu.dp01.controller;

import cn.edu.ctbu.dp01.entity.Teacher;
import cn.edu.ctbu.dp01.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/list")
    public String getList(Model model) {

        List<Teacher> teachers = teacherService.getAllTeacher();
        model.addAttribute("teachers", teachers);
        return "/teacher/list";
    }

}
