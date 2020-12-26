package ProgramareWebJava.repositories;


import ProgramareWebJava.entities.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OngRepository extends UserBaseRepository<Ong> {

    @Query("select o from Ong as o where o.approved = 0")
    Iterable<Ong> findOngsToAccept();

    @Query("select o from Ong as o where o.approved = 1")
    Iterable<Ong> findOngsAccepted();

}
