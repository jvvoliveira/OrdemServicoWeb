
package servico;

import entidades.ServicoE;
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

@Stateless(name = "ejb/ServicoEServico")
@LocalBean
@ValidateOnExecution(type = ExecutableType.ALL)
public class ServicoEServico extends Servico<ServicoE> {

    @PostConstruct
    public void init() {
        super.setClasse(ServicoE.class);
    }
    
    @Override
    public ServicoE criar() {
        return new ServicoE();
    }

    @Override
    public boolean existe(@NotNull ServicoE servico) {
        TypedQuery<ServicoE> query
                = entityManager.createNamedQuery(ServicoE.SERVICOE_POR_CLIENTE_CPF, classe);
        query.setParameter(1, servico.getId());
        return !query.getResultList().isEmpty();
    }
    
    @TransactionAttribute(SUPPORTS)
    public List<ServicoE> consultarPorClienteCPF(@NotNull String cpf) {
        return super.consultarEntidades(new Object[] {cpf}, ServicoE.SERVICOE_POR_CLIENTE_CPF);
    } 
    
    @TransactionAttribute(SUPPORTS)
    public List<ServicoE> consultarPorFuncionarioMatricula(@NotNull String matricula) {
        return super.consultarEntidades(new Object[] {matricula}, ServicoE.SERVICOE_POR_FUNCIONARIO_MATRICULA);
    } 
    
    @TransactionAttribute(SUPPORTS)
    public List<ServicoE> consultarPorStatus(@NotNull String status) {
        return super.consultarEntidades(new Object[] {status}, ServicoE.SERVICOE_POR_STATUS);
    } 
}
