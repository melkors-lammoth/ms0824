package com.demo.ms0824.repository;

import com.demo.ms0824.model.ToolType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolTypeRepository extends JpaRepository<ToolType, Long> {
    ToolType findToolTypeByType(String type);
}
