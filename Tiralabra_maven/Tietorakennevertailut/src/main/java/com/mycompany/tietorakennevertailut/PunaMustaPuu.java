package com.mycompany.tietorakennevertailut;

/**
 *
 * @author Tomi
 * @version 1.0
 * @since 22.11.2014
 */
public class PunaMustaPuu {
    
    private Solmu juuri;
    
    private String tulostus;
    
    /**
     * Muodostetaan ensin tyhjä hakupuu. Tämän jälkeen puuhun voi lisätä solmuja.
     * Ensimmäinen solmu on juuri.
     */
    public PunaMustaPuu() {
        
        this.juuri = null;
    }
    
    /**
     * Solmun väri
     * @param solmu
     * @return palauttaa solmun värin.
     */
    public char vari(Solmu solmu) {
        
        if(solmu == null)
            return 'm';
        return solmu.vari();
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
        
        if(etsi(arvo))
            return;
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
    
    /**
     * Tarkistaa ja tarvittaessa muutttaa puun solmun lisäämisen jälkeen 
     * punamustapuuksi 
     * @param solmu lisätty solmu
     */
    public void tasapainotaPuu(Solmu solmu) {
        
        Solmu solmu2;
        while(vari(solmu.vanhempi()) == 'p') {
            if(solmu.vanhempi().equals(solmu.vanhempi().vanhempi().vasen())) {
                solmu2 = solmu.vanhempi().vanhempi().oikea();
                if(solmu2.vari() == 'p') {
                    solmu.vanhempi().asetaVari('m');
                    solmu2.asetaVari('m');
                    solmu.vanhempi().vanhempi().asetaVari('p');
                    solmu = solmu.vanhempi().vanhempi();
                } else {
                    if(solmu.equals(solmu.vanhempi().oikea())) {
                        solmu = solmu.vanhempi();
                        kiertoVasemmalle(solmu);
                    }
                    solmu.vanhempi().asetaVari('m');
                    solmu.vanhempi().vanhempi().asetaVari('p');
                    kiertoOikealle(solmu.vanhempi().vanhempi());
                }
            } else {
                solmu2 = solmu.vanhempi().vanhempi().vasen();
                if(solmu2.vari() == 'p') {
                    solmu.vanhempi().asetaVari('m');
                    solmu2.asetaVari('m');
                    solmu.vanhempi().vanhempi().asetaVari('p');
                    solmu = solmu.vanhempi().vanhempi();
                } else {
                    if(solmu.equals(solmu.vanhempi().vasen())) {
                        solmu = solmu.vanhempi();
                        kiertoOikealle(solmu);
                    }
                    solmu.vanhempi().asetaVari('m');
                    solmu.vanhempi().vanhempi().asetaVari('p');
                    kiertoVasemmalle(solmu.vanhempi().vanhempi());
                }
            }
        }
        juuri.asetaVari('m');
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
            if(vari(poista) == 'm')
                tasapainotaPoistonJalkeen(lapsi);
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
        if(vari(seuraaja) == 'm')
            tasapainotaPoistonJalkeen(lapsi);
        return poista;
    }
    
    /**
     * Tarkistaa ja tarvittaessa muutttaa puun solmun lisäämisen jälkeen 
     * punamustapuuksi 
     * @param solmu lisätty solmu
     */
    private void tasapainotaPoistonJalkeen(Solmu solmu) {
        
        Solmu w;
        while(!solmu.equals(juuri) && vari(solmu) == 'm') {
            if(solmu.equals(solmu.vanhempi().vasen())) {
                w = solmu.vanhempi().oikea();
                if(vari(w) == 'p') {
                    w.asetaVari('m');
                    solmu.vanhempi().asetaVari('p');
                    kiertoVasemmalle(solmu.vanhempi());
                    w = solmu.vanhempi().oikea();
                }
                if(vari(w.vasen()) == 'm' && vari(w.oikea()) == 'm') {
                    w.asetaVari('p');
                    solmu = solmu.vanhempi();
                } else {
                    if(vari(w.oikea()) == 'm') {
                        w.vasen().asetaVari('m');
                        w.asetaVari('p');
                        kiertoOikealle(w);
                        w = solmu.vanhempi().oikea();
                    }
                    w.asetaVari(vari(solmu.vanhempi()));
                    solmu.vanhempi().asetaVari('m');
                    w.oikea().asetaVari('m');
                    kiertoVasemmalle(solmu.vanhempi());
                    solmu = this.juuri;
                }
            } else {
                w = solmu.vanhempi().vasen();
                if(vari(w) == 'p') {
                    w.asetaVari('m');
                    solmu.vanhempi().asetaVari('p');
                    kiertoOikealle(solmu.vanhempi());
                    w = solmu.vanhempi().vasen();
                }
                if(vari(w.oikea()) == 'm' && vari(w.vasen()) == 'm') {
                    w.asetaVari('p');
                    solmu = solmu.vanhempi();
                } else {
                    if(vari(w.vasen()) == 'm') {
                        w.oikea().asetaVari('m');
                        w.asetaVari('p');
                        kiertoVasemmalle(w);
                        w = solmu.vanhempi().vasen();
                    }
                    w.asetaVari(vari(solmu.vanhempi()));
                    solmu.vanhempi().asetaVari('m');
                    w.vasen().asetaVari('m');
                    kiertoOikealle(solmu.vanhempi());
                    solmu = this.juuri;
                }
            }
        }
        solmu.asetaVari('m');
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
        
        PunaMustaPuu pm = new PunaMustaPuu();
        int maara = 200000;
        int lisattavat[] = new int[maara], etsittavat[] = new int[10000];
        for(int i=0; i<maara; i++) {
            lisattavat[i] = i;
        }
        for(int i=0; i<10000; i++) {
            etsittavat[i] = (int)(40000*Math.random());
        }
        
        long aikaAlussa = System.currentTimeMillis();  
        for(int i=0; i<maara; i++) {
            pm.lisaa(lisattavat[i]);
        }
        long aikaLopussa = System.currentTimeMillis();  
        System.out.println("Lisäämisiin kului aikaa(pm: "+maara+"): " + (aikaLopussa - aikaAlussa) + "ms.");
        
        aikaAlussa = System.currentTimeMillis();  
        for(int i=0; i<10000; i++) {
            pm.etsi(etsittavat[i]);
        }  
        aikaLopussa = System.currentTimeMillis();  
        System.out.println("Etsimisiin kului aikaa("+maara+"): " + (aikaLopussa - aikaAlussa) + "ms.");
        
        aikaAlussa = System.currentTimeMillis();  
        pm.etsiMax();
        aikaLopussa = System.currentTimeMillis();  
        System.out.println("Suurimman alkion etsimiseen kului aikaa("+maara+"): " + (aikaLopussa - aikaAlussa) + "ms.");
        
        aikaAlussa = System.currentTimeMillis();  
        pm.etsiMin();
        aikaLopussa = System.currentTimeMillis();  
        System.out.println("Pienimmän alkion etsimiseen kului aikaa("+maara+"): " + (aikaLopussa - aikaAlussa) + "ms.");

        aikaAlussa = System.currentTimeMillis();  
        for(int i=0; i<maara; i++) {
            pm.poista(lisattavat[i]);
        }  
        aikaLopussa = System.currentTimeMillis();  
        System.out.println("Poistamisiin kului aikaa("+maara+"): " + (aikaLopussa - aikaAlussa) + "ms.");
    }
}
