package com.example.SpringLibraryTest3.Entities;

        import com.example.SpringLibraryTest3.Enums.UserRole;
        import jakarta.persistence.*;
        import java.util.List;

        @Entity
        @DiscriminatorValue("student")
        public class Student extends Users {
            @ManyToMany
            @JoinTable(
                    name = "student_courses",
                    joinColumns = @JoinColumn(name = "student_id"),
                    inverseJoinColumns = @JoinColumn(name = "course_id")
            )
            private List<Courses> coursesBought;
            private double accountBalance;

            public Student() {
            }

            public Student(String firstName, String lastName, String email, String password, UserRole role, List<Courses> coursesBought, double accountBalance) {
                super(firstName, lastName, email, password, role);
                this.coursesBought = coursesBought;
                this.accountBalance = accountBalance;
            }

            public Student(String firstName, String lastName, String email, String password, UserRole role) {
                super(firstName, lastName, email, password, role);
            }

            public List<Courses> getCoursesBought() {
                return coursesBought;
            }

            public void setCoursesBought(List<Courses> coursesBought) {
                this.coursesBought = coursesBought;
            }

            public double getAccountBalance() {
                return accountBalance;
            }

            public void setAccountBalance(double accountBalance) {
                this.accountBalance = accountBalance;
            }
        }