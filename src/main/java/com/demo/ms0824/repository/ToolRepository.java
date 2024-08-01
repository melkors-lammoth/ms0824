package com.demo.ms0824.repository;

import com.demo.ms0824.model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolRepository extends JpaRepository<Tool, Long> {
    Tool findByCode(String code);
}
