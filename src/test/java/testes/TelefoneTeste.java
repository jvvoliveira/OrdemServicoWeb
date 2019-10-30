package testes;

import entidades.Telefone;
import javax.naming.NamingException;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import servico.TelefoneServico;

public class TelefoneTeste extends Teste{
    
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
}
