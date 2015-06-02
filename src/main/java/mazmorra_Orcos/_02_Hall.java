package mazmorra_Orcos;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import datos.Enemigo;
import datos.Sala;
import personajes.Goblin;

public class _02_Hall extends Sala {
	
	public _02_Hall() {
		enemigos.add(new Goblin());
		enemigos.add(new Goblin());
	}
	@Override
	public String nombre() {
		return "Hall";
	}

	@Override
	public ArrayList<Enemigo> getEnemigos() {
		return enemigos;
	}
	
	@Override
	public Icon getIcon() {
		return new ImageIcon(_02_Hall.class.getResource("/imagenes/salas/2.jpg"));
	}
}
