package cn.lvhaosir.browser.controller.manage;

import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.pojo.vo.condition.DateSearchVo;
import cn.lvhaosir.browser.pojo.vo.manage.StudentVo;
import cn.lvhaosir.browser.service.StudentService;
import cn.lvhaosir.core.pojo.po.Student;
import cn.lvhaosir.core.pojo.vo.Result;
import cn.lvhaosir.core.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lvhaosir on 2018/10/18.
 */
@RequestMapping("/student")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;



    /**
     *  分页多条件查询
     * @param pageVo
     * @param student
     * @param dateSearchVo
     * @return
     */
    @GetMapping("/getAllByPage")
    public Result<Page<StudentVo>> getAllByPage(@ModelAttribute PageVo pageVo,
                                                @ModelAttribute Student student,
                                                @ModelAttribute DateSearchVo dateSearchVo) {
        Page<StudentVo> studentVos = studentService.queryPageList(pageVo,student, dateSearchVo);
        return new ResultUtil<Page<StudentVo>>().setData(studentVos);
    }

    @GetMapping("/getAll")
    public Result<List<StudentVo>> getAll() {
        List<StudentVo> studentVos = studentService.queryAll();
        return new ResultUtil<List<StudentVo>>().setData(studentVos);
    }

    /**
     *  根据cardId查询学生，判断此学号是否被占用
     * @param cardId
     * @return
     */
    @GetMapping("/queryByCardId/{cardId}")
    public Result<Object> queryByCardId(@PathVariable String cardId) {
        Student student = studentService.queryByCardId(cardId);
        System.out.println(student);
        if (student == null) {
            return new ResultUtil<Object>().setErrorMessage(300,"该学号未使用");
        }
        return new ResultUtil<Object>().setSuccessMessage("学号已占用");
    }

    /**
     *  根据 id 修改如下属性，除了 status外
     * @param id
     * @param cardId
     * @param name
     * @param departmentId
     * @param classId
     * @param buildId
     * @param roomId
     * @return
     */
    @PutMapping("/updateExceptStatus")
    public Result<Object> update(@RequestParam("id") String id,
                                 @RequestParam("cardId") String cardId,
                                 @RequestParam("name") String name,
                                 @RequestParam("departmentId") String departmentId,
                                 @RequestParam("classId") String classId,
                                 @RequestParam("buildId") String buildId,
                                 @RequestParam("roomId") String roomId) {
        studentService.update(id, cardId, name, departmentId, classId, buildId, roomId);
        return new ResultUtil<Object>().setSuccessMessage("修改成功");
    }

    /**
     *  新增学生信息
     * @param student
     * @return
     */
    @PostMapping("/save")
    public Result<Object> save(@ModelAttribute Student student) {
        studentService.save(student);
        return new ResultUtil<Object>().setSuccessMessage("修改成功");
    }

    /**
     *  重置密码
     * @param id
     * @return
     */
    @PutMapping("/resetPassword")
    public Result<Object> resetPassword(@RequestParam("id") String id) {
        studentService.resetPassword(id);
        return new ResultUtil<Object>().setSuccessMessage("修改成功");
    }

    /**
     *  修改学生状态为 -1 （禁用）
     * @param id
     * @return
     */
    @PutMapping("/disable")
    public Result<Object> updateStatusDisable(@RequestParam("id") String id ) {
        studentService.updateStatus(id, -1);
        return new ResultUtil<Object>().setSuccessMessage("修改成功");
    }

    /**
     *  修改学生状态为 1 （在校）
     * @param id
     * @return
     */
    @PutMapping("/enable")
    public Result<Object> updateStatusEnable(@RequestParam("id") String id ) {
        studentService.updateStatus(id, 1);
        return new ResultUtil<Object>().setSuccessMessage("修改成功");
    }


    @PutMapping("/updateRoomChiefById/{id}")
    public Result<Object> updateRoomChiefById(@PathVariable("id") String id) {
        studentService.updateRoomChiefById(id);
        return new ResultUtil<Object>().setSuccessMessage("设置成功");
    }




}
