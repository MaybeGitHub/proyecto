package habilidades;

import java.util.ArrayList;

import javax.swing.Icon;

import datos.Enemigo;
import personajes.Chaman_Goblin;
import personajes.Goblin_Esbirro;

public class Llamar_Goblins extends Habilidad {

	public String descripcion() {
		return "El jefe realiza un grito de mando y aparecen un grupo de goblins por la puerta";
	}

	@Override
	public String nombre() {
		return "Llamar Goblins";
	}

	public String mecanica(ArrayList<Enemigo> enemigos) {
		int cantidad = 5;
		while( cantidad > 0 ){
			if ( enemigos.size() < 6 ){
				if ( cantidad%3 == 0 ){
					enemigos.add(new Chaman_Goblin());
				}else{
					enemigos.add(new Goblin_Esbirro());
				}
			}else{
				break;
			}
			cantidad--;
		}
		return descripcion();
	}

	@Override
	public Icon icon() {
		return null;
	}
}
