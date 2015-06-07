package personajes;

import habilidades.Golpe_Concentrado;
import habilidades.Habilidad;

import java.awt.Window;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import baseDatosOracle.Heroe;
import pociones.Pocion;
import armaduras.Armadura;
import armaduras.Armadura_Placas;
import armaduras.Sin_Armadura;
import armas.Arma;
import armas.Espada_Orca;
import armas.Sin_Arma;
import datos.Enemigo;
import datos.Item;
import escudos.Escudo;
import escudos.Escudo_Metal;
import escudos.Sin_Escudo;

public class Cacique_3 extends Enemigo {

	private Heroe heroe;
	private boolean dichoTextoPrimeraFase = false;
	private boolean dichoTextoSegundaFase = false;
	private boolean dichoTextoTerceraFase = false;
	private Window owner;
	
	public Cacique_3(Heroe heroe, ArrayList<Enemigo> enemigos, Window owner) {
		setNombre("Jefe Orco Consumido");
		setNivel(10);
		setFuerza(10);
		setDestreza(10);
		setInteligencia(10);
		setPVMax( 500 );
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
		setSkillCountMax(6);
		this.heroe = heroe;
		this.owner = owner;
	}

	@Override
	public ArrayList<String> skill(Enemigo enemigo, ArrayList<Enemigo> enemigos, Heroe heroe, int bonusDef) {
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

	@Override
	public Icon getIcon() {
		return new ImageIcon(Cacique_3.class.getResource("/imagenes/enemigos/garrosh3.png"));
	}
	
	public String primerCuartoVida(){
		String eventoHabilidad = "";
		eventoHabilidad += " Notas como si tu cuerpo empezara a pesar mas y mas, como la vista se te va nublando. Lo que antes era una imagen clara del enemigo     \n";
		eventoHabilidad += " ahora es una nube borrosa con una luz brillante enfocando tu cabeza. Segun pasan los segundos te ves cada vez mas cerca el suelo.      \n";
		eventoHabilidad += " Con las pocas fuerzas que te quedan, decides alzar el escudo de metal y tratar reflectar su rayo hacia su origen, lo que provoca una   \n";
		eventoHabilidad += " explosion de magia y terminais los dos en el suelo !! Te levantas y descubres, que ambos habeis perdido parte de vuestro equipo.       ";
		return eventoHabilidad;
	}
	
	public String mitadVida(){
		String eventoHabilidad = "";
		eventoHabilidad += " Cuando vas a atacar al Orco, este desaparece ante tus ojos y todo el mundo se consume en una oscuridad que no te permite ver nada.     \n";
		eventoHabilidad += " De repente notas como si alguien se acercara rápido a por ti, asique te pones en guardia. Esquivas el ataque, esquivas el segundo y    \n";
		eventoHabilidad += " aprovechas la finta para clavar tu espada en el enemigo lo que provoca que este se desplome en el suelo. De repente te despiertas con  \n";
		eventoHabilidad += " un dolor agudo en el estomago. Al mirar ves sorprendido como tu armadura rota y que el enemigo al que has clavado la espada eras tu.   ";
		return eventoHabilidad;
	}
		
	public String ultimoCuartoVida(){
		String eventoHabilidad = "";
		eventoHabilidad += " El Cacique, o lo que queda de el, se enfurece y empieza a atacarte con una fuerza sobrenatural. Cada ataque que rechazas solo provoca   \n";
		eventoHabilidad += " que su ira aumente. La lluvia de golpes no te deja tiempo para recomponerte y contratacar lo que provoca que solo puedes defenderte.    \n";
		eventoHabilidad += " En una de tus fintas, el Cacique consigue bloquear tu espada con su puño y partirla con facilidad. Despues de hacerlo, se lanza a       \n";
		eventoHabilidad += " terminar el trabajo. Consigues que te golpea solo parcialmente y que su arma choque y termine rota contra la pared en la que estabas.   ";
		return eventoHabilidad;
	} 
	
	@Override
	public void setPV(int vida){		
		if ( vida <= 1 ){
			this.PV = 1;
		}else if ( vida >= getPVMax() ){
			PV  = getPVMax();
		}else{
			PV = vida;
		}
		
		if ( getPV() < getPVMax()*3/4 && !dichoTextoPrimeraFase ){
			Escudo sinEscudo = new Sin_Escudo();
			heroe.setEscudo(sinEscudo.getTipoEscudoBBDD());			
			JOptionPane.showMessageDialog(owner, primerCuartoVida());
			dichoTextoPrimeraFase = true;
		}else if ( getPV() < getPVMax()/2 && !dichoTextoSegundaFase ){
			Armadura sinArmadura = new Sin_Armadura();
			heroe.setArmadura(sinArmadura.getTipoArmaduraBBDD());
			JOptionPane.showMessageDialog(owner, mitadVida());
			dichoTextoSegundaFase = true;
		}else if ( getPV() < getPVMax()/4 && !dichoTextoTerceraFase ){
			Arma sinArma = new Sin_Arma();
			heroe.setArma(sinArma.getTipoArmaBBDD());
			JOptionPane.showMessageDialog(owner, ultimoCuartoVida());
			dichoTextoTerceraFase = true;
		}
	}
}
