package habilidades;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import baseDatosOracle.Heroe;
import datos.Enemigo;

public class Golpe_Concentrado extends Habilidad {

	private String descripcionHeroe(int da�o) {
		return "Usas tu habilidad para buscar un hueco en sus defensas y usar un ataque imparable de da�o " + da�o;
	}

	private String descripcionEnemigo(Enemigo maloso, int da�o) {
		return "El " + maloso.getNombre() + " aprovecha un momento de despiste para colarse entre tus defensas y hacerte " + da�o + " puntos de da�o.";
	}
	
	@Override
	public String nombre() {
		return "Golpe Concentrado";
	}

	public String mecanica(Enemigo enemigo, Heroe heroe, int bonusDef) {
		int defensaEquipo = heroe.getDefensa();
		int resistencia = heroe.getDestreza() + defensaEquipo;
		
		int da�o = (enemigo.getFuerza()*2 + enemigo.getDestreza()*2 + enemigo.getArma().da�o()*2 - resistencia) / bonusDef;
		heroe.setPV( heroe.getPV() - da�o );
		return descripcionEnemigo(enemigo, da�o);
	}

	public String mecanica(Heroe heroe, Enemigo enemigo) {
		int resistencia = enemigo.getDestreza() + enemigo.getEscudo().bonusDef() + enemigo.getArmadura().bonusArmor();
		int da�o = heroe.getFuerza()*2 + heroe.getDestreza()*2 + heroe.getAtaque() - resistencia;
		enemigo.setPV( enemigo.getPV() - da�o );
		return descripcionHeroe(da�o);		
	}

	@Override
	public Icon icon() {
		return new ImageIcon(Golpe_Concentrado.class.getResource("/imagenes/habilidades/Golpe Concentrado.png"));
	}
}
