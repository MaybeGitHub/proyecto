package personajes;

import habilidades.Curar;
import habilidades.Habilidad;
import habilidades.Llamarada;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import baseDatosOracle.Heroe;
import armaduras.Armadura_Cuero;
import armas.Sin_Arma;
import datos.Enemigo;
import datos.Item;
import escudos.Escudo_Madera;
import pociones.Pocion;

public class Chaman_Goblin extends Enemigo {

	public Chaman_Goblin() {
		setNombre("Chaman Goblin");
		setNivel(2);
		setFuerza(2);
		setDestreza(2);
		setInteligencia(2);
		setPVMax( 20 );
		setPV(getPVMax());
		setArma(new Sin_Arma());
		setEscudo(new Escudo_Madera());
		setArmadura(new Armadura_Cuero());
		setMochila(new ArrayList<Pocion>());
		setHabilidades(new ArrayList<Habilidad>());
		setBotin(new ArrayList<Item>());
		crearHabilidades();
		crearPociones();
		setSkillCount(0);
		setSkillCountMax(7);
	}
	
	@Override
	public ArrayList<String> skill(Enemigo enemigo, ArrayList<Enemigo> enemies, Heroe heroe, int bonusDef) {
		ArrayList<String> eventos = new ArrayList<String>();
		for ( Enemigo objetivo : enemies ){	
			( ( Curar )getHabilidades().get(0) ).mecanica( this, objetivo );
		}
		eventos.add(this + " usa una magia curativa parar sanar a todos sus aliados.");
		eventos.add(((Llamarada)getHabilidades().get(1)).mecanica(this, heroe, bonusDef));
		return eventos;
	}

	@Override
	protected void crearHabilidades() {
		getHabilidades().add(new Curar());
		getHabilidades().add(new Llamarada());
	}

	@Override
	public Icon getIcon() {
		return new ImageIcon(Chaman_Goblin.class.getResource("/imagenes/enemigos/chaman goblin.png"));
	}
	
	@Override
	protected void crearPociones() {
	}
}
