package com.ensat.services;

import com.ensat.entities.Ong;
import com.ensat.repositories.OngRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Ong service implement.
 */
@Service
public class OngServiceImpl implements OngService {

    @Autowired
    private OngRepository ongRepository;

    @Override
    public Iterable<Ong> listAllOngs() {
        return ongRepository.findAll();
    }

    @Override
    public Iterable<Ong> findOngsToAccept() {
        return ongRepository.findOngsToAccept();
    }

    @Override
    public Iterable<Ong> findOngsAccepted() {
        return ongRepository.findOngsAccepted();
    }
}
