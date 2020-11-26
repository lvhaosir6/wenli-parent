package cn.lvhaosir.browser.service;

import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.pojo.vo.manage.RoomVo;
import cn.lvhaosir.core.pojo.po.Room;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by lvhaosir on 2018/10/21.
 */
public interface RoomService {
    /**
     *  根据楼栋Id查询
     * @param buildId
     * @return
     */
    List<Room> queryByBuildId(String buildId);

    /**
     *  根据 id 查询
     * @param id
     * @return
     */
    Room queryById(String id);

    /**
     *  根据id查询宿舍名称
     * @param id
     * @return 若不存在则返回：无
     */
    String queryNameById(String id);

    /**
     *  根据id修改寝室长
     * @param id
     * @param studentId
     */
    void updateStudentId(String id, String studentId);

    /**
     *  分页查询数据
     * @param pageVo
     * @param room
     * @return
     */
    Page<RoomVo> queryPageList(PageVo pageVo, Room room);

    /**
     *  新增
     * @param room
     */
    void save(Room room);

    /**
     *  根据 id 更新
     * @param room
     */
    void updateById(Room room);

    /**
     *  根据 id 删除
     * @param ids
     * @return
     */
    boolean delByIds(String[] ids);


}
