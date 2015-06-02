package pociones;

import habilidades.Habilidad;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Pocion_Mana extends Pocion {
	
	public String descripcion() {
		return  "Usas " + nombre() + " para reiniciar el enfriamiento de todas tus habilidades";
	}

	@Override
	public String nombre() {
		return "Pocion de Mana";
	}

	public String mecanica(ArrayList<Habilidad> habilidades) {		
		for ( Habilidad habilidad : habilidades ){
			habilidad.setCooldown(0);
		}
		return descripcion();
	}

	@Override
	public javax.swing.Icon icon() {
		return new ImageIcon(Pocion_Mana.class.getResource("/imagenes/pociones/Pocion Mana.png"));
	}
}
