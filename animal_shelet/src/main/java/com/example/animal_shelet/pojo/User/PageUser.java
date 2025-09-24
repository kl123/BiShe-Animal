package com.example.animal_shelet.pojo.User;

import com.example.animal_shelet.pojo.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageUser {
    private List<User> users;
    private int totalCount;
}
