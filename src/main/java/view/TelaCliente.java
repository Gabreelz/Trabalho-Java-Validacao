package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Cliente;
import service.ClienteService;
import service.ValidadorEmail;

/**
 * Tela de Cadastro de Cliente.
 * Estrutura: view/TelaCliente.java
 */
public class TelaCliente {

    private final Stage owner;

    public TelaCliente(Stage owner) {
        this.owner = owner;
    }

    public void exibir() {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Cadastro de Cliente");
        dialog.setResizable(false);

        VBox layout = new VBox();
        layout.getStyleClass().add("tela-filha");

        // ─── Cabeçalho ───────────────────────────────────────────────────────
        HBox cabecalho = new HBox(12);
        cabecalho.getStyleClass().add("cabecalho-filha");
        cabecalho.setAlignment(Pos.CENTER_LEFT);
        cabecalho.setPadding(new Insets(18, 24, 18, 24));

        Label icone = new Label("👤");
        icone.getStyleClass().add("cabecalho-icone");

        VBox textos = new VBox(2,
            new Label("Cadastro de Cliente") {{ getStyleClass().add("cabecalho-titulo"); }},
            new Label("Registre um cliente com CPF/CNPJ e e-mail válidos") {{ getStyleClass().add("cabecalho-subtitulo"); }}
        );
        cabecalho.getChildren().addAll(icone, textos);

        Separator sep = new Separator();
        sep.getStyleClass().add("separador");

        VBox formulario = new VBox(16);
        formulario.getStyleClass().add("formulario");
        formulario.setPadding(new Insets(24, 32, 24, 32));

        Label lblNome = new Label("Nome:");
        lblNome.getStyleClass().add("label-campo");

        TextField campoNome = new TextField();
        campoNome.setPromptText("Nome completo");
        campoNome.getStyleClass().add("campo-texto");
        campoNome.setMaxWidth(Double.MAX_VALUE);

        Label lblTipo = new Label("Tipo de documento:");
        lblTipo.getStyleClass().add("label-campo");

        ToggleGroup tipoDocumento = new ToggleGroup();
        RadioButton rbCPF = new RadioButton("CPF");
        rbCPF.setToggleGroup(tipoDocumento);
        rbCPF.setSelected(true);
        RadioButton rbCNPJ = new RadioButton("CNPJ");
        rbCNPJ.setToggleGroup(tipoDocumento);

        HBox rowTipo = new HBox(12, rbCPF, rbCNPJ);
        rowTipo.setAlignment(Pos.CENTER_LEFT);

        Label lblDocumento = new Label("Documento:");
        lblDocumento.getStyleClass().add("label-campo");

        TextField campoDocumento = new TextField();
        campoDocumento.setPromptText("Ex: 123.456.789-09");
        campoDocumento.getStyleClass().add("campo-texto");
        campoDocumento.setMaxWidth(Double.MAX_VALUE);

        Label lblEmail = new Label("E-mail:");
        lblEmail.getStyleClass().add("label-campo");

        TextField campoEmail = new TextField();
        campoEmail.setPromptText("exemplo@dominio.com");
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

        campoDocumento.textProperty().addListener((obs, antigo, novo) -> {
            boolean isCnpj = rbCNPJ.isSelected();
            String digitos = novo.replaceAll("\\D", "");
            if (isCnpj) {
                if (digitos.length() > 14) digitos = digitos.substring(0, 14);
                String formatado = formatarCNPJ(digitos);
                if (!formatado.equals(novo)) {
                    campoDocumento.setText(formatado);
                    campoDocumento.positionCaret(formatado.length());
                }
            } else {
                if (digitos.length() > 11) digitos = digitos.substring(0, 11);
                String formatado = formatarCPF(digitos);
                if (!formatado.equals(novo)) {
                    campoDocumento.setText(formatado);
                    campoDocumento.positionCaret(formatado.length());
                }
            }
        });

        rbCPF.setOnAction(e -> {
            campoDocumento.clear();
            campoDocumento.setPromptText("Ex: 123.456.789-09");
            campoDocumento.getStyleClass().removeAll("campo-sucesso", "campo-erro", "campo-aviso");
            areaResultado.setVisible(false);
            areaResultado.setManaged(false);
        });

        rbCNPJ.setOnAction(e -> {
            campoDocumento.clear();
            campoDocumento.setPromptText("Ex: 11.222.333/0001-81");
            campoDocumento.getStyleClass().removeAll("campo-sucesso", "campo-erro", "campo-aviso");
            areaResultado.setVisible(false);
            areaResultado.setManaged(false);
        });

        HBox botoes = new HBox(12);
        botoes.setAlignment(Pos.CENTER_RIGHT);
        botoes.setPadding(new Insets(8, 0, 0, 0));

        Button btnLimpar = new Button("🗑 Limpar");
        btnLimpar.getStyleClass().addAll("btn-acao", "btn-limpar");

        Button btnCadastrar = new Button("✔ Cadastrar");
        btnCadastrar.getStyleClass().addAll("btn-acao", "btn-validar");
        btnCadastrar.setDefaultButton(true);

        btnCadastrar.setOnAction(e -> {
            areaResultado.setVisible(false);
            areaResultado.setManaged(false);
            campoNome.getStyleClass().removeAll("campo-sucesso", "campo-erro", "campo-aviso");
            campoDocumento.getStyleClass().removeAll("campo-sucesso", "campo-erro", "campo-aviso");
            campoEmail.getStyleClass().removeAll("campo-sucesso", "campo-erro", "campo-aviso");

            String nome = campoNome.getText().trim();
            String documento = campoDocumento.getText().trim();
            String email = campoEmail.getText().trim();
            boolean isCnpj = rbCNPJ.isSelected();

            if (nome.isEmpty()) {
                mostrar(areaResultado, lblTitulo, lblDetalhe,
                        "⚠ Nome obrigatorio", "Informe o nome do cliente.", "resultado-aviso");
                campoNome.getStyleClass().add("campo-aviso");
                return;
            }

            if (documento.isEmpty()) {
                mostrar(areaResultado, lblTitulo, lblDetalhe,
                        "⚠ Documento obrigatorio", "Informe o CPF ou CNPJ do cliente.", "resultado-aviso");
                campoDocumento.getStyleClass().add("campo-aviso");
                return;
            }

            String resultadoEmail = ValidadorEmail.validar(email);
            if (!resultadoEmail.equals("E-mail valido!")) {
                mostrar(areaResultado, lblTitulo, lblDetalhe,
                        "⚠ E-mail invalido", resultadoEmail, "resultado-aviso");
                campoEmail.getStyleClass().add("campo-aviso");
                return;
            }

            Cliente cliente = new Cliente(nome, documento, isCnpj, email);
            String resposta = new ClienteService().cadastrar(cliente);

            if (resposta.toLowerCase().contains("erro")) {
                mostrar(areaResultado, lblTitulo, lblDetalhe,
                        "❌ Falha ao cadastrar", resposta, "resultado-erro");
                campoNome.getStyleClass().add("campo-erro");
                campoDocumento.getStyleClass().add("campo-erro");
                campoEmail.getStyleClass().add("campo-erro");
            } else {
                mostrar(areaResultado, lblTitulo, lblDetalhe,
                        "✅ Cliente cadastrado", resposta, "resultado-sucesso");
                campoNome.getStyleClass().add("campo-sucesso");
                campoDocumento.getStyleClass().add("campo-sucesso");
                campoEmail.getStyleClass().add("campo-sucesso");
            }
        });

        btnLimpar.setOnAction(e -> {
            campoNome.clear();
            campoDocumento.clear();
            campoEmail.clear();
            areaResultado.setVisible(false);
            areaResultado.setManaged(false);
            campoNome.getStyleClass().removeAll("campo-sucesso", "campo-erro", "campo-aviso");
            campoDocumento.getStyleClass().removeAll("campo-sucesso", "campo-erro", "campo-aviso");
            campoEmail.getStyleClass().removeAll("campo-sucesso", "campo-erro", "campo-aviso");
        });

        botoes.getChildren().addAll(btnLimpar, btnCadastrar);
        formulario.getChildren().addAll(
                lblNome, campoNome,
                lblTipo, rowTipo,
                lblDocumento, campoDocumento,
                lblEmail, campoEmail,
                areaResultado,
                botoes
        );
        layout.getChildren().addAll(cabecalho, sep, formulario);

        Scene scene = new Scene(layout, 640, 520);
        scene.getStylesheets().add(getClass().getResource("/styles/estilo.css").toExternalForm());
        dialog.setMinWidth(640);
        dialog.setMinHeight(520);
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
        if (sb.length() > 3) sb.insert(3, '.');
        if (sb.length() > 7) sb.insert(7, '.');
        if (sb.length() > 11) sb.insert(11, '-');
        return sb.toString();
    }

    private String formatarCNPJ(String d) {
        StringBuilder sb = new StringBuilder(d);
        if (sb.length() > 2) sb.insert(2, '.');
        if (sb.length() > 6) sb.insert(6, '.');
        if (sb.length() > 10) sb.insert(10, '/');
        if (sb.length() > 15) sb.insert(15, '-');
        return sb.toString();
    }
}
