package pociones;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import baseDatosOracle.Heroe;
import datos.Enemigo;

public class Pocion_Explosiva extends Pocion {

	public String descripcionHeroe() {
		return "Lanzas una pocion explosiva que hace da�o a todos los enemigos";
	}

	public String descripcionEnemigo(int da�o) {
		return "Te lanzan una pocion explosiva que te hace " + da�o + " puntos de da�o";
	}	

	@Override
	public String nombre() {
		return "Pocion Explosiva";
	}

	public String mecanica(Heroe heroe, ArrayList<Enemigo> enemigos){
		int da�o = 0;
		for ( Enemigo maloso : enemigos ){
			da�o = heroe.getDestreza()*5 - maloso.getArmadura().bonusArmor() - maloso.getEscudo().bonusDef();
			if ( da�o > 0 ){
				maloso.setPV(maloso.getPV() - da�o );
			}
		}
		return descripcionHeroe();
	}

	public String mecanica(Enemigo enemigo, Heroe heroe, int defensaExtra){
		int da�o = (enemigo.getDestreza()*3 - heroe.getDefensa()) / defensaExtra;
		if ( da�o > 0 ){
			heroe.setPV(heroe.getPV() - da�o );
		}else{
			return "La armadura de " + heroe.getNombre() + " absorbe el da�o";
		}
		return descripcionEnemigo(da�o);
	}

	@Override
	public Icon icon() {
		return new ImageIcon(Pocion_Explosiva.class.getResource("/imagenes/pociones/Pocion Explosiva.png"));
	}
}
