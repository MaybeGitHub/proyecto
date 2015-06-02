package datos;

import habilidades.Habilidad;

import java.util.ArrayList;

import pociones.Pocion;
import armaduras.Armadura;
import armas.Arma;
import escudos.Escudo;

public class Personaje {
	
	protected String nombre;
	protected int PV, PVMax, fuerza, destreza, inteligencia, nivel;
	protected Arma arma;
	protected Escudo escudo;
	protected Armadura armadura;
	protected ArrayList<Pocion> mochila;
	protected ArrayList<Habilidad> habilidades;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPV() {
		return PV;
	}

	public void setPV(int pV) {
		if ( pV <= 0 ){
			this.PV = 0;
		}else if ( pV >= getPVMax() ){
			PV  = getPVMax();
		}else{
			PV = pV;
		}
	}

	public int getFuerza() {
		return fuerza;
	}

	public void setFuerza(int fuerza) {
		this.fuerza = fuerza;
	}

	public int getDestreza() {
		return destreza;
	}

	public void setDestreza(int destreza) {
		this.destreza = destreza;
	}

	public int getInteligencia() {
		return inteligencia;
	}

	public void setInteligencia(int inteligencia) {
		this.inteligencia = inteligencia;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public Arma getArma() {
		return arma;
	}

	public void setArma(Arma arma) {
		this.arma = arma;
	}

	public Escudo getEscudo() {
		return escudo;
	}

	public void setEscudo(Escudo escudo) {
		this.escudo = escudo;
	}

	public Armadura getArmadura() {
		return armadura;
	}

	public void setArmadura(Armadura armadura) {
		this.armadura = armadura;
	}

	public ArrayList<Pocion> getPociones() {
		return mochila;
	}

	public void setMochila(ArrayList<Pocion> mochila) {
		this.mochila = mochila;
	}

	public ArrayList<Habilidad> getHabilidades() {
		return habilidades;
	}

	public void setHabilidades(ArrayList<Habilidad> habilidades) {
		this.habilidades = habilidades;
	}

	public int getPVMax() {
		return PVMax;
	}

	public void setPVMax(int pVMax) {
		PVMax = pVMax;
	}
	
	@Override
	public String toString() {
		return getNombre();
	}
}
