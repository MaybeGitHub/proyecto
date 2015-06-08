package armas;

import datos.Item;

public abstract class Arma extends Item {
	
	public Arma() {
	}
	
	public abstract int dano();
	
	public abstract String getTipoArmaBBDD();

	public static Arma creaAPartirDeTipoBBDD(String tipoArmaBBDD) {
		switch (tipoArmaBBDD) {
		case Espada_Corta.TIPOBBDD: return new Espada_Corta();
		case Espada_Larga.TIPOBBDD: return new Espada_Larga();
		case Espada_Orca.TIPOBBDD: return new Espada_Orca();
		case Sin_Arma.TIPOBBDD: return new Sin_Arma();
		default:
			throw new IllegalArgumentException();
		}
	}
}
