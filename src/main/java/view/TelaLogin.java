package view;

import service.LoginService;
import service.ValidadorEmail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JLabel lblMensagem;

    public TelaLogin() {

        setTitle("Sistema de Cadastro - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JPanel pnlEmail = new JPanel(new FlowLayout());
        pnlEmail.add(new JLabel("E-mail:"));
        txtEmail = new JTextField(20);
        pnlEmail.add(txtEmail);


        JPanel pnlSenha = new JPanel(new FlowLayout());
        pnlSenha.add(new JLabel("Senha:"));
        txtSenha = new JPasswordField(20);
        pnlSenha.add(txtSenha);


        JPanel pnlBotao = new JPanel(new FlowLayout());
        btnEntrar = new JButton("Entrar");
        pnlBotao.add(btnEntrar);


        lblMensagem = new JLabel("", SwingConstants.CENTER);
        lblMensagem.setForeground(Color.RED);

        add(pnlEmail);
        add(pnlSenha);
        add(pnlBotao);
        add(lblMensagem);

        configurarAcoes();
    }

    private void configurarAcoes() {
        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazerLogin();
            }
        });
    }

    private void fazerLogin() {
        String email = txtEmail.getText();
        String senha = new String(txtSenha.getPassword());

        lblMensagem.setText("");
        lblMensagem.setForeground(Color.RED);

        String resultadoEmail = ValidadorEmail.validar(email);
        if (!resultadoEmail.equals("E-mail valido!")) {
            lblMensagem.setText(resultadoEmail);
            return;
        }

        boolean sucesso = LoginService.validarLogin(email, senha);

        if (sucesso) {
            lblMensagem.setForeground(Color.GREEN);
            lblMensagem.setText("Login efetuado com sucesso!");


        } else {
            lblMensagem.setText("E-mail ou senha incorretos.");
        }
    }
}