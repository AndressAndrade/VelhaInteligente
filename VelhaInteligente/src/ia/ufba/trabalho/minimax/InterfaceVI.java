package ia.ufba.trabalho.minimax;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
//import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;

class Jogada {
    int x, y;
    public Jogada(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
}

class Classificacao {
    int     pontuacao;
    Jogada  jogadas;
    
    Classificacao(int pontuacao, Jogada jogadas) {
        this.pontuacao = pontuacao;
        this.jogadas = jogadas;
    }
}

public class InterfaceVI implements ActionListener{
    static int      count = 0;
    static int      numPerdas = 0;
    static int      numEmpates = 0;
    static int      numVitorias = 0; // Impossível!
    static JFrame   frmVelhaInteligente;
    static JButton  res[][] = new JButton[3][3];
    static JLabel   vit;
    static JLabel   emp;
    static JLabel   per;
    static int      COMPUTADOR = 0;
    static int      PESSOA = 1;
    
    List<Jogada>   jogadasDisponiveis;
    List<Classificacao> nosFilhos;
    Jogada         computador;

    private void initialize() {
        frmVelhaInteligente = new JFrame();
        frmVelhaInteligente.setResizable(false);
        //frmVelhaInteligente.setIconImage(Toolkit.getDefaultToolkit().getImage(InterfaceVI.class.getResource("/Imagens/icon2.png")));
        frmVelhaInteligente.setTitle("Velha Inteligente - Busca Cega");
        frmVelhaInteligente.setBounds(100, 100, 638, 476);
        frmVelhaInteligente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmVelhaInteligente.getContentPane().setLayout(null);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                res[i][j] = new JButton("");
                frmVelhaInteligente.getContentPane().add(res[i][j]);
                res[i][j].addActionListener(this);
            }
        }

        res[0][0].setBounds(38, 93, 103, 91);
        res[0][1].setBounds(151, 93, 103, 91);
        res[0][2].setBounds(264, 93, 103, 91);
        res[1][0].setBounds(38, 195, 103, 91);
        res[1][1].setBounds(151, 195, 103, 91);
        res[1][2].setBounds(264, 195, 103, 91);
        res[2][0].setBounds(38, 297, 103, 91);
        res[2][1].setBounds(151, 297, 103, 91);
        res[2][2].setBounds(264, 297, 103, 91);

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
        btnNovoJogo.addActionListener((ActionEvent arg0) -> {
            reset();
        });
        btnNovoJogo.setToolTipText("");
        btnNovoJogo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnNovoJogo.setBounds(451, 332, 123, 23);
        frmVelhaInteligente.getContentPane().add(btnNovoJogo);

