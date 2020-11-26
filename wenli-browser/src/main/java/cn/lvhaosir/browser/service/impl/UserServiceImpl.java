package cn.lvhaosir.browser.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.lvhaosir.browser.pojo.vo.manage.UserVo;
import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.pojo.vo.condition.DateSearchVo;
import cn.lvhaosir.browser.service.UserService;
import cn.lvhaosir.browser.utils.PageUtil;
import cn.lvhaosir.core.constant.EntityConstant;
import cn.lvhaosir.core.dao.*;
import cn.lvhaosir.core.pojo.po.*;
//import com.sun.istack.internal.Nullable;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lvhaosir on 2018/9/20.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public UserVo queryByUsername(String username) {
        UserVo userVo = null;
        // 根据 用户名 和 用户状态查询
        User user = userDao.findByUsernameAndStatus(username, EntityConstant.USER_STATUS_NORMAL);
        if (user != null) {
            String userId = user.getId();
            //
            userVo = new UserVo(user);
            String departmentId = user.getDepartmentId();
            if (StringUtils.isNotBlank(departmentId)) {
                // 查询部门
                Department department = departmentDao.findById(departmentId).get();
                // 设置用户部门信息
                userVo.setDepartmentTitle(department.getTitle());
            }
            // 关联角色
            List<Role> roleList = roleDao.findByUserId(userId);
            userVo.setRoles(roleList);
            // 关联权限菜单
            List<Permission> permissionList = permissionDao.queryByUserId(userId);
            userVo.setPermissions(permissionList);
            return userVo;
        }
        return null;
    }

    @Override
    public Page<UserVo> queryByCondition(User user, DateSearchVo dateSearchVo, PageVo pageVo) {

        Pageable pageable = PageUtil.initPage(pageVo);

        Page<User> page = userDao.findAll(new Specification<User>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                Path<String> usernameField = root.get("username");
                Path<String> mobileField = root.get("mobile");
                Path<String> emailField = root.get("email");
                Path<String> departmentIdField = root.get("departmentId");
                Path<Integer> sexField = root.get("sex");
                Path<Integer> typeField = root.get("type");
                Path<Integer> statusField = root.get("status");
                Path<Date> createTimeField = root.get("createTime");

                List<Predicate> list = new ArrayList<Predicate>();

                //模糊搜素
                if (StringUtils.isNotBlank(user.getUsername())) {
                    list.add(cb.like(usernameField, '%' + user.getUsername() + '%'));
                }
                if (StringUtils.isNotBlank(user.getMobile())) {
                    list.add(cb.like(mobileField, '%' + user.getMobile() + '%'));
                }
                if (StringUtils.isNotBlank(user.getEmail())) {
                    list.add(cb.like(emailField, '%' + user.getEmail() + '%'));
                }
                // 部门
                if (StrUtil.isNotBlank(user.getDepartmentId())) {
                    list.add(cb.equal(departmentIdField, user.getDepartmentId()));
                }

                //性别
                if (user.getSex() != null) {
                    list.add(cb.equal(sexField, user.getSex()));
                }
                //类型
                if (user.getType() != null) {
                    list.add(cb.equal(typeField, user.getType()));
                }
                //状态
                if (user.getStatus() != null) {
                    list.add(cb.equal(statusField, user.getStatus()));
                }
                //创建时间
                if (StrUtil.isNotBlank(dateSearchVo.getStartDate()) && StrUtil.isNotBlank(dateSearchVo.getEndDate())) {

                    Date start = DateUtil.parse(dateSearchVo.getStartDate());
                    Date end = DateUtil.parse(dateSearchVo.getEndDate());
                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
                }

                Predicate[] arr = new Predicate[list.size()];
                query.where(list.toArray(arr));
                return null;
            }
        }, pageable);

        List<UserVo> listDto = new ArrayList<>();

        for (User u : page.getContent()) {
            UserVo ud = new UserVo(u);
            String departmentId = u.getDepartmentId();
            if (StringUtils.isNotBlank(departmentId)) {
                Department department = departmentDao.findById(departmentId).get();
                ud.setDepartmentTitle(department.getTitle());
            }
            List<Role> roleList = roleDao.findByUserId(u.getId());
            ud.setRoles(roleList);
            ud.setPassword(null);
            listDto.add(ud);
        }

        Page<UserVo> dtoPage = new PageImpl<UserVo>(listDto,pageable,page.getTotalElements());

        return dtoPage;
    }

    @Override
    public User addUser(User u, String[] roles) {

        String encode = bCryptPasswordEncoder.encode(u.getPassword());
        u.setPassword(encode);

        User user = userDao.save(u);
        if (user == null) {
            return null;
        }
        String userId = user.getId();
        // 新增用户角色
        saveRoleUser(roles, userId);
        return user;
    }

    @Transactional
    @Override
    public Integer updateUser(User u, String[] roles) {
        String username = u.getUsername();
        String userId = u.getId();
        User oldUser = userDao.findById(userId).get();
        // 如果需要修改用户名
        if (!username.equals(oldUser.getUsername())) {
            // 需要判断新用户名是否存在
            User byUsername = userDao.findByUsername(username);
            if (byUsername != null) {
                // 返回 -2 表示，新修改的用户名已存在
                return -2;
            }
        }
        // 不需要修改用户名
        u.setPassword(oldUser.getPassword());
        User user = userDao.saveAndFlush(u);
        if (user == null) {
            // 表示修改失败
            return -1;
        }
        // 先删除该用户角色
        userRoleDao.deleteByUserId(userId);
        // 再新增用户角色
        saveRoleUser(roles, userId);
        return 0;
    }

    @Override
    public boolean updateUser(String userId, Integer status) {

        User one = userDao.findById(userId).get();
        if (one == null) {
            return false;
        }
        one.setStatus(status);
        userDao.saveAndFlush(one);
        return true;
    }

    @Transactional
    @Override
    public void delByIds(String[] ids) {

        for (String id : ids) {
            userDao.deleteById(id);
            userRoleDao.deleteByUserId(id);
        }

    }

    @Override
    public boolean unLock(String password) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User u = userDao.findByUsername(user.getUsername());
        if (!bCryptPasswordEncoder.matches(password,u.getPassword())) {
            // 如果密码不相同
            return false;
        }
        return true;
    }

    @Override
    public boolean updateUser(User u) {
        User oldUser = userDao.findById(u.getId()).get();
        u.setUsername(oldUser.getUsername());
        u.setPassword(oldUser.getPassword());
        User user = userDao.saveAndFlush(u);
        if (user == null) {
            return false;
        }
        return true;
    }

    @Override
    public User modifyPass(String id, String password, String newPass) {
        User oldUser = userDao.findById(id).get();
        if (!bCryptPasswordEncoder.matches(password,oldUser.getPassword())) {
            // 旧密码不正确
            return null;
        }
        String encode = bCryptPasswordEncoder.encode(newPass);
        oldUser.setPassword(encode);
        User user = userDao.saveAndFlush(oldUser);
        if (user == null) {
            return null;
        }
        return oldUser;
    }



    private void saveRoleUser(String[] roles, String userId) {
        if (roles != null && roles.length > 0 ) {
            // 添加用户所对应的角色
            for (String roleId : roles) {
                UserRole ur = new UserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                userRoleDao.save(ur);
            }
        }
    }

    @Override
    public String queryNameById(String id) {
        if (StringUtils.isBlank(id)) {
            return "无";
        } else {
            return userDao.findById(id).get().getNickName();
        }
    }

    @Override
    public List<User> queryAll() {
        return userDao.findAll();
    }

    @Override
    public List<User> queryByDepartmentId(String departmentId) {
        return userDao.findByDepartmentId(departmentId);
    }
}
