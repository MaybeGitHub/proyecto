package personajes;

import habilidades.Golpe_Concentrado;
import habilidades.Habilidad;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import baseDatosOracle.Heroe;
import pociones.Pocion;
import datos.Enemigo;
import datos.Item;
import escudos.Sin_Escudo;
import armaduras.Armadura_Cuero;
import armas.Espada_Corta;

public class Goblin extends Enemigo{

	public Goblin(){
		setNombre("Goblin");
		setNivel(1);
		setFuerza(2);
		setDestreza(2);
		setInteligencia(1);
		setPVMax( 20 );
		setPV(getPVMax());
		setArma(new Espada_Corta());
		setEscudo(new Sin_Escudo());
		setArmadura(new Armadura_Cuero());
		setMochila(new ArrayList<Pocion>());
		setHabilidades(new ArrayList<Habilidad>());
		setBotin(new ArrayList<Item>());
		crearHabilidades();
		crearPociones();
		setSkillCount(0);
		setSkillCountMax(5);
	}

	public Icon getIcon() {
		return new ImageIcon(Goblin.class.getResource("/imagenes/enemigos/goblin.png"));
	}

	public ArrayList<String> skill(Enemigo enemigo, ArrayList<Enemigo> enemies, Heroe heroe, int bonusDef) {
		ArrayList<String> eventos = new ArrayList<String>();
		eventos.add(((Golpe_Concentrado)getHabilidades().get(0)).mecanica(this, heroe, bonusDef));
		return eventos;
	}

	@Override
	public void crearHabilidades() {
		getHabilidades().add(new Golpe_Concentrado());
	}

	@Override
	public void crearPociones() {
	}
}
