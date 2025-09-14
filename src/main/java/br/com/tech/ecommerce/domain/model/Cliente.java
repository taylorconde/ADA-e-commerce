package src.main.java.br.com.tech.ecommerce.domain.model;

import src.main.java.br.com.tech.ecommerce.domain.model.enums.TipoDocumento;

import java.io.Serial;
import java.io.Serializable;

public class Cliente implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String id;
    private String nome;
    private String documentoId;
    private TipoDocumento tipoDocumento;
    private String email;

    public Cliente(String id, String nome, String documentoId, TipoDocumento tipoDocumento, String email) {
        this.id = id;
        this.nome = nome;
        this.documentoId = documentoId;
        this.tipoDocumento = tipoDocumento;
        this.email = email;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String novoNome) { this.nome = novoNome; }
    public String getDocumentoId() { return documentoId; }
    public void setDocumentoId(String documentoId) { this.documentoId = documentoId; }
    public TipoDocumento getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(TipoDocumento tipoDocumento) { this.tipoDocumento = tipoDocumento; }
    public String getEmail() { return email; }
    public void setEmail(String novoEmail) { this.email = novoEmail; }
}