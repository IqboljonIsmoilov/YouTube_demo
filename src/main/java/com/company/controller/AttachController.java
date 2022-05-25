package com.company.controller;

import com.company.enums.ProfileRole;
import com.company.service.AttachService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/attach")
@Api(tags = "Atach")
public class AttachController {

    @Autowired
    private AttachService attachService;

    private Logger log = LoggerFactory.getLogger(AttachController.class);

    @ApiOperation(value = "upload", notes = "Mathod used for upload", nickname = "nickname")
    @PostMapping("/upload")
    public ResponseEntity<?> create(@RequestParam("file") MultipartFile file) {
        log.info("/upload");
        return ResponseEntity.ok(attachService.upload(file));
    }


    @ApiOperation(value = "Open", notes = "Method used for open files")
    @GetMapping(value = "/open/{id}", produces = MediaType.ALL_VALUE)
    public byte[] open(@PathVariable("id") String id) {
        log.info("/open/{id} {}", id);
        return attachService.open(id);
    }


    @ApiOperation(value = "Download", notes = "Method used for download files")
    @GetMapping("/download/{id}")
    public ResponseEntity<?> download(@PathVariable("id") String id) {
        log.info("/download/{id} {}", id);
        return attachService.download(id);
    }


    @ApiOperation(value = "List", notes = "Method used for get list",
            authorizations = @Authorization(value = "JWT Token"))
    @GetMapping("/adm")
    public ResponseEntity<?> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "3") int size,
                                  HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(attachService.pagination(page, size));
    }


    @ApiOperation(value = "Delete", notes = "Method used for delete",
            authorizations = @Authorization(value = "JWT Token"))
    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id,
                                    HttpServletRequest request) {
        log.info("DELETE {}", id);
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(attachService.delete(id));
    }
}