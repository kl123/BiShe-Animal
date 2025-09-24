# animal_shelet

exported at 2025-09-24 17:56:58

## 动物记录控制/pc

动物记录控制/pc


---
### 获取动物记录

> BASIC

**Path:** /animalRecord

**Method:** GET

> REQUEST



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 1 成功 ， 0 失败 |
| msg | string | 提示信息 |
| data | object | 数据 data |

**Response Demo:**

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```




---
### 分页可条件查询动物记录

> BASIC

**Path:** /animalRecord_bypage

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| page | integer |  |
| limit | integer |  |
| shelters_and_animalProfiles | object |  |
| &ensp;&ensp;&#124;─shelter | object |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─id | integer |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─shelter_name | string |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─contact_person | string |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─business_license | string |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─qualification_file | string |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─userId | integer |  |
| &ensp;&ensp;&#124;─animalProfile | object |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─id | integer | 主键 |
| &ensp;&ensp;&ensp;&ensp;&#124;─shelterId | integer | 所属流浪所ID |
| &ensp;&ensp;&ensp;&ensp;&#124;─shelterName | string | 所属流浪所名字 |
| &ensp;&ensp;&ensp;&ensp;&#124;─contactPerson | string | 流浪所联系人 |
| &ensp;&ensp;&ensp;&ensp;&#124;─name | string | 宠物名字 |
| &ensp;&ensp;&ensp;&ensp;&#124;─species | string | 物种 |
| &ensp;&ensp;&ensp;&ensp;&#124;─breed | string | 品种 |
| &ensp;&ensp;&ensp;&ensp;&#124;─gender | string | 性别（0:雌, 1:雄）<br>FEMALE :FEMALE<br>MALE :MALE<br>UNKNOWN :UNKNOWN |
| &ensp;&ensp;&ensp;&ensp;&#124;─genderStr | string |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─age | number | 年龄（年） |
| &ensp;&ensp;&ensp;&ensp;&#124;─healthStatus | string | 健康状况 |
| &ensp;&ensp;&ensp;&ensp;&#124;─description | string | 描述 |
| &ensp;&ensp;&ensp;&ensp;&#124;─imageUrl | string | 图片路径 |
| &ensp;&ensp;&ensp;&ensp;&#124;─status | string | 状态（0:待审核, 1:可领养, 2:已领养, 3:审核不通过）<br>PENDING :PENDING<br>AVAILABLE :AVAILABLE<br>ADOPTED :ADOPTED<br>REJECTED :REJECTED<br>UNKNOWN :UNKNOWN |
| &ensp;&ensp;&ensp;&ensp;&#124;─statusStr | string |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─createdAt | string | 创建时间 |
| &ensp;&ensp;&ensp;&ensp;&#124;─updatedAt | string | 更新时间 |

**Request Demo:**

```json
{
  "page": 0,
  "limit": 0,
  "shelters_and_animalProfiles": {
    "shelter": {
      "id": 0,
      "shelter_name": "",
      "contact_person": "",
      "business_license": "",
      "qualification_file": "",
      "userId": 0
    },
    "animalProfile": {
      "id": 0,
      "shelterId": 0,
      "shelterName": "",
      "contactPerson": "",
      "name": "",
      "species": "",
      "breed": "",
      "gender": "",
      "genderStr": "",
      "age": 0.0,
      "healthStatus": "",
      "description": "",
      "imageUrl": "",
      "status": "",
      "statusStr": "",
      "createdAt": "",
      "updatedAt": ""
    }
  }
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 1 成功 ， 0 失败 |
| msg | string | 提示信息 |
| data | object | 数据 data |

**Response Demo:**

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```




---
### 宠物发布状态的审批

> BASIC

**Path:** /approvalReleaseAnimalStatus

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| auditRecords | object |  |
| &ensp;&ensp;&#124;─id | integer |  |
| &ensp;&ensp;&#124;─adminId | integer |  |
| &ensp;&ensp;&#124;─targetType | string | @JsonIgnore<br>USER :USER<br>POST :POST<br>COMMENT :COMMENT<br>PET_PROFILE :PET_PROFILE |
| &ensp;&ensp;&#124;─targetStr | string |  |
| &ensp;&ensp;&#124;─targetId | integer |  |
| &ensp;&ensp;&#124;─action | string | @JsonIgnore<br>REJECT :REJECT<br>APPROVE :APPROVE |
| &ensp;&ensp;&#124;─actionStr | string |  |
| &ensp;&ensp;&#124;─reason | string |  |
| &ensp;&ensp;&#124;─auditTime | string |  |
| animalProfile | object |  |
| &ensp;&ensp;&#124;─id | integer | 主键 |
| &ensp;&ensp;&#124;─shelterId | integer | 所属流浪所ID |
| &ensp;&ensp;&#124;─shelterName | string | 所属流浪所名字 |
| &ensp;&ensp;&#124;─contactPerson | string | 流浪所联系人 |
| &ensp;&ensp;&#124;─name | string | 宠物名字 |
| &ensp;&ensp;&#124;─species | string | 物种 |
| &ensp;&ensp;&#124;─breed | string | 品种 |
| &ensp;&ensp;&#124;─gender | string | 性别（0:雌, 1:雄）<br>FEMALE :FEMALE<br>MALE :MALE<br>UNKNOWN :UNKNOWN |
| &ensp;&ensp;&#124;─genderStr | string |  |
| &ensp;&ensp;&#124;─age | number | 年龄（年） |
| &ensp;&ensp;&#124;─healthStatus | string | 健康状况 |
| &ensp;&ensp;&#124;─description | string | 描述 |
| &ensp;&ensp;&#124;─imageUrl | string | 图片路径 |
| &ensp;&ensp;&#124;─status | string | 状态（0:待审核, 1:可领养, 2:已领养, 3:审核不通过）<br>PENDING :PENDING<br>AVAILABLE :AVAILABLE<br>ADOPTED :ADOPTED<br>REJECTED :REJECTED<br>UNKNOWN :UNKNOWN |
| &ensp;&ensp;&#124;─statusStr | string |  |
| &ensp;&ensp;&#124;─createdAt | string | 创建时间 |
| &ensp;&ensp;&#124;─updatedAt | string | 更新时间 |

**Request Demo:**

```json
{
  "auditRecords": {
    "id": 0,
    "adminId": 0,
    "targetType": "",
    "targetStr": "",
    "targetId": 0,
    "action": "",
    "actionStr": "",
    "reason": "",
    "auditTime": ""
  },
  "animalProfile": {
    "id": 0,
    "shelterId": 0,
    "shelterName": "",
    "contactPerson": "",
    "name": "",
    "species": "",
    "breed": "",
    "gender": "",
    "genderStr": "",
    "age": 0.0,
    "healthStatus": "",
    "description": "",
    "imageUrl": "",
    "status": "",
    "statusStr": "",
    "createdAt": "",
    "updatedAt": ""
  }
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 1 成功 ， 0 失败 |
| msg | string | 提示信息 |
| data | object | 数据 data |

**Response Demo:**

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```





## getAnimalsPage_AnimalFilesPageLimit

getAnimalsPage_AnimalFilesPageLimit


---
### getPage

**Params:**

Non-Parameter
**Return:**

| name | type | desc |
| ------------ | ------------ | ------------ |
|  | integer |  |




---
### getLimit

**Params:**

Non-Parameter
**Return:**

| name | type | desc |
| ------------ | ------------ | ------------ |
|  | integer |  |




---
### getShelters_and_animalProfiles

**Params:**

Non-Parameter
**Return:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| shelter | object |  |
| &ensp;&ensp;&#124;─id | integer |  |
| &ensp;&ensp;&#124;─shelter_name | string |  |
| &ensp;&ensp;&#124;─contact_person | string |  |
| &ensp;&ensp;&#124;─business_license | string |  |
| &ensp;&ensp;&#124;─qualification_file | string |  |
| &ensp;&ensp;&#124;─userId | integer |  |
| animalProfile | object |  |
| &ensp;&ensp;&#124;─id | integer | 主键 |
| &ensp;&ensp;&#124;─shelterId | integer | 所属流浪所ID |
| &ensp;&ensp;&#124;─shelterName | string | 所属流浪所名字 |
| &ensp;&ensp;&#124;─contactPerson | string | 流浪所联系人 |
| &ensp;&ensp;&#124;─name | string | 宠物名字 |
| &ensp;&ensp;&#124;─species | string | 物种 |
| &ensp;&ensp;&#124;─breed | string | 品种 |
| &ensp;&ensp;&#124;─gender | string | 性别（0:雌, 1:雄）<br>FEMALE :FEMALE<br>MALE :MALE<br>UNKNOWN :UNKNOWN |
| &ensp;&ensp;&#124;─genderStr | string |  |
| &ensp;&ensp;&#124;─age | number | 年龄（年） |
| &ensp;&ensp;&#124;─healthStatus | string | 健康状况 |
| &ensp;&ensp;&#124;─description | string | 描述 |
| &ensp;&ensp;&#124;─imageUrl | string | 图片路径 |
| &ensp;&ensp;&#124;─status | string | 状态（0:待审核, 1:可领养, 2:已领养, 3:审核不通过）<br>PENDING :PENDING<br>AVAILABLE :AVAILABLE<br>ADOPTED :ADOPTED<br>REJECTED :REJECTED<br>UNKNOWN :UNKNOWN |
| &ensp;&ensp;&#124;─statusStr | string |  |
| &ensp;&ensp;&#124;─createdAt | string | 创建时间 |
| &ensp;&ensp;&#124;─updatedAt | string | 更新时间 |




---
### setPage

**Params:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| page | integer |  |

**Return:**

Non-Return



---
### setLimit

**Params:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| limit | integer |  |

**Return:**

Non-Return



---
### setShelters_and_animalProfiles

**Params:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| shelter | object |  |
| &ensp;&ensp;&#124;─id | integer |  |
| &ensp;&ensp;&#124;─shelter_name | string |  |
| &ensp;&ensp;&#124;─contact_person | string |  |
| &ensp;&ensp;&#124;─business_license | string |  |
| &ensp;&ensp;&#124;─qualification_file | string |  |
| &ensp;&ensp;&#124;─userId | integer |  |
| animalProfile | object |  |
| &ensp;&ensp;&#124;─id | integer | 主键 |
| &ensp;&ensp;&#124;─shelterId | integer | 所属流浪所ID |
| &ensp;&ensp;&#124;─shelterName | string | 所属流浪所名字 |
| &ensp;&ensp;&#124;─contactPerson | string | 流浪所联系人 |
| &ensp;&ensp;&#124;─name | string | 宠物名字 |
| &ensp;&ensp;&#124;─species | string | 物种 |
| &ensp;&ensp;&#124;─breed | string | 品种 |
| &ensp;&ensp;&#124;─gender | string | 性别（0:雌, 1:雄）<br>FEMALE :FEMALE<br>MALE :MALE<br>UNKNOWN :UNKNOWN |
| &ensp;&ensp;&#124;─genderStr | string |  |
| &ensp;&ensp;&#124;─age | number | 年龄（年） |
| &ensp;&ensp;&#124;─healthStatus | string | 健康状况 |
| &ensp;&ensp;&#124;─description | string | 描述 |
| &ensp;&ensp;&#124;─imageUrl | string | 图片路径 |
| &ensp;&ensp;&#124;─status | string | 状态（0:待审核, 1:可领养, 2:已领养, 3:审核不通过）<br>PENDING :PENDING<br>AVAILABLE :AVAILABLE<br>ADOPTED :ADOPTED<br>REJECTED :REJECTED<br>UNKNOWN :UNKNOWN |
| &ensp;&ensp;&#124;─statusStr | string |  |
| &ensp;&ensp;&#124;─createdAt | string | 创建时间 |
| &ensp;&ensp;&#124;─updatedAt | string | 更新时间 |

**Return:**

Non-Return



---
### canEqual

**Params:**

| name | type | desc |
| ------------ | ------------ | ------------ |

**Return:**

| name | type | desc |
| ------------ | ------------ | ------------ |
|  | boolean |  |





