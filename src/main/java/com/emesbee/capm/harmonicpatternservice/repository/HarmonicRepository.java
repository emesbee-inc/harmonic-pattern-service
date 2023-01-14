package com.emesbee.capm.harmonicpatternservice.repository;

import com.emesbee.capm.harmonicpatternservice.entity.PatternNotification;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@Repository
public interface HarmonicRepository extends JpaRepository<PatternNotification, UUID> {
}
