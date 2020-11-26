package cn.lvhaosir.browser.security.permission;

import cn.lvhaosir.browser.service.PermissionService;
import cn.lvhaosir.core.constant.EntityConstant;
import cn.lvhaosir.core.dao.PermissionDao;
import cn.lvhaosir.core.pojo.po.Permission;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.*;

@Slf4j
@Component
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private PermissionService permissionService;

    private Map<String, Collection<ConfigAttribute>> map = null;


    private void loadResourceDefine() {
        map = new HashMap<>();
        Collection<ConfigAttribute> configAttributes;
        ConfigAttribute cfg;
        // 获取启用的权限操作
        List<Permission> permissions = permissionService.queryByTypeAndStatusOrderBySortOrder(EntityConstant.PERMISSION_OPERATION, EntityConstant.STATUS_NORMAL);
        // log.info("perimission:"+permissions);
        for (Permission permission : permissions) {
            if (StringUtils.isNotBlank(permission.getTitle()) &&
                 StringUtils.isNotBlank(permission.getPath())) {
                configAttributes = new ArrayList<>();
                cfg = new SecurityConfig(permission.getTitle());
                // 作为MyAccessDecisionManager类的decide的第三个参数
                configAttributes.add(cfg);
                // 用权限的path作为map的key，用ConfigAttribute的集合作为value
                map.put(permission.getPath(), configAttributes);
            }
        }

    }

    /**
     *   判定用户请求url是否在权限表中
     *   如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限
     *   如果不在权限表中，直接放行
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (map == null) {
            loadResourceDefine();
        }
        //log.info("## MySecurityMetadataSource #####");
        // Object中包含用户请求 request
        String url = ((FilterInvocation) object).getRequestUrl();
        // log.info("url:"+ url);
        PathMatcher pathMatcher = new AntPathMatcher();
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String resURL = iterator.next();
            // log.info("resURL:"+ resURL);
            // log.info("pathMatcher.match(resURL, url):"+pathMatcher.match(resURL, url));
            if (StringUtils.isNotBlank(resURL) && pathMatcher.match(resURL, url)) {
                log.info("map.get(resURL):"+ map.get(resURL));
                return map.get(resURL);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
