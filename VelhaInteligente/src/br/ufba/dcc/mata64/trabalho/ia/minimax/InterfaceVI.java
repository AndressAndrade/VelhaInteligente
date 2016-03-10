/**
 * Trabalho de MATA64 - Inteligência Artificial
 * 
 * Implementação da inteligência artificial para o Jogo da Velha (em ingles TicTacToe)
 * utilizando o conceito de minimazação, maximzação minimax.
 * 
 * @author Andressa Andrade
 * @author Renata Antunes
 * @author Virginia de Paula
 * @version 1.0
 */
package br.ufba.dcc.mata64.trabalho.ia.minimax;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.io.IOException;

/**
 * Classe que representa Jogadas possiveis de ser executada, de acordo
 * com as entradas disponiveis na nossa matriz.
*/
class Jogada {

    int i, j; // Coordenadas das jogadas

    /**
     * Constroi uma jogada, com as coordenadas passadas.
     *
     * @param i linha
     * @param j coluna
     */
    public Jogada(int i, int j) {
        this.i = i;
        this.j = j;
    }
    
    @Override
    public String toString() {
        return "[" + i + ", " + j + "]";
    }
}

/**
 * Classificacao e uma classe que contem a pontuacao de uma determinada jogada,
 * Ela é utilizada para classificar o quao bom uma jogada pode ser.
 */
class Classificacao {

    int pontuacao;
    Jogada jogada;

    /**
     * Constroi a classificacao da jogada, tendo a pontuacao e a jogada em si.
     *
     * @param pontuacao pontuacao da jogada
     * @param jogada    jogada
     */
    Classificacao(int pontuacao, Jogada jogada) {
        this.pontuacao = pontuacao;
        this.jogada = jogada;
    }
}

/**
 * Classe principal da aplicação, define a interface gráfica e a IA.
 */
public class InterfaceVI implements ActionListener {

    // Variaveis do jogo
    static int      count = 0;                      // Contador de turnos do jogo
    static int      numEmpates = 0;                 // Numero de empates
    static int      numPerdas = 0;                  // Numero de perdas do jogador
    static int      numVitorias = 0;                // Numero de vitorias do jogador
    
    // Variaveis de interface
    static JFrame   frmVelhaInteligente;            // Frame do jogo
    static JButton  res[][] = new JButton[3][3];    // Matriz com todos os botoes
    static JLabel   vit;                            // Label com as vitorias
    static JLabel   emp;                            // Label com os empates  
    static JLabel   per;                            // Label com as derrotas
    
    // Definicoes de turno
    static int      COMPUTADOR = 0;                 // Representa um jogador                 
    static int      PESSOA = 1;                     // Representa uma pessoa

    List<Jogada>        jogadasDisponiveis;
    List<Classificacao> nosFilhos;
    Jogada              computador;

