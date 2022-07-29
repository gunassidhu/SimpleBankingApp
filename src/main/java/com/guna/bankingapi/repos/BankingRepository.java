package com.guna.bankingapi.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guna.bankingapi.entities.Banking;

public interface BankingRepository extends JpaRepository<Banking, Integer> {

}
