/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 */
public class BinaariHakupuuTest {
    
    private BinaariHakupuu bin;
    
    public BinaariHakupuuTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        bin = new BinaariHakupuu();
        bin.lisaa(5);
        bin.lisaa(2);
        bin.lisaa(8);
    }
    
    @After
    public void tearDown() {
    }

//     TODO add test methods here.
//     The methods must be annotated with annotation @Test. For example:
//    
     
     @Test
     public void lisaaminenJaTulostaminenOnnistuuOikein() {
         
         bin.lisaa(7);
         bin.lisaa(3);
         bin.lisaa(9);
         assertEquals("2 3 5 7 8 9", bin.toString());
     }
     
     @Test
     public void etsiminenOnnistuu() {
         
         assertEquals(true, bin.etsi(5));
         assertEquals(false, bin.etsi(6));
     }
     
     @Test
     public void etsiPieninJaSuurinArvo() {
         
         assertEquals(2, bin.etsiMin());
         assertEquals(8, bin.etsiMax());
     }
     
     @Test
     public void poistaminenOnnistuu() {
         
         bin.poista(8);
         assertEquals("2 5", bin.toString());
         bin.poista(6);
         assertEquals("2 5", bin.toString());
     }
}
