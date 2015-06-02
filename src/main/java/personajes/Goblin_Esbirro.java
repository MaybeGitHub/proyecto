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

public class Goblin_Esbirro extends Enemigo{

	public Goblin_Esbirro(){
		setNombre("Goblin Esbirro");
		setNivel(0);
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
		return new ImageIcon(Goblin_Esbirro.class.getResource("/imagenes/enemigos/goblin esbirro.png"));
	}

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
	protected void crearPociones() {
			
	}
}
