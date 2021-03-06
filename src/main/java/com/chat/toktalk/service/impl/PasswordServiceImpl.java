package com.chat.toktalk.service.impl;

import com.chat.toktalk.domain.PasswordResetToken;
import com.chat.toktalk.domain.User;
import com.chat.toktalk.repository.PasswordResetTokenRepository;
import com.chat.toktalk.repository.UserRepository;
import com.chat.toktalk.service.PasswordService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PasswordServiceImpl implements PasswordService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public PasswordServiceImpl(UserRepository userRepository, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }



    @Override
    @Transactional(readOnly = true)
    public PasswordResetToken findByUser(User user) {
       return passwordResetTokenRepository.findByUser(user);
    }

    @Override
    @Transactional
    public void savePasswordResetToken(PasswordResetToken passwordResetToken){
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    @Transactional(readOnly = true)
    public PasswordResetToken findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    @Transactional
    public void deletepasswordResetToken(PasswordResetToken passwordResetToken){
        passwordResetTokenRepository.delete(passwordResetToken);
    }

    @Override
    @Transactional
    public void savePassword(User user, String password){
        user.setPassword(passwordEncoder.encode(password));
    }
}
