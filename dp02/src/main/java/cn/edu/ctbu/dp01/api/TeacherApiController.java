package cn.edu.ctbu.dp01.api;

import cn.edu.ctbu.dp01.entity.Teacher;
import cn.edu.ctbu.dp01.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherApiController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/list") // 获取教师列表
    public List<Teacher> findAll() {
        return teacherService.getAllTeacher();
    }

    @GetMapping("/{id}") // 根据ID查找教师
    public Teacher findById(@PathVariable int id) {
        return teacherService.findTeacherById(id);
    }


    @PostMapping("/add") // 添加教师
    public Teacher add( Teacher teacher) {
        return teacherService.add(teacher);
    }

    @PutMapping("/update") // 更新教师
    public Teacher update( Teacher teacher) {
        return teacherService.newupdate(teacher);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        teacherService.delete(id);
    }
}