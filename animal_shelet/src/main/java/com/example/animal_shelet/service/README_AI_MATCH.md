# 智能动物匹配系统

## 功能概述

本系统实现了基于AI大模型的智能动物匹配功能，通过用户提供的关键词和偏好，自动筛选出最合适的前5条可领养动物记录，并为每个匹配结果提供适配百分比。

## 接口说明

### 智能匹配接口

**接口地址：** `POST /intelligentMatch`

**请求参数：**
```json
{
  "keywords": "温顺 小型犬 适合家庭",
  "preferences": "希望找一只性格温和的小狗",
  "livingEnvironment": "公寓，有小院子",
  "experience": "有3年养狗经验"
}
```

**响应示例：**
```json
{
  "code": 200,
  "message": "匹配成功",
  "data": [
    {
      "animal": {
        "id": 1,
        "name": "小白",
        "species": "犬",
        "breed": "比熊",
        "gender": 0,
        "age": 2.0,
        "healthStatus": "健康",
        "description": "性格温顺，适合家庭饲养",
        "imageUrl": "http://example.com/image1.jpg",
        "status": 1
      },
      "matchPercentage": 95.0,
      "matchReason": "该犬种性格温顺，体型小巧，非常适合公寓饲养，与您的需求高度匹配"
    }
  ]
}
```

## 配置说明

### AI API 配置

在 `application.yml` 中配置AI服务：

```yaml
ai:
  api:
    url: https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation
    key: YOUR_API_KEY  # 请替换为您的实际API密钥
    model: qwen-turbo
    enabled: true
    timeout: 30000
```

### 配置参数说明

- `url`: AI API的服务地址
- `key`: API密钥，需要从AI服务提供商获取
- `model`: 使用的AI模型名称
- `enabled`: 是否启用AI匹配功能，设为false时将使用基础匹配算法
- `timeout`: 请求超时时间（毫秒）

## 匹配算法

### AI匹配（主要方式）

1. 获取所有状态为"可领养"的动物
2. 构建包含用户需求和动物信息的提示词
3. 调用AI大模型API进行智能分析
4. 解析AI返回的匹配结果和评分
5. 按匹配度排序，返回前5条记录

### 基础匹配（备选方案）

当AI服务不可用时，系统会自动切换到基础匹配算法：

1. 基于关键词进行文本匹配
2. 计算匹配度评分
3. 按评分排序返回结果

## 数据库表结构

系统使用现有的 `animal_profiles` 表，主要字段：

- `id`: 动物ID
- `name`: 动物名称
- `species`: 物种
- `breed`: 品种
- `gender`: 性别（0:雌性, 1:雄性）
- `age`: 年龄
- `health_status`: 健康状况
- `description`: 描述信息
- `status`: 状态（1:可领养）

## 使用示例

### 前端调用示例

```javascript
// 发送匹配请求
const matchRequest = {
  keywords: "温顺 小型犬 适合家庭",
  preferences: "希望找一只性格温和的小狗",
  livingEnvironment: "公寓，有小院子",
  experience: "有3年养狗经验"
};

fetch('/intelligentMatch', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify(matchRequest)
})
.then(response => response.json())
.then(data => {
  console.log('匹配结果:', data);
});
```

## 注意事项

1. **API密钥安全**: 请妥善保管AI服务的API密钥，不要在代码中硬编码
2. **错误处理**: 系统具备完善的错误处理机制，AI服务异常时会自动降级到基础匹配
3. **性能优化**: 建议根据实际使用情况调整超时时间和并发限制
4. **数据质量**: 动物描述信息的质量直接影响匹配效果，建议完善动物档案信息

## 扩展功能

系统支持以下扩展：

1. **多模型支持**: 可配置不同的AI模型
2. **缓存机制**: 可添加Redis缓存提高响应速度
3. **用户画像**: 可结合用户历史行为优化匹配算法
4. **反馈学习**: 可收集用户反馈持续优化匹配效果


token:pat_ZAqhamKIGcIboRpj757LeVuaVfTzwUMMBszlq1fXipR1FyI53SnulOcTiKDaTJ8M