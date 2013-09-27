package alquiler;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.DescribedAs;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.ObjectType;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.util.TitleBuffer;
import org.apache.isis.applib.annotation.MemberGroups;

import cliente.Cliente;
import cliente.ClienteServicio;



import categoria.Categoria;
import categoria.CategoriaServicio;

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY)
@javax.jdo.annotations.Version(strategy=VersionStrategy.VERSION_NUMBER, column="VERSION")
@MemberGroups({"Cliente", "Categoria"})
@AutoComplete(repository=CategoriaServicio.class, action="autoComplete")


@ObjectType("ALQUILER")
public class Alquiler {
	
	@Named("Alquiler")
	// {{ Identification on the UI	
	public String title() {
		final TitleBuffer buf = new TitleBuffer();
		buf.append("ALQUILER");	       
		return buf.toString();	
	}
	// }}
	
	// {{ {{ OwnedBy (property)	
	private String ownedBy;
	@Hidden 
	public String getOwnedBy() {
	    return ownedBy;	
	}
	public void setOwnedBy(final String ownedBy){
	    this.ownedBy = ownedBy;	
	}	
	// }}
	
	// {{ Categoria
	@Persistent
	private Categoria categoria;
	@DescribedAs("La categoria del vehiculo.")
	@RegEx(validation = "\\w[@&:\\-\\,\\.\\+ \\w]*")
	@MemberOrder(name="Categoria",sequence="1")	
	public Categoria getCategoria() {
		return categoria;
	}	
	public void setCategoria(final Categoria categoria)	{		
		this.categoria=categoria;
	}	
	// }}
	
	// {{ Cliente
	
	@Persistent	
	private Cliente clienteId;
	@DescribedAs("Numero de CUIL/CUIT")
	@RegEx(validation = "\\w[@&:\\-\\,\\.\\+ \\w]*")
	@MemberOrder(name="Cliente",sequence="2")	
	public Cliente getClienteId() {
		return clienteId;
	}	
	public void setClienteId(final Cliente clienteId)	{		
		this.clienteId=clienteId;
	}	
	// }}
	
    // {{ Campo Activo
   	private boolean activo;
   	@Hidden
   	@DescribedAs("Activo")
   	@MemberOrder(sequence="12")
   	public boolean getActivo() {
   		return activo; 
   	}   	
   	public void setActivo(boolean activo){
   		this.activo=activo; 
   	}	
    // }}
   	   	
   	//{{ Remove   	
   	public void remove(){
   		setActivo(false);
   	}   	
   	//}}
	
    // {{ injected: DomainObjectContainer
    @SuppressWarnings("unused")
    private DomainObjectContainer container;
    
    public void setDomainObjectContainer(final DomainObjectContainer container) {
        this.container = container;
       
    }
    // }}	
}
