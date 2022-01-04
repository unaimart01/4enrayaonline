package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//clase que se encargará del transcurso de la partida.
public class Hilo implements Runnable {
	// definimos el socket en el que transcurre la partida.
	private Socket socket;
	// definimos el jugador que esta jugando X o O
	private int jugadoractivo;
	// definimos un tablero en el que guardaremos los movimientos de la partida.
	private int tablero[][];
	// creamos una lista con los jugadores de la partida
	private List<Socket> jugadores = new ArrayList<Socket>();
	// definimos el turno de cada jugador
	private boolean turno;
	// esta variable nos ayuda a generar un turno aleatorio, para que no siempre
	// empieze X o O.
	private static int quienempieza;

	// constructor de la clase
	public Hilo(List<Socket> jugadores, Socket socket, int[][] tablero, int letocajugador) {
		// asignamos todos los parametros a sus correspondientes atributos.
		this.jugadores = jugadores;
		this.socket = socket;
		this.tablero = tablero;
		this.jugadoractivo = letocajugador;
		// cuando entra un jugador se genera un turno. Por lo que si el primer jugador
		// obtiene como turno=true el segundo obtendra turno=false o viceversa.
		if (this.jugadores.size() == 1) {
			Random generador = new Random();
			// generador que genera numero entero entre 0 y n-1 (en este caso entre 0 y 1)
			quienempieza = generador.nextInt(2);
		}

	}

	// lo que se ejecuta dentro del hilo concurrentemente.
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		// abrimos los canales de comunicacion con el cliente
		try (DataOutputStream out = new DataOutputStream(this.socket.getOutputStream());
				DataInputStream in = new DataInputStream(this.socket.getInputStream());) {
			// incializamos el tablero
			this.inicializartablero();
			// asignamos el turno en funcion a la variable "quienempieza" explicada
			// anteriormente.
			if (this.jugadoractivo == quienempieza) {
				this.turno = true;
			} else {
				this.turno = false;
			}
			// generamos un mensaje que contendra primero si es X o si es O y posteriormente
			// contendrá si su turno es true o por el contrario es false.
			String mandarturno = "";
			if (this.jugadoractivo == 1) {
				mandarturno = mandarturno + "X:" + this.turno;
			} else {
				mandarturno = mandarturno + "O:" + this.turno;
			}
			// enviamos a los jugadores el mensaje
			out.writeBytes(mandarturno + "\r\n");
			// definimos un bucle infinito
			while (true) {
				// el servidor, recibe por parte de un jugador la columna en la que quiere
				// insertar la ficha.
				String recibirpos = in.readLine();
				int columna = Integer.parseInt(recibirpos) - 1;

				boolean ins = false;
				while (ins == false) {
					// probamos que la ficha se puede introducir en la columna que el usuario ha
					// dichoo.
					// si no es asi, el servidor manda un mensaje al usuario advirtiendo de que no
					// se puede introducir en esa columna por algun motivo.
					// esto se hará tantas veces como la columna no sea valida, acabando por tanto,
					// cuando la columna sea valida.
					ins = this.insertarficha(columna);
					out.writeBytes(Boolean.toString(ins) + "\r\n");
					if (ins == false) {
						String recibirpos2 = in.readLine();
						columna = Integer.parseInt(recibirpos2) - 1;
					}
				}
				// una vez hemos insertado la ficha en el tablero y sabemos que la columna es
				// valida generamos un mensaje que enviaremos a ambos jugadores que esten
				// jugando la partida.
				String envmng = "";
				// este mensaje contendra primero, el jugador que ha hecho el movimiento(1 si se
				// trata de X y 0 si se trata de O)
				// Posteriormente contendra la columna en la que se ha insertado la ficha
				// Por ultimo, contendra un mensaje que puede ser X si ha ganado X, O si ha
				// ganado O, nadie si la partida no ha finalizado o empate si el tablero se ha
				// llenado y no hay ningun ganador
				envmng = envmng + this.jugadoractivo + ":";
				envmng = envmng + columna + ":";
				// llamamos a las funciones que se encargan de comprobar que el tablero este
				// lleno o que alguien haya ganado.
				boolean ganador = this.gana(this.jugadoractivo);
				boolean lleno = this.tablerolleno();

				if (!lleno && !ganador) {
					envmng = envmng + "nadie";
				} else if (!ganador && lleno) {
					envmng = envmng + "empate";
				} else if (ganador) {
					if (this.jugadoractivo == 1) {
						envmng = envmng + "X";
					} else {
						envmng = envmng + "O";
					}
					this.inicializartablero();
				}
				// una vez creado el mensaje, enviamos dicho mensaje para todos los jugadores
				// que esten jugando la partida.
				int tam = this.jugadores.size();
				for (int i = 0; i < tam; i++) {
					DataOutputStream out2 = new DataOutputStream(this.jugadores.get(i).getOutputStream());
					out2.writeBytes(envmng + "\r\n");
				}

			}
		} catch (Exception e) {
			// en el caso de que un jugador abandone la partida, se le elimina de la lista
			// de jugadores y se reinicia el tablero
			e.printStackTrace();
			for (int i = 0; i < this.jugadores.size(); i++) {
				if (this.jugadores.get(i) == socket) {
					this.jugadores.remove(i);
					break;
				}
			}
			this.inicializartablero();
		}
	}

	// funcion que se encarga de inicializar el tablero. Pone todas las casillas con
	// un -1 que indica que dicha casilla esta libre.
	private void inicializartablero() {
		for (int i = 0; i < 6; i++) {
			for (int o = 0; o < 7; o++) {
				this.tablero[i][o] = -1;
			}
		}
	}

	// funcion que se encarga de insertar la ficha en el tablero en el caso de que
	// se pueda. Devuelve true en el caso de que si se haya insertado, y false en
	// caso contrario
	private boolean insertarficha(int columna) {
		int i = 0;
		while (i < 6) {
			if (this.tablero[i][columna] == -1) {
				this.tablero[i][columna] = this.jugadoractivo;
				return true;
			}
			i++;
		}

		return false;

	}

