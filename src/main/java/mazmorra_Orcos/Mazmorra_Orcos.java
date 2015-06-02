package mazmorra_Orcos;

import java.util.ArrayList;

import datos.Sala;

public class Mazmorra_Orcos {

	private ArrayList<Sala> mazmorra = new ArrayList<Sala>();
	
	public Mazmorra_Orcos(){
		mazmorra.add(new _01_Entrada_Principal());
		mazmorra.add(new _02_Hall());
		mazmorra.add(new _03_Vestibulo_Derecho());
		mazmorra.add(new _04_Pasillo());
		mazmorra.add(new _05_Vestibulo_Izquierdo());
		mazmorra.add(new _06_Sangrienta());
		mazmorra.add(new _07_Pictogramas());
		mazmorra.add(new _08_Sacrificios());
		mazmorra.add(new _09_Fuente());
		mazmorra.add(new _10_Sala_Jefe());
	}
	
	public ArrayList<Sala> getMazmorra(){
		return mazmorra;
	}
}
