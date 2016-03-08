package ia.ufba.trabalho;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class InterfaceVI implements ActionListener{

	static JFrame frmVelhaInteligente;
	static JButton res[] = new JButton[9];
	static int count = 0;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public InterfaceVI() {
		initialize();
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		new InterfaceVI();
		frmVelhaInteligente.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmVelhaInteligente = new JFrame();
		frmVelhaInteligente.setResizable(false);
		frmVelhaInteligente.setIconImage(Toolkit.getDefaultToolkit().getImage(InterfaceVI.class.getResource("/ia/ufba/imagens/icon2.png")));
		frmVelhaInteligente.setTitle("Velha Inteligente 0.1");
		frmVelhaInteligente.setBounds(100, 100, 604, 513);
		frmVelhaInteligente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmVelhaInteligente.getContentPane().setLayout(null);
		
		for (int i = 0; i < 9; i++) {
			
			res[i] = new JButton(" ");
			frmVelhaInteligente.getContentPane().add(res[i]);
			res[i].addActionListener(this);
		}
		
		res[0].setBounds(38, 93, 103, 91);
		res[1].setBounds(151, 93, 103, 91);
		res[2].setBounds(264, 93, 103, 91);
		res[3].setBounds(38, 195, 103, 91);
		res[4].setBounds(151, 195, 103, 91);
		res[5].setBounds(264, 195, 103, 91);
		res[6].setBounds(38, 297, 103, 91);
		res[7].setBounds(151, 297, 103, 91);
		res[8].setBounds(264, 297, 103, 91);
		
		JLabel Nome = new JLabel("Velha Inteligente");
		Nome.setFont(new Font("Tahoma", Font.BOLD, 20));
		Nome.setBounds(113, 21, 176, 46);
		frmVelhaInteligente.getContentPane().add(Nome);
		
		JLabel lblJpgadprX = new JLabel("Jogador: X");
		lblJpgadprX.setBounds(417, 104, 121, 46);
		frmVelhaInteligente.getContentPane().add(lblJpgadprX);
		
		JLabel lblNewLabel = new JLabel("Computador: O");
		lblNewLabel.setBounds(417, 142, 121, 14);
		frmVelhaInteligente.getContentPane().add(lblNewLabel);
		
		JLabel Resultado = new JLabel("");
		Resultado.setBounds(441, 297, 46, 14);
		frmVelhaInteligente.getContentPane().add(Resultado);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(count%2 == 0)
			for (int i = 0; i < 9; i++) {
				if (e.getSource() == res[i]) {
					res[i].setText("X");
					res[i].setEnabled(false);
					count+=1;
				}
			}
		else{
			for (int i = 0; i < 9; i++) {
				if (e.getSource() == res[i]) {
					res[i].setText("O");
					res[i].setEnabled(false);
					count+=1;
				}
			}
		}
		
	}
}
