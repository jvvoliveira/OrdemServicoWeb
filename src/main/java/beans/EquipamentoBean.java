package beans;

import entidades.Equipamento;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import servico.EquipamentoServico;

@RequestScoped
@Named("equipBean")
public class EquipamentoBean extends Bean<Equipamento> implements Serializable {
    
    @Inject
    private EquipamentoServico equipServico;
    
    @Override
    protected void iniciarCampos() {
        setEntidade(equipServico.criar());
    }

    @Override
    protected boolean salvar(Equipamento entidade) {
       equipServico.persistir(entidade);
       return true;
    }
    
}
