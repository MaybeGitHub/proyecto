package z_interfaz;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Title extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public Title() {
		
		JLabel iconLabel_2 = new JLabel();
		iconLabel_2.setBounds(0, 0, 100, 100);
		iconLabel_2.setIcon(new ImageIcon(Body.class.getResource("/imagenes/varios/orc_Icon.png")));
		super.add(iconLabel_2);

		JLabel titleLabel = new JLabel("Orc Dungeon Master");
		titleLabel.setBounds(100, 0, 300, 100);
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		super.add(titleLabel);

		JLabel iconLabel = new JLabel("");
		iconLabel.setBounds(400, 0, 100, 100);
		iconLabel.setIcon(new ImageIcon(Body.class.getResource("/imagenes/varios/orc_Icon.png")));
		super.add(iconLabel);
	}
}
