package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.RelatorioService;

/**
 * Tela de Relatório de Clientes.
 * Estrutura: view/TelaRelatorio.java
 */
public class TelaRelatorio {

    private final Stage owner;

    public TelaRelatorio(Stage owner) {
        this.owner = owner;
    }

    public void exibir() {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Relatório de Clientes");
        dialog.setResizable(false);

        VBox layout = new VBox();
        layout.getStyleClass().add("tela-filha");

        HBox cabecalho = new HBox(12);
        cabecalho.getStyleClass().add("cabecalho-filha");
        cabecalho.setAlignment(Pos.CENTER_LEFT);
        cabecalho.setPadding(new Insets(18, 24, 18, 24));

        Label icone = new Label("📊");
        icone.getStyleClass().add("cabecalho-icone");

        VBox textos = new VBox(2,
            new Label("Relatório de Clientes") {{ getStyleClass().add("cabecalho-titulo"); }},
            new Label("Visualize o conteúdo cadastrado e gere um relatório dos clientes.") {{ getStyleClass().add("cabecalho-subtitulo"); }}
        );
        cabecalho.getChildren().addAll(icone, textos);

        Separator sep = new Separator();
        sep.getStyleClass().add("separador");

        VBox formulario = new VBox(16);
        formulario.getStyleClass().add("formulario");
        formulario.setPadding(new Insets(24, 32, 24, 32));

        TextArea textoRelatorio = new TextArea();
        textoRelatorio.setEditable(false);
        textoRelatorio.setWrapText(true);
        textoRelatorio.getStyleClass().add("texto-relatorio");
        textoRelatorio.setPromptText("Clique em Gerar relatório para carregar os clientes cadastrados.");
        textoRelatorio.setPrefHeight(420);
        textoRelatorio.setPrefWidth(520);
        VBox.setVgrow(textoRelatorio, Priority.ALWAYS);

        HBox botoes = new HBox(12);
        botoes.setAlignment(Pos.CENTER_RIGHT);
        botoes.setPadding(new Insets(8, 0, 0, 0));

        Button btnGerar = new Button("✔ Gerar relatório");
        btnGerar.getStyleClass().addAll("btn-acao", "btn-validar");
        btnGerar.setDefaultButton(true);

        Button btnFechar = new Button("Fechar");
        btnFechar.getStyleClass().addAll("btn-acao", "btn-limpar");

        btnGerar.setOnAction(e -> {
            String conteudo = RelatorioService.getConteudo();
            textoRelatorio.setText(conteudo);
            if (conteudo.toLowerCase().contains("erro")) {
                textoRelatorio.getStyleClass().removeAll("campo-sucesso");
                textoRelatorio.getStyleClass().add("campo-erro");
            } else {
                textoRelatorio.getStyleClass().removeAll("campo-erro");
                textoRelatorio.getStyleClass().add("campo-sucesso");
            }
        });

        btnFechar.setOnAction(e -> dialog.close());

        botoes.getChildren().addAll(btnFechar, btnGerar);
        formulario.getChildren().addAll(textoRelatorio, botoes);

        layout.getChildren().addAll(cabecalho, sep, formulario);

        Scene scene = new Scene(layout, 680, 520);
        scene.getStylesheets().add(getClass().getResource("/styles/estilo.css").toExternalForm());
        dialog.setMinWidth(680);
        dialog.setMinHeight(520);
        dialog.setScene(scene);
        dialog.showAndWait();
    }
}
