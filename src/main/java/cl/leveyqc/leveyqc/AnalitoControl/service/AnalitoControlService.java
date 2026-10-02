package cl.leveyqc.leveyqc.AnalitoControl.service;

import cl.leveyqc.leveyqc.AnalitoControl.model.AnalitoControl;
import cl.leveyqc.leveyqc.AnalitoControl.repository.AnalitoControlRepository;
import cl.leveyqc.leveyqc.NivelesAnalitosControl.model.NivelesAnalitosControl;
import cl.leveyqc.leveyqc.NivelesAnalitosControl.service.NivelesAnalitosControlService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class AnalitoControlService {

    private final AnalitoControlRepository AnalitosControlrepository;
    private final NivelesAnalitosControlService NivelesAnalitosService;

    public AnalitoControlService(AnalitoControlRepository analitosControlrepository, NivelesAnalitosControlService nivelesAnalitosService) {
        AnalitosControlrepository = analitosControlrepository;
        NivelesAnalitosService = nivelesAnalitosService;
    }

    private AnalitoControl validacionAnalito(AnalitoControl n){
        if (n==null){
            return null;
        }
        if (n.getAnalitoId()==null){
            return null;
        }
        if (n.getIdControl()==null){
            return  null;
        }
        if (n.getUsuarioCreacion()==null || n.getUsuarioCreacion().isBlank()){
            return  null;
        }
        return n;
    }


    @Transactional(transactionManager = "laboratorioTransactionManager")
    public boolean insertarAnalitoControl(AnalitoControl analitoControl, String[] nombresNiveles ){
        AnalitoControl analito = validacionAnalito(analitoControl);
        if (analito == null || nombresNiveles ==null || nombresNiveles.length == 0  ) {
            return  false;
        }

        for (String nombreNivel : nombresNiveles) {
            if (nombreNivel == null || nombreNivel.isBlank())return false;}

        AnalitoControl analitoInsertado = AnalitosControlrepository.save(analito);
        Long idAnalitoAsignado = analitoInsertado.getIdAnalitoControl();
        String usuarioCreacion = analito.getUsuarioCreacion();

        for (String nombreNivel : nombresNiveles) {
            NivelesAnalitosControl nuevoNievelControl = new NivelesAnalitosControl();
            nuevoNievelControl.setNombreNivel(nombreNivel);
            nuevoNievelControl.setIdAnalitoAsignado(idAnalitoAsignado);
            nuevoNievelControl.setUsuarioCreacion(usuarioCreacion);
            NivelesAnalitosControl resultadoInsercion =  NivelesAnalitosService.insertarNivel(nuevoNievelControl);
            if (resultadoInsercion ==null){
                return false;
            }
        }
        return  true;
    }

    public boolean eliminarAnalitoControl(Long idAnalitoControl){
        if (idAnalitoControl==null){
            return false;
        }
        Optional<AnalitoControl> analitoBuscado = AnalitosControlrepository.findById(idAnalitoControl);
        AnalitoControl encontrado;
        if (analitoBuscado.isPresent()){
            encontrado = analitoBuscado.get();
            encontrado.setActivo(0);
            AnalitosControlrepository.save(encontrado);
            return   true;
        }else{
            return  false;
        }
    }

    public List<Object[]> listarNivelesyAnalitosControl(){
        return AnalitosControlrepository.ListarAnalitosyNivelesTodos();
    }

    public List<Object[]> listarAnalitosNivelesActivos(){
        return AnalitosControlrepository.listarSoloActivos();
    }

    public List<Object[]> listarAnalitosNivelesIncativos(){
        return AnalitosControlrepository.listarSoloInactivos();
    }

    public List<Object[]> listarLoteSimilar(String numeroLote){
        return AnalitosControlrepository.buscarPorLoteSimilar(numeroLote);
    }

    public List<Object[]> listarNombreControlesSimilares(String nombreControl){
        return AnalitosControlrepository.buscarPorNombreControl(nombreControl);
    }

    public List<Object[]> listarPorAnalitosObject(Long idAnalito){
        return AnalitosControlrepository.buscarPorAnalitoTecnica(idAnalito);
    }

    public List<Object[]> buscarEntreFechas(
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin
    ){
        return AnalitosControlrepository.buscarEntreFechas(fechaInicio,fechaFin);
    }
}
