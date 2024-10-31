package org.etutoria.courseapp.service.impl;

import lombok.AllArgsConstructor;
import org.etutoria.courseapp.dao.RoleDao;
import org.etutoria.courseapp.entities.Role;
import org.etutoria.courseapp.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private RoleDao roleDao;
    @Override
    public Role createRole(String roleName) {
        return roleDao.save(new Role(roleName));
    }
}
