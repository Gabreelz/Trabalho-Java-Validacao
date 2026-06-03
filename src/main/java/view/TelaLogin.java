package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.LoginService;
import service.ValidadorEmail;

public class TelaLogin {

    private final Stage owner;

    public TelaLogin(Stage owner) {
        this.owner = owner;
    }

    public void exibir() {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Login");
        dialog.setResizable(false);

        VBox layout = new VBox();
        layout.getStyleClass().add("tela-filha");

        HBox cabecalho = new HBox(12);
        cabecalho.getStyleClass().add("cabecalho-filha");
        cabecalho.setAlignment(Pos.CENTER_LEFT);
        cabecalho.setPadding(new Insets(18, 24, 18, 24));

        Label icone = new Label("🔑");
        icone.getStyleClass().add("cabecalho-icone");

        VBox textos = new VBox(2,
            new Label("Login") {{ getStyleClass().add("cabecalho-titulo"); }},
            new Label("Informe seu e-mail e senha para acessar") {{ getStyleClass().add("cabecalho-subtitulo"); }}
        );
        cabecalho.getChildren().addAll(icone, textos);

        Separator sep = new Separator();
        sep.getStyleClass().add("separador");

        VBox formulario = new VBox(16);
        formulario.getStyleClass().add("formulario");
        formulario.setPadding(new Insets(24, 32, 24, 32));

        Label lblEmail = new Label("E-mail:");
        lblEmail.getStyleClass().add("label-campo");

        TextField campoEmail = new TextField();
        campoEmail.setPromptText("usuario@dominio.com");
        campoEmail.getStyleClass().add("campo-texto");
        campoEmail.setMaxWidth(Double.MAX_VALUE);

        Label lblSenha = new Label("Senha:");
        lblSenha.getStyleClass().add("label-campo");

        PasswordField campoSenha = new PasswordField();
        campoSenha.setPromptText("Senha");
        campoSenha.getStyleClass().add("campo-texto");
        campoSenha.setMaxWidth(Double.MAX_VALUE);

        VBox areaResultado = new VBox(8);
        areaResultado.getStyleClass().add("area-resultado");
        areaResultado.setVisible(false);
        areaResultado.setManaged(false);

        Label lblTitulo = new Label();
        lblTitulo.getStyleClass().add("resultado-titulo");
        Label lblDetalhe = new Label();
        lblDetalhe.getStyleClass().add("resultado-detalhe");
        lblDetalhe.setWrapText(true);
        areaResultado.getChildren().addAll(lblTitulo, lblDetalhe);

        HBox botoes = new HBox(12);
        botoes.setAlignment(Pos.CENTER_RIGHT);
        botoes.setPadding(new Insets(8, 0, 0, 0));

        Button btnLimpar = new Button("🗑 Limpar");
        btnLimpar.getStyleClass().addAll("btn-acao", "btn-limpar");

        Button btnEntrar = new Button("✔ Entrar");
        btnEntrar.getStyleClass().addAll("btn-acao", "btn-validar");
        btnEntrar.setDefaultButton(true);

        btnEntrar.setOnAction(e -> {
            String email = campoEmail.getText().trim();
            String senha = campoSenha.getText();

            campoEmail.getStyleClass().removeAll("campo-sucesso", "campo-erro", "campo-aviso");
            campoSenha.getStyleClass().removeAll("campo-sucesso", "campo-erro", "campo-aviso");
            areaResultado.setVisible(false);
            areaResultado.setManaged(false);

            if (email.isEmpty()) {
                mostrar(areaResultado, lblTitulo, lblDetalhe,
                        "⚠ Campo vazio!", "Digite um e-mail para continuar.", "resultado-aviso");
                campoEmail.getStyleClass().add("campo-aviso");
                return;
            }

            if (senha.isEmpty()) {
                mostrar(areaResultado, lblTitulo, lblDetalhe,
                        "⚠ Campo vazio!", "Digite a senha para continuar.", "resultado-aviso");
                campoSenha.getStyleClass().add("campo-aviso");
                return;
            }

            String resultadoEmail = ValidadorEmail.validar(email);
            if (!resultadoEmail.equals("E-mail valido!")) {
                mostrar(areaResultado, lblTitulo, lblDetalhe,
                        "❌ E-mail inválido", resultadoEmail, "resultado-erro");
                campoEmail.getStyleClass().add("campo-erro");
                return;
            }

            boolean sucesso = LoginService.validarLogin(email, senha);
            if (sucesso) {
                mostrar(areaResultado, lblTitulo, lblDetalhe,
                        "✅ Login realizado", "Bem-vindo! Você acessou o sistema com sucesso.", "resultado-sucesso");
                campoEmail.getStyleClass().add("campo-sucesso");
                campoSenha.getStyleClass().add("campo-sucesso");
            } else {
                mostrar(areaResultado, lblTitulo, lblDetalhe,
                        "❌ Falha no login", "E-mail ou senha incorretos.", "resultado-erro");
                campoEmail.getStyleClass().add("campo-erro");
                campoSenha.getStyleClass().add("campo-erro");
            }
        });

        btnLimpar.setOnAction(e -> {
            campoEmail.clear();
            campoSenha.clear();
            areaResultado.setVisible(false);
            areaResultado.setManaged(false);
            campoEmail.getStyleClass().removeAll("campo-sucesso", "campo-erro", "campo-aviso");
            campoSenha.getStyleClass().removeAll("campo-sucesso", "campo-erro", "campo-aviso");
        });

        botoes.getChildren().addAll(btnLimpar, btnEntrar);
        formulario.getChildren().addAll(lblEmail, campoEmail, lblSenha, campoSenha, areaResultado, botoes);
        layout.getChildren().addAll(cabecalho, sep, formulario);

        dialog.setMinWidth(620);
        dialog.setMinHeight(440);
        Scene scene = new Scene(layout, 620, 440);
        scene.getStylesheets().add(getClass().getResource("/styles/estilo.css").toExternalForm());
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private void mostrar(VBox area, Label titulo, Label detalhe,
                          String txtTit, String txtDet, String css) {
        titulo.setText(txtTit);
        detalhe.setText(txtDet);
        area.getStyleClass().removeAll("resultado-sucesso", "resultado-erro", "resultado-aviso");
        area.getStyleClass().add(css);
        area.setVisible(true);
        area.setManaged(true);
    }
}