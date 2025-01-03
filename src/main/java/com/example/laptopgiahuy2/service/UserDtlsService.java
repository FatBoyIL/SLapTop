package com.example.laptopgiahuy2.service;

import com.example.laptopgiahuy2.model.UserDtls;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserDtlsService{
     UserDtls saveUserDtls(UserDtls userDtls);
     UserDtls getUserDtlsByEmail(String email);
     List<UserDtls> getAllUserDtls(String role);
     Boolean updateUserDtlsActicve(Integer id,Boolean status);
     public void increaseFailedAttempts(UserDtls userDtls);
     public void userAccountLock(UserDtls userDtls);
     public boolean unclockAccountTimeExpired(UserDtls userDtls);
     public void resetAttempts(int userId);
     void updateUserResetToken(String email, String resetToken);
     UserDtls getUserByToken(String token);
     UserDtls updateUserDtls(UserDtls userDtls);
     UserDtls updateUserProfile(UserDtls userDtls, MultipartFile img);
}
