package org.etutoria.courseapp.dao;

import org.etutoria.courseapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
