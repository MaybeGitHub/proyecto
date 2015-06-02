package armaduras;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Armadura_Placas extends Armadura {

	public static final String TIPOBBDD = "Armadura de Placas";

	public Armadura_Placas() {
	}

	@Override
	public String nombre() {
		return "Armadura de Placas";
	}
	
	@Override
	public int bonusArmor() {
		return 4;
	}

	@Override
	public Icon icon() {
		return new ImageIcon( Armadura_Placas.class.getResource("/imagenes/equipo/armadura placas.png"));
	}

	@Override
	public String getTipoArmaduraBBDD() {
		return TIPOBBDD;
	}	
}
