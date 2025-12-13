package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.Specialization;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

public class Doctor {

    private long id;
    private String name;
    private int experience;
    private Specialization specialization;
    private Set<DayOfWeek> availableDays;
    private LocalDateTime createdAt;
    private LocalTime availableFrom;
    private LocalTime availableTo;

    public Doctor(){}

    public Doctor(long id, String name, int experience, Specialization specialization, Set<DayOfWeek> availableDays, LocalDateTime createdAt, LocalTime availableFrom, LocalTime availableTo) {
        this.id = id;
        this.name = name;
        this.experience = experience;
        this.specialization = specialization;
        this.availableDays = availableDays;
        this.createdAt = createdAt;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
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

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public Set<DayOfWeek> getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(Set<DayOfWeek> availableDays) {
        this.availableDays = availableDays;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalTime getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(LocalTime availableFrom) {
        this.availableFrom = availableFrom;
    }

    public LocalTime getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(LocalTime availableTo) {
        this.availableTo = availableTo;
    }
}
