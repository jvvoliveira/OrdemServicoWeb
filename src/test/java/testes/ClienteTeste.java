package testes;

import entidades.Cliente;
import java.util.Date;
import java.util.List;
import javax.ejb.EJBException;
import javax.naming.NamingException;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import servico.ClienteServico;

public class ClienteTeste extends Teste {

    private ClienteServico clienteServico;

    @Before
    public void setUp() throws NamingException {
        clienteServico = (ClienteServico) container.getContext().lookup("java:global/classes/ejb/ClienteServico!servico.ClienteServico");
    }

    @After
    public void tearDown() {
        clienteServico = null;
    }

    @Test
    public void existeCliente() {
        Cliente cliente = clienteServico.criar();
        cliente.setCpf("297.121.520-22");
        assertTrue(clienteServico.existe(cliente));
    }

    @Test
    public void getClientePorCPF() {
        Cliente cliente = clienteServico.consultarPorCPF("025.696.830-60");
        assertNotNull(cliente);
        assertEquals("acf@mail.com", cliente.getEmail());
    }

    @Test(expected = EJBException.class)
    public void consultarClienteCPFInvalido() {
        try {
            clienteServico.consultarPorCPF("222.11sdsdsd");
        } catch (EJBException ex) {
            assertTrue(ex.getCause() instanceof ConstraintViolationException);
            throw ex;
        }
    }

    @Test(expected = EJBException.class)
    public void getClientePorCPFInexistente() {
        try {
            Cliente cliente = clienteServico.consultarPorCPF("111.793.034-31");
        } catch (EJBException ex) {
            assertTrue(ex.getCause() instanceof NoResultException);
            throw ex;
        }
    }

    @Test
    public void getClientePorNome() {
        List<Cliente> clientes = clienteServico.consultarPorNome("Bruna Amancio Vilanova");
        assertEquals(1, clientes.size());
        assertEquals("327.392.580-97", clientes.get(0).getCpf());
    }

    @Test
    public void getClientePorNomeInexistente() {
        List<Cliente> clientes = clienteServico.consultarPorNome("AAAAAAAA");
        assertEquals(0, clientes.size());
    }

    @Test
    public void getClientePorTelefone() {
        List<Cliente> clientes = clienteServico.consultarPorTelefone("34648400");
        assertEquals(1, clientes.size());
        assertEquals("Amanda Costa e Silva", clientes.get(0).getNome());
    }

    @Test
    public void getClientePorBairro() {
        List<Cliente> clientes = clienteServico.consultarPorBairro("Boa Vista");
        assertEquals(1, clientes.size());
        assertEquals("bvpf@parisofrutas.com", clientes.get(0).getEmail());
    }

    @Test
    public void persistirCliente() {
        Cliente cliente = clienteServico.criar();
        cliente.setCpf("111.793.034-31");
        cliente.setNome("João Victor");
        cliente.setEmail("joao@email.com");
        cliente.setDataNasc(new Date(99, 5, 31));

        clienteServico.persistir(cliente);

        assertNotNull(cliente.getId());
    }

    @Test(expected = EJBException.class)
    public void persistirClienteCPFInvalido() {
        try {
            Cliente cliente = clienteServico.criar();
            cliente.setCpf("11wdwwr3434");
            cliente.setNome("João Victor");
            cliente.setEmail("joao@email.com");
            cliente.setDataNasc(new Date(99, 5, 31));
            clienteServico.persistir(cliente);
        } catch (EJBException ex) {
            assertTrue(ex.getCause() instanceof ConstraintViolationException);
            
            ConstraintViolationException erro = (ConstraintViolationException) ex.getCause();
            for (ConstraintViolation erroValidacao : erro.getConstraintViolations()) {
                assertThat(erroValidacao.getMessage(),
                        CoreMatchers.anyOf(
                            startsWith("Erro de validação do CPF(xxx.xxx.xxx-xx) / CNPJ (xx.xxx.xxx/xxxx-xx)")
                        ));
            }
            throw ex;
        }
    }
    
//    @Test
//    public void atualizarCliente() { 
//        Cliente cliente = clienteServico.consultarPorId(6L);
//        assertEquals("Arlindo Cavalcanti Filho", cliente.getNome());
//        cliente.setNome("Ronaldo"); 
//        clienteServico.atualizar(cliente);
//        cliente = clienteServico.consultarPorId(6L);
//        assertEquals("Ronaldo", cliente.getNome());
//    }
}
