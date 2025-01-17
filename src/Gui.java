import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Gui extends JFrame implements ActionListener {
    JButton[][] btns = new JButton[3][3];
    Boolean turnoX = true;
    

    Gui(){
        //layout
        setLayout(new GridLayout(3,3));
        UIManager.put("Button.disabledText", Color.black);

        //adiciona os btns
        for (int i = 0; i < btns.length; i++) {
            for (int j = 0; j < btns[i].length; j++ ){
                JButton btn = new JButton();
                btn.setFocusable(false);
                btn.setFont(btn.getFont().deriveFont(30f));
                btn.setBackground(Color.white);

                btns[i][j] = btn;
                btns[i][j].addActionListener(this);
                add(btns[i][j]);
            }
        }

        //adiciona as bordas
        for (int i = 0; i < btns.length; i++) {
            for (int j = 0; j < btns[i].length; j++) {
            int top = 0, left = 0, bottom = (i < 2) ? 1 : 0, right = (j < 2) ? 1 : 0;
            btns[i][j].setBorder(javax.swing.BorderFactory.createMatteBorder(top, left, bottom, right, java.awt.Color.BLACK));
            }
        }

        //configs da janela
        setTitle("Jogo da velha");
        setSize(400, 400);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < btns.length; i++){
            for (int j = 0; j < btns[i].length; j++) {
                //eventos dos btns
                if (e.getSource() == btns[i][j]) {
                    if(turnoX){
                        btns[i][j].setText("X");
                        turnoX = false;
                    } else {
                        btns[i][j].setText("O");
                        turnoX = true;
                    }
                    btns[i][j].setEnabled(false);
                    verificarVitoria();
                }
            }
        }
    }

    void verificarVitoria(){
        for (int i = 0; i < btns.length; i++){
            //linhas
            if (btns[i][0].getText().equals(btns[i][1].getText()) && btns[i][0].getText().equals(btns[i][2].getText()) && !btns[i][0].getText().equals("")) {
                fimJogo();
                return;
            }

            //colunas
            else if (btns[0][i].getText().equals(btns[1][i].getText()) && btns[0][i].getText().equals(btns[2][i].getText()) && !btns[0][i].getText().equals("")) {
                fimJogo();
                return;
            }
        }
        
        //diagonal
        if(btns[0][0].getText().equals(btns[1][1].getText()) && btns[0][0].getText().equals(btns[2][2].getText()) && !btns[0][0].getText().equals("")){
            fimJogo();
            return;
        }

        else if(btns[0][2].getText().equals(btns[1][1].getText()) && btns[0][2].getText().equals(btns[2][0].getText()) && !btns[0][2].getText().equals("")){
            fimJogo();
            return;
        }

        //verificar velha
        int btnsUsados = 0;
        for(int i = 0; i < btns.length; i++){
            for (int j = 0; j < btns[i].length; j++){
                if (btns[i][j].isEnabled() == false){
                    btnsUsados++;
                }        
            }
        }

        if (btnsUsados == 9){
            String msg = "Empate!\n" + "Desseja jogar novamente?"; 
            int escolha = JOptionPane.showOptionDialog(this, msg, getTitle(), JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, 0);

            if (escolha == 0){
                resetarJogo();
            }
            
            else{
                dispose();
            }
        } 
    }

    void fimJogo(){
        String msg = "Parabéns " + (turnoX ? "O" : "X") + " você ganhou!\n" + 
                     "Desseja Jogar novamente?" ;
        int escolha = JOptionPane.showOptionDialog(this, msg, getTitle(), JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, 0);

        if (escolha == 0) {
            resetarJogo();
        }

        else{
            dispose();
        }
    }
    
    void resetarJogo(){
        for (int i = 0; i < btns.length; i++){
            for (int j = 0; j < btns[i].length; j++){
                btns[i][j].setText("");
                btns[i][j].setEnabled(true);
            }
        }
    }
}