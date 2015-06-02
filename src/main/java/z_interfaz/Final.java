package z_interfaz;

import javax.swing.JDialog;

import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import baseDatosOracle.Heroe;
import datos.Enemigo;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Final extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField respuesta1Field;
	private JTextField respuesta2Field;
	private JTextField respuesta3Field;
	private JTextField respuesta4Field;
	private int intentos = 3;
	private static boolean derrotado = false;
	private static boolean fail = false;
	
	public Final(final ArrayList<Enemigo> enemigos, final Heroe heroe, Window owner) {
		super(owner);
		setTitle("Dungeon Master"); //$NON-NLS-1$
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Final.class.getResource("/imagenes/varios/mazmorraOrca.jpg"))); //$NON-NLS-1$
		setAlwaysOnTop(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(292, 119, 450, 300);
		getContentPane().setLayout(null);
		setVisible(true);
		
		JLabel pregunta1Label = new JLabel(Messages.getString("Final.pregunta1")); //$NON-NLS-1$
		pregunta1Label.setBounds(0, 5, 444, 29);
		pregunta1Label.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(pregunta1Label);
		
		respuesta1Field = new JTextField();
		respuesta1Field.setBounds(0, 35, 444, 23);
		getContentPane().add(respuesta1Field);
		respuesta1Field.setColumns(10);
		
		final JButton darGolpeFinalButton = new JButton(Messages.getString("Final.Final")); //$NON-NLS-1$
		darGolpeFinalButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				boolean respuesta1 = respuesta1Field.getText().toLowerCase().equals(Messages.getString("Final.respuesta1")); //$NON-NLS-1$
				boolean respuesta2 = respuesta2Field.getText().toLowerCase().equals(Messages.getString("Final.respuesta2")); //$NON-NLS-1$
				boolean respuesta3 = respuesta3Field.getText().toLowerCase().equals(Messages.getString("Final.respuesta3")); //$NON-NLS-1$
				boolean respuesta4 = respuesta4Field.getText().toLowerCase().equals(Messages.getString("Final.respuesta4")); //$NON-NLS-1$
				if ( respuesta1 && respuesta2 && respuesta3 && respuesta4 ){					
					derrotado = true;
					dispose();					
				}else {
					switch (intentos){
					case 3: darGolpeFinalButton.setText(Messages.getString("Final.Intento1")); intentos--; break; //$NON-NLS-1$
					case 2: darGolpeFinalButton.setText(Messages.getString("Final.Intento2")); intentos--; break; //$NON-NLS-1$
					case 1: darGolpeFinalButton.setText(Messages.getString("Final.Intento3")); intentos--; break; //$NON-NLS-1$
					case 0: heroe.setPV(0); fail = true; dispose(); break;
					}
				}
			}
		});
		darGolpeFinalButton.setBounds(0, 249, 444, 23);
		getContentPane().add(darGolpeFinalButton);
		
		JLabel pregunta2Label = new JLabel(Messages.getString("Final.pregunta2")); //$NON-NLS-1$
		pregunta2Label.setHorizontalAlignment(SwingConstants.CENTER);
		pregunta2Label.setBounds(0, 70, 444, 29);
		getContentPane().add(pregunta2Label);
		
		respuesta2Field = new JTextField();
		respuesta2Field.setColumns(10);
		respuesta2Field.setBounds(0, 103, 444, 23);
		getContentPane().add(respuesta2Field);
		
		JLabel pregunta3Label = new JLabel(Messages.getString("Final.pregunta3")); //$NON-NLS-1$
		pregunta3Label.setHorizontalAlignment(SwingConstants.CENTER);
		pregunta3Label.setBounds(0, 135, 444, 29);
		getContentPane().add(pregunta3Label);
		
		respuesta3Field = new JTextField();
		respuesta3Field.setColumns(10);
		respuesta3Field.setBounds(0, 168, 444, 23);
		getContentPane().add(respuesta3Field);
		
		respuesta4Field = new JTextField();
		respuesta4Field.setColumns(10);
		respuesta4Field.setBounds(0, 224, 444, 23);
		getContentPane().add(respuesta4Field);
		
		JLabel pregunta4Label = new JLabel(Messages.getString("Final.pregunta4")); //$NON-NLS-1$
		pregunta4Label.setHorizontalAlignment(SwingConstants.CENTER);
		pregunta4Label.setBounds(0, 198, 444, 23);
		getContentPane().add(pregunta4Label);
	}

	public static boolean isDerrotado() {
		return derrotado;
	}
	
	public static boolean isFail() {
		return fail;
	}
}
