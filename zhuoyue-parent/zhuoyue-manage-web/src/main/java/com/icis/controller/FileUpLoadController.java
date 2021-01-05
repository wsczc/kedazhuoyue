package com.icis.controller;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

//文件上传控制器
@RestController
@CrossOrigin
public class FileUpLoadController {
    @Value("${fastdfs.url}")
    private String fastdfsUrl;

    //使用静态代码块初始化配置
    static {

        try {
            //初始化配置  指定服务器
            String fastdfsfile = FileUpLoadController.class.getResource("/tracker.conf").getFile();
            ClientGlobal.init(fastdfsfile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    //构建文件上传路径 实现图片上传 http://192.168.231.133/group1/M00/00/00/wKgEK1_cboOAMFMqACLTGplly6A056.jpg

    // http://localhost:8082/fileUpload
    @RequestMapping("/fileUpload")        //名字与前端一致
    public String fileUpLoad(MultipartFile file) throws IOException, MyException {
        //实现文件上传
        //判断文件是否存在
        if (file!=null){
            //1.获得原始文件名称
            String originalFilename = file.getOriginalFilename();
            //2.根据原始文件名  截取  获得文件扩展名
            String extFilename = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //3.实现向fastdfs上传文件

            //客户端
            TrackerClient trackerClient=new TrackerClient();
            //根据客户端 获得服务端
            TrackerServer trackerServer=trackerClient.getTrackerServer();
            //根据服务端获得存储节点
            StorageClient storageClient=new StorageClient(trackerServer,null);
            String[] upload_file = storageClient.upload_file(file.getBytes(), extFilename, null);
            //构建一个 StringBuilder对象
            StringBuilder stringBuilder=new StringBuilder(fastdfsUrl);

            //返回字符串数组  组名(盘符)  文件夹
            for (int i = 0; i < upload_file.length; i++) {
                String s = upload_file[i];
                //拼接字符串
                stringBuilder.append("/");
                stringBuilder.append(s);
            }

            //返回路径
            return stringBuilder.toString();


        }else {
            //没有选择图片  返回一个空
            return "";
        }


    }



}
