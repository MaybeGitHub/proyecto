package armas;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Espada_Larga extends Arma {

	public static final String TIPOBBDD = "Espada de acero larga";

	public Espada_Larga() {
	}

	@Override
	public String nombre() {
		return "Espada de acero larga";
	}

	@Override
	public int dano() {
		return 2;
	}

	@Override
	public Icon icon() {
		return new ImageIcon(Espada_Larga.class.getResource("/imagenes/equipo/espada larga.png"));
	}

	@Override
	public String getTipoArmaBBDD() {
		return TIPOBBDD;
	}
}
