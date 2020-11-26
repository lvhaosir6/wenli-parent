package cn.lvhaosir.app.security.service;

import cn.lvhaosir.core.pojo.po.Student;
import cn.lvhaosir.core.pojo.po.User;
import lombok.Data;

/**
 * Created by lvhaosir on 2018/10/13.
 */
@Data
public class AppUser {

    /**
     *  教师 与 学生共有的所需的属性
     */
    private String username;
    private String password;
    private Integer status;

    public AppUser() {}

    public AppUser(User user) {
        if (user != null) {
            this.setUsername(user.getUsername());
            this.setPassword(user.getPassword());
            this.setStatus(user.getStatus());
        }
    }

    public AppUser(Student student) {
        if (student != null) {
            this.setUsername(student.getCardId());
            this.setPassword(student.getPassword());
            this.setStatus(student.getStatus());
        }
    }


}
