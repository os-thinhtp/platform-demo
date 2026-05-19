package com.platform.demo.platformapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "applications")
public class ApplicationEntity {

    @Id
    private Long id;

    @Column(name = "business_key", nullable = false, unique = true)
    private String businessKey;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name = "launcher_url", nullable = false)
    private String launcherUrl;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @Column(nullable = false)
    private Boolean enabled;

    public Long getId() {
        return id;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLauncherUrl() {
        return launcherUrl;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public Boolean getEnabled() {
        return enabled;
    }
}
