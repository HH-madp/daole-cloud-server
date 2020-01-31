package com.daole.cloud.assistant.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyid;

    @Value("${aliyun.oss.file.keysecret}")
    private String keysecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketname;

    @Value("${aliyun.oss.file.filehost}")
    private String filehost;

    public static String END_POINT ="oss-cn-beijing.aliyuncs.com";
    public static String KEY_ID = "LTAI4FrfnCYueTBdnhJADQ2y";
    public static String KEY_SECRET = "NRzMULHw6zhD0WWcYNxPMOlEYrF6RF";
    public static String BUCKET_NAME = "daole-oss";
    public static String FILE_HOST = "ueditor";

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;

        KEY_ID = keyid;

        KEY_SECRET = keysecret;

        BUCKET_NAME = bucketname;

        FILE_HOST = filehost;

    }
}
