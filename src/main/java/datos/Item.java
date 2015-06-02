package datos;

import javax.swing.Icon;

public abstract class Item {

	public abstract String nombre();
	public abstract Icon icon();
	
	public Item() {		
	}	
	
	@Override
	public String toString() {
		return nombre();
	}
}
