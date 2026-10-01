package cl.leveyqc.leveyqc.NivelesAnalitosControl.service;
import cl.leveyqc.leveyqc.NivelesAnalitosControl.model.NivelesAnalitosControl;
import cl.leveyqc.leveyqc.NivelesAnalitosControl.repository.NivelesAnalitosControlRepository;
import org.springframework.stereotype.Service;

@Service
public class NivelesAnalitosControlService {

    private final NivelesAnalitosControlRepository repository;

    public NivelesAnalitosControlService(NivelesAnalitosControlRepository repository) {
        this.repository = repository;
    }

    private NivelesAnalitosControl validacionInsercionNivel(NivelesAnalitosControl n){
        if (n==null){
            return null;
        }

        if (n.getIdAnalitoAsignado()==null){
            return  null;
        }

        if (n.getNombreNivel()==null || n.getNombreNivel().isBlank()){
            return  null;
        }

        if (n.getUsuarioCreacion()==null || n.getUsuarioCreacion().isBlank()){
            return  null;
        }

        return n;
    }


    public NivelesAnalitosControl insertarNivel(NivelesAnalitosControl nivelesAnalitosControl){
        NivelesAnalitosControl nuevoNivel = validacionInsercionNivel(nivelesAnalitosControl);
        if (nuevoNivel==null){
            return null;
        }
        return repository.save(nuevoNivel);
    }
}
