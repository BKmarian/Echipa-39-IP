package com.ensat.services;

import com.ensat.entities.Ong;

public interface OngService {

    Iterable<Ong> listAllOngs();

    Ong getOngById(Integer id);

    Ong saveOng(Ong ong);

    void deleteOng(Integer id);

    Ong getOngByUsername(String username);

    Ong getOngByEmail(String email);

    Iterable<Ong> findOngsToAccept();

    Iterable<Ong> findOngsAccepted();
}
