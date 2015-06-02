package mazmorra_Orcos;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import datos.Enemigo;
import datos.Sala;
import personajes.Goblin;

public class _01_Entrada_Principal extends Sala {
	
	public _01_Entrada_Principal() {
		enemigos.add(new Goblin());
	}
	@Override
	public String nombre() {
		return "Entrada Principal";
	}

	@Override
	public ArrayList<Enemigo> getEnemigos() {
		return enemigos;
	}
	
	@Override
	public Icon getIcon() {
		return new ImageIcon(_01_Entrada_Principal.class.getResource("/imagenes/salas/1.jpg"));
	}
}
