package servico;

import entidades.Equipamento;
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
import validadores.Valida_Matricula;

@Stateless(name = "ejb/EquipamentoServico")
@LocalBean
@ValidateOnExecution(type = ExecutableType.ALL)
public class EquipamentoServico extends ServicoS<Equipamento>{
    
    @PostConstruct
    public void init() {
        super.setClasse(Equipamento.class);
    }
    
    @Override
    public Equipamento criar() {
        return new Equipamento();
    }
    
    @Override
    public boolean existe(@NotNull Equipamento equip) {
        TypedQuery<Equipamento> query
                = entityManager.createNamedQuery(Equipamento.EQUIPAMENTO_POR_ID, classe);
        query.setParameter(1, equip.getId());
        return !query.getResultList().isEmpty();
    }
    
    @TransactionAttribute(SUPPORTS)
    public Equipamento consultaPorId(@NotNull Long id){
        return super.consultarEntidade(new Object[] {id}, Equipamento.EQUIPAMENTO_POR_ID);
    }
    
    @TransactionAttribute(SUPPORTS)
    public List<Equipamento> consultaPorFuncionarioMatricula(@NotNull String matricula){
        return super.consultarEntidades(new Object[] {matricula}, Equipamento.EQUIPAMENTO_POR_FUNCIONARIO);
    }
    
    @TransactionAttribute(SUPPORTS)
    public List<Equipamento> consultaPoMarca(@NotNull String marca){
        return super.consultarEntidades(new Object[] {marca}, Equipamento.EQUIPAMENTO_POR_MARCA);
    }
    
    @TransactionAttribute(SUPPORTS)
    public List<Equipamento> consultaPorServicoId(@NotNull Long id){
        return super.consultarEntidades(new Object[] {id}, Equipamento.EQUIPAMENTO_POR_SERVICO);
    }
    
    @TransactionAttribute(SUPPORTS)
    public List<Equipamento> consultaPorClienteCpf(@NotNull String cpf){
        return super.consultarEntidades(new Object[] {cpf}, Equipamento.EQUIPAMENTO_POR_CLIENTE);
    }
    
    
}
