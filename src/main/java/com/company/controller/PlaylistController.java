package com.company.controller;

import com.company.enums.ProfileRole;
import com.company.service.PlaylistService;
import com.company.util.JwtUtil;
import dto.PlaylistAboutDTO;
import dto.PlaylistDTO;
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
@RequestMapping("/playlist")
@Api(tags = "Playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    @ApiOperation(value = "create", notes = "Mathod used for create", nickname = "nickname")
    @PostMapping("/create/{channelId}")
    public ResponseEntity<?> create(@RequestBody @Valid PlaylistDTO requestDTO,
                                    @PathVariable("channelId") String channelId,
                                    HttpServletRequest request) {
        log.info("Playlist_create: {}{}", requestDTO, PlaylistController.class);
        return ResponseEntity.ok(playlistService
                .create(requestDTO, channelId, JwtUtil.getIdFromHeader(request, ProfileRole.USER)));
    }

    @ApiOperation(value = "Update About", notes = "Method used for update about of playlist",
            authorizations = @Authorization(value = "JWT Token"))
    @PutMapping("/public/{playlistId}")
    public ResponseEntity<?> updatePlaylist(@RequestBody @Valid PlaylistAboutDTO requestDTO,
                                            @PathVariable("playlistId") String playlistId,
                                            HttpServletRequest request) {
        log.info("UPDATE about {}{}", requestDTO, PlaylistController.class);
        return ResponseEntity.ok(playlistService.updatePlaylist(requestDTO, playlistId,
                JwtUtil.getIdFromHeader(request, ProfileRole.USER)));
    }


    @ApiOperation(value = "Delete", notes = "Method used for delete playlist only owner delete own playlists",
            authorizations = @Authorization(value = "JWT Token"))
    @DeleteMapping("/public/{playlistId}/delete")
    public ResponseEntity<?> delete(@PathVariable("playlistId") String playlistId,
                                    HttpServletRequest request) {
        log.info("Delete: {}{}", playlistId, PlaylistController.class);
        return ResponseEntity.ok(playlistService.delete(playlistId,
                JwtUtil.getIdFromHeader(request, ProfileRole.USER)));
    }
}