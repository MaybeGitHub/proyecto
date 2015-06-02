package habilidades;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import armas.Arma;
import baseDatosOracle.Heroe;
import datos.Enemigo;

public class Torbellino extends Habilidad {

	public String descripcionHeroe() {
		return "Giras entre tus enemigos para golpearlos a todos con tu arma";
	}

	@Override
	public String nombre() {
		return "Torbellino";
	}

	public String mecanica(Heroe heroe, ArrayList<Enemigo> enemigos) {
		Arma arma = Arma.creaAPartirDeTipoBBDD(heroe.getArma());
		for ( Enemigo enemigo : enemigos ){
			int da�oHeroe = heroe.getFuerza()*2 + heroe.getDestreza()*2 + arma.da�o();
			int resistenciaEnemigo = enemigo.getEscudo().bonusDef() + enemigo.getArmadura().bonusArmor() + enemigo.getDestreza();			
			int da�oTotal = da�oHeroe - resistenciaEnemigo;
			if ( da�oTotal > 0 ){				
				enemigo.setPV(enemigo.getPV() - da�oTotal );	
			}
		}
		return descripcionHeroe();
	}

	@Override
	public Icon icon() {
		return new ImageIcon(Torbellino.class.getResource("/imagenes/habilidades/Torbellino.png"));
	}
}
