package com.demo.ms0824.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Tool {

    @Id
    private String code;
    private String type;
    private String brand;

    public Tool(String code, String type, String brand) {
        this.code = code;
        this.type = type;
        this.brand = brand;
    }

    public Tool() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
