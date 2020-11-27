package com.ensat.repositories;

/**
 * Created by oem on 3/22/17.
 */

import com.ensat.entities.Ong;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OngRepository extends UserBaseRepository<Ong> {

    @Query("select o from Ong as o where o.approved = 0")
    Iterable<Ong> findOngsToAccept();

    @Query("select o from Ong as o where o.approved = 1")
    Iterable<Ong> findOngsAccepted();
}
