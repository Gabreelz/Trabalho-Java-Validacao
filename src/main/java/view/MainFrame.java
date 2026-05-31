package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Tela Principal — estrutura MDI com menu horizontal.
 * Estrutura: view/MainFrame.java
 */
public class MainFrame {

    private final BorderPane root;
    private final Stage primaryStage;
    private final Label statusBar;

    public MainFrame(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.root = new BorderPane();
        this.statusBar = new Label("Pronto");

        root.getStyleClass().add("tela-principal");

        construirMenuTopo();
        construirAreaConteudo();
        construirStatusBar();
    }

    // ─── MENU HORIZONTAL (TOPO) ──────────────────────────────────────────────

    private void construirMenuTopo() {
        HBox menuTopo = new HBox(12);
        menuTopo.getStyleClass().add("menu-topo");
        menuTopo.setAlignment(Pos.CENTER_LEFT);
        menuTopo.setPadding(new Insets(14, 20, 14, 20));

        Label titulo = new Label("🛡 ValidaSystem");
        titulo.getStyleClass().add("menu-titulo");

        Region espacador = new Region();
        HBox.setHgrow(espacador, Priority.ALWAYS);

        Button btnCPF  = criarBotaoMenu("📋 CPF",   "btn-menu-cpf");
        Button btnCNPJ = criarBotaoMenu("🏢 CNPJ",  "btn-menu-cnpj");

        // Botões de outros grupos (serão implementados pelos colegas)
        Button btnEmail     = criarBotaoMenu("✉ Email",     "btn-menu-outros");
        Button btnLogin     = criarBotaoMenu("🔑 Login",    "btn-menu-outros");
        Button btnCliente   = criarBotaoMenu("👤 Cliente",  "btn-menu-outros");
        Button btnCSV       = criarBotaoMenu("📂 CSV",      "btn-menu-outros");
        Button btnRelatorio = criarBotaoMenu("📊 Relatório","btn-menu-outros");

        btnCPF.setOnAction(e  -> abrirTela("cpf"));
        btnCNPJ.setOnAction(e -> abrirTela("cnpj"));

        menuTopo.getChildren().addAll(titulo, espacador, btnCPF, btnCNPJ,
                btnEmail, btnLogin, btnCliente, btnCSV, btnRelatorio);
        root.setTop(menuTopo);
    }

    private Button criarBotaoMenu(String texto, String cssClass) {
        Button btn = new Button(texto);
        btn.getStyleClass().addAll("btn-menu", cssClass);
        return btn;
    }

    // ─── ÁREA CENTRAL (BOAS-VINDAS / ATALHOS) ────────────────────────────────

    private void construirAreaConteudo() {
        StackPane areaConteudo = new StackPane();
        areaConteudo.getStyleClass().add("area-conteudo");

        VBox bemVindo = new VBox(18);
        bemVindo.setAlignment(Pos.CENTER);

        Label icone = new Label("🛡");
        icone.getStyleClass().add("boas-vindas-icone");

        Label titulo = new Label("Sistema de Validação");
        titulo.getStyleClass().add("boas-vindas-titulo");

        Label subtitulo = new Label("Selecione uma opção no menu acima para começar.");
        subtitulo.getStyleClass().add("boas-vindas-subtitulo");

        HBox atalhos = new HBox(16);
        atalhos.setAlignment(Pos.CENTER);

        Button aCPF  = criarAtalho("📋", "Validar CPF",  () -> abrirTela("cpf"));
        Button aCNPJ = criarAtalho("🏢", "Validar CNPJ", () -> abrirTela("cnpj"));

        atalhos.getChildren().addAll(aCPF, aCNPJ);
        bemVindo.getChildren().addAll(icone, titulo, subtitulo, atalhos);
        areaConteudo.getChildren().add(bemVindo);

        root.setCenter(areaConteudo);
    }

    private Button criarAtalho(String icone, String texto, Runnable acao) {
        Button btn = new Button();
        btn.getStyleClass().add("btn-atalho");
        btn.setOnAction(e -> acao.run());

        VBox conteudo = new VBox(6, new Label(icone) {{
            getStyleClass().add("btn-atalho-icone");
        }}, new Label(texto) {{
            getStyleClass().add("btn-atalho-texto");
        }});
        conteudo.setAlignment(Pos.CENTER);
        btn.setGraphic(conteudo);
        return btn;
    }

    // ─── STATUS BAR (RODAPÉ) ─────────────────────────────────────────────────

    private void construirStatusBar() {
        HBox barra = new HBox();
        barra.getStyleClass().add("status-bar");
        barra.setPadding(new Insets(6, 16, 6, 16));
        barra.setAlignment(Pos.CENTER_LEFT);

        statusBar.getStyleClass().add("status-texto");

        Region esp = new Region();
        HBox.setHgrow(esp, Priority.ALWAYS);

        Label versao = new Label("v1.0  |  Trabalho Java");
        versao.getStyleClass().add("status-versao");

        barra.getChildren().addAll(statusBar, esp, versao);
        root.setBottom(barra);
    }

    // ─── ABRIR TELAS FILHAS ──────────────────────────────────────────────────

    private void abrirTela(String tipo) {
        switch (tipo) {
            case "cpf"  -> { setStatus("Validação de CPF..."); new TelaCPF(primaryStage).exibir(); }
            case "cnpj" -> { setStatus("Validação de CNPJ..."); new TelaCNPJ(primaryStage).exibir(); }
        }
        setStatus("Pronto");
    }

    public void setStatus(String msg) { statusBar.setText(msg); }
    public BorderPane getRoot() { return root; }
}
