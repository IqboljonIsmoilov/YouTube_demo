package com.company.controller;

import com.company.service.VideoLikeService;
import dto.VideoLikeDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/videoLike")
@Api(tags = "videoLike")
public class VideoLikeController {

    private final VideoLikeService videoLikeService;


    @ApiOperation(value = "Video like", notes = "Mathod used for Video like")
    @PostMapping("/")
    public ResponseEntity<?> createVideolike(@RequestBody @Valid VideoLikeDTO requestDTO) {
        log.info("Video Like: {}{}", requestDTO, VideoLikeController.class);
        return ResponseEntity.ok(videoLikeService.createVideolike(requestDTO));
    }


    @ApiOperation(value = "remove", notes = "Mathod used for remove")
    @DeleteMapping("/")
    public ResponseEntity<?> removeVideolike(@RequestBody @Valid VideoLikeDTO dto) {
        //  return ResponseEntity.ok(videoLikeService.removeVideolike(dto));
        return null;
    }
}