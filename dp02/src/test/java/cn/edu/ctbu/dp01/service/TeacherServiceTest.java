package cn.edu.ctbu.dp01.service;

import cn.edu.ctbu.dp01.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TeacherServiceTest {
    @Autowired
    TeacherService teacherService;

    @Test
    void getAllTeacher(){
        List<Teacher> teachers = teacherService.getAllTeacher();

        assertNotNull(teachers);
    }

    @Test
    void findById() {
        Teacher teacher = teacherService.findTeacherById(1);

        assertNotNull(teacher);
    }

    @Test
    void insert() {
        Teacher teacher = new Teacher();
        teacher.setName("赵六");
        teacher.setSex(1);

        teacherService.insert(teacher);

        assertNotNull(teacher.getId());
    }

    @Test
    void update() {
        Teacher teacher = teacherService.findTeacherById(4);
        teacher.setAge(21);

        teacherService.update(teacher);
        assertNotNull(teacher);
    }

    @Test
    void delete() {
        teacherService.delete(7);
        Teacher teacher = teacherService.findTeacherById(7);

        assertNotNull(teacher);
    }

    @Test
    void findOldestTeachers(){
        List<Teacher> result = teacherService.findLatestTeachers();

        assertNotNull(result);
    }
}
