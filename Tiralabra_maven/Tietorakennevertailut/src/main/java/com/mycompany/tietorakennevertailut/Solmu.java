package com.mycompany.tietorakennevertailut;

/**
 *
 * @author Tomi Heiskanen
 * @version 1.0
 * @since 6.11.2014
 */
public class Solmu {
    
    /**    
     *Puiden tiedot ovat kokonaislukuja. Tieto voisi olla myös joku muu
     * vertailtava arvo.
     */
    private int tieto;
    
    /**
     * Viite vasempaan lapseen.
     */
    private Solmu vasen;
    
    /**
     * Viite oikeaan lapseen
     */
    private Solmu oikea;
    
    /**
     * korkeus
     */
    private int korkeus;
    
    /**
     * Viite vanhempaan
     */
    private Solmu vanhempi;
    
    /**
     * Alustetaan solmu.
     * @param tieto toimii samalla solmun avaimena
     */
    public Solmu(int tieto) {
        
        this.tieto = tieto;
    }
    
    /**
     * @return palauttaa solmun tieto-kentän sisällön 
     */
    public int tieto() {
        
        return this.tieto;
    }
    
    /**
     * 
     * @return palauttaa viitteen solmun vasempaan lapseen
     */
    public Solmu vasen() {
        
        return this.vasen;
    }
    
    /**
     * 
     * @return palauttaa viitteen solmun oikeaan lapseen
     */
    public Solmu oikea() {
        
        return this.oikea;
    }
    
    /**
     * 
     * @return palauttaa viitteen solmun vanhempaan
     */
    public Solmu vanhempi() {
        
        return this.vanhempi;
    }
    
    /**
     * 
     * @return palauttaa solmun korkeuden
     */
    public int korkeus() {
        
        return this.korkeus;
    }
    
    /**
     * Muuttaa tieto-kentän arvoa.
     * @param uusiLuku tieto-kentän uusi arvo
     */
    public void muutaTietoa(int uusiLuku) {
        
        this.tieto = uusiLuku;
    }
    
    /**
     * Asettaa vasemman solmun viitteen
     * @param vasen viite vasempaan solmuun
     */
    public void asetaVasen(Solmu vasen) {
        
        this.vasen = vasen;
    }
    
    /**
     * Asettaa oikean solmun viitteen
     * @param oikea viite oikeaan solmuun
     */
    public void asetaOikea(Solmu oikea) {
        
        this.oikea = oikea;
    }
    
    /**
     * Asettaa solmulla vanhemman
     * @param vanhempi viite solmun vanhempaan 
     */
    public void asetaVanhempi(Solmu vanhempi) {
        
        this.vanhempi = vanhempi;
    }
    
    /**
     * Asettaa korkeuden AVL puun solmulle
     * @param korkeus int.
     */
    public void asetaKorkeus(int korkeus) {
        
        this.korkeus = korkeus;
    }
}
