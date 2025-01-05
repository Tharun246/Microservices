package com.example.Order_Service.Repository;

import com.example.Order_Service.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long>
{
}
