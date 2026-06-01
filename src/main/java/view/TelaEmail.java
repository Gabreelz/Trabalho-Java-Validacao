package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.ValidadorEmail;

/**
 * Tela de Validação de E-mail — janela filha (modal).
 * Estrutura: view/TelaEmail.java
 */
public class TelaEmail {

    private final Stage owner;

    public TelaEmail(Stage owner) {
        this.owner = owner;
    }

    public void exibir() {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Validação de E-mail");
        dialog.setResizable(false);

        VBox layout = new VBox();
        layout.getStyleClass().add("tela-filha");

        HBox cabecalho = new HBox(12);
        cabecalho.getStyleClass().add("cabecalho-filha");
        cabecalho.setAlignment(Pos.CENTER_LEFT);
        cabecalho.setPadding(new Insets(18, 24, 18, 24));

        Label icone = new Label("✉");
        icone.getStyleClass().add("cabecalho-icone");

        VBox textos = new VBox(2,
            new Label("Validação de E-mail") {{ getStyleClass().add("cabecalho-titulo"); }},
            new Label("Digite o e-mail para verificar se é válido") {{ getStyleClass().add("cabecalho-subtitulo"); }}
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
        campoEmail.setPromptText("Ex: usuario@dominio.com");
        campoEmail.getStyleClass().add("campo-texto");
        campoEmail.setMaxWidth(Double.MAX_VALUE);

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

        Button btnValidar = new Button("✔ Validar E-mail");
        btnValidar.getStyleClass().addAll("btn-acao", "btn-validar");
        btnValidar.setDefaultButton(true);

        btnValidar.setOnAction(e -> {
            String email = campoEmail.getText().trim();
            if (email.isEmpty()) {
                mostrar(areaResultado, lblTitulo, lblDetalhe,
                        "⚠ Campo vazio!", "Por favor, digite um e-mail para validar.", "resultado-aviso");
                campoEmail.getStyleClass().removeAll("campo-sucesso", "campo-erro", "campo-aviso");
                campoEmail.getStyleClass().add("campo-aviso");
                return;
            }

            String resultado = ValidadorEmail.validar(email);
            campoEmail.getStyleClass().removeAll("campo-sucesso", "campo-erro", "campo-aviso");

            if (resultado.equals("E-mail valido!")) {
                mostrar(areaResultado, lblTitulo, lblDetalhe,
                        "✅  E-MAIL VÁLIDO!", "O e-mail informado é válido.", "resultado-sucesso");
                campoEmail.getStyleClass().add("campo-sucesso");
            } else {
                mostrar(areaResultado, lblTitulo, lblDetalhe,
                        "❌  E-MAIL INVÁLIDO!", resultado, "resultado-erro");
                campoEmail.getStyleClass().add("campo-erro");
            }
        });

        btnLimpar.setOnAction(e -> {
            campoEmail.clear();
            areaResultado.setVisible(false);
            areaResultado.setManaged(false);
            campoEmail.getStyleClass().removeAll("campo-sucesso", "campo-erro", "campo-aviso");
        });

        botoes.getChildren().addAll(btnLimpar, btnValidar);
        formulario.getChildren().addAll(lblEmail, campoEmail, areaResultado, botoes);
        layout.getChildren().addAll(cabecalho, sep, formulario);

        Scene scene = new Scene(layout, 480, 320);
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