        JButton btnSobreOJogo = new JButton("Sobre o Jogo");
        btnSobreOJogo.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null, "Velha Inteligente \nJogo desenvolvido por Andressa Andrade, Renata Antunes e VirgÃ­nia de Paula. \nDesenvolvido para a disciplina de InteligÃªncia Artificial ministrada pela Professora Dra. Tatiane Nogueira.", "Sobre o Jogo", JOptionPane.INFORMATION_MESSAGE);
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
    
    public InterfaceVI() {
        initialize();
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
        for(int i =0; i < 3; i++){
            for (int j = 0; j < 3; j++) {
                res[i][j].setText("");
                res[i][j].setEnabled(true);
                count = 0;
            }
        }
    }

    private boolean Perdas(){
        if(    res[0][0].getText().equals("O") && res[0][1].getText().equals("O")&& res[0][2].getText().equals("O") 
            || res[1][0].getText().equals("O") && res[1][1].getText().equals("O")&& res[1][2].getText().equals("O") 
            || res[2][0].getText().equals("O") && res[2][1].getText().equals("O")&& res[2][2].getText().equals("O") 
            || res[0][0].getText().equals("O") && res[1][0].getText().equals("O")&& res[2][0].getText().equals("O") 
            || res[0][1].getText().equals("O") && res[1][1].getText().equals("O")&& res[2][1].getText().equals("O") 
            || res[0][2].getText().equals("O") && res[1][2].getText().equals("O")&& res[2][2].getText().equals("O") 
            || res[0][0].getText().equals("O") && res[1][1].getText().equals("O")&& res[2][2].getText().equals("O") 
            || res[0][2].getText().equals("O") && res[1][1].getText().equals("O")&& res[2][0].getText().equals("O")){

            return (true);
        }
        return false;
    }

    private boolean Vitorias(){ // Impossível!
        if(    res[0][0].getText().equals("X") && res[0][1].getText().equals("X")&& res[0][2].getText().equals("X") 
            || res[1][0].getText().equals("X") && res[1][1].getText().equals("X")&& res[1][2].getText().equals("X") 
            || res[2][0].getText().equals("X") && res[2][1].getText().equals("X")&& res[2][2].getText().equals("X") 
            || res[0][0].getText().equals("X") && res[1][0].getText().equals("X")&& res[2][0].getText().equals("X") 
            || res[0][1].getText().equals("X") && res[1][1].getText().equals("X")&& res[2][1].getText().equals("X") 
            || res[0][2].getText().equals("X") && res[1][2].getText().equals("X")&& res[2][2].getText().equals("X") 
            || res[0][0].getText().equals("X") && res[1][1].getText().equals("X")&& res[2][2].getText().equals("X") 
            || res[0][2].getText().equals("X") && res[1][1].getText().equals("X")&& res[2][0].getText().equals("X")){
           
            return (true);
        }	
        return (false);
    }

    private boolean Empates(){
        if(count == 9){ 
            return (true);
        }
        return (false);
    }
    
    private boolean Verifica(){
        if(Vitorias()){
            JOptionPane.showMessageDialog(null, "Parabéns, você ganhou!", "GANHOU!", JOptionPane.INFORMATION_MESSAGE);
            novojogo();
            numVitorias += 1;
            vit.setText("VitÃ³rias: " + numVitorias);
            return true;
        }
        else if(Perdas()){
            JOptionPane.showMessageDialog(null, "Que pena, você perdeu!", "PERDEU!", JOptionPane.INFORMATION_MESSAGE);
            novojogo();
            numPerdas += 1;
            per.setText("Perdas: " + numPerdas);
            return true;
        }
        else if(Empates()){
            JOptionPane.showMessageDialog(null, "Deu Velha!", "EMPATOU!", JOptionPane.INFORMATION_MESSAGE);
            novojogo();
            numEmpates += 1;
            emp.setText("Empates: " + numEmpates);
            return true;
        }
        
        return false;
    }

    public List<Jogada> getJogadaDisponiveis() {
        jogadasDisponiveis = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (res[i][j].getText().equals("")) {
                    jogadasDisponiveis.add(new Jogada(i, j));
                }
            }
        }
      
        return jogadasDisponiveis;
    }
    
    public void fazerJogadaComputador(Jogada jogada){
        res[jogada.x][jogada.y].setText("O");
        res[jogada.x][jogada.y].setEnabled(false);
        count += 1;
    }
    
    public void fazerJogadaComputadorFalso(Jogada jogada){
        res[jogada.x][jogada.y].setText("O");
    }
    
    public void fazerJogadaPessoaFalso(Jogada jogada){
        res[jogada.x][jogada.y].setText("X");
    }
    
    public Jogada retornaMelhorJogada(){
        int MAX = Integer.MIN_VALUE;
        int melhor = -1;
        
        for(int i = 0; i < nosFilhos.size(); i++){
            if(MAX < nosFilhos.get(i).pontuacao){
                MAX = nosFilhos.get(i).pontuacao;
                melhor = i;
            }
        }
        
        return nosFilhos.get(melhor).jogadas;
    }
    
    public int retornaMinimo(List<Integer> pontos){
        int menor = Integer.MAX_VALUE;
        int posicao = -1;
        
        for(int i = 0; i < pontos.size(); i++){
            if(pontos.get(i) < menor){
                menor = pontos.get(i);
                posicao = i;
            }
        }
        
        return pontos.get(posicao);
    }
    
    public int retornaMaximo(List<Integer> pontos){
        int maior = Integer.MIN_VALUE;
        int posicao = -1;
        
        for(int i = 0; i < pontos.size(); i++){
            if(pontos.get(i) > maior){
                maior = pontos.get(i);
                posicao = i;
            }
        }
        
        return pontos.get(posicao);
    }
    
    public int minimax(int profundidade, int vez){
        if(Vitorias()) return -1;
        else if(Perdas()) return +1;
        
        List<Jogada> jogadasDisponivelAux = getJogadaDisponiveis();
        if(jogadasDisponivelAux.isEmpty()) return 0;
        List<Integer> pontuacao = new ArrayList<>();
        
        for (int i = 0; i < jogadasDisponivelAux.size(); i++) {
            Jogada j = jogadasDisponivelAux.get(i);
            
            if(vez == COMPUTADOR){
                fazerJogadaComputadorFalso(j);
                int pontuacaoAtual = minimax(profundidade + 1, PESSOA);
                pontuacao.add(pontuacaoAtual);
                
                if(profundidade == 0){
                    nosFilhos.add(new Classificacao(pontuacaoAtual, j));
                }
            }
            else if(vez == PESSOA){
                fazerJogadaPessoaFalso(j);
                pontuacao.add((minimax(profundidade + 1, COMPUTADOR)));
            }
            res[j.x][j.y].setText("");
        }
        
        return vez == COMPUTADOR ? retornaMaximo(pontuacao) : retornaMinimo(pontuacao);
    }
    
 
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (e.getSource() == res[i][j]) {
                    res[i][j].setText("X");
                    res[i][j].setEnabled(false);
                    count += 1;
                }
            }
        }
        
        // X = i ; Y = j
        if(Verifica())
            return;
        
        nosFilhos = new ArrayList<>();
        minimax(0, COMPUTADOR);
        
        System.out.println("-------- RODADA " + count + " --------");
        for(Classificacao cla : nosFilhos){
            //System.out.println("Jogada: " + cla.jogadas + " Pontuação: " + cla.pontuacao);
            System.out.println(" Pontuação: " + cla.pontuacao);
        }
        System.out.println("--------------------------");
        
        fazerJogadaComputador(retornaMelhorJogada());
        
        if(Verifica())
            return;
    }
    
    @SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, InterruptedException {
        InterfaceVI interfaceVI = new InterfaceVI();
        frmVelhaInteligente.setVisible(true);
        System.out.println("+++++ Jogo da Velha - MiniMax +++++\n"+
                           "++++++++ Velha Inteligente ++++++++\n" +
                           "Desenvolvido por: \n" +
                           "Andressa Andrade,\n" + 
                           "Renata Antunes,\n" +
                           "Virginia De Paula \n \n");
    }
}
