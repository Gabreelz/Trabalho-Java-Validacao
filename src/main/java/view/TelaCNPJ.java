package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.ValidadorCNPJ;

/**
 * Tela de Validação de CNPJ — janela filha (modal).
 * Estrutura: view/TelaCNPJ.java
 */
public class TelaCNPJ {

    private final Stage owner;

    public TelaCNPJ(Stage owner) {
        this.owner = owner;
    }

    public void exibir() {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Validação de CNPJ");
        dialog.setResizable(false);

        VBox layout = new VBox();
        layout.getStyleClass().add("tela-filha");

        // ─── Cabeçalho ───────────────────────────────────────────────────────
        HBox cabecalho = new HBox(12);
        cabecalho.getStyleClass().add("cabecalho-filha");
        cabecalho.setAlignment(Pos.CENTER_LEFT);
        cabecalho.setPadding(new Insets(18, 24, 18, 24));

        Label icone = new Label("🏢");
        icone.getStyleClass().add("cabecalho-icone");

        VBox textos = new VBox(2,
            new Label("Validação de CNPJ") {{ getStyleClass().add("cabecalho-titulo"); }},
            new Label("Digite o CNPJ para verificar se é válido") {{ getStyleClass().add("cabecalho-subtitulo"); }}
        );
        cabecalho.getChildren().addAll(icone, textos);

        // ─── Separador ───────────────────────────────────────────────────────
        Separator sep = new Separator();
        sep.getStyleClass().add("separador");

        // ─── Formulário ──────────────────────────────────────────────────────
        VBox formulario = new VBox(16);
        formulario.getStyleClass().add("formulario");
        formulario.setPadding(new Insets(24, 32, 24, 32));

        Label lblCNPJ = new Label("CNPJ:");
        lblCNPJ.getStyleClass().add("label-campo");

        TextField campoCNPJ = new TextField();
        campoCNPJ.setPromptText("Ex: 11.222.333/0001-81");
        campoCNPJ.getStyleClass().add("campo-texto");
        campoCNPJ.setMaxWidth(Double.MAX_VALUE);

        // Máscara automática: 00.000.000/0000-00
        campoCNPJ.textProperty().addListener((obs, anterior, novo) -> {
            String digitos = novo.replaceAll("\\D", "");
            if (digitos.length() > 14) digitos = digitos.substring(0, 14);
            String formatado = formatarCNPJ(digitos);
            if (!formatado.equals(novo)) {
                campoCNPJ.setText(formatado);
                campoCNPJ.positionCaret(formatado.length());
            }
        });

        // Área de resultado
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

        Button btnValidar = new Button("✔ Validar CNPJ");
        btnValidar.getStyleClass().addAll("btn-acao", "btn-validar");
        btnValidar.setDefaultButton(true);

        btnValidar.setOnAction(e -> {
            String cnpj = campoCNPJ.getText().trim();
            if (cnpj.isEmpty()) {
                mostrar(areaResultado, lblTitulo, lblDetalhe,
                        "⚠ Campo vazio!", "Por favor, digite um CNPJ para validar.", "resultado-aviso");
                return;
            }
            boolean valido = ValidadorCNPJ.cnpjValido(cnpj);
            campoCNPJ.getStyleClass().removeAll("campo-sucesso", "campo-erro", "campo-aviso");
            if (valido) {
                mostrar(areaResultado, lblTitulo, lblDetalhe,
                        "✅  CNPJ VÁLIDO!", "O CNPJ informado é válido.", "resultado-sucesso");
                campoCNPJ.getStyleClass().add("campo-sucesso");
            } else {
                mostrar(areaResultado, lblTitulo, lblDetalhe,
                        "❌  CNPJ INVÁLIDO!", "O CNPJ informado não é válido. Verifique os dígitos.", "resultado-erro");
                campoCNPJ.getStyleClass().add("campo-erro");
            }
        });

        btnLimpar.setOnAction(e -> {
            campoCNPJ.clear();
            areaResultado.setVisible(false);
            areaResultado.setManaged(false);
            campoCNPJ.getStyleClass().removeAll("campo-sucesso", "campo-erro", "campo-aviso");
        });

        botoes.getChildren().addAll(btnLimpar, btnValidar);
        formulario.getChildren().addAll(lblCNPJ, campoCNPJ, areaResultado, botoes);
        layout.getChildren().addAll(cabecalho, sep, formulario);

        Scene scene = new Scene(layout, 460, 320);
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

    private String formatarCNPJ(String d) {
        StringBuilder sb = new StringBuilder(d);
        if (sb.length() > 2)  sb.insert(2, '.');
        if (sb.length() > 6)  sb.insert(6, '.');
        if (sb.length() > 10) sb.insert(10, '/');
        if (sb.length() > 15) sb.insert(15, '-');
        return sb.toString();
    }
}
