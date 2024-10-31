package org.etutoria.courseapp.service;

import org.etutoria.courseapp.entities.User;

public interface UserService {
    User loadUserByEmail(String email);

    User createUser(String email, String password);

    void assignRoleToUser(String email, String roleName);
}
