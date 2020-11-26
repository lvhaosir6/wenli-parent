package cn.lvhaosir.browser.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.pojo.vo.condition.DateSearchVo;
import cn.lvhaosir.browser.service.LogService;
import cn.lvhaosir.browser.utils.PageUtil;
import cn.lvhaosir.core.dao.LogDao;
import cn.lvhaosir.core.pojo.po.Log;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lvhaosir on 2018/10/7.
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    @Override
    public Page<Log> findAll(PageVo pageVo) {
        Pageable pageable = PageUtil.initPage(pageVo);
        Page<Log> all = logDao.findAll(pageable);
        return all;
    }

    @Override
    public Page<Log> searchLog(String key, DateSearchVo dateSearchVo, PageVo pageVo) {

        Pageable pageable = PageUtil.initPage(pageVo);

        Page<Log> page = logDao.findAll(new Specification<Log>() {
            @Override
            public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                Path<String> requestUrlField = root.get("requestUrl");
                Path<String> requestTypeField = root.get("requestType");
                Path<String> requestParamField = root.get("requestParam");
                Path<String> usernameField = root.get("username");
                Path<String> ipField = root.get("ip");
                Path<String> ipInfoField = root.get("ipInfo");
                Path<Date> createTimeField = root.get("createTime");

                List<Predicate> list = new ArrayList<Predicate>();

                // 模糊搜索
                if (StringUtils.isNotBlank(key)) {
                    Predicate p1 = criteriaBuilder.like(requestUrlField, '%' + key + '%');
                    Predicate p2 = criteriaBuilder.like(requestTypeField, '%' + key + '%');
                    Predicate p3 = criteriaBuilder.like(requestParamField, '%' + key + '%');
                    Predicate p4 = criteriaBuilder.like(usernameField, '%' + key + '%');
                    Predicate p5 = criteriaBuilder.like(ipField, '%' + key + '%');
                    Predicate p6 = criteriaBuilder.like(ipInfoField, '%' + key + '%');

                    list.add(criteriaBuilder.or(p1, p2, p3, p4, p5, p6));
                }

                // 创建时间
                String startDate = dateSearchVo.getStartDate();
                String endDate = dateSearchVo.getEndDate();
                if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
                    DateTime start = DateUtil.parse(startDate);
                    DateTime end = DateUtil.parse(endDate);
                    list.add(criteriaBuilder.between(createTimeField, start, DateUtil.endOfDay(end)));
                }

                Predicate[] arr = new Predicate[list.size()];
                query.where(list.toArray(arr));
                return null;
            }
        }, pageable);
        return page;
    }
}
