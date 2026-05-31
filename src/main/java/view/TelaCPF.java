package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.ValidadorCPF;

/**
 * Tela de Validação de CPF — janela filha (modal).
 * Estrutura: view/TelaCPF.java
 */
public class TelaCPF {

    private final Stage owner;

    public TelaCPF(Stage owner) {
        this.owner = owner;
    }

    public void exibir() {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Validação de CPF");
        dialog.setResizable(false);

        VBox layout = new VBox();
        layout.getStyleClass().add("tela-filha");

        // ─── Cabeçalho ───────────────────────────────────────────────────────
        HBox cabecalho = new HBox(12);
        cabecalho.getStyleClass().add("cabecalho-filha");
        cabecalho.setAlignment(Pos.CENTER_LEFT);
        cabecalho.setPadding(new Insets(18, 24, 18, 24));

        Label icone = new Label("📋");
        icone.getStyleClass().add("cabecalho-icone");

        VBox textos = new VBox(2,
            new Label("Validação de CPF") {{ getStyleClass().add("cabecalho-titulo"); }},
            new Label("Digite o CPF para verificar se é válido") {{ getStyleClass().add("cabecalho-subtitulo"); }}
        );
        cabecalho.getChildren().addAll(icone, textos);

        // ─── Separador ───────────────────────────────────────────────────────
        Separator sep = new Separator();
        sep.getStyleClass().add("separador");

        // ─── Formulário ──────────────────────────────────────────────────────
        VBox formulario = new VBox(16);
        formulario.getStyleClass().add("formulario");
        formulario.setPadding(new Insets(24, 32, 24, 32));

        Label lblCPF = new Label("CPF:");
        lblCPF.getStyleClass().add("label-campo");

        TextField campoCPF = new TextField();
        campoCPF.setPromptText("Ex: 123.456.789-09");
        campoCPF.getStyleClass().add("campo-texto");
        campoCPF.setMaxWidth(Double.MAX_VALUE);

        // Máscara automática: 000.000.000-00
        campoCPF.textProperty().addListener((obs, anterior, novo) -> {
            String digitos = novo.replaceAll("\\D", "");
            if (digitos.length() > 11) digitos = digitos.substring(0, 11);
            String formatado = formatarCPF(digitos);
            if (!formatado.equals(novo)) {
                campoCPF.setText(formatado);
                campoCPF.positionCaret(formatado.length());
            }
        });

        // Área de resultado (oculta até validar)
        VBox areaResultado = new VBox(8);
        areaResultado.getStyleClass().add("area-resultado");
        areaResultado.setVisible(false);
        areaResultado.setManaged(false);

        Label lblTitulo  = new Label();
        lblTitulo.getStyleClass().add("resultado-titulo");
        Label lblDetalhe = new Label();
        lblDetalhe.getStyleClass().add("resultado-detalhe");
        lblDetalhe.setWrapText(true);

        areaResultado.getChildren().addAll(lblTitulo, lblDetalhe);

        // ─── Botões ───────────────────────────────────────────────────────────
        HBox botoes = new HBox(12);
        botoes.setAlignment(Pos.CENTER_RIGHT);
        botoes.setPadding(new Insets(8, 0, 0, 0));

        Button btnLimpar = new Button("🗑 Limpar");
        btnLimpar.getStyleClass().addAll("btn-acao", "btn-limpar");

        Button btnValidar = new Button("✔ Validar CPF");
        btnValidar.getStyleClass().addAll("btn-acao", "btn-validar");
        btnValidar.setDefaultButton(true);

        btnValidar.setOnAction(e -> {
            String cpf = campoCPF.getText().trim();
            if (cpf.isEmpty()) {
                mostrar(areaResultado, lblTitulo, lblDetalhe,
                        "⚠ Campo vazio!", "Por favor, digite um CPF para validar.", "resultado-aviso");
                return;
            }
            boolean valido = ValidadorCPF.cpfValido(cpf);
            campoCPF.getStyleClass().removeAll("campo-sucesso", "campo-erro", "campo-aviso");
            if (valido) {
                mostrar(areaResultado, lblTitulo, lblDetalhe,
                        "✅  CPF VÁLIDO!", "O CPF informado é válido.", "resultado-sucesso");
                campoCPF.getStyleClass().add("campo-sucesso");
            } else {
                mostrar(areaResultado, lblTitulo, lblDetalhe,
                        "❌  CPF INVÁLIDO!", "O CPF informado não é válido. Verifique os dígitos.", "resultado-erro");
                campoCPF.getStyleClass().add("campo-erro");
            }
        });

        btnLimpar.setOnAction(e -> {
            campoCPF.clear();
            areaResultado.setVisible(false);
            areaResultado.setManaged(false);
            campoCPF.getStyleClass().removeAll("campo-sucesso", "campo-erro", "campo-aviso");
        });

        botoes.getChildren().addAll(btnLimpar, btnValidar);
        formulario.getChildren().addAll(lblCPF, campoCPF, areaResultado, botoes);
        layout.getChildren().addAll(cabecalho, sep, formulario);

        Scene scene = new Scene(layout, 440, 320);
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

    private String formatarCPF(String d) {
        StringBuilder sb = new StringBuilder(d);
        if (sb.length() > 3)  sb.insert(3, '.');
        if (sb.length() > 7)  sb.insert(7, '.');
        if (sb.length() > 11) sb.insert(11, '-');
        return sb.toString();
    }
}
