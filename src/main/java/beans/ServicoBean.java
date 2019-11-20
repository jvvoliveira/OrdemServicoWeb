package beans;

import entidades.Servico;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import servico.ServicoServico;


@RequestScoped
@Named("servicoBean")
public class ServicoBean extends Bean<Servico> implements Serializable {
    
    private List<Servico> servicos;

    @Inject
    private ServicoServico servicoServico;

    @Override
    protected void iniciarCampos() {
        setEntidade(servicoServico.criar());
    }

    @Override
//    protected boolean salvar(Servico entidade) throws ExcecaoNegocio {
    protected boolean salvar(Servico entidade){
        servicoServico.persistir(entidade);
        return true;
    }
    
    public List<Servico> getServicos() {
        if (servicos == null) {
            servicos = servicoServico.consultarTodosServicos();
        }

        return servicos;
    }



    public ServicoServico getServicoServico() {
        return servicoServico;
    }

    public void setServicoServico(ServicoServico servicoServico) {
        this.servicoServico = servicoServico;
    }
}
