package com.example.animal_shelet.contoller.wechat;
import com.example.animal_shelet.pojo.Adopte.AdoptionApplications;
import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.service.AdoptersService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 领养控制/wechat
 */
@Slf4j
@RestController("wechatAdoptersController")
@RequestMapping("/wechat/adopters")
public class AdoptersController {
    @Autowired
    private AdoptersService adoptersService;

    /**
     * 插入领养申请
     * @param result
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/insertAdoptionApplications")
    public Result insertAdoptionApplications(
        @RequestBody Map<String, Integer> result,
        HttpServletRequest httpServletRequest)
    {
        String token = httpServletRequest.getHeader("token");
        log.info("result:{}",result);
        Integer animalId = result.get("animalId");
        log.info("animalId:{}", animalId);
        return adoptersService.insertAdoptionApplications(token, animalId);
    }


}
