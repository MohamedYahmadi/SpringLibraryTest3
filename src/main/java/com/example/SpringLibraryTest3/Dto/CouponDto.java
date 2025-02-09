// CouponDto.java
    package com.example.SpringLibraryTest3.Dto;

    public class CouponDto {
        private int courseId;
        private String code;
        private double discountPercentage;
        private int maxUse;

        // Getters and Setters
        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public double getDiscountPercentage() {
            return discountPercentage;
        }

        public void setDiscountPercentage(double discountPercentage) {
            this.discountPercentage = discountPercentage;
        }

        public int getMaxUse() {
            return maxUse;
        }

        public void setMaxUse(int maxUse) {
            this.maxUse = maxUse;
        }
    }