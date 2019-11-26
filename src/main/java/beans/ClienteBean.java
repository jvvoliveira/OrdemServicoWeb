package beans;

import entidades.Cliente;
import entidades.Endereco;
import entidades.Telefone;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;
import servico.ClienteServico;

@RequestScoped
@Named("clienteBean")
public class ClienteBean extends Bean<Cliente> implements Serializable {

    private List<Cliente> clientes;
    private Endereco endereco;
    private Telefone telefone;
    private List<Telefone> telefones;
    
    @Inject
    private ClienteServico clienteServico;
    
    @Override
    protected void iniciarCampos() {
        setEntidade(clienteServico.criar());
        endereco = new Endereco();
        telefone = new Telefone();
        telefones = new ArrayList<Telefone>();
//        entidade.setTelefones(telefones);
        entidade.setEndereco(endereco);
        entidade.addTelefones(telefone);
        
    }

    @Override
    protected boolean salvar(Cliente entidade) {
        clienteServico.persistir(entidade);
        return true;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }
    
    public void onRowEdit() {
        FacesMessage msg = new FacesMessage("Car Edited");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel() {
        FacesMessage msg = new FacesMessage("Edit Cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
 
    public void onAddNew() {
        // Add one new car to the table:
        Telefone tel = new Telefone();
        telefones.add(tel);
        FacesMessage msg = new FacesMessage("New Car added");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
}
