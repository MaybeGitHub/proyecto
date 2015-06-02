package armaduras;

import datos.Item;

public abstract class Armadura extends Item {
	
	public Armadura() {
	}
	
	public abstract int bonusArmor();
	public abstract String getTipoArmaduraBBDD();
	
	public static Armadura creaAPartirDeTipoBBDD(String tipoArmaduraBBDD) {
		switch (tipoArmaduraBBDD) {
		case Armadura_Cuero.TIPOBBDD: return new Armadura_Cuero();
		case Armadura_Malla.TIPOBBDD: return new Armadura_Malla();
		case Armadura_Placas.TIPOBBDD: return new Armadura_Placas();
		case Sin_Armadura.TIPOBBDD: return new Sin_Armadura();
		default:
			throw new IllegalArgumentException();
		}
	}
	
	
}
