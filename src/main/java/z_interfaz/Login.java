package z_interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.SwingConstants;

import baseDatosOracle.GestorUsuarios;
import baseDatosOracle.Usuario;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import net.miginfocom.swing.MigLayout;

public class Login extends JPanel {

	private static final long serialVersionUID = 1L;
	private NewUser newuser;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private Usuario usuario;
	private GestorUsuarios gu;
	private JLabel passwordLabel;
	private JLabel usernameLabel;
	private JButton newuserButton;
	private JButton loginButton;
	private EntityManager em;

	public Login(final EntityManager em, final JPanel centerPanel, final JFrame bodyFrame ) {

		this.gu = new GestorUsuarios(em);
		this.em = em;
		
		setSize(new Dimension(350, 140));		
		setBackground(new Color(70, 130, 180));
		setLayout(new MigLayout("", "[350px]", "[140px]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		JPanel loginPanel = new JPanel();
		loginPanel.setPreferredSize(new Dimension(350, 140));
		add(loginPanel, "cell 0 0,grow"); //$NON-NLS-1$
		loginPanel.setLayout(new MigLayout("", "[70px][70.00px][70.00px][70.00px][51.00px][24]", "[20.00px][35.00px][35.00px][24.00][]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		usernameLabel = new JLabel(Messages.getString("Login.UserName")); //$NON-NLS-1$
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usernameLabel.setPreferredSize(new Dimension(58, 14));
		usernameLabel.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$
		loginPanel.add(usernameLabel, "cell 0 1,grow"); //$NON-NLS-1$

		usernameField = new JTextField();
		usernameField.setToolTipText(Messages.getString("Login.usernameField.toolTipText")); //$NON-NLS-1$
		usernameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				usernameField.selectAll();
			}
		});
		usernameField.setPreferredSize(new Dimension(192, 20));
		loginPanel.add(usernameField, "cell 1 1 4 1,growx,aligny center"); //$NON-NLS-1$

		usernameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if ( e.getKeyCode() == KeyEvent.VK_ENTER ){
					if ( existeUsuarioYNoEstaConectado() ){
						if ( passwordCorrecto() ){
							cargarUser(centerPanel, bodyFrame);
						}else{
							errorPassword();
						}
					}else{
						errorUser();
					}
				}
			}
		});

		passwordLabel = new JLabel(Messages.getString("Login.Password")); //$NON-NLS-1$
		passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		passwordLabel.setPreferredSize(new Dimension(58, 14));
		passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$
		loginPanel.add(passwordLabel, "cell 0 2,grow"); //$NON-NLS-1$

		passwordField = new JPasswordField();
		passwordField.setToolTipText(Messages.getString("Login.passwordField.toolTipText")); //$NON-NLS-1$
		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				passwordField.selectAll();
			}
		});
		passwordField.setPreferredSize(new Dimension(192, 20));
		loginPanel.add(passwordField, "flowx,cell 1 2 4 1,growx,aligny center"); //$NON-NLS-1$

		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if ( e.getKeyCode() == KeyEvent.VK_ENTER ){
					if ( existeUsuarioYNoEstaConectado() ){
						if ( passwordCorrecto() ){
							cargarUser(centerPanel, bodyFrame);
						}else{
							errorPassword();
						}
					}else{
						errorUser();
					}
				}
			}
		});

		newuserButton = new JButton(Messages.getString("Login.NewUser")); //$NON-NLS-1$
		newuserButton.setPreferredSize(Body.dimensionBotones);
		newuserButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				cargarNuevoUser(centerPanel, bodyFrame);
			}
		});
		loginPanel.add(newuserButton, "cell 0 4 2 1,alignx right,aligny bottom"); //$NON-NLS-1$

		loginButton = new JButton(Messages.getString("Login.LogIn")); //$NON-NLS-1$
		loginButton.setPreferredSize(Body.dimensionBotones);
		loginPanel.add(loginButton, "cell 3 4 2 1,alignx left,aligny bottom"); //$NON-NLS-1$

		loginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if ( existeUsuarioYNoEstaConectado() ){
					if ( passwordCorrecto() ){						
						cargarUser(centerPanel, bodyFrame);
					}else{
						errorPassword();
					}
				}else{
					errorUser();
				}
			}
		});
	}

	private void obtenerUsuario() {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		usuario = gu.findByPrimaryKey(usernameField.getText());
		tx.commit();
	}

	private boolean existeUsuarioYNoEstaConectado() {
		boolean existe = false;
		obtenerUsuario();
		if ( usuario != null && !usuario.isConectado() ){
			Body.usuario = usuario;
			existe = true;
		}
		return existe;
	}

	private boolean passwordCorrecto() {
		String password = String.valueOf(passwordField.getPassword());
		obtenerUsuario();
		return String.valueOf(usuario.getPassword()).equals(password) && !String.valueOf(passwordField.getPassword()).equals(""); //$NON-NLS-1$
	}

	private void actualizaUsuario() {
		usuario.setConectado(true);
		EntityTransaction et = em.getTransaction();
		et.begin();
		gu.update(usuario);
		et.commit();
	}
	
	private void cargarUser(JPanel centerPanel, JFrame bodyFrame) {
		centerPanel.removeAll();
		actualizaUsuario();
		User user = new User(em, usuario, centerPanel, bodyFrame);
		centerPanel.add(user, BorderLayout.CENTER);
		centerPanel.revalidate();
		centerPanel.repaint();
		user.getHero1Button().grabFocus();
	}
	
	private void cargarNuevoUser(JPanel centerPanel, JFrame bodyFrame) {
		centerPanel.removeAll();
		newuser = new NewUser(em, centerPanel, bodyFrame);
		centerPanel.add(newuser, BorderLayout.CENTER);
		centerPanel.revalidate();
		centerPanel.repaint();
		newuser.getUsernameField().grabFocus();
	}
	
	private void errorUser() {
		JOptionPane.showMessageDialog(this, Messages.getString("Login.NombreMessage")); //$NON-NLS-1$
		limpiarBordes();
		usernameLabel.setBorder(new LineBorder(new Color(255, 0, 0)));
		usernameField.grabFocus();
		usernameField.selectAll();
	}

	private void errorPassword() {
		JOptionPane.showMessageDialog(this, Messages.getString("Login.PasswordMessage")); //$NON-NLS-1$
		limpiarBordes();
		passwordLabel.setBorder(new LineBorder(new Color(255, 0, 0)));
		passwordField.grabFocus();
		passwordField.selectAll();
	}

	private void limpiarBordes() {
		passwordLabel.setBorder(null);
		usernameLabel.setBorder(null);
	}

	public JTextField getUsernameField() {
		return usernameField;
	}
}
