package escudos;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Escudo_Madera extends Escudo{

	public static final String TIPOBBDD = "Escudo de madera";

	public Escudo_Madera() {
	}

	@Override
	public String nombre() {
		return "Escudo de madera";
	}

	@Override
	public int bonusDef() {
		return 2;
	}

	@Override
	public Icon icon() {
		return new ImageIcon(Escudo_Madera.class.getResource("/imagenes/equipo/escudo madera.png"));
	}

	@Override
	public String getTipoEscudoBBDD() {
		return TIPOBBDD;
	}
}
