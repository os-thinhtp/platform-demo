package com.platform.demo.platformapi.controller;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import com.platform.demo.platformapi.dto.AppLauncherDto;
import com.platform.demo.platformapi.service.AppQueryService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/platform")
public class PlatformController {

    private final AppQueryService appQueryService;

    public PlatformController(AppQueryService appQueryService) {
        this.appQueryService = appQueryService;
    }

    @GetMapping("/me")
    Map<String, Object> me(Authentication authentication) {
        return Map.of(
            "username", authentication.getName(),
            "authorities", authentication.getAuthorities(),
            "timestamp", Instant.now().toString());
    }

    @GetMapping("/apps")
    List<AppLauncherDto> apps() {
        return appQueryService.getEnabledApps();
    }
}
