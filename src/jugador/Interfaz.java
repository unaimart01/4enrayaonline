//UNAI MARTINEZ URRACA
package jugador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

//Esta Clase sirve para ejecutar el juego con la interfaz que he creado.
public class Interfaz extends JFrame {

	// definicion de todos los botones que necesitaremos para que el tablero
	// responda a nuestras acciones.
	private static tablero t;
	private JButton[][] botones = new JButton[6][7];
	private JButton B11;
	private JButton B12;
	private JButton B13;
	private JButton B14;
	private JButton B21;
	private JButton B22;
	private JButton B23;
	private JButton B31;
	private JButton B32;
	private JButton B33;
	private JButton B15;
	private JButton B16;
	private JButton B17;
	private JButton B24;
	private JButton B25;
	private JButton B26;
	private JButton B27;
	private JButton B34;
	private JButton B35;
	private JButton B36;
	private JButton B37;
	private JButton B41;
	private JButton B42;
	private JButton B43;
	private JButton B44;
	private JButton B45;
	private JButton B46;
	private JButton B47;
	private JButton B51;
	private JButton B52;
	private JButton B53;
	private JButton B54;
	private JButton B55;
	private JButton B56;
	private JButton B57;
	private JButton B61;
	private JButton B62;
	private JButton B63;
	private JButton B64;
	private JButton B65;
	private JButton B66;
	private JButton B67;

	// constructor de la clase interfaz
	public Interfaz() {

		initComponents();
		// asignamos a todos los botones su respectivo boton con una accion en concreto
		botones[0][0] = B11;
		botones[0][1] = B12;
		botones[0][2] = B13;
		botones[0][3] = B14;
		botones[0][4] = B15;
		botones[0][5] = B16;
		botones[0][6] = B17;
		botones[1][0] = B21;
		botones[1][1] = B22;
		botones[1][2] = B23;
		botones[1][3] = B24;
		botones[1][4] = B25;
		botones[1][5] = B26;
		botones[1][6] = B27;
		botones[2][0] = B31;
		botones[2][1] = B32;
		botones[2][2] = B33;
		botones[2][3] = B34;
		botones[2][4] = B35;
		botones[2][5] = B36;
		botones[2][6] = B37;
		botones[3][0] = B41;
		botones[3][1] = B42;
		botones[3][2] = B43;
		botones[3][3] = B44;
		botones[3][4] = B45;
		botones[3][5] = B46;
		botones[3][6] = B47;
		botones[4][0] = B51;
		botones[4][1] = B52;
		botones[4][2] = B53;
		botones[4][3] = B54;
		botones[4][4] = B55;
		botones[4][5] = B56;
		botones[4][6] = B57;
		botones[5][0] = B61;
		botones[5][1] = B62;
		botones[5][2] = B63;
		botones[5][3] = B64;
		botones[5][4] = B65;
		botones[5][5] = B66;
		botones[5][6] = B67;

		// llamamos al constructor de tablero y le pasamos este JFrame
		t = new tablero(this);

	}

