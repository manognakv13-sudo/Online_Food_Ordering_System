package com.Project.Online_Food_Ordering_System.repository;

import com.Project.Online_Food_Ordering_System.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
