package personajes;

import habilidades.Curar;
import habilidades.Habilidad;
import habilidades.Llamarada;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import baseDatosOracle.Heroe;
import pociones.Pocion;
import armaduras.Armadura_Malla;
import armas.Sin_Arma;
import datos.Enemigo;
import datos.Item;
import escudos.Escudo_Madera;

public class Chaman_Orco extends Enemigo {

	public Chaman_Orco() {
		setNombre("Chaman Orco");
		setNivel(6);
		setFuerza(2);
		setDestreza(3);
		setInteligencia(4);
		setPVMax( 40 );
		setPV(getPVMax());
		setArma(new Sin_Arma());
		setEscudo(new Escudo_Madera());
		setArmadura(new Armadura_Malla());
		setMochila(new ArrayList<Pocion>());
		setHabilidades(new ArrayList<Habilidad>());
		setBotin(new ArrayList<Item>());
		crearHabilidades();
		crearPociones();
		setSkillCount(0);
		setSkillCountMax(7);
	}
	
	@Override
	public ArrayList<String> skill(Enemigo enemigo, ArrayList<Enemigo> enemiegos, Heroe heroe, int bonusDef) {
		ArrayList<String> eventos = new ArrayList<String>();
		for ( Enemigo objetivo : enemiegos ){	
			( ( Curar )getHabilidades().get(0) ).mecanica( this, objetivo );
		}
		eventos.add(this + " usa una magia curativa para sanar a todos sus aliados.");
		eventos.add(((Llamarada)getHabilidades().get(1)).mecanica(this, heroe, bonusDef));
		return eventos;
	}

	@Override
	protected void crearHabilidades() {
		getHabilidades().add(new Curar());
		getHabilidades().add(new Llamarada());
	}

	@Override
	protected void crearPociones() {	
	}

	@Override
	public Icon getIcon() {
		return new ImageIcon(Chaman_Orco.class.getResource("/imagenes/enemigos/chaman orco.png"));
	}

}
