package com.fmjava.core.controller;

import com.fmjava.core.pojo.entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fmjava.core.utils.FastDFSClient;

import java.util.HashMap;
import java.util.Map;

/**
 * @author joker_dj
 * @create 2020-04-12日 1:17
 * fastDFS文件上传
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

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
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:fastDFS/fdfs_client.conf");//获取fastDFS的配置文件
            String path = fastDFSClient.uploadFile(file.getBytes(), file.getOriginalFilename(), file.getSize());//获取上传文件的字节，文件名称，文件大小
            String url=FILE_SERVER_URL+path;
            System.out.println(file.getOriginalFilename());//获取文件名称

            return new Result(true, url);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"上传失败");
        }

    }

    /**
     * fastdfs删除图片
     * @param url 图片路径
     * @return
     */
    @RequestMapping("/deleteImg")
    public Result deleteImg(String url){
        try {
            String path = url.substring(FILE_SERVER_URL.length());//截取图片名称 去除IP地址
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:fastDFS/fdfs_client.conf");//获取fastDFS的配置文件
            Integer res = fastDFSClient.delete_file(path);//调用删除图片的方法
            if(res==0){
                return new Result(true,"删除成功");
            }else{
                return new Result(false,"删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }

    /**
     * 富文本上传图片
     * @param upfile 文件
     * @return
     * @throws Exception
     */
    @RequestMapping("/uploadImage")
    public Map uploadImage(MultipartFile upfile) throws Exception {
        try {
            FastDFSClient fastDFS=new FastDFSClient("classpath:fastDFS/fdfs_client.conf");
            //上传文件返回文件保存的路径和文件名
            String path = fastDFS.uploadFile(upfile.getBytes(), upfile.getOriginalFilename(), upfile.getSize());
            //拼接上服务器的地址返回给前端
            String url  = FILE_SERVER_URL + path;
            Map<String ,Object > result = new HashMap<>();
            result.put("state","SUCCESS");
            result.put("url",url);
            result.put("title",upfile.getOriginalFilename());
            result.put("original",upfile.getOriginalFilename());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
