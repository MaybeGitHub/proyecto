package baseDatosOracle;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Usuario {
	
	@OneToMany(mappedBy="usuario")
	private List<Heroe> heroes;
	
	@OneToMany(mappedBy="usuario")
	private List<Chat> mensajes;
	
	@Id @Column private String nombre;
	@Column private String password;
	@Column private boolean conectado;
	@Column private boolean jugando;
	
	public Usuario(String nombre, String password, boolean conectado) {
		this.setNombre(nombre);
		this.setPassword(password);
		this.setConectado(conectado);
	}

	public Usuario(){		
	}
	
	public List<Heroe> getHeroes() {
		if (heroes == null) {
			heroes = new ArrayList<Heroe>();			
		}
		return heroes;
	}
	
	public void setHeroes(List<Heroe> heroes) {
		this.heroes = heroes;
	}
	
	public void addHeroe( Heroe heroe ){
		if( !getHeroes().contains(heroe) ){
			getHeroes().add(heroe);
		}
	}
	
	public void removeHeroe( Heroe heroe ){
		getHeroes().remove(heroe);
	}	
	
	public List<Chat> getMensajes() {
		if (mensajes == null) {
			mensajes = new ArrayList<Chat>();			
		}
		return mensajes;
	}
	
	public void setMensaje(List<Chat> mensaje) {
		this.mensajes = mensaje;
	}
	
	public void addMensaje( Chat mensaje ){
		if( !getMensajes().contains(mensaje) ){
			getMensajes().add(mensaje);
		}
	}
	
	public void removeMensaje( String mensaje ){
		getMensajes().remove(mensaje);
	}	

	public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isConectado() {
		return conectado;
	}

	public void setConectado(boolean conectado) {
		this.conectado = conectado;
	}

	public void setMensajes(List<Chat> mensajes) {
		this.mensajes = mensajes;
	}
	
	@Override
	public String toString() {
		return getNombre();
	}

	public boolean isJugando() {
		return jugando;
	}

	public void setJugando(boolean jugando) {
		this.jugando = jugando;
	}
}

