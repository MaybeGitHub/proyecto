package personajes;

import habilidades.Habilidad;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import baseDatosOracle.Heroe;
import pociones.Pocion;
import pociones.Pocion_Explosiva;
import armaduras.Sin_Armadura;
import armas.Espada_Corta;
import datos.Enemigo;
import datos.Item;
import escudos.Sin_Escudo;

public class Hombre_Rata extends Enemigo {

	public Hombre_Rata() {
		setNombre("Hombre Rata");
		setNivel(1);
		setFuerza(2);
		setDestreza(2);
		setInteligencia(1);
		setPVMax( 20 );
		setPV(getPVMax());
		setArma(new Espada_Corta());
		setEscudo(new Sin_Escudo());
		setArmadura(new Sin_Armadura());
		setMochila(new ArrayList<Pocion>());
		setHabilidades(new ArrayList<Habilidad>());
		setBotin(new ArrayList<Item>());
		crearHabilidades();
		crearPociones();
		setSkillCount(0);
		setSkillCountMax(5);
	}
	
	@Override
	public ArrayList<String> skill(Enemigo enemigo, ArrayList<Enemigo> enemies, Heroe heroe, int bonusDef) {
		ArrayList<String> eventos = new ArrayList<String>();
		eventos.add(((Pocion_Explosiva)getPociones().get(0)).mecanica(this, heroe, bonusDef));
		return eventos;		
	}

	@Override
	protected void crearHabilidades() {		
	}

	@Override
	protected void crearPociones() {
		getPociones().add(new Pocion_Explosiva());
	}

	@Override
	public Icon getIcon() {
		return new ImageIcon(Hombre_Rata.class.getResource("/imagenes/enemigos/hombre rata.png"));
	}

}
