package com.example.animal_shelet.contoller.UserController;

import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    //登录接口
    @PostMapping("login")
    public Result login(@RequestBody Map<String, Object> loginData){

        String username = (String) loginData.get("username");
        String password = (String) loginData.get("password");
//        System.out.println(username);
//        System.out.println(password);

        return userService.checklogin(username,password);
    }

    //分页查询用户,传参数当前页面,每页数目
    @PostMapping("getusers")
    public Result getAllUsers(@RequestBody Map<String, Object> Data){
        int page = (int) Data.get("page");
        int limit= (int) Data.get("limit");
        System.out.println(page);
        System.out.println(limit);
        return userService.getAllUsers(page,limit);
    }

    //注册接口
    @PostMapping("register")
    public Result register(@RequestBody Map<String, Object> registerData){
        // 1. 提取前端传来的数据
        String username = (String) registerData.get("username");
        String phone = (String) registerData.get("phone");
        String password = (String) registerData.get("password");
        String email = (String) registerData.get("email");
        return userService.register(username,phone,password,email);
    }

    //图片上传接口import org.springframework.core.io.ByteArrayResource;

    // 在 UserController 类中添加以下方法
    @PostMapping("/uploadImage")
    public Result uploadImage(@RequestParam("file") MultipartFile file) {
        // 1. 验证文件是否存在
        if (file.isEmpty()) {
            return Result.error("文件不能为空");
        }

        try {
            // 2. 使用 RestTemplate 调用外部 API
            RestTemplate restTemplate = new RestTemplate();

            // 3. 构建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            headers.set("Authorization", "Bearer 1583|nNKW0Bm7r3ld4qzF6ulpmLjZIRoje2sToj6ikuwD");

            // 4. 构建请求体（multipart）
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            // 将 MultipartFile 转为 ByteArrayResource
            ByteArrayResource fileResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename(); // 保留原始文件名
                }
            };
            body.add("file", fileResource);
            body.add("token", "142cd42355ba117588319a3876aaaa58"); // 固定 token

            // 5. 构建完整请求
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // 6. 发送 POST 请求到 picui.cn
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    "https://picui.cn/api/v1/upload",
                    requestEntity,
                    Map.class
            );

            // 7. 处理响应
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                // 假设返回结构: { "success": true, "data": { "url": "https://..." } }
                Boolean success = (Boolean) responseBody.get("status");
                if (Boolean.TRUE.equals(success)) {
                    Map<String, Object> data = (Map<String, Object>) responseBody.get("data");
                    Map<String, Object> links = (Map<String, Object>) data.get("links");
                    String imageUrl = (String) links.get("url");
                    System.out.println("上传成功返回地址:"+imageUrl);
                    // 返回成功结果
                    Map<String, String> result = Map.of("url", imageUrl);
                    return Result.success(result);
                } else {
                    return Result.error("上传失败: " + responseBody.get("message"));
                }
            } else {
                return Result.error("请求失败，状态码: " + response.getStatusCode());
            }

        } catch (IOException e) {
            return Result.error("文件读取失败: " + e.getMessage());
        } catch (Exception e) {
            return Result.error("上传异常: " + e.getMessage());
        }
    }

}
