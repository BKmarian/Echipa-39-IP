package com.ensat.repositories;

import com.ensat.entities.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends UserBaseRepository<User> {

}