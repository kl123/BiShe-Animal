package com.example.animal_shelet.contoller.pc.Animal;

import com.example.animal_shelet.pojo.result.Result;
import com.example.animal_shelet.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController("pcAnimalController")
public class AnimalController {
    @Autowired
    private AnimalService animalService;

    @RequestMapping("animalRecord")
    public Result getAnimal_record(){
        return animalService.getAnimal_record();
    }
    //分页查询内容记录
    @PostMapping ("animalRecord_bypage")
    public Result getAnimalsPage(@RequestBody Map<String, Object> Data){
        int page = (int) Data.get("page");
        int limit= (int) Data.get("limit");
        return animalService.getAnimal_bypage(page,limit);
    }

}
