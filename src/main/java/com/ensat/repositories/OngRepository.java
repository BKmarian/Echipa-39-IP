package com.ensat.repositories;

/**
 * Created by oem on 3/22/17.
 */

import com.ensat.entities.Ong;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OngRepository extends CrudRepository<Ong, Integer> {
    Ong getOngByUsername(String username);
    Ong getOngByEmail(String email);

    @Query("select o from Ong o where o.approved = 0")
    Iterable<Ong> findOngsToAccept();

    @Query("select o from Ong o where o.approved = 1")
    Iterable<Ong> findOngsAccepted();
}
