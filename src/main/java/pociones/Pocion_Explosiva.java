package pociones;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import baseDatosOracle.Heroe;
import datos.Enemigo;

public class Pocion_Explosiva extends Pocion {

	public String descripcionHeroe() {
		return "Lanzas una pocion explosiva que hace dano a todos los enemigos";
	}

	public String descripcionEnemigo(int dano) {
		return "Te lanzan una pocion explosiva que te hace " + dano + " puntos de dano";
	}	

	@Override
	public String nombre() {
		return "Pocion Explosiva";
	}

	public String mecanica(Heroe heroe, ArrayList<Enemigo> enemigos){
		int dano = 0;
		for ( Enemigo maloso : enemigos ){
			dano = heroe.getDestreza()*5 - maloso.getArmadura().bonusArmor() - maloso.getEscudo().bonusDef();
			if ( dano > 0 ){
				maloso.setPV(maloso.getPV() - dano );
			}
		}
		return descripcionHeroe();
	}

	public String mecanica(Enemigo enemigo, Heroe heroe, int defensaExtra){
		int dano = (enemigo.getDestreza()*3 - heroe.getDefensa()) / defensaExtra;
		if ( dano > 0 ){
			heroe.setPV(heroe.getPV() - dano );
		}else{
			return "La armadura de " + heroe.getNombre() + " absorbe el dano";
		}
		return descripcionEnemigo(dano);
	}

	@Override
	public Icon icon() {
		return new ImageIcon(Pocion_Explosiva.class.getResource("/imagenes/pociones/Pocion Explosiva.png"));
	}
}
