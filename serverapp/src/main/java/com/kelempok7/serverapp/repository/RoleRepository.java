package com.kelempok7.serverapp.repository;

import com.kelempok7.serverapp.models.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

}
