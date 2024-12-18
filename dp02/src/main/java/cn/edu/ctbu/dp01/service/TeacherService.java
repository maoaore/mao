package cn.edu.ctbu.dp01.service;

import cn.edu.ctbu.dp01.constant.REnum;
import cn.edu.ctbu.dp01.dao.TeacherRepository;
import cn.edu.ctbu.dp01.entity.Teacher;
import cn.edu.ctbu.dp01.exception.RException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Teacher> getAllTeacher(Pageable pageable) {

        return teacherRepository.findAll(pageable);
    }

    public Page<Teacher> getAllTeacher(Example<Teacher> example, Pageable pageable) {

        return teacherRepository.findAll(example,pageable);
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
     * 按M名字进行查询。like
     * @param name
     * @return
     */
    public List<Teacher> findByName(String name){

        return teacherRepository.findByNameLike(name);
    }
//    /**
//     * 按名字和密码进行查询
//     * @param name
//     * @param password
//     * @return
//     */
//    public List<Teacher> findByNameAndPassword(String name,String password){
//
//
//        return teacherRepository.findByNameAndPassword(name,password);
//    }
    /**
     * 增
     * @param teacher
     */
    public void insert(Teacher teacher){
        teacherRepository.save(teacher);
    }

    /**
     * 新增学生
     * @param teacher
     * @return
     */
    public Teacher add(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    /**
     * 改
     * @param teacher
     */
    public Teacher update(Teacher teacher){
        return teacherRepository.save(teacher);
    }

    /**
     * 删
     * @param id
     */
    public void delete(Integer id){
        teacherRepository.deleteById(id);
    }

    public Teacher validateUsernameAndPassword(String tno, String password) throws Exception {
        List<Teacher> teachers = teacherRepository.findBytno(tno);
        if (teachers.size() > 0) {

            Teacher teacher = teachers.get(0);
            if (teacher.getPassword().equals(password)) {
                return teacher;
            } else {
                // 密码不正确
                throw new RException(REnum.LOGIN_ERR);
            }
        }else{    // 用户不存在
        throw new RException(REnum.LOGIN_ERR);}
    }



//    /**
//     * 工龄最大
//     * @return
//     */
//    public List<Teacher> findLatestTeachers() {
//        List<Teacher> allTeachers = teacherRepository.findAll();
//        int maxYear = allTeachers.stream()
//                .mapToInt(t -> Integer.parseInt(t.getTno().substring(0, 4)))
//                .max()
//                .orElseThrow(() -> new IllegalStateException("没有找到教师"));
//        return allTeachers.stream()
//                .filter(t -> Integer.parseInt(t.getTno().substring(0, 4)) == maxYear)
//                .collect(Collectors.toList());
//    }
//    public  Teacher add(Teacher teacher){
//        return teacherRepository.save(teacher);
//    }
//
//    public  Teacher newupdate(Teacher teacher){
//        return teacherRepository.save(teacher);
//    }
}

