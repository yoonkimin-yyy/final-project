package kr.kro.bbanggil.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component 
@ConfigurationProperties(prefix = "file-upload") 
public class FileUploadProperties {
    private String localPath;
    private String resourcesPath;
}