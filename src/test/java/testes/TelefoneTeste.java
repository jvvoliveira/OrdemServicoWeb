package testes;

import entidades.Cliente;
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
import servico.TelefoneServico;

public class TelefoneTeste extends Teste {

    private TelefoneServico telefoneServico;

    @Before
    public void setUp() throws NamingException {
        telefoneServico = (TelefoneServico) container.getContext().lookup("java:global/classes/ejb/TelefoneServico!servico.TelefoneServico");
    }

    @After
    public void tearDown() {
        telefoneServico = null;
    }

    @Test
    public void existeTelefone() {
        Telefone telefone = telefoneServico.criar();
        telefone.setNumero("998753008");
        assertTrue(telefoneServico.existe(telefone));
    }

    @Test
    public void getTelefonePorClienteCPF() {
        List<Telefone> telefones = telefoneServico.consultarPorClienteCPF("297.121.520-22");
        assertNotNull(telefones);
        assertEquals(3, telefones.size());
        assertEquals("34648400", telefones.get(0).getNumero());
        assertEquals("998753008", telefones.get(1).getNumero());
        assertEquals("987652079", telefones.get(2).getNumero());
    }

    @Test
    public void getTelefonePorClienteCPFInexistente() {
        List<Telefone> telefones = telefoneServico.consultarPorClienteCPF("744.455.190-14");
        assertEquals(0, telefones.size());
    }

    @Test
    public void getTelefonePorClienteNome() {
        List<Telefone> telefones = telefoneServico.consultarPorClienteNome("Boa Vista Paraiso das Frutas ME");
        assertNotNull(telefones);
        assertEquals(2, telefones.size());
        assertEquals("34658709", telefones.get(0).getNumero());
        assertEquals("34658708", telefones.get(1).getNumero());
    }

    @Test
    public void getTelefonePorClienteNomeInexistente() {
        List<Telefone> telefones = telefoneServico.consultarPorClienteNome("AAAAAAA");
        assertEquals(0, telefones.size());
    }

    @Test
    public void getTelefonePorDDD() {
        List<Telefone> telefones = telefoneServico.consultarPorDDD("81");
        assertNotNull(telefones);
        assertEquals(8, telefones.size());
        assertEquals("34648400", telefones.get(0).getNumero());
        assertEquals("987600987", telefones.get(4).getNumero());
        assertEquals("987609908", telefones.get(7).getNumero());
    }

    @Test
    public void getTelefonePorNumero() {
        Telefone telefone = telefoneServico.consultarPorNumero("987609908");
        assertNotNull(telefone);
        assertEquals(8L, telefone.getCliente().getId(), 0);
    }

    @Test
    public void persistirTelefone() {
        Cliente c = new Cliente();
        c.setDataNasc(new Date(99, 5, 31));
        c.setNome("João Victor");
        c.setEmail("joao-vv@email.com");

        Telefone telefone = telefoneServico.criar();
        telefone.setNumero("32683268");
        telefone.setDdd("11");
        telefone.setCliente(c);

        telefoneServico.persistir(telefone);

        assertNotNull(telefone.getId());
    }
    
    @Test(expected = EJBException.class)
    public void persistirTelefoneInvalidoQuantidadeIncorretaNumeros() {
        try {
            Cliente c = new Cliente();
        c.setDataNasc(new Date(99, 5, 31));
        c.setNome("João Victor");
        c.setEmail("joao-vv@email.com");

        Telefone telefone = telefoneServico.criar();
        telefone.setNumero("3");
        telefone.setDdd("11345345345");
        telefone.setCliente(c);

        telefoneServico.persistir(telefone);
        } catch (EJBException ex) {
            assertTrue(ex.getCause() instanceof ConstraintViolationException);

            ConstraintViolationException erro = (ConstraintViolationException) ex.getCause();
            for (ConstraintViolation erroValidacao : erro.getConstraintViolations()) {
                assertThat(erroValidacao.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("Quantidade incorreta de caracteres para número de telefone"),
                                startsWith("Quantidade incorreta de caracteres para DDD")
                        ));
            }
            throw ex;
        }
    }

    @Test(expected = EJBException.class)
    public void persistirTelefoneInvalido() {
        try {
            Telefone telefone = telefoneServico.criar();
            telefoneServico.persistir(telefone);
        } catch (EJBException ex) {
            assertTrue(ex.getCause() instanceof ConstraintViolationException);

            ConstraintViolationException erro = (ConstraintViolationException) ex.getCause();
            for (ConstraintViolation erroValidacao : erro.getConstraintViolations()) {
                assertThat(erroValidacao.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("Número de telefone não pode ser nulo"),
                                startsWith("DDD não pode ser nulo"),
                                startsWith("Telefone precisa estar associado a um cliente")
                        ));
            }
            throw ex;
        }
    }
}
