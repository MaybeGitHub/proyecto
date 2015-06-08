package datos;

import habilidades.Curar;
import habilidades.Golpe_Concentrado;
import habilidades.Habilidad;
import habilidades.Llamarada;
import habilidades.Torbellino;

import java.util.ArrayList;

import baseDatosOracle.Heroe;
import pociones.Pocion;
import pociones.Pocion_Curativa;
import pociones.Pocion_Explosiva;
import pociones.Pocion_Mana;
import armaduras.Armadura;
import armas.Arma;
import escudos.Escudo;

public class Combate {

	private Heroe heroe;
	private ArrayList<Item> botinEnemigos = new ArrayList<Item>();

	public Combate(Heroe heroe, Sala sala){
		setHeroe(heroe);
		for ( Enemigo maloso : sala.getEnemigos()){
			botinEnemigos.addAll(maloso.getBotin());
		}
	}

	public void setHeroe(Heroe heroe) {
		this.heroe = heroe;
	}

	public ArrayList<String> usarPocion(ArrayList<Enemigo> enemigos, Pocion pocion, ArrayList<Habilidad> habilidades) {
		ArrayList<String> eventos = new ArrayList<String>();
		if ( pocion instanceof Pocion_Curativa ){
			eventos.add( ( ( Pocion_Curativa )pocion ).mecanica( heroe ) );
		}

		if ( pocion instanceof Pocion_Mana ){
			eventos.add( ( ( Pocion_Mana )pocion ).mecanica( habilidades ) );
		}

		if ( pocion instanceof Pocion_Explosiva ){
			eventos.add( ( ( Pocion_Explosiva )pocion ).mecanica( heroe, enemigos ) );
			ArrayList<Enemigo> enemiesDead = new ArrayList<Enemigo>();
			for ( Enemigo enemy : enemigos ){
				if ( enemy.getPV() <= 0 ){
					heroe.setExperiencia(heroe.getExperiencia()+enemy.getNivel());
					enemiesDead.add(enemy);						
				}
			}
			enemigos.removeAll(enemiesDead);
		}
		return eventos;
	}

	public ArrayList<String> usarHabilidad(Habilidad habilidad, ArrayList<Enemigo> enemigos, Enemigo enemigo ) {		
		ArrayList<String> eventos = new ArrayList<String>();
		if ( habilidad instanceof Curar ){
			eventos.add( ( ( Curar )habilidad ).mecanica( heroe ) );
		}

		if ( habilidad instanceof Llamarada ){
			ArrayList<Enemigo> enemigosEliminados = new ArrayList<Enemigo>();
			eventos.add( ( ( Llamarada )habilidad ).mecanica( heroe, enemigos ) );
			for ( Enemigo enemy : enemigos ){
				if ( enemy.getPV() <= 0 ){
					enemigosEliminados.add(enemy);
					heroe.setExperiencia(heroe.getExperiencia()+enemy.getNivel());
				}
			}
			enemigos.removeAll(enemigosEliminados);
		}

		if ( habilidad instanceof Golpe_Concentrado ){
			eventos.add( ( ( Golpe_Concentrado )habilidad ).mecanica( heroe, enemigo ) );
			if ( enemigo.getPV() <= 0 ){
				enemigos.remove(enemigo);
				heroe.setExperiencia(heroe.getExperiencia()+enemigo.getNivel());
			}
		}
		
		if ( habilidad instanceof Torbellino ){
			ArrayList<Enemigo> enemigosEliminados = new ArrayList<Enemigo>();
			eventos.add(((Torbellino) habilidad).mecanica(heroe, enemigos));
			for ( Enemigo enemy : enemigos ){
				if ( enemy.getPV() <= 0 ){
					enemigosEliminados.add(enemy);
					heroe.setExperiencia(heroe.getExperiencia()+enemy.getNivel());
				}
			}
			enemigos.removeAll(enemigosEliminados);
		}
		return eventos;
	}

