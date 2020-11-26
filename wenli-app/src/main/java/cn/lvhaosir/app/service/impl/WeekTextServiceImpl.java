package cn.lvhaosir.app.service.impl;

import cn.lvhaosir.app.pojo.vo.RoomVo;
import cn.lvhaosir.app.pojo.vo.WeekTextVo;
import cn.lvhaosir.app.service.BuildService;
import cn.lvhaosir.app.service.RoomService;
import cn.lvhaosir.app.service.WeekTextService;
import cn.lvhaosir.core.dao.BuildDao;
import cn.lvhaosir.core.dao.RoomDao;
import cn.lvhaosir.core.dao.WeekTextDao;
import cn.lvhaosir.core.pojo.po.Room;
import cn.lvhaosir.core.pojo.po.WeekText;
import cn.lvhaosir.core.utils.WeekTextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvhaosir on 2018/10/14.
 */
@Slf4j
@Service
public class WeekTextServiceImpl implements WeekTextService {

    @Autowired
    private WeekTextDao weekTextDao;
    @Autowired
    private RoomService roomService;
    @Autowired
    private BuildService buildService;

    @Override
    public WeekText save(WeekText weekText) {
        Room room = roomService.queryById(weekText.getRoomId());
        weekText.setBuildId(room.getBuildId());
        weekText.setDepartmentId(room.getDepartmentId());
        weekText.setClassId(room.getClassId());
        return weekTextDao.save(weekText);
    }

    @Override
    public List<WeekText> queryByRoomId(String roomId) {
        return weekTextDao.findByRoomId(roomId);
    }

    @Override
    public WeekText queryById(String id) {
        return weekTextDao.findById(id).get();
    }

    @Transactional
    @Override
    public void updateReturnTextById(String id, String teacherReturnText) {
        // 教师可能先去标记为 异常周记 status=2 为异常周记
        Integer status = this.queryById(id).getStatus();
        if (status >= 2) {
            weekTextDao.updateTeacherReturnText(id, teacherReturnText, status);
        } else {
            weekTextDao.updateTeacherReturnText(id, teacherReturnText, 1);
        }
    }

    @Override
    public List<WeekTextVo> queryNoReturnByTeacherId(String teacherId) {

        // 首先根据教师Id 查询出所有管理的宿舍
        List<Room> rooms = roomService.queryByTeacherId(teacherId);
        List<WeekText> list = new ArrayList<>();
        for (Room room : rooms) {
            // 根据 宿舍 id 和 未读周记
            List<WeekText> byRoomIdAndStatus = weekTextDao.findByRoomIdAndStatus(room.getId(), 0);
            if (byRoomIdAndStatus != null && byRoomIdAndStatus.size() > 0) {
                list.addAll(byRoomIdAndStatus);
            }
        }
        // 转换为 VoList
        return changeVoList(list);
    }

    @Transactional
    @Override
    public void updateStatus(String id, Integer status) {
        weekTextDao.updateStatus(id, status);
    }

    /**
     * 紧急周记打印
     *
     * @param id
     * @return
     */
    public String printUrgencyWeekText(String id) {
        // 并将周记状态改变
        this.updateStatus(id, 2);
        StringBuilder sb = new StringBuilder();
        WeekText weekText = this.queryById(id);
        // 该宿舍的详细信息
        RoomVo roomVo = roomService.queryVoById(weekText.getRoomId());
        sb.append("该宿舍信息【宿舍位置:" + roomVo.getBuildName() + roomVo.getRoomName());
        sb.append(" , 系部:" + roomVo.getDepartmentName());
        sb.append(" , 班级:" + roomVo.getClassName());
        sb.append(" , 教师:" + roomVo.getTeacherName() + "】");

        // 周记详细信息
        sb.append("【学习任务完成情况:" + WeekTextUtil.getStatusName(weekText.getStudyStatus()));
        sb.append(" , 情绪情况:" + WeekTextUtil.getStatusName(weekText.getMoodStatus()));
        sb.append(" , 身体情况:" + WeekTextUtil.getStatusName(weekText.getHealthStatus()));
        sb.append(" , 彻夜未归情况:" + WeekTextUtil.getStatusName(weekText.getReturnRoomStatus()));
        sb.append(" , 休息情况:" + WeekTextUtil.getStatusName(weekText.getSleepStatus()));
        sb.append(" , 消费情况:" + WeekTextUtil.getStatusName(weekText.getConsumeStatus()));
        sb.append(" , 失恋情况:" + WeekTextUtil.getStatusName(weekText.getLoveStatus()));
        sb.append(" , 学生备注信息:" + weekText.getConditionText());
        sb.append(" , 教师回复信息:" + weekText.getTeacherReturnText());
        sb.append(" , 教师回复时间:" + weekText.getReturnTime() + " 】");
        return sb.toString();
    }

    /**
     * 转换为 VoList
     *
     * @param list
     * @return
     */
    private List<WeekTextVo> changeVoList(List<WeekText> list) {
        List<WeekTextVo> weekTextVos = new ArrayList<>();
        for (WeekText weekText : list) {
            weekTextVos.add(changeVo(weekText));
        }
        return weekTextVos;
    }

    /**
     * 转换为 Vo
     *
     * @param weekText
     * @return
     */
    private WeekTextVo changeVo(WeekText weekText) {
        WeekTextVo weekTextVo = new WeekTextVo(weekText);
        weekTextVo.setRoomName(roomService.queryNameById(weekText.getRoomId()));
        weekTextVo.setBuildName(buildService.queryNameById(weekText.getBuildId()));
        return weekTextVo;
    }
}
