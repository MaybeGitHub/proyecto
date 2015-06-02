package datos;

import java.util.ArrayList;

import javax.swing.Icon;

public abstract class Sala {	

	public abstract String nombre();
	public abstract Icon getIcon();
	
	protected ArrayList<Enemigo> enemigos = new ArrayList<Enemigo>();
	
	@Override
	public String toString() {
		return this.nombre();
	}
	
	public ArrayList<Enemigo> getEnemigos() {
		return enemigos;
	}	
}