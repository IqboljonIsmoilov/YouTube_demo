package com.company.controller;

import com.company.dto.VideoLikeDTO;
import com.company.service.VideoLikeService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/videoLike")
@Api(tags = "videoLike")
public class VideoLikeController {

    @Autowired
    private VideoLikeService videoLikeService;

    private Logger log = LoggerFactory.getLogger(VideoLikeController.class);

    @ApiOperation(value = "createVideolike", notes = "Mathod used for createVideolike", nickname = "nicname")
    @PostMapping("/createVideolike")
    public ResponseEntity<?> createVideolike(@RequestBody @Valid VideoLikeDTO dto) {
        log.info("VideoLike_create: {}", dto);
        return ResponseEntity.ok(videoLikeService.createVideolike(dto));
    }


    @ApiOperation(value = "remove", notes = "Mathod used for remove", nickname = "nicname")
    @DeleteMapping("/removeVideolike")
    public ResponseEntity<?> removeVideolike(@RequestBody @Valid VideoLikeDTO dto) {
      //  return ResponseEntity.ok(videoLikeService.removeVideolike(dto));
        return null;
    }
}
