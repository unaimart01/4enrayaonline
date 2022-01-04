package jugador;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;

public class Jugador implements Runnable {
	// definimos el puerto
	private int puerto = 8080;
	// definimos el host
	private String host = "localhost";
	// definimos las variables que nos ayudaran a establecer la comunicacion entre
	// cliente-servidor
	private DataInputStream in;
	private DataOutputStream out;
	// definimos el tablero en el que iremos guardando los movimientos de la partida
	private String tablero[][] = new String[6][7];
	// guardamos si nos toca o no
	private boolean turno;
	// guardamos el socket
	private Socket s;

	// constructor de la clase jugador
	public Jugador() {
		try {
			// inicializamos el socket y incializamos los puentes de comunicacion
			this.s = new Socket(host, puerto);
			this.in = new DataInputStream(s.getInputStream());
			this.out = new DataOutputStream(s.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// lo que se ejecuta dentro del hilo concurrentemente.
	public void run() {
		// definimos un scanner para leer por teclado la columna en la que el usuario
		// quiere insertar la ficha.
		try (Scanner leer = new Scanner(System.in);) {
			// inicializamos el tablero
			this.inicializartablero();
			// recibimos por partr del servidor
			// msgpartir[0] == contiene si somos X o O
			// msgpartir[1] == contiene si nuestro turno es true o false. Es decir, si nos
			// toca o no.
			String recibido = this.in.readLine();
			String[] msgpartir = recibido.split(":");
			int columna = 0;
			String columna2 = "";
			System.out.println("Eres " + msgpartir[0] + ":");
			this.turno = Boolean.valueOf(msgpartir[1]);
			while (true) {
				// mostramos el tablero con los movimientos que se han hecho hasta este momento
				this.mostrartablero();
				// Solo si tenemos el turno
				if (turno) {
					boolean insertado = false;
					while (insertado == false) {
						// Introducimos la columna en la que queremos insertar la ficha
						System.out.println("Introduce la columna a insertar la ficha :");
						columna2 = leer.nextLine();
						boolean sepuede = Validacion(columna2);
						while (sepuede == false) {
							if (sepuede == false) {
								System.out.println("Por favor, introduzca un numero valido: ");
								columna2 = leer.nextLine();
								sepuede = Validacion(columna2);
							}
						}
						// mientras que no sea una columna valida,el usuario tendra que seguir
						// introduciendo la columna hasta que una sea valida
						columna = Integer.parseInt(columna2);
						// enviamos al servidor la columna que el usuario ha elegido.
						this.enviarTurno(columna);
						// el servidor nos responde con un booleano que indica si la ficha se ha podido
						// insertar correctamente o no se ha podido insertar. En el caso de que no se
						// pueda insertar, el usuario tendra que meter otra columna hasta que esta sea
						// valida
						String sehainsertado = this.in.readLine();
						insertado = Boolean.valueOf(sehainsertado);
					}
				}
				// una vez insertada la ficha, el servidor nos devuelve los datos de la partida,
				// concretamente:
				// Datos[0] si es 1 nos indica que es X si es 0 nos indica que es O
				// Datos[1] nos indica la columna en la que el usuario ha insertado la ficha.
				// Datos[2] si contiene X es que ha ganado X. Si contiene O es que ha ganado O.
				// Si contiene empate es que la partida a finalizado y ha sido un empate Si
				// contiene nadie es que la partida aun no ha finalizado y se debe continuar
				String datospartida = this.in.readLine();
				String[] Datos = datospartida.split(":");
				// Si el turno era true, lo cambiamos a false, y si era false, lo cambiamos a
				// true.
				this.turno = !this.turno;
				// se inserta la ficha en el tablero y se muestra por pantalla
				this.insertarficha(Integer.parseInt(Datos[1]), Datos[0]);
				this.mostrartablero();
				// mostramos los mensajes por pantalla pertinentes en el caso de que la partida
				// haya finalizado con un ganador y perdedor, o por el contrario con un empate.
				if (!Datos[2].equals("nadie")) {
					if (msgpartir[0].equals(Datos[2])) {
						System.out.println("¡¡Felicidades!!, HAS GANADO LA PARTIDA");
						System.out.println("----------------------");
						System.out.println("Reiniciando Partida...");
						this.inicializartablero();
					} else if (Datos[2].equals("empate")) {
						System.out.println("¡¡Has empatado la partida!!");
						this.inicializartablero();
					} else if (!(Datos[2].equals("nadie")) && (!msgpartir[0].equals(Datos[2]))) {
						System.out.println("Has perdido la partida");
						this.inicializartablero();
					}
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// funcion que se encarga de mostrar el tablero
	private void mostrartablero() {
		System.out.println("-----------------");

		for (int i = 5; i > -1; i--) {
			int contador = 0;
			for (int o = 0; o < 7; o++) {
				contador++;

				System.out.print("|" + this.tablero[i][o]);
				if (contador == 7) {
					System.out.println("|");
				}

			}
		}
		System.out.println(" 1 2 3 4 5 6 7 ");
		System.out.println("-----------------");
	}

	// funcion que se encarga de insertar la ficha en la columna que el usuario haya
	// indicado
	// "marca" hace referencia a si el jugador es X o es O para poner su simbolo en
	// el tablero.
	private void insertarficha(int columna, String marca) {
		int i = 0;
		boolean insertado = false;
		while (i < 6 && insertado == false) {
			if (this.tablero[i][columna] == "-") {
				if (marca.equals("1")) {
					this.tablero[i][columna] = "x";
				} else {
					this.tablero[i][columna] = "o";
				}
				insertado = true;
			}
			i++;
		}

	}

//funcion que pone todas las casillas del tablero con el simbolo "-" que indica que esa casilla esta vacia.
	private void inicializartablero() {
		for (int i = 0; i < 6; i++) {
			for (int o = 0; o < 7; o++) {
				this.tablero[i][o] = "-";
			}
		}
	}

//funcion que envia la columna al servidor en caso de que le jugador tenga el turno. En caso de que no lo tenga, mostrara un mensaje por pantalla.
	private void enviarTurno(int c) {
		try {
			if (turno) {
				this.out.writeBytes(String.valueOf(c) + "\r\n");
			} else {
				System.out.println("No es tu turno");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// funcion que se encarga de validar de que la columna que el usuario mete por
	// teclado, no sea una letra y sea un numero comprendido entre el 1 y el 7
	private boolean Validacion(String cadena) {
		int num;
		try {
			num = Integer.parseInt(cadena);
			if (num > 7 || num < 1) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}

}
