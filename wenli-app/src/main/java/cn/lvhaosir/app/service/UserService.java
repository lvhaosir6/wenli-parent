package cn.lvhaosir.app.service;

import cn.lvhaosir.app.pojo.vo.TeacherVo;
import cn.lvhaosir.core.pojo.po.User;

/**
 * Created by lvhaosir on 2018/10/13.
 */
public interface UserService {

    /**
     *  根据 userName 查询用户信息
     * @param username
     * @return
     */
    User queryByUsername(String username);

    /**
     *  修改密码
     * @param id
     * @param password
     * @param newPass
     * @return
     */
    User updatePasswordById(String id, String password, String newPass);


    TeacherVo queryTeacherVoByUsername(String username);

    /**
     *  根据id查询教师名称
     * @param id
     * @return 若不存在则返回：无
     */
    String queryNameById(String id);


}
