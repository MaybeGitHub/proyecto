package baseDatosOracle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;

import escudos.Escudo;
import escudos.Sin_Escudo;
import armaduras.Armadura;
import armaduras.Sin_Armadura;
import armas.Arma;
import armas.Sin_Arma;

@Entity
public class Heroe {
	
	@Id
	@OrderBy
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private int idHeroe;
	
	@ManyToOne
    @JoinColumn(name="dueno")
	private Usuario usuario;
	
	@Column private String nombre;
	@Column @Lob private byte[] imagen;
	@Column private int nivel;
	@Column private int fuerza;
	@Column private int destreza;
	@Column private int inteligencia;
	@Column private int pvMax;
	@Column private int pv;
	@Column private int experiencia;
	@Column private int experienciaMax;
	@Column private int sala;
	@Column private String arma = Sin_Arma.TIPOBBDD; 
	@Column private String armadura = Sin_Armadura.TIPOBBDD;
	@Column private String escudo = Sin_Escudo.TIPOBBDD;	
	@Column private int defensa;
	@Column private int ataque;
	
	public Heroe(String nombre, Usuario usuario, byte[] icono, int nivel, int fuerza, int destreza, int inteligencia, int PVMax, int PV, int experiencia, int experienciaMax, int sala, String arma, String armadura, String escudo) {
		
		this.setNombre(nombre);
		this.setUsuario(usuario);
		this.setImagen(icono);
		this.setNivel(nivel);
		this.setFuerza(fuerza);
		this.setDestreza(destreza);
		this.setInteligencia(inteligencia);
		this.setPVMax(PVMax);
		this.setPV(PV);
		this.setExperienciaMax(experienciaMax);
		this.setExperiencia(experiencia);
		this.setSala(sala);
		this.setArma(arma);
		this.setArmadura(armadura);
		this.setEscudo(escudo);
	}

	public Heroe() {
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public String getNombre() {
        return nombre;
    }

    public void setNombre(String name) {
        this.nombre = name;
    }

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public int getFuerza() {
		return fuerza;
	}

	public void setFuerza(int fuerza) {
		this.fuerza = fuerza;
	}

	public int getDestreza() {
		return destreza;
	}

	public void setDestreza(int destreza) {
		this.destreza = destreza;
	}

	public int getInteligencia() {
		return inteligencia;
	}

	public void setInteligencia(int inteligencia) {
		this.inteligencia = inteligencia;
	}

	public int getPVMax() {
		return pvMax;
	}

	public void setPVMax(int pvMax) {
		this.pvMax = pvMax;
	}

	public int getPV() {
		return pv;
	}

	public void setPV(int pv) {
		if ( pv <= 0 ){
			pv = 0;
		}else if ( pv >= getPVMax() ){
			pv  = getPVMax();
		}		
		this.pv = pv;
	}

	public int getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(int experiencia) {
		if ( experiencia >= getExperienciaMax() ){
			experiencia %= getExperienciaMax();
			setNivel(getNivel() + 1);
			setFuerza(getFuerza() + 1);
			setDestreza(getDestreza() + 1);			
			setInteligencia(getInteligencia() + 1);
			setPVMax(getPVMax() + 25);			
			setExperienciaMax(getExperienciaMax() + 15 * getNivel());
			setPV(getPVMax());
		}
		this.experiencia = experiencia;
	}

	public int getExperienciaMax() {
		return experienciaMax;
	}

	public void setExperienciaMax(int experienciaMax) {
		this.experienciaMax = experienciaMax;
	}

	public int getSala() {
		return sala;
	}

	public void setSala(int sala) {
		this.sala = sala;
	}

	public String getArma() {
		return arma;
	}

	public void setArma(String arma) {
		this.arma = arma;
		setAtaque(Arma.creaAPartirDeTipoBBDD(arma).dano());
	}

	public String getArmadura() {
		return armadura;
	}

	public void setArmadura(String armadura) {
		this.armadura = armadura;
		setDefensa(Armadura.creaAPartirDeTipoBBDD(armadura).bonusArmor() + Escudo.creaAPartirDeTipoBBDD(escudo).bonusDef());
	}

	public String getEscudo() {
		return escudo;
	}

	public void setEscudo(String escudo) {
		this.escudo = escudo;
		setDefensa(Armadura.creaAPartirDeTipoBBDD(armadura).bonusArmor() + Escudo.creaAPartirDeTipoBBDD(escudo).bonusDef());
	}
	
	public int getDefensa() {
		return defensa;
	}

	public void setDefensa(int defensa) {
		this.defensa = defensa;
	}

	public int getAtaque() {
		return ataque;
	}

	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}

	public int getIdHeroe() {
		return idHeroe;
	}

	public void setIdHeroe(int idHeroe) {
		this.idHeroe = idHeroe;
	}
	
	@Override
	public String toString() {
		return getNombre();
	}

	public String getFrase1() {
		return "Te he derrotado una vez y volvere a hacerlo tantas veces como haga falta, porque no eres mas que un simple orco. Luchemos !!! ";
	}

	public String getFrase2() {
		return "Da igual cuantas veces te transformes... No conseguiras dominar mi mente con sucios trucos. Preparate porque ha llegado tu hora !!!";
	}
}

