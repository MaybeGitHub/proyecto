package armas;

import javax.swing.Icon;

public class Sin_Arma extends Arma{

	public static final String TIPOBBDD = "Sin Arma";
	
	public Sin_Arma() {
	}

	@Override
	public String nombre() {
		return "Sin Arma";
	}

	@Override
	public int daño() {
		return 0;
	}

	@Override
	public Icon icon() {
		return null;
	}

	@Override
	public String getTipoArmaBBDD() {
		return TIPOBBDD;
	}
}
