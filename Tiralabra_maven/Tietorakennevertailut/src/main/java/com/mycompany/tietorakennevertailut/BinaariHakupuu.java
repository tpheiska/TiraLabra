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
        
        if(etsi(arvo))
            return ;
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
     * @return palauttaa puuhun talletettujen solmujen tiedot suuruusjärjestyksessä
     * pienimmästä alkaen
     */
    public String tulosta(Solmu solmu) {
        
        if(solmu != null) {
            tulosta(solmu.vasen());
            //System.out.println(solmu.tieto());
            tulostus = tulostus+solmu.tieto()+" ";
            tulosta(solmu.oikea());
        }
        return tulostus.trim();
    }
    
    /**
     *Poistetaan arvoa vastaava solmu muuttamalla sen arvoa. Jos poistettava
     * solmu on lehti se saa arvon null. Jos poistettavalla on yksi joko vasemman tai
     * oikean puoleinen lapsi, lapsen arvo asetetaan poistettavan solmun arvoksi ja
     * lapsi poistetaan. Jos poistettavalla solmulla on kaksi lasta, niin solmun
     * tiedoksi tulee oikean puoleisen alipuun pienin arvo. Tämä pienin arvo poistetaan
     * ja tällä pienimmällä arvolla ei ole kuin korkeintaan oikea lapsi ja 
     * poistaminen tapahtuu kuten aikaisemmin.
     * @param arvo poistettava arvo
     * @return palauttaa viitteen poistettuun solmuun
     */
    public Solmu poista(int arvo) {
        
        Solmu poista = etsi(juuri, arvo), vanhempi, lapsi;
        if(poista == null)
            return null;
        else
            vanhempi = poista.vanhempi();
        if(poista.vasen() == null && poista.oikea() == null) {
            if(vanhempi == null) {
                juuri = null;
                return poista;
            }
            if(vanhempi.oikea() != null && vanhempi.oikea().equals(poista))
                vanhempi.asetaOikea(null);
            else
                vanhempi.asetaVasen(null);
            return poista;
        }
        if(poista.vasen() == null || poista.oikea() == null) {
            if(poista.vasen() != null)
                lapsi = poista.vasen();
            else
                lapsi = poista.oikea();
            vanhempi = poista.vanhempi();
            lapsi.asetaVanhempi(vanhempi);
            if(vanhempi == null) {
                juuri = lapsi;
                return poista;
            }
            if(poista.equals(vanhempi.vasen()))
                vanhempi.asetaVasen(lapsi);
            else
                vanhempi.asetaOikea(lapsi);
            return poista;
        }
        Solmu seuraaja = etsiMin(poista.oikea());
        poista.muutaTietoa(seuraaja.tieto());
        lapsi = seuraaja.oikea();
        vanhempi = seuraaja.vanhempi();
        if(vanhempi.vasen().equals(seuraaja))
            vanhempi.asetaVasen(lapsi);
        else
            vanhempi.asetaOikea(lapsi);
        if(lapsi != null)
            lapsi.asetaVanhempi(vanhempi);
        return poista;
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
     * @return viitteen solmuun, jonka tieto-kenttä vastaa arvoa tai null, jos arvoa ei
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
    
    /**
     * @return Palauttaa puuhun tallennettujen solmujen tiedot suuruusjärjestyksessä
     * pienimmästä alkaen.
     */
    @Override
    public String toString() {
        
        tulostus = "";
        //System.out.println(tulosta(juuri));
        return tulosta(juuri);
    }
    
    /**
     * Testipääohjelma.
     * @param args
     */
    public static void main(String args[]) {
        
        BinaariHakupuu bin = new BinaariHakupuu();
        int maara = 1000;
        int lisattavat[] = new int[maara], etsittavat[] = new int[10000];
        for(int i=0; i<maara; i++) {
            lisattavat[i] = i;
        }
        for(int i=0; i<10000; i++) {
            etsittavat[i] = (int)(2*maara*Math.random());
        }
        
        long aikaAlussa = System.currentTimeMillis();  
        for(int i=0; i<maara; i++) {
            bin.lisaa(lisattavat[i]);
        }
        long aikaLopussa = System.currentTimeMillis();  
        System.out.println("Lisäämisiin kului aikaa(bin: "+maara+"): " + (aikaLopussa - aikaAlussa) + "ms.");
        
        aikaAlussa = System.currentTimeMillis();  
        for(int i=0; i<10000; i++) {
            bin.etsi(etsittavat[i]);
        }  
        aikaLopussa = System.currentTimeMillis();  
        System.out.println("Etsimisiin kului aikaa("+maara+"): " + (aikaLopussa - aikaAlussa) + "ms.");
        
        aikaAlussa = System.currentTimeMillis();  
        bin.etsiMax();
        aikaLopussa = System.currentTimeMillis();  
        System.out.println("Suurimman alkion etsimiseen kului aikaa("+maara+"): " + (aikaLopussa - aikaAlussa) + "ms.");
        
        aikaAlussa = System.currentTimeMillis();  
        bin.etsiMin();
        aikaLopussa = System.currentTimeMillis();  
        System.out.println("Pienimmän alkion etsimiseen kului aikaa("+maara+"): " + (aikaLopussa - aikaAlussa) + "ms.");

        aikaAlussa = System.currentTimeMillis();  
        for(int i=0; i<maara; i++) {
            bin.poista(lisattavat[i]);
        }  
        aikaLopussa = System.currentTimeMillis();  
        System.out.println("Poistamisiin kului aikaa("+maara+"): " + (aikaLopussa - aikaAlussa) + "ms.");
    }
}
