package com.company.controller;

import com.company.service.PlayListVideoService;
import com.company.util.JwtUtil;
import dto.PlaylistVideoDTO;
import dto.PlaylistVideoIdDTO;
import dto.UpdateOrderNumDTO;
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
@RequestMapping("/playListVideo")
@Api(tags = "playListVideo")
public class PlayListVideoController {

    private final PlayListVideoService playListVideoService;


    @ApiOperation(value = "Create", notes = "Method used for add video to playlist",
            authorizations = @Authorization(value = "JWT Token"))
    @PostMapping("/public")
    public ResponseEntity<?> create(@RequestBody @Valid PlaylistVideoDTO reqestDTO,
                                    HttpServletRequest request) {
        log.info("CREATE {}{}", reqestDTO, PlayListVideoController.class);
        return ResponseEntity.ok(playListVideoService.create(reqestDTO, JwtUtil.getIdFromHeader(request)));
    }

    @ApiOperation(value = "Update", notes = "Method used for update update order num videos in the playlist",
            authorizations = @Authorization(value = "JWT Token"))
    @PutMapping("/public/{playlistVideoId}")
    public ResponseEntity<?> update(@RequestBody @Valid UpdateOrderNumDTO updateDTO,
                                    @PathVariable("playlistVideoId") String playlistVideoId,
                                    HttpServletRequest request) {
        log.info("Update: {}{}", updateDTO, PlayListVideoController.class);
        return ResponseEntity.ok(playListVideoService.update(updateDTO, playlistVideoId, JwtUtil.getIdFromHeader(request)));
    }

    @ApiOperation(value = "Delete", notes = "Method used for delete playlist video only owner deleted",
            authorizations = @Authorization(value = "JWT Token"))
    @DeleteMapping("/public/delete")
    public ResponseEntity<?> delete(@RequestBody @Valid PlaylistVideoIdDTO dto,
                                    HttpServletRequest request) {
        log.info("Delete: {}{}", dto, PlayListVideoController.class);
        return ResponseEntity.ok(playListVideoService.delete(dto, JwtUtil.getIdFromHeader(request)));
    }
}