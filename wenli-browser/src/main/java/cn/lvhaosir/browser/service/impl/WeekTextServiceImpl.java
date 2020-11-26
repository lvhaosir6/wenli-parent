package cn.lvhaosir.browser.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.lvhaosir.browser.pojo.vo.condition.DateSearchVo;
import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.pojo.vo.manage.WeekTextVo;
import cn.lvhaosir.browser.service.*;
import cn.lvhaosir.browser.utils.PageUtil;
import cn.lvhaosir.core.dao.WeekTextDao;
import cn.lvhaosir.core.pojo.po.Room;
import cn.lvhaosir.core.pojo.po.WeekText;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lvhaosir on 2018/10/26.
 */
@Service
public class WeekTextServiceImpl implements WeekTextService{

    @Autowired
    private WeekTextDao weekTextDao;

    @Override
    public Page<WeekTextVo> queryPageList(PageVo pageVo, WeekTextVo weekTextVo, DateSearchVo dateSearchVo) {
        Pageable pageable = PageUtil.initPage(pageVo);

        Page<WeekText> page = weekTextDao.findAll(new Specification<WeekText>() {
            @Override
            public Predicate toPredicate(Root<WeekText> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                Path<String> departmentIdFiled = root.get("departmentId");
                Path<String> classIdFiled = root.get("classId");
                Path<String> buildIdFiled = root.get("buildId");
                Path<String> roomIdFiled = root.get("roomId");
                Path<Integer> statusFiled = root.get("status");
                Path<Date> createTimeField = root.get("createTime");

                List<Predicate> list = new ArrayList<Predicate>();

                // 系部
                if (StringUtils.isNotBlank(weekTextVo.getDepartmentId())) {
                    list.add(criteriaBuilder.equal(departmentIdFiled, weekTextVo.getDepartmentId()));
                }
                // 班级
                if (StringUtils.isNotBlank(weekTextVo.getClassId())) {
                    list.add(criteriaBuilder.equal(classIdFiled, weekTextVo.getClassId()));
                }
                // 宿舍楼栋
                if (StringUtils.isNotBlank(weekTextVo.getBuildId())) {
                    list.add(criteriaBuilder.equal(buildIdFiled, weekTextVo.getBuildId()));
                }
                // 宿舍号码
                if (StringUtils.isNotBlank(weekTextVo.getRoomId())) {
                    list.add(criteriaBuilder.equal(roomIdFiled,weekTextVo.getRoomId()));
                }
                //状态
                if (weekTextVo.getStatus() != null) {
                    list.add(criteriaBuilder.equal(statusFiled, weekTextVo.getStatus()));
                }
                //创建时间
                if (StrUtil.isNotBlank(dateSearchVo.getStartDate()) && StrUtil.isNotBlank(dateSearchVo.getEndDate())) {
                    Date start = DateUtil.parse(dateSearchVo.getStartDate());
                    Date end = DateUtil.parse(dateSearchVo.getEndDate());
                    list.add(criteriaBuilder.between(createTimeField, start, DateUtil.endOfDay(end)));
                }

                Predicate[] arr = new Predicate[list.size()];
                query.where(list.toArray(arr));
                return null;
            }
        }, pageable);

        List<WeekTextVo> weekTextVos = new ArrayList<>();
        for (WeekText weekText : page.getContent()) {
            weekTextVos.add(changeToVo(weekText));
        }

        Page<WeekTextVo> weekTextVoPage = new PageImpl<>(weekTextVos, pageable, page.getTotalElements());

        return weekTextVoPage;
    }

    @Transactional
    @Override
    public void delByIds(String[] ids) {
        for (String id : ids) {
            weekTextDao.deleteById(id);
        }
    }

    @Autowired
    private RoomService roomService;
    @Autowired
    private BuildService buildService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UserService userService;

    /**
     *  转换为 VO,补充信息
     * @param weekText
     * @return
     */
    private WeekTextVo changeToVo(WeekText weekText) {
        WeekTextVo weekTextVo = new WeekTextVo(weekText);
        Room room = roomService.queryById(weekTextVo.getRoomId());
        // 宿舍名称
        weekTextVo.setRoomName(roomService.queryNameById(room.getId()));
        // 楼栋名称
        weekTextVo.setBuildName(buildService.queryNameById(room.getBuildId()));
        // 班级名称
        weekTextVo.setClassName(clazzService.queryNameById(room.getClassId()));
        // 系部名称
        weekTextVo.setDepartmentName(departmentService.queryNameById(room.getDepartmentId()));
        // 教师名称
        weekTextVo.setTeacherName(userService.queryNameById(room.getTeacherId()));
        return weekTextVo;
    }

}