//algoritmo que se encarga de detectar si el jugador que se le pasa como parametro ha ganado la partida.
	private boolean gana(int numjugador) {
		int gana1 = 0;
		int gana2 = 0;
		int seguido = 0;
		int x = 0;
		int y = 0;
		// detecta si hay 4 fichas seguidas en vertical
		while (x < 6 && seguido < 4) {
			gana1 = 0;
			seguido = 0;
			while (y < 7 && seguido < 4) {
				if (tablero[x][y] == numjugador) {
					gana1++;
					seguido++;
				} else {
					seguido = 0;
				}
				y++;
			}
			x++;
			y = 0;
		}
		if (gana1 >= 4 && seguido >= 4) {
			return true;
		}

		x = 0;
		y = 0;
		seguido = 0;
		// detecta si hay cuatro fichas seguidas en horizontal
		while (y < 7 && seguido < 4) {
			gana2 = 0;
			seguido = 0;
			while (x < 6 && seguido < 4) {
				if (tablero[x][y] == numjugador) {
					gana2++;
					seguido++;
				} else {
					seguido = 0;
				}
				x++;
			}
			y++;
			x = 0;
		}
		if (gana2 >= 4 && seguido >= 4) {
			return true;
		}
		// deteccion de todas las diagonales posibles.
		x = 2;
		y = 0;
		int gana3 = 0;
		seguido = 0;
		while (x < 6 && y < 4 && seguido < 4) {
			if (tablero[x][y] == numjugador) {
				gana3++;
				seguido++;
			} else {
				seguido = 0;
			}
			x++;
			y++;
		}
		if (gana3 >= 4 && seguido >= 4) {
			return true;
		}
		x = 1;
		y = 0;
		gana3 = 0;
		seguido = 0;
		while (x < 6 && y < 5 && seguido < 4) {
			if (tablero[x][y] == numjugador) {
				gana3++;
				seguido++;
			} else {
				seguido = 0;
			}
			x++;
			y++;
		}
		if (gana3 >= 4 && seguido >= 4) {
			return true;
		}
		x = 0;
		y = 0;
		gana3 = 0;
		seguido = 0;
		while (x < 6 && y < 6 && seguido < 4) {
			if (tablero[x][y] == numjugador) {
				gana3++;
				seguido++;
			} else {
				seguido = 0;
			}
			x++;
			y++;
		}
		if (gana3 >= 4 && seguido >= 4) {
			return true;
		}
		x = 0;
		y = 1;
		gana3 = 0;
		seguido = 0;
		while (x < 6 && y < 7 && seguido < 4) {
			if (tablero[x][y] == numjugador) {
				gana3++;
				seguido++;
			} else {
				seguido = 0;
			}
			x++;
			y++;
		}
		if (gana3 >= 4 && seguido >= 4) {
			return true;
		}
		x = 0;
		y = 2;
		gana3 = 0;
		seguido = 0;
		while (x < 5 && y < 7 && seguido < 4) {
			if (tablero[x][y] == numjugador) {
				gana3++;
				seguido++;
			} else {
				seguido = 0;
			}
			x++;
			y++;
		}
		if (gana3 >= 4 && seguido >= 4) {
			return true;
		}
		x = 0;
		y = 3;
		gana3 = 0;
		seguido = 0;
		while (x < 4 && y < 7 && seguido < 4) {
			if (tablero[x][y] == numjugador) {
				gana3++;
				seguido++;
			} else {
				seguido = 0;
			}
			x++;
			y++;
		}
		if (gana3 >= 4 && seguido >= 4) {
			return true;
		}
		x = 2;
		y = 6;
		gana3 = 0;
		seguido = 0;
		while (x < 6 && y > 2 && seguido < 4) {
			if (tablero[x][y] == numjugador) {
				gana3++;
				seguido++;
			} else {
				seguido = 0;
			}
			x++;
			y--;
		}
		if (gana3 >= 4 && seguido >= 4) {
			return true;
		}
		x = 1;
		y = 6;
		gana3 = 0;
		seguido = 0;
		while (x < 6 && y > 1 && seguido < 4) {
			if (tablero[x][y] == numjugador) {
				gana3++;
				seguido++;
			} else {
				seguido = 0;
			}
			x++;
			y--;
		}
		if (gana3 >= 4 && seguido >= 4) {
			return true;
		}
		x = 0;
		y = 6;
		gana3 = 0;
		seguido = 0;
		while (x < 6 && y > 0 && seguido < 4) {
			if (tablero[x][y] == numjugador) {
				gana3++;
				seguido++;
			} else {
				seguido = 0;
			}
			x++;
			y--;
		}
		if (gana3 >= 4 && seguido >= 4) {
			return true;
		}
		x = 0;
		y = 5;
		gana3 = 0;
		seguido = 0;
		while (x < 6 && y > -1 && seguido < 4) {
			if (tablero[x][y] == numjugador) {
				gana3++;
				seguido++;
			} else {
				seguido = 0;
			}
			x++;
			y--;
		}
		if (gana3 >= 4 && seguido >= 4) {
			return true;
		}
		x = 0;
		y = 4;
		gana3 = 0;
		seguido = 0;
		while (x < 5 && y > -1 && seguido < 4) {
			if (tablero[x][y] == numjugador) {
				gana3++;
				seguido++;
			} else {
				seguido = 0;
			}
			x++;
			y--;
		}
		if (gana3 >= 4 && seguido >= 4) {
			return true;
		}
		x = 0;
		y = 3;
		gana3 = 0;
		seguido = 0;
		while (x < 4 && y > -1 && seguido < 4) {
			if (tablero[x][y] == numjugador) {
				gana3++;
				seguido++;
			} else {
				seguido = 0;
			}
			x++;
			y--;
		}
		if (gana3 >= 4 && seguido >= 4) {
			return true;
		}
		return false;
	}

	// funcion que detecta si el tablero se ha llenado de fichas
	public boolean tablerolleno() {
		for (int i = 0; i < 6; i++) {
			for (int o = 0; o < 7; o++) {
				if (this.tablero[i][o] == -1) {
					return false;
				}
			}
		}
		this.inicializartablero();
		return true;
	}

}
