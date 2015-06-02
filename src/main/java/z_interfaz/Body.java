package z_interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import baseDatosOracle.GestorUsuarios;
import baseDatosOracle.Usuario;
import net.miginfocom.swing.MigLayout;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Body extends JFrame {

	private static final long serialVersionUID = 1L;
	private int width = 800;
	private int height = 600;
	public static Dimension dimensionBotones = new Dimension(79, 23);
	private JFrame bodyFrame = this;
	private JPanel mainPanel;
	public static Usuario usuario;
	private GestorUsuarios gu;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());					
					Body bodyFrame = new Body();					
					bodyFrame.pack();
					bodyFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Body() {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("oracle"); //$NON-NLS-1$
		final EntityManager em = emf.createEntityManager();

		gu = new GestorUsuarios(em);

		setSize(new Dimension(width, height));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Body.class.getResource("/imagenes/varios/mazmorraOrca.jpg"))); //$NON-NLS-1$
		setTitle("Dungeon Master"); //$NON-NLS-1$
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(new Color(70, 130, 180));

		JPanel bodyPanel = new JPanel();
		bodyPanel.setBackground(new Color(70, 130, 180));
		setContentPane(bodyPanel);
		bodyPanel.setLayout(new MigLayout("", "[800px,grow]", "[150px,grow][393px,grow]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		JPanel topPanel = new JPanel();
		topPanel.setBackground(new Color(70, 130, 180));
		bodyPanel.add(topPanel, "cell 0 0,growx,aligny top"); //$NON-NLS-1$
		topPanel.setLayout(new MigLayout("", "[800px,grow]", "[150px,grow]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		Title tituloPanel = new Title();
		topPanel.add(tituloPanel, "cell 0 0,alignx center,aligny center"); //$NON-NLS-1$
		tituloPanel.setBackground(new Color(70, 130, 180));

		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(70, 130, 180));
		bodyPanel.add(mainPanel, "cell 0 1,growx,aligny top"); //$NON-NLS-1$
		mainPanel.setLayout(new MigLayout("", "[800px,grow]", "[393px,grow]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		Login login = new Login(em, mainPanel, bodyFrame);
		login.getUsernameField().grabFocus();
		mainPanel.add(login, "cell 0 0,alignx center,aligny center");

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if ( usuario != null ){
					EntityTransaction et = em.getTransaction();
					usuario.setConectado(false);
					et.begin();
					gu.update(usuario);
					et.commit();
				}
			}
		});
	}
}
