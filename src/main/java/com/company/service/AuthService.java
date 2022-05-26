package com.company.service;

import com.company.dto.AuthDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.RegistrationDTO;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.exception.AppBadRequestException;
import com.company.exception.AppForbiddenException;
import com.company.exception.EmailAlreadyExistsException;
import com.company.exception.PasswordOrEmailWrongException;
import com.company.repository.ProfileRepository;
import com.company.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final ProfileRepository profileRepository;

    private final EmailService emailService;

    private final AttachService attachService;


    public ProfileDTO login(AuthDTO dto) {
        String pswd = DigestUtils.md5Hex(dto.getPassword());
        Optional<ProfileEntity> optional =
                profileRepository.findByEmailAndPassword(dto.getEmail(), pswd);
        if (optional.isEmpty()) {
            throw new PasswordOrEmailWrongException("Password or email wrong!");
        }

        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new AppForbiddenException("No Access");
        }
        ProfileDTO profile = new ProfileDTO();

        profile.setJwt(JwtUtil.encode(Integer.valueOf(entity.getId().toString()), entity.getRole()));
        profile.setEmail(entity.getEmail());
        profile.setName(entity.getName());
        profile.setSurname(entity.getSurname());
        profile.setRole(entity.getRole());

        return profile;
    }


    public String registration(RegistrationDTO dto) {

        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new EmailAlreadyExistsException("Email Already Exits");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());

        String pswd = DigestUtils.md5Hex(dto.getPassword());
        entity.setPassword(pswd);

        entity.setRole(ProfileRole.USER);
        entity.setStatus(ProfileStatus.NOT_ACTIVE);
        profileRepository.save(entity);

        return "Chack your email";
    }


    public String verification(String jwt) {
        Integer userId = null;
        try {
            userId = JwtUtil.decodeAndGetId(jwt);
        } catch (JwtException e) {
            throw new AppBadRequestException("Verification not completed");
        }

        profileRepository.updateStatus(ProfileStatus.ACTIVE, userId.toString());
        return "";
    }


/*
    private void sendVerificationEmail(ProfileEntity entity) {
        StringBuilder builder = new StringBuilder();
        String jwt = JwtUtil.encode(entity.getId());
        builder.append("Salom \n");
        builder.append("To verify your registration click to next link.");
        builder.append("http://localhost:8080/auth/verification/").append(jwt);
        builder.append("\nnimadur!");
        emailService.send(entity.getEmail(), "Activate Your Registration", builder.toString());

    }*/
}