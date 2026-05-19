package com.platform.demo.platformapi.repository;

import java.util.List;

import com.platform.demo.platformapi.domain.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {

    List<ApplicationEntity> findByEnabledTrueOrderByDisplayOrderAsc();
}
