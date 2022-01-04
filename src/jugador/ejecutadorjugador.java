//UNAI MARTINEZ URRACA

package jugador;
//Esta clase sirve para poder ejecutar el juego en modo Consola

public class ejecutadorjugador {

	private static Jugador jugador;
//simplemente tiene una llamada al constructor de la clase jugador y posteriormente se arranca o inicia el hilo.
	public static void main(String[] args) {
		jugador = new Jugador();
		Thread hilo = new Thread(jugador);
		hilo.start();
	}
}
