package personajes;

import habilidades.Golpe_Concentrado;
import habilidades.Habilidad;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import baseDatosOracle.Heroe;
import pociones.Pocion;
import armaduras.Armadura_Placas;
import armas.Espada_Orca;
import datos.Enemigo;
import datos.Item;
import escudos.Escudo_Metal;

public class Orco_Negro extends Enemigo {

	public Orco_Negro() {
		setNombre("Orco Negro");
		setNivel(7);
		setFuerza(5);
		setDestreza(3);
		setInteligencia(2);
		setPVMax( 40 );
		setPV(getPVMax());
		setArma(new Espada_Orca());
		setEscudo(new Escudo_Metal());
		setArmadura(new Armadura_Placas());
		setMochila(new ArrayList<Pocion>());
		setHabilidades(new ArrayList<Habilidad>());
		setBotin(new ArrayList<Item>());
		crearHabilidades();
		crearPociones();
		crearBotin();
		setSkillCount(0);
		setSkillCountMax(5);
	}
	
	protected void crearBotin() {
		getBotin().add(new Escudo_Metal());
		getBotin().add(new Espada_Orca());
		getBotin().add(new Armadura_Placas());
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
		return new ImageIcon(Orco_Negro.class.getResource("/imagenes/enemigos/cacique.png"));
	}
	@Override
	protected void crearPociones() {
	}
}
