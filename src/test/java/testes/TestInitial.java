/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import java.math.BigDecimal;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestInitial extends Teste {

//    private ItemServico itemServico;
//    private CompradorServico compradorServico;
//
//    @Before
//    public void setUp() throws NamingException {
//        itemServico = (ItemServico) container.getContext().lookup("java:global/classes/ejb/ItemServico!softwarecorporativo.exemplo.ejb.servico.ItemServico");
//        compradorServico = (CompradorServico) container.getContext().lookup("java:global/classes/ejb/CompradorServico!softwarecorporativo.exemplo.ejb.servico.CompradorServico");
//    }
//
//    @After
//    public void tearDown() {
//        itemServico = null;
//    }

    @Test
    public void initialTrue() {
        assertTrue(true);
    }

}
