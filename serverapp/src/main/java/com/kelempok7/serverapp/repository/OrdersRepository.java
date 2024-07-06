package com.kelempok7.serverapp.repository;

import com.kelempok7.serverapp.models.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    @Query("SELECT o FROM Orders o WHERE o.user.username = :username AND o.id = :id")
    Optional<Orders> getOrderByUsernameAndId(@Param("username") String username, @Param("id") Integer id);

    @Query(value = "SELECT DATE(o.date) AS orderDate, COUNT(o.id) AS orderCount " +
            "FROM orders o " +
            "WHERE DATE(o.date) BETWEEN :min AND :max AND o.status = 'Completed' " +
            "GROUP BY DATE(o.date) " +
            "ORDER BY DATE(o.date)", nativeQuery = true)
    List<Object[]> getOrderDate(@Param("min") LocalDate min, @Param("max") LocalDate max);

    @Query("SELECT o FROM Orders o WHERE o.user.username = :username ")
    List<Orders> findOrdersByUsername(@Param("username") String username);
}
