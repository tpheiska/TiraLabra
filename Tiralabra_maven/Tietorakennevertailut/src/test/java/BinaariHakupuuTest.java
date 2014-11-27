import com.mycompany.tietorakennevertailut.BinaariHakupuu;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tomi
 * @version 1.0
 * @since 20.11.2014
 */
public class BinaariHakupuuTest {
    
    private BinaariHakupuu bin;
    
    
    @Before
    public void setUp() {
        
        bin = new BinaariHakupuu();
        bin.lisaa(5);
        bin.lisaa(2);
        bin.lisaa(8);
        bin.lisaa(4);
        bin.lisaa(6);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
     public void lisaaminenJaTulostaminenOnnistuuOikein() {
         
         bin.lisaa(7);
         bin.lisaa(3);
         bin.lisaa(9);
         bin.lisaa(2);
         assertEquals("2 3 4 5 6 7 8 9", bin.toString());
    }
    
    @Test
    public void etsiminenOnnistuu() {
         
        assertEquals(true, bin.etsi(5));
        assertEquals(false, bin.etsi(1));
    }
    
    @Test
    public void etsiPieninJaSuurinArvo() {
         
        assertEquals(2, bin.etsiMin());
        assertEquals(8, bin.etsiMax());
    }
    
    @Test
    public void poistaminenOnnistuu() {
         
        bin.poista(8);
        assertEquals("2 4 5 6", bin.toString());
        bin.poista(9);
        assertEquals("2 4 5 6", bin.toString());
        bin.poista(6);
        assertEquals("2 4 5", bin.toString());
        bin.poista(2);
        bin.poista(4);
        bin.poista(5);
        assertEquals("", bin.toString());
     }
}
