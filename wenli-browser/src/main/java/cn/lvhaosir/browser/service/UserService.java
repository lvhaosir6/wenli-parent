package cn.lvhaosir.browser.service;

import cn.lvhaosir.browser.pojo.vo.manage.UserVo;
import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.pojo.vo.condition.DateSearchVo;
import cn.lvhaosir.core.pojo.po.User;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by lvhaosir on 2018/9/20.
 */
public interface UserService {

    /**
     *  根据用户名查询用户信息
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     *  根据用户名查询，返回 dto 对象
     *
     * @param username
     * @return
     */
    UserVo queryByUsername(String username);

    /**
     *  多条件分页获取用户列表
     * @param user
     * @param dateSearchVo
     * @param pageVo
     * @return
     */
    Page<UserVo> queryByCondition(User user, DateSearchVo dateSearchVo, PageVo pageVo);

    /**
     *  新用户注册
     * @param u
     * @param roles
     */
    User addUser(User u , String[] roles);

    /**
     *  修改用户信息 和 角色
     * @param u
     * @param roles
     * @return
     */
    Integer updateUser(User u,String[] roles);
    /**
     *  用户修改自己的信息
     * @param u
     * @return
     */
    boolean updateUser(User u);

    /**
     *  禁用或开启用户
     * @param userId
     */
    boolean updateUser(String userId,Integer status);

    /**
     *  删除用户
     * @param ids
     */
    void delByIds(String[] ids);

    /**
     *  解锁验证密码
     * @param password
     * @return
     */
    boolean unLock(String password);

    /**
     *  修改密码
     * @param id
     * @param password
     * @param newPass
     * @return
     */
    User modifyPass(String id,String password,String newPass);
    /**
     *  根据id查询教师名称
     * @param id
     * @return 若不存在则返回：无
     */
    String queryNameById(String id);

    List<User> queryAll();

    List<User> queryByDepartmentId(String departmentId);
}
