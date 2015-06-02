package z_interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import baseDatosOracle.GestorUsuarios;
import baseDatosOracle.Usuario;
import net.miginfocom.swing.MigLayout;

public class NewUser extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel userLabel;
	private JLabel passLabel;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	GestorUsuarios gu;
	private JLabel confirmPasswordLabel;
	private EntityManager em;

	public NewUser( final EntityManager em, final JPanel centerPanel, final JFrame bodyFrame ) {

		gu = new GestorUsuarios(em);
		this.em = em;

		setSize(new Dimension(450, 140));		
		setBackground(new Color(70, 130, 180));
		setLayout(new MigLayout("", "[450px]", "[140px]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		JPanel newUserPane = new JPanel();
		newUserPane.setPreferredSize(new Dimension(450, 140));
		add(newUserPane, "cell 0 0,alignx center,aligny center"); //$NON-NLS-1$
		newUserPane.setLayout(new MigLayout("", "[70.00px][70.00px][70.00px][70.00px][70.00px]", "[20.00px][25px][25px][25px][20px][20px]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		userLabel = new JLabel(Messages.getString("NewUser.UserName")); //$NON-NLS-1$
		userLabel.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$
		userLabel.setHorizontalAlignment(SwingConstants.CENTER);
		newUserPane.add(userLabel, "cell 0 1,grow"); //$NON-NLS-1$
		userLabel.setLabelFor(usernameField);

		usernameField = new JTextField();
		usernameField.setToolTipText(Messages.getString("NewUser.usernameField.toolTipText")); //$NON-NLS-1$
		usernameField.setColumns(10);
		newUserPane.add(usernameField, "cell 1 1 4 1,growx,aligny top"); //$NON-NLS-1$

		usernameField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if ( e.getKeyCode() == KeyEvent.VK_ENTER ){
					if ( !existeUsuario() && !usernameField.getText().equals("") ){ //$NON-NLS-1$
						if ( contraseñasIguales() ){
							cargarLogin(centerPanel, bodyFrame, true);
						}else{
							errorPassword();
						}
					}else{
						errorUser();
					}
				}
			}
		});

		passLabel = new JLabel(Messages.getString("NewUser.Password")); //$NON-NLS-1$
		passLabel.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$
		passLabel.setHorizontalAlignment(SwingConstants.CENTER);
		newUserPane.add(passLabel, "cell 0 2,grow"); //$NON-NLS-1$
		passLabel.setLabelFor(passwordField);

		passwordField = new JPasswordField();
		passwordField.setToolTipText(Messages.getString("NewUser.passwordField.toolTipText")); //$NON-NLS-1$
		passwordField.setHorizontalAlignment(SwingConstants.LEFT);
		passwordField.setEchoChar('*');
		newUserPane.add(passwordField, "cell 1 2 4 1,growx,aligny top"); //$NON-NLS-1$

		passwordField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if ( e.getKeyCode() == KeyEvent.VK_ENTER ){
					if ( !existeUsuario() && !usernameField.getText().equals("") ){ //$NON-NLS-1$
						if ( contraseñasIguales() ){
							cargarLogin(centerPanel, bodyFrame, true);
						}else{
							errorPassword();
						}
					}else{
						errorUser();
					}
				}
			}
		});

		confirmPasswordLabel = new JLabel(Messages.getString("NewUser.Confirm_Password")); //$NON-NLS-1$
		confirmPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		confirmPasswordLabel.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$

		newUserPane.add(confirmPasswordLabel, "cell 0 3,growx,aligny center"); //$NON-NLS-1$

		JButton backButton = new JButton(Messages.getString("NewUser.Back")); //$NON-NLS-1$
		backButton.setMinimumSize(new Dimension(79, 23));
		backButton.setPreferredSize(Body.dimensionBotones);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				cargarLogin(centerPanel, bodyFrame, false);
			}
		});

		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setToolTipText(Messages.getString("NewUser.confirmPasswordField.toolTipText")); //$NON-NLS-1$
		confirmPasswordField.setHorizontalAlignment(SwingConstants.LEFT);
		confirmPasswordField.setEchoChar('*');
		newUserPane.add(confirmPasswordField, "cell 1 3 4 1,growx,aligny top"); //$NON-NLS-1$

		confirmPasswordField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if ( e.getKeyCode() == KeyEvent.VK_ENTER ){
					if ( !existeUsuario() && !usernameField.getText().equals("") ){ //$NON-NLS-1$
						if ( contraseñasIguales() ){
							cargarLogin(centerPanel, bodyFrame, true);
						}else{
							errorPassword();
						}
					}else{
						errorUser();
					}
				}
			}
		});
		backButton.setPreferredSize(new Dimension(0, 0));
		newUserPane.add(backButton, "cell 0 5 2 1,alignx right,growy"); //$NON-NLS-1$

		JButton createButton = new JButton(Messages.getString("NewUser.Create")); //$NON-NLS-1$
		createButton.setPreferredSize(Body.dimensionBotones);
		newUserPane.add(createButton, "cell 3 5 2 1,alignx left,growy"); //$NON-NLS-1$

		createButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if ( !existeUsuario() && !usernameField.getText().equals("") ){ //$NON-NLS-1$
					if ( contraseñasIguales() ){
						cargarLogin(centerPanel, bodyFrame, true);
					}else{
						errorPassword();
					}
				}else{
					errorUser();
				}
			}
		});

		createButton.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if ( e.getKeyCode() == KeyEvent.VK_ENTER ){
					if ( !existeUsuario() && !usernameField.getText().equals("") ){ //$NON-NLS-1$
						if ( contraseñasIguales() ){
							cargarLogin(centerPanel, bodyFrame, true);
						}else{
							errorPassword();
						}
					}else{
						errorUser();
					}
				}
			}
		});
	}

	public JTextField getUsernameField() {
		return usernameField;
	}

	private boolean existeUsuario() {
		boolean existe = false;
		Usuario usuario = null;
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		usuario = gu.findByPrimaryKey(usernameField.getText());
		tx.commit();
		if ( usuario != null ){
			existe = true;
		}
		return existe;
	}

	private void creaNuevoUsuario() {
		Usuario usuario = new Usuario( usernameField.getText().toLowerCase() , String.valueOf(passwordField.getPassword()).toLowerCase(), false);
		EntityTransaction et = em.getTransaction();
		et.begin();
		gu.insert(usuario);
		et.commit();
	}

	private boolean contraseñasIguales() {
		return String.valueOf(passwordField.getPassword()).equals(String.valueOf(confirmPasswordField.getPassword())) && !String.valueOf(confirmPasswordField.getPassword()).equals(""); //$NON-NLS-1$
	}

	private void errorUser() {
		JOptionPane.showMessageDialog(this, Messages.getString("NewUser.NombreMessage")); //$NON-NLS-1$
		limpiarBordes();
		userLabel.setBorder(new LineBorder(new Color(255, 0, 0)));
		usernameField.grabFocus();
		usernameField.selectAll();
	}

	private void errorPassword() {
		JOptionPane.showMessageDialog(this, Messages.getString("NewUser.PasswordMessage")); //$NON-NLS-1$
		limpiarBordes();
		passLabel.setBorder(new LineBorder(new Color(255, 0, 0)));
		confirmPasswordLabel.setBorder(new LineBorder(new Color(255, 0, 0)));
		passwordField.grabFocus();
		passwordField.selectAll();
	}

	private void cargarLogin(JPanel centerPanel, JFrame bodyFrame, boolean nuevoUsuario) {
		if ( nuevoUsuario ){
			creaNuevoUsuario();			
		}
		Login login = new Login(em, centerPanel, bodyFrame);
		centerPanel.removeAll();			
		centerPanel.add(login, BorderLayout.CENTER);
		centerPanel.revalidate();
		centerPanel.repaint();
		login.getUsernameField().grabFocus();		
	}
	
	private void limpiarBordes() {
		passLabel.setBorder(null);
		userLabel.setBorder(null);
		confirmPasswordLabel.setBorder(null);
	}
}
