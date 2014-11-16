package com.mycompany.tietorakennevertailut;

/**
 *
 * @author Tomi
 * @version 1.0
 * @since 6.11.2014
 */
public class BinaariHakupuu {
    
    private Solmu juuri;
    
    private String tulostus;
    
    /**
     * Muodostetaan ensin tyhjä hakupuu. Tämän jälkeen puuhun voi lisätä solmuja.
     * Ensimmäinen solmu on juuri.
     */
    public BinaariHakupuu() {
        juuri = null;
    }
    
    /**
     * Lisätään uusi arvo puuhun.
     * @param arvo lisättävä arvo
     */
    public void lisaa(int arvo) {
        
        Solmu uusi = new Solmu(arvo);
        if(juuri == null) {
            juuri = uusi;
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
        
    }
    
    /**
     * Tulostetaan puun solmujen tietojen arvot suuruusjärjestyksessä.
     * @param solmu puun T alkiot voidaan tulostaa kutsumalla rekursiivista 
     * algoritmia parametrilla T.juuri
     */
    public String tulosta(Solmu solmu) {
        
        if(solmu != null) {
            tulosta(solmu.vasen());
            //System.out.println(solmu.tieto());
            tulostus = tulostus+solmu.tieto()+" ";
            tulosta(solmu.oikea());
        }
        return tulostus;
    }
    
    /**
     * Poistetaan arvoa vastaava solmu muuttamalla sen arvoa. Jos poistettava
     * solmu on lehti se saa arvon null. Jos poistettavalla on yksi joko vasemman tai
     * oikean puoleinen lapsi, lapsen arvo asetetaan poistettavan solmun arvoksi ja
     * lapsi poistetaan. Jos poistettavalla solmulla on kaksi lasta.....
     * @param arvo poistettava arvo
     */
    public Solmu poista(int arvo) {
        
        Solmu pois = etsi(juuri, arvo), vanhempi=pois.vanhempi(), lapsi;
        if(pois.vasen() == null && pois.oikea() == null) {
            if(vanhempi.oikea().equals(pois))
                vanhempi.asetaOikea(null);
            else
                vanhempi.asetaVasen(null);
            return pois;
        }
        if(pois.vasen() == null || pois.oikea() == null) {
            if(pois.vasen() != null)
                vanhempi.asetaVasen(pois.vasen());
            else
                vanhempi.asetaOikea(pois.oikea());
            return pois;
        }
        Solmu seuraaja = etsiMin(pois);
        pois.muutaTietoa(seuraaja.tieto());
        if(seuraaja.oikea() != null)
            seuraaja = seuraaja.oikea();
        return pois;
    }
    
    /**
     * Etsitään haluttua arvoa.
     * @param arvo arvo, jota etsitään
     * @return palauttaa true, jos arvo löytyy puusta ja false, jos ei
     */
    public boolean etsi(int arvo) {
        
        return etsi(juuri, arvo) != null;
    }
    
    /**
     * Etsitään alipuusta arvoa rekursiivisesti
     * @param solmu alipuun juuri solmu
     * @param arvo etsitty arvo
     * @return solmun, jonka tieto-kenttä vastaa arvoa tai null, jos arvoa ei
     * löydy puusta
     */
    private Solmu etsi(Solmu solmu, int arvo) {
        
        if(solmu == null)
            return null;
        if(arvo < solmu.tieto())
            return etsi(solmu.vasen(), arvo);
        if(arvo > solmu.tieto())
            return etsi(solmu.oikea(), arvo);
        return solmu;
    }
    
    /**
     * Etsitään pienin arvo
     * @return pienin arvo tai 0, jos tyhjä puu
     */
    public int etsiMin() {
        
        Solmu pienin = etsiMin(juuri);
        if(pienin == null)
            return 0;
        return pienin.tieto();
    }
    
    /**
     * Etsitään alipuun pienin arvo
     * @param juuri alipuun juuri
     * @return viite pienimpään solmuun
     */
    private Solmu etsiMin(Solmu juuri) {
        
        if(juuri != null) {
            while(juuri.vasen() != null)
                juuri = juuri.vasen();
        }
        return juuri;
    }
    
    /**
     * Etsitään puun suurin arvo.
     * @return suurin arvo tai 0, jos tyhjä puu
     */
    public int etsiMax() {
        
        Solmu suurin = etsiMax(juuri);
        if(suurin == null)
            return 0;
        return suurin.tieto();
    }
    
    /**
     * Etsitään alipuun suurin arvo
     * @param juuri alipuun juuri
     * @return viite suurimpaan solmuun
     */
    private Solmu etsiMax(Solmu juuri) {
        
        if(juuri != null) {
            while(juuri.oikea() != null)
                juuri = juuri.oikea();
        }
        return juuri;
    }
    
    @Override
    public String toString() {
        
        tulostus = "";
        //System.out.println(tulosta(juuri));
        return tulosta(juuri).trim();
    }
}
