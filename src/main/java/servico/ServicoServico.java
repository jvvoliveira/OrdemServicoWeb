
package servico;

import entidades.Servico;
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
import utils.Status;

@Stateless(name = "ejb/ServicoServico")
@LocalBean
@ValidateOnExecution(type = ExecutableType.ALL)
public class ServicoServico extends ServicoS<Servico> {

    @PostConstruct
    public void init() {
        super.setClasse(Servico.class);
    }
    
    @Override
    public Servico criar() {
        return new Servico();
    }

    @Override
    public boolean existe(@NotNull Servico servico) {
        TypedQuery<Servico> query
                = entityManager.createNamedQuery(Servico.SERVICO_POR_ID, classe);
        query.setParameter(1, servico.getId());
        return !query.getResultList().isEmpty();
    }
    
    @TransactionAttribute(SUPPORTS)
    public List<Servico> consultarPorClienteCPF(@NotNull @CPF String cpf) {
        return super.consultarEntidades(new Object[] {cpf}, Servico.SERVICO_POR_CLIENTE_CPF);
    } 
    
    @TransactionAttribute(SUPPORTS)
    public List<Servico> consultarPorFuncionarioMatricula(@NotNull  String matricula) {
        return super.consultarEntidades(new Object[] {matricula}, Servico.SERVICO_POR_FUNCIONARIO_MATRICULA);
    } 
    
    @TransactionAttribute(SUPPORTS)
    public List<Servico> consultarPorStatus(@NotNull Status status) {
        return super.consultarEntidades(new Object[] {status}, Servico.SERVICO_POR_STATUS);
    }
    
    @TransactionAttribute(SUPPORTS)
    public List<Servico> consultarTodosServicos(){
        return super.consultarEntidades(new Object[] {}, Servico.TODOS_SERVICO);
    }
}
