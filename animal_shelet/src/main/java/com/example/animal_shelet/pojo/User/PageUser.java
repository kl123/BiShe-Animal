package com.example.animal_shelet.pojo.User;

import com.example.animal_shelet.pojo.User.User;

import java.util.List;

public class PageUser {
    private List<User> users;
    private int totalCount;

    public PageUser(List<User> users, int totalCount) {
        this.users = users;
        this.totalCount = totalCount;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
