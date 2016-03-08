package ia.ufba.trabalho.busca.cega;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
//import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;


public class InterfaceVI implements ActionListener{

	static JFrame frmVelhaInteligente;
	static JButton res[] = new JButton[9];
	static int count = 0;
	static int numPerdas = 0;
	static int numEmpates = 0;
	static int numVitorias = 0;
	static JLabel vit;
	static JLabel emp;
	static JLabel per;
	
	public InterfaceVI() {
		initialize();
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		new InterfaceVI();
		frmVelhaInteligente.setVisible(true);
	}
	
	private void reset(){
		novojogo();
		numVitorias = 0;
		numEmpates = 0;
		numPerdas = 0;
		vit.setText("Vitórias: " + numVitorias);
		emp.setText("Empates: " + numEmpates);
		per.setText("Perdas: " + numPerdas);
	}
	
	private void novojogo(){
		for(int i =0; i < 9; i++){
			res[i].setText("");
			res[i].setEnabled(true);
			count = 0;
		}
	}

	private void initialize() {
		frmVelhaInteligente = new JFrame();
		//frmVelhaInteligente.setIconImage(Toolkit.getDefaultToolkit().getImage(InterfaceVI.class.getResource("/Image/icon2.png")));
		frmVelhaInteligente.setTitle("Velha Inteligente - Busca Cega");
		frmVelhaInteligente.setBounds(100, 100, 638, 476);
		frmVelhaInteligente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmVelhaInteligente.getContentPane().setLayout(null);
		
		for (int i = 0; i < 9; i++) {
			res[i] = new JButton("");
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
		Nome.setFont(new Font("Segoe UI Symbol", Font.BOLD, 24));
		Nome.setBounds(211, 17, 246, 46);
		frmVelhaInteligente.getContentPane().add(Nome);
		
		JLabel Resultado = new JLabel("");
		Resultado.setBounds(441, 297, 46, 14);
		frmVelhaInteligente.getContentPane().add(Resultado);
		
		JPanel panel = new JPanel();
		panel.setToolTipText("");
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Est\u00E1tisticas", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(399, 74, 213, 237);
		frmVelhaInteligente.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblJpgadprX = new JLabel("Jogador: X");
		lblJpgadprX.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblJpgadprX.setBounds(20, 36, 143, 24);
		panel.add(lblJpgadprX);
		
		JLabel lblNewLabel = new JLabel("Computador: O");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(20, 61, 143, 24);
		panel.add(lblNewLabel);
		
		vit = new JLabel("Vit\u00F3rias: 0");
		vit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		vit.setBounds(20, 113, 143, 14);
		panel.add(vit);
		
		per = new JLabel("Perdas: 0");
		per.setFont(new Font("Tahoma", Font.PLAIN, 14));
		per.setBounds(20, 142, 143, 14);
		panel.add(per);
		
		emp = new JLabel("Empates: 0");
		emp.setFont(new Font("Tahoma", Font.PLAIN, 14));
		emp.setBounds(20, 167, 143, 14);
		panel.add(emp);
		
		JButton btnNovoJogo = new JButton("Novo Jogo");
		btnNovoJogo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
			}
		});
		btnNovoJogo.setToolTipText("");
		btnNovoJogo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNovoJogo.setBounds(451, 332, 123, 23);
		frmVelhaInteligente.getContentPane().add(btnNovoJogo);
		
		JButton btnSobreOJogo = new JButton("Sobre o Jogo");
		btnSobreOJogo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Velha Inteligente \nJogo desenvolvido por Andressa Andrade, Renata Antunes e Virgínia de Paula. \nDesenvolvido para a disciplina de Inteligência Artificial ministrada pela Professora Dra. Tatiane Nogueira.", "Sobre o Jogo", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnSobreOJogo.setToolTipText("");
		btnSobreOJogo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSobreOJogo.setBounds(451, 366, 123, 23);
		frmVelhaInteligente.getContentPane().add(btnSobreOJogo);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(22, 83, 358, 318);
		frmVelhaInteligente.getContentPane().add(panel_1);
		
	}
	
	private void Perdas(){
		numPerdas += 1;
		per.setText("Perdas: " + numPerdas);
	}
	
	private void Vitorias(){
		numVitorias += 1;
		vit.setText("Vitórias: " + numVitorias);
	}
	
	private void Empates(){
		numEmpates += 1;
		emp.setText("Empates: " + numEmpates);
	}
	
	private void verifica(){
		if(res[0].getText().equals("X") && res[1].getText().equals("X")&& res[2].getText().equals("X") || res[0].getText().equals("X") && res[3].getText().equals("X")&& res[6].getText().equals("X") || res[1].getText().equals("X") && res[4].getText().equals("X")&& res[7].getText().equals("X") || res[2].getText().equals("X") && res[5].getText().equals("X")&& res[8].getText().equals("X") || res[0].getText().equals("X") && res[4].getText().equals("X")&& res[8].getText().equals("X") || res[2].getText().equals("X") && res[4].getText().equals("X")&& res[6].getText().equals("X") || res[3].getText().equals("X") && res[4].getText().equals("X")&& res[5].getText().equals("X") || res[6].getText().equals("X") && res[7].getText().equals("X")&& res[8].getText().equals("X")){
			JOptionPane.showMessageDialog(null, "Parabéns X", "VOCÊ GANHOU!", JOptionPane.INFORMATION_MESSAGE);
			Vitorias();
			novojogo();
		}
		if(res[0].getText().equals("O") && res[1].getText().equals("O")&& res[2].getText().equals("O") || res[0].getText().equals("O") && res[3].getText().equals("O")&& res[6].getText().equals("O") || res[1].getText().equals("O") && res[4].getText().equals("O")&& res[7].getText().equals("O") || res[2].getText().equals("O") && res[5].getText().equals("O")&& res[8].getText().equals("O") || res[0].getText().equals("O") && res[4].getText().equals("O")&& res[8].getText().equals("O") || res[2].getText().equals("O") && res[4].getText().equals("O")&& res[6].getText().equals("O") || res[3].getText().equals("O") && res[4].getText().equals("O")&& res[5].getText().equals("O") || res[6].getText().equals("O") && res[7].getText().equals("O")&& res[8].getText().equals("O")){
			JOptionPane.showMessageDialog(null, "Parabéns O", "VOCÊ GANHOU!", JOptionPane.INFORMATION_MESSAGE);
			Perdas();
			novojogo();
		}
		if(count == 9){
			JOptionPane.showMessageDialog(null, "Deu Velha!", "EMPATOU!", JOptionPane.INFORMATION_MESSAGE);
			Empates();
			novojogo();
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// 
		if(count % 2 == 0){
			for (int i = 0; i < 9; i++) {
				if (e.getSource() == res[i]) {
					res[i].setText("X");
					res[i].setEnabled(false);
					count += 1;
				}
			}
		}
		else{
			for (int i = 0; i < 9; i++) {
				if (e.getSource() == res[i]) {
					res[i].setText("O");
					res[i].setEnabled(false);
					count += 1;
				}
			}
		}
		
		verifica();	
	}
}
