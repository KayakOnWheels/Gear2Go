package com.gear2go.repository;

import com.gear2go.entity.User;
import com.gear2go.entity.enums.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByMail(String mail);

    void deleteUserByMail(String mail);

    List<User> findUserByRoleAndExpirationDateIsGreaterThan(Role role, Date date);
}
