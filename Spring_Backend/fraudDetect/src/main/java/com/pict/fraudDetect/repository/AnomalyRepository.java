package com.pict.fraudDetect.repository;

import com.pict.fraudDetect.model.Anomaly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnomalyRepository extends JpaRepository<Anomaly, Long> {
}
