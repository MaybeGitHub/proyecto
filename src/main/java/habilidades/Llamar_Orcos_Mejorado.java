package habilidades;

import java.util.ArrayList;

import javax.swing.Icon;

import datos.Enemigo;
import personajes.Chaman_Orco;
import personajes.Orco_Negro;

public class Llamar_Orcos_Mejorado extends Habilidad {

	public String descripcion() {
		return "El jefe realiza un grito de mando y aparecen un grupo de orcos armados por la puerta";
	}

	@Override
	public String nombre() {
		return "Llamar Orcos Elites";
	}

	public String mecanica(ArrayList<Enemigo> enemigos) {
		int cantidad = 5;
		while( cantidad > 0 ){
			if ( enemigos.size() < 6 ){
				if ( cantidad%3 == 0 ){
					enemigos.add(new Chaman_Orco());
				}else{
					enemigos.add(new Orco_Negro());
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
