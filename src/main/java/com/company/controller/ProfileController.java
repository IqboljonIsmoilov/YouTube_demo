package com.company.controller;

import com.company.enums.ProfileRole;
import com.company.service.ProfileService;
import com.company.util.JwtUtil;
import dto.ProfileDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/profile")
@Api(tags = "Profile")
public class ProfileController {

    private final ProfileService profileService;


    @ApiOperation(value = "Get", notes = "Method used for get profile info")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        return ResponseEntity.ok(profileService.get(id));
    }


    @ApiOperation(value = "Create", notes = "Method used for create",
            authorizations = @Authorization(value = "JWT Token"))
    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody @Valid ProfileDTO requestDTO,
                                    HttpServletRequest request) {
        log.info("CREATE {}{}", requestDTO, ProfileController.class);
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.create(requestDTO));
    }


    @ApiOperation(value = "updateEmail", notes = "Mathod used for updateEmail")
    @PutMapping("/adm/update{email}")
    public ResponseEntity<?> updateEmail(@RequestBody @Valid ProfileDTO updateDTO,
                                         HttpServletRequest request) {
        log.info("Profile_update: {}{}", updateDTO, ProfileController.class);
        return ResponseEntity.ok(profileService.updateEmail(updateDTO, JwtUtil.getIdFromHeader(request)));
    }


    @ApiOperation(value = "updateProfileDetali", notes = "Mathod used for updateProfileDetali")
    @PutMapping("/updateProfileDetali")
    public ResponseEntity<?> updateProfileDetali(@RequestBody @Valid ProfileDTO updateDTO,
                                                 HttpServletRequest request) {
        String id = JwtUtil.getIdFromHeader(request);
        log.info("Profile_update: {}{}", updateDTO, ProfileController.class);
        return ResponseEntity.ok(profileService.updateProfileDetail(updateDTO, id));
    }


    @ApiOperation(value = "updateProfileAttach", notes = "Mathod used for updateProfileAttach", nickname = "nicname")
    @PutMapping("/updateProfileAttach")
    public ResponseEntity<?> updateProfileAttach(@PathVariable("id") String id,
                                                 HttpServletRequest request) {
        String pId = JwtUtil.getIdFromHeader(request);
        log.info("Profile_update: {}");
        return ResponseEntity.ok(profileService.updateProfileAttach(pId, id));
    }


    @ApiOperation(value = "List", notes = "Method used for get list of profiles",
            authorizations = @Authorization(value = "JWT Token"))
    @GetMapping("/adm")
    public ResponseEntity<?> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "5") int size,
                                  HttpServletRequest request) {
        log.info("LIST page={} size={}", page, size);
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.list(page, size));
    }
}