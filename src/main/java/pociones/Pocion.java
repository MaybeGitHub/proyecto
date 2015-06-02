package pociones;

import datos.Item;

public abstract class Pocion extends Item {

	private int cooldown = 0;
	
	public int getCooldownMax(){
		return 10;
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
