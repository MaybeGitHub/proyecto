package z_interfaz;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

public class CambioBoss extends JDialog{

	private static final long serialVersionUID = 1L;

	public CambioBoss(ArrayList<String> textoBoss, String textoHeroe ) {
		
		JPanel mainPanel = new JPanel();
		mainPanel.setMinimumSize(new Dimension(1280, 125));
		mainPanel.setSize(new Dimension(1280, 125));
		mainPanel.setPreferredSize(new Dimension(1280, 125));
		setContentPane(mainPanel);
		setTitle("Dungeon Master"); //$NON-NLS-1$
		setIconImage(Toolkit.getDefaultToolkit().getImage(CambioBoss.class.getResource("/imagenes/varios/mazmorraOrca.jpg"))); //$NON-NLS-1$
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(292, 119, 1280, 150);
		setVisible(true);
		DefaultListModel<String> modeloLista = new DefaultListModel<String>();
		for ( String s : textoBoss ){
			modeloLista.addElement(s);
		}
		
		JButton salirButton = new JButton();
		salirButton.setText(textoHeroe);
		salirButton.setSize(new Dimension(1280, 23));
		salirButton.setBounds(new Rectangle(0, 99, 1280, 23));
		salirButton.setMaximumSize(new Dimension(1280, 23));
		salirButton.setMinimumSize(new Dimension(1280, 23));
		salirButton.setPreferredSize(new Dimension(1280, 23));
		salirButton.setText(textoHeroe);
		salirButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				dispose();
			}
		});
		mainPanel.setLayout(null);
		
		JList<String> textoList = new JList<String>();
		textoList.setBounds(new Rectangle(0, 0, 1280, 100));
		textoList.setSize(new Dimension(1280, 100));
		textoList.setMaximumSize(new Dimension(1280, 100));
		textoList.setMinimumSize(new Dimension(1280, 100));
		textoList.setPreferredSize(new Dimension(1280, 149));
		textoList.setFont(new Font("Monospaced", Font.BOLD, 12));
		textoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		textoList.setBackground(new Color(255, 250, 205));
		textoList.setModel(modeloLista);
		getContentPane().add(textoList);
		getContentPane().add(salirButton);
	}
}
