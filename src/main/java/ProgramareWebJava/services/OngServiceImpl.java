package ProgramareWebJava.services;

import ProgramareWebJava.entities.*;
import ProgramareWebJava.repositories.OngRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
