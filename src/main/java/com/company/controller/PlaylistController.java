package com.company.controller;

import com.company.dto.PlaylistAboutDTO;
import com.company.dto.PlaylistDTO;
import com.company.enums.ProfileRole;
import com.company.service.PlaylistService;
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
@RequestMapping("/playlist")
@Api(tags = "Playlist")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;
    private Logger log = LoggerFactory.getLogger(PlaylistController.class);

    @ApiOperation(value = "create", notes = "Mathod used for create", nickname = "nickname")
    @PostMapping("/create/{channelId}")
    public ResponseEntity<?> create(@RequestBody @Valid PlaylistDTO dto,
                                    @PathVariable("channelId") String channelId,
                                    HttpServletRequest request) {
        log.info("Playlist_create: {}", dto);
        return ResponseEntity.ok(playlistService.create(dto, channelId,
                JwtUtil.getIdFromHeader(request, ProfileRole.USER)));

    }

    @ApiOperation(value = "Update About", notes = "Method used for update about of playlist",
            authorizations = @Authorization(value = "JWT Token"))
    @PutMapping("/public/{playlistId}")
    public ResponseEntity<?> updatePlaylist(@RequestBody @Valid PlaylistAboutDTO dto,
                                            @PathVariable("playlistId") String playlistId,
                                            HttpServletRequest request) {
        log.info("UPDATE about {}", dto);
        return ResponseEntity.ok(playlistService.updatePlaylist(dto, playlistId,
                JwtUtil.getIdFromHeader(request, ProfileRole.USER)));
    }


    @ApiOperation(value = "Delete", notes = "Method used for delete playlist only owner delete own playlists", authorizations = @Authorization(value = "JWT Token"))
    @DeleteMapping("/public/{playlistId}/delete")
    public ResponseEntity<?> delete(@PathVariable("playlistId") String playlistId,
                                    HttpServletRequest request) {
        log.info("/public/{playlistId}/delete {}", playlistId);
        return ResponseEntity.ok(playlistService.delete(playlistId,
                JwtUtil.getIdFromHeader(request, ProfileRole.USER)));
    }
}
