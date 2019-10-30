package entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;
import validadores.ValidaCPF_CNPJ;

@Entity
@DiscriminatorValue(value = "C")
@NamedQueries(
        {
            @NamedQuery(
                    name = Cliente.CLIENTE_POR_CPF,
                    query = "SELECT c FROM Cliente c WHERE c.cpf = ?1"
            )
            ,
            @NamedQuery(
                    name = Cliente.CLIENTE_POR_NOME,
                    query = "SELECT c FROM Cliente c WHERE c.nome LIKE ?1"
            )
            ,
            @NamedQuery(
                    name = Cliente.CLIENTE_POR_TELEFONE,
                    query = "SELECT c FROM Cliente c WHERE EXISTS "
                    + "(SELECT t FROM Telefone t WHERE t.cliente = c AND t.numero = ?1)"
            )
            ,
            @NamedQuery(
                    name = Cliente.CLIENTE_POR_BAIRRO,
                    query = "SELECT c FROM Cliente c WHERE EXISTS "
                    + "(SELECT e FROM Endereco e WHERE c.endereco = e AND e.bairro LIKE ?1)"
            )
        }
)
public class Cliente extends Pessoa {

    public static final String CLIENTE_POR_CPF = "ClientePorCPF";
    public static final String CLIENTE_POR_NOME = "ClientePorNome";
    public static final String CLIENTE_POR_TELEFONE = "ClientePorTelefone";
    public static final String CLIENTE_POR_BAIRRO = "ClientePorBairro";

    @ValidaCPF_CNPJ
    @Size(min = 11, max = 18, message = "CPF com quantidade incorreta de caracteres")
    @Column(name = "CLI_CPF", unique = true)
    private String cpf;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Telefone> telefones;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "FK_END", referencedColumnName = "ID")
    private Endereco endereco;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "cliente")
    private List<Servico> servicos;

    public Cliente() {
        this.telefones = new ArrayList();
        this.servicos = new ArrayList();
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    public void addTelefones(Telefone telefone) {
        this.telefones.add(telefone);
        telefone.setCliente(this);
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
        if (!(endereco == null)) {
            this.endereco.setCliente(this);
        }
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public Servico getServico(long id) {
        for (Servico serv : servicos) {
            if (serv.getId() == id) {
                return serv;
            }
        }
        return null;
    }

    public void addServicos(Servico servico) {
        this.servicos.add(servico);
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }
}
