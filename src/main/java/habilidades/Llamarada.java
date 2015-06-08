package habilidades;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import baseDatosOracle.Heroe;
import datos.Enemigo;

public class Llamarada extends Habilidad {

	public String descripcionHeroe( Heroe heroe ) {
		return heroe.getNombre() + " lanza una llamarada que abrasa a todos los enemigos";
	}

	public String descripcionEnemigo( Enemigo enemigo, Heroe heroe  ) {
		return enemigo.getNombre() + " lanza una llamarada a " + heroe.getNombre();
	}

	@Override
	public String nombre() {
		return "Llamarada";
	}

	public String mecanica( Enemigo enemigo, Heroe heroe, int bonusDef ) {		
		int danoEnemigo = enemigo.getInteligencia()*3 + enemigo.getDestreza()*2;
		int resistenciaHeroe = heroe.getDefensa() + heroe.getDestreza();
		
		int dano = (danoEnemigo - resistenciaHeroe) / bonusDef;
		if ( dano > 0 ){			
			heroe.setPV(heroe.getPV() - dano);
		}
		return descripcionEnemigo(enemigo, heroe);
	}

	public String mecanica(Heroe heroe, ArrayList<Enemigo> enemigos) {
		for ( Enemigo enemigo : enemigos ){
			int danoHeroe = heroe.getInteligencia()*2 + heroe.getDestreza()*2;
			int resistenciaEnemigo = enemigo.getEscudo().bonusDef() + enemigo.getArmadura().bonusArmor() + enemigo.getDestreza();
			int dano = danoHeroe - resistenciaEnemigo;
			if ( dano > 0 ){				
				enemigo.setPV(enemigo.getPV() - dano );	
			}
		}
		return descripcionHeroe(heroe);
	}

	@Override
	public Icon icon() {
		return new ImageIcon(Llamarada.class.getResource("/imagenes/habilidades/Llamarada.png"));
	}
}
