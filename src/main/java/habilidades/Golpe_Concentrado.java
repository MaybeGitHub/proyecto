package habilidades;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import baseDatosOracle.Heroe;
import datos.Enemigo;

public class Golpe_Concentrado extends Habilidad {

	private String descripcionHeroe(int dano) {
		return "Usas tu habilidad para buscar un hueco en sus defensas y usar un ataque imparable de dano " + dano;
	}

	private String descripcionEnemigo(Enemigo maloso, int dano) {
		return "El " + maloso.getNombre() + " aprovecha un momento de despiste para colarse entre tus defensas y hacerte " + dano + " puntos de dano.";
	}
	
	@Override
	public String nombre() {
		return "Golpe Concentrado";
	}

	public String mecanica(Enemigo enemigo, Heroe heroe, int bonusDef) {
		int defensaEquipo = heroe.getDefensa();
		int resistencia = heroe.getDestreza() + defensaEquipo;
		
		int dano = (enemigo.getFuerza()*2 + enemigo.getDestreza()*2 + enemigo.getArma().dano()*2 - resistencia) / bonusDef;
		heroe.setPV( heroe.getPV() - dano );
		return descripcionEnemigo(enemigo, dano);
	}

	public String mecanica(Heroe heroe, Enemigo enemigo) {
		int resistencia = enemigo.getDestreza() + enemigo.getEscudo().bonusDef() + enemigo.getArmadura().bonusArmor();
		int dano = heroe.getFuerza()*2 + heroe.getDestreza()*2 + heroe.getAtaque() - resistencia;
		enemigo.setPV( enemigo.getPV() - dano );
		return descripcionHeroe(dano);		
	}

	@Override
	public Icon icon() {
		return new ImageIcon(Golpe_Concentrado.class.getResource("/imagenes/habilidades/Golpe Concentrado.png"));
	}
}
