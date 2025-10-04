package com.example.animal_shelet.contoller.coze;

import com.example.animal_shelet.pojo.coze.CozeDocumentRequest;
import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.service.CozeDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 扣子文档创建控制器
 */
@RestController
@RequestMapping("/api/coze")
@CrossOrigin
public class CozeDocumentController {

    @Autowired
    private CozeDocumentService cozeDocumentService;

    /**
     * 创建扣子文档
     * @param request 文档创建请求
     * @return 创建结果
     */
    @PostMapping("/document/create")
    public Result createDocument(@RequestBody CozeDocumentRequest request) {
        try {
            Object response = cozeDocumentService.createDocument(request);
            return Result.success(response);
        } catch (Exception e) {
            return Result.error("创建文档失败: " + e.getMessage());
        }
    }
}