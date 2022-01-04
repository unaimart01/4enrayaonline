package servidor;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {
	//definimos el numero de jugadores que soportara el servidor. al ser un juego de 1 vs 1 seran 2.
	private final static int numeroconexiones = 2;
	//definimos el puerto en el que se alojará el servidor
	private final static int puerto = 8080;
	//creamos una lista para albergar a los jugadores que juegen la partida
	private final static List<Socket> jugadores = new ArrayList<Socket>();
	//creamos un tablero para poner en el los movimientos que se den durante la partida.
	private static int tablero[][] = new int[6][7];
	//definimos la variable que se encargará de dar un turno a los jugadores
	private static int turnos = 0;

	public static void main(String[] args) {
		//creamos la pool de hilos
		ExecutorService pool = Executors.newFixedThreadPool(2);
		//creamos el servidor
		try (ServerSocket servidor = new ServerSocket(puerto, numeroconexiones);) {
			System.out.println("Esperando jugadores....");
			while (true) {
				try {
					//aceptamos la conexion de un jugador
					Socket jugador = servidor.accept();
					//lo añadimos a la lista de los jugadores.
					jugadores.add(jugador);
					//ejecutamos el hilo que contendra el trascurso de la partida.
					//le pasamos el jugador, la lista de jugadores, el tablero y su turno.
					pool.execute(new Hilo(jugadores, jugador, tablero, turnos));
					turnos++;
					//si el turno llega a dos significa que el servidor no puede alojar a mas jugadores.
					if (turnos == 2) {
						System.out.println("partida llena");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//en el caso de que haya un fallo, se cierra la pool.
			pool.shutdown();
		}

	}

}
