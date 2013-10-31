package alquiler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.VersionStrategy;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Audited;
import org.apache.isis.applib.annotation.AutoComplete;

import org.apache.isis.applib.annotation.DescribedAs;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.NotPersisted;
import org.apache.isis.applib.annotation.ObjectType;
import org.apache.isis.applib.annotation.RegEx;

import org.apache.isis.applib.annotation.Where;

import org.apache.isis.applib.annotation.MemberGroups;

import cliente.Cliente;

import disponibles.AutosPorFecha;



@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.APPLICATION)
@javax.jdo.annotations.Version(strategy=VersionStrategy.VERSION_NUMBER, column="VERSION")
@MemberGroups({"Estados","Datos del Alquiler","Autos"})
//@javax.jdo.annotations.Query(name="reservas", language="JDOQL",value="SELECT FROM dom.reserva.Reserva order by numero desc")
//@AutoComplete(repository=ReservaServicio.class, action="completaReservas")
@AutoComplete(repository=AlquilerServicio.class, action="autoComplete")
@Audited 
@ObjectType("ALQUILER2")


public class Alquiler2 {
    
	public static enum EstadoAlquiler{
		RESERVADO, EN_PROCESO, FINALIZADO;
	}
	
	public static enum TipoPago{
		EFECTIVO, CHEQUE, TARJETA_CREDITO, TARJETA_DEBITO;
	}
	
	//{{Titulo
    public String title(){
            return getEstado().toString();        
    }
    //}}
    
    //{{Numero de la reserva
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long numero;
    @Disabled
    @MemberOrder(name="Datos del Alquiler",sequence="1")
    public Long getNumero() {
            return numero;
    }    
    public void setNumero(final Long numero) {
            this.numero = numero;
    }
    //}}
    
    private String nombreEstado;    
    @Named("Estado")
    @NotPersisted
    @Hidden(where=Where.ALL_TABLES)
    @MemberOrder(name="Estados",sequence="1")
    public String getNombreEstado() {
            nombreEstado = getEstado().toString();
            return nombreEstado;
    }    
    private EstadoAlquiler estado;    
    @Hidden
    public EstadoAlquiler getEstado() {
            return estado;
    }
    public void setEstado(final EstadoAlquiler estado) {
            this.estado = estado;
    }
    //}}
    
    //{{Fecha en la que se realiza la reserva
    @Named("Fecha")
    @MemberOrder(name="Datos del Alquiler",sequence="2")
    public String getFechaString() {
            if(getFecha() != null) {
                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                    return formato.format(getFecha());
            }
            return null;
    }    
    private Date fecha;
    @Hidden
    public Date getFecha() {
            return fecha;
    }
    public void setFecha(final Date fecha) {
            this.fecha = fecha;
    }
    //}}

	// {{ Precio 
	private float precio;
    @Named("Precio")
    @Hidden(where=Where.ALL_TABLES)
    @MemberOrder(name="Datos del Alquiler",sequence="3")
	public float getPrecioAlquiler(){
		return precio;
	}
	public void setPrecioAlquiler(final float precio){
		this.precio=precio;
	}	
	// }}
	
	// {{ Tipo de Pago
	private TipoPago tipoPago;
    @Named("Tipo de Pago")
    @Hidden(where=Where.ALL_TABLES)
    @MemberOrder(name="Datos del Alquiler",sequence="4")
	public TipoPago getTipoPago() {
		return tipoPago;
	}
	public void setTipoPago(final TipoPago tipoPago) {
		this.tipoPago = tipoPago;
	}
	// }}	
	
	// {{ Numero de Recibo
	private int recibo;
    @Named("Nro Recibo")
    @Hidden(where=Where.ALL_TABLES)
    @MemberOrder(name="Datos del Alquiler",sequence="5")
	public int getNumeroRecibo() {
		return recibo;
	}
	public void setNumeroRecibo(final int recibo) {
		this.recibo = recibo;
	}
	// }}	
	
	// {{ Lista de Autos 
	@Persistent(mappedBy="alquiler")
	private List<AutosPorFecha> autos = new ArrayList<AutosPorFecha>();
    public List<AutosPorFecha> getAutos() {
        return autos;
    }

    public void setAutos(List<AutosPorFecha> listaAutos) {
    	this.autos = listaAutos;
    }
    @Named("Borrar")
    @MemberOrder(name="Autos",sequence="1")
    public Alquiler2 removeFromAutos(final AutosPorFecha auto) {
            autos.remove(auto);
            container.removeIfNotAlready(auto);
            return this;
    }
    @Hidden
    public void addToHabitacion(AutosPorFecha auto) {
     if(auto == null || autos.contains(auto)) {
             return;
     }
     auto.setAlquiler(this);
     autos.add(auto);
    }
    // }}
    
	// {{ Cliente		
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
    	
    // {{ injected: DomainObjectContainer
    private DomainObjectContainer container;
    
    public void setDomainObjectContainer(final DomainObjectContainer container) {
        this.container = container;
       
    }
    // }}	
}
