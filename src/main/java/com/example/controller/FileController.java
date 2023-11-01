package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.constant.GlobalResponseEnum;
import com.example.model.GlobalResponseModel;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: BYDylan
 * @date: 2023/11/5
 * @description: 文件交互
 */
@Slf4j
@RestController
public class FileController {

    @RequestMapping("/test/file/upload")
    public Object uploadFile(HttpServletRequest request, MultipartFile upload) {
        // 上传的位置
        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        File file = new File(path);
        String filename = upload.getOriginalFilename();
        // 把文件的名称设置唯一值
        String uuid = UUID.randomUUID().toString().replace("-", "");
        filename = uuid + "_" + filename;
        // 完成文件上传
        try {
            upload.transferTo(new File(path, filename));
        } catch (IOException e) {
            log.error("upload file error: ", e);
        }
        return GlobalResponseModel.setResponse(GlobalResponseEnum.SUCCESS);
    }

}
