package armaduras;

import javax.swing.Icon;

public class Sin_Armadura extends Armadura {

	public static final String TIPOBBDD = "Sin Armadura";

	public Sin_Armadura() {
	}

	@Override
	public String nombre() {
		return "Sin Armadura";
	}

	@Override
	public int bonusArmor() {
		return 0;
	}

	@Override
	public Icon icon() {
		return null;
	}

	@Override
	public String getTipoArmaduraBBDD() {
		return TIPOBBDD;
	}
}
