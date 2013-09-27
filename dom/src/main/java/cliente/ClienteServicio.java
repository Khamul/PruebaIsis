package cliente;


import java.util.List;


import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;

import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.filter.Filter;


import com.google.common.base.Objects;

import cliente.Cliente.TipoId;
import cliente.Cliente.TipoPago;


@Named("Cliente")
public class ClienteServicio extends AbstractFactoryAndRepository {
	
	//Carga de clientes
	@MemberOrder(sequence = "1")
	public Cliente CargarCliente(@Named("Nombre") String nombre,
			@Named("Apellido") String apellido,
			@Named("Tipo de Id Tributaria") TipoId tipo,
			@Named("Numero") int numeroId,
			@Named("Numero de Telefono") int numeroTel,
			@Named("Correo Electrónico") String mail,
			@Named("Tipo de Pago") TipoPago tipoPago,
			@Named("Numero de Recibo") int numeroRecibo){
		final String ownedBy = currentUserName();
		final boolean activo = true;
		return elCliente(nombre, apellido, tipo, numeroId, numeroTel, mail, tipoPago, numeroRecibo,
				ownedBy, activo);
	}	
	@Hidden
	public Cliente elCliente(
			final String nombre, 
			final String apellido, 
			final TipoId tipo,
			final int numeroId, 
			final int numeroTel, 
			final String mail, 
			final TipoPago tipoPago, 
			final int numeroRecibo,
			final String userName, 
			final boolean activo) {		
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
			cliente.setOwnedBy(userName);
			cliente.setActivo(true);
			
			
			persistIfNotAlready(cliente);			
			
		} else {
			cliente = null;
			getContainer().warnUser("YA SE ENCUENTRA EL CLIENTE CARGADO");
			System.out.println("YA SE ENCUENTRA EL CLIENTE CARGADO");
			
		}

		return cliente;
	} 
	
	// {{ complete (action)
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Cliente> ClienteActivos() {
		List<Cliente> items = doComplete();
		if (items.isEmpty()) {
			getContainer().informUser("No hay clientes activos :-(");
		}
		return items;
	}

	protected List<Cliente> doComplete() {
		return allMatches(Cliente.class, new Filter<Cliente>() {
			@Override
			public boolean accept(final Cliente t) {
				return t.getActivo();
			}
		});
	}
	// }}
	
	@Hidden    
	public List<Cliente> autoComplete(final String cliente) {
		return allMatches(Cliente.class, new Filter<Cliente>() {
		@Override
		public boolean accept(final Cliente t) {		
		return t.getApellido().contains(cliente); 
		}
	  });				
	}
	// }}
	
	 
	// {{ Helpers
	protected boolean ownedByCurrentUser(final Cliente t) {
		return Objects.equal(t.getOwnedBy(), currentUserName());
	}
	protected String currentUserName() {
		return getContainer().getUser().getName();
	}
	// }}

}