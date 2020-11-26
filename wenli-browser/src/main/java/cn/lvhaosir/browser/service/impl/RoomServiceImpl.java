package cn.lvhaosir.browser.service.impl;

import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.pojo.vo.manage.RoomVo;
import cn.lvhaosir.browser.service.*;
import cn.lvhaosir.browser.utils.PageUtil;
import cn.lvhaosir.core.dao.RoomDao;
import cn.lvhaosir.core.pojo.po.Room;
import cn.lvhaosir.core.pojo.po.Student;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Set;

/**
 * Created by lvhaosir on 2018/10/21.
 */
@Service
@CacheConfig(cacheNames = "roomCache")
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomDao roomDao;

    @Cacheable(key = "'rooms:'+#buildId")
    @Override
    public List<Room> queryByBuildId(String buildId) {
        return roomDao.findByBuildId(buildId);
    }

    @Override
    public Room queryById(String id) {
        return roomDao.findById(id).get();
    }

    @Override
    public String queryNameById(String id) {
        if (StringUtils.isBlank(id)) {
            return "无";
        } else {
            return roomDao.findById(id).get().getName();
        }
    }

    @Transactional
    @Override
    public void updateStudentId(String id, String studentId) {
        roomDao.updateStudentId(id, studentId);
    }

    @Override
    public Page<RoomVo> queryPageList(PageVo pageVo, Room room) {
        Pageable pageable = PageUtil.initPage(pageVo);

        Page<Room> page = roomDao.findAll(new Specification<Room>() {
            @Override
            public Predicate toPredicate(Root<Room> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                Path<String> nameFiled = root.get("name");
                Path<String> buildIdFiled = root.get("buildId");
                Path<String> departmentIdFiled = root.get("departmentId");
                Path<String> classIdFiled = root.get("classId");

                List<Predicate> list = Lists.newArrayList();

                // 名称模糊查询
                if (StringUtils.isNotBlank(room.getName())) {
                    list.add(criteriaBuilder.like(nameFiled, room.getName() + '%'));
                }
                // 楼栋
                if (StringUtils.isNotBlank(room.getBuildId())) {
                    list.add(criteriaBuilder.equal(buildIdFiled, room.getBuildId()));
                }
                // 系部
                if (StringUtils.isNotBlank(room.getDepartmentId())) {
                    list.add(criteriaBuilder.equal(departmentIdFiled, room.getDepartmentId()));
                }
                // 班级
                if (StringUtils.isNotBlank(room.getClassId())) {
                    list.add(criteriaBuilder.equal(classIdFiled, room.getClassId()));
                }
                Predicate[] arr = new Predicate[list.size()];
                query.where(list.toArray(arr));
                return null;
            }
        }, pageable);

        Page<RoomVo> roomVos = new PageImpl<RoomVo>(changeToListVo(page.getContent()), pageable, page.getTotalElements());
        return roomVos;
    }

    @Override
    public void save(Room room) {
        roomDao.save(room);
        this.deleteCacheFormBuildId();
    }

    @Override
    public void updateById(Room room) {
        roomDao.saveAndFlush(room);
        this.deleteCacheFormBuildId();
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     *  删除 根据 buildId  查询出来的集合
     */
    private void deleteCacheFormBuildId() {
        Set<String> keys = stringRedisTemplate.keys("roomCache::rooms:*");
        stringRedisTemplate.delete(keys);
    }

    @Autowired
    private StudentService studentService;

    @Override
    public boolean delByIds(String[] ids) {
        for (String id : ids) {
            List<Student> students = studentService.queryByRoomId(id);
            if (students != null && students.size() > 0) {
                return false;
            }
        }
        for (String id : ids) {
            roomDao.deleteById(id);
        }
        return true;
    }

    @Autowired
    private BuildService buildService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ClazzService clazzService;


    private RoomVo changeToVo(Room room) {
        RoomVo roomVo = new RoomVo(room);
        // 设置楼栋名称
        roomVo.setBuildName(buildService.queryNameById(room.getBuildId()));
        // 系部名称
        roomVo.setDepartmentName(departmentService.queryNameById(room.getDepartmentId()));
        // 班级名称
        roomVo.setClassName(clazzService.queryNameById(room.getClassId()));
        // 学生名称
        roomVo.setStudentName(studentService.queryNameById(room.getStudentId()));
        return roomVo;
    }

    private List<RoomVo> changeToListVo(List<Room> list) {
        List<RoomVo> roomVos = Lists.newArrayList();
        for (Room room : list) {
            roomVos.add(changeToVo(room));
        }
        return roomVos;
    }
}
