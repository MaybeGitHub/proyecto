package habilidades;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import baseDatosOracle.Heroe;
import datos.Enemigo;

public class Golpe_Concentrado extends Habilidad {

	private String descripcionHeroe(int daño) {
		return "Usas tu habilidad para buscar un hueco en sus defensas y usar un ataque imparable de daño " + daño;
	}

	private String descripcionEnemigo(Enemigo maloso, int daño) {
		return "El " + maloso.getNombre() + " aprovecha un momento de despiste para colarse entre tus defensas y hacerte " + daño + " puntos de daño.";
	}
	
	@Override
	public String nombre() {
		return "Golpe Concentrado";
	}

	public String mecanica(Enemigo enemigo, Heroe heroe, int bonusDef) {
		int defensaEquipo = heroe.getDefensa();
		int resistencia = heroe.getDestreza() + defensaEquipo;
		
		int daño = (enemigo.getFuerza()*2 + enemigo.getDestreza()*2 + enemigo.getArma().daño()*2 - resistencia) / bonusDef;
		heroe.setPV( heroe.getPV() - daño );
		return descripcionEnemigo(enemigo, daño);
	}

	public String mecanica(Heroe heroe, Enemigo enemigo) {
		int resistencia = enemigo.getDestreza() + enemigo.getEscudo().bonusDef() + enemigo.getArmadura().bonusArmor();
		int daño = heroe.getFuerza()*2 + heroe.getDestreza()*2 + heroe.getAtaque() - resistencia;
		enemigo.setPV( enemigo.getPV() - daño );
		return descripcionHeroe(daño);		
	}

	@Override
	public Icon icon() {
		return new ImageIcon(Golpe_Concentrado.class.getResource("/imagenes/habilidades/Golpe Concentrado.png"));
	}
}
