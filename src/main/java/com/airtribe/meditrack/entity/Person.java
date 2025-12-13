package com.airtribe.meditrack.entity;

import java.time.LocalDateTime;

public class Person {

    private long id;
    private String name;
    private int  age;
    private String mobileNo;
    private LocalDateTime createdAt;

    public Person(){}

    public Person(long id, String name, int age, String mobileNo) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.mobileNo = mobileNo;
        this.createdAt = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
