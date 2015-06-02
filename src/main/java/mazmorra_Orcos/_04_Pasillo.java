package mazmorra_Orcos;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import datos.Enemigo;
import datos.Sala;
import personajes.Chaman_Goblin;
import personajes.Goblin;

public class _04_Pasillo extends Sala {

	public _04_Pasillo() {
		enemigos.add(new Chaman_Goblin());
		enemigos.add(new Goblin());
	}
	@Override
	public String nombre() {
		return "Pasillo Mugriento";
	}

	@Override
	public ArrayList<Enemigo> getEnemigos() {
		return enemigos;
	}
	
	@Override
	public Icon getIcon() {
		return new ImageIcon(_04_Pasillo.class.getResource("/imagenes/salas/4.jpg"));
	}
}
