package servico;

import entidades.Cliente;
import entidades.Endereco;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.SUPPORTS;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;
import org.hibernate.validator.constraints.br.CPF;

@Stateless(name = "ejb/EnderecoServico")
@LocalBean
@ValidateOnExecution(type = ExecutableType.ALL)
public class EnderecoServico extends ServicoS<Endereco> {
    
    @PostConstruct
    public void init() {
        super.setClasse(Endereco.class);
    }
    
    @Override
    public Endereco criar() {
        return new Endereco();
    }
    
    @Override
    public boolean existe(@NotNull Endereco endereco) {
        TypedQuery<Endereco> query
                = entityManager.createNamedQuery(Endereco.ENDERECO_POR_CPF, classe);
        query.setParameter(1, endereco.getCliente().getCpf());
        return !query.getResultList().isEmpty();
    }
    
    @TransactionAttribute(SUPPORTS)
    public Endereco consultarPorCPF(@CPF String cpf) {
        return super.consultarEntidade(new Object[] {cpf}, Endereco.ENDERECO_POR_CPF);
    } 
    
}
