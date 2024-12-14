package com.example.laptopgiahuy2.config;


import com.example.laptopgiahuy2.model.UserDtls;
import com.example.laptopgiahuy2.repository.UserDtlsRepository;
import com.example.laptopgiahuy2.service.UserDtlsService;
import com.example.laptopgiahuy2.util.AppConstant;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
	private UserDtlsRepository userRepository;

	@Autowired
	private UserDtlsService userService;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		String email = request.getParameter("username");

		UserDtls userDtls = userRepository.findByEmail(email);

		if (userDtls != null) {

			if (userDtls.getHoatDong()) {

				if (userDtls.getAccountNonLocked()) {

					if (userDtls.getFailedAttempts() < AppConstant.ATTEMPT_TIME) {
						userService.increaseFailedAttempts(userDtls);
					} else {
						userService.userAccountLock(userDtls);
						exception = new LockedException("Tài khoản của bạn đã bị khóa.Hãy cố gắng nhập đúng tài khoản để được mở khóa");
					}
				} else {

					if (userService.unclockAccountTimeExpired(userDtls)) {
						exception = new LockedException("Tài khoản của bạn đã được mở khóa xin mời đăng nhặp");
					} else {
						exception = new LockedException("Tài khoản của bạn đã bị khóa xin mời đăng nhặp sau 1 thời gian");
					}
				}

			} else {
				exception = new LockedException("Tài khoản của bạn đang bị khóa liên hệ với admin!!!");
			}
		} else {
			exception = new LockedException("Tài khoản hoặc mật khẩu không hợp lệ");
		}

		super.setDefaultFailureUrl("/signing?error");
		super.onAuthenticationFailure(request, response, exception);
	}

}
