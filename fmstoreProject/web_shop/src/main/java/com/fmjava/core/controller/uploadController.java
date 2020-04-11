package com.fmjava.core.controller;

import com.fmjava.core.pojo.entity.Result;
import com.fmjava.core.utils.FastDFSClient;
import org.csource.fastdfs.ClientGlobal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author joker_dj
 * @create 2020-04-12日 3:47
 */
@RestController
@RequestMapping("/upload")
public class uploadController {
    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;


    /**
     * 上传文件的方法
     * @param file 传来的文件
     * @return
     * @throws Exception
     */
    @RequestMapping("/uploadFile")
    public Result uploadFile(MultipartFile file) throws Exception {
        try {
            System.out.println(1);
            ClientGlobal.init("J:\\撩课-高新强\\分布式项目（代码）\\code\\fmjava\\fmstoreProject\\web_shop\\src\\main\\resources\\fastDFS\\fdfs_client.conf");
            System.out.println(2);
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:fastDFS/fdfs_client.conf");//获取fastDFS的配置文件
            String path = fastDFSClient.uploadFile(file.getBytes(), file.getOriginalFilename(), file.getSize());//获取上传文件的字节，文件名称，文件大小
            String url=FILE_SERVER_URL+path;
            System.out.println(file.getOriginalFilename());//获取文件名称

            System.out.println(url);
            return new Result(true, url);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"上传失败");
        }

    }
}
