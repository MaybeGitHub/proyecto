package mazmorra_Orcos;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import datos.Enemigo;
import datos.Sala;
import personajes.Chaman_Goblin;
import personajes.Jefe_Goblin;

public class _05_Vestibulo_Izquierdo extends Sala {

	public _05_Vestibulo_Izquierdo() {
		enemigos.add(new Jefe_Goblin());
		enemigos.add(new Chaman_Goblin());
	}
	@Override
	public String nombre() {
		return "Vestibulo Izquierdo";
	}

	@Override
	public ArrayList<Enemigo> getEnemigos() {
		return enemigos;
	}
	
	@Override
	public Icon getIcon() {
		return new ImageIcon(_05_Vestibulo_Izquierdo.class.getResource("/imagenes/salas/5.jpg"));
	}
}
