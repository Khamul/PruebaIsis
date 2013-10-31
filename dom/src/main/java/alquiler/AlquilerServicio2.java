package alquiler;

import java.util.List;
import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.query.QueryDefault;
import org.joda.time.LocalDate;

import cliente.Cliente;
import disponibles.AutosPorFecha;
import disponibles.Disponibles;

public class AlquilerServicio2 extends AbstractFactoryAndRepository{

    @Named("Reservar")
    @MemberOrder(sequence="1")
    public Alquiler2 reservar(
                    @Named("Cliente") Cliente cliente
                    ) {
            
            Alquiler2 alquiler = newTransientInstance(Alquiler2.class);
            persistIfNotAlready(alquiler);
            
            List<Disponibles> disponibilidad = listaAutosReservados();
            
            return crear(alquiler,disponibilidad,cliente);
    }
    
    private Alquiler2 crear(
    		final Alquiler2 alquiler,
    		final List<Disponibles> disponibilidad,
    		final Cliente cliente		
    		){
    		if(disponibilidad.size()>0){
    			alquiler.setClienteId(cliente);
    			alquiler.setFecha(LocalDate.now().toDate());
    			alquiler.setNombreCliente(cliente.getNombre());
    			alquiler.setApellidoCliente(cliente.getApellido());
    			
    			for (Disponibles disp:disponibilidad){
    				if (disp.estaSeleccionada()){
    					AutosPorFecha autoF=newTransientInstance(AutosPorFecha.class);
    					autoF.setFecha(disp.getFecha());
    					autoF.setCategoria(disp.getCategoria());
    					autoF.setPatente(disp.getPatente());
    					autoF.setAlquiler(alquiler);
    					autoF.setModeloAuto(disp.getModeloAuto());
    					alquiler.addToHabitacion(autoF);
    					persistIfNotAlready(autoF);
    				}  
    				getContainer().removeIfNotAlready(disp);
    			}
    			
    		}
    	return alquiler;
	}
    // }}
    
    // {{ 
    private List<Disponibles> listaAutosReservados() {         
        return allMatches(QueryDefault.create(Disponibles.class, "Disponibles"));
    } 
    // }}
    
    // {{
    @Named("Listado Alquileres")
    @MemberOrder(sequence="2")
    public List<Alquiler2> listaAlquileres() {
            return allMatches(QueryDefault.create(Alquiler2.class, "traerAlquileres"));
    }
    // }}
}
