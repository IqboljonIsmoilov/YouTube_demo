package com.company.controller;

import com.company.dto.VideoAboutDTO;
import com.company.dto.VideoDTO;
import com.company.service.VideoService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/video")
@Api(tags = "video")
public class VideoController {

    @Autowired
    private VideoService videoService;
    private Logger log = LoggerFactory.getLogger(VideoController.class);


    @ApiOperation(value = "Create", notes = "Method used for create video",
            authorizations = @Authorization(value = "JWT Token"))
    @PostMapping("/public")
    public ResponseEntity<?> create(@RequestBody @Valid VideoDTO dto,
                                    HttpServletRequest request) {
        log.info("CREATE {}", dto);
        return ResponseEntity.ok(videoService.create(dto, JwtUtil.getIdFromHeader(request)));
    }


    @ApiOperation(value = "updateVideoDetail ", notes = "Method used for updateVideoDetail",
            authorizations = @Authorization(value = "JWT Token"))
    @PutMapping("/public/{videoId}")
    public ResponseEntity<?> updateVideoDetail(@RequestBody @Valid VideoAboutDTO dto,
                                               @PathVariable("videoId") String videoId,
                                               HttpServletRequest request) {
        log.info("UPDATE about {}", dto);
        return ResponseEntity.ok(videoService.updateVideoDetail(dto, videoId, JwtUtil.getIdFromHeader(request)));
    }


    @ApiOperation(value = "Delete", notes = "Method used for delete video only owner delete own videos",
            authorizations = @Authorization(value = "JWT Token"))
    @DeleteMapping("/public/{videoId}/delete")
    public ResponseEntity<?> delete(@PathVariable("videoId") String videoId,
                                    HttpServletRequest request) {
        log.info("/public/{videoId}/delete {}", videoId);
        return ResponseEntity.ok(videoService.delete(videoId, JwtUtil.getIdFromHeader(request)));
    }


}
