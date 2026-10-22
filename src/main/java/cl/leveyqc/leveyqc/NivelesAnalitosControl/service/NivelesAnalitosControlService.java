package cl.leveyqc.leveyqc.NivelesAnalitosControl.service;
import cl.leveyqc.leveyqc.NivelesAnalitosControl.model.NivelesAnalitosControl;
import cl.leveyqc.leveyqc.NivelesAnalitosControl.repository.NivelesAnalitosControlRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

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



    public NivelesAnalitosControl editarEsperados(NivelesAnalitosControl n){
        if (n==null){
            return null;
        }
        if (n.getMediaObjetivo() ==null){
            return   null;
        }
        if (n.getDesviacionEstandarObjetivo() ==null){
            return  null;
        }
        if (n.getIdNivelesAnalitosControl()==null){
            return  null;
        }
        if (n.getUsuarioModificacion()==null){
            return  null;
        }

        System.out.println("IMPRIMIENDO DATOS QUE LLEGAN DESDE EL CONTROLLER");
        System.out.println("-------------------------------------------------");
        System.out.println(n.getIdNivelesAnalitosControl());
        System.out.println(n.getMediaObjetivo());
        System.out.println("-------------------------------------------------");




        Optional<NivelesAnalitosControl> buscado = repository.findById(n.getIdNivelesAnalitosControl());
        NivelesAnalitosControl encontrado;

        if (buscado.isPresent()) {
            encontrado = buscado.get();
            encontrado.setMediaObjetivo(n.getMediaObjetivo());
            encontrado.setDesviacionEstandarObjetivo(n.getDesviacionEstandarObjetivo());
            encontrado.setUsuarioModificacion(n.getUsuarioModificacion());
            NivelesAnalitosControl nivelActualizado = repository.save(encontrado);


            System.out.println("DETALLE DE LA INSERCION:");
            System.out.println("-------------------------------------------------");
            System.out.println("NIVEL-ANALITO-CONTROL ALMACENADO Y ACTUALZIADO :" + nivelActualizado.getIdNivelesAnalitosControl());
            System.out.println("MediaObjetivo:" + nivelActualizado.getMediaObjetivo());
            System.out.println("DesviacionEstandarObjetivo:" + nivelActualizado.getDesviacionEstandarObjetivo());;
            System.out.println("-------------------------------------------------");

            return nivelActualizado;



        }

        return  null;

    }


    public NivelesAnalitosControl seleccionarNivelEspecifico(Long idNivelesAnalitosControl){
        if (idNivelesAnalitosControl==null){
            return null;
        }
        Optional<NivelesAnalitosControl>  buscado = repository.findById(idNivelesAnalitosControl);
        NivelesAnalitosControl encontrado;
        if (buscado.isPresent()) {
            encontrado = buscado.get();
            return  encontrado;
        }else  {
            return  null;
        }
    }

}
