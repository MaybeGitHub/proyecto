package escudos;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Escudo_Metal extends Escudo {

	public static final String TIPOBBDD = "Escudo de hierro";

	public Escudo_Metal() {
	}

	@Override
	public String nombre() {
		return "Escudo de hierro";
	}

	@Override
	public int bonusDef() {
		return 4;
	}

	@Override
	public Icon icon() {
		return new ImageIcon(Escudo_Metal.class.getResource("/imagenes/equipo/escudo metal.png"));
	}

	@Override
	public String getTipoEscudoBBDD() {
		return TIPOBBDD;
	}
}
