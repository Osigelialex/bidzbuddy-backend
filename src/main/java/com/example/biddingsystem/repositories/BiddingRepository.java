package com.example.biddingsystem.repositories;

import com.example.biddingsystem.models.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BiddingRepository extends JpaRepository<Bid, String> {
}
