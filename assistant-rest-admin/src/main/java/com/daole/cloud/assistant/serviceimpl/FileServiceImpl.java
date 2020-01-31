package com.daole.cloud.assistant.serviceimpl;


import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.daole.cloud.assistant.service.FileService;
import com.daole.cloud.assistant.util.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(MultipartFile file) {

         String endpoint = ConstantPropertiesUtil.END_POINT;

         String keyid = ConstantPropertiesUtil.KEY_ID;

         String keysecret = ConstantPropertiesUtil.KEY_SECRET;

         String bucketname =  ConstantPropertiesUtil.BUCKET_NAME;

         String filehost =  ConstantPropertiesUtil.FILE_HOST;

        InputStream inputStream = null;
        String uploadUrl = null;
        try {
            inputStream = file.getInputStream();
            //创建ossClient实例
            OSSClient ossClient = new OSSClient(endpoint,keyid,keysecret);
            if (!ossClient.doesBucketExist(bucketname)){
                ossClient.createBucket(bucketname);
                ossClient.setBucketAcl(bucketname, CannedAccessControlList.PublicRead);
            }
            //获取原始文件名
            String original = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String fileType = original.substring(original.lastIndexOf("."));
            String newName = fileName + fileType;

            //创建文件上传目录
            String filaPath = new DateTime().toString("yyy/MM/dd");
            //上传的文件位置
            String fileUrl = filehost + "/" + filaPath +"/"+ newName;

            //上传文件流
            ossClient.putObject(bucketname,fileUrl,inputStream);
            //关闭连接
            ossClient.shutdown();
            //组装文件的最终地址
            uploadUrl = "https://" +  bucketname + "." + endpoint + "/" +fileUrl;
        }catch (Exception e){

        }

        return uploadUrl;
    }
}
