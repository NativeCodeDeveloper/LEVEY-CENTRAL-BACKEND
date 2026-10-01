package cl.leveyqc.leveyqc.Controles.service;
import cl.leveyqc.leveyqc.Controles.model.Controles;
import cl.leveyqc.leveyqc.Controles.repository.ControlesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ControlesService {

    private final ControlesRepository repository;
    public ControlesService(ControlesRepository repository) {
        this.repository = repository;
    }


// =========================================================
// VALIDACION DE INSERT
// =========================================================
    private Controles validacionActualizacion(Controles c){
        if (c==null){
            return null;
        }
        if (c.getIdControl()==null){
            return null;
        }
        if (c.getNombreControl()==null || c.getNombreControl().isBlank()){
            return null;
        }
        if (c.getNumeroLote()==null || c.getNumeroLote().isBlank()){
            return null;
        }
        if (c.getIdProveedor()==null){
            return null;
        }
        if (c.getIdMatriz()==null){
            return null;
        }
        if (c.getCategoriaId()==null){
            return null;
        }
        if (c.getFechaCaducidad()==null){
            return  null;
        }
        if (c.getUnidadesStock()==null || c.getUnidadesStock() < 0){
            return null;
        }
        if (c.getUsuarioModificacion()==null || c.getUsuarioModificacion().isBlank()){
            return null;
        }
        return c;
    }





    private Controles validacionCreacion(Controles c){
        if (c==null){
            return null;
        }
        if (c.getNombreControl()==null || c.getNombreControl().isBlank()){
            return null;
        }
        if (c.getNumeroLote()==null || c.getNumeroLote().isBlank()){
            return null;
        }
        if (c.getIdProveedor()==null){
            return null;
        }
        if (c.getIdMatriz()==null){
            return null;
        }
        if (c.getCategoriaId()==null){
            return null;
        }
        if (c.getFechaCaducidad()==null){
            return  null;
        }
        if (c.getUnidadesStock()==null || c.getUnidadesStock() < 0){
            return null;
        }
        if (c.getActivo()==null){
            return  null;
        }

        if (c.getUsuarioCreacion()==null || c.getUsuarioCreacion().isBlank()){
            return null;
        }
        return c;
    }



// =========================================================
// 1. INSERTAR CONTROL
// + insertar(control: Controles): Controles
// =========================================================
    public Controles crear(Controles c){
       Controles controlNuevo =  validacionCreacion(c);
       if (controlNuevo==null){
           return null;
       }else {
           return repository.save(controlNuevo);
       }
    }


// =========================================================
// 2. SELECCIONAR TODOS LOS CONTROLES
// + seleccionarTodos(): List<Controles>
// =========================================================
public List<Object[]> listarTodos (){
    return repository.SeleccionarTodos();
}


// =========================================================
// 3. ACTUALIZAR CONTROL
// + actualizar(control: Controles): Controles
// =========================================================
    public Controles actualizar(Controles c){
       Controles controlCamposValidados = validacionActualizacion(c);
       if (controlCamposValidados==null){
           return null;
       }
       Optional<Controles> controlBuscado = repository.findById(controlCamposValidados.getIdControl());
       Controles controlEncontrado;
       if(controlBuscado.isPresent()){
           controlEncontrado = controlBuscado.get();
           controlEncontrado.setNombreControl(controlCamposValidados.getNombreControl());
           controlEncontrado.setNumeroLote(controlCamposValidados.getNumeroLote());
           controlEncontrado.setIdProveedor(controlCamposValidados.getIdProveedor());
           controlEncontrado.setCategoriaId(controlCamposValidados.getCategoriaId());
           controlEncontrado.setFechaCaducidad(controlCamposValidados.getFechaCaducidad());
           controlEncontrado.setUnidadesStock(controlCamposValidados.getUnidadesStock());
           return repository.save(controlEncontrado);
       }else  {
           return null;
       }
    }



    // =========================================================
// 4. SELECCIONAR ESPECIFICO POR SU ID
// + seleccionarActivos(): List<Object[]>
// =========================================================
    public Controles seleccionarPorId(Long idControl){
        Optional<Controles> controlBuscado = repository.findById(idControl);
        Controles controlEncontrado;

        if (controlBuscado.isPresent()){
            controlEncontrado = controlBuscado.get();
            return  controlEncontrado;
        }else  {
            return null;
        }
    }


// =========================================================
// 4. SELECCIONAR CONTROLES ACTIVOS
// + seleccionarActivos(): List<Object[]>
// =========================================================
    public List<Object[]> listaControlesActivos(){
        return repository.SeleccionarPorEstado(1);
    }


// =========================================================
// 5. SELECCIONAR CONTROLES DESACTIVADOS
// + seleccionarDesactivados(): List<Object[]>
// =========================================================
public List<Object[]> listaControlesInactivos(){
    return repository.SeleccionarPorEstado(0);
}


// =========================================================
// 6. ACTIVAR CONTROL
// + activar(idControl: Long): Controles
// =========================================================
public Boolean activar(Long idControl){
        if (idControl==null){
            return false;
        }
    Optional<Controles> controlBuscado = repository.findById(idControl);
    Controles controlEncontrado;
    if (controlBuscado.isPresent()){
        controlEncontrado = controlBuscado.get();
        controlEncontrado.setActivo(1);
        repository.save(controlEncontrado);
        return true;
    }else{
        return false;
    }
}

// =========================================================
// 7. DESACTIVAR CONTROL
// + desactivar(idControl: Long): Controles
// =========================================================
public Boolean desactivar(Long idControl){
    if (idControl==null){
        return false;
    }
    Optional<Controles> controlBuscado = repository.findById(idControl);
    Controles controlEncontrado;
    if (controlBuscado.isPresent()){
        controlEncontrado = controlBuscado.get();
        controlEncontrado.setActivo(0);
        repository.save(controlEncontrado);
        return true;
    }else{
        return false;
    }
}


// =========================================================
// 8. SELECCIONAR POR SIMILITUD DE NOMBRE
// + seleccionarPorSimilitudNombre(nombre: String): List<Object[]>
// =========================================================
public List<Object[]> buscarPorNombreControl(String nombreControl){
    return repository.SeleccionarPorSimilitudNombreControl(nombreControl);
}


// =========================================================
// 9. SELECCIONAR POR CATEGORÍA
// + seleccionarPorCategoria(categoriaId: Long): List<Object[]>
// =========================================================
public List<Object[]> buscarPorCategoria(Long categoriaId){
    return repository.SeleccionarPorCategoriaId(categoriaId);
}

// =========================================================
// 10. BUSCAR POR NÚMERO DE LOTE
// + buscarPorLote(numeroLote: String): List<Object[]>
// =========================================================

    public List<Object[]> SeleccionarPorSimilitudNumeroLote(String numeroLote){
        return repository.SeleccionarPorSimilitudNumeroLote(numeroLote);
    }


    // =========================================================
// 11. SELECCIONAR POR MATRIZ
// + seleccionarPorMatriz(idMatriz: String): List<Object[]>
// =========================================================
public List<Object[]> buscarPorMatrizId(Long idMatriz){
    return repository.SeleccionarPorMatriz(idMatriz);
}

// =========================================================
// 12. SELECCIONAR POR FECHA DE INGRESO
// + seleccionarPorFechaIngreso(fecha: LocalDate): List<Object[]>
// =========================================================


// =========================================================
// 13. SELECCIONAR POR FECHA DE CADUCIDAD
// + seleccionarPorFechaCaducidad(fecha: LocalDate): List<Object[]>
// =========================================================
}
