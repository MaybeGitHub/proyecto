package pociones;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import baseDatosOracle.Heroe;
import datos.Enemigo;

public class Pocion_Curativa extends Pocion {

	public String descripcion(Heroe heroe) {
		return "Usas una pocion curativa y te curas " + heroe.getPVMax() / 4 + " PV";
	}

	public String descripcion(Enemigo enemigo) {
		return enemigo.getNombre() + "Usa una pocion curativa y se cura " + enemigo.getPVMax() / 4 + " PV";
	}

	@Override
	public String nombre() {
		return "Pocion curativa";
	}

	@Override
	public Icon icon() {
		return new ImageIcon(Pocion_Curativa.class.getResource("/imagenes/pociones/Pocion Curativa.png"));
	}

	public String mecanica(Heroe heroe) {		
		heroe.setPV(heroe.getPV() + heroe.getPVMax() / 4);
		return descripcion(heroe);
	}

	public String mecanica(Enemigo enemigo){		
		enemigo.setPV(enemigo.getPV() + enemigo.getPVMax() / 4);
		return descripcion(enemigo);
	}	
}
