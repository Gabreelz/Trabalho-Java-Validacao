# 🛡 ValidaSystem — Trabalho Java (2ª Entrega)

Sistema de validação com interface gráfica JavaFX.

---

## 📁 Estrutura do Projeto (Segunda Entrega)

```
Trabalho-Java-Validacao/
├── pom.xml                          ← Dependências Maven + JavaFX
├── compilar.ps1                     ← Script alternativo sem Maven
└── src/
    └── main/
        ├── java/
        │   ├── module-info.java
        │   └── br/validacao/
        │       ├── MainApp.java          ← Ponto de entrada JavaFX
        │       ├── TelaPrincipal.java    ← Tela principal MDI
        │       ├── TelaValidacaoCPF.java ← Janela de validação CPF
        │       ├── TelaValidacaoCNPJ.java← Janela de validação CNPJ
        │       ├── ValidacaoCPF.java     ← Lógica CPF (reaproveitada)
        │       └── ValidacaoCNPJ.java    ← Lógica CNPJ (reaproveitada)
        └── resources/
            └── styles/
                └── estilo.css           ← Tema visual dark premium
```

---

## ▶️ Como Rodar

### Opção 1 — Via Maven (Recomendado)
> Precisa ter o [Maven](https://maven.apache.org/download.cgi) instalado.
```bash
mvn javafx:run
```

### Opção 2 — Via VS Code
1. Abra o projeto no VS Code
2. Certifique-se de ter a extensão **Extension Pack for Java** instalada
3. Abra `MainApp.java`
4. Clique em **▶ Run** no topo do arquivo

### Opção 3 — Script PowerShell
```powershell
.\compilar.ps1
```
*(Detecta o JDK automaticamente e baixa o JavaFX se necessário)*

---

## 🖥️ Telas Implementadas

| Tela | Responsável | Descrição |
|------|-------------|-----------|
| **Tela Principal** | *(seu nome)* | Menu horizontal MDI + barra de status |
| **Validação CPF** | *(seu nome)* | Dialog com máscara automática + resultado colorido |
| **Validação CNPJ** | *(seu nome)* | Dialog com máscara automática + resultado colorido |

---

## 🎨 Funcionalidades das Telas

### Tela Principal
- Menu horizontal com botões coloridos para cada funcionalidade
- Área central de boas-vindas com atalhos rápidos
- Barra de status no rodapé
- Estrutura **MDI** (janelas filhas sobre a principal)

### Tela Validação CPF / CNPJ
- Máscara automática enquanto digita (ex: `123.456.789-09`)
- Validação com os **mesmos algoritmos** da 1ª entrega
- Resultado visual colorido:
  - 🟢 **Verde** = válido
  - 🔴 **Vermelho** = inválido
  - 🟡 **Amarelo** = campo vazio
- Botão "Limpar" para resetar
- Abre como **dialog modal** sobre a tela principal

---

## 🔑 Credenciais de Login
```
Usuário: admin
Senha:   Senha1@
```
*(Herdado da 1ª entrega — Login.java)*
