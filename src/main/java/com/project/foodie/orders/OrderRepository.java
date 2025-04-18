package com.project.foodie.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUser_IdAndStatus(Long userId, String status);
    Optional<Order> findByGuestSessionIdAndStatus(String guestSessionId, String pending);
}
