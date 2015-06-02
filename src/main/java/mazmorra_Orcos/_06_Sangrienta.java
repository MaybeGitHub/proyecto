package mazmorra_Orcos;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import datos.Enemigo;
import datos.Sala;
import personajes.Goblin;
import personajes.Orco;

public class _06_Sangrienta extends Sala {

	public _06_Sangrienta() {
		enemigos.add(new Orco());
		enemigos.add(new Goblin());
		enemigos.add(new Goblin());
	}
	@Override
	public String nombre() {
		return "Habitacion ";
	}

	@Override
	public ArrayList<Enemigo> getEnemigos() {
		return enemigos;
	}
	
	@Override
	public Icon getIcon() {
		return new ImageIcon(_06_Sangrienta.class.getResource("/imagenes/salas/6.jpg"));
	}
}
