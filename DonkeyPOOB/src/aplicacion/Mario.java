package aplicacion;

public  class Mario extends Personaje {

	public Mario(int x, int y) {
		// Personaje(int x, int y, String marioRight, String marioRRun, String marioR, String marioLeft, String marioLRun, String marioL, String kill, String scaleL, String scaleR) {
		super(x, y);
		setImagen("rsc/marioSprite0.png");
	}
	
	public void marioDerecha() {
		setImagen("rsc/marioSprite4.png");
	}
	
	

	


}
