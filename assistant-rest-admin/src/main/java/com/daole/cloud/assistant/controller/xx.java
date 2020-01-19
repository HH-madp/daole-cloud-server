package com.daole.cloud.assistant.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import sun.net.www.http.HttpClient;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

public class xx {

    /**
     * ueditor 操作
     * @param action
     * @return
     */
    @RequestMapping(value="/action")
    @ResponseBody
    public Object uEditorConfig(@RequestParam String action , HttpServletRequest httpServletRequest ) throws Exception {

        log.info("ueditor请求: action={}" , action);

        Object responseResult = null ;

        if(UtilString.equalsIgnoreCase( action, "config" )){

            Resource resource = resourceLoader.getResource("classpath:/ueditor/config.json") ;

            InputStream inputStream = null ;
            JsonNode jsonNode = null ;
            try{
                inputStream = resource.getInputStream() ;
                jsonNode = objectMapper.readValue( inputStream , JsonNode.class ) ;
            }catch( Exception e ){
                log.error( " 获取ueditor配置异常: {}" , e ) ;
            }finally {
                if( inputStream!=null ){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            responseResult = jsonNode!=null ? jsonNode.toString() : "{}" ;
        }else if( UtilString.equalsIgnoreCase( action , "uploadimage" ) ){
            MultipartRequest multipartRequest = null ;
            if( httpServletRequest instanceof MultipartRequest ){
                multipartRequest = (MultipartRequest)httpServletRequest ;
            }
            AppAttachment appAttachmentEntity = new AppAttachment() ;

            //获取上传文件,ueditor图片上传的文件名参数为upfile
            MultipartFile muFile = multipartRequest.getFile( AppAttachmentProperties.UPLOAD_FILE_PARAM ) ;
            String fileName = muFile.getOriginalFilename(); // 文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
            String filePath = "http://s.dtdjzx.gov.cn/cloud/oss/upload/box_resource/single/"; // 上传后的路径
            fileName = UUID.randomUUID() + suffixName; // 新文件名
            //将MultipartFile转为file类型
            File file = new File(fileName);
            FileUtils.copyInputStreamToFile(muFile.getInputStream(), file);

            String responseData = HttpClient
                    // 请求方式和请求url
                    .post(filePath)
                    .param("file",file)
                    .asString();
            //返回上传返回参数
            Map<String,Object> responseParam = Maps.newHashMap();
            JSONObject jsonObject = (JSONObject) JSONObject.parseObject(responseData).get("data");
            responseParam.put("size" ,jsonObject.get("size")) ;
            responseParam.put("state" , "SUCCESS") ;
            responseParam.put("url" ,  jsonObject.get("url")) ;
            responseResult = JsonMapper.toJSONString( responseParam ) ;
            //将创建的本地文件删除掉
            if (file.exists()){
                file.delete();
            }
            return responseResult;
        }

        return responseResult ;
    }

    public void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    @RequestMapping(value="/image/upload")
    @ResponseBody
    public Object imageUpload(MultipartRequest multipartRequest ,
                              AppAttachment appAttachmentEntity) throws IOException {
        //获取上传文件,ueditor图片上传的文件名参数为upfile
        MultipartFile multipartFile = multipartRequest.getFile( AppAttachmentProperties.UPLOAD_FILE_PARAM ) ;

        try {
            appAttachmentEntity = appAttachmentService.saveAttachment( appAttachmentEntity ,
                    multipartFile.getBytes() ,
                    multipartFile.getOriginalFilename() ) ;
        } catch (IOException e) {
            // 处理 getBytes 异常
            return ResponseParam.fail().message("获取上传文件内容失败") ;
        }catch (Exception e) {
            // 处理文件上传处理的异常
            return ResponseParam.fail().message("上传文件处理异常") ;
        }

        Map<String,Object> responseParam = Maps.newHashMap() ;
        //返回上传返回参数
        responseParam.put("originalName" , appAttachmentEntity.getOriginName() ) ;
        responseParam.put("name" , appAttachmentEntity.getStorageName()) ;
        responseParam.put("size" , appAttachmentEntity.getSize()) ;
        responseParam.put("state" , "SUCCESS") ;
        responseParam.put("type" , appAttachmentEntity.getExtension() ) ;
        responseParam.put("url" , appAttachmentEntity.getDownloadPath() ) ;

        return JsonMapper.toJSONString( responseParam ) ;
    }

}
