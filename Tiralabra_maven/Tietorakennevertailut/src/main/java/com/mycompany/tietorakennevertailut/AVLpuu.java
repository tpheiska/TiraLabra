package com.mycompany.tietorakennevertailut;

/**
 *
 * @author Tomi
 * @version 1.0
 * @since 14.11.2014
 */
public class AVLpuu {
    
    private Solmu juuri;
    
    private String tulostus;
    
    /**
     * Muodostetaan ensin tyhjä hakupuu. Tämän jälkeen puuhun voi lisätä solmuja.
     * Ensimmäinen solmu on juuri.
     */
    public AVLpuu() {
        
        juuri = null;
    }
    
    /**
     * Vertailee kokonaislukuja ja palauttaa suuremman kokonaisluvun arvon
     * @param eka ensimmäinen kokonaisluku
     * @param toka toinen kokonaisluku
     * @return suurempi kokonaisluku
     */
    private int maksimi(int eka, int toka) {
        
        if(eka > toka)
            return eka;
        else
            return toka;
    }
    
    /**
     * Palauttaa solmun korkeuden
     * @param solmu solmu, jonka korkeus palautetaan
     * @return kokonaisluku, jos tyhjäpuu, niin arvo on -1
     */
    private int korkeus(Solmu solmu) {
        
        if(solmu == null)
            return -1;
        return solmu.korkeus();
    }
    
    /**
     * Yksinkertainen kierto oikealle
     * @param solmu solmu, jota kierretään oikealle
     * @return palauttaa kierron jälkeisen alipuun juuren
     */
    private Solmu kiertoOikealle(Solmu solmu) {
        
        Solmu solmu2 = solmu.vasen();
        solmu.asetaVasen(solmu2.oikea());
        solmu2.asetaOikea(solmu);
        solmu.asetaKorkeus(maksimi(solmu.oikea().korkeus(), solmu.vasen().korkeus()) + 1);
        solmu2.asetaKorkeus(maksimi(solmu2.vasen().korkeus(), solmu.korkeus()) + 1);
        return solmu2;
    }
    
    /**
     * Yksinkertainen kierto vasemmalle
     * @param solmu solmu, jota kierretään vasemmalle
     * @return palauttaa kierron jälkeisen alipuun juuren
     */
    private Solmu kiertoVasemmalle(Solmu solmu) {
        
        Solmu solmu2 = solmu.oikea();
        solmu.asetaOikea(solmu2.vasen());
        solmu2.asetaVasen(solmu);
        solmu.asetaKorkeus(maksimi(solmu.oikea().korkeus(), solmu.vasen().korkeus()) + 1);
        solmu2.asetaKorkeus(maksimi(solmu2.oikea().korkeus(), solmu.korkeus()) + 1);
        return solmu2;
    }
    
    /**
     * Kaksoiskierto vasemmalle
     * @param solmu
     * @return palauttaa kiertojen jälkeisen puun juuren
     */
    private Solmu kaksoiskiertoVasemmalle(Solmu solmu) {
        
        solmu.asetaOikea(kiertoOikealle(solmu.oikea()));
        return kiertoVasemmalle(solmu);
    }
    
    /**
     * Kaksoiskierto oikealle
     * @param solmu
     * @return 
     */
    private Solmu kaksoiskiertoOikealle(Solmu solmu) {
        
        solmu.asetaVasen(kiertoVasemmalle(solmu.vasen()));
        return kiertoOikealle(solmu);
    }
    
    /**
     * Lisää luvun AVL puuhun
     * @param arvo lisättävä luku
     */
    public void lisaa(int arvo) {
        
        juuri = lisaa(juuri, arvo);
    }
    
    private Solmu lisaa(Solmu solmu, int arvo) {
        
        if(solmu == null) {
            solmu = new Solmu(arvo);
        } else if(arvo < solmu.tieto()) {
            solmu.asetaVasen(lisaa(solmu.vasen(), arvo));
            if(korkeus(solmu.vasen()) - korkeus(solmu.oikea()) == 2)
                if(arvo < solmu.vasen().tieto())
                    solmu = kiertoOikealle(solmu);
                else
                    solmu = kaksoiskiertoOikealle(solmu);
        } else if(arvo > solmu.tieto()) {
            solmu.asetaOikea(lisaa(solmu.oikea(), arvo));
            if(korkeus(solmu.oikea()) - korkeus(solmu.vasen()) == 2)
                if(arvo > solmu.oikea().tieto())
                    solmu = kiertoVasemmalle(solmu);
                else
                    solmu = kaksoiskiertoVasemmalle(solmu);
        } else ;
        solmu.asetaKorkeus(maksimi(korkeus(solmu.vasen()), korkeus(solmu.oikea()) + 1));
        return solmu;
    }
    
    public void poista(int arvo) {
        
        Solmu vanhempi, poista = poista(juuri, arvo);
        Solmu p = poista.vanhempi();
        while(p != null) {
            if(Math.abs(p.vasen().korkeus()-p.oikea().korkeus()) > 1) {
                vanhempi = p.vanhempi();
                if(p.vasen().vasen().korkeus()-p.vasen().oikea().korkeus() == 2)
                    p.asetaVasen(kiertoOikealle(p));
                else if(p.oikea().oikea().korkeus()-p.oikea().vasen().korkeus() == 2)
                    p.asetaOikea(kiertoVasemmalle(p));
                else if(p.vasen().oikea().korkeus()-p.vasen().vasen().korkeus() == 2)
                    p.asetaVasen(kaksoiskiertoOikealle(p));
                else
                    p.asetaOikea(kaksoiskiertoVasemmalle(p));
                if(p.vanhempi() == null) {
                    juuri = p;
                    return ;
                }
                p = vanhempi;
            } else {
                p.asetaKorkeus(maksimi(p.vasen().korkeus(), p.oikea().korkeus())+1);
                p = p.vanhempi();
            }
        }
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
    private Solmu poista(Solmu solmu, int arvo) {
        
        Solmu poista = etsi(solmu, arvo), vanhempi, lapsi;
        if(poista == null)
            return null;
        else
            vanhempi = poista.vanhempi();
        if(poista.vasen() == null && poista.oikea() == null) {
            if(vanhempi == null) {
                juuri = null;
                return poista;
            }
            if(vanhempi.oikea().equals(poista))
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
        return tulostus.trim();
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
        return tulosta(juuri);
    }
}
