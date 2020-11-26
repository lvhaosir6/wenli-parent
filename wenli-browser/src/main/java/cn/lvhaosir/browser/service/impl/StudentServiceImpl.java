package cn.lvhaosir.browser.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.lvhaosir.browser.pojo.vo.condition.DateSearchVo;
import cn.lvhaosir.browser.pojo.vo.manage.StudentVo;
import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.service.*;
import cn.lvhaosir.browser.utils.PageUtil;
import cn.lvhaosir.core.dao.*;
import cn.lvhaosir.core.pojo.po.Room;
import cn.lvhaosir.core.pojo.po.Student;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lvhaosir on 2018/10/18.
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private BuildService buildService;


    private final StudentDao studentDao;

    @Autowired
    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }


    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Page<StudentVo> queryPageList(PageVo pageVo, Student student, DateSearchVo dateSearchVo) {

        Pageable pageable = PageUtil.initPage(pageVo);
        Page<Student> page = studentDao.findAll(new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                Path<String> cardIdFiled = root.get("cardId");
                Path<String> nameFiled = root.get("name");
                Path<String> departmentIdFiled = root.get("departmentId");
                Path<String> classIdFiled = root.get("classId");
                Path<String> buildIdFiled = root.get("buildId");
                Path<String> roomIdFiled = root.get("roomId");
                Path<Integer> statusFiled = root.get("status");
                Path<Date> createTimeField = root.get("createTime");

                List<Predicate> list = new ArrayList<Predicate>();

                // 学号
                if (StringUtils.isNotBlank(student.getCardId())) {
                    // 模糊查询
                    list.add(criteriaBuilder.like(cardIdFiled, student.getCardId() + '%'));
                }
                // 姓名
                if (StringUtils.isNotBlank(student.getName())) {
                    list.add(criteriaBuilder.like(nameFiled, '%' + student.getName() + '%'));
                }
                // 系部
                if (StringUtils.isNotBlank(student.getDepartmentId())) {
                    list.add(criteriaBuilder.equal(departmentIdFiled, student.getDepartmentId()));
                }
                // 班级
                String classId = student.getClassId();
                if (StringUtils.isNotBlank(classId)) {
                    list.add(criteriaBuilder.equal(classIdFiled, classId));
                }
                // 宿舍楼栋
                if (StringUtils.isNotBlank(student.getBuildId())) {
                    list.add(criteriaBuilder.equal(buildIdFiled, student.getBuildId()));
                }
                // 宿舍号码
                if (StringUtils.isNotBlank(student.getRoomId())) {
                    list.add(criteriaBuilder.equal(roomIdFiled, student.getRoomId()));
                }
                //状态
                if (student.getStatus() != null) {
                    list.add(criteriaBuilder.equal(statusFiled, student.getStatus()));
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

        List<Student> content = page.getContent();
        List<StudentVo> studentVos = changeToVoList(content);

        Page<StudentVo> lists = new PageImpl<StudentVo>(studentVos, pageable, page.getTotalElements());
        return lists;
    }

    @Override
    public List<StudentVo> queryAll() {
        return changeToVoList(studentDao.findAll());
    }

    @Transactional
    @Override
    public void update(String id, String cardId, String name, String departmentId, String classId, String buildId, String roomId) {
        studentDao.updateByRegister(id, cardId, name, departmentId, classId, buildId, roomId);
    }


    @Override
    public void save(Student student) {
        // 在校
        student.setStatus(1);
        studentDao.save(student);
    }

    @Transactional
    @Override
    public void resetPassword(String id) {
        studentDao.updatePassword(id, passwordEncoder.encode("111"));
    }

    @Transactional
    @Override
    public void updateStatus(String id, Integer status) {
        studentDao.updateStatus(id, status);
    }

    @Override
    public List<Student> queryByClassId(String classId) {
        return studentDao.findByClassId(classId);
    }

    /**
     * 转换为 ListVo
     *
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

    @Override
    public Student queryByCardId(String cardId) {
        return studentDao.findByCardId(cardId);
    }

    @Override
    public Student queryById(String id) {
        return studentDao.findById(id).get();
    }

    @Override
    public void updateRoomChiefById(String id) {
        roomService.updateStudentId(this.queryById(id).getRoomId(), id);
    }

    @Override
    public List<Student> queryByRoomId(String roomId) {
        return studentDao.findByRoomId(roomId);
    }

    @Override
    public String queryNameById(String id) {
        if (StringUtils.isNotBlank(id)) {
            Student student = queryById(id);
            if (student != null) {
                return student.getName();
            }
        }
        return "无";
    }

    /**
     * 转换为 Vo
     *
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
