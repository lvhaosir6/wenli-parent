package cn.lvhaosir.app.service.common;

import cn.lvhaosir.app.pojo.vo.StudentVo;
import cn.lvhaosir.app.service.StudentService;
import cn.lvhaosir.app.service.WeekTextService;
import cn.lvhaosir.core.pojo.po.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by lvhaosir on 2018/10/23.
 */
@Slf4j
@Service
public class MailSender implements InitializingBean {

    private JavaMailSenderImpl mailSender;

    @Autowired
    private StudentService studentService;

    @Autowired
    private WeekTextService weekTextService;



    @Override
    public void afterPropertiesSet() throws Exception {

        mailSender = new JavaMailSenderImpl();
        mailSender.setUsername("985694388@qq.com");
        mailSender.setPassword("yoqmpkijjnyhbedg");
        mailSender.setHost("smtp.qq.com");
        Properties prop = new Properties();

        //服务器进行认证
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.timeout", "20000");
        //邮箱发送服务器端口,这里设置为465端口
        prop.setProperty("mail.smtp.port", "465");
        prop.setProperty("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.ssl.enable", "true");

        mailSender.setJavaMailProperties(prop);

        mailSender.setJavaMailProperties(prop);
    }

    /**
     *  意见反馈的邮件发送
     * @param content
     * @param cardId
     * @return
     */
    public boolean sendFeedback(String content, String cardId) {
        StudentVo studentVo = studentService.queryByUsername(cardId);
        this.sendSimpleMail("985694388@qq.com","意见反馈",content + studentVo.print());
        this.sendSimpleMail("heyxhazx1003@163.com","意见反馈",content +studentVo.print());
        return true;
    }

    /**
     *  根据周记id，发送详细信息
     * @param id
     * @return
     */
    public boolean sendUrgencyWeekText(String id) {
        String content = weekTextService.printUrgencyWeekText(id);
        this.sendSimpleMail("106992947@qq.com","异常情况反映",content);
        this.sendSimpleMail("heyxhazx1003@163.com","异常情况反映",content);
        return true;
    }

    public boolean sendSimpleMail(String to, String subject, String content){
        MimeMessage message = null;
        boolean flag = true;
        try {
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            // 邮件发送者
            helper.setFrom("985694388@qq.com");
            // 邮件接收者
            helper.setTo(to);
            // 邮件主题
            helper.setSubject(subject);
            helper.setText(content);
            mailSender.send(message);
        } catch (Exception e) {
            flag = false;
            log.error("系统异常邮件发送异常:{}", e);
            e.printStackTrace();
        }
        return flag;
    }
}
