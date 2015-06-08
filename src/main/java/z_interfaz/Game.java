package z_interfaz;

import habilidades.Curar;
import habilidades.Golpe_Concentrado;
import habilidades.Habilidad;
import habilidades.Llamarada;
import habilidades.Torbellino;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import personajes.Cacique_1;
import personajes.Cacique_2;
import personajes.Cacique_3;
import pociones.Pocion;
import pociones.Pocion_Curativa;
import pociones.Pocion_Explosiva;
import pociones.Pocion_Mana;
import datos.Combate;
import datos.Enemigo;
import datos.Sala;
import escudos.Escudo;

import javax.swing.JTextField;
import javax.swing.JScrollPane;

import armaduras.Armadura;
import armas.Arma;
import baseDatosOracle.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JSeparator;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.ByteArrayInputStream;

public class Game extends JFrame {

	private static final long serialVersionUID = 1L;
	private ArrayList<Enemigo> enemigos;
	private Heroe heroe;
	private Enemigo selectedEnemy;
	private Combate combat;
	private JLabel nameValueLabel, levelValueLabel, expValueLabel;
	private JLabel strValueLabel, dexValueLabel, intValueLabel, atkValueLabel, powerValueLabel;
	private JLabel defValueLabel, weaponIcon, armorIcon, shieldIcon, enemy1Icon, enemy2Icon, enemy3Icon, enemy4Icon, enemy5Icon, enemy6Icon;
	private JPanel enemy1Panel, enemy2Panel, enemy3Panel, enemy4Panel, enemy5Panel, enemy6Panel;
	private JLabel enemy1NameLabel, enemy2NameLabel, enemy3NameLabel, enemy4NameLabel, enemy5NameLabel, enemy6NameLabel, mapLabel;
	private boolean dead = false;
	private JList<Usuario> userList;	
	private JProgressBar enemy1SkillBar, enemy2SkillBar, enemy4SkillBar, enemy3SkillBar, enemy5SkillBar, enemy6SkillBar;
	private JProgressBar enemy1PVBar, enemy2PVBar, enemy3PVBar, enemy4PVBar, enemy5PVBar, enemy6PVBar;
	private JLabel damageValueLabel;
	private JProgressBar heroPVBar;
	private JLabel heroeIconLabel, levelLabel;
	private Sala salaActual;
	private JPanel AtkDefPanel, SkillPotionPanel;
	private Timer chatRefreshTimer;
	private JTextField chatTextField;
	private ArrayList<Usuario> usuariosJugando;
	private ArrayList<String> almacenChat, almacenEventos;
	private JList<String> chatList;
	private JButton sendButton;
	private JScrollPane userScrollPane;
	private GestorUsuarios gu;
	private GestorHeroes gh;
	private GestorChat gc;
	private JLabel usersLabel;
	private ArrayList<Sala> mazmorra;
	private JSeparator separator;
	private JButton atkButton, defenseButton, exitButton, nextRoomButton;
	private JPanel habilidadesPanel;
	private ArrayList<Pocion> pocionesAlmacenadas = new ArrayList<Pocion>();
	private ArrayList<Habilidad> habilidadesAlmacenadas = new ArrayList<Habilidad>();
	private JPanel pocionesPanel;
	private JLabel combatValueLabel;
	private Pocion pocion;
	private Habilidad habilidad;
	private boolean bossFinal = false;
	private EntityManager em;
	private JScrollPane eventosScrollPane;
	private JList<String> listaEventos;
	private JLabel torbellinoLabel, golpeConcentradoLabel, curarLabel, llamaradaLabel, pocionExplosivaLabel, pocionCurativaLabel, pocionManaLabel;
	private Arma arma;
	private Armadura armadura;
	private Escudo escudo;
	private ArrayList<JLabel> listaHabilidadesLabel = new ArrayList<JLabel>();
	private ArrayList<JLabel> listaPocionesLabel = new ArrayList<JLabel>();
	private final Usuario usuarioActual;
	private JFrame game;
	private JScrollPane chatScrollPane;
	private boolean juegoTerminado = false;

