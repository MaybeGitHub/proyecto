package pociones;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import baseDatosOracle.Heroe;
import datos.Enemigo;

public class Pocion_Explosiva extends Pocion {

	public String descripcionHeroe() {
		return "Lanzas una pocion explosiva que hace daño a todos los enemigos";
	}

	public String descripcionEnemigo(int daño) {
		return "Te lanzan una pocion explosiva que te hace " + daño + " puntos de daño";
	}	

	@Override
	public String nombre() {
		return "Pocion Explosiva";
	}

	public String mecanica(Heroe heroe, ArrayList<Enemigo> enemigos){
		int daño = 0;
		for ( Enemigo maloso : enemigos ){
			daño = heroe.getDestreza()*5 - maloso.getArmadura().bonusArmor() - maloso.getEscudo().bonusDef();
			if ( daño > 0 ){
				maloso.setPV(maloso.getPV() - daño );
			}
		}
		return descripcionHeroe();
	}

	public String mecanica(Enemigo enemigo, Heroe heroe, int defensaExtra){
		int daño = (enemigo.getDestreza()*3 - heroe.getDefensa()) / defensaExtra;
		if ( daño > 0 ){
			heroe.setPV(heroe.getPV() - daño );
		}else{
			return "La armadura de " + heroe.getNombre() + " absorbe el daño";
		}
		return descripcionEnemigo(daño);
	}

	@Override
	public Icon icon() {
		return new ImageIcon(Pocion_Explosiva.class.getResource("/imagenes/pociones/Pocion Explosiva.png"));
	}
}
