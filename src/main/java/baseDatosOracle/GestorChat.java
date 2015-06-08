package baseDatosOracle;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class GestorChat {

	private EntityManager em;

	public GestorChat( EntityManager em ){
		this.em = em;
	}
	
	public void update(Chat chat) {
		em.persist(chat);
	}

	public void insert(Chat chat)  {
		em.persist(chat);
	}

	public void delete(Chat chat)  {
		em.remove(chat);
	}

	public ArrayList<Chat> findByUsuarioYNoLeido(Usuario usuario, boolean leido)  {
		TypedQuery<Chat> query = em.createQuery("from Chat where dueno=:usuario and leido=:leido", Chat.class);
		query.setParameter("usuario", usuario );
		query.setParameter("leido", leido);
		ArrayList<Chat> resultList = new ArrayList<Chat>();
		resultList.addAll(query.getResultList());
		return resultList;
	}
}
