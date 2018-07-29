package com.exchangerate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exchangerate.dto.RateDTO;

public interface RateRepository extends JpaRepository<RateDTO, Long>{

}