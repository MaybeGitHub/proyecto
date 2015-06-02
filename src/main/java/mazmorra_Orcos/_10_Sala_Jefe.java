package mazmorra_Orcos;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import datos.Enemigo;
import datos.Sala;
import personajes.Cacique_1;

public class _10_Sala_Jefe extends Sala {

	public _10_Sala_Jefe() {
		enemigos.add(new Cacique_1());
	}
	@Override
	public String nombre() {
		return "Sala del Cacique";
	}

	@Override
	public ArrayList<Enemigo> getEnemigos() {
		return enemigos;
	}
	
	@Override
	public Icon getIcon() {
		return new ImageIcon(_10_Sala_Jefe.class.getResource("/imagenes/salas/10.jpg"));
	}
	
}
