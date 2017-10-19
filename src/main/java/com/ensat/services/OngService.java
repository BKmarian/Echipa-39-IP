package com.ensat.services;

import com.ensat.entities.Ong;

public interface OngService {

    Iterable<Ong> listAllOngs();

    Iterable<Ong> findOngsToAccept();

    Iterable<Ong> findOngsAccepted();
}
