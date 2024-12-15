package com.example.laptopgiahuy2.service.Implement;

import com.example.laptopgiahuy2.model.UserDtls;
import com.example.laptopgiahuy2.repository.UserDtlsRepository;
import com.example.laptopgiahuy2.service.UserDtlsService;
import com.example.laptopgiahuy2.util.AppConstant;
import jakarta.mail.Multipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserDtlsServiceImpl implements UserDtlsService {
    @Autowired
    private UserDtlsRepository userDtlsRepository;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDtls saveUserDtls(UserDtls userDtls) {
        userDtls.setRole("ROLE_USER");
        userDtls.setHoatDong(true);
        userDtls.setAccountNonLocked(true);
        userDtls.setFailedAttempts(0);

        String encodedPassword = passwordEncoder.encode(userDtls.getPassword());
        userDtls.setPassword(encodedPassword);
        UserDtls savedUserDtls = userDtlsRepository.save(userDtls);
        return savedUserDtls;
    }

    @Override
    public UserDtls getUserDtlsByEmail(String email) {

        return userDtlsRepository.findByEmail(email);
    }

    @Override
    public List<UserDtls> getAllUserDtls(String role) {

        return userDtlsRepository.findByRole(role);
    }

    @Override
    public Boolean updateUserDtlsActicve(Integer id, Boolean status) {
        Optional<UserDtls> userDtls = userDtlsRepository.findById(id);
        if(userDtls.isPresent()) {
            UserDtls userDtls1 = userDtls.get();
            userDtls1.setHoatDong(status);
            userDtlsRepository.save(userDtls1);
            return true;
        }
        return false;
    }
//han che tai khoan khi nhap sai mk nhieu lan
    @Override
    public void increaseFailedAttempts(UserDtls userDtls) {
        int attempts = userDtls.getFailedAttempts()+1;
        userDtls.setFailedAttempts(attempts);
        userDtlsRepository.save(userDtls);
    }

    @Override
    public void userAccountLock(UserDtls userDtls) {
        userDtls.setAccountNonLocked(false);
        userDtls.setLockTime(new Date());
        userDtlsRepository.save(userDtls);
    }

    @Override
    public boolean unclockAccountTimeExpired(UserDtls userDtls) {
        long lockTime = userDtls.getLockTime().getTime();
        long unlockTime = lockTime - AppConstant.UNCLOCK_DURATION_TIME;
        long currentTime = System.currentTimeMillis();
        if(currentTime > unlockTime) {
            userDtls.setAccountNonLocked(true);
            userDtls.setFailedAttempts(0);
            userDtls.setLockTime(null);
            userDtlsRepository.save(userDtls);
            return true;
        }
        return false;
    }

    @Override
    public void resetAttempts(int userId) {

    }
    @Override
    public void updateUserResetToken(String email, String resetToken) {
        UserDtls findByEmail = userDtlsRepository.findByEmail(email);
        findByEmail.setResetToken(resetToken);
        userDtlsRepository.save(findByEmail);
    }

    @Override
    public UserDtls getUserByToken(String token) {

        return userDtlsRepository.findByResetToken(token);
    }

    @Override
    public UserDtls updateUserDtls(UserDtls userDtls) {
        return userDtlsRepository.save(userDtls);
    }

    @Override
    public UserDtls updateUserProfile(UserDtls userDtls, MultipartFile img) {
        UserDtls dbUser = userDtlsRepository.findById(userDtls.getUserId()).get();

        if (!img.isEmpty()) {
            dbUser.setProfileImage(img.getOriginalFilename());
        }

        if (!ObjectUtils.isEmpty(dbUser)) {

            dbUser.setUserName(userDtls.getUserName());
            dbUser.setSdt(userDtls.getSdt());
            dbUser.setDiachi(userDtls.getDiachi());
            dbUser = userDtlsRepository.save(dbUser);
        }
        try {
            if (!img.isEmpty()) {
                File saveFile = new ClassPathResource("static/img").getFile();

                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "profile_img" + File.separator
                        + img.getOriginalFilename());

//			System.out.println(path);
                Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dbUser;
    }

}
