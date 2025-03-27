package com.example.centralized_server.repository;

import com.example.centralized_server.entity.Role;
import com.example.centralized_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findByAddress(String address);
    Boolean existsByAddress(String address);
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);
    List<User> findByIsApprove(boolean isApprove);

    @Query("SELECT MONTH(o.createAt), COUNT(o) FROM User o WHERE YEAR(o.createAt) = :year GROUP BY MONTH(o.createAt)")
    List<Object[]> countUsersPerMonth(@Param("year") int year);
}
