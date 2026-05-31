package model;

/**
 * Modelo de dados do Cliente.
 * Estrutura: model/Cliente.java
 */
public class Cliente {

    private String nome;
    private String documento; // CPF ou CNPJ
    private boolean isCnpj;
    private String email;

    public Cliente(String nome, String documento, boolean isCnpj, String email) {
        this.nome = nome;
        this.documento = documento;
        this.isCnpj = isCnpj;
        this.email = email;
    }

    // ─── Getters e Setters ────────────────────────────────────────────────────

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public boolean isCnpj() { return isCnpj; }
    public void setCnpj(boolean cnpj) { isCnpj = cnpj; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTipoDocumento() {
        return isCnpj ? "CNPJ" : "CPF";
    }

    @Override
    public String toString() {
        return "Cliente{nome='" + nome + "', " + getTipoDocumento() + "='" + documento + "', email='" + email + "'}";
    }
}
