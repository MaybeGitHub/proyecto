package z_interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import baseDatosOracle.Chat;
import baseDatosOracle.GestorChat;
import baseDatosOracle.GestorHeroes;
import baseDatosOracle.GestorUsuarios;
import baseDatosOracle.Heroe;
import baseDatosOracle.Usuario;
import datos.Sala;
import mazmorra_Orcos.Mazmorra_Orcos;
import net.miginfocom.swing.MigLayout;

import javax.swing.border.LineBorder;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class User extends JPanel{

	private static final long serialVersionUID = 1L;
	protected ArrayList<Sala> mazmorraCargada = new Mazmorra_Orcos().getMazmorra();
	private Icon newHeroIcon = new ImageIcon(User.class.getResource("/imagenes/varios/sin heroe.png")); //$NON-NLS-1$
	private Icon deadHeroIcon = new ImageIcon(User.class.getResource("/imagenes/varios/muerto.png")); //$NON-NLS-1$
	private Icon winHeroIcon = new ImageIcon(User.class.getResource("/imagenes/varios/victoria.png")); //$NON-NLS-1$
	private JButton hero2Button, hero3Button, hero1Button, hero2EraseButton, hero1EraseButton, hero3EraseButton;
	private JLabel hero1NameLabel, hero2NameLabel, hero3NameLabel;
	private ArrayList<Heroe> listaHeroes;
	private GestorHeroes gh;
	private GestorUsuarios gu;
	private GestorChat gc;
	private Usuario usuario;
	private EntityManager em;
	private JButton salirButton;
	private JPanel centerPanel;
	private JFrame bodyFrame;

	public User( final EntityManager em, final Usuario usuario, JPanel centerPanel, JFrame bodyFrame ) {

		gu = new GestorUsuarios(em);
		gh = new GestorHeroes(em);
		gc = new GestorChat(em);

		this.em = em;
		this.usuario = usuario;
		this.centerPanel = centerPanel;
		this.bodyFrame = bodyFrame;
		heroesDelUsuario();

		setSize(new Dimension(450, 240));		
		setBackground(new Color(70, 130, 180));
		setLayout(new MigLayout("", "[450px]", "[230.00px]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		JPanel userPanel = new JPanel();
		userPanel.setPreferredSize(new Dimension(450, 200));
		add(userPanel, "cell 0 0,grow"); //$NON-NLS-1$

		hero1Button = new JButton(""); //$NON-NLS-1$
		hero1Button.setToolTipText(Messages.getString("User.hero1Button.toolTipText")); //$NON-NLS-1$
		hero1Button.setMaximumSize(new Dimension(89, 107));
		hero1Button.setMinimumSize(new Dimension(89, 107));
		hero1Button.setPreferredSize(new Dimension(89, 117));
		hero1Button.setVisible(false);
		userPanel.setLayout(new MigLayout("", "[150px,center][150px,center][150px,center]", "[20px,center][107px,center][35px,center][10][30px]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		userPanel.add(hero1Button, "cell 0 1"); //$NON-NLS-1$

		hero2Button = new JButton(""); //$NON-NLS-1$
		hero2Button.setToolTipText(Messages.getString("User.hero2Button.toolTipText")); //$NON-NLS-1$
		hero2Button.setMaximumSize(new Dimension(89, 107));
		hero2Button.setVisible(false);
		userPanel.add(hero2Button, "cell 1 1"); //$NON-NLS-1$

		hero3Button = new JButton(""); //$NON-NLS-1$
		hero3Button.setToolTipText(Messages.getString("User.hero3Button.toolTipText")); //$NON-NLS-1$
		hero3Button.setMaximumSize(new Dimension(89, 107));
		hero3Button.setVisible(false);
		userPanel.add(hero3Button, "cell 2 1");		 //$NON-NLS-1$

		hero1Button.setIcon(newHeroIcon);
		hero2Button.setIcon(newHeroIcon);
		hero3Button.setIcon(newHeroIcon);

		hero2EraseButton = new JButton(Messages.getString("User.Erase1"));		 //$NON-NLS-1$
		hero2EraseButton.setMaximumSize(new Dimension(60, 25));
		hero2EraseButton.setPreferredSize(new Dimension(60, 25));
		hero2EraseButton.setVisible(false);
		userPanel.add(hero2EraseButton, "cell 1 2"); //$NON-NLS-1$

		hero1EraseButton = new JButton(Messages.getString("User.Erase2"));
		hero1EraseButton.setPreferredSize(new Dimension(60, 25));
		hero1EraseButton.setMaximumSize(new Dimension(70, 30));
		hero1EraseButton.setVisible(false);
		userPanel.add(hero1EraseButton, "cell 0 2"); //$NON-NLS-1$

		hero3EraseButton = new JButton(Messages.getString("User.Erase3")); //$NON-NLS-1$
		hero3EraseButton.setMaximumSize(new Dimension(60, 25));
		hero3EraseButton.setPreferredSize(new Dimension(60, 25));
		hero3EraseButton.setVisible(false);
		userPanel.add(hero3EraseButton, "cell 2 2"); //$NON-NLS-1$

		hero1NameLabel = new JLabel(""); //$NON-NLS-1$
		hero1NameLabel.setVisible(false);
		hero1NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userPanel.add(hero1NameLabel, "cell 0 0,alignx center,aligny center"); //$NON-NLS-1$

		hero2NameLabel = new JLabel(""); //$NON-NLS-1$
		hero2NameLabel.setVisible(false);
		hero2NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userPanel.add(hero2NameLabel, "cell 1 0,alignx center,growy"); //$NON-NLS-1$

		hero3NameLabel = new JLabel(""); //$NON-NLS-1$
		hero3NameLabel.setVisible(false);
		hero3NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userPanel.add(hero3NameLabel, "cell 2 0,grow"); //$NON-NLS-1$

		salirButton = new JButton(Messages.getString("User.End_Session"));
		salirButton.setMaximumSize(new Dimension(150, 23));
		salirButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				cerrarSesion();
				cargarLogin();
			}
		});
		userPanel.add(salirButton, "cell 1 4,grow"); //$NON-NLS-1$

		userPanelUpdate();

		hero1EraseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				borrarHeroe(0);
			}
		});

		hero2EraseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarHeroe(1);
			}
		});

		hero3EraseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarHeroe(2);
			}
		});	

		hero1Button.addMouseListener( new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if ( listaHeroes.size() > 0 ){
					empezarJuego(0);
				}else{
					nuevoHeroe();
				}
			}
		});

		hero2Button.addMouseListener( new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if ( listaHeroes.size() > 1 ){
					empezarJuego(1);
				}else if (  listaHeroes.size() == 1 ){
					nuevoHeroe();
				}
			}
		});

		hero3Button.addMouseListener( new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if ( listaHeroes.size() > 2 ){
					empezarJuego(2);
				}else if ( listaHeroes.size() == 2  ){
					nuevoHeroe();
				}
			}
		});

		inicializoHotKeys();
		inicializoListenerFocus();
	}

	private void inicializoListenerFocus() {
		
		hero1Button.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				reinicioBordes();
				hero1Button.setBorder(new LineBorder(new Color(60, 179, 113), 2));
			}
		});
		
		hero2Button.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				reinicioBordes();
				hero2Button.setBorder(new LineBorder(new Color(60, 179, 113), 2));
			}
		});
		
		hero3Button.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				reinicioBordes();
				hero3Button.setBorder(new LineBorder(new Color(60, 179, 113), 2));
			}
		});
		
		hero1EraseButton.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				reinicioBordes();
				hero1EraseButton.setBorder(new LineBorder(new Color(60, 179, 113), 2));
			}
		});
		
		hero2EraseButton.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				reinicioBordes();
				hero2EraseButton.setBorder(new LineBorder(new Color(60, 179, 113), 2));
			}
		});
		
		hero3EraseButton.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				reinicioBordes();
				hero3EraseButton.setBorder(new LineBorder(new Color(60, 179, 113), 2));
			}
		});
		
		salirButton.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				reinicioBordes();
				salirButton.setBorder(new LineBorder(new Color(60, 179, 113), 2));
			}
		});
	}

	private void inicializoHotKeys() {

		hero1Button.addKeyListener(new KeyAdapter() { 
			@Override
			public void keyPressed( KeyEvent e) {
				if ( hero1EraseButton.isVisible() ){
					if ( e.getKeyCode() == KeyEvent.VK_DOWN ){
						hero1EraseButton.grabFocus();
					}else if ( e.getKeyCode() == KeyEvent.VK_RIGHT && hero2Button.isVisible() ){
						hero2Button.grabFocus();
					}else if ( e.getKeyCode() == KeyEvent.VK_ENTER ){
						empezarJuego(0);
					}
				}else if ( e.getKeyCode() == KeyEvent.VK_ENTER ){
					nuevoHeroe();
				}else if ( e.getKeyCode() == KeyEvent.VK_DOWN ){
					salirButton.grabFocus();
				}
			}
		});	

		hero2Button.addKeyListener(new KeyAdapter() { 
			@Override
			public void keyPressed( KeyEvent e) {
				if ( hero2EraseButton.isVisible() ){
					if ( e.getKeyCode() == KeyEvent.VK_DOWN ){
						hero2EraseButton.grabFocus();
					}else if ( e.getKeyCode() == KeyEvent.VK_LEFT ){
						hero1Button.grabFocus();
					}else if ( e.getKeyCode() == KeyEvent.VK_RIGHT && hero3Button.isVisible() ){
						hero3Button.grabFocus();
					}else if ( e.getKeyCode() == KeyEvent.VK_ENTER ){
						empezarJuego(1);
					}
				}else if ( e.getKeyCode() == KeyEvent.VK_ENTER ){
					nuevoHeroe();
				}else if ( e.getKeyCode() == KeyEvent.VK_DOWN ){
					salirButton.grabFocus();
				}else if ( e.getKeyCode() == KeyEvent.VK_LEFT ){
					hero1Button.grabFocus();
				}
			}
		});

		hero3Button.addKeyListener(new KeyAdapter() { 
			@Override
			public void keyPressed( KeyEvent e) {
				if ( hero3EraseButton.isVisible() ){
					if ( e.getKeyCode() == KeyEvent.VK_DOWN ){
						hero3EraseButton.grabFocus();
					}else if ( e.getKeyCode() == KeyEvent.VK_LEFT ){
						hero2Button.grabFocus();
					}else if ( e.getKeyCode() == KeyEvent.VK_ENTER ){
						empezarJuego(2);
					}
				}else if ( e.getKeyCode() == KeyEvent.VK_ENTER ){
					nuevoHeroe();
				}else if ( e.getKeyCode() == KeyEvent.VK_DOWN ){
					salirButton.grabFocus();
				}else if ( e.getKeyCode() == KeyEvent.VK_LEFT ){
					hero2Button.grabFocus();
				}
			}
		});

		hero1EraseButton.addKeyListener(new KeyAdapter() { 
			@Override
			public void keyPressed( KeyEvent e) {
				if ( e.getKeyCode() == KeyEvent.VK_DOWN ){
					salirButton.grabFocus();
				}else if ( e.getKeyCode() == KeyEvent.VK_RIGHT && hero2EraseButton.isVisible() ){
					hero2EraseButton.grabFocus();
				}else if ( e.getKeyCode() == KeyEvent.VK_UP ){
					hero1Button.grabFocus();
				}else if ( e.getKeyCode() == KeyEvent.VK_ENTER ){
					borrarHeroe(0);
					hero1Button.grabFocus();
				}
			}
		});			

		hero2EraseButton.addKeyListener(new KeyAdapter() { 
			@Override
			public void keyPressed( KeyEvent e) {
				if ( e.getKeyCode() == KeyEvent.VK_DOWN ){
					salirButton.grabFocus();
				}else if ( e.getKeyCode() == KeyEvent.VK_LEFT ){
					hero1EraseButton.grabFocus();
				}else if ( e.getKeyCode() == KeyEvent.VK_UP ){
					hero2Button.grabFocus();
				}else if ( e.getKeyCode() == KeyEvent.VK_RIGHT && hero3EraseButton.isVisible()){
					hero3EraseButton.grabFocus();
				}else if ( e.getKeyCode() == KeyEvent.VK_ENTER ){
					borrarHeroe(1);
					hero2Button.grabFocus();
				}
			}
		});			

		hero3EraseButton.addKeyListener(new KeyAdapter() { 
			@Override
			public void keyPressed( KeyEvent e) {
				if ( e.getKeyCode() == KeyEvent.VK_DOWN ){
					salirButton.grabFocus();
				}else if ( e.getKeyCode() == KeyEvent.VK_LEFT ){
					hero2EraseButton.grabFocus();
				}else if ( e.getKeyCode() == KeyEvent.VK_UP ){
					hero3Button.grabFocus();
				}else if ( e.getKeyCode() == KeyEvent.VK_ENTER ){
					borrarHeroe(2);
					hero3Button.grabFocus();
				}
			}
		});

		salirButton.addKeyListener(new KeyAdapter() { 
			@Override
			public void keyPressed( KeyEvent e) {
				if ( e.getKeyCode() == KeyEvent.VK_UP && hero2EraseButton.isVisible() ){
					hero2EraseButton.grabFocus();
				}else if ( e.getKeyCode() == KeyEvent.VK_UP && !hero1EraseButton.isVisible() ){
					hero1Button.grabFocus();					
				}else if ( e.getKeyCode() == KeyEvent.VK_UP && !hero2EraseButton.isVisible() ){
					hero2Button.grabFocus();
				}else if ( e.getKeyCode() == KeyEvent.VK_ENTER ){
					cargarLogin();
				}
			}
		});
	}

	public JButton getHero1Button() {
		return hero1Button;
	}

	private void userPanelUpdate() {

		switch( listaHeroes.size() ){
		case 0:
			hideAll();			
			break;
		case 1:
			showHero1();
			hero2Button.setVisible(true);
			hero2Button.setIcon(newHeroIcon);
			break;
		case 2: 
			showHero1();
			showHero2();
			hero3Button.setVisible(true);
			hero3Button.setIcon(newHeroIcon);
			break;
		case 3: 
			showHero1();
			showHero2();
			showHero3();
			break;
		}
	}

	private void hideAll() {
		hero1NameLabel.setVisible(false);
		hero1Button.setVisible(true);
		hero1EraseButton.setVisible(false);
		hero1Button.setIcon(newHeroIcon); 
		hero2NameLabel.setVisible(false);
		hero2Button.setVisible(false);
		hero2EraseButton.setVisible(false);
		hero3NameLabel.setVisible(false);
		hero3Button.setVisible(false);
		hero3EraseButton.setVisible(false);
	}

	private void showHero3() {
		hero3EraseButton.setVisible(true);
		hero3NameLabel.setVisible(true);
		hero3NameLabel.setText(listaHeroes.get(2).getNombre());			
		paintButton(hero3Button, 2);
		hero3Button.setVisible(true);
	}

	private void showHero2() {
		hero2EraseButton.setVisible(true);
		hero2NameLabel.setVisible(true);
		hero2NameLabel.setText(listaHeroes.get(1).getNombre());
		hero2Button.setVisible(true);
		paintButton(hero2Button, 1);
		hero3NameLabel.setVisible(false);
		hero3EraseButton.setVisible(false);
	}

	private void showHero1() {
		hero1EraseButton.setVisible(true);
		hero1NameLabel.setVisible(true);
		hero1NameLabel.setText(listaHeroes.get(0).getNombre());
		hero1Button.setVisible(true);
		paintButton(hero1Button, 0);
		hero2NameLabel.setVisible(false);
		hero2EraseButton.setVisible(false);
		hero3Button.setVisible(false);
	}

	private void paintButton(JButton heroButton, int posicion) {
		Heroe heroe = listaHeroes.get(posicion);
		if ( heroe.getPV() > 0 ){
			if ( heroe.getSala() < 10 ){
				heroButton.setIcon(new ImageIcon(heroe.getImagen()));
			}else{
				heroButton.setIcon(winHeroIcon);
			}
		}else{
			heroButton.setIcon(deadHeroIcon);
		}
	}

	private void heroesDelUsuario() {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		listaHeroes = (ArrayList<Heroe>) gh.findByUsuario(usuario);
		tx.commit();
	}

	private void empezarJuego(int posicion) {
		Heroe heroe = listaHeroes.get(posicion);
		actualizaUsuario();
		if ( heroe.getPV() != 0 && heroe.getSala() < 10 ){
			bodyFrame.setVisible(false);
			new Game(em, heroe, mazmorraCargada, usuario, centerPanel, bodyFrame);
		}
	}

	private void nuevoHeroe() {
		centerPanel.removeAll();
		NewHero newHero = new NewHero(em, usuario, centerPanel, bodyFrame);
		centerPanel.add(newHero, BorderLayout.CENTER);
		centerPanel.revalidate();
		centerPanel.repaint();
		newHero.getNameField().grabFocus();
	}

	private void cargarLogin() {
		centerPanel.removeAll();
		Login login = new Login(em, centerPanel, bodyFrame);
		centerPanel.add(login, BorderLayout.CENTER);
		centerPanel.revalidate();
		centerPanel.repaint();
		login.getUsernameField().grabFocus();
	}

	private void borrarHeroe(int posicion) {
		Heroe heroe = listaHeroes.get(posicion);
		EntityTransaction et = em.getTransaction();
		et.begin();
		gh.delete(heroe);
		et.commit();
		listaHeroes.remove(listaHeroes.get(posicion));
		userPanelUpdate();
	}

	private void actualizaUsuario() {
		usuario.setJugando(true);
		EntityTransaction et = em.getTransaction();
		et.begin();
		gu.update(usuario);
		et.commit();
		mensajeBienvenida();
	}

	private void mensajeBienvenida() {
		EntityTransaction et = em.getTransaction();
		et.begin();
		ArrayList<Usuario> usuariosJugando = (ArrayList<Usuario>) gu.findByJugando(true);
		Date date = new Date();
		Chat chat;		
		for ( Usuario jugador : usuariosJugando ){
			chat = new Chat(jugador, "** " + usuario.getNombre() + Messages.getString("User.UserLogIn"), date, false); //$NON-NLS-1$ //$NON-NLS-2$
			
			gc.insert(chat);
			
		}
		et.commit();
	}

	private void cerrarSesion() {
		EntityTransaction et = em.getTransaction();
		usuario.setConectado(false);
		et.begin();
		gu.update(usuario);
		et.commit();
	}
	
	private void reinicioBordes() {
		hero1Button.setBorder(null);
		hero2Button.setBorder(null);
		hero3Button.setBorder(null);
		hero1EraseButton.setBorder(null);
		hero2EraseButton.setBorder(null);
		hero3EraseButton.setBorder(null);
		salirButton.setBorder(null);
	}
}
