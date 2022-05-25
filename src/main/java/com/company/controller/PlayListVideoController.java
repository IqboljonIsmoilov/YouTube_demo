package com.company.controller;

import com.company.dto.PlaylistVideoDTO;
import com.company.dto.PlaylistVideoIdDTO;
import com.company.dto.UpdateOrderNumDTO;
import com.company.enums.ProfileRole;
import com.company.service.PlayListVideoService;
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
@RequestMapping("/playListVideo")
@Api(tags = "playListVideo")
public class PlayListVideoController {

    @Autowired
    private PlayListVideoService playListVideoService;

    private Logger log = LoggerFactory.getLogger(AuthController.class);



    @ApiOperation(value = "Create", notes = "Method used for add video to playlist",
            authorizations = @Authorization(value = "JWT Token"))
    @PostMapping("/public")
    public ResponseEntity<?> create(@RequestBody @Valid PlaylistVideoDTO dto,
                                    HttpServletRequest request) {
        log.info("CREATE {}", dto);
        return ResponseEntity.ok(playListVideoService.create(dto, JwtUtil.getIdFromHeader(request)));
    }

    @ApiOperation(value = "Update", notes = "Method used for update update order num videos in the playlist",
            authorizations = @Authorization(value = "JWT Token"))
    @PutMapping("/public/{playlistVideoId}")
    public ResponseEntity<?> update(@RequestBody @Valid UpdateOrderNumDTO dto,
                                    @PathVariable("playlistVideoId") String playlistVideoId,
                                    HttpServletRequest request) {
        log.info("/public/{playlistVideoId} {}", dto);
        return ResponseEntity.ok(playListVideoService.update(dto, playlistVideoId, JwtUtil.getIdFromHeader(request)));
    }

    @ApiOperation(value = "Delete", notes = "Method used for delete playlist video only owner deleted",
            authorizations = @Authorization(value = "JWT Token"))
    @DeleteMapping("/public/delete")
    public ResponseEntity<?> delete(@RequestBody @Valid PlaylistVideoIdDTO dto,
                                    HttpServletRequest request) {
        log.info("/public/delete {}", dto);
        return ResponseEntity.ok(playListVideoService.delete(dto, JwtUtil.getIdFromHeader(request)));
    }


}
