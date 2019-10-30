package servico;

import entidades.Funcionario;
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

@Stateless(name = "ejb/FuncionarioServico")
@LocalBean
@ValidateOnExecution(type = ExecutableType.ALL)
public class FuncionarioServico extends ServicoS<Funcionario>{

    @PostConstruct
    public void init() {
        super.setClasse(Funcionario.class);
    }
    
    @Override
    public Funcionario criar() {
        return new Funcionario();
    }
    
    @Override
    public boolean existe(@NotNull Funcionario func) {
        TypedQuery<Funcionario> query
                = entityManager.createNamedQuery(Funcionario.FUNCIONARIO_POR_MATRICULA, classe);
        query.setParameter(1, func.getMatricula());
        return !query.getResultList().isEmpty();
    }
    
    @TransactionAttribute(SUPPORTS)
    public Funcionario consultarPorMatricula(@NotNull String matricula) {
        return super.consultarEntidade(new Object[] {matricula}, Funcionario.FUNCIONARIO_POR_MATRICULA);
    }
    
    @TransactionAttribute(SUPPORTS)
    public List<Funcionario> consultarPorNome(@NotNull String nome) {
        return super.consultarEntidades(new Object[] {nome}, Funcionario.FUNCIONARIO_POR_NOME);
    }
    
}
