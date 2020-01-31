package com.daole.cloud.assistant.controller;

import com.alibaba.fastjson.JSONObject;
import com.daole.cloud.assistant.service.FileService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/assistant/ueditor")
public class UeditorController {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FileService fileService;
    /**
     * ueditor 操作
     *
     * @param action
     * @return
     */
    @RequestMapping(value = "/action")
    @ResponseBody
    public Object uEditorConfig(@RequestParam String action, HttpServletRequest httpServletRequest) throws Exception {

        Object responseResult = null;

        if (StringUtils.equalsIgnoreCase(action, "config")) {

            Resource resource = resourceLoader.getResource("classpath:/ueditor/config.json");

            InputStream inputStream = null;
            JsonNode jsonNode = null;
            try {
                inputStream = resource.getInputStream();
                jsonNode = objectMapper.readValue(inputStream, JsonNode.class);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            responseResult = jsonNode != null ? jsonNode.toString() : "{}";
        } else if (StringUtils.equalsIgnoreCase(action, "uploadimage")) {
            MultipartRequest multipartRequest = null;
            if (httpServletRequest instanceof MultipartRequest) {
                multipartRequest = (MultipartRequest) httpServletRequest;
            }
//            AppAttachment appAttachmentEntity = new AppAttachment() ;

            //获取上传文件,ueditor图片上传的文件名参数为upfile
            MultipartFile muFile = multipartRequest.getFile("file");
            String uploadurl = fileService.uploadFile(muFile);

//            String fileName = muFile.getOriginalFilename(); // 文件名
//            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
//            String filePath = "http://daole-oss.oss-cn-beijing.aliyuncs.com/ueditor/"; // 上传后的路径
//            fileName = UUID.randomUUID() + suffixName; // 新文件名
//            //将MultipartFile转为file类型
//            File file = new File(fileName);
//            FileUtils.copyInputStreamToFile(muFile.getInputStream(), file);
//
//            String responseData = HttpClient
//                    // 请求方式和请求url
//                    .post(filePath)
//                    .param("file", file)
//                    .asString();
            //返回上传返回参数
            Map<String, Object> responseParam = new HashMap<>();
//            JSONObject jsonObject = (JSONObject) JSONObject.parseObject(responseData).get("data");
//            responseParam.put("size", jsonObject.get("size"));
            responseParam.put("state", "SUCCESS");
//            responseParam.put("url", jsonObject.get("url"));
            responseParam.put("url", uploadurl);
            responseResult = new JSONObject(responseParam);
            //将创建的本地文件删除掉
//            if (file.exists()) {
//                file.delete();
//            }
            return responseResult;
        }

        return responseResult;
    }
}
