package cn.lvhaosir.browser.controller.manage;

import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.pojo.vo.manage.RoomVo;
import cn.lvhaosir.browser.service.RoomService;
import cn.lvhaosir.core.pojo.po.Build;
import cn.lvhaosir.core.pojo.po.Room;
import cn.lvhaosir.core.pojo.vo.Result;
import cn.lvhaosir.core.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lvhaosir on 2018/10/21.
 */
@RequestMapping("/room")
@RestController
public class RoomController {

    @Autowired
    private RoomService roomService;

    /**
     *  根据 宿舍楼栋id（buildId）查询 宿舍信息
     * @param buildId
     * @return
     */
    @GetMapping("/queryByBuildId/{buildId}")
    public Result<List<Room>> queryByBuildId(@PathVariable String buildId ) {
        List<Room> rooms = roomService.queryByBuildId(buildId);
        return new ResultUtil<List<Room>>().setData(rooms);
    }

    /**
     *  模糊、分页查询
     * @param pageVo
     * @param room
     * @return
     */
    @GetMapping("/getAllByPage")
    public Result<Page<RoomVo>> getAllByPage(PageVo pageVo, Room room) {
        Page<RoomVo> roomVos = roomService.queryPageList(pageVo, room);
        return new ResultUtil<Page<RoomVo>>().setData(roomVos);
    }

    @PostMapping("/save")
    public Result<Object> save(Room room) {
        roomService.save(room);
        return new ResultUtil<Object>().setSuccessMessage("添加成功");
    }

    @PutMapping("/updateById")
    public Result<Object> updateById(Room room) {
        roomService.updateById(room);
        return new ResultUtil<Object>().setSuccessMessage("修改成功");
    }

    @DeleteMapping("/delByIds/{ids}")
    public Result<Object> delByIds(@PathVariable("ids") String[] ids) {
        boolean flag = roomService.delByIds(ids);
        if (flag) {
            return new ResultUtil<Object>().setSuccessMessage("删除成功");
        } else {
            return new ResultUtil<Object>().setErrorMessage("删除失败，宿舍正在被使用");
        }
    }




}
