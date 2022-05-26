package com.company.controller;

import com.company.dto.VideoLikeDTO;
import com.company.service.VideoLikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/videoLike")
@Api(tags = "videoLike")
public class VideoLikeController {

    private final VideoLikeService videoLikeService;

    private Logger log = LoggerFactory.getLogger(VideoLikeController.class);

    @ApiOperation(value = "createVideolike", notes = "Mathod used for createVideolike", nickname = "nicname")
    @PostMapping("/")
    public ResponseEntity<?> createVideolike(@RequestBody @Valid VideoLikeDTO dto) {
        log.info("VideoLike_create: {}", dto);
        return ResponseEntity.ok(videoLikeService.createVideolike(dto));
    }


    @ApiOperation(value = "remove", notes = "Mathod used for remove", nickname = "nicname")
    @DeleteMapping("/")
    public ResponseEntity<?> removeVideolike(@RequestBody @Valid VideoLikeDTO dto) {
        //  return ResponseEntity.ok(videoLikeService.removeVideolike(dto));
        return null;
    }
}
