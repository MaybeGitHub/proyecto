package habilidades;

import java.util.ArrayList;

import javax.swing.Icon;

import datos.Enemigo;
import personajes.Chaman_Orco;
import personajes.Orco;

public class Llamar_Orcos extends Habilidad {

	public String descripcion() {
		return "El jefe realiza un grito de mando y aparecen un grupo de orcos por la puerta";
	}

	@Override
	public String nombre() {
		return "Llamar Orcos";
	}

	public String mecanica(ArrayList<Enemigo> enemigos) {
		int cantidad = 5;
		while( cantidad > 0 ){
			if ( enemigos.size() < 6 ){
				if ( cantidad%3 == 0 ){
					enemigos.add(new Chaman_Orco());
				}else{
					enemigos.add(new Orco());
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
