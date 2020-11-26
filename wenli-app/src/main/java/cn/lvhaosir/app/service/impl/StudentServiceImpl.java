package cn.lvhaosir.app.service.impl;

import cn.lvhaosir.app.pojo.vo.StudentVo;
import cn.lvhaosir.app.service.*;
import cn.lvhaosir.core.dao.*;
import cn.lvhaosir.core.pojo.po.Department;
import cn.lvhaosir.core.pojo.po.Room;
import cn.lvhaosir.core.pojo.po.Student;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvhaosir on 2018/10/13.
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private BuildService buildService;
    @Autowired
    private RoomService roomService;

    BCryptPasswordEncoder passwordEncoder =new BCryptPasswordEncoder();

    @Override
    public Student queryByCardId(String cardId) {
        return studentDao.findByCardId(cardId);
    }

    @Override
    public List<StudentVo> queryByRoomId(String roomId) {
        List<Student> students = studentDao.findByRoomId(roomId);
        return changeToVoList(students);
    }

    @Transactional
    @Override
    public void updateByRegister(String id, String departmentId, String buildId, String roomId, String classId) {
        studentDao.updateByRegister(id, departmentId, buildId, roomId, classId);
    }

    @Transactional
    @Override
    public Student updatePassword(String id, String password, String newPass) {
        Student student = studentDao.findById(id).get();
        if (!passwordEncoder.matches(password,student.getPassword())) {
            // 旧密码输入错误
            return null;
        }
        String newPassword = passwordEncoder.encode(newPass);
        studentDao.updatePassword(id,newPassword);
        student.setPassword(newPassword);
        return student;
    }

    @Transactional
    @Override
    public void resetPassword(String id) {
        studentDao.updatePassword(id,passwordEncoder.encode("111"));
    }

    @Override
    public StudentVo queryByUsername(String username) {
        Student byCardId = studentDao.findByCardId(username);
        return chageToVo(byCardId);
    }

    /**
     *  转换为 ListVo
     * @param students
     * @return
     */
    private List<StudentVo> changeToVoList(List<Student> students) {
        List<StudentVo> list = new ArrayList<>();
        for (Student student : students) {
            list.add(chageToVo(student));
        }
        return list;
    }



    /**
     *  转换为 Vo
     * @param student
     * @return
     */
    private StudentVo chageToVo(Student student) {
        StudentVo studentVo = new StudentVo(student);
        // 系部
        studentVo.setDepartmentName(departmentService.queryNameById(student.getDepartmentId()));
        // 班级
        studentVo.setClassName(clazzService.queryNameById(student.getClassId()));
        // 楼栋
        studentVo.setBuildName(buildService.queryNameById(student.getBuildId()));
        // 宿舍
        studentVo.setRoomName(roomService.queryNameById(student.getRoomId()));
        return studentVo;
    }


}
