package z_interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import baseDatosOracle.GestorHeroes;
import baseDatosOracle.Heroe;
import baseDatosOracle.Usuario;
import armaduras.Armadura_Cuero;
import armas.Espada_Corta;
import escudos.Sin_Escudo;
import net.miginfocom.swing.MigLayout;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class NewHero extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel nameLabel;
	private JTextField nameField;
	private Usuario usuario;
	private JSlider strSlider, dexSlider, intSlider;
	private JComboBox<String> imageCombo;
	private GestorHeroes gh;
	private JLabel imagenPreviewLabel;
	private String selectedImage;
	private JTextField strField;
	private JTextField dexField;
	private JTextField intField;
	private JLabel statsLabel;
	private int puntosTotales = 8;
	private EntityManager em;
	private JPanel centerPanel; 
	private JFrame bodyFrame;
	private JButton cancelButton;
	private JButton createButton;

	public NewHero( EntityManager em, Usuario user, JPanel centerPanel, JFrame bodyFrame ) {

		gh = new GestorHeroes(em);
		this.em = em;
		this.usuario = user;
		this.centerPanel = centerPanel;
		this.bodyFrame = bodyFrame;

		setSize(new Dimension(470, 310));		
		setBackground(new Color(70, 130, 180));
		setLayout(new MigLayout("", "[470px]", "[310px]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		JPanel newHeroPanel = new JPanel();
		newHeroPanel.setPreferredSize(new Dimension(470, 310));
		add(newHeroPanel, "cell 0 0,alignx left,growy"); //$NON-NLS-1$
		newHeroPanel.setLayout(new MigLayout("", "[105.00px][39.00px][35.00px][73.00px][103.00px][46.00px][45px]", "[39px][21px][19px][19px][22px][19px][][27px][58.00px]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		String[] images = new String[]{"avatar 1", "avatar 2", "avatar 3", "avatar 4", "avatar 5", "avatar 6"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
		DefaultComboBoxModel<String> imageComboModel = new DefaultComboBoxModel<String>(images);

		nameLabel = new JLabel(Messages.getString("NewHero.Name")); //$NON-NLS-1$
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		newHeroPanel.add(nameLabel, "cell 1 1,growx,aligny center"); //$NON-NLS-1$

		nameField = new JTextField();
		nameField.setToolTipText(Messages.getString("NewHero.nameField.toolTipText")); //$NON-NLS-1$
		nameField.setColumns(10);
		newHeroPanel.add(nameField, "cell 2 1 4 1,growx,aligny center"); //$NON-NLS-1$

		imagenPreviewLabel = new JLabel();
		imagenPreviewLabel.setToolTipText(Messages.getString("NewHero.imagenPreviewLabel.toolTipText")); //$NON-NLS-1$
		imagenPreviewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		try {
			BufferedImage image = ImageIO.read(Body.class.getResource("/imagenes/heroes/avatar 1.png")); //$NON-NLS-1$ //$NON-NLS-2$
			imagenPreviewLabel.setIcon( new ImageIcon(image) );
		} catch (IOException exc) {
			exc.printStackTrace();
		}
		newHeroPanel.add(imagenPreviewLabel, "cell 0 0 1 5,grow"); //$NON-NLS-1$

		JLabel iconLabel = new JLabel(Messages.getString("NewHero.Icon")); //$NON-NLS-1$
		iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
		newHeroPanel.add(iconLabel, "cell 1 3,grow"); //$NON-NLS-1$
		imageCombo = new JComboBox<String>(imageComboModel);
		imageCombo.setToolTipText(Messages.getString("NewHero.imageCombo.toolTipText")); //$NON-NLS-1$
		imageCombo.setSelectedIndex(0);
		selectedImage = (String) imageCombo.getSelectedItem();
		newHeroPanel.add(imageCombo, "cell 2 3 4 1,grow");		 //$NON-NLS-1$

		imageCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedImage = (String) imageCombo.getSelectedItem();				
				try {
					BufferedImage image = ImageIO.read(Body.class.getResource("/imagenes/heroes/" + selectedImage + ".png")); //$NON-NLS-1$ //$NON-NLS-2$
					imagenPreviewLabel.setIcon( new ImageIcon(image) );
				} catch (IOException exc) {
					exc.printStackTrace();
				}
			}
		});

		JLabel strLabel = new JLabel(Messages.getString("NewHero.Str")); //$NON-NLS-1$
		strLabel.setToolTipText(Messages.getString("NewHero.strLabel.toolTipText")); //$NON-NLS-1$
		strLabel.setHorizontalAlignment(SwingConstants.CENTER);
		newHeroPanel.add(strLabel, "cell 1 5,growx,aligny center"); //$NON-NLS-1$
		strSlider = new JSlider();
		strSlider.setToolTipText(Messages.getString("NewHero.strSlider.toolTipText")); //$NON-NLS-1$
		strSlider.setMinorTickSpacing(1);
		strSlider.setPaintTicks(true);

		strSlider.setValue(2);
		strSlider.setMinimum(2);
		strSlider.setMaximum(4);
		newHeroPanel.add(strSlider, "cell 2 5 4 1,alignx center,growy"); //$NON-NLS-1$

		strSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				limpiarBordes();			
				strField.setText(String.valueOf(strSlider.getValue()));
				modificoStatslabel(strSlider.getValue() + dexSlider.getValue() + intSlider.getValue());
				if ( strSlider.getValue() + dexSlider.getValue() + intSlider.getValue() > puntosTotales ){
					if( intSlider.getValue() != 2 ){
						intSlider.setValue(intSlider.getValue()-1);
					}else{
						dexSlider.setValue(dexSlider.getValue()-1);
					}					
				}
			}
		});

		strField = new JTextField();
		strField.setFocusable(false);
		strField.setBorder(new LineBorder(new Color(171, 173, 179)));
		strField.setEditable(false);
		strField.setHorizontalAlignment(SwingConstants.CENTER);
		newHeroPanel.add(strField, "cell 6 5,growx,aligny center");		 //$NON-NLS-1$
		strField.setText(String.valueOf(strSlider.getValue()));

		statsLabel = new JLabel();
		modificoStatslabel(6);
		statsLabel.setEnabled(false);
		statsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		newHeroPanel.add(statsLabel, "cell 0 6,alignx center,aligny center"); //$NON-NLS-1$

		JLabel dexLabel = new JLabel(Messages.getString("NewHero.Dex")); //$NON-NLS-1$
		dexLabel.setToolTipText(Messages.getString("NewHero.dexLabel.toolTipText")); //$NON-NLS-1$
		dexLabel.setHorizontalAlignment(SwingConstants.CENTER);
		newHeroPanel.add(dexLabel, "cell 1 6,growx,aligny center"); //$NON-NLS-1$

		dexSlider = new JSlider();
		dexSlider.setToolTipText(Messages.getString("NewHero.dexSlider.toolTipText")); //$NON-NLS-1$
		dexSlider.setMinorTickSpacing(1);
		dexSlider.setPaintTicks(true);
		dexSlider.setMaximum(4);
		dexSlider.setMinimum(2);
		dexSlider.setValue(2);
		newHeroPanel.add(dexSlider, "cell 2 6 4 1,alignx center,growy"); //$NON-NLS-1$

		dexSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {				
				limpiarBordes();
				dexField.setText(String.valueOf(dexSlider.getValue()));
				modificoStatslabel(strSlider.getValue() + dexSlider.getValue() + intSlider.getValue());
				if ( strSlider.getValue() + dexSlider.getValue() + intSlider.getValue() > puntosTotales ){
					if( intSlider.getValue() != 2 ){
						intSlider.setValue(intSlider.getValue()-1);
					}else{
						strSlider.setValue(strSlider.getValue()-1);
					}
				}
			}
		});

		dexField = new JTextField();
		dexField.setFocusable(false);
		dexField.setBorder(new LineBorder(new Color(171, 173, 179)));
		dexField.setText("2"); //$NON-NLS-1$
		dexField.setEditable(false);
		dexField.setHorizontalAlignment(SwingConstants.CENTER);
		newHeroPanel.add(dexField, "cell 6 6,growx,aligny center");	 //$NON-NLS-1$

		JLabel intLabel = new JLabel("Int"); //$NON-NLS-1$
		intLabel.setToolTipText(Messages.getString("NewHero.intLabel.toolTipText")); //$NON-NLS-1$
		intLabel.setHorizontalAlignment(SwingConstants.CENTER);
		newHeroPanel.add(intLabel, "cell 1 7,growx,aligny center"); //$NON-NLS-1$

		intSlider = new JSlider();
		intSlider.setToolTipText(Messages.getString("NewHero.intSlider.toolTipText")); //$NON-NLS-1$
		intSlider.setMinorTickSpacing(1);
		intSlider.setPaintTicks(true);
		intSlider.setMaximum(4);
		intSlider.setMinimum(2);
		intSlider.setValue(2);
		newHeroPanel.add(intSlider, "cell 2 7 4 1,alignx center,growy"); //$NON-NLS-1$

		intField = new JTextField();
		intField.setFocusable(false);
		intField.setBorder(new LineBorder(new Color(171, 173, 179)));
		intField.setText("2"); //$NON-NLS-1$
		intField.setEditable(false);
		intField.setHorizontalAlignment(SwingConstants.CENTER);
		newHeroPanel.add(intField, "cell 6 7,growx,aligny center"); //$NON-NLS-1$

		intSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				limpiarBordes();	
				intField.setText(String.valueOf(intSlider.getValue()));
				modificoStatslabel(strSlider.getValue() + dexSlider.getValue() + intSlider.getValue());
				if ( strSlider.getValue() + dexSlider.getValue() + intSlider.getValue() > puntosTotales ){
					if( dexSlider.getValue() != 2 ){
						dexSlider.setValue(dexSlider.getValue()-1);
					}else{
						strSlider.setValue(strSlider.getValue()-1);
					}
				}
			}
		});

		createButton = new JButton(Messages.getString("NewHero.Create")); //$NON-NLS-1$
		createButton.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if ( e.getKeyCode() == KeyEvent.VK_ENTER ){
					Heroe heroe = crearHeroe();
					if ( !existeHeroeConMismoNombre(usuario, heroe) && !nameField.getText().equals("") ){ //$NON-NLS-1$
						if ( strSlider.getValue() + dexSlider.getValue() + intSlider.getValue() == puntosTotales ){
							cargarUser(heroe, true);
						}else{
							limpiarBordes();
							errorEstadisticas();
						}
					}else{
						limpiarBordes();
						errorNombre();
					}
				}
			}
		});

		createButton.setPreferredSize(Body.dimensionBotones);
		createButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Heroe heroe = crearHeroe();
				if ( !existeHeroeConMismoNombre(usuario, heroe) && !nameField.getText().equals("") ){ //$NON-NLS-1$
					if ( strSlider.getValue() + dexSlider.getValue() + intSlider.getValue() == puntosTotales ){
						cargarUser(heroe, true);
					}else{
						limpiarBordes();
						errorEstadisticas();
					}
				}else{
					limpiarBordes();
					errorNombre();
				}
			}
		});
		newHeroPanel.add(createButton, "cell 4 8 3 1,alignx left,aligny center"); //$NON-NLS-1$

		cancelButton = new JButton(Messages.getString("NewHero.Cancel")); //$NON-NLS-1$
		cancelButton.setPreferredSize(Body.dimensionBotones);
		cancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				cargarUser(null, false);
			}
		});

		cancelButton.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if ( e.getKeyCode() == KeyEvent.VK_ENTER ){
					cargarUser(null, false);
				}
			}
		});
		newHeroPanel.add(cancelButton, "cell 0 8 3 1,alignx right,aligny center");	 //$NON-NLS-1$
		nameField.grabFocus();
	}

	private void errorNombre() {
		JOptionPane.showMessageDialog(this, Messages.getString("NewHero.NameMessage")); //$NON-NLS-1$
		nameLabel.setBorder(new LineBorder(new Color(255, 0, 0)));
	}

	private void errorEstadisticas() {
		JOptionPane.showMessageDialog(this, Messages.getString("NewHero.StatsMessage")); //$NON-NLS-1$
		statsLabel.setBorder(new LineBorder(new Color(255, 0, 0)));
		strField.setBorder(new LineBorder(new Color(255, 0, 0)));
		dexField.setBorder(new LineBorder(new Color(255, 0, 0)));
		intField.setBorder(new LineBorder(new Color(255, 0, 0)));
	}

	public JTextField getNameField() {
		return nameField;
	}

	private void modificoStatslabel(int puntos) {
		statsLabel.setText(Messages.getString("NewHero.StatsLeft") + (puntosTotales - puntos));		 //$NON-NLS-1$
	}

	protected Heroe crearHeroe() {
		String nombre = nameField.getText();	
		byte[] icono = almacenoImagen();
		int nivel = 1;
		int fuerza = strSlider.getValue();
		int destreza = dexSlider.getValue();
		int inteligencia = intSlider.getValue();
		int PVMax = 50;
		int PV = PVMax;
		int exp = 0;
		int expMax = 10;
		int sala = 0;
		String arma = new Espada_Corta().getTipoArmaBBDD();
		String armadura = new Armadura_Cuero().getTipoArmaduraBBDD();
		String escudo = new Sin_Escudo().getTipoEscudoBBDD();

		Heroe heroe = new Heroe( nombre, usuario, icono, nivel, fuerza, destreza, inteligencia, PVMax, PV, exp, expMax, sala, arma, armadura, escudo );

		return heroe;
	}

	private byte[] almacenoImagen() {
		byte[] bytes = null;
		try {
			ImageIcon icon = (ImageIcon) imagenPreviewLabel.getIcon();
			BufferedImage image = (BufferedImage) icon.getImage();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(image,"png",out); //$NON-NLS-1$
			bytes = out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytes;
	}

	private boolean existeHeroeConMismoNombre(Usuario usuario, Heroe heroe) {
		boolean ret = false;
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		List<Heroe> listaHeroes = gh.findByUsuario(usuario);
		tx.commit();
		for(Heroe heroes : listaHeroes ){
			if ( heroes.getNombre().equals(heroe.getNombre()) ){
				ret = true;
				break;
			}
		}
		return ret;
	}

	private void limpiarBordes() {
		nameLabel.setBorder(null);
		statsLabel.setBorder(null);
		strField.setBorder(new LineBorder(new Color(171, 173, 179)));
		dexField.setBorder(new LineBorder(new Color(171, 173, 179)));
		intField.setBorder(new LineBorder(new Color(171, 173, 179)));
	}

	private void cargarUser(Heroe heroe, boolean nuevoHeroe) {
		if ( nuevoHeroe ) {
			EntityTransaction et = em.getTransaction();
			et.begin();
			gh.insert(heroe);
			et.commit();
		}
		centerPanel.removeAll();
		User user = new User(em, usuario, centerPanel, bodyFrame);
		centerPanel.add(user, BorderLayout.CENTER);
		centerPanel.revalidate();
		centerPanel.repaint();
		user.getHero1Button().grabFocus();
	}
}
