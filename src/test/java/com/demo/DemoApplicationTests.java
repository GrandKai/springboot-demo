package com.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Environment env;

	@Autowired
	private SpringTemplateEngine engine;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private ServletContext servletContext;




	//	private String[] to = new String[] {"1048352511@qq.com", "446223455@qq.com"};
	private String[] to = new String[] {"446223455@qq.com"};
	private String from;

	@Before
	public void set() {
		from = env.getProperty("spring.mail.username");
		ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
		resolver.setPrefix("mail/");
		resolver.setTemplateMode("HTML5");
		resolver.setCharacterEncoding("UTF-8");
		resolver.setOrder(1);

		engine.setTemplateResolver(resolver);
	}

	@Test
	public void testSimpleMailMessage() {


		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setSubject("你有新的消息请注意查收");
		message.setText("hello world");
		message.setTo(to);

		mailSender.send(message);
	}

	@Test
	public void testSendMimeMessage() throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		FileSystemResource resource = new FileSystemResource("/home/zhangyn/Pictures/1.jpg");
		helper.setSubject("你有新的消息请注意查收");
		helper.setText("hello world");

		helper.setFrom(from);
		helper.setTo(to);
//		发送附件
//		helper.addAttachment("大妞儿.jpg", resource);
//		图片直接显示在富文本中
 		helper.setText("<html><body><img src='cid:logo'>" +
				"<h4>大妞儿 says...</h4>" +
				"<i>欢姐棒棒哒</i>" +
				"</body></html>", true);
		helper.addInline("logo", resource);
		mailSender.send(message);
	}

	@Test
	public void testSendRichMessage() throws MessagingException {
		WebContext context = new WebContext(request, response, servletContext);
//		Context context = new Context();

		context.setVariable("name", "测试");
		context.setVariable("text", "吧啦吧啦吧啦吧啦");
		String text = engine.process("template.html", context);

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setSubject("你有新的消息请注意查收");
		helper.setText("hello world");

		helper.setFrom(from);
		helper.setTo(to);
		helper.setText(text, true);
		mailSender.send(message);

	}
}
