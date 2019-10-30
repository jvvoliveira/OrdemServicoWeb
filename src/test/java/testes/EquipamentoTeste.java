package testes;

import entidades.Cliente;
import entidades.Equipamento;
import entidades.Funcionario;
import entidades.Servico;
import utils.Status;
import java.util.Date;
import java.util.List;
import javax.ejb.EJBException;
import javax.naming.NamingException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import servico.EquipamentoServico;

public class EquipamentoTeste extends Teste{
    
    private EquipamentoServico equipServico;
    
    @Before
    public void setUp() throws NamingException {
        equipServico = (EquipamentoServico) container.getContext().lookup("java:global/classes/ejb/EquipamentoServico!servico.EquipamentoServico");
    }

    @After
    public void tearDown() {
        equipServico = null;
    }
    
    @Test
    public void existeEquipamento() {
        Equipamento equip = equipServico.criar();
        equip.setId(3L);
        assertTrue(equipServico.existe(equip));
    }
    
    @Test
    public void getEquipamentoPorMarca() {
        List<Equipamento> equips = equipServico.consultaPoMarca("HP");
        assertEquals(3, equips.size());
        assertEquals("ASD8766" , equips.get(0).getSerie());
        assertEquals("AKJ987YHj" , equips.get(1).getSerie());
        assertEquals("IJK877KJ" , equips.get(2).getSerie());
    }
    
    
    @Test(expected = EJBException.class)
    public void getEquipamentoPorClienteCPFInvalido() {
        try {
            equipServico.consultaPorClienteCpf("222.11sdsdsd");
        } catch (EJBException ex) {
            assertTrue(ex.getCause() instanceof ConstraintViolationException);
            throw ex;
        }
    }

    @Test
    public void getEquipamentoPorClienteCPFInexistente() {
        List<Equipamento> equips = equipServico.consultaPorClienteCpf("744.455.190-14");;
        assertEquals(0, equips.size());
    }

    @Test
    public void getEquipamentoPorFuncionarioMatricula() {
        List<Equipamento> equips = equipServico.consultaPorFuncionarioMatricula("201812OS4");
        assertEquals(2, equips.size());
        assertEquals("YI879AN" , equips.get(0).getSerie());
        assertEquals("X510uaDF60T" , equips.get(1).getSerie());
    }

    @Test
    public void getServicoPorFuncionarioMatriculaInexistente() {
        List<Equipamento> equips = equipServico.consultaPorFuncionarioMatricula("201904OS2");
        assertEquals(0, equips.size());
    }

    @Test
    public void getEquipamentoPorMarcas() {
        List<Equipamento> equips = equipServico.consultaPoMarca("Sony");
        assertEquals(1, equips.size());
    }

    @Test
    public void persistirEquipamento() {
        
        Cliente c = new Cliente();
        c.setNome("João Victor");
        c.setEmail("joao-vv@gmail.com");
        c.setDataNasc(new Date(99, 5, 31));

        Funcionario f = new Funcionario();
        f.setNome("João Paulo");
        f.setEmail("joao-pp@email.com");
        f.setDataNasc(new Date(103, 12, 12));

        Servico servico = new Servico();
        servico.setStatus(Status.ABERTO);
        servico.setInicio(new Date(119, 10, 12));
        servico.setPrevFim(new Date(119, 10, 29));
        servico.setCliente(c);
        servico.setFuncionario(f);
        
        //Crio um equipamento
        Equipamento equip = equipServico.criar();
        equip.setDescricao("Celular com tela trincada");
        equip.setDefeito("Reinicia sozinho o tempo todo");
        equip.setMarca("samsung");
        equip.setModelo("galaxy s7");
        equip.setSerie("85dsfdfs");
        equip.setSolucao("Cliente Desistiu do serviço");
        equip.setMaoObra(0);
        equip.setCustoPecas(0);
        
        servico.addEquipamento(equip);
        
        assertEquals(equip.getServico().getId(), servico.getId());
        equipServico.persistir(equip);
        assertNotNull(equip.getId());
    }

    @Test(expected = EJBException.class)
    public void persistirEquipamentoInvalido() {
        Equipamento equip = equipServico.criar();
        try {
            equipServico.persistir(equip);
        }  catch (EJBException ex) {
            assertTrue(ex.getCause() instanceof ConstraintViolationException);
            ConstraintViolationException erro = (ConstraintViolationException) ex.getCause();
            for (ConstraintViolation erroValidacao : erro.getConstraintViolations()) {
                assertThat(erroValidacao.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("Descrição do equipamento não pode ser nulo"),
                                startsWith("Marca do equipamento não pode ser nulo"),
                                startsWith("Modelo do equipamento não pode ser nulo"),
                                startsWith("Série do equipamento não pode ser nulo"),
                                startsWith("Defeito do equipamento não pode ser nulo"),
                                startsWith("Equipamento deve estar associado a um serviço")
                        ));
            }
            assertEquals(6, erro.getConstraintViolations().size());
            throw ex;
        }
    }
    
     @Test
    public void atualizarFuncionario() { 
        Equipamento equip = equipServico.consultaPorId(5L);
        assertEquals("ASD8766", equip.getSerie());
        equip.setSerie("ASD8766A"); 
        equipServico.atualizar(equip);
        equip = equipServico.consultaPorId(5L);
        assertEquals("ASD8766A", equip.getSerie());
    }
}
