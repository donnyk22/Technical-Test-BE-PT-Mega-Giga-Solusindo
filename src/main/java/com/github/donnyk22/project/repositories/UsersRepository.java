package com.github.donnyk22.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.github.donnyk22.project.models.entities.Users;

@Repository
public interface UsersRepository extends CrudRepository<Users, Integer> {
}
