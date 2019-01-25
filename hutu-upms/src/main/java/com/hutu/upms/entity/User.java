package com.hutu.upms.entity;

import java.io.Serializable;

public class User implements Serializable {
    public User() {
    }

    public User(String name, Integer age){
        this.name = name;
        this.age = age;
    }
    public String name;
    public Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}