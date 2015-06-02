package mazmorra_Orcos;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import datos.Enemigo;
import datos.Sala;
import personajes.Chaman_Goblin;
import personajes.Chaman_Orco;
import personajes.Goblin;
import personajes.Hombre_Rata;
import personajes.Orco;

public class _08_Sacrificios extends Sala {

	public _08_Sacrificios() {
		enemigos.add(new Chaman_Orco());
		enemigos.add(new Orco());
		enemigos.add(new Orco());
		enemigos.add(new Goblin());
		enemigos.add(new Hombre_Rata());
		enemigos.add(new Chaman_Goblin());
	}
	@Override
	public String nombre() {
		return "Sala de Sactificios";
	}

	@Override
	public ArrayList<Enemigo> getEnemigos() {
		return enemigos;
	}
	
	@Override
	public Icon getIcon() {
		return new ImageIcon(_08_Sacrificios.class.getResource("/imagenes/salas/8.jpg"));
	}

}
