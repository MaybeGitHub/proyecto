package mazmorra_Orcos;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import datos.Enemigo;
import datos.Sala;
import personajes.Orco;
import personajes.Orco_Negro;

public class _09_Fuente extends Sala {

	public _09_Fuente() {
		enemigos.add(new Orco_Negro());
		enemigos.add(new Orco_Negro());
		enemigos.add(new Orco_Negro());
		enemigos.add(new Orco());
		enemigos.add(new Orco());
		enemigos.add(new Orco());
	}
	
	@Override
	public String nombre() {
		return "Salon con Fuentes de Sangre";
	}

	@Override
	public ArrayList<Enemigo> getEnemigos() {
		return enemigos;
	}

	@Override
	public Icon getIcon() {
		return new ImageIcon(_09_Fuente.class.getResource("/imagenes/salas/9.jpg"));
	}
}
