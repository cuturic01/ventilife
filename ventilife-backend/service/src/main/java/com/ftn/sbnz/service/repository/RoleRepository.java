package com.ftn.sbnz.service.repository;

import com.ftn.sbnz.service.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

}
