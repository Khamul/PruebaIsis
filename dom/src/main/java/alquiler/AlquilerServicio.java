package alquiler;


import java.util.List;



import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.filter.Filter;

import categoria.Categoria;

import cliente.Cliente;


import com.google.common.base.Objects;

import alquiler.Alquiler;


@Named("Alquiler")
public class AlquilerServicio extends AbstractFactoryAndRepository{
	/*
	// {{ 
	// Carga de Alquiler
	public Alquiler CargarAlquiler(
			@Named("Categoria") Categoria categoria,			
			@Named("Nombre") String nombre,
			@Named("Apellido") String apellido,
			@Named("Tipo de Id Tributaria") TipoId tipo,
			@Named("Numero") int numeroId,
			@Named("Numero de Telefono") int numeroTel,
			@Named("Correo Electrónico") String mail,
			@Named("Tipo de Pago") TipoPago tipoPago,
			@Named("Numero de Recibo") int numeroRecibo) {
		final boolean activo = true;
		final String ownedBy = currentUserName();
		return elAlq(categoria, numeroId, nombre, apellido, tipo, numeroId, numeroTel, mail, tipoPago, numeroRecibo,activo, ownedBy);

	}
	// }}
	// {{
	@Hidden
	// for use by fixtures
	public Alquiler elAlq(final Categoria categoria, 
			final int numero,
			final String nombre, 
			final String apellido, 
			final TipoId tipo,
			final int numeroId, 
			final int numeroTel, 
			final String mail, 
			final TipoPago tipoPago, 
			final int numeroRecibo,			
			final boolean activo,
			final String userName) {
		final Alquiler alquiler = newTransientInstance(Alquiler.class);
		alquiler.setCategoria(categoria);
		alquiler.setCliente(numeroId);
		alquiler.setOwnedBy(userName);
		alquiler.setActivo(true);
		final List<Cliente> mismoNumDoc = allMatches(Cliente.class,
				new Filter<Cliente>() {
					@Override
					public boolean accept(final Cliente cliente) {
						return Objects.equal(cliente.getNumeroId(), numeroId);
					}
				});

		Cliente cliente = newTransientInstance(Cliente.class);

		if (mismoNumDoc.size() == 0) {

			cliente.setNombre(nombre);
			cliente.setApellido(apellido);
			cliente.setTipoId(tipo);
			cliente.setNumeroId(numeroId);
			cliente.setNumeroTel(numeroTel);
			cliente.setEmail(mail);
			cliente.setTipoPago(tipoPago);
			cliente.setNumeroRecibo(numeroRecibo);
			cliente.setActivo(true);
			cliente.setOwnedBy(userName);
		
			persistIfNotAlready(alquiler);
			persistIfNotAlready(cliente);

		} else {
			cliente = null;
			getContainer().warnUser("YA SE ENCUENTRA EL CLIENTE CARGADO");
			
		}
		
		
		return alquiler;
	}
	// }}
	*/
		
	// {{ 
	// Carga de Alquiler
	public Alquiler CargarAlquiler(
			@Named("Categoria") Categoria categoria,			
			@Named("Numero Cuil/Cuit") Cliente id) {
		final boolean activo = true;
		final String ownedBy = currentUserName();
		return elAlq(categoria, id ,activo, ownedBy);

	}
	// }}
	// {{
	@Hidden
	// for use by fixtures
	public Alquiler elAlq(final Categoria categoria, 
			final Cliente id,			
			final boolean activo,
			final String userName) {
		final Alquiler alquiler = newTransientInstance(Alquiler.class);
		alquiler.setCategoria(categoria);
		alquiler.setClienteId(id);
		alquiler.setOwnedBy(userName);
		alquiler.setActivo(true);
		
		persistIfNotAlready(alquiler);
		
		return alquiler;
	}
	// }}
	
	// {{ complete (action)
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Alquiler> AlquileresActivos() {
		List<Alquiler> items = doComplete();
		if (items.isEmpty()) {
			getContainer().informUser("No hay alquileres activos :-(");
		}
		return items;
	}

	protected List<Alquiler> doComplete() {
		return allMatches(Alquiler.class, new Filter<Alquiler>() {
			@Override
			public boolean accept(final Alquiler t) {
				return t.getActivo();
			}
		});
	}
	// }}	
		
	// {{ Helpers
	protected boolean ownedByCurrentUser(final Alquiler t) {
	    return Objects.equal(t.getOwnedBy(), currentUserName());
	}
	protected String currentUserName() {
	    return getContainer().getUser().getName();
	}
	// }}
}
