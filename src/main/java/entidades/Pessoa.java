package entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "TB_PESSOA")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PESS_TIPO", discriminatorType = DiscriminatorType.STRING, length = 1)
public abstract class Pessoa extends Entidade {
    
    @NotNull(message = "Nome não pode ser nulo")
    @Size(max = 100, message = "Caracteres a mais para nome")
    @Column(name = "PESS_NOME")
    private String nome;
    
    @Size(max = 100, message = "Caracteres a mais para email")
    @Email(message = "Email inválido")
    @NotNull(message = "Email não pode ser nulo")
    @Column(name = "PESS_EMAIL")
    private String email;
    
    @NotNull(message = "Data de Nascimento não pode ser nulo")
    @Past(message = "Data de Nascimento inválida")
    @Temporal(TemporalType.DATE)
    @Column(name = "PESS_DATA_NASCIMENTO")
    private Date dataNasc;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }
}
