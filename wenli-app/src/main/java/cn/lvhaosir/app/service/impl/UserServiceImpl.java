package cn.lvhaosir.app.service.impl;

import cn.lvhaosir.app.pojo.vo.TeacherVo;
import cn.lvhaosir.app.service.UserService;
import cn.lvhaosir.core.dao.DepartmentDao;
import cn.lvhaosir.core.dao.UserDao;
import cn.lvhaosir.core.pojo.po.Department;
import cn.lvhaosir.core.pojo.po.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.coyote.http11.Constants.a;

/**
 * Created by lvhaosir on 2018/10/14.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private DepartmentDao departmentDao;

    BCryptPasswordEncoder passwordEncoder =new BCryptPasswordEncoder();

    @Override
    public User queryByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Transactional
    @Override
    public User updatePasswordById(String id, String password, String newPass) {
        User user = userDao.findById(id).get();
        if (!passwordEncoder.matches(password,user.getPassword())) {
            // 原密码输入错误
            return null;
        }
        String encode = passwordEncoder.encode(newPass);
        userDao.updatePassword(id,encode);
        user.setPassword(encode);
        return user;
    }

    @Override
    public TeacherVo queryTeacherVoByUsername(String username) {
        User byUsername = userDao.findByUsername(username);
        return changeVo(byUsername);
    }

    @Override
    public String queryNameById(String id) {
        if (StringUtils.isBlank(id)) {
            return "无";
        } else {
            return userDao.findById(id).get().getNickName();
        }
    }

    private TeacherVo changeVo(User user) {
        TeacherVo teacherVo = new TeacherVo(user);
        String departmentId = user.getDepartmentId();
        if (StringUtils.isBlank(departmentId)) {
            teacherVo.setDepartmentName("无");
        } else {
            Department department = departmentDao.findById(departmentId).get();
            teacherVo.setDepartmentName(department.getTitle());
        }
        return teacherVo;
    }
}
