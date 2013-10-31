package disponibles;

import java.util.ArrayList;
import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.filter.Filter;
import org.joda.time.LocalDate;

import autos.Auto;


@Named("Disponibles")
public class DisponiblesServicio extends AbstractFactoryAndRepository{

    @MemberOrder(sequence = "1")
    @Named("Entre fechas")
    public List<Disponibles> entreFechas(
                    @Named("Fecha de alquiler:") LocalDate fechaAlq,
                    @Named("Fecha de devolución:") LocalDate fechaDev){
            
    		List<Disponibles> listaAutosDisponibles= new ArrayList<Disponibles>();
            final List<Auto> autos = listaAutos();
            
            LocalDate fechaAux = fechaAlq;
            LocalDate hastaAux = (fechaDev!=null) ? fechaDev : fechaAlq;
            
            for(int i=0; i <= calculoDias(fechaAlq, hastaAux); i++) {
                
                for(Auto auto: autos) {                        
                   Disponibles disp = newTransientInstance(Disponibles.class);
                   
                   if(existeAlquiler(fechaAux,auto.getPatente())!=null){
                	   AutosPorFecha autoFecha=existeAlquiler(fechaAux,auto.getPatente());
                	   disp.setPatente(autoFecha.getPatente());
                	   disp.setCategoria(autoFecha.getCategoria());
                	   disp.setAlquiler(autoFecha.getAlquiler());
                   }
                   else{
                	   disp.setPatente(auto.getPatente());
                	   disp.setCategoria(auto.getCategoria());
                   }
                   disp.setFecha(fechaAux.toDate());
                   persistIfNotAlready(disp);
                   listaAutosDisponibles.add(disp);
                }
                fechaAux=fechaAlq.plusDays(i+1);
            }
    	return listaAutosDisponibles;
    }
    // }}
 
	// {{ Calculo de diferencia de dias entre fechas.
	protected int calculoDias(final LocalDate a1, final LocalDate a2){
		long inicio=a1.toDate().getTime();
		long fin =a2.toDate().getTime();
		long diferencia = fin-inicio;
		long resultado = diferencia/(24*60*60*1000);
		
		return (int) resultado;
	}
	// }}
	
	// {{ Validacion del ingreso de fechas
    public String validateEntreFechas(LocalDate desde, LocalDate hasta) {
        if(hasta == null) {
                return null;
        }
        else {
                if(hasta.isBefore(desde)||hasta.isEqual(desde)) {
                        return "La fecha hasta debe ser mayor a desde";
                }
                else {
                        return null;
                }
        }
    }
    // }}
    @Programmatic
    public List<Auto> listaAutos() {
             return allMatches(QueryDefault.create(Auto.class, "listado_autos"));        
    }
    // }}
    
    // {{
    private AutosPorFecha existeAlquiler(final LocalDate fecha,final String patente) {        
        return uniqueMatch(AutosPorFecha.class, new Filter<AutosPorFecha>(){
        @Override
           public boolean accept(AutosPorFecha auto) {                       
             return auto.getFecha().equals(fecha.toDate())&&auto.getPatente().equals(patente);
             }                                
        });                         
    }
    // }}
    
    // {{
    @Hidden
    public List<AutosPorFecha> autosAlquilados(final String patente) {
            return allMatches(AutosPorFecha.class,new Filter<AutosPorFecha>(){
                    @Override
                    public boolean accept(final AutosPorFecha auto) {
                            return auto.getPatente().contains(patente);
                    }                        
            });
    }
    //}}
}

