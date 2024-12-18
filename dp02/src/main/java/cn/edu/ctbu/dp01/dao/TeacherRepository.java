package cn.edu.ctbu.dp01.dao;

import cn.edu.ctbu.dp01.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Integer>{

    /**
     * 按名字查询
     * @param name
     * @return
     */
    List<Teacher> findByNameLike(String name);

    /**
     * 按名和密码查询
     * @param name
     * @param password
     * @return
     */
    List<Teacher> findByNameAndPassword(String name,String password);
    public List<Teacher> findBytno(String tno);

}