	private void initComponents() {
//Esta accion se encarga de iniciar todos los botones y de generar una accion cada vez que uno de ellos es pulsado
		B14 = new javax.swing.JButton();
		B11 = new javax.swing.JButton();
		B13 = new javax.swing.JButton();
		B12 = new javax.swing.JButton();
		B21 = new javax.swing.JButton();
		B22 = new javax.swing.JButton();
		B23 = new javax.swing.JButton();
		B31 = new javax.swing.JButton();
		B32 = new javax.swing.JButton();
		B33 = new javax.swing.JButton();
		B15 = new javax.swing.JButton();
		B16 = new javax.swing.JButton();
		B17 = new javax.swing.JButton();
		B24 = new javax.swing.JButton();
		B25 = new javax.swing.JButton();
		B26 = new javax.swing.JButton();
		B27 = new javax.swing.JButton();
		B34 = new javax.swing.JButton();
		B35 = new javax.swing.JButton();
		B36 = new javax.swing.JButton();
		B37 = new javax.swing.JButton();
		B41 = new javax.swing.JButton();
		B42 = new javax.swing.JButton();
		B43 = new javax.swing.JButton();
		B44 = new javax.swing.JButton();
		B45 = new javax.swing.JButton();
		B46 = new javax.swing.JButton();
		B47 = new javax.swing.JButton();
		B51 = new javax.swing.JButton();
		B52 = new javax.swing.JButton();
		B53 = new javax.swing.JButton();
		B54 = new javax.swing.JButton();
		B55 = new javax.swing.JButton();
		B56 = new javax.swing.JButton();
		B57 = new javax.swing.JButton();
		B61 = new javax.swing.JButton();
		B62 = new javax.swing.JButton();
		B63 = new javax.swing.JButton();
		B64 = new javax.swing.JButton();
		B65 = new javax.swing.JButton();
		B66 = new javax.swing.JButton();
		B67 = new javax.swing.JButton();

		// aqui es donde asignamos a cada boton, la accion que tendrá que realizar.
		B11.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B11ActionPerformed(evt);
			}

		});

		B12.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B21ActionPerformed(evt);
			}

		});

		B13.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B31ActionPerformed(evt);
			}

		});

		B14.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B41ActionPerformed(evt);
			}

		});

		B15.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B51ActionPerformed(evt);
			}

		});

		B16.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B61ActionPerformed(evt);
			}

		});
		B17.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B71ActionPerformed(evt);
			}

		});

		B21.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B11ActionPerformed(evt);
			}

		});
		B22.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B21ActionPerformed(evt);
			}

		});
		B23.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B31ActionPerformed(evt);
			}

		});
		B24.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B41ActionPerformed(evt);
			}

		});
		B25.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B51ActionPerformed(evt);
			}

		});
		B26.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B61ActionPerformed(evt);
			}

		});
		B27.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B71ActionPerformed(evt);
			}

		});
		B31.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B11ActionPerformed(evt);
			}

		});
		B32.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B21ActionPerformed(evt);
			}

		});
		B33.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B31ActionPerformed(evt);
			}

		});
		B34.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B41ActionPerformed(evt);
			}

		});
		B35.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B51ActionPerformed(evt);
			}

		});
		B36.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B61ActionPerformed(evt);
			}

		});
		B37.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B71ActionPerformed(evt);
			}

		});
		B41.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B11ActionPerformed(evt);
			}

		});
		B42.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B21ActionPerformed(evt);
			}

		});
		B43.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B31ActionPerformed(evt);
			}

		});
		B44.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B41ActionPerformed(evt);
			}

		});
		B45.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B51ActionPerformed(evt);
			}

		});
		B46.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B61ActionPerformed(evt);
			}

		});
		B47.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B71ActionPerformed(evt);
			}

		});
		B51.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B11ActionPerformed(evt);
			}

		});
		B52.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B21ActionPerformed(evt);
			}

		});
		B53.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B31ActionPerformed(evt);
			}

		});
		B54.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B41ActionPerformed(evt);
			}

		});
		B55.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B51ActionPerformed(evt);
			}

		});
		B56.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B61ActionPerformed(evt);
			}

		});
		B57.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B71ActionPerformed(evt);
			}

		});
		B61.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B11ActionPerformed(evt);
			}
		});
		B62.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B21ActionPerformed(evt);
			}
		});
		B63.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B31ActionPerformed(evt);
			}
		});
		B64.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B41ActionPerformed(evt);
			}
		});
		B65.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B51ActionPerformed(evt);
			}
		});
		B66.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B61ActionPerformed(evt);
			}
		});
		B67.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				B71ActionPerformed(evt);
			}
		});

		pack();
	}

	// en el caso de que se pulse un boton, la accion a realizar es enviar al
	// servidor la columna donde queremos insertar la ficha en caso de que sea
	// nuestro turno. En caso de que no sea nuestro turno, el jframe nos avisa con
	// un cuadro de dialogo en el que nos avisa de que no es nuestro turno.
	private void B61ActionPerformed(java.awt.event.ActionEvent evt) {

		if (!this.enviarTurno(6)) {
			JOptionPane.showMessageDialog(this, "Espera tu turno");
		}
	}

	private void B11ActionPerformed(java.awt.event.ActionEvent evt) {

		if (!this.enviarTurno(1)) {
			JOptionPane.showMessageDialog(this, "Espera tu turno");
		}
	}

	private void B21ActionPerformed(java.awt.event.ActionEvent evt) {

		if (!this.enviarTurno(2)) {
			JOptionPane.showMessageDialog(this, "Espera tu turno");
		}
	}

	private void B31ActionPerformed(java.awt.event.ActionEvent evt) {

		if (!this.enviarTurno(3)) {
			JOptionPane.showMessageDialog(this, "Espera tu turno");
		}
	}

	private void B41ActionPerformed(java.awt.event.ActionEvent evt) {

		if (!this.enviarTurno(4)) {
			JOptionPane.showMessageDialog(this, "Espera tu turno");
		}
	}

	private void B51ActionPerformed(java.awt.event.ActionEvent evt) {

		if (!this.enviarTurno(5)) {
			JOptionPane.showMessageDialog(this, "Espera tu turno");
		}
	}

	private void B71ActionPerformed(java.awt.event.ActionEvent evt) {

		if (!this.enviarTurno(7)) {
			JOptionPane.showMessageDialog(this, "Espera tu turno");
		}
	}

//Sirve para llamar a la funcion enviar turno de la clase tablero
	public boolean enviarTurno(int c) {
		return t.enviarTurno(c);
	}

//sirve para obtener todos los botones que hemos definido previamente.
	public JButton[][] getBotones() {
		return botones;
	}

	// Ejecutable
	public static void main(String[] args) {
		new Interfaz();

	}

}
