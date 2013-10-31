package disponibles;




import java.util.Date;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Audited;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.ObjectType;
import org.apache.isis.applib.util.TitleBuffer;

import categoria.Categoria;
import alquiler.Alquiler2;



@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY)
@javax.jdo.annotations.Version(strategy=VersionStrategy.VERSION_NUMBER, column="VERSION")
@javax.jdo.annotations.Queries( {
    @javax.jdo.annotations.Query(
            name="Disponibles", language="JDOQL",
            value="SELECT FROM dom.disponibles.Disponibles")
})
@ObjectType("Disponibles")
@Audited


public class Disponibles {

	@Named("Auto")
	// {{ Identification on the UI
	public String title() {		
		final TitleBuffer buf = new TitleBuffer();
		buf.append(getPatente());	       
		return buf.toString();	
	}
	// }}
	    
    // {{	
    private String auto;
    @Named("Auto")
    public String getPatente(){
    	return auto;
    }
    public void setPatente(final String auto){
    	this.auto=auto;
    }
    // }}
    
    // {{
    private boolean seleccionar;    
    @Named("Seleccionada")
    public boolean estaSeleccionada() {
            return seleccionar;
    }
    public void setEstaSeleccionada(final boolean seleccionar) {
            this.seleccionar = seleccionar;
    }
    // }}
    
    // {{
    private Date fecha;
    @Named("Fecha")
    public Date getFecha() {
            return fecha;
    }
    public void setFecha(final Date fecha) {
            this.fecha = fecha;
    }
    // }}
    
    // {{
    private Categoria categoria;
    @Named("Categoria")
    public Categoria getCategoria(){
    	return categoria;
    }
    public void setCategoria(final Categoria categoria){
    	this.categoria=categoria;
    }
    // }}
    
    // {{
    private Alquiler2 alquiler;
    @Named("Estado Alquiler")
    public Alquiler2 getAlquiler(){
    	return alquiler;
    }
    public void setAlquiler(final Alquiler2 alquiler){
    	this.alquiler=alquiler;
    }
    
    // {{
    @Bulk
    @Named("Seleccionar")
    public Disponibles reserva(){
    	if (getAlquiler()==null){
    		if(estaSeleccionada()){
    			setEstaSeleccionada(false);
    		}else{
    			setEstaSeleccionada(true);
    		}
    	}    	
    	return this;    	
    }
    // }}
    
    public String disableReserva() {
    return seleccionar ? "Ya esta seleccionada!" : null;
    }  
    
    // {{ Inyeccion del Servicio
    @SuppressWarnings("unused")
	private DisponiblesServicio servicio;
    public void injectDisponiblesServicio(final DisponiblesServicio serv){
    	this.servicio=serv;
    }    
    
    // {{ injected: DomainObjectContainer 
	@SuppressWarnings("unused")
	private DomainObjectContainer container;
    
    public void injectDomainObjectContainer(final DomainObjectContainer container) {
     this.container = container;
    }
}
