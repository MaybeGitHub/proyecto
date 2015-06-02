package personajes;

import habilidades.Golpe_Concentrado;
import habilidades.Habilidad;
import habilidades.Llamar_Orcos_Mejorado;

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

public class Cacique_2 extends Enemigo {

	public Cacique_2() {
		setNombre("Jefe Orco Transformado");
		setNivel(10);
		setFuerza(10);
		setDestreza(10);
		setInteligencia(10);
		setPVMax( 200 );
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
			eventos.add( ( ( Llamar_Orcos_Mejorado ) getHabilidades().get(0) ).mecanica( enemigos ) );
		}else{
			eventos.add(((Golpe_Concentrado)getHabilidades().get(1)).mecanica(this, heroe, bonusDef));
		}
		return eventos;
	}

	@Override
	protected void crearHabilidades() {
		getHabilidades().add(new Llamar_Orcos_Mejorado());
		getHabilidades().add(new Golpe_Concentrado());
	}

	@Override
	protected void crearPociones() {
	}

	@Override
	public Icon getIcon() {
		return new ImageIcon(Cacique_2.class.getResource("/imagenes/enemigos/garrosh2.png"));
	}
	
	public String terceraFase(){
		String eventos = "";
		eventos += " Loco de ira por la evidente derrota y debil por las heridas, el Jefe Orco Transformado empieza a pronunciar unas palabras estrañas y a engullirle    \n";
		eventos += " el poder oscuro que la pócima le proporcionó para derrotarte, lo que le hace retorcerse de dolor mientras se convierte en una monstruosidad ante     \n";
		eventos += " tus ojos. Cuando termina de transformarse, asesina a todos sus esbirros para desmostrarte su fuerza y una vez estan todos muertos, se gira hacia     \n";
		eventos += " ti... empiezas a oir susurros profundos y siniestras en tu cabeza . 'Solos...tu...y yo...Dejemonos...De...Jueguecitos'. Empieza la batalla final !!  ";
		return eventos;
	}
}
