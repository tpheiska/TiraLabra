import com.mycompany.tietorakennevertailut.AVLpuu;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tomi
 * @version 1.0
 * @since 24.11.2014
 */
public class AVLpuuTest {
    
    private AVLpuu avl;
    
    @Before
    public void setUp() {
        
        avl = new AVLpuu();
        avl.lisaa(5);
        avl.lisaa(2);
        avl.lisaa(8);
        avl.lisaa(1);
        avl.lisaa(4);
        avl.lisaa(6);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void lisaaminenJaTulostaminenOnnistuu() {
    
        assertEquals("1 2 4 5 6 8", avl.toString());
        avl.lisaa(7);
        avl.lisaa(3);
        avl.lisaa(9);
        avl.lisaa(2);
        assertEquals("1 2 3 4 5 6 7 8 9", avl.toString());
    }
    
    @Test
    public void etsiminenOnnistuu() {
        
        assertEquals(true, avl.etsi(5));
        assertEquals(false, avl.etsi(15));
    }
    
    @Test
    public void etsiPieninJaSuurin() {
        
        assertEquals(1, avl.etsiMin());
        assertEquals(8, avl.etsiMax());
    }
    
    @Test
    public void poistaminenOnnistuu() {
        
        avl.poista(5);
        assertEquals("1 2 4 6 8", avl.toString());
        avl.poista(12);
        assertEquals("1 2 4 6 8", avl.toString());
        avl.poista(8);
        assertEquals("1 2 4 6", avl.toString());
        avl.poista(1);
        avl.poista(2);
        avl.poista(4);
        avl.poista(6);
        assertEquals("", avl.toString());
    }
}
