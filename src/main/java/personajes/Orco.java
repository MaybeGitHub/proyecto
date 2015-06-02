package personajes;

import habilidades.Golpe_Concentrado;
import habilidades.Habilidad;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import baseDatosOracle.Heroe;
import pociones.Pocion;
import armaduras.Armadura_Cuero;
import armas.Espada_Larga;
import datos.Enemigo;
import datos.Item;
import escudos.Escudo_Madera;

public class Orco extends Enemigo {

	public Orco() {
		setNombre("Orco");
		setNivel(5);
		setFuerza(4);
		setDestreza(3);
		setInteligencia(2);
		setPVMax( 40 );
		setPV(getPVMax());
		setArma(new Espada_Larga());
		setEscudo(new Escudo_Madera());
		setArmadura(new Armadura_Cuero());
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
		eventos.add(((Golpe_Concentrado)getHabilidades().get(0)).mecanica(this, heroe, bonusDef));
		return eventos;
	}

	@Override
	protected void crearHabilidades() {
		getHabilidades().add(new Golpe_Concentrado());
	}

	@Override
	public Icon getIcon() {
		return new ImageIcon(Orco.class.getResource("/imagenes/enemigos/orco.png"));
	}
	@Override
	protected void crearPociones() {
	}

}
