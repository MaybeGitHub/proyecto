package personajes;

import habilidades.Golpe_Concentrado;
import habilidades.Habilidad;
import habilidades.Llamar_Orcos;

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

public class Cacique_1 extends Enemigo {

	public Cacique_1() {
		setNombre("Jefe Orco");
		setNivel(10);
		setFuerza(10);
		setDestreza(10);
		setInteligencia(10);
		setPVMax( 150 );
		setPV(getPVMax());
		setArma(new Espada_Orca());
		setEscudo(new Escudo_Metal());
		setArmadura(new Armadura_Placas());
		setMochila(new ArrayList<Pocion>());
		setHabilidades(new ArrayList<Habilidad>());
		setBotin(new ArrayList<Item>());
		crearHabilidades();
		crearPociones();
		setSkillCount(0);
		setSkillCountMax(8);
	}

	@Override
	public ArrayList<String> skill(Enemigo enemigo, ArrayList<Enemigo> enemigos, Heroe heroe, int bonusDef) {
		ArrayList<String> eventos = new ArrayList<String>();
		if ( enemigos.size() < 6 ){
			eventos.add( ( ( Llamar_Orcos ) getHabilidades().get(0) ).mecanica( enemigos ) );
		}else{
			eventos.add(((Golpe_Concentrado)getHabilidades().get(1)).mecanica(this, heroe, bonusDef));
		}
		return eventos;
	}

	@Override
	protected void crearHabilidades() {
		getHabilidades().add(new Llamar_Orcos());
		getHabilidades().add(new Golpe_Concentrado());
	}

	@Override
	protected void crearPociones() {
	}

	@Override
	public Icon getIcon() {
		return new ImageIcon(Cacique_1.class.getResource("/imagenes/enemigos/garrosh.png"));
	}

	public String segundaFase(){
		String eventos = "";
		eventos += " Ante una derrota inminente y con esfuerzo para mantenerse en pie, el Jefe Orco corre hacia una estanteria al fondo de su habitacion y  \n";
		eventos += " toma una botella cubierta de polvo con un color negruzco y se la bebe de un trago. Segun se la esta terminando, ves como se cura,      \n";
		eventos += " rompe la botella con la mano y se gira hacia ti con una sonrisa, sin un rasguño en el cuerpo, mucho mas fuerte que antes y con rabia.  ";
		return eventos;
	}
}
