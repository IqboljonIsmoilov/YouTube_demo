package com.company.controller;

import com.company.dto.TagDTO;
import com.company.enums.ProfileRole;
import com.company.service.TagService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tag")
@Api(tags = "Tag")
public class TagController {

    private final TagService tagService;
    private Logger log = LoggerFactory.getLogger(TagController.class);

    @ApiOperation(value = "create", notes = "Mathod used for create", nickname = "nicname")
    @PostMapping("/public")
    public ResponseEntity<?> create(@RequestBody @Valid TagDTO dto) {
        log.info("Tag_create: {}", dto);
        return ResponseEntity.ok(tagService.create(dto));
    }


    @ApiOperation(value = "Update", notes = "Method used for update tag",
            authorizations = @Authorization(value = "JWT Token"))
    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id,
                                    @RequestBody @Valid TagDTO dto,
                                    HttpServletRequest request) {
        log.info("UPDATE {}", dto);
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(tagService.update(id, dto));
    }


    @ApiOperation(value = "Delete", notes = "Method used for delete tag",
            authorizations = @Authorization(value = "JWT Token"))
    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id,
                                    HttpServletRequest request) {
        log.info("DELETE {}", id);
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(tagService.delete(id));
    }


    @ApiOperation(value = "List", notes = "Method used for get list of tags")
    @GetMapping("/public")
    public ResponseEntity<?> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "5") int size) {
        log.info("LIST page={} size={}", page, size);
        return ResponseEntity.ok(tagService.list(page, size));
    }
}