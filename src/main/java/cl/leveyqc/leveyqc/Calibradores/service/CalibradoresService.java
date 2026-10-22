package cl.leveyqc.leveyqc.Calibradores.service;
import cl.leveyqc.leveyqc.Calibradores.model.Calibradores;
import cl.leveyqc.leveyqc.Calibradores.repository.CalibradoresRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CalibradoresService {

    private final CalibradoresRepository repository;
    public CalibradoresService(CalibradoresRepository repository) {
        this.repository = repository;
    }


        private Calibradores validacionInsercion(Calibradores c){
        if (c==null){
            return null;
        }
        if (c.getNombreCalibrador()==null || c.getNombreCalibrador().isBlank()){
            return  null;
        }
        if (c.getLoteCalibrador()==null || c.getLoteCalibrador().isBlank()){
            return  null;
        }
        if (c.getFechaCaducicad()==null){
                return  null;
        }
        if (c.getIdProvededor()==null){
            return  null;
        }
        if (c.getCondicionesAlmacenamiento()==null || c.getCondicionesAlmacenamiento().isBlank()){
            return   null;
        }
        if (c.getStock()==null || c.getStock()<0){
            return  null;
        }
        if (c.getUsuarioCreacion() == null || c.getUsuarioCreacion().isBlank()){
            return  null;
        }
        return c;
    }

    public Calibradores crearCalibrador(Calibradores c){
        Calibradores calibrador = validacionInsercion(c);
        if (calibrador==null){
            return  null;
        }else{
            return repository.save(calibrador);
        }
    }


    public List<Calibradores> listarTodosCalibradores(){
        return repository.findAll();
    }



    public List<Calibradores> listarCalibradores(){
        return repository.findAllByActivo(1);
    }



    public Calibradores buscarPorId(Long idCalibrador){
        if (idCalibrador==null){
            return null;
        }else{
            Optional<Calibradores> buscado = repository.findById(idCalibrador);
            Calibradores encontrado;
            if (buscado.isPresent()){
                encontrado = buscado.get();
                return encontrado;
            }else{
                return null;
            }
        }
    }



    private Calibradores verificacionActualizacion(Calibradores c){
        if (c==null){
            return null;
        }
        if (c.getNombreCalibrador()==null || c.getNombreCalibrador().isBlank()){
            return  null;
        }
        if (c.getLoteCalibrador()==null || c.getLoteCalibrador().isBlank()){
            return  null;
        }
        if (c.getFechaCaducicad()==null){
            return  null;
        }
        if (c.getIdProvededor()==null){
            return  null;
        }
        if (c.getCondicionesAlmacenamiento()==null || c.getCondicionesAlmacenamiento().isBlank()){
            return   null;
        }
        if (c.getStock()==null || c.getStock()<0){
            return  null;
        }
        if (c.getUsuarioModificacion() == null || c.getUsuarioModificacion().isBlank()){
            return  null;
        }
        if (c.getIdCalibrador()==null){
            return  null;
        }
        return c;
    }



    public Calibradores actualziarCalibrador(Calibradores c){
        Calibradores calibrador = verificacionActualizacion(c);
        if (calibrador==null){
            return  null;
        }else {
            Optional<Calibradores> buscado = repository.findById(calibrador.getIdCalibrador());
            Calibradores encontrado;
            if (buscado.isPresent()){
                encontrado = buscado.get();
                encontrado.setNombreCalibrador(c.getNombreCalibrador());
                encontrado.setLoteCalibrador(c.getLoteCalibrador());
                encontrado.setFechaCaducicad(c.getFechaCaducicad());
                encontrado.setIdProvededor(c.getIdProvededor());
                encontrado.setIdProvededor(c.getIdProvededor());
                encontrado.setCondicionesAlmacenamiento(c.getCondicionesAlmacenamiento());
                encontrado.setStock(c.getStock());
                repository.save(encontrado);
                return encontrado;
            }else {
                return null;
            }
        }
    }



    public boolean desactivar(Long idCalibrador){
        if (idCalibrador==null){
            return false;
        }else{
            Optional<Calibradores> buscado = repository.findById(idCalibrador);
            Calibradores encontrado;
            if (buscado.isPresent()){
                encontrado = buscado.get();
                encontrado.setActivo(0);
                repository.save(encontrado);
                return true;
            }else{
                return false;
            }
        }
    }





    public boolean activarCalibrador(Long idCalibrador){
        if (idCalibrador==null){
            return false;
        }else{
                Optional<Calibradores> buscado = repository.findById(idCalibrador);
                Calibradores encontrado;
            if (buscado.isPresent()){
                encontrado = buscado.get();
                encontrado.setActivo(1);
                repository.save(encontrado);
                return true;
            }else{
                return false;
            }
        }
    }


    public List<Calibradores> listarCalibradoresParecidos(String  nombreCalibrador){
        return repository.findAllByNombreMatrizContainingIgnoreCase(nombreCalibrador);
    }


    public List<Calibradores> listarCalibradoresActivos(){
        return repository.findAllByActivo(1);
    }


    public List<Calibradores> listarCalibradoresInactivos(){
        return repository.findAllByActivo(0);
    }

}
