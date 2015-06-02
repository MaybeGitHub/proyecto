package habilidades;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import baseDatosOracle.Heroe;
import datos.Enemigo;

public class Curar extends Habilidad{

	public String descripcionHeroe( Heroe heroe, int cantidad ) {
		return heroe.getNombre() + " usa una magia curativa y se sana " + cantidad;
	}

	public String descripcionMalosos( Enemigo caster) {
		return caster.getNombre() + " usa una magia curativa y sana a todos.";
	}

	@Override
	public String nombre() {
		return "Curar";
	}

	public String mecanica( Enemigo caster, Enemigo enemigo ) {
		int cantidad = (enemigo.getPVMax() * caster.getInteligencia()) / (20 - caster.getDestreza() - caster.getInteligencia());
		enemigo.setPV( enemigo.getPV() + cantidad );		
		return descripcionMalosos(caster);
	}

	public String mecanica(Heroe heroe) {
		int cantidad = (heroe.getPVMax() * heroe.getInteligencia()) / (20 - heroe.getDestreza() - heroe.getInteligencia());
		heroe.setPV(heroe.getPV() + cantidad ) ;
		return descripcionHeroe(heroe, cantidad);
	}

	@Override
	public Icon icon() {
		return new ImageIcon(Curar.class.getResource("/imagenes/habilidades/Curar.png"));
	}	
}
