package habilidades;

import datos.Item;

public abstract class Habilidad extends Item{

	private int cooldown = 0;

	public int getCooldownMax(){
		return 5;
	}
	
	public int getCooldown() {
		return cooldown;
	}

	public void setCooldown(int cooldown) {
		if ( cooldown <= 0 ){
			cooldown = 0;
		}
		this.cooldown = cooldown;
	}
}
