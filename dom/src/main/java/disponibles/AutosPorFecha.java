package disponibles;


import java.util.Date;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Audited;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.ObjectType;
import org.apache.isis.applib.util.TitleBuffer;

import categoria.Categoria;
import alquiler.Alquiler2;



@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY)
@javax.jdo.annotations.Version(strategy=VersionStrategy.VERSION_NUMBER, column="VERSION")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(name="auto_para_alquilar", language="JDOQL",value="SELECT FROM disponiles.Disponibles WHERE estaSeleccionada == true"),
        @javax.jdo.annotations.Query(name="auto_libre", language="JDOQL",value="SELECT FROM disponibles.Disponibles WHERE estaSeleccionada == false"),
})
@ObjectType("AutosPorFecha")
@AutoComplete(repository=DisponiblesServicio.class,action="autosAlquilados")
@Audited

public class AutosPorFecha {
	@Named("Auto")
	// {{ Identification on the UI
	public String title() {		
		final TitleBuffer buf = new TitleBuffer();
		buf.append(getPatente());	       
		return buf.toString();	
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
    // }}
    
    // {{
    private String modelo;
    @Named("Modelo")
    public String getModeloAuto(){
    	return modelo;
    }
    public void setModeloAuto(final String modelo){
    	this.modelo=modelo;
    }
    // }}
    
    @SuppressWarnings("unused")
	private DomainObjectContainer container;    
    public void injectDomainObjectContainer(final DomainObjectContainer container) {
    }
    //}}
}
