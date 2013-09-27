package cliente;



import javax.jdo.annotations.IdentityType;

import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;

import org.apache.isis.applib.annotation.DescribedAs;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ObjectType;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.filter.Filter;
import org.apache.isis.applib.util.TitleBuffer;


import com.google.common.base.Objects;



@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY)
@javax.jdo.annotations.Queries({ @javax.jdo.annotations.Query(name = "listado_cliente", language = "JDQL", value = "SELECT * FROM dom.cliente.Cliente WHERE activo== :true ") })
@javax.jdo.annotations.Version(strategy = VersionStrategy.VERSION_NUMBER, column = "VERSION")
@ObjectType("Cliente")

//@AutoComplete(repository = LocalidadServicio.class, action = "autoComplete")
public class Cliente {

	public static enum TipoId {
		CUIL, CUIT;
	}
	public static enum TipoPago{
		EFECTIVO, CHEQUE, TARJETA_CREDITO, TARJETA_DEBITO;
	}

	// {{ Identification on the UI
	public String title() {
		final TitleBuffer buf = new TitleBuffer();
		buf.append(getNumeroId());
		return buf.toString();
	}
	// }}

	// {{ OwnedBy (property)
	private String ownedBy;

	@Hidden
	public String getOwnedBy() {
		return ownedBy;
	}
	public void setOwnedBy(final String ownedBy) {
		this.ownedBy = ownedBy;
	}
	// }}

	// {{ Nombre
	private String nombre;

	@RegEx(validation = "\\w[@&:\\-\\,\\.\\+ \\w]*")
	@MemberOrder(sequence = "1")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	// }}

	// {{ Apellido
	private String apellido;

	@RegEx(validation = "\\w[@&:\\-\\,\\.\\+ \\w]*")
	@MemberOrder(sequence = "2")
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	// }}

	// {{ Tipo de Identificacion Tributaria
	private TipoId tipo;

	@DescribedAs("Señala el tipo de documento")
	@RegEx(validation = "\\w[@&:\\-\\,\\.\\+ \\w]*")
	@MemberOrder(sequence = "3")
	public TipoId getTipoId() {
		return tipo;
	}
	public void setTipoId(TipoId tipo) {
		this.tipo = tipo;
	}
	// }}

	// {{ Numero de Identificacion Tributaria
	private int numeroId;

	@RegEx(validation = "\\w[@&:\\-\\,\\.\\+ \\w]*")
	@MemberOrder(sequence = "4")
	public int getNumeroId() {
		return numeroId;
	}
	public void setNumeroId(int numeroId) {
		this.numeroId = numeroId;
	}
	// }}
	
	// {{ Numero de telefono
	private int numeroTel;

	@RegEx(validation = "\\w[@&:\\-\\,\\.\\+ \\w]*")
	@MemberOrder(sequence = "5")
	public int getNumeroTel() {
		return numeroTel;
	}
	public void setNumeroTel(int numeroTel) {
		this.numeroTel = numeroTel;
	}
	// }}	
	
	// {{ Correo electronico
	private String mail;

	@RegEx(validation = "\\w[@&:\\-\\,\\.\\+ \\w]*")
	@MemberOrder(sequence = "6")
	public String getEmail() {
		return mail;
	}
	public void setEmail(String mail) {
		this.mail = mail;
	}
	// }}	
	
	// {{ Tipo de Pago
	private TipoPago tipoPago;

	@RegEx(validation = "\\w[@&:\\-\\,\\.\\+ \\w]*")
	@MemberOrder(sequence = "7")
	public TipoPago getTipoPago() {
		return tipoPago;
	}
	public void setTipoPago(TipoPago tipoPago) {
		this.tipoPago = tipoPago;
	}
	// }}	
	
	// {{ Numero de Recibo
	private int recibo;

	@RegEx(validation = "\\w[@&:\\-\\,\\.\\+ \\w]*")
	@MemberOrder(sequence = "8")
	public int getNumeroRecibo() {
		return recibo;
	}
	public void setNumeroRecibo(int recibo) {
		this.recibo = recibo;
	}
	// }}	
		
	// {{ Activo
	private boolean activo;

	@Hidden
	@MemberOrder(sequence = "9")
	public boolean getActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	// }}

	// {{ Remove (action)
	public void remove() {
		setActivo(false);
	}
	// }}

	// {{ Filtro
	public static Filter<Cliente> thoseOwnedBy(final String currentUser) {
		return new Filter<Cliente>() {
			@Override
			public boolean accept(final Cliente cliente) {
				return Objects.equal(cliente.getOwnedBy(), currentUser);
			}
		};
	}
	// }}

	// {{ injected: DomainObjectContainer
	private DomainObjectContainer container;

	protected DomainObjectContainer getContainer() {
		return container;
	}
	public void setDomainObjectContainer(final DomainObjectContainer container) {
		this.container = container;
	}
	// }}

}