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

    private OngRepository ongRepository;

    @Autowired
    public void setOngRepository(OngRepository ongRepository) {
        this.ongRepository = ongRepository;
    }

    @Override
    public Iterable<Ong> listAllOngs() {
        return ongRepository.findAll();
    }

    @Override
    public Ong getOngById(Integer id) {
        return ongRepository.findOne(id);
    }

    @Override
    public Ong saveOng(Ong ong) {
        return ongRepository.save(ong);
    }

    @Override
    public void deleteOng(Integer id) {
        ongRepository.delete(id);
    }

    @Override
    public Ong getOngByUsername(String username) {
        return ongRepository.getOngByUsername(username);
    }

    @Override
    public Ong getOngByEmail(String email) {
        return ongRepository.getOngByEmail(email);
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
