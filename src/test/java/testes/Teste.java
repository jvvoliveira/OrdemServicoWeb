
package testes;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.embeddable.EJBContainer;
import org.junit.AfterClass;
import org.junit.BeforeClass;


public class Teste {

    protected static EJBContainer container;
    protected final Logger logger = Logger.getGlobal();

    @BeforeClass
    public static void setUpClass() {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(EJBContainer.MODULES, new File("target/classes"));
        container = EJBContainer.createEJBContainer(properties);
        DbUnitUtil.inserirDados();
    }

    @AfterClass
    public static void tearDownClass() {
        container.close();
    }
}
