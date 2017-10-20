package com.ensat.repositories;

import com.ensat.entities.Ong;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import com.ensat.entities.User;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
public interface UserBaseRepository<T>
        extends CrudRepository<T, Integer> {

     T getUserByEmail(String email);
     T getUserByUsername(String username);
     T getUserById(Integer id);
}