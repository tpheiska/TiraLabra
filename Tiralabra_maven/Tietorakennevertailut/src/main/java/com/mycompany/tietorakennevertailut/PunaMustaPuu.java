package com.mycompany.tietorakennevertailut;

/**
 *
 * @author Tomi
 * @version 1.0
 * @since 22.11.2014
 */
public class PunaMustaPuu {
    
    private Solmu juuri;
    
    /**
     * Muodostetaan ensin tyhjä hakupuu. Tämän jälkeen puuhun voi lisätä solmuja.
     * Ensimmäinen solmu on juuri.
     */
    public PunaMustaPuu() {
        
        this.juuri = null;
    }
    
    /**
     * Kierto vasemmalle
     * @param solmu solmu, jota kierretään vasemmalle
     * @return palauttaa kierron jälkeisen alipuun juuren
     */
    public Solmu kiertoVasemmalle(Solmu solmu) {
        
        Solmu solmu2 = solmu.oikea();
        solmu.asetaOikea(solmu2.vasen());
        solmu2.vasen().asetaVanhempi(solmu);
        Solmu w = solmu.vanhempi();
        if(w == null)
            this.juuri = solmu2;
        else if(w.vasen().equals(solmu))
            w.asetaVasen(solmu2);
        else
            w.asetaOikea(solmu2);
        solmu2.asetaVasen(solmu);
        solmu.asetaVanhempi(solmu2);
        return solmu2;
    }
    
    /**
     * Kierto oikealle
     * @param solmu solmu, jota kierretään oikealle
     * @return palauttaa kierron jälkeisen alipuun juuren
     */
    public Solmu kiertoOikealle(Solmu solmu) {
        
        Solmu solmu2 = solmu.vasen();
        solmu.asetaVasen(solmu2.oikea());
        solmu2.oikea().asetaVanhempi(solmu);
        Solmu w = solmu.vanhempi();
        if(w == null)
            this.juuri = solmu2;
        else if(w.vasen().equals(solmu))
            w.asetaVasen(solmu2);
        else
            w.asetaOikea(solmu2);
        solmu2.asetaOikea(solmu);
        solmu.asetaVanhempi(solmu2);
        return solmu2;
    }
    
    /**
     * Lisätään uusi arvo puuhun
     * @param arvo lisättävä arvo
     */
    public void lisaa(int arvo) {
        
        Solmu uusi = new Solmu(arvo);
        if(juuri == null) {
            juuri = uusi;
            juuri.asetaVari('p');
        } else {
            Solmu solmu = juuri, vanhempi = juuri;
            while(solmu != null) {
                vanhempi = solmu;
                if(uusi.tieto() < solmu.tieto())
                    solmu = solmu.vasen();
                else
                    solmu = solmu.oikea();
            }
            uusi.asetaVanhempi(vanhempi);
            if(uusi.tieto() < vanhempi.tieto())
                vanhempi.asetaVasen(uusi);
            else
                vanhempi.asetaOikea(uusi);
        }
        tasapainotaPuu(uusi);
    }
    
    
    public void tasapainotaPuu(Solmu solmu) {
        
        while(solmu.vanhempi().vari() == 'p') {
            
        }
    }
}
