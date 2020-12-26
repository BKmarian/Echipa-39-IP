package ProgramareWebJava.repositories;

import ProgramareWebJava.entities.*;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends UserBaseRepository<User> {

}