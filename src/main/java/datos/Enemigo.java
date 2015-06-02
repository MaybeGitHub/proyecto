package datos;

import java.util.ArrayList;

import javax.swing.Icon;

import baseDatosOracle.Heroe;

public abstract class Enemigo extends Personaje {

	protected int skillCount, skillCountMax;
	protected ArrayList<Item> botin;

	public abstract ArrayList<String> skill(Enemigo enemigo, ArrayList<Enemigo> enemigos, Heroe heroe, int bonusDef);
	protected abstract void crearHabilidades();
	protected abstract void crearPociones();
	public abstract Icon getIcon();

	public int getSkillCountMax() {
		return skillCountMax;
	}

	public void setSkillCountMax(int newSkillCountMax) {
		skillCountMax = newSkillCountMax;
	}

	public int getSkillCount() {
		return skillCount;
	}	

	public void setSkillCount(int count) {
		if ( count >= getSkillCountMax() ){
			skillCount = 0;
		}else{
			skillCount = count;
		}
	}

	public ArrayList<Item> getBotin() {
		return botin;
	}

	public void setBotin(ArrayList<Item> botin) {
		this.botin = botin;
	}
	
	public String toTipString() {
		String ret =" Peligro: ";
		if ( getNivel() < 3 ){
			ret += "Bajo";
		}else if ( getNivel() < 7 ){
			ret += "Medio";
		}else {
			ret += "Alto";
		}
		return ret;
	}
}
