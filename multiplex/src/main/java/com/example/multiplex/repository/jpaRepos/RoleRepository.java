package com.example.multiplex.repository.jpaRepos;

import com.example.multiplex.model.persistence.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
