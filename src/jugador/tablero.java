package jugador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class tablero extends JFrame implements ActionListener, Runnable {
	// definimos todos aquellos elementos que nos ayudaran a crear la interfaz
	private JButton botones[][] = new JButton[6][7];
	private JMenuBar BarraTareas = new JMenuBar();
	private JMenu pestañaArchivo = new JMenu("Archivo");
	private JMenuItem OpcionSalir = new JMenuItem("Salir");
	private JLabel Ronda = new JLabel("", JLabel.CENTER);
	private ImageIcon foto1;
	private ImageIcon X;
	private ImageIcon O;
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
	// Definimos la interfaz en la que se mostrara el tablero
	private Interfaz i;

	// definimos el constructor de la clase
	public tablero(Interfaz in) {
//asignamos la interfaz en la que queremos mostrar el tablero
		this.i = in;

		// cargamos las diferentes imagenes
		foto1 = new ImageIcon("Captura.PNG");
		X = new ImageIcon("amarillo.png");
		O = new ImageIcon("rojo.png");
		ImageIcon img = new ImageIcon("imagen.png");
		// añadimos las correspondencias entre los elementos de la interfaz
		this.setIconImage(img.getImage());
		this.pestañaArchivo.addSeparator();
		this.OpcionSalir.addActionListener(this);
		this.pestañaArchivo.add(this.OpcionSalir);
		this.BarraTareas.add(this.pestañaArchivo);
		this.setJMenuBar(this.BarraTareas);

		// definimos el JPanel que contendra el tablero, es decir, los botones
		JPanel PanelPrincipal = new JPanel();
		PanelPrincipal.setLayout(new GridLayout(6, 7));
		// obtenemos los botones de la interfaz y se los asignamos a esta clase
		this.botones = this.i.getBotones();
		PanelPrincipal.setBackground(Color.BLUE);
		// para cada boton le ponemos una serie de propiedades:
		for (int i = 5; i > -1; i--) {
			for (int o = 0; o < 7; o++) {
				botones[i][o].setBorder(null);
				botones[i][o].setFocusable(false);
				botones[i][o].setFocusPainted(false);
				botones[i][o].addActionListener(this);
				botones[i][o].setIcon(foto1);
				botones[i][o].setContentAreaFilled(false);
				botones[i][o].setBackground(Color.BLUE);
				PanelPrincipal.add(this.botones[i][o]);
			}
			// definimos el color y otros campos que tendran la label que indicará la ronda
			// en la que nos encontramos.
			this.Ronda.setForeground(Color.BLACK);
			this.add(this.Ronda, "North");
			this.add(PanelPrincipal, "Center");

		}
		// definimos el comportamiento que debe tener, en caso de que el usuario pinche
		// sobre la opcion salir del menu. (cierra la aplicacion)
		this.OpcionSalir.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Cerrarjuego(evt);
			}

			private void Cerrarjuego(ActionEvent evt) {
				System.exit(0);

			}
		});

		// definimos otros campos del JFrame Interfaz.
		setLocation(170, 25);
		setSize(600, 600);
		setResizable(false);
		setTitle("Conecta 4 Online (SD)                      Unai Martínez Urraca");
		setVisible(true);

		try {
			// inicializamos el socket y incializamos los puentes de comunicacion
			this.s = new Socket(host, puerto);
			this.in = new DataInputStream(s.getInputStream());
			this.out = new DataOutputStream(s.getOutputStream());
			Thread hilo = new Thread(this);
			hilo.start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// funcion que se encarga de insertar la ficha dentro del tablero logico y que
	// muestra el icono en la interfaz simulando que se ha puesto la ficha.
	private void insertarficha(int columna, String marca) {
		//control del turno para que solo se pueda insertar una ficha durante la animación
		boolean noentrar = false;
		if (this.turno == true) {
			this.turno = false;
			noentrar = true;
		}
		int i = 0;
		boolean insertado = false;
		while (i < 6 && insertado == false) {

			if (this.tablero[i][columna] == "-") {
				if (marca.equals("1")) {
					this.tablero[i][columna] = "x";
					for (int o = 5; o > i; o--) {
						botones[o][columna].setIcon(X);
						try {
							Thread.sleep(75);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						botones[o][columna].setIcon(foto1);
					}
					botones[i][columna].setIcon(X);
				} else {
					this.tablero[i][columna] = "o";
					for (int o = 5; o > i; o--) {
						botones[o][columna].setIcon(O);
						try {
							Thread.sleep(75);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						botones[o][columna].setIcon(foto1);
					}
					botones[i][columna].setIcon(O);
				}
				insertado = true;

			}
			i++;
		}
		if (noentrar == false) {
			this.turno = true;
		}
	}

	// funcion que se encarga o bien de reiniciar el tablero o bien de
	// inicializarlo.
	// Pone el tablero logico con los simbolos "-" que indican que esta libre esa
	// casilla y en tablero de la interfaz quita todas las fichas que se hayan
	// podido poner.
	private void inicializartablero() {
		for (int i = 0; i < 6; i++) {
			for (int o = 0; o < 7; o++) {
				this.tablero[i][o] = "-";
			}
		}
		for (int i = 5; i > -1; i--) {
			for (int o = 0; o < 7; o++) {
				botones[i][o].setIcon(foto1);
			}
		}
	}

	// funcion que envia la columna al servidor en caso de que le jugador tenga el
	// turno. En caso de que no lo tenga, mostrara un mensaje por pantalla.
	public boolean enviarTurno(int c) {
		try {
			if (turno) {
				this.out.writeBytes(String.valueOf(c) + "\r\n");
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	// lo que se ejecuta dentro del hilo concurrentemente.
	public void run() {
		try {
			// inicializamos el tablero
			this.inicializartablero();
			// recibimos por partr del servidor
			// msgpartir[0] == contiene si somos X o O
			// msgpartir[1] == contiene si nuestro turno es true o false. Es decir, si nos
			// toca o no.
			String recibido = this.in.readLine();
			String[] msgpartir = recibido.split(":");
			int num = 1;
			this.Ronda.setText("Ronda " + num);
			this.turno = Boolean.valueOf(msgpartir[1]);

			while (true) {

				String datospartida = "";
				// mientras que el servidor no nos de los datos de la partida nos mantenemos
				// esperando a que lleguen esos datos.
				while (datospartida == "" || datospartida.equals("true") || datospartida.equals("false")) {
					datospartida = this.in.readLine();
				}
				// una vez insertada la ficha, el servidor nos devuelve los datos de la partida,
				// concretamente:
				// Datos[0] si es 1 nos indica que es X si es 0 nos indica que es O
				// Datos[1] nos indica la columna en la que el usuario ha insertado la ficha.
				// Datos[2] si contiene X es que ha ganado X. Si contiene O es que ha ganado O.
				// Si contiene empate es que la partida a finalizado y ha sido un empate Si
				// contiene nadie es que la partida aun no ha finalizado y se debe continuar
				String[] Datos = datospartida.split(":");

				// se inserta la ficha en el tablero y se muestra por la interfaz.
				this.insertarficha(Integer.parseInt(Datos[1]), Datos[0]);
				// mostramos los mensajes pertinentes por ventanas flotantes dentro de la
				// interfaz en el caso de que la partida
				// haya finalizado con un ganador y perdedor, o por el contrario con un empate.
				if (!Datos[2].equals("nadie")) {
					if (msgpartir[0].equals(Datos[2])) {
						int respuesta = JOptionPane.showConfirmDialog(this, "¡¡Felicidades!!, HAS GANADO LA PARTIDA",
								"¿Deseas volver a jugar?", JOptionPane.YES_NO_OPTION);
						if (respuesta == 0) {
							this.inicializartablero();
							num = 0;
						} else {
							System.exit(0);
						}
					} else if (Datos[2].equals("empate")) {
						int respuesta = JOptionPane.showConfirmDialog(this, "¡¡Has empatado la partida!!",
								"¿Deseas volver a jugar?", JOptionPane.YES_NO_OPTION);
						if (respuesta == 0) {
							this.inicializartablero();
							num = 0;
						} else {
							System.exit(0);
						}

					} else if (!(Datos[2].equals("nadie")) && (!msgpartir[0].equals(Datos[2]))) {
						int respuesta = JOptionPane.showConfirmDialog(this, "¡¡Has perdido la partida!!",
								"¿Deseas volver a jugar?", JOptionPane.YES_NO_OPTION);
						if (respuesta == 0) {
							this.inicializartablero();
							num = 0;
						} else {
							System.exit(0);
						}
					}
				}
				// sumamos uno a la ronda y cambiamos el texto de la jlabel para que se
				// actualize con la ronda en la que estamos.
				num++;
				Ronda.setText("Ronda " + num);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// no hace nada

	}
}