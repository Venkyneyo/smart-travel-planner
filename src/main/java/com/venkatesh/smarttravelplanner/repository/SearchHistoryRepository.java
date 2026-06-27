package com.venkatesh.smarttravelplanner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.venkatesh.smarttravelplanner.entity.SearchHistory;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    List<SearchHistory> findAllByOrderBySearchedAtDesc();

    long count();

    @Query("SELECT COUNT(DISTINCT s.city) FROM SearchHistory s")
    long countDistinctCities();

    @Query("SELECT AVG(s.temperature) FROM SearchHistory s")
    Double getAverageTemperature();
}