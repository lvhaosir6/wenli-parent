package cn.lvhaosir.app.service;

import cn.lvhaosir.app.pojo.vo.WeekTextVo;
import cn.lvhaosir.core.pojo.po.WeekText;

import java.util.List;

/**
 * Created by lvhaosir on 2018/10/13.
 */
public interface WeekTextService {

    /**
     *  新增周记
     * @param weekText
     * @return
     */
    WeekText save(WeekText weekText);

    /**
     * 根据宿舍ID查询改宿舍的所有周记
     * @param roomId
     * @return
     */
    List<WeekText> queryByRoomId(String roomId);

    /**
     *  根据周记编号查询出周记信息
     * @param id
     * @return
     */
    WeekText queryById(String id);

    /**
     *  教师回复信息
     * @param id
     * @param teacherReturnText
     * @return
     */
    void updateReturnTextById(String id, String teacherReturnText);

    /**
     *  查询教师管理下宿舍的所有未读周记
     * @param teacherId
     * @return 返回数据为 Vo
     */
    List<WeekTextVo> queryNoReturnByTeacherId(String teacherId);

    /**
     *  根据Id输出，系部、班级、楼栋、宿舍、教师、周记。。等详细信息
     * @param id
     * @return
     */
    String printUrgencyWeekText(String id);

    /**
     *  根据id修改status
     * @param id
     * @param status
     */
    void updateStatus(String id, Integer status);
}
