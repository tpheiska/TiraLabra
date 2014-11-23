import com.mycompany.tietorakennevertailut.AVLpuu;
import com.mycompany.tietorakennevertailut.BinaariHakupuu;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tomi
 * @version 1.0
 * @since 20.11.2014
 */
public class PuuTest {
    
    private BinaariHakupuu bin;
    private AVLpuu avl;
    
    @Before
    public void setUp() {
        
        bin = new BinaariHakupuu();
        bin.lisaa(5);
        bin.lisaa(2);
        bin.lisaa(8);
        
        avl = new AVLpuu();
        avl.lisaa(5);
        avl.lisaa(2);
        avl.lisaa(8);
        avl.lisaa(1);
        avl.lisaa(4);
        avl.lisaa(6);
        avl.lisaa(9);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
     public void lisaaminenJaTulostaminenOnnistuuOikein() {
         
         bin.lisaa(7);
         bin.lisaa(3);
         bin.lisaa(9);
         assertEquals("2 3 5 7 8 9", bin.toString());
         
        assertEquals("1 2 4 5 6 8 9", avl.toString());
        avl.lisaa(7);
        avl.lisaa(3);
        avl.lisaa(9);
        assertEquals("1 2 3 4 5 6 7 8 9", avl.toString());
    }
    
    @Test
    public void etsiminenOnnistuu() {
         
        assertEquals(true, bin.etsi(5));
        assertEquals(false, bin.etsi(6));
        
        assertEquals(true, avl.etsi(5));
        assertEquals(false, avl.etsi(15));
    }
    
    @Test
    public void etsiPieninJaSuurinArvo() {
         
        assertEquals(2, bin.etsiMin());
        assertEquals(8, bin.etsiMax());
        
        assertEquals(1, avl.etsiMin());
        assertEquals(9, avl.etsiMax());
    }
    
    @Test
    public void poistaminenOnnistuu() {
         
        bin.poista(8);
        assertEquals("2 5", bin.toString());
        bin.poista(6);
        assertEquals("2 5", bin.toString());
        bin.poista(5);
        assertEquals("2", bin.toString());
        bin.poista(2);
        assertEquals("", bin.toString());
        
        avl.poista(5);
        assertEquals("1 2 3 4 6 7 8 9", avl.toString());
//        avl.poista(12);
//        assertEquals("1 2 3 4 5 6 7 9", avl.toString());
//        avl.poista(5);
//        assertEquals("1 2 3 4 6 7 9", avl.toString());
//        avl.poista(1);
//        avl.poista(2);
//        avl.poista(3);
//        avl.poista(4);
//        avl.poista(6);
//        avl.poista(7);
//        avl.poista(9);
//        assertEquals("", avl.toString());
     }
}