    // Constroi a interface grafica
    // <editor-fold defaultstate="collapsed" desc="Inicializador da interface grafica">
    private void initialize() {
        frmVelhaInteligente = new JFrame();
        frmVelhaInteligente.setResizable(false);
        frmVelhaInteligente.setIconImage(Toolkit
                .getDefaultToolkit()
                .getImage(getClass().getResource("/br/ufba/dcc/mata64/trabalho/ia/assets/icon.png")));
        frmVelhaInteligente.setTitle("Velha Inteligente - Minimax");
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
            JOptionPane.showMessageDialog(null, "Velha Inteligente \n"
                    + "Jogo desenvolvido por Andressa Andrade, Renata Antunes e Virgínia de Paula.\n"
                    + "Desenvolvido para a disciplina de Inteligência Artificial ministrada pela "
                    + "Professora Dra. Tatiane Nogueira.", "Sobre o Jogo",
                    JOptionPane.INFORMATION_MESSAGE);
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
    // </editor-fold>
    
    // Construtor do jogo
    // <editor-fold defaultstate="collapsed" desc="Construtor da classe jogo">
    public InterfaceVI() {
        initialize();
    }
    // </editor-fold>
    
    /**
     * Reseta a interface do jogo e os contadores de vitoria e derrotas.
     */
    private void reset() {
        novojogo();
        numVitorias = 0;
        numEmpates = 0;
        numPerdas = 0;
        vit.setText("Vitorias: " + numVitorias);
        emp.setText("Empates: " + numEmpates);
        per.setText("Perdas: " + numPerdas);
    }

    /**
     * Inicia um novo jogo e habilita os botoes.
     */
    private void novojogo() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                res[i][j].setText("");
                res[i][j].setEnabled(true);
                count = 0;
            }
        }
        System.out.println("\n--------------------------\n"
                           + "-------- NovoJogo --------\n"
                           + "--------------------------\n");
    }

    /**
     * Checa se o humano perdeu o jogo
     * 
     * @return Retorna 'true' se o jogador perdeu e 'false' se o jogo pode continuar
     */
    private boolean Perdas() {
        if (res[0][0].getText().equals("O") && res[0][1].getText().equals("O") && res[0][2].getText().equals("O")
                || res[1][0].getText().equals("O") && res[1][1].getText().equals("O") && res[1][2].getText().equals("O")
                || res[2][0].getText().equals("O") && res[2][1].getText().equals("O") && res[2][2].getText().equals("O")
                || res[0][0].getText().equals("O") && res[1][0].getText().equals("O") && res[2][0].getText().equals("O")
                || res[0][1].getText().equals("O") && res[1][1].getText().equals("O") && res[2][1].getText().equals("O")
                || res[0][2].getText().equals("O") && res[1][2].getText().equals("O") && res[2][2].getText().equals("O")
                || res[0][0].getText().equals("O") && res[1][1].getText().equals("O") && res[2][2].getText().equals("O")
                || res[0][2].getText().equals("O") && res[1][1].getText().equals("O") && res[2][0].getText().equals("O")) {

            return (true);
        }
        return false;
    }

    /**
     * Checa se o humano venceu o jogo
     * 
     * @return Retorna 'true' se o jogador venceu e 'false' se o jogo pode continuar
     */
    private boolean Vitorias() {
        if (res[0][0].getText().equals("X") && res[0][1].getText().equals("X") && res[0][2].getText().equals("X")
                || res[1][0].getText().equals("X") && res[1][1].getText().equals("X") && res[1][2].getText().equals("X")
                || res[2][0].getText().equals("X") && res[2][1].getText().equals("X") && res[2][2].getText().equals("X")
                || res[0][0].getText().equals("X") && res[1][0].getText().equals("X") && res[2][0].getText().equals("X")
                || res[0][1].getText().equals("X") && res[1][1].getText().equals("X") && res[2][1].getText().equals("X")
                || res[0][2].getText().equals("X") && res[1][2].getText().equals("X") && res[2][2].getText().equals("X")
                || res[0][0].getText().equals("X") && res[1][1].getText().equals("X") && res[2][2].getText().equals("X")
                || res[0][2].getText().equals("X") && res[1][1].getText().equals("X") && res[2][0].getText().equals("X")) {

            return (true);
        }
        return (false);
    }

    /**
     * Checa se houve empate no jogo
     * 
     * @return Retorna 'true' se o houve empate e 'false' se o jogo pode continuar
     */
    private boolean Empates() {
        if (count == 9) {
            return (true);
        }
        return (false);
    }

    /**
     * Verifica se o jogo chegou ao fim
     * 
     * @return Retorna 'true' se o jogo acabou, e 'false' se o ele puder continuar
     */
    private boolean Verifica() {
        if (Vitorias()) {       // Verifica se houve uma vitoria da pessoa
            JOptionPane.showMessageDialog(null, "Parabens, voce ganhou!", "GANHOU!", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("----- Vitoria Humano -----\n"
                             + "--------------------------");
            novojogo();
            numVitorias += 1;
            vit.setText("Vitórias: " + numVitorias);
            return true;
        } else if (Perdas()) {  // Verifica se houve uma vitoria do computador
            JOptionPane.showMessageDialog(null, "Que pena, voce perdeu!", "PERDEU!", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("------- Vitoria IA -------\n"
                             + "--------------------------");
            novojogo();
            numPerdas += 1;
            per.setText("Perdas: " + numPerdas);
            return true;
        } else if (Empates()) { // Verifica se houve um empate
            JOptionPane.showMessageDialog(null, "Deu Velha!", "EMPATOU!", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("--------- Empate ---------\n"
                             + "--------------------------");
            novojogo();
            numEmpates += 1;
            emp.setText("Empates: " + numEmpates);
            return true;
        }

        return false;
    }

    /**
     * Retorna uma lista com as jogadas disponiveis no jogo, de acordo com as
     * valores da matriz atual.
     * 
     * @return Retorna uma lista de jogadas possiveis
     */
    List<Jogada> getJogadaDisponiveis() {
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

    /**
     * Modifica a interface de acordo com a jogada do computador
     * 
     * @param jogada Jogada que a inteligência artificial deseja fazer
     */
    void fazerJogadaComputador(Jogada jogada) {
        res[jogada.i][jogada.j].setText("O");
        res[jogada.i][jogada.j].setEnabled(false);
        count += 1;
    }

    /**
     * Apenas modifica a matriz para verficar as possibilidades de jogada da IA,
     * sem modificar a interface
     * 
     * @param jogada Jogada que a inteligencia artificial deseja fazer
     */
    void fazerJogadaComputadorFalso(Jogada jogada) {
        res[jogada.i][jogada.j].setText("O");
    }

    /**
     * Apenas modifica a matriz para verficar as possibilidades de jogada do jogador humano,
     * sem modificar a interface
     * 
     * @param jogada Jogada que a inteligencia artificial deseja fazer
     */
    void fazerJogadaPessoaFalso(Jogada jogada) {
        res[jogada.i][jogada.j].setText("X");
    }

    /**
     * Retorna a melhor jogada possivel, para a IA
     * 
     * @return Retorna a melhor jogada dentre uma lista de jogadas possiveis
     */
    Jogada retornaMelhorJogada() {
        int MAX = Integer.MIN_VALUE;
        int melhor = -1;

        for (int i = 0; i < nosFilhos.size(); i++) {
            if (MAX < nosFilhos.get(i).pontuacao) {
                MAX = nosFilhos.get(i).pontuacao;
                melhor = i;
            }
        }

        return nosFilhos.get(melhor).jogada;
    }

    /**
     * Retorna o menor valor de uma lista de pontos adquiridos da simulação de jogadas
     * 
     * @param pontos    Lista de pontos adquiridos de acordo com a simulação
     * @return          Retorna o menor valor de uma lista de pontos
     */
    public int retornaMinimo(List<Integer> pontos) {
        int menor = Integer.MAX_VALUE;
        int posicao = -1;

        for (int i = 0; i < pontos.size(); i++) {
            if (pontos.get(i) < menor) {
                menor = pontos.get(i);
                posicao = i;
            }
        }

        return pontos.get(posicao);
    }
    
    /**
     * Retorna o maior valor de uma lista de pontos adquiridos da simulação de jogadas
     * 
     * @param pontos    Lista de pontos adquiridos de acordo com a simulação
     * @return          Retorna o maior valor de uma lista de pontos
     */
    public int retornaMaximo(List<Integer> pontos) {
        int maior = Integer.MIN_VALUE;
        int posicao = -1;

        for (int i = 0; i < pontos.size(); i++) {
            if (pontos.get(i) > maior) {
                maior = pontos.get(i);
                posicao = i;
            }
        }

        return pontos.get(posicao);
    }

    public int minimax(int profundidade, int vez) {
        // Verifica se houve uma vitoria por parte do jogador e da valor -1 a essa
        // possibilidade ;; Lembrete o jogador humano nao pode ganhar para a IA
        if (Vitorias()) {
            return -1;
        }
        // Verifica se houve uma vitoria por parte da IA e da valor +1 a essa possibilidade
        else if (Perdas()) {
            return +1;
        }
        
        // Pega uma lista de jogadas possiveis, se tal lista eh vazia houve um empate,
        // e essa possibilidade recebe pontuacao igual a 0
        List<Jogada> jogadasDisponivelAux = getJogadaDisponiveis();
        if (jogadasDisponivelAux.isEmpty()) {
            return 0;
        }
        List<Integer> pontuacao = new ArrayList<>();

        // Para todas as jogadas possiveis testa recursivamente quais sao as melhores
        // possibilidades de ganho para a IA
        for (int i = 0; i < jogadasDisponivelAux.size(); i++) {
            Jogada jogadaAtual = jogadasDisponivelAux.get(i);

            // Se a vez for do computador, chama recursivamente minimax e armazena o retorno
            // dessa execução, no final adiciona a pontuação de retorno a lista de pontuacoes
            // de todas as jogadas disponiveis, se a profundidade for 0 entao temos que essa
            // eh a proxima jogada do computador, logo salvamos essa jogada para depois compararmos
            // com as outras, e verificarmos se ela eh ou não a melhor jogada a ser efetuada
            if (vez == COMPUTADOR) {
                fazerJogadaComputadorFalso(jogadaAtual);
                int pontuacaoAtual = minimax(profundidade + 1, PESSOA);
                pontuacao.add(pontuacaoAtual);

                if (profundidade == 0) {
                    nosFilhos.add(new Classificacao(pontuacaoAtual, jogadaAtual));
                }
            }
            // Faz a mesma simulacao agora para a vez da pessoa
            else if (vez == PESSOA) {
                fazerJogadaPessoaFalso(jogadaAtual);
                pontuacao.add((minimax(profundidade + 1, COMPUTADOR)));
            }
            res[jogadaAtual.i][jogadaAtual.j].setText("");
        }

        return vez == COMPUTADOR ? retornaMaximo(pontuacao) : retornaMinimo(pontuacao);
    }

    // Quando ocorrer um clique em algum ponto do jogo executar a inteligencia
    // artificial para determinar a jogada da IA
    @Override
    public void actionPerformed(ActionEvent e) {
        // Procura pela jogada efetuada, pega os indices da mesma e altera a interface
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (e.getSource() == res[i][j]) {
                    res[i][j].setText("X");
                    res[i][j].setEnabled(false);
                    count += 1;
                }
            }
        }

        // Verifica se ainda eh possivel continuar, ou se houve uma vitoria, derrota ou empate
        if (Verifica()) {
            return;
        }
        
        // Procura pela melhor jogada possivel para o computador, e efetua a mesma
        long start_time = System.nanoTime();    // Tempo inicial da execucao da IA
        nosFilhos = new ArrayList<>();
        minimax(0, COMPUTADOR);
        Jogada jogadaIA = retornaMelhorJogada();
        long end_time = System.nanoTime();      // Tempo final da execucao da IA
        fazerJogadaComputador(jogadaIA);
        
        // Tempo em ns - Nano segundos para executar a IA
        NumberFormat formatter = new DecimalFormat("#0.00");
        double difference = (end_time - start_time)/1e6;
        System.out.println(   "-------- RODADA " + count + " --------\n"
                            + "Custo em ns: " + formatter.format(difference) + "\n"
                            +"--------------------------");

        nosFilhos.stream().forEach((classificacao) -> {
            System.out.println("Jogada: " + classificacao.jogada
            + " Pontuacao: " + classificacao.pontuacao);
        });
        System.out.println("--------------------------");
        
        // Verifica se houve uma vitoria, derrota ou empate por parte do computador ou se o jogo pode continuar
        Verifica();
    }

    // Inicializa a interface e o jogo
    @SuppressWarnings("unused")
    public static void main(String[] args) throws IOException, InterruptedException {
        InterfaceVI interfaceVI = new InterfaceVI();
        frmVelhaInteligente.setVisible(true);
        System.out.println("+++++ Jogo da Velha - MiniMax +++++\n"
                + "++++++++ Velha Inteligente ++++++++\n\n"
                + "Desenvolvido por: \n"
                + "\tAndressa Andrade,\n"
                + "\tRenata Antunes,\n"
                + "\tVirginia De Paula \n");
    }
}
