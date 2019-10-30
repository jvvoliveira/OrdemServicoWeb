package entidades;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TB_TELEFONE")
@NamedQueries(
        {
            @NamedQuery(
                    name = Telefone.TELEFONE_POR_NUMERO,
                    query = "SELECT t FROM Telefone t WHERE t.numero = ?1"
            )
            ,
            @NamedQuery(
                    name = Telefone.TELEFONE_POR_DDD,
                    query = "SELECT t FROM Telefone t WHERE t.ddd = ?1"
            )
            ,
            @NamedQuery(
                    name = Telefone.TELEFONE_POR_CLIENTE_CPF,
                    query = "SELECT t FROM Telefone t WHERE t.cliente IN (SELECT c FROM Cliente c WHERE c.cpf = ?1)"
            )
            ,
            @NamedQuery(
                    name = Telefone.TELEFONE_POR_CLIENTE_NOME,
                    query = "SELECT t FROM Telefone t WHERE t.cliente IN (SELECT c FROM Cliente c WHERE c.nome LIKE ?1)"
            )
        }
)
public class Telefone extends Entidade {

    public static final String TELEFONE_POR_NUMERO = "TelefonePorNumero";
    public static final String TELEFONE_POR_DDD = "TelefonePorDDD";
    public static final String TELEFONE_POR_CLIENTE_CPF = "TelefonePorClienteCPF";
    public static final String TELEFONE_POR_CLIENTE_NOME = "TelefonePorClienteNome";

    @NotNull(message = "Número de telefone não pode ser nulo")
    @Size(min = 8, max = 12, message = "Quantidade incorreta de caracteres para número de telefone")
    @Column(name = "TEL_NUMERO")
    private String numero;

    @NotNull(message = "DDD não pode ser nulo")
    @Size(min = 2, max = 4, message = "Quantidade incorreta de caracteres para DDD")
    @Column(name = "TEL_DDD")
    private String ddd;

    @NotNull(message = "Telefone precisa estar associado a um cliente")
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_ID_CLI", referencedColumnName = "ID")
    private Cliente cliente;

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