	public ArrayList<String> atacarConArma(Enemigo enemigo, ArrayList<Enemigo> enemies) {
		ArrayList<String> eventos = new ArrayList<String>();
		int resistencia = enemigo.getArmadura().bonusArmor() + enemigo.getEscudo().bonusDef();
		int dano = heroe.getAtaque()*3 + heroe.getFuerza() + heroe.getDestreza() - resistencia;
		if ( dano >= 1 ){			
			enemigo.setPV(enemigo.getPV() - dano);
			eventos.add("Golpeas a " +  enemigo.getNombre() + ": " + dano);
			if ( enemigo.getPV() <= 0 ){
				enemies.remove(enemigo);
				heroe.setExperiencia(heroe.getExperiencia()+enemigo.getNivel());
				eventos.add("Has matado al enemigo " + enemigo);
			}			
		}else{
			eventos.add("La armadura del objetivo es impenetrable para ti en este turno");
		}
		return eventos;
	}

	public ArrayList<String> atacarConArma(Enemigo enemigo, int bonusDef){
		ArrayList<String> eventos = new ArrayList<String>();
		int dano = (enemigo.getArma().dano() + enemigo.getFuerza() - heroe.getDefensa()) / bonusDef;
		if ( dano >= 1 ){
			heroe.setPV(heroe.getPV() - dano);
			eventos.add("Te golpea " +  enemigo.getNombre() + " y te hace " + dano + " puntos de dano");			
		}else{
			eventos.add("Tu armadura bloquea por completo el dano del " + enemigo);
		}
		return eventos;
	}

	public ArrayList<String> turnoEnemigos(Enemigo enemigo, ArrayList<Enemigo> enemigos, int bonusDef) {
		ArrayList<String> eventos = new ArrayList<String>();
		if ( enemigo.getSkillCount() == enemigo.getSkillCountMax() - 1 ){
			eventos.addAll( enemigo.skill(enemigo, enemigos, heroe, bonusDef) );
		}else {
			eventos.addAll( atacarConArma(enemigo, bonusDef) );
		}
		return eventos;
	}

	public ArrayList<String> obtenerBotin(ArrayList<Item> botinEnemigos) {
		return equiparBotin(getBotinEnemigos());
	}

	public ArrayList<String> equiparBotin(ArrayList<Item> botinEnemigos) {
		ArrayList<String> eventos = new ArrayList<String>();
		for(Item item : botinEnemigos ){
			if ( item instanceof Arma ){
				Arma armaHeroe = Arma.creaAPartirDeTipoBBDD(heroe.getArma());
				Arma armaLoot = Arma.creaAPartirDeTipoBBDD(((Arma) item).getTipoArmaBBDD());
				if ( armaLoot.dano() > armaHeroe.dano() ){
					heroe.setArma(((Arma) item).getTipoArmaBBDD());
					eventos.add("Te equipas " + item);
				}
			}

			if ( item instanceof Escudo ){
				Escudo escudoHeroe = Escudo.creaAPartirDeTipoBBDD(heroe.getEscudo());
				Escudo escudoLoot = Escudo.creaAPartirDeTipoBBDD(((Escudo) item).getTipoEscudoBBDD());
				if ( escudoLoot.bonusDef() > escudoHeroe.bonusDef() ){
					heroe.setEscudo(((Escudo) item).getTipoEscudoBBDD());
					eventos.add("Te equipas " + item);
				}
			}

			if ( item instanceof Armadura ){
				Armadura armaduraHeroe = Armadura.creaAPartirDeTipoBBDD(heroe.getArmadura());
				Armadura armaduraLoot = Armadura.creaAPartirDeTipoBBDD(((Armadura) item).getTipoArmaduraBBDD());
				if ( armaduraLoot.bonusArmor() > armaduraHeroe.bonusArmor() ){
					heroe.setArmadura(((Armadura) item).getTipoArmaduraBBDD());
					eventos.add("Te equipas " + item);
				}
			}
		}
		return eventos;
	}

	public ArrayList<Item> getBotinEnemigos() {
		return botinEnemigos;
	}
}
