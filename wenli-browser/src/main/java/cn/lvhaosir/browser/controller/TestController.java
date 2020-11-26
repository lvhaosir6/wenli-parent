package cn.lvhaosir.browser.controller;

import cn.lvhaosir.core.pojo.vo.Result;
import cn.lvhaosir.core.utils.ResultUtil;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvhaosir on 2018/9/18.
 *
 *  为了一下功能，测试所用
 */
@RestController
public class TestController {



    @RequestMapping("/permission/getMenuList/{id}")
    public Result<List<Permission>> getMenuList(@Param("id") @PathVariable Integer id) {

        List<Permission> menuList = new ArrayList<>();
        // 创建系统管理菜单demo
        Permission sys = new Permission();
        sys.setName("sys");
        sys.setLeavel(1);
        sys.setTitle("系统管理");
        sys.setPath("/form");
        sys.setComponent("Main");
        sys.setIcon("ios-settings");
        sys.setSortOrder(BigDecimal.valueOf(1.0));
        // -- 子类菜单
        List<Permission> sysChildren = new ArrayList<>();
        Permission sysC1 = new Permission();
        sysC1.setName("user-manage");
        sysC1.setLeavel(2);
        sysC1.setTitle("用户管理");
        sysC1.setPath("user-manage");
        sysC1.setComponent("sys/user-manage/userManage");
        sysC1.setIcon("md-person");
        sysC1.setSortOrder(BigDecimal.valueOf(1.10));
        sysC1.setLength(0);
        sysC1.setChildren(null);
        sysChildren.add(sysC1);
        // --
        sys.setChildren(sysChildren);
        sys.setLength(sysChildren.size());

        // 创建系统管理菜单demo
        Permission demo = new Permission();
        demo.setName("dmeo");
        demo.setLeavel(1);
        demo.setTitle("dmeo");
        demo.setPath("/demo");
        demo.setComponent("dmeo");
        demo.setIcon("ios-settings");
        demo.setSortOrder(BigDecimal.valueOf(2.0));
        demo.setChildren(null);
        demo.setLength(0);

        menuList.add(sys);
        menuList.add(demo);


        return new ResultUtil<List<Permission>>().setData(menuList);
    }


    private class Permission {
        // 组件名称
        private String name ;
        // 菜单层级
        private Integer leavel;
        // 菜单名称
        private String title;
        // 路径
        private String path;
        // 组件位置
        private String component;
        // 图标
        private String icon;
        // 排序
        private BigDecimal sortOrder;
        // 子类
        private List<Permission> children;
        private Integer length;

        public Integer getLength() {
            return length;
        }

        public void setLength(Integer length) {
            this.length = length;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getLeavel() {
            return leavel;
        }

        public void setLeavel(Integer leavel) {
            this.leavel = leavel;
        }

        public String getTitle() {
            return title;
        }

        public Permission() {}

        public Permission(String name, Integer leavel, String title, String path, String component, String icon, BigDecimal sortOrder, List<Permission> children) {
            this.name = name;
            this.leavel = leavel;
            this.title = title;
            this.path = path;
            this.component = component;
            this.icon = icon;
            this.sortOrder = sortOrder;
            this.children = children;
        }

        public BigDecimal getSortOrder() {
            return sortOrder;
        }

        public void setSortOrder(BigDecimal sortOrder) {
            this.sortOrder = sortOrder;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getComponent() {
            return component;
        }

        public void setComponent(String component) {
            this.component = component;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public List<Permission> getChildren() {
            return children;
        }

        public void setChildren(List<Permission> children) {
            this.children = children;
        }
    }
}
