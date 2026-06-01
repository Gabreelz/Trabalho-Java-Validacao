package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.CsvService;

import java.io.File;

/**
 * Tela de Leitura de CSV.
 * Estrutura: view/TelaCSV.java
 */
public class TelaCSV {

    private final Stage owner;

    public TelaCSV(Stage owner) {
        this.owner = owner;
    }

    public void exibir() {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Leitura de CSV");
        dialog.setResizable(false);

        VBox layout = new VBox();
        layout.getStyleClass().add("tela-filha");

        HBox cabecalho = new HBox(12);
        cabecalho.getStyleClass().add("cabecalho-filha");
        cabecalho.setAlignment(Pos.CENTER_LEFT);
        cabecalho.setPadding(new Insets(18, 24, 18, 24));

        Label icone = new Label("📂");
        icone.getStyleClass().add("cabecalho-icone");

        VBox textos = new VBox(2,
            new Label("Leitura de CSV") {{ getStyleClass().add("cabecalho-titulo"); }},
            new Label("Selecione um arquivo CSV para ler os estados e gerar a saída.") {{ getStyleClass().add("cabecalho-subtitulo"); }}
        );
        cabecalho.getChildren().addAll(icone, textos);

        Separator sep = new Separator();
        sep.getStyleClass().add("separador");

        VBox formulario = new VBox(16);
        formulario.getStyleClass().add("formulario");
        formulario.setPadding(new Insets(24, 32, 24, 32));

        Label lblArquivo = new Label("Arquivo CSV:");
        lblArquivo.getStyleClass().add("label-campo");

        HBox arquivoRow = new HBox(12);
        arquivoRow.setAlignment(Pos.CENTER_LEFT);

        TextField campoArquivo = new TextField();
        campoArquivo.setPromptText("Caminho do arquivo CSV");
        campoArquivo.getStyleClass().add("campo-texto");
        campoArquivo.setMaxWidth(Double.MAX_VALUE);

        Button btnBuscar = new Button("Buscar");
        btnBuscar.getStyleClass().addAll("btn-acao", "btn-validar");

        arquivoRow.getChildren().addAll(campoArquivo, btnBuscar);
        HBox.setHgrow(campoArquivo, Priority.ALWAYS);

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

        Button btnLer = new Button("✔ Ler CSV");
        btnLer.getStyleClass().addAll("btn-acao", "btn-validar");
        btnLer.setDefaultButton(true);

        btnBuscar.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Selecione o arquivo CSV");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
            File escolhido = chooser.showOpenDialog(dialog);
            if (escolhido != null) {
                campoArquivo.setText(escolhido.getAbsolutePath());
            }
        });

        btnLer.setOnAction(e -> {
            areaResultado.setVisible(false);
            areaResultado.setManaged(false);
            campoArquivo.getStyleClass().removeAll("campo-sucesso", "campo-erro", "campo-aviso");

            String caminho = campoArquivo.getText().trim();
            if (caminho.isEmpty()) {
                mostrar(areaResultado, lblTitulo, lblDetalhe,
                        "⚠ Arquivo não informado", "Selecione um arquivo CSV antes de prosseguir.", "resultado-aviso");
                campoArquivo.getStyleClass().add("campo-aviso");
                return;
            }

            CsvService service = new CsvService(caminho);
            try {
                service.lerEstados();
                mostrar(areaResultado, lblTitulo, lblDetalhe,
                        "✅ CSV processado", "Arquivo lido com sucesso. Saída gerada em 'saida_estados.txt'.", "resultado-sucesso");
                campoArquivo.getStyleClass().add("campo-sucesso");
            } catch (Exception ex) {
                mostrar(areaResultado, lblTitulo, lblDetalhe,
                        "❌ Falha ao ler CSV", "Não foi possível processar o arquivo: " + ex.getMessage(), "resultado-erro");
                campoArquivo.getStyleClass().add("campo-erro");
            }
        });

        btnLimpar.setOnAction(e -> {
            campoArquivo.clear();
            areaResultado.setVisible(false);
            areaResultado.setManaged(false);
            campoArquivo.getStyleClass().removeAll("campo-sucesso", "campo-erro", "campo-aviso");
        });

        botoes.getChildren().addAll(btnLimpar, btnLer);
        formulario.getChildren().addAll(lblArquivo, arquivoRow, areaResultado, botoes);
        layout.getChildren().addAll(cabecalho, sep, formulario);

        Scene scene = new Scene(layout, 620, 380);
        scene.getStylesheets().add(getClass().getResource("/styles/estilo.css").toExternalForm());
        dialog.setMinWidth(620);
        dialog.setMinHeight(380);
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
