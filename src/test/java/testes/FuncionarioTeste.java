package testes;

import javax.naming.NamingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import servico.FuncionarioServico;
import entidades.Funcionario;
import java.util.Date;
import java.util.List;
import javax.ejb.EJBException;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.*;

public class FuncionarioTeste extends Teste {
    
    private FuncionarioServico funcServico;
    
    @Before
    public void setUp() throws NamingException {
        funcServico = (FuncionarioServico) container.getContext().lookup("java:global/classes/ejb/FuncionarioServico!servico.FuncionarioServico");
    }

    @After
    public void tearDown() {
        funcServico = null;
    }
    
    @Test
    public void existeFuncionario() {
        Funcionario func = funcServico.criar();
        func.setMatricula("201705OS2");
        assertTrue(funcServico.existe(func));
    }
    
    @Test
    public void getFuncioanrioPorMatricula() {
        Funcionario func = funcServico.consultarPorMatricula("201808OS3");
        assertNotNull(func);
        assertEquals("Natalia Shonda Feltrao Silva", func.getNome());
    }

    @Test(expected = EJBException.class)
    public void consultarFuncionarioMatriculaInvalida() {
        try {
            funcServico.consultarPorMatricula(null);
        } catch (EJBException ex) {
            assertTrue(ex.getCause() instanceof ConstraintViolationException);
            throw ex;
        }
    }

    @Test(expected = EJBException.class)
    public void getMatriculaPorMatriculaInexistente() {
        try {
            Funcionario func = funcServico.consultarPorMatricula("201708OS3");
        } catch (EJBException ex) {
            assertTrue(ex.getCause() instanceof NoResultException);
            throw ex;
        }
    }

    @Test
    public void getFuncionarioPorNome() {
        List<Funcionario> funcs = funcServico.consultarPorNome("Pedro Marcell Dias Rabello");
        assertEquals(1, funcs.size());
        assertEquals("pedro.rabello@mail.com", funcs.get(0).getEmail());
    }

    @Test
    public void getFuncionarioNomeInexistente() {
       List<Funcionario> funcs = funcServico.consultarPorNome("Funcionario Teste");
        assertEquals(0, funcs.size());
    }

    
    @Test
    public void persistirFuncionario() {
        Funcionario funcionario = funcServico.criar();
        funcionario.setCargo("atendente");
        funcionario.setDataNasc(new Date(15, 10, 1995));
        funcionario.setNome("Flávio Couto");
        funcionario.setMatricula("201910OS9");
        funcionario.setEmail("flavio.couto@mail.com");

        funcServico.persistir(funcionario);

        assertNotNull(funcionario.getId());
    }

    @Test(expected = EJBException.class)
    public void persistirFuncionarioMatriculaEmailInvalido() {
        try {
            Funcionario funcionario = funcServico.criar();
            funcionario.setCargo("técnico");
            funcionario.setDataNasc(new Date(25, 6, 1993));
            funcionario.setNome("Karen Moureira Rodriguez");
            funcionario.setMatricula(null);
            funcionario.setEmail("etwerter");
            funcServico.persistir(funcionario);
        } catch (EJBException ex) {
            assertTrue(ex.getCause() instanceof ConstraintViolationException);
              
            ConstraintViolationException erro = (ConstraintViolationException) ex.getCause();
            for (ConstraintViolation erroValidacao : erro.getConstraintViolations()) {
                assertThat(erroValidacao.getMessage(),
                        CoreMatchers.anyOf(
                            startsWith("Email inválido"),
                            startsWith("Erro de validação da matrícula(xxxxxxOSx)")
                        ));
            }
            throw ex;
        }
    }
    
    @Test
    public void atualizarFuncionario() { 
        Funcionario funcionario = funcServico.consultarPorId(4L);
        assertEquals("Natanael Maior Couto", funcionario.getNome());
        funcionario.setNome("Natanael Menor Couto"); 
//        funcServico.atualizar(funcionario);
//        funcionario = funcServico.consultarPorId(4L);
//        assertEquals("Natanael Menor Couto", funcionario.getNome());
    }
}
