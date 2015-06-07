package personajes;

import habilidades.Golpe_Concentrado;
import habilidades.Habilidad;
import habilidades.Llamar_Goblins;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import baseDatosOracle.Heroe;
import datos.Enemigo;
import datos.Item;
import pociones.Pocion;
import armaduras.Armadura_Malla;
import armas.Espada_Larga;
import escudos.Escudo_Madera;

public class Jefe_Goblin extends Enemigo {

	public Jefe_Goblin() {
		setNombre("Inspector Goblin");
		setNivel(10);
		setFuerza(3);
		setDestreza(2);
		setInteligencia(2);
		setPVMax( 30 );
		setPV(getPVMax());
		setArma(new Espada_Larga());
		setEscudo(new Escudo_Madera());
		setArmadura(new Armadura_Malla());
		setMochila(new ArrayList<Pocion>());
		setHabilidades(new ArrayList<Habilidad>());
		setBotin(new ArrayList<Item>());
		crearHabilidades();
		crearPociones();
		crearBotin();
		setSkillCount(0);
		setSkillCountMax(8);
	}
	@Override
	public ArrayList<String> skill(Enemigo enemigo, ArrayList<Enemigo> enemies, Heroe heroe, int bonusDef) {
		ArrayList<String> eventos = new ArrayList<String>();		
		if ( enemies.size() < 6 ){
			eventos.add(((Llamar_Goblins) getHabilidades().get(0)).mecanica(enemies));
		}else{
			eventos.add(((Golpe_Concentrado)getHabilidades().get(1)).mecanica(this, heroe, bonusDef));
		}
		return eventos;
	}

	protected void crearBotin() {
		getBotin().add(new Escudo_Madera());
		getBotin().add(new Espada_Larga());
		getBotin().add(new Armadura_Malla());
	}

	@Override
	protected void crearHabilidades() {
		getHabilidades().add(new Llamar_Goblins());
		getHabilidades().add(new Golpe_Concentrado());
	}

	@Override
	public Icon getIcon() {
		return new ImageIcon(Jefe_Goblin.class.getResource("/imagenes/enemigos/capataz goblin.png"));
	}

	@Override
	protected void crearPociones() {		
	}
}
