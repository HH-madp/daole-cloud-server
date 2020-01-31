package com.daole.cloud.assistant.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String uploadFile(MultipartFile file) throws Exception;
}
