package testes;

import entidades.Cliente;
import entidades.Endereco;
import entidades.Telefone;
import java.util.Date;
import java.util.List;
import javax.ejb.EJBException;
import javax.naming.NamingException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import servico.EnderecoServico;

public class EnderecoTeste extends Teste{    
    
    private EnderecoServico enderecoServico;
    
    @Before
    public void setUp() throws NamingException {
        enderecoServico = (EnderecoServico) container.getContext().lookup("java:global/classes/ejb/EnderecoServico!servico.EnderecoServico");
    }
    
    @After
    public void tearDown() {
        enderecoServico = null;
    }
    
    @Test
    public void enderecoPorCPF(){
        Endereco endereco = enderecoServico.consultarPorCPF("025.696.830-60");
        assertNotNull(endereco);
    }

    @Test
    public void enderecoClienteComMaisDeUmServico(){
        List<Endereco> enderecos = enderecoServico.EnderecoPorClienteComMaisDeUmServico();
        assertEquals(enderecos.size(), 1);
    }
    
    @Test
    public void persistirEnderecoInvalido(){
        try{
            
        Endereco endereco = enderecoServico.criar();
        Cliente cliente = new Cliente();
        cliente.setDataNasc(new Date(99, 5, 31));
        cliente.setNome("João Victor");
        cliente.setEmail("joao-vv@email.com");
        
        endereco.setBairro("Pixete");
        endereco.setCep("54430710");
        endereco.setCidade("São Lourenço da Mata");
        endereco.setCliente(cliente);
        endereco.setComplemento("casa");
        endereco.setId(Long.valueOf(23));
        endereco.setNumero(101);
        endereco.setRua("");
        
        enderecoServico.persistir(endereco);       
            

        }catch (EJBException ex) {
            assertTrue(ex.getCause() instanceof ConstraintViolationException);
        
    
        ConstraintViolationException erro = (ConstraintViolationException) ex.getCause();
            for (ConstraintViolation erroValidacao : erro.getConstraintViolations()) {
                assertThat(erroValidacao.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("Cidade não pode ser nulo"),
                                startsWith("Logradouro não pode ser nulo"),
                                startsWith("Número não pode ser nulo"),
                                startsWith("CEP não pode ser nulo"),
                                startsWith("Endereço precisa estar associado a um cliente")
                        ));
            }
            throw ex;
        }
    }
    
    
    @Test
    public void persistirEndereco(){
        Endereco endereco = enderecoServico.criar();
        Cliente cliente = new Cliente();
        cliente.setDataNasc(new Date(99, 5, 31));
        cliente.setNome("João Victor");
        cliente.setEmail("joao-vv@email.com");
        
        endereco.setBairro("Pixete");
        endereco.setCep("54430710");
        endereco.setCidade("São Lourenço da Mata");
        endereco.setCliente(cliente);
        endereco.setComplemento("casa");
        endereco.setId(Long.valueOf(23));
        endereco.setNumero(101);
        endereco.setRua("Rua Adolfo Maranhão");
        
        enderecoServico.persistir(endereco);
        
        assertNotNull(endereco.getId());
        
    }
    
    
    
    
    
    
    
    
    
    
}
