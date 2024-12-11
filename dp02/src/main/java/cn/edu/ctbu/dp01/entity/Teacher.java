package cn.edu.ctbu.dp01.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name="tb_teacher")
public class Teacher {
    //主键自增

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //姓名
    private String name;

    //年龄
    private Integer age;

    //性别，1男，2女，0未知
    private Integer sex;

    //工号
    private String tno;

}