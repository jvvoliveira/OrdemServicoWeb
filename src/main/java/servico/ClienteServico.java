package servico;

import entidades.Cliente;
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

@Stateless(name = "ejb/ClienteServico")
@LocalBean
@ValidateOnExecution(type = ExecutableType.ALL)
public class ClienteServico extends ServicoS<Cliente> {

    @PostConstruct
    public void init() {
        super.setClasse(Cliente.class);
    }
    
    @Override
    public Cliente criar() {
        return new Cliente();
    }
    
    @Override
    public boolean existe(@NotNull Cliente cliente) {
        TypedQuery<Cliente> query
                = entityManager.createNamedQuery(Cliente.CLIENTE_POR_CPF, classe);
        query.setParameter(1, cliente.getCpf());
        return !query.getResultList().isEmpty();
    }
    
    @TransactionAttribute(SUPPORTS)
    public Cliente consultarPorCPF(@CPF String cpf) {
        return super.consultarEntidade(new Object[] {cpf}, Cliente.CLIENTE_POR_CPF);
    }    
    
    @TransactionAttribute(SUPPORTS)
    public List<Cliente> consultarPorNome(@NotNull String nome){
        return super.consultarEntidades(new Object[] {nome}, Cliente.CLIENTE_POR_NOME);
    }
    
    @TransactionAttribute(SUPPORTS)
    public List<Cliente> consultarPorTelefone(@NotNull String numeroTelefone){
        return super.consultarEntidades(new Object[] {numeroTelefone}, Cliente.CLIENTE_POR_TELEFONE);
    }
    
    @TransactionAttribute(SUPPORTS)
    public List<Cliente> consultarPorBairro(@NotNull String bairro){
        return super.consultarEntidades(new Object[] {bairro}, Cliente.CLIENTE_POR_BAIRRO);
    }
}
