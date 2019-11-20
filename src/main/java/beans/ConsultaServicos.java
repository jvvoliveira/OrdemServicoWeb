/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entidades.Servico;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import servico.ServicoServico;

@RequestScoped
@Named("servicosPaginator")
public class ConsultaServicos implements Serializable {

    @Inject
    private ServicoServico servicoServico;

    private List<Servico> servicos;

    public List<Servico> getServicos() {
        if (servicos == null) {
            servicos = servicoServico.consultarTodosServicos();
        }

        return servicos;
    }
}
