package aplicacion;
import java.lang.Math;
import java.util.Random;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class DonkeyPOOB {
	private static DonkeyPOOB juego = null; 
	private Jugador[] jugadores; 
	private static Barril[] barriles;
	private static Sorpresa[] sorpresas;
	public static ArrayList<Plataforma> piso; 
	public static ArrayList<Escalera> puntosEscalera; 
	private boolean enPausa; 
	
	/**
	 * Constructor DonkeyPOOB
	 * */
	
	public DonkeyPOOB() {

		piso = new ArrayList<Plataforma>();
		puntosEscalera = new ArrayList<Escalera>();
	}
	
	/**
	 * Retorna el juego existente 
	 * @return DonkeyPOOB juego 
	 * */
	
	public static DonkeyPOOB getJuego() {
		if(juego == null) {
			juego = new DonkeyPOOB();
		}
		return juego; 		
	}
	
	/**
	 * Crea un nuevo juego 
	 */
	
	public static void nuevoJuego() {
		juego = new DonkeyPOOB();
	}
	
	/**
	 * Prepara los tipos de barriles para jugar
	 * @param Nbarriles numero de barriles  
	 * */
	
	public void prepareBarriles(int Nbarriles) {
		barriles = new Barril[Nbarriles];
		for(int i=0;i<barriles.length;i++) {
			int x = elegirElBarrilaLanzar(0,3);
			//System.out.println(x);
			if (x==0) {
				barriles[i]= new BarrilAmarillo(200,200);
			}
			else if(x==1){
				barriles[i]= new BarrilAzul(200,200);
			}
			else if(x==2) { 
				barriles[i]= new BarrilVerde(200,200);
			}
			else if(x==3) { 
				int cordenadaX = elegirElBarrilaLanzar(200,795);
				barriles[i]= new BarrilRojo(cordenadaX,200);
			}
		}							
	}
	
	
	/**
	 * Prepara las sorpresas para jugar 
	 * @param Nsorpresas numero de sorpresas 
	 * */
	public void prepareSorpresa(int Nsorpresas) {
		sorpresas = new Sorpresa [Nsorpresas];
		for(int i=0;i<sorpresas.length;i++) {
			//int x = ElegirElBarrilaLanzar(0,3);
			//System.out.println(x);
			
			sorpresas[i]= new Martillo(200,587);
		}							
	}
	
	/**
	 * Crea los jugadores dependiendo el tipo si es una maquina o un usuario 
	 * @param NJugadores numero de jugadores 
	 * @param maquina el tipo de jugador 
	 * */
	
	public void prepareJugadores(int NJugadores,int maquina){
		jugadores = new Jugador[NJugadores];
		if(NJugadores == 2){
			jugadores[0] = new Usuario(188,550); //Posicion izquierda
			if(maquina > 0) {
				switch (maquina){
				case 1:
					jugadores[1] = new Protector(556,550); //Posicion derecha
					break;
				case 2:
					jugadores[1] = new Temeroso(556,550); //Posicion derecha
					break;
				default:
					jugadores[1] = new Mimo(556,550); //Posicion derecha
					break;
					}
			}else {
				jugadores[1] = new Usuario(556,550); //Posicion derecha
			}
		}else{
			jugadores[0] = new Usuario(400,750); //Posicion centro
		}
	}
	
	/**
	 * Prepara los barriles seleccionados en el menu para jugar 
	 * @param barriles arreglo con los barriles seleccionados 
	 * */
	
	public void barrilesParaJugar(int[] barriles) throws DonkeyPOOBException{
		int numeroBarriles = 0;
		for (int i=0;i<barriles.length;i++) {
			if(barriles[i]==1) {
				numeroBarriles+=1;
			}
		}
		if(numeroBarriles==0) {
			throw new DonkeyPOOBException(DonkeyPOOBException.SIN_BARRILES);
		}
	}
	
	/**
	 * Prepara las sorpresas seleccionadas en el menu para jugar 
	 * @param sorpresas seleccionadas 
	 * */
	
	public void sorpresasParaJugar(int[] sorpresas) throws DonkeyPOOBException{
		int numeroSorpresas = 0;
		for (int i=0;i<sorpresas.length;i++) {
			if(sorpresas[i]==1) {
				numeroSorpresas+=1;
			}
		}
		if(numeroSorpresas==0) {
			throw new DonkeyPOOBException(DonkeyPOOBException.SIN_SORPRESAS);
		}
	}
	
	/**
	 * devuelve el numero de jugadores 
	 * @return numero de jugadores 
	 * */
	
	public int numeroJugadores() {
		return jugadores.length;
	}
	
	/**
	 * Devuelve el numero de sorpresas 
	 * @return numero de sorpresas 
	 * */

	public int numeroDeSorpresas() {
		return sorpresas.length;
	}
	
	/**
	 * Retorna un jugador dependiendo el indice que se de 
	 * @param i indice del jugador 
	 * @return jugador 
	 * */
	
	public Jugador getJugador(int i){
		Jugador jugador = null;
		if (i >- 1 && i < jugadores.length) {
			jugador = jugadores[i];
		}
		return jugador;
	}
	
	/**
	 * Cambia el estado del juego a pausado 
	 * */
	
	public void pausa() {
		enPausa = !enPausa;
	}
	
	/**
	 * Mueve un jugador a la derecha dependiendo el numero del jugador 
	 * */
	
	public void JugadorRight(int n) {
		jugadores[n].moveRight();
	}
	
	/**
	 * Mueve un jugador a la izquierda dependiendo el numero del jugador 
	 * */
	public void JugadorLeft(int n) {
		jugadores[n].moveLeft();
	}
	
	/**
	 * Mueve un jugador a hacia arriba dependiendo el numero del jugador 
	 * */
	
	public void JugadorUp(int n) {
		jugadores[n].moveUp();
	}
	
	/**
	 * Mueve un jugador hacia abajo dependiendo el numero del jugador 
	 * */
	
	public void JugadorDown(int n) {
		jugadores[n].moveDown();
	}
	
	/**
	 * Ejecuta un salto  en un jugador dependiendo el numero del jugador 
	 * */
	public void jugadorSalto(int n) {
		jugadores[n].moveJump();
	}
	
	/**
	 * Cambia el estado del juego a terminado o gameOver
	 * @return aunActivo si el jugador sigue con vidas o no 
	 * */
	
	public boolean gameOver() {
		boolean aunActivo = false;
		for(Jugador j: jugadores) {
			aunActivo = (j.getVidas()==0)?true:aunActivo;
		}
		return aunActivo;
	}
	
	/**
	 * Mueve un jugador a la derecha dependiendo el numero del jugador 
	 * */
	
	
	/**
	 * Retorna si el juego esta pausado o no 
	 * @return enPausa 
	 * */
	
	public boolean enPausa() {
		return enPausa; 
	}
	
	/**
	 * A�ade las plataformas del mapa 
	 * */
	
	public void addPlataformas() {
		int[] puntoCero = {300,211};int[] puntoUno = {497,211};
 		Plataforma plataformaUno = new Plataforma(puntoCero, puntoUno);
 		piso.add(plataformaUno);
		
 		int[] puntoDos = {0,346};int[] puntoTres = {795,346};		
		Plataforma plataformaDos = new Plataforma(puntoDos, puntoTres);
		piso.add(plataformaDos);
		
		int[] puntoCuatro = {100,440};int[] puntoCinco = {900,440};		
		Plataforma plataformaTres = new Plataforma(puntoCuatro, puntoCinco);
		piso.add(plataformaTres);
		
		int[] puntoSeis = {0,530};int[] puntoSiete = {800,530};		
		Plataforma plataformaCuatro = new Plataforma(puntoSeis, puntoSiete);
		piso.add(plataformaCuatro);
		int[] puntoOcho = {100,620};int[] puntoNueve = {900,620};		
		Plataforma plataformaCinco = new Plataforma(puntoOcho, puntoNueve);
		piso.add(plataformaCinco);
		int[] puntoDiez = {0,715};int[] puntoOnce = {800,715};		
		Plataforma plataformaSeis = new Plataforma(puntoDiez, puntoOnce);
		piso.add(plataformaSeis);
		int[] puntoDoce = {0,805};int[] puntoTrece = {892,805};		
		Plataforma plataformaSiete = new Plataforma(puntoDoce, puntoTrece);
		piso.add(plataformaSiete);
	}	
	
	/**
	 * A�ade las escaleras del mapa 
	 * */
	
	public void addEscaleras() {
		int[] puntoCero = {481,211};int[] puntoUno = {481,346};
 		Escalera EscaleraUno = new Escalera(puntoCero, puntoUno);
 		puntosEscalera.add(EscaleraUno);
		
 		int[] puntoDos = {750,346};int[] puntoTres = {750,440};		
		Escalera EscaleraDos = new Escalera(puntoDos, puntoTres);
		puntosEscalera.add(EscaleraDos);

		int[] puntoCuatro = {144,440};int[] puntoCinco = {144,530};		
		Escalera EscaleraTres = new Escalera(puntoCuatro, puntoCinco);
		puntosEscalera.add(EscaleraTres);
		
		int[] puntoSeis = {308,440};int[] puntoSiete = {308,530};		
		Escalera EscaleraCuatro = new Escalera(puntoSeis, puntoSiete);
		puntosEscalera.add(EscaleraCuatro);
		int[] puntoOcho = {488,530};int[] puntoNueve = {488,620};		
		Escalera EscaleraCinco = new Escalera(puntoOcho, puntoNueve);
		puntosEscalera.add(EscaleraCinco);
		int[] puntoDiez = {762,530};int[] puntoOnce = {762,620};		
		Escalera EscaleraSeis = new Escalera(puntoDiez, puntoOnce);
		puntosEscalera.add(EscaleraSeis);
		
		int[] puntoDoce = {143,620};int[] puntoTrece = {143,715};		
		Escalera EscaleraSiete = new Escalera(puntoDoce, puntoTrece);
		puntosEscalera.add(EscaleraSiete);
		
		int[] puntoCatorce = {343,620};int[] puntoQuince= {343,715};		
		Escalera EscaleraOcho = new Escalera(puntoCatorce, puntoQuince);
		puntosEscalera.add(EscaleraOcho);
		
		int[] puntoDieciseis = {766,715};int[] puntoDiecisiete = {766,805};		
		Escalera EscaleraNueve = new Escalera(puntoDieciseis, puntoDiecisiete);
		puntosEscalera.add(EscaleraNueve);				
	}
	
	/**
	 * Retorna las plataformas del mapa
	 * @return piso plataformas 
	 * */
	public static ArrayList<Plataforma> getPlataformas(){
		return piso; 		
	}  
	
	/**
	 * Retorna las escaleras del mapa 
	 * @return puntosEscalera donde estan ubicadas 
	 * */
	
	public static ArrayList<Escalera> getEscaleras(){
		return puntosEscalera; 
		
	} 
	
	/**
	 * Retorna los barriles con los que se esta jugando 
	 * @return barriles 
	 * */
		
	public static Barril[] getBarriles() {
		return barriles;
	}
	
	/**
	 * Da un barril en una posicion i especifica
	 * @param i posicion del barril
	 * @return el barril en la posicion i, si no retorna null
	 */
	public Barril getBarril(int i){
		Barril barril = null;
		if (i >- 1 && i< barriles.length) {
			barril = barriles[i];
		}
		return barril;
	}
	
	/**
	 * Lanza los barriles seleccionados
	 * */
	
	public void lanzarBarriles() {
		
		
		for (int i = 0; i < barriles.length;i++) {
			if (i == 0) {
				barriles[0].moverHastaUnaPlataforma();
			}else {
				if(barriles[i-1].getY() >= 400) {
					barriles[i].moverHastaUnaPlataforma();
					
				}
				
			}
			if(juego.getJugador(0).getPersonaje().comprobarColision(barriles[i].getX(), barriles[i].getY())) {
				quitarVidas(0);
			}
		}
	}
	public void colisionSopresa() {
		for (int i = 0; i < sorpresas.length;i++) {
			if(juego.getJugador(0).getPersonaje().comprobarColisionSorpresa(sorpresas[i].getX(), sorpresas[i].getY()) ) {
				juego.getJugador(0).poderMartillo();
				sorpresas[i].setVisible(false);
			}
		}
	}
	/**
	 * Selecciona aleatoriamente un numero dependiendo un rango  
	 * @param min rango minimo 
	 * @param max rango maximo 
	 * @return x numero aleatorio 
	 * */
	public static int elegirElBarrilaLanzar(int min, int max){
		int x =(int) (Math.random()*((max-min)+1))+min;
		return x;
	}
	
	/**
	 * Quita una vida a un jugador especifico 
	 * @param indice del jugador 
	 * */
	
	public void quitarVidas(int jugador) {
		jugadores[jugador].quitarVida();
	}
	
	/**
	 * Retorna la sorpresa en un indice dado 
	 * @param indice de la sorpresa 
	 * */
	
	public Sorpresa getSorpresa(int i){
		return sorpresas[i];
	}
	
	public boolean finished() {
		return false;
	}
	
}
