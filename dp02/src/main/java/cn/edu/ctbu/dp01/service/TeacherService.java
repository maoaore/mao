package cn.edu.ctbu.dp01.service;

import cn.edu.ctbu.dp01.dao.TeacherRepository;
import cn.edu.ctbu.dp01.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    @Autowired
    public TeacherRepository teacherRepository;

    public List<Teacher> getAllTeacher(){
        return teacherRepository.findAll();
    }

    /**
     * 按id查询
     * @param id，主键，整数
     * @return
     */
    public Teacher findTeacherById(Integer id){
        return teacherRepository.findById(id).orElse(null);

    }

    /**
     * 增
     * @param teacher
     */
    public void insert(Teacher teacher){
        teacherRepository.save(teacher);
    }

    /**
     * 改
     * @param teacher
     */
    public void update(Teacher teacher){
        teacherRepository.save(teacher);
    }

    /**
     * 删
     * @param id
     */
    public void delete(Integer id){
        teacherRepository.deleteById(id);
    }

    /**
     * 工龄最大
     * @return
     */
    public List<Teacher> findLatestTeachers() {
        List<Teacher> allTeachers = teacherRepository.findAll();
        int maxYear = allTeachers.stream()
                .mapToInt(t -> Integer.parseInt(t.getTno().substring(0, 4)))
                .max()
                .orElseThrow(() -> new IllegalStateException("没有找到教师"));
        return allTeachers.stream()
                .filter(t -> Integer.parseInt(t.getTno().substring(0, 4)) == maxYear)
                .collect(Collectors.toList());
    }
    public  Teacher add(Teacher teacher){
        return teacherRepository.save(teacher);
    }

    public  Teacher newupdate(Teacher teacher){
        return teacherRepository.save(teacher);
    }
}

