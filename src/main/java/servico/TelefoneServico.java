
package servico;

import entidades.Telefone;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.SUPPORTS;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;

@Stateless(name = "ejb/TelefoneServico")
@LocalBean
@ValidateOnExecution(type = ExecutableType.ALL)
public class TelefoneServico extends Servico<Telefone>{

    @Override
    public Telefone criar() {
        return new Telefone();
    }

    @Override
    public boolean existe(@NotNull Telefone telefone) {
        TypedQuery<Telefone> query
                = entityManager.createNamedQuery(Telefone.TELEFONE_POR_NUMERO, classe);
        query.setParameter(1, telefone.getNumero());
        return !query.getResultList().isEmpty();
    }

    @TransactionAttribute(SUPPORTS)
    public List<Telefone> consultarPorClienteCPF(@NotNull String cpf) {
        return super.consultarEntidades(new Object[] {cpf}, Telefone.TELEFONE_POR_CLIENTE_CPF);
    } 
    
    @TransactionAttribute(SUPPORTS)
    public List<Telefone> consultarPorClienteNome(@NotNull String ClienteNome) {
        return super.consultarEntidades(new Object[] {ClienteNome}, Telefone.TELEFONE_POR_CLIENTE_NOME);
    }
    
    @TransactionAttribute(SUPPORTS)
    public List<Telefone> consultarPorDDD(@NotNull String ddd) {
        return super.consultarEntidades(new Object[] {ddd}, Telefone.TELEFONE_POR_DDD);
    }
    
    @TransactionAttribute(SUPPORTS)
    public Telefone consultarPorNumero(@NotNull String numero) {
        return super.consultarEntidade(new Object[] {numero}, Telefone.TELEFONE_POR_NUMERO);
    }
    
}
