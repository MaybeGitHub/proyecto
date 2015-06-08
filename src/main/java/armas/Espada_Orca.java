package armas;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Espada_Orca extends Arma{

	public static final String TIPOBBDD = "Espada Orca";

	public Espada_Orca() {
	}

	@Override
	public String nombre() {
		return "Espada Orca";
	}

	@Override
	public int dano() {
		return 3;
	}

	@Override
	public Icon icon() {
		return new ImageIcon ( Espada_Orca.class.getResource("/imagenes/equipo/espada orca.png"));
	}

	@Override
	public String getTipoArmaBBDD() {
		return TIPOBBDD;
	}
}
