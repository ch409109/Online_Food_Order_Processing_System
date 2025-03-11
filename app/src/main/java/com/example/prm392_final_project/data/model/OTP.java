package com.example.prm392_final_project.data.model;

public class OTP {
    private int id;
    private int userId;
    private String otpCode;
    private String expiresAt;
    private int isUsed;

    public OTP(int id, int userId, String otpCode, String expiresAt, int isUsed) {
        this.id = id;
        this.userId = userId;
        this.otpCode = otpCode;
        this.expiresAt = expiresAt;
        this.isUsed = isUsed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }
}
