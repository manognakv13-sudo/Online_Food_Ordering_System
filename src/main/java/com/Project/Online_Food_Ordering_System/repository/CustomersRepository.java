package com.Project.Online_Food_Ordering_System.repository;

import com.Project.Online_Food_Ordering_System.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomersRepository extends JpaRepository<Customers, Long> {
}
