package com.example.animal_shelet.service;

import com.example.animal_shelet.config.AIConfig;
import com.example.animal_shelet.mapper.AnimalMapper;
import com.example.animal_shelet.pojo.Animal.AnimalMatchResult;
import com.example.animal_shelet.pojo.Animal.AnimalProfile;
import com.example.animal_shelet.pojo.Animal.MatchRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * AI智能匹配服务类
 */
@Service
public class AIMatchService {
    
    @Autowired
    private AnimalMapper animalMapper;
    
    @Autowired
    private AIConfig aiConfig;
    
    @Autowired
    private RestTemplate restTemplate;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * 智能匹配动物
     * @param matchRequest 匹配请求
     * @return 匹配结果列表（前5条）
     */
    public List<AnimalMatchResult> intelligentMatch(MatchRequest matchRequest) {
        try {
            // 检查AI功能是否启用
            if (!matchRequest.isEnabled()) {
                return basicMatch(matchRequest);
            }
            
            // 1. 获取所有可领养的动物
            List<AnimalProfile> availableAnimals = animalMapper.getAvailableAnimals();
            
            if (availableAnimals.isEmpty()) {
                return new ArrayList<>();
            }
            
            // 2. 构建AI提示词
            String prompt = buildPrompt(matchRequest, availableAnimals);
            
            // 3. 调用AI API进行匹配
            String aiResponse = callAIAPI(prompt);
            
            // 4. 解析AI响应并生成匹配结果
            List<AnimalMatchResult> matchResults = parseAIResponse(aiResponse, availableAnimals);
            
            // 5. 返回前5条结果
            return matchResults.stream()
                    .limit(5)
                    .collect(Collectors.toList());
                    
        } catch (Exception e) {
            e.printStackTrace();
            // 如果AI匹配失败，使用基础匹配算法作为备选
            return basicMatch(matchRequest);
        }
    }
    
    /**
     * 构建AI提示词
     */
    private String buildPrompt(MatchRequest request, List<AnimalProfile> animals) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("作为宠物领养专家，请根据用户需求为以下动物进行匹配评分。\n\n");
        prompt.append("用户需求：\n");
        prompt.append("关键词：").append(request.getKeywords()).append("\n");
        prompt.append("偏好：").append(request.getPreferences()).append("\n");
        prompt.append("居住环境：").append(request.getLivingEnvironment()).append("\n");
        prompt.append("养宠经验：").append(request.getExperience()).append("\n\n");
        
        prompt.append("可领养动物列表：\n");
        for (int i = 0; i < animals.size(); i++) {
            AnimalProfile animal = animals.get(i);
            prompt.append(String.format("%d. ID:%d, 名字:%s, 物种:%s, 品种:%s, 性别:%s, 年龄:%.1f岁, 健康状况:%s, 描述:%s\n",
                    i + 1, animal.getId(), animal.getName(), animal.getSpecies(), animal.getBreed(),
                    animal.getGender() == 1 ? "雄性" : "雌性", animal.getAge(), 
                    animal.getHealthStatus(), animal.getDescription()));
        }
        
        prompt.append("\n请为每只动物评分（0-100分），并说明匹配原因。");
        prompt.append("请严格按照以下JSON格式返回结果：\n");
        prompt.append("{\n");
        prompt.append("  \"matches\": [\n");
        prompt.append("    {\n");
        prompt.append("      \"animalId\": 动物ID,\n");
        prompt.append("      \"score\": 匹配分数(0-100),\n");
        prompt.append("      \"reason\": \"匹配原因说明\"\n");
        prompt.append("    }\n");
        prompt.append("  ]\n");
        prompt.append("}\n");
        
