package cn.lvhaosir.app.security.service;

import cn.lvhaosir.app.service.StudentService;
import cn.lvhaosir.app.service.UserService;
import cn.lvhaosir.core.pojo.po.Student;
import cn.lvhaosir.core.pojo.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by lvhaosir on 2018/10/12.
 */
@Slf4j
@Component("appUserDetailServiceImpl")
public class AppUserDetailServiceImpl implements AppUserDetailService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 去截断用户名吧
        AppUser user = new AppUser();
        user.setUsername("lvhao");
        String encryptPass = passwordEncoder.encode("123456");
        user.setPassword(encryptPass);
        user.setStatus(0);
        return new AppUserDetails(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username, String loginType) throws UsernameNotFoundException {
        AppUser user = null;
        if (loginType.equals("studentLogin")) {
            Student student = studentService.queryByCardId(username);
            if (student == null) {
                return null;
            }
            user = new AppUser(student);
        } else if (loginType.equals("teacherLogin")) {
            User teacher = userService.queryByUsername(username);
            if (teacher == null) {
                return null;
            }
            user = new AppUser(teacher);
        }
        return new AppUserDetails(user);
    }
}
