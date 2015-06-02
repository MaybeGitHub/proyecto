package mazmorra_Orcos;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import datos.Enemigo;
import datos.Sala;
import personajes.Goblin;
import personajes.Hombre_Rata;

public class _03_Vestibulo_Derecho extends Sala {
	
	public _03_Vestibulo_Derecho() {
		enemigos.add(new Goblin());
		enemigos.add(new Hombre_Rata());
		enemigos.add(new Hombre_Rata());
	}
	@Override
	public String nombre() {
		return "Vestibulo Derecho";
	}

	@Override
	public ArrayList<Enemigo> getEnemigos() {
		return enemigos;
	}
	
	@Override
	public Icon getIcon() {
		return new ImageIcon(_03_Vestibulo_Derecho.class.getResource("/imagenes/salas/3.jpg"));
	}
}
