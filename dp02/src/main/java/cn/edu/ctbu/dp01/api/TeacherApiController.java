package cn.edu.ctbu.dp01.api;

import cn.edu.ctbu.dp01.constant.REnum;
import cn.edu.ctbu.dp01.entity.Teacher;
import cn.edu.ctbu.dp01.exception.RException;
import cn.edu.ctbu.dp01.service.TeacherService;
import cn.edu.ctbu.dp01.util.RUtil;
import cn.edu.ctbu.dp01.vo.QueryObj;
import cn.edu.ctbu.dp01.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherApiController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/list") // 获取教师列表
    public R<List<Teacher>> findAll() {
        List<Teacher> teachers = teacherService.getAllTeacher();
        return RUtil.success(teachers);

    }

    @GetMapping("/{id}") // 根据ID查找教师
    public R<Teacher> findById(@PathVariable int id) {

        Teacher teacher = teacherService.findTeacherById(id);
        return RUtil.success(teacher);
    }

    @GetMapping("/get/{name}")
    public R<List<Teacher>> findByName(@PathVariable String name) {

        List<Teacher> teachers = teacherService.findByName(name);
        return RUtil.success(teachers);

    }
    @PostMapping("/add") // 添加教师
    public R<Teacher> add( Teacher teacher) {
        return RUtil.success(teacherService.add(teacher));
    }

    @PutMapping("/update") // 更新教师
    public R<Teacher> update( Teacher teacher) {
        return RUtil.success(teacherService.update(teacher));
    }

    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Integer id) {
        teacherService.delete(id);
        return RUtil.success();
    }

    @GetMapping("/validateUser")
    public R validateSnoAndPassword(String tno, String password) throws Exception {
        return RUtil.success(teacherService.validateUsernameAndPassword(tno, password));
    }

    @PostMapping("/getbypage")
    public R<Page<Teacher>> getByPage(@RequestBody QueryObj<Teacher> qObj){
        //id排序
        Sort sort = Sort.by(Sort.Direction.ASC,"id");
        Integer pageIndex = 0;
        Integer pageSize = 10;

        if (qObj == null) {
            //teacher对象空，调用分页
            Pageable pageable = PageRequest.of(pageIndex,pageSize,sort);
            Page<Teacher> teachers = teacherService.getAllTeacher(pageable);
            return RUtil.success(teachers.getContent(), teachers.getTotalElements());
        } else {
            pageIndex = qObj.getPage()-1;
            pageSize = qObj.getLimit();

            if(qObj.getData() instanceof Teacher){
                Teacher teacher = (Teacher) qObj.getData();
                Pageable pageable = PageRequest.of(pageIndex,pageSize,sort);

                ExampleMatcher matcher = ExampleMatcher.matching()
                        .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                        .withIgnoreNullValues();

                Example<Teacher> example = Example.of(teacher, matcher);
                Page<Teacher> teacherPage = teacherService.getAllTeacher(example,pageable);
                return RUtil.success(teacherPage.getContent(), teacherPage.getTotalElements());
            }else{
                throw new RException(REnum.QUERY_ERR);
            }
        }
    }
    // 批量删除学生
    @DeleteMapping("/delete")
    public R deleteBatch(@RequestBody List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return RUtil.error(REnum.COMMON_ERR); // 返回错误如果ID列表无效
        }

        try {
            for (Integer id : ids) {
                teacherService.delete(id); // 调用删除方法
            }
            return RUtil.success(); // 返回成功响应
        } catch (Exception e) {
            // 捕抓异常并返回错误响应
            return RUtil.error(1, "删除失败: " + e.getMessage()); // 包含详细的错误信息
        }
    }

}