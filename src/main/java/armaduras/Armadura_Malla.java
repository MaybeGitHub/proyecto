package armaduras;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Armadura_Malla extends Armadura {

	public static final String TIPOBBDD = "Armadura de Malla";

	public Armadura_Malla() {
	}

	@Override
	public String nombre() {
		return "Armadura de Malla";
	}

	@Override
	public int bonusArmor() {
		return 3;
	}

	@Override
	public Icon icon() {
		return new ImageIcon(Armadura_Malla.class.getResource("/imagenes/equipo/armadura malla.png"));
	}

	@Override
	public String getTipoArmaduraBBDD() {
		return TIPOBBDD;
	}
}
