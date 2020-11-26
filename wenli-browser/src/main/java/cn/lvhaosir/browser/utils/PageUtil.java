package cn.lvhaosir.browser.utils;

import cn.hutool.core.util.StrUtil;
import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtil {

    /**
     * 分页数据初始化
     *
     * @param page
     * @return
     */
    public static Pageable initPage(PageVo page) {

        Pageable pageable = null;
        // 页号
        int pageNumber = page.getPageNumber();
        // 页面展示数据数量
        int pageSize = page.getPageSize();
        // 排序字段
        String sort = page.getSort();
        // 排序方式
        String order = page.getOrder();
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        if (pageSize < 1) {
            // 数据量默认为 10
            pageSize = 10;
        }
        if (StringUtils.isNotBlank(sort)) {
            Sort.Direction d;
            if (StrUtil.isBlank(order)) {
                d = Sort.Direction.DESC;
            } else {
                d = Sort.Direction.valueOf(order.toUpperCase());
            }
            Sort s = new Sort(d, sort);
//            pageable =new PageRequest(pageNumber-1, pageSize,s);
            // 如果是 SpringBoot 2.x 则为 PageRequest.of
            pageable = PageRequest.of(pageNumber - 1, pageSize, s);
        } else {
//            pageable = new PageRequest(pageNumber-1, pageSize);
            pageable = PageRequest.of(pageNumber - 1, pageSize);
        }
        return pageable;
    }
}