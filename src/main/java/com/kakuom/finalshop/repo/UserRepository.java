package com.kakuom.finalshop.repo;

import com.kakuom.finalshop.dto.AuthDTO;
import com.kakuom.finalshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT new com.kakuom.finalshop.dto.AuthDTO " +
            "(u.name, u.email, u.password, u.role) FROM User u WHERE u.email = :userEmail ")
    Optional<AuthDTO>  loadByEmail(@Param("userEmail") String userEmail);

    Boolean existsByName(String name);

    Boolean existsByEmail(String email);
}
