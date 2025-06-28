package com.ontapauth.auth.repository;


import com.ontapauth.auth.entity.Permission;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

  /*- SELECT * FROM permission WHERE name IN ('GET', 'POST', 'PUT'); -*/
  Set<Permission> findByNameIn(List<String> names);
}
