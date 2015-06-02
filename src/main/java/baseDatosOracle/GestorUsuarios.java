package baseDatosOracle;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class GestorUsuarios {

	private EntityManager em;

	public GestorUsuarios( EntityManager em ){
		this.em = em;
	}

	public void update(Usuario a) {
		em.persist(a);
	}

	public void insert(Usuario a)  {
		em.persist(a);
	}

	public void delete(Usuario a)  {
		em.remove(a);
	}

	public Usuario findByPrimaryKey(String nombre)  {
		TypedQuery<Usuario> query = em.createQuery("from Usuario where nombre=:nombre", Usuario.class);
		query.setParameter("nombre", nombre );
		ArrayList<Usuario> resultList = new ArrayList<Usuario>();
		resultList.addAll(query.getResultList());
		if ( resultList.size() != 0 ){
			return resultList.get(0);
		}else{
			return null;
		}
	}

	public ArrayList<Usuario> findByConectado(boolean conectado)  {
		TypedQuery<Usuario> query = em.createQuery("from Usuario where conectado=:conectado", Usuario.class);
		query.setParameter("conectado", conectado );
		ArrayList<Usuario> resultList = new ArrayList<Usuario>();
		resultList.addAll(query.getResultList());
		return resultList;
	}
	
	public ArrayList<Usuario> findByJugando(boolean jugando)  {
		TypedQuery<Usuario> query = em.createQuery("from Usuario where jugando=:jugando", Usuario.class);
		query.setParameter("jugando", jugando );
		ArrayList<Usuario> resultList = new ArrayList<Usuario>();
		resultList.addAll(query.getResultList());
		return resultList;
	}
}
