package cl.leveyqc.leveyqc.InfomracionLaboratorio.service;

import cl.leveyqc.leveyqc.InfomracionLaboratorio.model.InformacionLaboratorio;
import cl.leveyqc.leveyqc.InfomracionLaboratorio.repository.InformacionLaboratorioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InformacionLaboratorioService {

    private final InformacionLaboratorioRepository repository;

    public InformacionLaboratorioService(InformacionLaboratorioRepository repository) {
        this.repository = repository;
    }


    public List<InformacionLaboratorio> obtenerInformacionLaboratorio(){
        return repository.findAll();
    }

}
