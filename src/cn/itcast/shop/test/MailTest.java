package cn.itcast.shop.test;

import javax.mail.MessagingException;

import org.junit.Test;

import cn.itcast.shop.utils.MailUtils;

public class MailTest {

	// 测试sss
	@Test
	public void sendMail() {
		String user = "jack@itpower.com";
		String subject = "e-mail测试";
		String emailMsg = "亲爱的jack，您好：这是一封测试邮件！";
		try {
			MailUtils.sendMail(user, subject, emailMsg);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
