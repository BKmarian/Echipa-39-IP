package ProgramareWebJava.services;

import ProgramareWebJava.entities.*;
import org.springframework.stereotype.Service;

@Service
public interface OngService {

    Iterable<Ong> findOngsToAccept();

    Iterable<Ong> findOngsAccepted();
}
