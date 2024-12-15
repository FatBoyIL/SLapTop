package com.example.laptopgiahuy2.util;

import com.example.laptopgiahuy2.model.ProductOrder;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.Principal;

@Component
public class CommonUtil {
	@Autowired
	private JavaMailSender mailSender;
	public Boolean sendMail(String url, String reciepentEmail) throws UnsupportedEncodingException, MessagingException {

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("huyclone722@gmail.com", "Shopping Cart");
		helper.setTo(reciepentEmail);

		String content = "<p>Hello,</p>" + "<p>You have requested to reset your password.</p>"
				+ "<p>Click the link below to change your password:</p>" + "<p><a href=\"" + url
				+ "\">Change my password</a></p>";
		helper.setSubject("Password Reset");
		helper.setText(content, true);
		mailSender.send(message);
		return true;
	}

	public static String generateUrl(HttpServletRequest request) {

		// http://localhost:8080/forgot-password
		String siteUrl = request.getRequestURL().toString();

		return siteUrl.replace(request.getServletPath(), "");
	}
	String msg= null;
	public Boolean sendMailProductOrder(ProductOrder productOrder,String status) throws MessagingException, UnsupportedEncodingException {
		msg="<p>Hello [[name]],</p>"
				+ "<p>Thank you order <b>[[orderStatus]]</b>.</p>"
				+ "<p><b>Product Details:</b></p>"
				+ "<p>Name : [[productName]]</p>"
				+ "<p>Category : [[category]]</p>"
				+ "<p>Quantity : [[quantity]]</p>"
				+ "<p>Price : [[price]]</p>"
				+ "<p>Payment Type : [[paymentType]]</p>";
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("huyclone722@gmail.com", "Shopping Cart");
		helper.setTo(productOrder.getOrderAddress().getEmail());

		msg=msg.replace("[[name]]",productOrder.getOrderAddress().getGuestName());
		msg=msg.replace("[[orderStatus]]",status);
		msg=msg.replace("[[productName]]", productOrder.getProduct().getTensanpham());
		msg=msg.replace("[[category]]", productOrder.getProduct().getDanhMuc());
		msg=msg.replace("[[quantity]]", productOrder.getQuantity().toString());
		msg=msg.replace("[[price]]", productOrder.getPrice().toString());
		msg=msg.replace("[[paymentType]]", productOrder.getPaymentType());
		helper.setSubject("Product Order Status");
		helper.setText(msg, true);
		mailSender.send(message);
		return true;
	}

}
