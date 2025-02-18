package org.etutoria.courseapp.dao;

import org.etutoria.courseapp.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
