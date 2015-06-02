package escudos;

import javax.swing.Icon;

public class Sin_Escudo extends Escudo {

	public static final String TIPOBBDD = "Sin Escudo";

	public Sin_Escudo() {
	}

	@Override
	public int bonusDef() {
		return 0;
	}

	@Override
	public String nombre() {
		return "Sin Escudo";
	}

	@Override
	public Icon icon() {
		return null;
	}

	@Override
	public String getTipoEscudoBBDD() {
		return TIPOBBDD;
	}
}
