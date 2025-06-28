package com.ontapauth.auth.repository;

import com.ontapauth.auth.entity.Role;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
  Set<Role> findByNameIn(List<String> names);
}
