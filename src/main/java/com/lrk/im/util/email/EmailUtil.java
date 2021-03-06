package com.lrk.im.util.email;

import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {

	public static String myEmailAccount = "colaim@qq.com";
    public static String myEmailPassword = "sftdzoqnhmutjjih";
 
    // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
    // 腾讯企业邮箱的 SMTP 服务器地址为: smtp.163.com
    public static String myEmailSMTPHost = "smtp.qq.com";
	
    public static void sendEmail(String receiveEmail,String account) throws Exception {
    	// 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");// 需要请求认证
        props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getDefaultInstance(props);
        session.setDebug(false);
        // 3. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        // 4. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
        message.setFrom(new InternetAddress(myEmailAccount, "colaim", "UTF-8"));
        // 5. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveEmail, "用户", "UTF-8"));
        // 6. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
        message.setSubject("欢迎注册", "UTF-8");
        // 7. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
        message.setContent("<div><h2>&nbsp;&nbsp;&nbsp;&nbsp;商户您好,您的支付功能已经审核通过</h2><p style=\"margin-top: 20px;\">&nbsp;&nbsp;&nbsp;&nbsp;查询账户流水和到账情况请在口腔管家公众号内点击历史记录查询按钮进行查看，您的登陆账号为:"+account+",请妥善保管，密码默认为注册时填写的手机号。</p><p>&nbsp;&nbsp;&nbsp;&nbsp;感谢您的使用!</p></div>", "text/html;charset=UTF-8");
        // 8. 设置发件时间
        message.setSentDate(new Date());
        // 9. 保存设置
        message.saveChanges();
        // 10. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();
        transport.connect(myEmailAccount, myEmailPassword);
        // 11. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());
        // 12. 关闭连接
        transport.close();
	}
    
}
