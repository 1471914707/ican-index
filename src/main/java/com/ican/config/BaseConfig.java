package com.ican.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseConfig {

    @Value("${ican.image.basePath}")
    private String basePath;
    @Value("${ican.image.baseUrl}")
    private String baseUrl;

    @Value("${ican.doc.basePath}")
    private String baseDocPath;
    @Value("${ican.doc.baseUrl}")
    private String baseDocUrl;

    @Value("${ican.headshot.man}")
    private String manHeadshot;
    @Value("${ican.headshot.woman}")
    private String womanHeadshot;
    @Value("${ican.headshot.default}")
    private String defaultHeadshot;

    public String getBaseDocPath() {
        return baseDocPath;
    }

    public void setBaseDocPath(String baseDocPath) {
        this.baseDocPath = baseDocPath;
    }

    public String getBaseDocUrl() {
        return baseDocUrl;
    }

    public void setBaseDocUrl(String baseDocUrl) {
        this.baseDocUrl = baseDocUrl;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getManHeadshot() {
        return manHeadshot;
    }

    public void setManHeadshot(String manHeadshot) {
        this.manHeadshot = manHeadshot;
    }

    public String getWomanHeadshot() {
        return womanHeadshot;
    }

    public void setWomanHeadshot(String womanHeadshot) {
        this.womanHeadshot = womanHeadshot;
    }

    public String getDefaultHeadshot() {
        return defaultHeadshot;
    }

    public void setDefaultHeadshot(String defaultHeadshot) {
        this.defaultHeadshot = defaultHeadshot;
    }
}
