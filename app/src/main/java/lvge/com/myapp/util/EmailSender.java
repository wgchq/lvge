package lvge.com.myapp.util;

/**
 * Created by JGG on 2017-09-08.
 */
import android.util.Log;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
public class EmailSender {
    private static final String defaultSMTPHost = "smtp.126.com";
    private static final String defaultMailFromName = "lvgeservice@126.com";//以这个邮箱地址的名义发送邮件,改成使用者的邮箱
    private static final String defaultMailFromPassword = "lvge2017";//上面那个名义上的邮件的邮箱登入密码,改成使用者的邮箱密码,我博文里给的当然是错误密码.

    public static int send(String mailTos, String mailSubject, String mailText) {
        // check params
        if (mailTos == null || mailSubject == null || mailText == null)
            return 0;

        // encoding params
        String encoding = "iso-8859-1";

        try {
            // 标题不需要编码,编码后为乱码
            // 正文需要编码
            mailText = new String(mailText.getBytes(), encoding);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Fail to encoding mail text to " + encoding);
        }

        Properties props = new Properties();
        props.put("mail.smtp.host", defaultSMTPHost);// 邮件服务器地址
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.user", "CTOPAY");// 发送方的发送名;
        props.put("mail.smtp.from", defaultMailFromName);// 发送邮箱地址;
        props.put("mail.debug", "false");// 不打印构建发送者信息

        // 构建发送者
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(defaultMailFromName,
                        defaultMailFromPassword);
            }
        };

        Session session = Session.getInstance(props, auth);
        session.setDebug(false);// 不打印发送信息

        /**
         * 一下内容是：发送邮件时添加附件
         */
        MimeBodyPart attachPart = new MimeBodyPart();
        FileDataSource fds = new FileDataSource("/sdcard/crash/exception.log"/*
                                                                             * Environment
                                                                             * .
                                                                             * getExternalStorageDirectory
                                                                             * (
                                                                             * )
                                                                             * +
                                                                             * "/crash/exception.log"
                                                                             */); // 打开要发送的文件
        MimeMultipart allMultipart = null;

        Log.i("TAGOO", "" + fds.getName());



        try {

            attachPart.setDataHandler(new DataHandler(fds));
            attachPart.setFileName(fds.getName());
            allMultipart = new MimeMultipart("mixed"); // 附件

            // 添加邮件正文
            attachPart.setContent(mailText, "text/html;charset=UTF-8");
//            allMultipart.addBodyPart(attachPart);

            allMultipart.addBodyPart(attachPart);// 添加

        } catch (MessagingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        Message msg = new MimeMessage(session);
        int sendNum = 0;// 发送的数量

        for (String mailTo : mailTos.split(",")) {
            try {
                InternetAddress[] addresses = { new InternetAddress(mailTo) };
                msg.setRecipients(Message.RecipientType.TO, addresses);// 设置邮件接收地址集
                msg.setSentDate(new java.util.Date());// 设置邮件发送日期
                msg.setSubject(mailSubject);// 设置邮件的标题
                msg.setText(mailText);// 设置邮件的内容(文本)
                msg.setContent(allMultipart);

                MailcapCommandMap mc = (MailcapCommandMap) CommandMap
                        .getDefaultCommandMap();
                mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
                mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
                mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
                mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
                mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
                CommandMap.setDefaultCommandMap(mc);

                Transport.send(msg);// 发送邮件

                sendNum++;// 发送记数
                System.out.println("Success to send email to " + mailTo);
            } catch (MessagingException e) {
                System.out.println("Fail to send email for:" + e);
            }
        }
        return sendNum;
    }
}
