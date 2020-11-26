package cn.lvhaosir.core.aop;

import cn.lvhaosir.core.dao.LogDao;
import cn.lvhaosir.core.pojo.po.Log;
import cn.lvhaosir.core.utils.IpInfoUtil;
import cn.lvhaosir.core.utils.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import cn.lvhaosir.core.annotation.SystemControllerLog;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * Created by lvhaosir on 2018/10/6.
 */
@Slf4j
@Aspect
@Component
public class SystemControllerLogAspect {

    private static final ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal<Date>("ThreadLocal beginTime");

    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    private IpInfoUtil ipInfoUtil;

    @Autowired
    private LogDao logDao;


    @Pointcut("@annotation(cn.lvhaosir.core.annotation.SystemControllerLog)")
    public void controllerAspect() {
    }

    /**
     * 前置通知 (在方法执行之前返回)
     * 用于拦截Controller层记录用户的操作的开始时间
     *
     * @param joinPoint
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        //线程绑定变量（该数据只有当前请求的线程可见）
        Date beginTime=new Date();
        beginTimeThreadLocal.set(beginTime);
    }

    /**
     * 后置通知(在方法执行之后返回)
     * 用于拦截Controller层操作
     *
     * @param joinPoint
     */
    @After("controllerAspect()")
    public void doAfter(JoinPoint joinPoint) {
        try {
            UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = user.getUsername();

            if (StringUtils.isNotBlank(username)) {
                // 用户名存在
                Log log = new Log();
                // 获取日志标题
                log.setName(getDescription(joinPoint));
                // URL
                log.setRequestUrl(request.getRequestURI());
                // 请求方式
                log.setRequestType(request.getMethod());
                // 请求参数
                Map<String, String[]> logParams = request.getParameterMap();
                log.setMapToParams(logParams);
                // 请求用户
                log.setUsername(username);
                // 请求IP
                String ipAddr = ipInfoUtil.getIpAddr(request);
                log.setIp(ipAddr);
                // IP地址
                log.setIpInfo(ipInfoUtil.getIpCity(ipAddr));
                // 请求开始时间
                long beginTime = beginTimeThreadLocal.get().getTime();
                long endTime = System.currentTimeMillis();
                // 请求耗时
                Long logElapsedTime = endTime - beginTime;
                log.setCostTime(logElapsedTime.intValue());

                // 调用线程报错
                ThreadPoolUtil.getPool().execute(new SaveSystemLogThread(log,logDao));
            }
        } catch (Exception e) {
            log.error("SystemControllerLogAspect中doAfter后置通知异常",e);
        }
    }

    /**
     * 获取注解中的描述
     *
     * @param joinPoint
     * @return
     * @throws ClassNotFoundException
     */
    private String getDescription(JoinPoint joinPoint) throws ClassNotFoundException {

        // 获取目标类名
        String targetName = joinPoint.getTarget().getClass().getName();
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 获取相关
        Object[] arguments = joinPoint.getArgs();
        // 生成类对象
        Class targetClass = Class.forName(targetName);
        // 获取该类中的方法
        Method[] methods = targetClass.getMethods();

        String description = "";

        for (Method method : methods) {
            if (!method.getName().equals(methodName)) {
                continue;
            }
            Class<?>[] clazzs = method.getParameterTypes();
            // 因为可能有方法重载
            if (clazzs.length != arguments.length) {
                continue;
            }
            description = method.getAnnotation(SystemControllerLog.class).description();
        }
        return description;
    }

    /**
     *  保存 日志信息
     */
    private class SaveSystemLogThread implements Runnable {
        private Log log;
        private LogDao logDao;

        public SaveSystemLogThread(Log log,LogDao logDao) {
            this.log = log;
            this.logDao = logDao;
        }

        @Override
        public void run() {
            logDao.save(log);
        }
    }

}
