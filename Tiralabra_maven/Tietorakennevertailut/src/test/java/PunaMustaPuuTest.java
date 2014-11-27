
import com.mycompany.tietorakennevertailut.PunaMustaPuu;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tomi
 * @version 1.0
 * @since 25.11.2014
 */
public class PunaMustaPuuTest {
    
    PunaMustaPuu pm = new PunaMustaPuu();
    
    @Before
    public void setUp() {
        pm.lisaa(5);
        pm.lisaa(2);
        pm.lisaa(8);
    }
    
    @Test
    public void lisaaminenjaTulostaminenOnnistuu() {
        
        pm.lisaa(4);
        pm.lisaa(3);
        pm.lisaa(7);
        pm.lisaa(9);
        assertEquals("2 3 4 5 7 8 9", pm.toString());
        pm.lisaa(2);
        assertEquals("2 3 4 5 7 8 9", pm.toString());
    }
    
    @Test
    public void etsiminenOnnistuu() {
        
        pm.lisaa(4);
        pm.lisaa(3);
        pm.lisaa(7);
        pm.lisaa(9);
        assertEquals(2, pm.etsiMin());
        assertEquals(9, pm.etsiMax());
        assertEquals(false, pm.etsi(1));
        assertEquals(true, pm.etsi(3));
    }
    
    @Test
    public void poistaminenOnnistuu() {
        pm.lisaa(4);
        pm.lisaa(3);
        pm.lisaa(7);
        pm.lisaa(9);
        pm.poista(2);
        assertEquals("3 4 5 7 8 9", pm.toString());
        pm.poista(1);
        assertEquals("3 4 5 7 8 9", pm.toString());
        pm.poista(5);
        assertEquals("3 4 7 8 9", pm.toString());
        pm.poista(3);
        pm.poista(4);
        pm.poista(7);
        pm.poista(8);
        pm.poista(9);
        assertEquals("", pm.toString());
    }
}
