package com.example.SpringLibraryTest3.Dto;

public class AddbalanceDto {
    private int studentId;
    private double amount;

    public AddbalanceDto(int studentId, double amount) {
        this.studentId = studentId;
        this.amount = amount;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
