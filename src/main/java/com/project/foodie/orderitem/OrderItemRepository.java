package com.project.foodie.orderitem;

import com.project.foodie.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByUserAndOrderIsNull(User user);

    List<OrderItem> findByGuestSessionIdAndOrderIsNull(String guestSessionId);
}
