package mazmorra_Orcos;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import datos.Enemigo;
import datos.Sala;
import personajes.Chaman_Orco;
import personajes.Goblin;

public class _07_Pictogramas extends Sala {

	public _07_Pictogramas() {
		enemigos.add(new Chaman_Orco());
		enemigos.add(new Goblin());
	}
	@Override
	public String nombre() {
		return "Habitacion con Pictogramas";
	}

	@Override
	public ArrayList<Enemigo> getEnemigos() {

		return enemigos;
	}
	@Override
	public Icon getIcon() {
		return new ImageIcon(_07_Pictogramas.class.getResource("/imagenes/salas/7.jpg"));
	}
}
