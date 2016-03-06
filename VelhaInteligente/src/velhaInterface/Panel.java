package velhaInterface;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;


public class Panel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Create the panel.
	 */
	public Panel() {
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("max(7dlu;default)"),
				ColumnSpec.decode("89px"),
				FormSpecs.GROWING_BUTTON_COLSPEC,
				FormSpecs.BUTTON_COLSPEC,
				FormSpecs.GROWING_BUTTON_COLSPEC,
				ColumnSpec.decode("max(42dlu;pref)"),},
			new RowSpec[] {
				RowSpec.decode("max(7dlu;default)"),
				FormSpecs.DEFAULT_ROWSPEC,
				RowSpec.decode("58px"),
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("24px:grow"),
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		add(btnNewGame, "2, 2");
		
		JLabel lblVelhaInteligente = new JLabel("Velha Inteligente");
		lblVelhaInteligente.setFont(new Font("Segoe UI Emoji", Font.BOLD, 25));
		add(lblVelhaInteligente, "3, 3, 3, 1, center, center");
		
		JButton btnNewButton_6 = new JButton("New button");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		add(btnNewButton_6, "3, 5");
		
		JButton btnNewButton_7 = new JButton("New button");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		add(btnNewButton_7, "4, 5");
		
		JButton btnNewButton_8 = new JButton("New button");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		add(btnNewButton_8, "5, 5");
		
		JButton btnNewButton_3 = new JButton("New button");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		add(btnNewButton_3, "3, 6, center, center");
		
		JButton btnNewButton_4 = new JButton("New button");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		add(btnNewButton_4, "4, 6");
		
		JButton btnNewButton_5 = new JButton("New button");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		add(btnNewButton_5, "5, 6");
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		add(btnNewButton, "3, 7, left, center");
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		add(btnNewButton_1, "4, 7, left, center");
		
		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		add(btnNewButton_2, "5, 7, left, center");
		
		JLabel lblVitrias = new JLabel("Computador:");
		add(lblVitrias, "2, 9, right, default");
		
		textField = new JTextField();
		add(textField, "3, 9, fill, default");
		textField.setColumns(10);
		
		JLabel lblPerdas = new JLabel("Homem:");
		add(lblPerdas, "2, 11, right, default");
		
		textField_1 = new JTextField();
		add(textField_1, "3, 11, fill, default");
		textField_1.setColumns(10);

	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}

