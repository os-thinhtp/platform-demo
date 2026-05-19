package com.platform.demo.platformapi.service;

import java.util.List;

import com.platform.demo.platformapi.dto.AppLauncherDto;
import com.platform.demo.platformapi.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

@Service
public class AppQueryService {

    private final ApplicationRepository applicationRepository;

    public AppQueryService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public List<AppLauncherDto> getEnabledApps() {
        return applicationRepository.findByEnabledTrueOrderByDisplayOrderAsc().stream()
            .map(app -> new AppLauncherDto(app.getBusinessKey(), app.getName(), app.getDescription(), app.getLauncherUrl()))
            .toList();
    }
}
