package testes;

import entidades.Cliente;
import entidades.Funcionario;
import entidades.Servico;
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
import servico.ServicoServico;
import utils.Status;

public class ServicoTeste extends Teste {

    private ServicoServico servicoServico;

    @Before
    public void setUp() throws NamingException {
        servicoServico = (ServicoServico) container.getContext().lookup("java:global/classes/ejb/ServicoServico!servico.ServicoServico");
    }

    @After
    public void tearDown() {
        servicoServico = null;
    }

    @Test
    public void existeServico() {
        Servico servico = servicoServico.criar();
        servico.setId(2L);
        assertTrue(servicoServico.existe(servico));
    }

    @Test
    public void getServicoPorClienteCPF() {
        List<Servico> servicos = servicoServico.consultarPorClienteCPF("025.696.830-60");
        assertEquals(2, servicos.size());
        assertEquals(Status.ENTREGUE, servicos.get(0).getStatus());
        assertEquals(Status.ABERTO, servicos.get(1).getStatus());
    }
//
//    @Test(expected = EJBException.class)
//    public void getServicoPorClienteCPFInvalido() {
//        try {
//            servicoServico.consultarPorClienteCPF("222.11sdsdsd");
//        } catch (EJBException ex) {
//            assertTrue(ex.getCause() instanceof ConstraintViolationException);
//            throw ex;
//        }
//    }
//
//    @Test
//    public void getServicoPorClienteCPFInexistente() {
//        List<Servico> servicos = servicoServico.consultarPorClienteCPF("744.455.190-14");;
//        assertEquals(0, servicos.size());
//    }
//
//    @Test
//    public void getServicoPorFuncionarioMatricula() {
//        List<Servico> servicos = servicoServico.consultarPorFuncionarioMatricula("201811OS1");
//        assertEquals(2, servicos.size());
//        assertEquals(Status.ENTREGUE, servicos.get(0).getStatus());
//        assertEquals(Status.CANCELADO, servicos.get(1).getStatus());
//    }
//
//    @Test
//    public void getServicoPorFuncionarioMatriculaInexistente() {
//        List<Servico> servicos = servicoServico.consultarPorFuncionarioMatricula("201904OS2");
//        assertEquals(0, servicos.size());
//    }
//
//    @Test
//    public void getServicoPorStatus() {
//        List<Servico> servicos = servicoServico.consultarPorStatus(Status.CANCELADO);
//        assertEquals(1, servicos.size());
//    }
//
//    @Test
//    public void persistirServico() {
//        Cliente c = new Cliente();
//        c.setNome("João Victor");
//        c.setEmail("joao-vv@gmail.com");
//        c.setDataNasc(new Date(99, 5, 31));
//
//        Funcionario f = new Funcionario();
//        f.setNome("João Paulo");
//        f.setEmail("joao-pp@email.com");
//        f.setDataNasc(new Date(103, 12, 12));
//
//        Servico servico = servicoServico.criar();
//        servico.setStatus(Status.ABERTO);
//        servico.setInicio(new Date(119, 10, 12));
//        servico.setPrevFim(new Date(119, 10, 29));
//        servico.setCliente(c);
//        servico.setFuncionario(f);
//
//        servicoServico.persistir(servico);
//
//        assertNotNull(servico.getId());
//    }
//
//    @Test(expected = EJBException.class)
//    public void persistirServicoInvalido() {
//        try {
//            Servico servico = servicoServico.criar();
//            servicoServico.persistir(servico);
//        } catch (EJBException ex) {
//            assertTrue(ex.getCause() instanceof ConstraintViolationException);
//            ConstraintViolationException erro = (ConstraintViolationException) ex.getCause();
//            for (ConstraintViolation erroValidacao : erro.getConstraintViolations()) {
//                assertThat(erroValidacao.getMessage(),
//                        CoreMatchers.anyOf(
//                                startsWith("Status do serviço não pode ser nulo"),
//                                startsWith("Data de início do serviço não pode ser nulo"),
//                                startsWith("Data de previsão de fim do serviço não pode ser nulo"),
//                                startsWith("Serviço deve estar associado a um cliente"),
//                                startsWith("Serviço deve estar associado a um funcionário atendente")
//                        ));
//            }
//            assertEquals(5, erro.getConstraintViolations().size());
//            throw ex;
//        }
//    }
//
//    @Test
//    public void atualizarServico() {
//        Servico servico = servicoServico.consultarPorId(1L);
//        assertEquals(Status.FINALIZADO, servico.getStatus());
//        servico.setStatus(Status.ABERTO);
//
//        servicoServico.atualizar(servico);
//
//        servico = servicoServico.consultarPorId(1L);
//        assertEquals(Status.ABERTO, servico.getStatus());
//    }
//
//    @Test(expected = EJBException.class)
//    public void atualizarServicoComDataInicioNula() {
//        try {
//            Servico servico = servicoServico.consultarPorId(2L);
//            servico.setInicio(null);
//
//            servicoServico.atualizar(servico);
//        } catch (EJBException ex) {
//            assertTrue(ex.getCause() instanceof ConstraintViolationException);
//            ConstraintViolationException erro = (ConstraintViolationException) ex.getCause();
//            for (ConstraintViolation erroValidacao : erro.getConstraintViolations()) {
//                assertThat(erroValidacao.getMessage(),
//                        CoreMatchers.anyOf(
//                                startsWith("Data de início do serviço não pode ser nulo")));
//            }
//            throw ex;
//        }
//
//    }
}
