package com.example.animal_shelet.pojo.Adopte;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.internal.build.AllowNonPortable;

@Data
@AllowNonPortable
@NoArgsConstructor
public class Adopters {

  private Integer id;
  private String name;
  private Integer gender;
  private Integer age;
  private String address;
  private String idCard;
  
}
