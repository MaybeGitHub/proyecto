package baseDatosOracle;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class GestorHeroes {

	private EntityManager _em;

	public GestorHeroes( EntityManager em ){
		_em = em;
	}
	
	public void update(Heroe a) {
		_em.persist(a);
	}

	public void insert(Heroe a)  {
		_em.persist(a);
	}

	public void delete(Heroe a)  {
		_em.remove(a);
	}

	public Heroe findByPrimaryKey(int idHeroe)  {
		TypedQuery<Heroe> query = _em.createQuery("from Heroe where idHeroe=:idHeroe", Heroe.class);
		query.setParameter("idHeroe", idHeroe);
		ArrayList<Heroe> resultList = new ArrayList<Heroe>();
		resultList.addAll(query.getResultList());
		if ( resultList.size() != 0 ){
			return resultList.get(0);
		}else{
			return null;
		}
	}

	public List<Heroe> findByUsuario(Usuario usuario)  {
		TypedQuery<Heroe> query = _em.createQuery("from Heroe where dueño=:usuario", Heroe.class);
		query.setParameter("usuario", usuario );
		ArrayList<Heroe> resultList = new ArrayList<Heroe>();
		resultList.addAll(query.getResultList());
		return resultList;
	}
}
