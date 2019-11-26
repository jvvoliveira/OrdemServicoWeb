package beans;

import entidades.Funcionario;
import entidades.Servico;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FlowEvent;
import static org.primefaces.shaded.commons.io.IOUtils.skip;
import servico.ClienteServico;
import servico.FuncionarioServico;
import servico.ServicoServico;


@RequestScoped
@Named("servicoBean")
public class ServicoBean extends Bean<Servico> implements Serializable {
    
    private List<Servico> servicos;
    
    private Funcionario atendente;
    
    private String cpfCliente;
    
    @Inject
    private ServicoServico servicoServico;
    
    @Inject
    private FuncionarioServico funcionarioServico;
    
    @Inject
    private ClienteServico clienteServico;

    @Override
    protected void iniciarCampos() {
        setEntidade(servicoServico.criar());
        setAtendente(funcionarioServico.consultarPorId(1L));
    }

    @Override
//    protected boolean salvar(Servico entidade) throws ExcecaoNegocio {
    protected boolean salvar(Servico entidade){
        System.out.println(cpfCliente);
        entidade.setCliente(clienteServico.consultarPorCPF(cpfCliente));
        entidade.setFuncionario(atendente);
        servicoServico.persistir(entidade);
        return true;
    }
    
    public List<Servico> getServicos() {
        if (servicos == null) {
            servicos = servicoServico.consultarTodosServicos();
        }

        return servicos;
    }

    public Funcionario getAtendente() {
        return atendente;
    }

    public void setAtendente(Funcionario atendente) {
        this.atendente = atendente;
    }

    public ServicoServico getServicoServico() {
        
        return servicoServico;
    }

    public void setServicoServico(ServicoServico servicoServico) {
        this.servicoServico = servicoServico;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public FuncionarioServico getFuncionarioServico() {
        return funcionarioServico;
    }

    public void setFuncionarioServico(FuncionarioServico funcionarioServico) {
        this.funcionarioServico = funcionarioServico;
    }
    
    public String onFlowProcess(FlowEvent event) {
        if(skip){
            skip = false;   //reset in case user goes back
            return "confirm";
        }
        else {
            return event.getNewStep();
        }
    }
}
