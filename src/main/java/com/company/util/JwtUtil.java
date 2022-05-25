package com.company.util;

import com.company.dto.ProfileJwtDTO;
import com.company.enums.ProfileRole;
import com.company.exception.AppForbiddenException;
import com.company.exception.TokenNotValidException;
import io.jsonwebtoken.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtil {
    private final static String secretKey = "kalitso'z";

    public static String encode(Integer id, ProfileRole role) {
        return doEncode(id, role, 10);
    }

    public static String encode(Integer id) {
        return doEncode(id, null, 5);
    }

    public static String doEncode(Integer id, ProfileRole role, long minute) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setSubject(String.valueOf(id));
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secretKey);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (minute * 60 * 1000)));
        jwtBuilder.setIssuer("production");

        if (role != null) {
            jwtBuilder.claim("role", role);
        }

        String jwt = jwtBuilder.compact();
        return jwt;
    }

    public static ProfileJwtDTO decode(String jwt) {
        JwtParser jwtParser = Jwts.parser();

        jwtParser.setSigningKey(secretKey);
        Jws<Claims> jws = jwtParser.parseClaimsJws(jwt);

        Claims claims = jws.getBody();
        String id = claims.getSubject();
        String role = String.valueOf(claims.get("role"));

        return new ProfileJwtDTO(Integer.parseInt(id), ProfileRole.valueOf(role));
    }

    public static Integer decodeAndGetId(String jwt) {
        JwtParser jwtParser = Jwts.parser();

        jwtParser.setSigningKey(secretKey);
        Jws<Claims> jws = jwtParser.parseClaimsJws(jwt);

        Claims claims = jws.getBody();
        String id = claims.getSubject();

        return Integer.parseInt(id);
    }

    public static String getIdFromHeader(HttpServletRequest request, ProfileRole... requiredRoles) {
        try {
            ProfileJwtDTO dto = (ProfileJwtDTO) request.getAttribute("profileJwtDto");
            if (requiredRoles == null || requiredRoles.length == 0) {
                return dto.getId().toString();
            }
            for (ProfileRole role : requiredRoles) {
                if (role.equals(dto.getRole())) {
                    return dto.getId().toString();
                }
            }
        } catch (RuntimeException e) {
            throw new TokenNotValidException("Not Authorized");
        }
        throw new AppForbiddenException("Not Access2");
    }

    public static ProfileJwtDTO getProfileFromHeader(HttpServletRequest request, ProfileRole... requiredRoles) {
        try {
            ProfileJwtDTO dto = (ProfileJwtDTO) request.getAttribute("profileJwtDto");
            if (requiredRoles == null || requiredRoles.length == 0) {
                return dto;
            }
            for (ProfileRole role : requiredRoles) {
                if (role.equals(dto.getRole())) {
                    return dto;
                }
            }
        } catch (RuntimeException e) {
            throw new TokenNotValidException("Not Authorized");
        }
        throw new AppForbiddenException("Not Access");
    }
}