        return prompt.toString();
    }
    
    /**
     * 调用AI API (使用OpenAI兼容格式)
     */
    private String callAIAPI(String prompt) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + aiConfig.getKey());
            
            // 使用OpenAI兼容格式
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", aiConfig.getModel());
            
            // 构建messages数组
            List<Map<String, String>> messages = new ArrayList<>();
            Map<String, String> systemMessage = new HashMap<>();
            systemMessage.put("role", "system");
            systemMessage.put("content", "你是一个专业的宠物领养匹配专家，请根据用户需求为动物进行匹配评分。");
            messages.add(systemMessage);
            
            Map<String, String> userMessage = new HashMap<>();
            userMessage.put("role", "user");
            userMessage.put("content", prompt);
            messages.add(userMessage);
            
            requestBody.put("messages", messages);
            requestBody.put("stream", false); // 不使用流式响应
            
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            
            ResponseEntity<String> response = restTemplate.exchange(
                    aiConfig.getUrl() + "/chat/completions", HttpMethod.POST, entity, String.class);
            
            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode jsonNode = objectMapper.readTree(response.getBody());
                // 解析OpenAI兼容格式的响应
                return jsonNode.path("choices").get(0).path("message").path("content").asText();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * 解析AI响应
     */
    private List<AnimalMatchResult> parseAIResponse(String aiResponse, List<AnimalProfile> animals) {
        List<AnimalMatchResult> results = new ArrayList<>();
        
        try {
            // 提取JSON部分
            int jsonStart = aiResponse.indexOf("{");
            int jsonEnd = aiResponse.lastIndexOf("}") + 1;
            
            if (jsonStart >= 0 && jsonEnd > jsonStart) {
                String jsonStr = aiResponse.substring(jsonStart, jsonEnd);
                JsonNode jsonNode = objectMapper.readTree(jsonStr);
                JsonNode matches = jsonNode.path("matches");
                
                Map<Integer, AnimalProfile> animalMap = animals.stream()
                        .collect(Collectors.toMap(AnimalProfile::getId, animal -> animal));
                
                for (JsonNode match : matches) {
                    int animalId = match.path("animalId").asInt();
                    double score = match.path("score").asDouble();
                    String reason = match.path("reason").asText();
                    
                    AnimalProfile animal = animalMap.get(animalId);
                    if (animal != null) {
                        results.add(new AnimalMatchResult(animal, score, reason));
                    }
                }
                
                // 按分数降序排序
                results.sort((a, b) -> Double.compare(b.getMatchPercentage(), a.getMatchPercentage()));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return results;
    }
    
    /**
     * 基础匹配算法（备选方案）
     */
    private List<AnimalMatchResult> basicMatch(MatchRequest request) {
        List<AnimalProfile> availableAnimals = animalMapper.getAvailableAnimals();
        List<AnimalMatchResult> results = new ArrayList<>();
        
        String keywords = request.getKeywords().toLowerCase();
        
        for (AnimalProfile animal : availableAnimals) {
            double score = calculateBasicScore(animal, keywords);
            String reason = "基于关键词匹配：" + keywords;
            results.add(new AnimalMatchResult(animal, score, reason));
        }
        
        return results.stream()
                .sorted((a, b) -> Double.compare(b.getMatchPercentage(), a.getMatchPercentage()))
                .limit(5)
                .collect(Collectors.toList());
    }
    
    /**
     * 计算基础匹配分数
     */
    private double calculateBasicScore(AnimalProfile animal, String keywords) {
        double score = 50.0; // 基础分数
        
        String animalInfo = (animal.getName() + " " + animal.getSpecies() + " " + 
                           animal.getBreed() + " " + animal.getDescription()).toLowerCase();
        
        String[] keywordArray = keywords.split("\\s+");
        int matchCount = 0;
        
        for (String keyword : keywordArray) {
            if (animalInfo.contains(keyword)) {
                matchCount++;
            }
        }
        
        // 根据匹配的关键词数量调整分数
        if (keywordArray.length > 0) {
            double matchRatio = (double) matchCount / keywordArray.length;
            score += matchRatio * 40; // 最多加40分
        }
        
        return Math.min(score, 100.0);
    }
}