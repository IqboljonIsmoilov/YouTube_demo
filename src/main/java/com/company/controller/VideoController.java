package com.company.controller;

import com.company.service.VideoService;
import com.company.util.JwtUtil;
import dto.VideoAboutDTO;
import dto.VideoDTO;
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
@RequestMapping("/video")
@Api(tags = "video")
public class VideoController {


    private final VideoService videoService;

    @ApiOperation(value = "Create", notes = "Method used for create video",
            authorizations = @Authorization(value = "JWT Token"))
    @PostMapping("/public")
    public ResponseEntity<?> create(@RequestBody @Valid VideoDTO requestDTO,
                                    HttpServletRequest request) {
        log.info("CREATE {}{}", requestDTO, VideoController.class);
        return ResponseEntity.ok(videoService.create(requestDTO, JwtUtil.getIdFromHeader(request)));
    }


    @ApiOperation(value = "update Video Detail ", notes = "Method used for updateVideoDetail",
            authorizations = @Authorization(value = "JWT Token"))
    @PutMapping("/public/{videoId}")
    public ResponseEntity<?> updateVideoDetail(@RequestBody @Valid VideoAboutDTO updateDTO,
                                               @PathVariable("videoId") String videoId,
                                               HttpServletRequest request) {
        log.info("UPDATE about {}{}", updateDTO, VideoController.class);
        return ResponseEntity.ok(videoService.updateVideoDetail(updateDTO, videoId, JwtUtil.getIdFromHeader(request)));
    }


    @ApiOperation(value = "Delete", notes = "Method used for delete video only owner delete own videos",
            authorizations = @Authorization(value = "JWT Token"))
    @DeleteMapping("/public/{videoId}/delete")
    public ResponseEntity<?> delete(@PathVariable("videoId") String videoId,
                                    HttpServletRequest request) {
        log.info("Delete: {}{}", videoId, VideoController.class);
        return ResponseEntity.ok(videoService.delete(videoId, JwtUtil.getIdFromHeader(request)));
    }
}