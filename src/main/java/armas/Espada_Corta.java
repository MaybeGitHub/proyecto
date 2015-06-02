package armas;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Espada_Corta extends Arma{

	public static final String TIPOBBDD = "Espada corta";

	public Espada_Corta() {
	}

	@Override
	public String nombre() {
		return "Espada corta";
	}

	@Override
	public int daño() {
		return 1;
	}

	@Override
	public Icon icon() {
		return new ImageIcon(Espada_Corta.class.getResource("/imagenes/equipo/espada corta.png"));
	}

	@Override
	public String getTipoArmaBBDD() {
		return TIPOBBDD;
	}

}
