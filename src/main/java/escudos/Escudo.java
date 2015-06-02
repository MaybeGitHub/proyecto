package escudos;

import datos.Item;

public abstract class Escudo extends Item {

	public Escudo() {
	}

	public abstract int bonusDef();
	public abstract String getTipoEscudoBBDD();

	public static Escudo creaAPartirDeTipoBBDD(String tipoEscudoBBDD) {
		switch (tipoEscudoBBDD) {
		case Escudo_Madera.TIPOBBDD: return new Escudo_Madera();
		case Escudo_Metal.TIPOBBDD: return new Escudo_Metal();
		case Sin_Escudo.TIPOBBDD: return new Sin_Escudo();
		default:
			throw new IllegalArgumentException();
		}
	}
}
