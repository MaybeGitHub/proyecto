package baseDatosOracle;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Chat {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private int idChat;
	
	@ManyToOne
    @JoinColumn(name="dueno")
	private Usuario usuario;
	
	@Column private String mensaje;
	@Column private Date fecha;
	@Column private boolean leido;	
	
	public Chat(Usuario receptor, String mensaje, Date fecha, boolean leido) {
		this.setUsuario(receptor);
		this.setMensaje(mensaje);
		this.setFecha(fecha);
		this.setLeido(leido);
	}
	
	public Chat() {
	} 
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario( Usuario usuario) {
		this.usuario = usuario;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean isLeido() {
		return leido;
	}

	public void setLeido(boolean leido) {
		this.leido = leido;
	}
}