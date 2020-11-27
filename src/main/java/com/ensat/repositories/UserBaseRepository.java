package com.ensat.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserBaseRepository<T>
        extends CrudRepository<T, Integer> {

    T getUserByEmail(String email);

    T getUserByUsername(String username);

    T getUserById(Integer id);
}