package cl.leveyqc.leveyqc.ReglasDeWestgard.service;
import cl.leveyqc.leveyqc.ReglasDeWestgard.model.ReglasDeWestgard;
import cl.leveyqc.leveyqc.ReglasDeWestgard.repository.ReglasDeWestgardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReglasDeWestgardService {
    private final ReglasDeWestgardRepository repository;
    public ReglasDeWestgardService(ReglasDeWestgardRepository repository) {
        this.repository = repository;
    }

    public List<ReglasDeWestgard> getRepository() {
        return repository.findAll();
    }

}
