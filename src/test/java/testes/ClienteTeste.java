package testes;

import entidades.Cliente;
import javax.naming.NamingException;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import servico.ClienteServico;

public class ClienteTeste extends Teste{
 
    
    private ClienteServico clienteServico;
    @Before
    public void setUp() throws NamingException {
        clienteServico = (ClienteServico) container.getContext().lookup("java:global/classes/ejb/ClienteServico!servico.ClienteServico");
    }

    @After
    public void tearDown() {
        clienteServico = null;
    }
    
//    @Test
//    public void existeCliente() {
//        Cliente cliente = clienteServico.criar();
//        cliente.setCpf("740.707.044-00");
//        assertTrue(compradorServico.existe(comprador));
//    }
}
