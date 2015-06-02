package armaduras;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Armadura_Cuero extends Armadura{
	
	public static final String TIPOBBDD = "Armadura de Cuero";

	public Armadura_Cuero() {
	}

	@Override
	public String nombre() {
		return "Armadura de Cuero";
	}

	@Override
	public int bonusArmor() {
		return 2;
	}

	@Override
	public Icon icon() {
		return new ImageIcon(Armadura_Cuero.class.getResource("/imagenes/equipo/armadura cuero.png"));
	}

	@Override
	public String getTipoArmaduraBBDD() {
		return TIPOBBDD;
	}
}
