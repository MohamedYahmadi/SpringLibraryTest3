package com.example.SpringLibraryTest3.Entities;

import com.example.SpringLibraryTest3.Enums.UserRole;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
@DiscriminatorValue("instructor")
public class Instructor extends Users {
    private String bio;
    @OneToMany(mappedBy = "instructor")
    private List<Courses> courses;

    public Instructor() {
    }

    public Instructor(String firstName, String lastName, String email, String password, UserRole role, String bio, List<Courses> courses) {
        super(firstName, lastName, email, password, role);
        this.bio = bio;
        this.courses = courses;
    }

    public Instructor (String firstName, String lastName, String email, String password, UserRole role) {
        super(firstName, lastName, email, password, role);
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Courses> getCourses() {
        return courses;
    }

    public void setCourses(List<Courses> courses) {
        this.courses = courses;
    }
}