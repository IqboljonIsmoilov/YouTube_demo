package com.company.controller;

import com.company.dto.AttachDTO;
import com.company.dto.ChannelDTO;
import com.company.dto.ChannelUpdateDTO;
import com.company.enums.ProfileRole;
import com.company.service.ChannelService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
@RequestMapping("/channel")
@Api(tags = "Channel")
public class ChannelController {

    private final ChannelService channelService;
    private Logger log = LoggerFactory.getLogger(ChannelController.class);


    @ApiOperation(value = "Get", notes = "Method used for get channel info")
    @GetMapping("/{channelId}")
    public ResponseEntity<?> get(@PathVariable("channelId") String channelId) {
        log.info("/{channelId} {}", channelId);
        return ResponseEntity.ok(channelService.get(channelId));
    }


    @ApiOperation(value = "Create", notes = "Method used for create channel",
            authorizations = @Authorization(value = "JWT Token"))
    @PostMapping("/public")
    public ResponseEntity<?> create(@RequestBody @Valid ChannelDTO dto,
                                    HttpServletRequest request) {
        log.info("CREATE {}", dto);

        return ResponseEntity.ok(channelService.create(dto, JwtUtil.getIdFromHeader(request)));
    }


    @ApiOperation(value = "updateChannel ", notes = "Method used for updateChannel",
            authorizations = @Authorization(value = "JWT Token"))
    @PutMapping("/public/{channelId}")
    public ResponseEntity<?> updateChannel(@RequestBody @Valid ChannelUpdateDTO dto,
                                           @PathVariable("channelId") String channelId,
                                           HttpServletRequest request) {
        log.info("UPDATE about {}", dto);
        return ResponseEntity.ok(channelService.updateChannel(dto, channelId, JwtUtil.getIdFromHeader(request)));
    }


    @ApiOperation(value = "updateChannelPhoto", notes = "Method used for updateChannelPhoto",
            authorizations = @Authorization(value = "JWT Token"))
    @PutMapping("/public/image/{channelId}")
    public ResponseEntity<?> updateChannelPhoto(@RequestBody @Valid AttachDTO dto,
                                                @PathVariable("channelId") String channelId,
                                                HttpServletRequest request) {
        log.info("/public/image/{channelId} {}", dto);
        return ResponseEntity.ok(channelService.updateChannelPhoto(dto.getId(), channelId, JwtUtil.getIdFromHeader(request)));
    }

    @ApiOperation(value = "Channel Banner", notes = "Method used for update channel banner",
            authorizations = @Authorization(value = "JWT Token"))
    @PutMapping("/public/banner/{channelId}")
    public ResponseEntity<?> channelBanner(@RequestBody @Valid AttachDTO dto,
                                           @PathVariable("channelId") String channelId,
                                           HttpServletRequest request) {
        log.info("/public/banner/{channelId} {}", dto);
        return ResponseEntity.ok(channelService.updateChannelBanner(dto.getId(), channelId, JwtUtil.getIdFromHeader(request)));
    }


    @ApiOperation(value = "Change Status", notes = "Method used for change channel's status with admin or owner",
            authorizations = @Authorization(value = "JWT Token"))
    @PutMapping("/public/status/{channelId}")
    public ResponseEntity<?> changeStatus(@PathVariable("channelId") String channelId,
                                          HttpServletRequest request) {
        log.info("/public/status/{channelId} {}", channelId);
        return ResponseEntity.ok(channelService.changeStatus(channelId, JwtUtil.getIdFromHeader(request)));
    }


    @ApiOperation(value = "List", notes = "Method used for get list of channels",
            authorizations = @Authorization(value = "JWT Token"))
    @GetMapping("/adm/list")
    public ResponseEntity<?> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "5") int size,
                                  HttpServletRequest request) {
        log.info("LIST page={} size={}", page, size);
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(channelService.list(page, size));
    }

}