	public Game ( final EntityManager em, Heroe hero, ArrayList<Sala> roomList, Usuario usuario, final JPanel centerPanelBody, final JFrame bodyFrame ) {
		
		game = this;
		setFocusable(false);
		gu = new GestorUsuarios(em);
		gh = new GestorHeroes(em);
		gc = new GestorChat(em);

		this.em = em;
		setSize(new Dimension(1280, 720));
		setVisible(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Body.class.getResource("/imagenes/varios/mazmorraOrca.jpg"))); //$NON-NLS-1$
		setTitle("Dungeon Master"); //$NON-NLS-1$
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		heroe = hero;
		mazmorra = roomList;
		usuarioActual = usuario;
		salaActual = mazmorra.get(heroe.getSala());
		enemigos = salaActual.getEnemigos();
		combat = new Combate(heroe, salaActual );
		usuariosJugando = new ArrayList<Usuario>();
		almacenChat = new ArrayList<String>();
		almacenEventos = new ArrayList<String>();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				exitFromGame(centerPanelBody, bodyFrame);
			}
		});

		chatRefreshTimer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent action) {
				tickDelTimer();
				
				if ( Final.isDerrotado() && !juegoTerminado ){
					Final.setDerrotado(false);
					juegoTerminado = true;
					enemigos.clear();
					siguienteSala();
					endTurn();
					cleanEnemiesPanel();
					updateGamePanel();
				}
				
				if ( Final.isFail() && !juegoTerminado ){
					Final.setFail(false);
					juegoTerminado = true;
					heroe.setPV(0);
					dead = true;
					endTurn();
					cleanEnemiesPanel();
					updateGamePanel();
				}
			}
		});

		chatRefreshTimer.start();
		JPanel gamePanel = new JPanel();
		gamePanel.setFocusable(false);
		setContentPane(gamePanel);
		gamePanel.setSize(new Dimension(1280, 720));
		GridBagLayout gbl_gamePanel = new GridBagLayout();
		gbl_gamePanel.rowWeights = new double[]{0.0, 1.0, 0.0};
		gbl_gamePanel.columnWeights = new double[]{1.0, 1.0, 1.0};
		gbl_gamePanel.columnWidths = new int[]{292, 656, 300};
		gbl_gamePanel.rowHeights = new int[]{119, 440, 101};
		gamePanel.setLayout(gbl_gamePanel);

		JPanel northPanel = new JPanel();
		northPanel.setFocusable(false);
		northPanel.setBorder(new LineBorder(new Color(192, 192, 192)));
		northPanel.setPreferredSize(new Dimension(1280, 130));
		GridBagConstraints gbc_northPanel = new GridBagConstraints();
		gbc_northPanel.fill = GridBagConstraints.BOTH;
		gbc_northPanel.insets = new Insets(0, 0, 5, 0);
		gbc_northPanel.gridwidth = 3;
		gbc_northPanel.gridx = 0;
		gbc_northPanel.gridy = 0;
		gamePanel.add(northPanel, gbc_northPanel);
		GridBagLayout gbl_northPanel = new GridBagLayout();
		gbl_northPanel.columnWidths = new int[]{120, 43, 77, 88, 21, 69, 38, 28, 74, 54, 32, 61, 50, 26, 92, 55, 21, 66, 50, 45, 102, 79, 43, 121, 0};
		gbl_northPanel.rowHeights = new int[]{25, 25, 25, 25, 25, 0};
		gbl_northPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_northPanel.rowWeights = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		northPanel.setLayout(gbl_northPanel);

		JLabel dungeonIcon2 = new JLabel(""); //$NON-NLS-1$
		dungeonIcon2.setIcon(new ImageIcon(Body.class.getResource("/imagenes/varios/orc_Icon.png"))); //$NON-NLS-1$
		GridBagConstraints gbc_dungeonIcon2 = new GridBagConstraints();
		gbc_dungeonIcon2.insets = new Insets(0, 0, 0, 5);
		gbc_dungeonIcon2.gridheight = 5;
		gbc_dungeonIcon2.gridx = 0;
		gbc_dungeonIcon2.gridy = 0;
		northPanel.add(dungeonIcon2, gbc_dungeonIcon2);

		expValueLabel = new JLabel(""); //$NON-NLS-1$
		expValueLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_expValueLabel = new GridBagConstraints();
		gbc_expValueLabel.insets = new Insets(0, 0, 5, 5);
		gbc_expValueLabel.gridx = 9;
		gbc_expValueLabel.gridy = 1;
		northPanel.add(expValueLabel, gbc_expValueLabel);

		dexValueLabel = new JLabel(""); //$NON-NLS-1$
		dexValueLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_dexValueLabel = new GridBagConstraints();
		gbc_dexValueLabel.insets = new Insets(0, 0, 5, 5);
		gbc_dexValueLabel.gridx = 12;
		gbc_dexValueLabel.gridy = 1;
		northPanel.add(dexValueLabel, gbc_dexValueLabel);

		combatValueLabel = new JLabel(""); //$NON-NLS-1$
		GridBagConstraints gbc_combatValueLabel = new GridBagConstraints();
		gbc_combatValueLabel.insets = new Insets(0, 0, 5, 5);
		gbc_combatValueLabel.gridx = 15;
		gbc_combatValueLabel.gridy = 1;
		northPanel.add(combatValueLabel, gbc_combatValueLabel);

		atkValueLabel = new JLabel(""); //$NON-NLS-1$
		atkValueLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_atkValueLabel = new GridBagConstraints();
		gbc_atkValueLabel.insets = new Insets(0, 0, 5, 5);
		gbc_atkValueLabel.gridx = 18;
		gbc_atkValueLabel.gridy = 1;
		northPanel.add(atkValueLabel, gbc_atkValueLabel);

		JLabel dungeonIconLabel = new JLabel(""); //$NON-NLS-1$
		dungeonIconLabel.setIcon(new ImageIcon(Body.class.getResource("/imagenes/varios/orc_Icon.png"))); //$NON-NLS-1$
		GridBagConstraints gbc_dungeonIconLabel = new GridBagConstraints();
		gbc_dungeonIconLabel.gridheight = 5;
		gbc_dungeonIconLabel.gridx = 23;
		gbc_dungeonIconLabel.gridy = 0;
		northPanel.add(dungeonIconLabel, gbc_dungeonIconLabel);

		JLabel expLabel = new JLabel("Exp :"); //$NON-NLS-1$
		expLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_expLabel = new GridBagConstraints();
		gbc_expLabel.insets = new Insets(0, 0, 5, 5);
		gbc_expLabel.gridx = 8;
		gbc_expLabel.gridy = 1;
		northPanel.add(expLabel, gbc_expLabel);

		JLabel dexLabel = new JLabel(Messages.getString("Game.Dex")); //$NON-NLS-1$
		dexLabel.setToolTipText(Messages.getString("Game.dexLabel.toolTipText")); //$NON-NLS-1$
		dexLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_dexLabel = new GridBagConstraints();
		gbc_dexLabel.insets = new Insets(0, 0, 5, 5);
		gbc_dexLabel.gridx = 11;
		gbc_dexLabel.gridy = 1;
		northPanel.add(dexLabel, gbc_dexLabel);

		JLabel combatSkillLabel = new JLabel(Messages.getString("Game.Combat")); //$NON-NLS-1$
		combatSkillLabel.setToolTipText(Messages.getString("Game.combatSkillLabel.toolTipText")); //$NON-NLS-1$
		combatSkillLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_combatSkillLabel = new GridBagConstraints();
		gbc_combatSkillLabel.anchor = GridBagConstraints.EAST;
		gbc_combatSkillLabel.gridwidth = 2;
		gbc_combatSkillLabel.insets = new Insets(0, 0, 5, 5);
		gbc_combatSkillLabel.gridx = 13;
		gbc_combatSkillLabel.gridy = 1;
		northPanel.add(combatSkillLabel, gbc_combatSkillLabel);

		JLabel atkLabel = new JLabel(Messages.getString("Game.Atk")); //$NON-NLS-1$
		atkLabel.setToolTipText(Messages.getString("Game.atkLabel.toolTipText")); //$NON-NLS-1$
		atkLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_atkLabel = new GridBagConstraints();
		gbc_atkLabel.insets = new Insets(0, 0, 5, 5);
		gbc_atkLabel.gridx = 17;
		gbc_atkLabel.gridy = 1;
		northPanel.add(atkLabel, gbc_atkLabel);

		nameValueLabel = new JLabel(""); //$NON-NLS-1$
		nameValueLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_nameValueLabel = new GridBagConstraints();
		gbc_nameValueLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameValueLabel.gridx = 3;
		gbc_nameValueLabel.gridy = 2;
		northPanel.add(nameValueLabel, gbc_nameValueLabel);

		levelValueLabel = new JLabel(""); //$NON-NLS-1$
		levelValueLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_levelValueLabel = new GridBagConstraints();
		gbc_levelValueLabel.insets = new Insets(0, 0, 5, 5);
		gbc_levelValueLabel.gridx = 6;
		gbc_levelValueLabel.gridy = 2;
		northPanel.add(levelValueLabel, gbc_levelValueLabel);

		damageValueLabel = new JLabel(""); //$NON-NLS-1$
		damageValueLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_damageValueLabel = new GridBagConstraints();
		gbc_damageValueLabel.insets = new Insets(0, 0, 5, 5);
		gbc_damageValueLabel.gridx = 21;
		gbc_damageValueLabel.gridy = 2;
		northPanel.add(damageValueLabel, gbc_damageValueLabel);

		JLabel nameLabel = new JLabel(Messages.getString("Game.Name")); //$NON-NLS-1$
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameLabel.gridx = 2;
		gbc_nameLabel.gridy = 2;
		northPanel.add(nameLabel, gbc_nameLabel);

		JLabel levelLabel_1 = new JLabel(Messages.getString("Game.Lv")); //$NON-NLS-1$
		levelLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_levelLabel_1 = new GridBagConstraints();
		gbc_levelLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_levelLabel_1.gridx = 5;
		gbc_levelLabel_1.gridy = 2;
		northPanel.add(levelLabel_1, gbc_levelLabel_1);

		JLabel damageLabel = new JLabel(Messages.getString("Game.Damage")); //$NON-NLS-1$
		damageLabel.setToolTipText(Messages.getString("Game.damageLabel.toolTipText")); //$NON-NLS-1$
		damageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_damageLabel = new GridBagConstraints();
		gbc_damageLabel.insets = new Insets(0, 0, 5, 5);
		gbc_damageLabel.gridx = 20;
		gbc_damageLabel.gridy = 2;
		northPanel.add(damageLabel, gbc_damageLabel);

		JLabel strLabel = new JLabel(Messages.getString("Game.Str")); //$NON-NLS-1$
		strLabel.setToolTipText(Messages.getString("Game.strLabel.toolTipText")); //$NON-NLS-1$
		strLabel.setPreferredSize(new Dimension(31, 14));
		strLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_strLabel = new GridBagConstraints();
		gbc_strLabel.insets = new Insets(0, 0, 5, 5);
		gbc_strLabel.gridx = 8;
		gbc_strLabel.gridy = 3;
		northPanel.add(strLabel, gbc_strLabel);

		JLabel intLabel = new JLabel("Int :"); //$NON-NLS-1$
		intLabel.setToolTipText(Messages.getString("Game.intLabel.toolTipText")); //$NON-NLS-1$
		intLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_intLabel = new GridBagConstraints();
		gbc_intLabel.insets = new Insets(0, 0, 5, 5);
		gbc_intLabel.gridx = 11;
		gbc_intLabel.gridy = 3;
		northPanel.add(intLabel, gbc_intLabel);

		JLabel powerLabel = new JLabel(Messages.getString("Game.Power")); //$NON-NLS-1$
		powerLabel.setToolTipText(Messages.getString("Game.powerLabel.toolTipText")); //$NON-NLS-1$
		powerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_powerLabel = new GridBagConstraints();
		gbc_powerLabel.anchor = GridBagConstraints.EAST;
		gbc_powerLabel.insets = new Insets(0, 0, 5, 5);
		gbc_powerLabel.gridx = 14;
		gbc_powerLabel.gridy = 3;
		northPanel.add(powerLabel, gbc_powerLabel);

		JLabel defLabel = new JLabel("Def :"); //$NON-NLS-1$
		defLabel.setToolTipText(Messages.getString("Game.defLabel.toolTipText")); //$NON-NLS-1$
		defLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_defLabel = new GridBagConstraints();
		gbc_defLabel.insets = new Insets(0, 0, 5, 5);
		gbc_defLabel.gridx = 17;
		gbc_defLabel.gridy = 3;
		northPanel.add(defLabel, gbc_defLabel);

		intValueLabel = new JLabel(""); //$NON-NLS-1$
		intValueLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_intValueLabel = new GridBagConstraints();
		gbc_intValueLabel.insets = new Insets(0, 0, 5, 5);
		gbc_intValueLabel.gridx = 12;
		gbc_intValueLabel.gridy = 3;
		northPanel.add(intValueLabel, gbc_intValueLabel);

		defValueLabel = new JLabel(""); //$NON-NLS-1$
		defValueLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_defValueLabel = new GridBagConstraints();
		gbc_defValueLabel.insets = new Insets(0, 0, 5, 5);
		gbc_defValueLabel.gridx = 18;
		gbc_defValueLabel.gridy = 3;
		northPanel.add(defValueLabel, gbc_defValueLabel);

		strValueLabel = new JLabel(""); //$NON-NLS-1$
		strValueLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_strValueLabel = new GridBagConstraints();
		gbc_strValueLabel.insets = new Insets(0, 0, 5, 5);
		gbc_strValueLabel.gridx = 9;
		gbc_strValueLabel.gridy = 3;
		northPanel.add(strValueLabel, gbc_strValueLabel);

		powerValueLabel = new JLabel(""); //$NON-NLS-1$
		powerValueLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_powerValueLabel = new GridBagConstraints();
		gbc_powerValueLabel.insets = new Insets(0, 0, 5, 5);
		gbc_powerValueLabel.gridx = 15;
		gbc_powerValueLabel.gridy = 3;
		northPanel.add(powerValueLabel, gbc_powerValueLabel);

		Panel westPanel = new Panel();
		westPanel.setFocusable(false);
		westPanel.setPreferredSize(new Dimension(300, 290));
		westPanel.setBounds(new Rectangle(0, 150, 300, 290));
		GridBagConstraints gbc_westPanel = new GridBagConstraints();
		gbc_westPanel.fill = GridBagConstraints.BOTH;
		gbc_westPanel.insets = new Insets(0, 0, 5, 5);
		gbc_westPanel.gridx = 0;
		gbc_westPanel.gridy = 1;
		gamePanel.add(westPanel, gbc_westPanel);
		GridBagLayout gbl_westPanel = new GridBagLayout();
		gbl_westPanel.columnWidths = new int[]{255, 0};
		gbl_westPanel.rowHeights = new int[]{411, 0};
		gbl_westPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_westPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		westPanel.setLayout(gbl_westPanel);

		JPanel chatPanel = new JPanel();
		chatPanel.setFocusable(false);
		chatPanel.setBorder(new LineBorder(new Color(192, 192, 192)));
		GridBagConstraints gbc_chatPanel = new GridBagConstraints();
		gbc_chatPanel.fill = GridBagConstraints.BOTH;
		gbc_chatPanel.gridx = 0;
		gbc_chatPanel.gridy = 0;
		westPanel.add(chatPanel, gbc_chatPanel);
		GridBagLayout gbl_chatPanel = new GridBagLayout();
		gbl_chatPanel.rowWeights = new double[]{0.0, 1.0, 0.0};
		gbl_chatPanel.columnWeights = new double[]{1.0, 1.0};
		gbl_chatPanel.columnWidths = new int[]{187, 66};
		gbl_chatPanel.rowHeights = new int[]{16, 348, 23};
		chatPanel.setLayout(gbl_chatPanel);

		JLabel userLabel = new JLabel("Chat"); //$NON-NLS-1$
		userLabel.setFocusable(false);
		userLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_userLabel = new GridBagConstraints();
		gbc_userLabel.fill = GridBagConstraints.BOTH;
		gbc_userLabel.gridx = 0;
		gbc_userLabel.gridy = 0;
		chatPanel.add(userLabel, gbc_userLabel);

		usersLabel = new JLabel(Messages.getString("Game.Users")); //$NON-NLS-1$
		usersLabel.setFocusable(false);
		usersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usersLabel.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(0, 0, 0)));
		GridBagConstraints gbc_usersLabel = new GridBagConstraints();
		gbc_usersLabel.fill = GridBagConstraints.BOTH;
		gbc_usersLabel.gridx = 1;
		gbc_usersLabel.gridy = 0;
		chatPanel.add(usersLabel, gbc_usersLabel);

		chatScrollPane = new JScrollPane();
		chatScrollPane.setFocusable(false);
		chatScrollPane.setBorder(new MatteBorder(0, 1, 1, 1, (Color) new Color(0, 0, 255)));
		GridBagConstraints gbc_chatScrollPane = new GridBagConstraints();
		gbc_chatScrollPane.fill = GridBagConstraints.BOTH;
		gbc_chatScrollPane.gridx = 0;
		gbc_chatScrollPane.gridy = 1;
		chatPanel.add(chatScrollPane, gbc_chatScrollPane);

		chatList = new JList<String>();
		chatList.setToolTipText(Messages.getString("Game.chatList.toolTipText")); //$NON-NLS-1$
		chatScrollPane.setViewportView(chatList);
		chatList.setEnabled(false);
		chatList.setFocusable(false);
		chatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		userScrollPane = new JScrollPane();
		userScrollPane.setFocusable(false);
		userScrollPane.setBorder(new MatteBorder(0, 0, 1, 1, (Color) new Color(0, 0, 255)));
		GridBagConstraints gbc_userScrollPane = new GridBagConstraints();
		gbc_userScrollPane.fill = GridBagConstraints.BOTH;
		gbc_userScrollPane.gridx = 1;
		gbc_userScrollPane.gridy = 1;
		chatPanel.add(userScrollPane, gbc_userScrollPane);

		userList = new JList<Usuario>();
		userList.setToolTipText(Messages.getString("Game.userList.toolTipText")); //$NON-NLS-1$
		userScrollPane.setViewportView(userList);
		userList.setEnabled(false);

		userList.setVisibleRowCount(30);
		userList.setFocusable(false);
		userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		sendButton = new JButton(Messages.getString("Game.Send")); //$NON-NLS-1$
		sendButton.setFocusable(false);
		sendButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				enviarMensaje();
			}
		});

		chatTextField = new JTextField();
		chatTextField.setBorder(new LineBorder(new Color(128, 128, 128)));
		chatTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if ( e.getKeyCode() == KeyEvent.VK_ENTER ){
					enviarMensaje();
				}
			}
		});
		GridBagConstraints gbc_chatTextField = new GridBagConstraints();
		gbc_chatTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_chatTextField.gridx = 0;
		gbc_chatTextField.gridy = 2;
		chatPanel.add(chatTextField, gbc_chatTextField);
		chatTextField.setColumns(10);
		chatTextField.grabFocus();
		GridBagConstraints gbc_sendButton = new GridBagConstraints();
		gbc_sendButton.anchor = GridBagConstraints.NORTH;
		gbc_sendButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_sendButton.gridx = 1;
		gbc_sendButton.gridy = 2;
		chatPanel.add(sendButton, gbc_sendButton);

		JPanel centerPanel = new JPanel();
		centerPanel.setFocusable(false);
		centerPanel.setBorder(new LineBorder(new Color(192, 192, 192)));
		centerPanel.setPreferredSize(new Dimension(680, 520));
		GridBagConstraints gbc_centerPanel = new GridBagConstraints();
		gbc_centerPanel.fill = GridBagConstraints.BOTH;
		gbc_centerPanel.gridx = 1;
		gbc_centerPanel.gridy = 1;
		gamePanel.add(centerPanel, gbc_centerPanel);
		GridBagLayout gbl_centerPanel = new GridBagLayout();
		gbl_centerPanel.columnWidths = new int[]{636, 0};
		gbl_centerPanel.rowHeights = new int[]{186, 37, 202, 0};
		gbl_centerPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_centerPanel.rowWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		centerPanel.setLayout(gbl_centerPanel);

		JPanel heroPanel = new JPanel();
		heroPanel.setFocusable(false);
		heroPanel.setPreferredSize(new Dimension(664, 208));
		heroPanel.setBorder(new LineBorder(new Color(123, 104, 238)));
		GridBagConstraints gbc_heroPanel = new GridBagConstraints();
		gbc_heroPanel.fill = GridBagConstraints.BOTH;
		gbc_heroPanel.gridx = 0;
		gbc_heroPanel.gridy = 0;
		centerPanel.add(heroPanel, gbc_heroPanel);
		GridBagLayout gbl_heroPanel = new GridBagLayout();
		gbl_heroPanel.columnWidths = new int[]{277, 77, 277, 0};
		gbl_heroPanel.rowHeights = new int[]{32, 137, 0};
		gbl_heroPanel.columnWeights = new double[]{1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_heroPanel.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		heroPanel.setLayout(gbl_heroPanel);

		JPanel PVPHPanel = new JPanel();
		PVPHPanel.setFocusable(false);
		PVPHPanel.setPreferredSize(new Dimension(660, 50));
		GridBagConstraints gbc_PVPHPanel = new GridBagConstraints();
		gbc_PVPHPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_PVPHPanel.gridwidth = 3;
		gbc_PVPHPanel.gridx = 0;
		gbc_PVPHPanel.gridy = 0;
		heroPanel.add(PVPHPanel, gbc_PVPHPanel);
		GridBagLayout gbl_PVPHPanel = new GridBagLayout();
		gbl_PVPHPanel.columnWidths = new int[]{146, 0};
		gbl_PVPHPanel.rowHeights = new int[]{23, 0};
		gbl_PVPHPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_PVPHPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		PVPHPanel.setLayout(gbl_PVPHPanel);

		heroPVBar = new JProgressBar();
		heroPVBar.setFocusable(false);
		GridBagConstraints gbc_heroPVBar = new GridBagConstraints();
		gbc_heroPVBar.fill = GridBagConstraints.VERTICAL;
		gbc_heroPVBar.gridx = 0;
		gbc_heroPVBar.gridy = 0;
		PVPHPanel.add(heroPVBar, gbc_heroPVBar);
		heroPVBar.setForeground(new Color(60, 179, 113));
		heroPVBar.setStringPainted(true);

		JPanel ImageButtonPanel = new JPanel();
		ImageButtonPanel.setFocusable(false);
		ImageButtonPanel.setPreferredSize(new Dimension(662, 156));
		GridBagConstraints gbc_ImageButtonPanel = new GridBagConstraints();
		gbc_ImageButtonPanel.fill = GridBagConstraints.BOTH;
		gbc_ImageButtonPanel.insets = new Insets(0, 0, 0, 5);
		gbc_ImageButtonPanel.gridx = 0;
		gbc_ImageButtonPanel.gridy = 1;
		heroPanel.add(ImageButtonPanel, gbc_ImageButtonPanel);
		GridBagLayout gbl_ImageButtonPanel = new GridBagLayout();
		gbl_ImageButtonPanel.columnWidths = new int[]{280, 0};
		gbl_ImageButtonPanel.rowHeights = new int[]{132, 0};
		gbl_ImageButtonPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_ImageButtonPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		ImageButtonPanel.setLayout(gbl_ImageButtonPanel);

		AtkDefPanel = new JPanel();
		AtkDefPanel.setFocusable(false);
		AtkDefPanel.setPreferredSize(new Dimension(285, 107));
		GridBagConstraints gbc_AtkDefPanel = new GridBagConstraints();
		gbc_AtkDefPanel.fill = GridBagConstraints.BOTH;
		gbc_AtkDefPanel.gridx = 0;
		gbc_AtkDefPanel.gridy = 0;
		ImageButtonPanel.add(AtkDefPanel, gbc_AtkDefPanel);
		GridBagLayout gbl_AtkDefPanel = new GridBagLayout();
		gbl_AtkDefPanel.columnWidths = new int[]{100, 0};
		gbl_AtkDefPanel.rowHeights = new int[]{35, 35, 0};
		gbl_AtkDefPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_AtkDefPanel.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		AtkDefPanel.setLayout(gbl_AtkDefPanel);

		atkButton = new JButton(Messages.getString("Game.Attack")); //$NON-NLS-1$	
		atkButton.setToolTipText(Messages.getString("Game.atkButton.toolTipText")); //$NON-NLS-1$
		atkButton.setFocusable(false);
		GridBagConstraints gbc_atkButton = new GridBagConstraints();
		gbc_atkButton.insets = new Insets(0, 0, 5, 0);
		gbc_atkButton.gridx = 0;
		gbc_atkButton.gridy = 0;
		AtkDefPanel.add(atkButton, gbc_atkButton);
		atkButton.setPreferredSize(new Dimension(100, 35));

		atkButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if ( enemigoSeleccionado() && !dead ){
					escribirEnListaEventos(combat.atacarConArma(selectedEnemy, enemigos));
					if ( selectedEnemy.getPV() <= 0 && enemigos.size() != 0){
						elegirSiguienteEnemigo();
					}
					if ( enemigos.size() != 0 ){
						turnoEnemigos(1);
					}else{
						siguienteSala();
					}				
					endTurn();
					cleanEnemiesPanel();
					updateGamePanel();
				}
			}
		});

		defenseButton = new JButton(Messages.getString("Game.Defense")); //$NON-NLS-1$
		defenseButton.setToolTipText(Messages.getString("Game.defenseButton.toolTipText")); //$NON-NLS-1$
		defenseButton.setFocusable(false);
		GridBagConstraints gbc_defenseButton = new GridBagConstraints();
		gbc_defenseButton.gridx = 0;
		gbc_defenseButton.gridy = 1;
		AtkDefPanel.add(defenseButton, gbc_defenseButton);
		defenseButton.setPreferredSize(new Dimension(100, 35));
		defenseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if ( enemigoSeleccionado() && !dead ){				
					turnoEnemigos(4);
					endTurn();
					updateGamePanel();
				}
			}
		});

		heroeIconLabel = new JLabel(""); //$NON-NLS-1$
		heroeIconLabel.setFocusable(false);
		pintoImagen();
		GridBagConstraints gbc_heroIconLabel = new GridBagConstraints();
		gbc_heroIconLabel.fill = GridBagConstraints.BOTH;
		gbc_heroIconLabel.insets = new Insets(0, 0, 0, 5);
		gbc_heroIconLabel.gridx = 1;
		gbc_heroIconLabel.gridy = 1;
		heroPanel.add(heroeIconLabel, gbc_heroIconLabel);
		heroeIconLabel.setPreferredSize(new Dimension(89, 107));
		SkillPotionPanel = new JPanel();
		SkillPotionPanel.setFocusable(false);
		GridBagConstraints gbc_SkillPotionPanel = new GridBagConstraints();
		gbc_SkillPotionPanel.fill = GridBagConstraints.BOTH;
		gbc_SkillPotionPanel.gridx = 2;
		gbc_SkillPotionPanel.gridy = 1;
		heroPanel.add(SkillPotionPanel, gbc_SkillPotionPanel);
		SkillPotionPanel.setPreferredSize(new Dimension(285, 107));
		GridBagLayout gbl_SkillPotionPanel = new GridBagLayout();
		gbl_SkillPotionPanel.columnWidths = new int[]{263, 0};
		gbl_SkillPotionPanel.rowHeights = new int[]{55, 1, 55, 0};
		gbl_SkillPotionPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_SkillPotionPanel.rowWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		SkillPotionPanel.setLayout(gbl_SkillPotionPanel);
		habilidadesPanel = new JPanel();
		habilidadesPanel.setFocusable(false);
		GridBagConstraints gbc_habilidadesPanel = new GridBagConstraints();
		gbc_habilidadesPanel.fill = GridBagConstraints.BOTH;
		gbc_habilidadesPanel.insets = new Insets(0, 0, 5, 0);
		gbc_habilidadesPanel.gridx = 0;
		gbc_habilidadesPanel.gridy = 0;
		SkillPotionPanel.add(habilidadesPanel, gbc_habilidadesPanel);
		GridBagLayout gbl_habilidadesPanel = new GridBagLayout();
		gbl_habilidadesPanel.columnWidths = new int[]{60, 60, 60, 60, 0};
		gbl_habilidadesPanel.rowHeights = new int[]{1, 0};
		gbl_habilidadesPanel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_habilidadesPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		habilidadesPanel.setLayout(gbl_habilidadesPanel);
		torbellinoLabel = new JLabel(""); //$NON-NLS-1$
		torbellinoLabel.setFocusable(false);
		torbellinoLabel.setToolTipText(Messages.getString("Game.torbellinoLabel.toolTipText")); //$NON-NLS-1$
		torbellinoLabel.setIcon(new ImageIcon(Game.class.getResource("/imagenes/habilidades/Torbellino.png"))); //$NON-NLS-1$
		torbellinoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_torbellinoLabel = new GridBagConstraints();
		gbc_torbellinoLabel.fill = GridBagConstraints.BOTH;
		gbc_torbellinoLabel.insets = new Insets(0, 0, 0, 5);
		gbc_torbellinoLabel.gridx = 0;
		gbc_torbellinoLabel.gridy = 0;
		habilidadesPanel.add(torbellinoLabel, gbc_torbellinoLabel);
		golpeConcentradoLabel = new JLabel(""); //$NON-NLS-1$
		golpeConcentradoLabel.setFocusable(false);
		golpeConcentradoLabel.setToolTipText(Messages.getString("Game.golpeConcentradoLabel.toolTipText")); //$NON-NLS-1$
		golpeConcentradoLabel.setIcon(new ImageIcon(Game.class.getResource("/imagenes/habilidades/Golpe Concentrado.png"))); //$NON-NLS-1$
		golpeConcentradoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_golpeConcentradoLabel = new GridBagConstraints();
		gbc_golpeConcentradoLabel.fill = GridBagConstraints.BOTH;
		gbc_golpeConcentradoLabel.insets = new Insets(0, 0, 0, 5);
		gbc_golpeConcentradoLabel.gridx = 1;
		gbc_golpeConcentradoLabel.gridy = 0;
		habilidadesPanel.add(golpeConcentradoLabel, gbc_golpeConcentradoLabel);
		curarLabel = new JLabel(""); //$NON-NLS-1$
		curarLabel.setFocusable(false);
		curarLabel.setToolTipText(Messages.getString("Game.curarLabel.toolTipText")); //$NON-NLS-1$
		curarLabel.setIcon(new ImageIcon(Game.class.getResource("/imagenes/habilidades/Curar.png"))); //$NON-NLS-1$
		curarLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_curarLabel = new GridBagConstraints();
		gbc_curarLabel.fill = GridBagConstraints.BOTH;
		gbc_curarLabel.insets = new Insets(0, 0, 0, 5);
		gbc_curarLabel.gridx = 2;
		gbc_curarLabel.gridy = 0;
		habilidadesPanel.add(curarLabel, gbc_curarLabel);
		llamaradaLabel = new JLabel(""); //$NON-NLS-1$
		llamaradaLabel.setFocusable(false);
		llamaradaLabel.setToolTipText(Messages.getString("Game.llamaradaLabel.toolTipText")); //$NON-NLS-1$
		llamaradaLabel.setIcon(new ImageIcon(Game.class.getResource("/imagenes/habilidades/Llamarada.png"))); //$NON-NLS-1$
		llamaradaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_llamaradaLabel = new GridBagConstraints();
		gbc_llamaradaLabel.fill = GridBagConstraints.BOTH;
		gbc_llamaradaLabel.gridx = 3;
		gbc_llamaradaLabel.gridy = 0;
		habilidadesPanel.add(llamaradaLabel, gbc_llamaradaLabel);
		separator = new JSeparator();
		separator.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 1;
		SkillPotionPanel.add(separator, gbc_separator);
		pocionesPanel = new JPanel();
		pocionesPanel.setFocusable(false);
		GridBagConstraints gbc_pocionesPanel = new GridBagConstraints();
		gbc_pocionesPanel.fill = GridBagConstraints.BOTH;
		gbc_pocionesPanel.gridx = 0;
		gbc_pocionesPanel.gridy = 2;
		SkillPotionPanel.add(pocionesPanel, gbc_pocionesPanel);
		GridBagLayout gbl_pocionesPanel = new GridBagLayout();
		gbl_pocionesPanel.columnWidths = new int[]{80, 80, 80, 0};
		gbl_pocionesPanel.rowHeights = new int[]{23, 0};
		gbl_pocionesPanel.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_pocionesPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		pocionesPanel.setLayout(gbl_pocionesPanel);
		pocionCurativaLabel = new JLabel(""); //$NON-NLS-1$
		pocionCurativaLabel.setFocusable(false);
		pocionCurativaLabel.setToolTipText(Messages.getString("Game.pocionCurativaLabel.toolTipText")); //$NON-NLS-1$
		pocionCurativaLabel.setIcon(new ImageIcon(Game.class.getResource("/imagenes/pociones/Pocion Curativa.png"))); //$NON-NLS-1$
		pocionCurativaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_pocionCurativaLabel = new GridBagConstraints();
		gbc_pocionCurativaLabel.fill = GridBagConstraints.BOTH;
		gbc_pocionCurativaLabel.insets = new Insets(0, 0, 0, 5);
		gbc_pocionCurativaLabel.gridx = 0;
		gbc_pocionCurativaLabel.gridy = 0;
		pocionesPanel.add(pocionCurativaLabel, gbc_pocionCurativaLabel);
		pocionManaLabel = new JLabel(""); //$NON-NLS-1$
		pocionManaLabel.setFocusable(false);
		pocionManaLabel.setToolTipText(Messages.getString("Game.pocionManaLabel.toolTipText")); //$NON-NLS-1$
		pocionManaLabel.setIcon(new ImageIcon(Game.class.getResource("/imagenes/pociones/Pocion Mana.png"))); //$NON-NLS-1$
		pocionManaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_pocionManaLabel = new GridBagConstraints();
		gbc_pocionManaLabel.fill = GridBagConstraints.BOTH;
		gbc_pocionManaLabel.insets = new Insets(0, 0, 0, 5);
		gbc_pocionManaLabel.gridx = 1;
		gbc_pocionManaLabel.gridy = 0;
		pocionesPanel.add(pocionManaLabel, gbc_pocionManaLabel);
		pocionExplosivaLabel = new JLabel(""); //$NON-NLS-1$
		pocionExplosivaLabel.setFocusable(false);
		pocionExplosivaLabel.setToolTipText(Messages.getString("Game.pocionExplosivaLabel.toolTipText")); //$NON-NLS-1$
		pocionExplosivaLabel.setIcon(new ImageIcon(Game.class.getResource("/imagenes/pociones/Pocion Explosiva.png"))); //$NON-NLS-1$
		pocionExplosivaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_pocionExplosivaLabel = new GridBagConstraints();
		gbc_pocionExplosivaLabel.fill = GridBagConstraints.BOTH;
		gbc_pocionExplosivaLabel.gridx = 2;
		gbc_pocionExplosivaLabel.gridy = 0;
		pocionesPanel.add(pocionExplosivaLabel, gbc_pocionExplosivaLabel);
		JPanel enemiesPanel = new JPanel();
		enemiesPanel.setFocusable(false);
		enemiesPanel.setBorder(new LineBorder(new Color(220, 20, 60)));
		GridBagConstraints gbc_enemiesPanel = new GridBagConstraints();
		gbc_enemiesPanel.fill = GridBagConstraints.BOTH;
		gbc_enemiesPanel.gridx = 0;
		gbc_enemiesPanel.gridy = 2;
		centerPanel.add(enemiesPanel, gbc_enemiesPanel);
		GridBagLayout gbl_enemiesPanel = new GridBagLayout();
		gbl_enemiesPanel.columnWidths = new int[]{102, 98, 98, 98, 98, 98, 0};
		gbl_enemiesPanel.rowHeights = new int[]{164, 0};
		gbl_enemiesPanel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_enemiesPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		enemiesPanel.setLayout(gbl_enemiesPanel);
		enemy1Panel = new JPanel();
		enemy1Panel.setFocusable(false);
		enemy1Panel.setVisible(false);
		enemy1Panel.setPreferredSize(new Dimension(98, 164));
		GridBagConstraints gbc_enemy1Panel = new GridBagConstraints();
		gbc_enemy1Panel.fill = GridBagConstraints.BOTH;
		gbc_enemy1Panel.insets = new Insets(0, 0, 0, 5);
		gbc_enemy1Panel.gridx = 0;
		gbc_enemy1Panel.gridy = 0;
		enemiesPanel.add(enemy1Panel, gbc_enemy1Panel);
		GridBagLayout gbl_enemy1Panel = new GridBagLayout();
		gbl_enemy1Panel.columnWidths = new int[]{90, 0};
		gbl_enemy1Panel.rowHeights = new int[]{22, 17, 98, 17, 0};
		gbl_enemy1Panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_enemy1Panel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		enemy1Panel.setLayout(gbl_enemy1Panel);
		enemy1NameLabel = new JLabel(""); //$NON-NLS-1$
		enemy1NameLabel.setFocusable(false);
		enemy1NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_enemy1NameLabel = new GridBagConstraints();
		gbc_enemy1NameLabel.fill = GridBagConstraints.BOTH;
		gbc_enemy1NameLabel.insets = new Insets(0, 0, 5, 0);
		gbc_enemy1NameLabel.gridx = 0;
		gbc_enemy1NameLabel.gridy = 0;
		enemy1Panel.add(enemy1NameLabel, gbc_enemy1NameLabel);
		enemy1PVBar = new JProgressBar();
		enemy1PVBar.setFocusable(false);
		enemy1PVBar.setForeground(new Color(102, 51, 102));
		enemy1PVBar.setStringPainted(true);
		enemy1PVBar.setVisible(false);
		GridBagConstraints gbc_enemy1PVBar = new GridBagConstraints();
		gbc_enemy1PVBar.fill = GridBagConstraints.BOTH;
		gbc_enemy1PVBar.insets = new Insets(0, 0, 5, 0);
		gbc_enemy1PVBar.gridx = 0;
		gbc_enemy1PVBar.gridy = 1;
		enemy1Panel.add(enemy1PVBar, gbc_enemy1PVBar);
		enemy1SkillBar = new JProgressBar();
		enemy1SkillBar.setFocusable(false);
		enemy1SkillBar.setForeground(new Color(153, 0, 51));
		enemy1SkillBar.setVisible(false);
		enemy1Icon = new JLabel(""); //$NON-NLS-1$
		enemy1Icon.setFocusable(false);
		enemy1Icon.setBorder(new LineBorder(new Color(255, 0, 0), 2));
		GridBagConstraints gbc_enemy1Icon = new GridBagConstraints();
		gbc_enemy1Icon.fill = GridBagConstraints.BOTH;
		gbc_enemy1Icon.insets = new Insets(0, 0, 5, 0);
		gbc_enemy1Icon.gridx = 0;
		gbc_enemy1Icon.gridy = 2;
		enemy1Panel.add(enemy1Icon, gbc_enemy1Icon);
		enemy1Icon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				borderReset();
				enemy1Icon.setBorder(new LineBorder ( new Color (255, 0, 0), 2));
				selectedEnemy = enemigos.get(0);
			}
		});
		enemy1Icon.setVisible(false);
		enemy1Icon.setPreferredSize(new Dimension(98, 98));
		enemy1Icon.setHorizontalAlignment(SwingConstants.CENTER);
		enemy1SkillBar.setStringPainted(true);
		GridBagConstraints gbc_enemy1SkillBar = new GridBagConstraints();
		gbc_enemy1SkillBar.fill = GridBagConstraints.BOTH;
		gbc_enemy1SkillBar.gridx = 0;
		gbc_enemy1SkillBar.gridy = 3;
		enemy1Panel.add(enemy1SkillBar, gbc_enemy1SkillBar);
		enemy2Panel = new JPanel();
		enemy2Panel.setFocusable(false);
		enemy2Panel.setVisible(false);
		enemy2Panel.setPreferredSize(new Dimension(98, 164));
		enemy2Panel.setBorder(null);
		GridBagConstraints gbc_enemy2Panel = new GridBagConstraints();
		gbc_enemy2Panel.fill = GridBagConstraints.BOTH;
		gbc_enemy2Panel.insets = new Insets(0, 0, 0, 5);
		gbc_enemy2Panel.gridx = 1;
		gbc_enemy2Panel.gridy = 0;
		enemiesPanel.add(enemy2Panel, gbc_enemy2Panel);
		GridBagLayout gbl_enemy2Panel = new GridBagLayout();
		gbl_enemy2Panel.columnWidths = new int[]{90, 0};
		gbl_enemy2Panel.rowHeights = new int[]{22, 17, 98, 17, 0};
		gbl_enemy2Panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_enemy2Panel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		enemy2Panel.setLayout(gbl_enemy2Panel);
		enemy2SkillBar = new JProgressBar();
		enemy2SkillBar.setFocusable(false);
		enemy2SkillBar.setForeground(new Color(153, 0, 51));
		enemy2SkillBar.setStringPainted(true);
		enemy2SkillBar.setVisible(false);
		enemy2NameLabel = new JLabel(""); //$NON-NLS-1$
		enemy2NameLabel.setFocusable(false);
		enemy2NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_enemy2NameLabel = new GridBagConstraints();
		gbc_enemy2NameLabel.fill = GridBagConstraints.BOTH;
		gbc_enemy2NameLabel.insets = new Insets(0, 0, 5, 0);
		gbc_enemy2NameLabel.gridx = 0;
		gbc_enemy2NameLabel.gridy = 0;
		enemy2Panel.add(enemy2NameLabel, gbc_enemy2NameLabel);

		enemy2PVBar = new JProgressBar();
		enemy2PVBar.setFocusable(false);
		enemy2PVBar.setForeground(new Color(102, 51, 102));
		enemy2PVBar.setVisible(false);
		enemy2PVBar.setStringPainted(true);
		GridBagConstraints gbc_enemy2PVBar = new GridBagConstraints();
		gbc_enemy2PVBar.fill = GridBagConstraints.BOTH;
		gbc_enemy2PVBar.insets = new Insets(0, 0, 5, 0);
		gbc_enemy2PVBar.gridx = 0;
		gbc_enemy2PVBar.gridy = 1;
		enemy2Panel.add(enemy2PVBar, gbc_enemy2PVBar);

		enemy2Icon = new JLabel(""); //$NON-NLS-1$
		enemy2Icon.setFocusable(false);
		GridBagConstraints gbc_enemy2Icon = new GridBagConstraints();
		gbc_enemy2Icon.fill = GridBagConstraints.BOTH;
		gbc_enemy2Icon.insets = new Insets(0, 0, 5, 0);
		gbc_enemy2Icon.gridx = 0;
		gbc_enemy2Icon.gridy = 2;
		enemy2Panel.add(enemy2Icon, gbc_enemy2Icon);
		enemy2Icon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				borderReset();
				enemy2Icon.setBorder(new LineBorder ( new Color (255, 0, 0), 2));
				selectedEnemy = enemigos.get(1);
			}
		});
		enemy2Icon.setVisible(false);
		enemy2Icon.setPreferredSize(new Dimension(98, 98));
		enemy2Icon.setHorizontalAlignment(SwingConstants.CENTER);
		enemy2Icon.setBorder(new LineBorder(new Color(192, 192, 192)));
		GridBagConstraints gbc_enemy2SkillBar = new GridBagConstraints();
		gbc_enemy2SkillBar.fill = GridBagConstraints.BOTH;
		gbc_enemy2SkillBar.gridx = 0;
		gbc_enemy2SkillBar.gridy = 3;
		enemy2Panel.add(enemy2SkillBar, gbc_enemy2SkillBar);
		enemy3Panel = new JPanel();
		enemy3Panel.setFocusable(false);
		enemy3Panel.setVisible(false);
		enemy3Panel.setPreferredSize(new Dimension(98, 164));
		GridBagConstraints gbc_enemy3Panel = new GridBagConstraints();
		gbc_enemy3Panel.fill = GridBagConstraints.BOTH;
		gbc_enemy3Panel.insets = new Insets(0, 0, 0, 5);
		gbc_enemy3Panel.gridx = 2;
		gbc_enemy3Panel.gridy = 0;
		enemiesPanel.add(enemy3Panel, gbc_enemy3Panel);
		GridBagLayout gbl_enemy3Panel = new GridBagLayout();
		gbl_enemy3Panel.columnWidths = new int[]{92, 0};
		gbl_enemy3Panel.rowHeights = new int[]{22, 17, 98, 17, 0};
		gbl_enemy3Panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_enemy3Panel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		enemy3Panel.setLayout(gbl_enemy3Panel);

		enemy3PVBar = new JProgressBar();
		enemy3PVBar.setFocusable(false);
		enemy3PVBar.setForeground(new Color(102, 51, 102));
		enemy3PVBar.setMinimum(1);
		enemy3PVBar.setVisible(false);

		enemy3NameLabel = new JLabel(""); //$NON-NLS-1$
		enemy3NameLabel.setFocusable(false);
		enemy3NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_enemy3NameLabel = new GridBagConstraints();
		gbc_enemy3NameLabel.fill = GridBagConstraints.BOTH;
		gbc_enemy3NameLabel.insets = new Insets(0, 0, 5, 0);
		gbc_enemy3NameLabel.gridx = 0;
		gbc_enemy3NameLabel.gridy = 0;
		enemy3Panel.add(enemy3NameLabel, gbc_enemy3NameLabel);
		enemy3PVBar.setStringPainted(true);
		GridBagConstraints gbc_enemy3PVBar = new GridBagConstraints();
		gbc_enemy3PVBar.fill = GridBagConstraints.BOTH;
		gbc_enemy3PVBar.insets = new Insets(0, 0, 5, 0);
		gbc_enemy3PVBar.gridx = 0;
		gbc_enemy3PVBar.gridy = 1;
		enemy3Panel.add(enemy3PVBar, gbc_enemy3PVBar);

		enemy3Icon = new JLabel(""); //$NON-NLS-1$
		enemy3Icon.setFocusable(false);
		GridBagConstraints gbc_enemy3Icon = new GridBagConstraints();
		gbc_enemy3Icon.fill = GridBagConstraints.BOTH;
		gbc_enemy3Icon.insets = new Insets(0, 0, 5, 0);
		gbc_enemy3Icon.gridx = 0;
		gbc_enemy3Icon.gridy = 2;
		enemy3Panel.add(enemy3Icon, gbc_enemy3Icon);
		enemy3Icon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				borderReset();
				enemy3Icon.setBorder(new LineBorder ( new Color (255, 0, 0), 2));
				selectedEnemy = enemigos.get(2);
			}
		});
		enemy3Icon.setVisible(false);
		enemy3Icon.setPreferredSize(new Dimension(98, 98));
		enemy3Icon.setHorizontalAlignment(SwingConstants.CENTER);
		enemy3Icon.setBorder(new LineBorder(new Color(192, 192, 192)));

		enemy3SkillBar = new JProgressBar();
		enemy3SkillBar.setFocusable(false);
		enemy3SkillBar.setForeground(new Color(153, 0, 51));
		enemy3SkillBar.setStringPainted(true);
		enemy3SkillBar.setVisible(false);
		GridBagConstraints gbc_enemy3SkillBar = new GridBagConstraints();
		gbc_enemy3SkillBar.fill = GridBagConstraints.BOTH;
		gbc_enemy3SkillBar.gridx = 0;
		gbc_enemy3SkillBar.gridy = 3;
		enemy3Panel.add(enemy3SkillBar, gbc_enemy3SkillBar);

		enemy4Panel = new JPanel();
		enemy4Panel.setFocusable(false);
		enemy4Panel.setVisible(false);
		enemy4Panel.setPreferredSize(new Dimension(98, 164));
		GridBagConstraints gbc_enemy4Panel = new GridBagConstraints();
		gbc_enemy4Panel.fill = GridBagConstraints.BOTH;
		gbc_enemy4Panel.insets = new Insets(0, 0, 0, 5);
		gbc_enemy4Panel.gridx = 3;
		gbc_enemy4Panel.gridy = 0;
		enemiesPanel.add(enemy4Panel, gbc_enemy4Panel);
		GridBagLayout gbl_enemy4Panel = new GridBagLayout();
		gbl_enemy4Panel.columnWidths = new int[]{92, 0};
		gbl_enemy4Panel.rowHeights = new int[]{22, 17, 98, 17, 0};
		gbl_enemy4Panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_enemy4Panel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		enemy4Panel.setLayout(gbl_enemy4Panel);

		enemy4NameLabel = new JLabel(""); //$NON-NLS-1$
		enemy4NameLabel.setFocusable(false);
		enemy4NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_enemy4NameLabel = new GridBagConstraints();
		gbc_enemy4NameLabel.fill = GridBagConstraints.BOTH;
		gbc_enemy4NameLabel.insets = new Insets(0, 0, 5, 0);
		gbc_enemy4NameLabel.gridx = 0;
		gbc_enemy4NameLabel.gridy = 0;
		enemy4Panel.add(enemy4NameLabel, gbc_enemy4NameLabel);

		enemy4PVBar = new JProgressBar();
		enemy4PVBar.setFocusable(false);
		enemy4PVBar.setForeground(new Color(102, 51, 102));
		enemy4PVBar.setVisible(false);
		enemy4PVBar.setStringPainted(true);
		GridBagConstraints gbc_enemy4PVBar = new GridBagConstraints();
		gbc_enemy4PVBar.fill = GridBagConstraints.BOTH;
		gbc_enemy4PVBar.insets = new Insets(0, 0, 5, 0);
		gbc_enemy4PVBar.gridx = 0;
		gbc_enemy4PVBar.gridy = 1;
		enemy4Panel.add(enemy4PVBar, gbc_enemy4PVBar);

		enemy4SkillBar = new JProgressBar();
		enemy4SkillBar.setFocusable(false);
		enemy4SkillBar.setForeground(new Color(153, 0, 51));
		enemy4SkillBar.setStringPainted(true);
		enemy4SkillBar.setVisible(false);

		enemy4Icon = new JLabel(""); //$NON-NLS-1$
		enemy4Icon.setFocusable(false);
		GridBagConstraints gbc_enemy4Icon = new GridBagConstraints();
		gbc_enemy4Icon.fill = GridBagConstraints.BOTH;
		gbc_enemy4Icon.insets = new Insets(0, 0, 5, 0);
		gbc_enemy4Icon.gridx = 0;
		gbc_enemy4Icon.gridy = 2;
		enemy4Panel.add(enemy4Icon, gbc_enemy4Icon);
		enemy4Icon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				borderReset();
				enemy4Icon.setBorder(new LineBorder ( new Color (255, 0, 0), 2));
				selectedEnemy = enemigos.get(3);
			}
		});
		enemy4Icon.setVisible(false);
		enemy4Icon.setPreferredSize(new Dimension(98, 98));
		enemy4Icon.setHorizontalAlignment(SwingConstants.CENTER);
		enemy4Icon.setBorder(new LineBorder(new Color(192, 192, 192)));
		GridBagConstraints gbc_enemy4SkillBar = new GridBagConstraints();
		gbc_enemy4SkillBar.fill = GridBagConstraints.BOTH;
		gbc_enemy4SkillBar.gridx = 0;
		gbc_enemy4SkillBar.gridy = 3;
		enemy4Panel.add(enemy4SkillBar, gbc_enemy4SkillBar);

		enemy5Panel = new JPanel();
		enemy5Panel.setFocusable(false);
		enemy5Panel.setVisible(false);
		enemy5Panel.setPreferredSize(new Dimension(98, 164));
		GridBagConstraints gbc_enemy5Panel = new GridBagConstraints();
		gbc_enemy5Panel.insets = new Insets(0, 0, 0, 5);
		gbc_enemy5Panel.fill = GridBagConstraints.BOTH;
		gbc_enemy5Panel.gridx = 4;
		gbc_enemy5Panel.gridy = 0;
		enemiesPanel.add(enemy5Panel, gbc_enemy5Panel);
		GridBagLayout gbl_enemy5Panel = new GridBagLayout();
		gbl_enemy5Panel.columnWidths = new int[]{92, 0};
		gbl_enemy5Panel.rowHeights = new int[]{22, 17, 98, 17, 0};
		gbl_enemy5Panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_enemy5Panel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		enemy5Panel.setLayout(gbl_enemy5Panel);

		enemy5SkillBar = new JProgressBar();
		enemy5SkillBar.setFocusable(false);
		enemy5SkillBar.setForeground(new Color(153, 0, 51));
		enemy5SkillBar.setStringPainted(true);
		enemy5SkillBar.setVisible(false);

		enemy5PVBar = new JProgressBar();
		enemy5PVBar.setFocusable(false);
		enemy5PVBar.setForeground(new Color(102, 51, 102));
		enemy5PVBar.setVisible(false);

		enemy5NameLabel = new JLabel(""); //$NON-NLS-1$
		enemy5NameLabel.setFocusable(false);
		enemy5NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_enemy5NameLabel = new GridBagConstraints();
		gbc_enemy5NameLabel.fill = GridBagConstraints.BOTH;
		gbc_enemy5NameLabel.insets = new Insets(0, 0, 5, 0);
		gbc_enemy5NameLabel.gridx = 0;
		gbc_enemy5NameLabel.gridy = 0;
		enemy5Panel.add(enemy5NameLabel, gbc_enemy5NameLabel);
		enemy5PVBar.setStringPainted(true);
		GridBagConstraints gbc_enemy5PVBar = new GridBagConstraints();
		gbc_enemy5PVBar.fill = GridBagConstraints.BOTH;
		gbc_enemy5PVBar.insets = new Insets(0, 0, 5, 0);
		gbc_enemy5PVBar.gridx = 0;
		gbc_enemy5PVBar.gridy = 1;
		enemy5Panel.add(enemy5PVBar, gbc_enemy5PVBar);

		enemy5Icon = new JLabel(""); //$NON-NLS-1$
		enemy5Icon.setFocusable(false);
		GridBagConstraints gbc_enemy5Icon = new GridBagConstraints();
		gbc_enemy5Icon.fill = GridBagConstraints.BOTH;
		gbc_enemy5Icon.insets = new Insets(0, 0, 5, 0);
		gbc_enemy5Icon.gridx = 0;
		gbc_enemy5Icon.gridy = 2;
		enemy5Panel.add(enemy5Icon, gbc_enemy5Icon);
		enemy5Icon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				borderReset();
				enemy5Icon.setBorder(new LineBorder ( new Color (255, 0, 0), 2));
				selectedEnemy = enemigos.get(4);
			}
		});
		enemy5Icon.setVisible(false);
		enemy5Icon.setPreferredSize(new Dimension(98, 98));
		enemy5Icon.setHorizontalAlignment(SwingConstants.CENTER);
		enemy5Icon.setBorder(new LineBorder(new Color(192, 192, 192)));
		GridBagConstraints gbc_enemy5SkillBar = new GridBagConstraints();
		gbc_enemy5SkillBar.fill = GridBagConstraints.BOTH;
		gbc_enemy5SkillBar.gridx = 0;
		gbc_enemy5SkillBar.gridy = 3;
		enemy5Panel.add(enemy5SkillBar, gbc_enemy5SkillBar);

		enemy6Panel = new JPanel();
		enemy6Panel.setFocusable(false);
		GridBagConstraints gbc_enemy6Panel = new GridBagConstraints();
		gbc_enemy6Panel.fill = GridBagConstraints.BOTH;
		gbc_enemy6Panel.gridx = 5;
		gbc_enemy6Panel.gridy = 0;
		enemiesPanel.add(enemy6Panel, gbc_enemy6Panel);
		enemy6Panel.setVisible(false);
		enemy6Panel.setPreferredSize(new Dimension(98, 164));
		GridBagLayout gbl_enemy6Panel = new GridBagLayout();
		gbl_enemy6Panel.columnWidths = new int[]{92, 0};
		gbl_enemy6Panel.rowHeights = new int[]{22, 17, 98, 17, 0};
		gbl_enemy6Panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_enemy6Panel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		enemy6Panel.setLayout(gbl_enemy6Panel);

		enemy6NameLabel = new JLabel(""); //$NON-NLS-1$
		enemy6NameLabel.setFocusable(false);
		enemy6NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_enemy6NameLabel = new GridBagConstraints();
		gbc_enemy6NameLabel.fill = GridBagConstraints.BOTH;
		gbc_enemy6NameLabel.insets = new Insets(0, 0, 5, 0);
		gbc_enemy6NameLabel.gridx = 0;
		gbc_enemy6NameLabel.gridy = 0;
		enemy6Panel.add(enemy6NameLabel, gbc_enemy6NameLabel);

		enemy6PVBar = new JProgressBar();
		enemy6PVBar.setFocusable(false);
		enemy6PVBar.setForeground(new Color(102, 51, 102));
		enemy6PVBar.setVisible(false);
		enemy6PVBar.setStringPainted(true);
		GridBagConstraints gbc_enemy6PVBar = new GridBagConstraints();
		gbc_enemy6PVBar.fill = GridBagConstraints.BOTH;
		gbc_enemy6PVBar.insets = new Insets(0, 0, 5, 0);
		gbc_enemy6PVBar.gridx = 0;
		gbc_enemy6PVBar.gridy = 1;
		enemy6Panel.add(enemy6PVBar, gbc_enemy6PVBar);

		enemy6Icon = new JLabel(""); //$NON-NLS-1$
		enemy6Icon.setFocusable(false);
		GridBagConstraints gbc_enemy6Icon = new GridBagConstraints();
		gbc_enemy6Icon.fill = GridBagConstraints.BOTH;
		gbc_enemy6Icon.insets = new Insets(0, 0, 5, 0);
		gbc_enemy6Icon.gridx = 0;
		gbc_enemy6Icon.gridy = 2;
		enemy6Panel.add(enemy6Icon, gbc_enemy6Icon);
		enemy6Icon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				borderReset();
				enemy6Icon.setBorder(new LineBorder ( new Color (255, 0, 0), 2));
				selectedEnemy = enemigos.get(5);
			}
		});
		enemy6Icon.setVisible(false);
		enemy6Icon.setPreferredSize(new Dimension(98, 98));
		enemy6Icon.setHorizontalAlignment(SwingConstants.CENTER);
		enemy6Icon.setBorder(new LineBorder(new Color(192, 192, 192)));

		enemy6SkillBar = new JProgressBar();
		enemy6SkillBar.setFocusable(false);
		enemy6SkillBar.setForeground(new Color(153, 0, 51));
		enemy6SkillBar.setStringPainted(true);
		enemy6SkillBar.setVisible(false);
		GridBagConstraints gbc_enemy6SkillBar = new GridBagConstraints();
		gbc_enemy6SkillBar.fill = GridBagConstraints.BOTH;
		gbc_enemy6SkillBar.gridx = 0;
		gbc_enemy6SkillBar.gridy = 3;
		enemy6Panel.add(enemy6SkillBar, gbc_enemy6SkillBar);

		JPanel eastPanel = new JPanel();
		eastPanel.setFocusable(false);
		eastPanel.setBorder(new LineBorder(new Color(192, 192, 192)));
		eastPanel.setPreferredSize(new Dimension(300, 720));
		eastPanel.setBounds(new Rectangle(0, 0, 200, 720));
		GridBagConstraints gbc_eastPanel = new GridBagConstraints();
		gbc_eastPanel.fill = GridBagConstraints.BOTH;
		gbc_eastPanel.insets = new Insets(0, 0, 5, 0);
		gbc_eastPanel.gridx = 2;
		gbc_eastPanel.gridy = 1;
		gamePanel.add(eastPanel, gbc_eastPanel);
		GridBagLayout gbl_eastPanel = new GridBagLayout();
		gbl_eastPanel.columnWidths = new int[]{289, 0};
		gbl_eastPanel.rowHeights = new int[]{142, 279, 0};
		gbl_eastPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_eastPanel.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		eastPanel.setLayout(gbl_eastPanel);

		JPanel mapPanel = new JPanel();
		mapPanel.setFocusable(false);
		GridBagConstraints gbc_mapPanel = new GridBagConstraints();
		gbc_mapPanel.insets = new Insets(0, 0, 5, 0);
		gbc_mapPanel.gridx = 0;
		gbc_mapPanel.gridy = 0;
		eastPanel.add(mapPanel, gbc_mapPanel);
		mapPanel.setBorder(new LineBorder(new Color(192, 192, 192)));
		GridBagLayout gbl_mapPanel = new GridBagLayout();
		gbl_mapPanel.columnWidths = new int[]{288, 0};
		gbl_mapPanel.rowHeights = new int[]{25, 120, 0};
		gbl_mapPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_mapPanel.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		mapPanel.setLayout(gbl_mapPanel);

		levelLabel = new JLabel(""); //$NON-NLS-1$
		levelLabel.setFocusable(false);
		levelLabel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(192, 192, 192)));
		levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_levelLabel = new GridBagConstraints();
		gbc_levelLabel.insets = new Insets(0, 0, 5, 0);
		gbc_levelLabel.gridx = 0;
		gbc_levelLabel.gridy = 0;
		mapPanel.add(levelLabel, gbc_levelLabel);

		mapLabel = new JLabel();
		mapLabel.setFocusable(false);
		mapLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_mapLabel = new GridBagConstraints();
		gbc_mapLabel.fill = GridBagConstraints.BOTH;
		gbc_mapLabel.gridx = 0;
		gbc_mapLabel.gridy = 1;
		mapPanel.add(mapLabel, gbc_mapLabel);

		JPanel playerPanel = new JPanel();
		playerPanel.setFocusable(false);
		GridBagConstraints gbc_playerPanel = new GridBagConstraints();
		gbc_playerPanel.gridx = 0;
		gbc_playerPanel.gridy = 1;
		eastPanel.add(playerPanel, gbc_playerPanel);
		GridBagLayout gbl_playerPanel = new GridBagLayout();
		gbl_playerPanel.columnWidths = new int[]{281, 0};
		gbl_playerPanel.rowHeights = new int[]{48, 48, 131, 0};
		gbl_playerPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_playerPanel.rowWeights = new double[]{1.0, 1.0, 0.0, Double.MIN_VALUE};
		playerPanel.setLayout(gbl_playerPanel);

		exitButton = new JButton(Messages.getString("Game.SaveExit")); //$NON-NLS-1$
		exitButton.setFocusable(false);
		exitButton.setToolTipText(Messages.getString("Game.exitButton.toolTipText")); //$NON-NLS-1$
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {				
				dispose();
			}
		});
		GridBagConstraints gbc_exitButton = new GridBagConstraints();
		gbc_exitButton.fill = GridBagConstraints.BOTH;
		gbc_exitButton.insets = new Insets(0, 0, 5, 0);
		gbc_exitButton.gridx = 0;
		gbc_exitButton.gridy = 0;
		playerPanel.add(exitButton, gbc_exitButton);

		nextRoomButton = new JButton(Messages.getString("Game.NextRoom")); //$NON-NLS-1$
		nextRoomButton.setFocusable(false);
		nextRoomButton.setToolTipText(Messages.getString("Game.nextRoomButton.toolTipText")); //$NON-NLS-1$
		nextRoomButton.setEnabled(false);
		GridBagConstraints gbc_nextRoomButton = new GridBagConstraints();
		gbc_nextRoomButton.fill = GridBagConstraints.BOTH;
		gbc_nextRoomButton.insets = new Insets(0, 0, 5, 0);
		gbc_nextRoomButton.gridx = 0;
		gbc_nextRoomButton.gridy = 1;
		playerPanel.add(nextRoomButton, gbc_nextRoomButton);

		nextRoomButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if ( !enemigoSeleccionado() || dead ){
					if ( heroe.getSala() < mazmorra.size() ){
						salaActual = mazmorra.get(heroe.getSala());
						combat = new Combate(heroe, mazmorra.get(heroe.getSala()));
						enemigos = mazmorra.get(heroe.getSala()).getEnemigos();
						elegirSiguienteEnemigo();
						nextRoomButton.setEnabled(false);
						atkButton.setEnabled(true);
						defenseButton.setEnabled(true);
						updateHeroe();
						updateGamePanel();
						drawEnemies();
					}				
					if( heroe.getSala() == mazmorra.size() - 1 ){
						bossFinal = true;
					}
				}
			}
		});

		if( heroe.getSala() == mazmorra.size() - 1 ){
			bossFinal = true;
		}
		
		JPanel equipoPanel = new JPanel();
		equipoPanel.setFocusable(false);
		GridBagConstraints gbc_equipoPanel = new GridBagConstraints();
		gbc_equipoPanel.fill = GridBagConstraints.BOTH;
		gbc_equipoPanel.gridx = 0;
		gbc_equipoPanel.gridy = 2;
		playerPanel.add(equipoPanel, gbc_equipoPanel);
		GridBagLayout gbl_equipoPanel = new GridBagLayout();
		gbl_equipoPanel.columnWidths = new int[]{79, 83, 79, 0};
		gbl_equipoPanel.rowHeights = new int[]{16, 85, 0};
		gbl_equipoPanel.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_equipoPanel.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		equipoPanel.setLayout(gbl_equipoPanel);

		JLabel equipmentLabel = new JLabel(Messages.getString("Game.Equipment")); //$NON-NLS-1$
		equipmentLabel.setFocusable(false);
		equipmentLabel.setToolTipText(Messages.getString("Game.equipmentLabel.toolTipText")); //$NON-NLS-1$
		GridBagConstraints gbc_equipmentLabel = new GridBagConstraints();
		gbc_equipmentLabel.fill = GridBagConstraints.BOTH;
		gbc_equipmentLabel.insets = new Insets(0, 0, 5, 0);
		gbc_equipmentLabel.gridwidth = 3;
		gbc_equipmentLabel.gridx = 0;
		gbc_equipmentLabel.gridy = 0;
		equipoPanel.add(equipmentLabel, gbc_equipmentLabel);
		equipmentLabel.setBorder(new LineBorder(new Color(192, 192, 192)));
		equipmentLabel.setHorizontalAlignment(SwingConstants.CENTER);

		weaponIcon = new JLabel();
		weaponIcon.setFocusable(false);
		weaponIcon.setPreferredSize(new Dimension(85, 85));
		GridBagConstraints gbc_weaponIcon = new GridBagConstraints();
		gbc_weaponIcon.insets = new Insets(0, 0, 0, 5);
		gbc_weaponIcon.fill = GridBagConstraints.BOTH;
		gbc_weaponIcon.gridx = 0;
		gbc_weaponIcon.gridy = 1;
		equipoPanel.add(weaponIcon, gbc_weaponIcon);
		weaponIcon.setBorder(new LineBorder(new Color(192, 192, 192)));

		armorIcon = new JLabel();
		armorIcon.setFocusable(false);
		armorIcon.setPreferredSize(new Dimension(90, 85));
		GridBagConstraints gbc_armorIcon = new GridBagConstraints();
		gbc_armorIcon.fill = GridBagConstraints.BOTH;
		gbc_armorIcon.insets = new Insets(0, 0, 0, 5);
		gbc_armorIcon.gridx = 1;
		gbc_armorIcon.gridy = 1;
		equipoPanel.add(armorIcon, gbc_armorIcon);
		armorIcon.setBorder(new LineBorder(new Color(192, 192, 192)));

		shieldIcon = new JLabel();
		shieldIcon.setFocusable(false);
		shieldIcon.setPreferredSize(new Dimension(85, 85));
		shieldIcon.setBorder(new LineBorder(new Color(192, 192, 192)));
		GridBagConstraints gbc_shieldIcon = new GridBagConstraints();
		gbc_shieldIcon.fill = GridBagConstraints.BOTH;
		gbc_shieldIcon.gridx = 2;
		gbc_shieldIcon.gridy = 1;
		equipoPanel.add(shieldIcon, gbc_shieldIcon);

		JPanel southPanel = new JPanel();
		southPanel.setFocusable(false);
		southPanel.setBorder(new LineBorder(new Color(192, 192, 192)));
		southPanel.setPreferredSize(new Dimension(1280, 100));
		GridBagConstraints gbc_southPanel = new GridBagConstraints();
		gbc_southPanel.fill = GridBagConstraints.BOTH;
		gbc_southPanel.gridwidth = 3;
		gbc_southPanel.gridx = 0;
		gbc_southPanel.gridy = 2;
		gamePanel.add(southPanel, gbc_southPanel);
		GridBagLayout gbl_southPanel = new GridBagLayout();
		gbl_southPanel.columnWidths = new int[]{1244, 0};
		gbl_southPanel.rowHeights = new int[]{85, 0};
		gbl_southPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_southPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		southPanel.setLayout(gbl_southPanel);

		GridBagConstraints gbc_eventosScrollPane = new GridBagConstraints();
		gbc_eventosScrollPane.fill = GridBagConstraints.BOTH;
		gbc_eventosScrollPane.gridx = 0;
		gbc_eventosScrollPane.gridy = 0;		
		eventosScrollPane = new JScrollPane();
		eventosScrollPane.setAutoscrolls(true);
		eventosScrollPane.setFocusable(false);
		eventosScrollPane.setBorder(new LineBorder(new Color(255, 165, 0)));
		southPanel.add(eventosScrollPane, gbc_eventosScrollPane);

		listaEventos = new JList<String>();
		listaEventos.setToolTipText(Messages.getString("Game.listaEventos.toolTipText")); //$NON-NLS-1$
		listaEventos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaEventos.setFont(new Font("Monospaced", Font.BOLD, 12)); //$NON-NLS-1$
		listaEventos.setFocusable(false);
		listaEventos.setEnabled(false);
		listaEventos.setBackground(new Color(255, 250, 205));
		eventosScrollPane.setViewportView(listaEventos);

		updateGamePanel();
		updateUserList();
		drawEnemies();
		elegirSiguienteEnemigo();
		creoDisparadoresHabilidadesPociones();
		rellenarHabilidades();
		rellenarPociones();
		rellenarHabilidadesPocionLabels();
		inicializoHotKeys();
	}

	private void inicializoHotKeys() {
		chatTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if ( e.isAltDown() && e.getKeyCode() == KeyEvent.VK_A){					
					if ( enemigoSeleccionado() && !dead ){
						escribirEnListaEventos(combat.atacarConArma(selectedEnemy, enemigos));
						if ( selectedEnemy.getPV() <= 0 && enemigos.size() != 0){
							elegirSiguienteEnemigo();
						}
						if ( enemigos.size() != 0 ){
							turnoEnemigos(1);
						}else{
							siguienteSala();						
						}				
						endTurn();
						cleanEnemiesPanel();
						updateGamePanel();
					}
				}else if ( e.isAltDown() && e.getKeyCode() == KeyEvent.VK_D){
					if ( enemigoSeleccionado() && !dead ){				
						turnoEnemigos(4);
						endTurn();
						updateGamePanel();
					}
				}else if ( e.isAltDown() && e.getKeyCode() == KeyEvent.VK_S){
					dispose();
				}else if ( e.isAltDown() && e.getKeyCode() == KeyEvent.VK_N){
					if ( !enemigoSeleccionado() || dead ){
						if ( heroe.getSala() < mazmorra.size() ){
							salaActual = mazmorra.get(heroe.getSala());
							combat = new Combate(heroe, mazmorra.get(heroe.getSala()));
							enemigos = mazmorra.get(heroe.getSala()).getEnemigos();
							elegirSiguienteEnemigo();
							nextRoomButton.setEnabled(false);
							atkButton.setEnabled(true);
							defenseButton.setEnabled(true);
							updateHeroe();
							updateGamePanel();
							drawEnemies();
						}				
						if( heroe.getSala() == mazmorra.size() - 1 ){
							bossFinal = true;
						}
					}
				}else if ( e.isAltDown() && e.getKeyCode() == KeyEvent.VK_1){
					if ( enemigoSeleccionado() && labelActivo(torbellinoLabel) && !dead ){
						habilidad = habilidadesAlmacenadas.get(0);
						torbellinoLabel.setEnabled(false);
						usarHabilidad();
					}
				}
				else if ( e.isAltDown() && e.getKeyCode() == KeyEvent.VK_2){
					if ( enemigoSeleccionado() && labelActivo(golpeConcentradoLabel) && !dead ){
						habilidad = habilidadesAlmacenadas.get(1);
						golpeConcentradoLabel.setEnabled(false);
						usarHabilidad();
					}
				}
				else if ( e.isAltDown() && e.getKeyCode() == KeyEvent.VK_3){
					if ( enemigoSeleccionado() && labelActivo(curarLabel) && !dead ){
						habilidad = habilidadesAlmacenadas.get(2);
						curarLabel.setEnabled(false);
						usarHabilidad();
					}
				}
				else if ( e.isAltDown() && e.getKeyCode() == KeyEvent.VK_4){
					if ( enemigoSeleccionado() && labelActivo(llamaradaLabel) && !dead ){
						habilidad = habilidadesAlmacenadas.get(3);
						llamaradaLabel.setEnabled(false);
						usarHabilidad();
					}
				}else if ( e.isAltDown() && e.getKeyCode() == KeyEvent.VK_F1){
					if ( enemigoSeleccionado() && labelActivo(pocionCurativaLabel) && !dead ){
						pocion = pocionesAlmacenadas.get(0);
						pocionCurativaLabel.setEnabled(false);
						lanzarPocion();
					}
				}
				else if ( e.isAltDown() && e.getKeyCode() == KeyEvent.VK_F2){
					if ( enemigoSeleccionado() && labelActivo(pocionManaLabel) && !dead ){
						pocion = pocionesAlmacenadas.get(1);
						pocionManaLabel.setEnabled(false);
						lanzarPocion();
					}
				}
				else if ( e.isAltDown() && e.getKeyCode() == KeyEvent.VK_F3){
					if ( enemigoSeleccionado() && labelActivo(pocionExplosivaLabel) && !dead ){
						pocion = pocionesAlmacenadas.get(2);
						pocionExplosivaLabel.setEnabled(false);
						lanzarPocion();
					}
				}
			}
		});
	}

	protected boolean enemigoSeleccionado() {
		if ( selectedEnemy == null ){
			return false;
		}
		return true;
	}

	private void rellenarHabilidadesPocionLabels() {
		listaHabilidadesLabel.add(torbellinoLabel);
		listaHabilidadesLabel.add(golpeConcentradoLabel);	
		listaHabilidadesLabel.add(curarLabel);	
		listaHabilidadesLabel.add(llamaradaLabel);	
		listaPocionesLabel.add(pocionCurativaLabel);	
		listaPocionesLabel.add(pocionManaLabel);	
		listaPocionesLabel.add(pocionExplosivaLabel);
	}

	private void creoDisparadoresHabilidadesPociones() {
		torbellinoLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if ( enemigoSeleccionado() && labelActivo(torbellinoLabel) && !dead ){
					habilidad = habilidadesAlmacenadas.get(0);
					torbellinoLabel.setEnabled(false);
					usarHabilidad();
				}
			}
		});
		golpeConcentradoLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if ( enemigoSeleccionado() && labelActivo(golpeConcentradoLabel) && !dead ){
					habilidad = habilidadesAlmacenadas.get(1);
					golpeConcentradoLabel.setEnabled(false);
					usarHabilidad();
				}
			}
		});
		curarLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if ( enemigoSeleccionado() && labelActivo(curarLabel) && !dead ){
					habilidad = habilidadesAlmacenadas.get(2);
					curarLabel.setEnabled(false);
					usarHabilidad();
				}
			}
		});
		llamaradaLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if ( enemigoSeleccionado() && labelActivo(llamaradaLabel) && !dead ){
					habilidad = habilidadesAlmacenadas.get(3);
					llamaradaLabel.setEnabled(false);
					usarHabilidad();
				}
			}
		});
		pocionCurativaLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if ( enemigoSeleccionado() && labelActivo(pocionCurativaLabel) && !dead ){
					pocion = pocionesAlmacenadas.get(0);
					pocionCurativaLabel.setEnabled(false);
					lanzarPocion();
				}
			}
		});
		pocionManaLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if ( enemigoSeleccionado() && labelActivo(pocionManaLabel) && !dead ){
					pocion = pocionesAlmacenadas.get(1);
					pocionManaLabel.setEnabled(false);
					lanzarPocion();
				}
			}
		});
		pocionExplosivaLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if ( enemigoSeleccionado() && labelActivo(pocionExplosivaLabel) && !dead ){
					pocion = pocionesAlmacenadas.get(2);
					pocionExplosivaLabel.setEnabled(false);
					lanzarPocion();
				}
			}
		});
	}

	protected boolean labelActivo(JLabel habilidadLabel) {
		return habilidadLabel.isEnabled();
	}

	private void rellenarPociones() {
		pocionesAlmacenadas.add(new Pocion_Curativa());
		pocionesAlmacenadas.add(new Pocion_Mana());
		pocionesAlmacenadas.add(new Pocion_Explosiva());
	}

	private void rellenarHabilidades() {
		habilidadesAlmacenadas.add(new Torbellino());
		habilidadesAlmacenadas.add(new Golpe_Concentrado());
		habilidadesAlmacenadas.add(new Curar());
		habilidadesAlmacenadas.add(new Llamarada());		
	}

	private void escribirEnListaEventos(ArrayList<String> eventos) {
		if ( almacenEventos.size() > 50 ){
			for ( int i = 0; i < eventos.size(); i++){
				almacenEventos.remove(0);
			}
		}
		almacenEventos.addAll(eventos);
		listaEventos.setModel(miModeloLista(almacenEventos));
		eventosScrollPane.getVerticalScrollBar().setValue(eventosScrollPane.getVerticalScrollBar().getMaximum());
	}

	private void updateHeroe() {
		EntityTransaction et = em.getTransaction();
		et.begin();
		gh.update(heroe);
		et.commit();
	}

	private void desconectarYSalir() {
		EntityTransaction et = em.getTransaction();
		et.begin();
		usuarioActual.setJugando(false);		
		gu.update(usuarioActual);
		updateUserList();
		Date date = new Date();
		Chat chat;
		for(Usuario jugador : usuariosJugando ){
			chat = new Chat(jugador, "** " + usuarioActual.getNombre() + Messages.getString("Game.UserLogOut"), date, false); //$NON-NLS-1$ //$NON-NLS-2$
			gc.insert(chat);
		}
		et.commit();
		chatRefreshTimer.stop();
	}

	private void endTurn() {
		controloHabilidades();
		controloPociones();
		controloEnemigos();
		eventoJefeFinal();
	}

	private void eventoJefeFinal() {
		if ( bossFinal ){
			Cacique_1 boss1 = null;
			Cacique_2 boss2 = null;
			Cacique_3 boss3 = null;
			
			for( Enemigo enemigo : enemigos ){
				if ( enemigo instanceof Cacique_1 ){
					boss1 = (Cacique_1) enemigo;

				}

				if ( enemigo instanceof Cacique_2 ){
					boss2 = (Cacique_2) enemigo;
				}
				
				if ( enemigo instanceof Cacique_3 ){
					boss3 = (Cacique_3) enemigo;
				}
			}

			if ( boss1 != null ){
				if ( boss1.getPV() < boss1.getPVMax()*0.2 ){
					heroe.setExperiencia(heroe.getExperienciaMax());
					JOptionPane.showMessageDialog(this, boss1.segundaFase());
					enemigos.remove(boss1);
					enemigos.add(0, new Cacique_2());
					elegirSiguienteEnemigo();
				}
			}

			if ( boss2 != null ){
				if ( boss2.getPV() < boss2.getPVMax()*0.2 ){
					heroe.setExperiencia(heroe.getExperienciaMax());
					JOptionPane.showMessageDialog(this, boss2.terceraFase());
					enemigos.clear();
					enemigos.add(new Cacique_3(heroe, enemigos, game));
					elegirSiguienteEnemigo();
				}
			}
			
			if ( boss3 != null ){
				if ( boss3.getPV() == 1 && !juegoTerminado ){
					Final f = new Final(this);
					f.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);					
				}
			}
		}
	}

	private void controloEnemigos() {
		for( Enemigo enemigo : enemigos ){
			enemigo.setSkillCount(enemigo.getSkillCount() + 1);
		}
	}

	private void controloPociones() {
		for ( Pocion pocion : pocionesAlmacenadas ){
			if ( pocion.getCooldown() > 0){
				pocion.setCooldown(pocion.getCooldown() - 1);
			}

			if ( pocion.getCooldown() > 0){
				int posicionPocion = pocionesAlmacenadas.indexOf(pocion);
				listaPocionesLabel.get(posicionPocion).setIcon(new ImageIcon(Body.class.getResource("/imagenes/contador/" + pocion.getCooldown() + ".png")));				 //$NON-NLS-1$ //$NON-NLS-2$
			}else{
				int posicionPocion = pocionesAlmacenadas.indexOf(pocion);
				listaPocionesLabel.get(posicionPocion).setEnabled(true);
				listaPocionesLabel.get(posicionPocion).setIcon(pocion.icon());
			}
		}
	}

	private void controloHabilidades() {
		for ( Habilidad habilidad : habilidadesAlmacenadas ){
			if ( habilidad.getCooldown() > 0){
				habilidad.setCooldown(habilidad.getCooldown() - 1);

			}

			if ( habilidad.getCooldown() > 0){
				int posicionHabilidad = habilidadesAlmacenadas.indexOf(habilidad);
				listaHabilidadesLabel.get(posicionHabilidad).setIcon(new ImageIcon(Body.class.getResource("/imagenes/contador/" + habilidad.getCooldown() + ".png")));				 //$NON-NLS-1$ //$NON-NLS-2$
			}else{
				int posicionHabilidad = habilidadesAlmacenadas.indexOf(habilidad);
				listaHabilidadesLabel.get(posicionHabilidad).setEnabled(true);
				listaHabilidadesLabel.get(posicionHabilidad).setIcon(habilidad.icon());
			}
		}
	}

	private void cleanEnemiesPanel() {
		enemy1Panel.setVisible(false);
		enemy2Panel.setVisible(false);	
		enemy3Panel.setVisible(false);	
		enemy4Panel.setVisible(false);	
		enemy5Panel.setVisible(false);	
		enemy6Panel.setVisible(false);
	}

	private void borderReset() {
		enemy1Icon.setBorder(new LineBorder(new Color(192, 192, 192)));
		enemy2Icon.setBorder(new LineBorder(new Color(192, 192, 192)));
		enemy3Icon.setBorder(new LineBorder(new Color(192, 192, 192)));
		enemy4Icon.setBorder(new LineBorder(new Color(192, 192, 192)));
		enemy5Icon.setBorder(new LineBorder(new Color(192, 192, 192)));
		enemy6Icon.setBorder(new LineBorder(new Color(192, 192, 192)));
	}

	private void updateGamePanel() {
		if ( !dead){
			pintoEnemigos();
			pintoEquipamiento();
			pintoVidaHeroe();
			pintoMapa();
			pintoEstadisticas();			
		}
		else{
			heroPVBar.setValue(0);
			heroPVBar.setString("DEAD"); //$NON-NLS-1$
			mapLabel.setIcon(new ImageIcon(Body.class.getResource("/imagenes/varios/muerte.png"))); //$NON-NLS-1$
			drawEnemies();
		}
	}

	private void pintoEstadisticas() {
		nameValueLabel.setText(heroe.getNombre());
		levelValueLabel.setText(String.valueOf(heroe.getNivel()));
		expValueLabel.setText(String.valueOf(heroe.getExperiencia() + "/" + String.valueOf(heroe.getExperienciaMax()))); //$NON-NLS-1$
		strValueLabel.setText(String.valueOf(heroe.getFuerza()));
		dexValueLabel.setText(String.valueOf(heroe.getDestreza()));
		intValueLabel.setText(String.valueOf(heroe.getInteligencia()));
		combatValueLabel.setText(String.valueOf(heroe.getDestreza()));
		damageValueLabel.setText(String.valueOf(arma.dano() + heroe.getFuerza()));
		atkValueLabel.setText(String.valueOf(heroe.getFuerza()));
		defValueLabel.setText(String.valueOf( escudo.bonusDef() + armadura.bonusArmor() + heroe.getDestreza() / 2 ));
		powerValueLabel.setText(String.valueOf(heroe.getInteligencia()));
	}

	private void pintoMapa() {
		levelLabel.setText(Messages.getString("Game.Sala") + salaActual.nombre()); //$NON-NLS-1$
		mapLabel.setIcon(salaActual.getIcon());
	}

	private void pintoVidaHeroe() {
		if ( heroe.getPV() > heroe.getPVMax()*0.7 ){
			heroPVBar.setForeground(new Color(60, 179, 113));
		}else if (  heroe.getPV() > heroe.getPVMax()*0.4 ){
			heroPVBar.setForeground(new Color(204, 204, 51));
		}else{
			heroPVBar.setForeground(new Color(204, 51, 102));
		}
		heroPVBar.setMaximum(heroe.getPVMax());
		heroPVBar.setValue(heroe.getPV());
		heroPVBar.setString(heroe.getPV() + "/" + heroPVBar.getMaximum()); //$NON-NLS-1$
	}

	private void pintoEnemigos() {
		enemigos = salaActual.getEnemigos();
		drawEnemies();
	}

	private void pintoEquipamiento() {
		arma = Arma.creaAPartirDeTipoBBDD(heroe.getArma());
		armadura = Armadura.creaAPartirDeTipoBBDD(heroe.getArmadura());
		escudo = Escudo.creaAPartirDeTipoBBDD(heroe.getEscudo());
		weaponIcon.setIcon(arma.icon());
		armorIcon.setIcon(armadura.icon());
		shieldIcon.setIcon(escudo.icon());
	}

	private void drawEnemies() {
		switch(enemigos.size()){
		case 6: 
			enemy6Icon.setVisible(true);
			enemy6Panel.setVisible(true);
			enemy6PVBar.setVisible(true);
			enemy6PVBar.setMaximum(enemigos.get(5).getPVMax());
			enemy6PVBar.setValue(enemigos.get(5).getPV());
			enemy6PVBar.setString(enemigos.get(5).getPV() + "/" + enemy6PVBar.getMaximum()); //$NON-NLS-1$
			enemy6SkillBar.setVisible(true);
			enemy6SkillBar.setMaximum(enemigos.get(5).getSkillCountMax() - 1);
			enemy6SkillBar.setValue(enemigos.get(5).getSkillCount());
			enemy6SkillBar.setString(enemigos.get(5).getSkillCount() + "/" + enemy6SkillBar.getMaximum());	 //$NON-NLS-1$
			enemy6Icon.setIcon(enemigos.get(5).getIcon());
			enemy6NameLabel.setText(enemigos.get(5).getNombre());
			enemy6Icon.setToolTipText(enemigos.get(5).toTipString());
		case 5:
			enemy5Icon.setVisible(true);
			enemy5Panel.setVisible(true);
			enemy5PVBar.setVisible(true);
			enemy5PVBar.setMaximum(enemigos.get(4).getPVMax());
			enemy5PVBar.setValue(enemigos.get(4).getPV());
			enemy5PVBar.setString(enemigos.get(4).getPV() + "/" + enemy5PVBar.getMaximum()); //$NON-NLS-1$
			enemy5SkillBar.setVisible(true);
			enemy5SkillBar.setMaximum(enemigos.get(4).getSkillCountMax() - 1);
			enemy5SkillBar.setValue(enemigos.get(4).getSkillCount());
			enemy5SkillBar.setString(enemigos.get(4).getSkillCount() + "/" + enemy5SkillBar.getMaximum());	 //$NON-NLS-1$
			enemy5Icon.setIcon(enemigos.get(4).getIcon());
			enemy5NameLabel.setText(enemigos.get(4).getNombre());
			enemy5Icon.setToolTipText(enemigos.get(4).toTipString());
		case 4:
			enemy4Icon.setVisible(true);
			enemy4Panel.setVisible(true);
			enemy4PVBar.setVisible(true);
			enemy4PVBar.setMaximum(enemigos.get(3).getPVMax());
			enemy4PVBar.setValue(enemigos.get(3).getPV());
			enemy4PVBar.setString(enemigos.get(3).getPV() + "/" + enemy4PVBar.getMaximum()); //$NON-NLS-1$
			enemy4SkillBar.setVisible(true);
			enemy4SkillBar.setMaximum(enemigos.get(3).getSkillCountMax() - 1);
			enemy4SkillBar.setValue(enemigos.get(3).getSkillCount());
			enemy4SkillBar.setString(enemigos.get(3).getSkillCount() + "/" + enemy4SkillBar.getMaximum());	 //$NON-NLS-1$
			enemy4Icon.setIcon(enemigos.get(3).getIcon());
			enemy4NameLabel.setText(enemigos.get(3).getNombre());
			enemy4Icon.setToolTipText(enemigos.get(3).toTipString());
		case 3:
			enemy3Icon.setVisible(true);
			enemy3Panel.setVisible(true);
			enemy3PVBar.setVisible(true);
			enemy3PVBar.setMaximum(enemigos.get(2).getPVMax());
			enemy3PVBar.setValue(enemigos.get(2).getPV());
			enemy3PVBar.setString(enemigos.get(2).getPV() + "/" + enemy3PVBar.getMaximum()); //$NON-NLS-1$
			enemy3SkillBar.setVisible(true);			
			enemy3SkillBar.setMaximum(enemigos.get(2).getSkillCountMax() - 1);
			enemy3SkillBar.setValue(enemigos.get(2).getSkillCount());
			enemy3SkillBar.setString(enemigos.get(2).getSkillCount() + "/" + enemy3SkillBar.getMaximum());	 //$NON-NLS-1$
			enemy3Icon.setIcon(enemigos.get(2).getIcon());
			enemy3NameLabel.setText(enemigos.get(2).getNombre());
			enemy3Icon.setToolTipText(enemigos.get(2).toTipString());
		case 2:
			enemy2NameLabel.setText(enemigos.get(1).getNombre());
			enemy2Panel.setVisible(true);
			enemy2PVBar.setVisible(true);
			enemy2PVBar.setMaximum(enemigos.get(1).getPVMax());
			enemy2PVBar.setValue(enemigos.get(1).getPV());
			enemy2PVBar.setString(enemigos.get(1).getPV() + "/" + enemy2PVBar.getMaximum()); //$NON-NLS-1$
			enemy2Icon.setVisible(true);
			enemy2SkillBar.setVisible(true);
			enemy2SkillBar.setMaximum(enemigos.get(1).getSkillCountMax() - 1);
			enemy2SkillBar.setValue(enemigos.get(1).getSkillCount());
			enemy2SkillBar.setString(enemigos.get(1).getSkillCount() + "/" + enemy2SkillBar.getMaximum());	 //$NON-NLS-1$
			enemy2Icon.setIcon(enemigos.get(1).getIcon());
			enemy2Icon.setToolTipText(enemigos.get(1).toTipString());

		case 1:
			enemy1Panel.setVisible(true);
			enemy1PVBar.setVisible(true);
			enemy1PVBar.setMaximum(enemigos.get(0).getPVMax());
			enemy1PVBar.setValue(enemigos.get(0).getPV());
			enemy1PVBar.setString(enemigos.get(0).getPV() + "/" + enemy1PVBar.getMaximum()); //$NON-NLS-1$
			enemy1NameLabel.setText(enemigos.get(0).getNombre());
			enemy1Icon.setVisible(true);
			enemy1SkillBar.setVisible(true);
			enemy1SkillBar.setMaximum(enemigos.get(0).getSkillCountMax() - 1);
			enemy1SkillBar.setValue(enemigos.get(0).getSkillCount());
			enemy1SkillBar.setString(enemigos.get(0).getSkillCount() + "/" + enemy1SkillBar.getMaximum());			 //$NON-NLS-1$
			enemy1Icon.setIcon(enemigos.get(0).getIcon());
			enemy1Icon.setToolTipText(enemigos.get(0).toTipString());
		}
	}

	private void updateUserList() {
		usuariosJugando = (ArrayList<Usuario>) gu.findByJugando(true);
		userList.setModel(miModeloLista(usuariosJugando));
	}

	private void updateChatList() {
		rellenoAlmacen();
		chatList.setModel(miModeloLista(almacenChat));
		chatScrollPane.getVerticalScrollBar().setValue(chatScrollPane.getVerticalScrollBar().getMaximum());
	}

	private void rellenoAlmacen(){
		EntityTransaction et = em.getTransaction();
		et.begin();
		ArrayList<Chat> mensajes = (ArrayList<Chat>) gc.findByUsuarioYNoLeido(usuarioActual, false);		
		if ( almacenChat.size() > 50 ){
			for ( int i = 0; i < mensajes.size(); i++ ){
				almacenChat.remove(i);
			}
		}
		for ( Chat chat : mensajes ){
			almacenChat.add(chat.getMensaje());
			chat.setLeido(true);			
			gc.update(chat);			
		}
		et.commit();
	}

	private void exitFromGame(JPanel centerPanelBody, JFrame bodyFrame) {
		desconectarYSalir();
		centerPanelBody.removeAll();
		bodyFrame.setVisible(true);
		User user = new User( em, usuarioActual, centerPanelBody, bodyFrame );
		centerPanelBody.add(user, BorderLayout.CENTER);
		centerPanelBody.revalidate();
		centerPanelBody.repaint();
		user.getHero1Button().grabFocus();
	}

	private void tickDelTimer() {
		updateUserList();
		updateChatList();
	}

	private void siguienteSala() {
		elegirSiguienteEnemigo();
		combat.obtenerBotin(combat.getBotinEnemigos());
		heroe.setSala(heroe.getSala()+1);
		atkButton.setEnabled(false);
		defenseButton.setEnabled(false);
		exitButton.setEnabled(true);
		EntityTransaction et = em.getTransaction();
		et.begin();
		gh.update(heroe);
		et.commit();
		if(heroe.getSala() < mazmorra.size()){
			nextRoomButton.setEnabled(true);
		}
	}

	private <T> DefaultListModel<T> miModeloLista( Collection<T> col ){
		DefaultListModel<T> ret = new DefaultListModel<T>();
		for ( T t : col ){
			ret.addElement(t);
		}
		return ret;
	}

	private void enviarMensaje() {
		if ( !chatTextField.getText().equals("") ){ //$NON-NLS-1$
			if ( chatTextField.getText().length() < 70 ){
				EntityTransaction et = em.getTransaction();
				et.begin();
				Date date = new Date();
				Chat chat;				
				for( Usuario usuario : usuariosJugando ){
					chat = new Chat(usuario, (usuarioActual.getNombre() + ": " + chatTextField.getText()), date, false); //$NON-NLS-1$
					gc.insert(chat);					
				}
				chatTextField.setText(""); //$NON-NLS-1$
				chatTextField.grabFocus();
				et.commit();
			}else{
				almacenChat.add(Messages.getString("Game.TooManyWords")); //$NON-NLS-1$
			}
		}
	}

	private void turnoEnemigos(int defensa) {
		ArrayList<Enemigo> enemigosEnEsteTurno = new ArrayList<Enemigo>();
		enemigosEnEsteTurno.addAll(enemigos);
		for ( Enemigo enemigo : enemigosEnEsteTurno ){
			escribirEnListaEventos(combat.turnoEnemigos(enemigo, enemigos, defensa));
		}
		if ( heroe.getPV() <= 0){
			dead = true;
		}
	}

	private void usarHabilidad() {
		habilidad.setCooldown(habilidad.getCooldownMax());
		escribirEnListaEventos(combat.usarHabilidad(habilidad, enemigos, selectedEnemy ));
		if ( selectedEnemy.getPV() <= 0 && enemigos.size() != 0 ){
			elegirSiguienteEnemigo();
		}
		if ( enemigos.size() != 0 ){
			turnoEnemigos(1);
		}else{
			siguienteSala();	
		}
		endTurn();
		cleanEnemiesPanel();
		updateGamePanel();					
	}

	private void lanzarPocion() {
		pocion.setCooldown(pocion.getCooldownMax());
		escribirEnListaEventos(combat.usarPocion(enemigos, pocion, habilidadesAlmacenadas));
		if ( selectedEnemy.getPV() <= 0 && enemigos.size() != 0 ){
			elegirSiguienteEnemigo();
		}
		if ( enemigos.size() != 0 ){
			turnoEnemigos(1);
		}else{
			siguienteSala();	
		}
		endTurn();
		cleanEnemiesPanel();
		updateGamePanel();
	}

	private void elegirSiguienteEnemigo() {
		borderReset();
		if ( enemigos.size() > 0 ){
			selectedEnemy = enemigos.get(0);
			enemy1Icon.setBorder(new LineBorder ( new Color (255, 0, 0), 2));
		}else{
			selectedEnemy = null;
		}
	}

	private void pintoImagen() {
		Image imagen ;
		try {
			imagen = ImageIO.read( new ByteArrayInputStream(heroe.getImagen()) );
			heroeIconLabel.setIcon( new ImageIcon(imagen));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}