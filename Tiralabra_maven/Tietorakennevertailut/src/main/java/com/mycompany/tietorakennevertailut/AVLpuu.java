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
        solmu2.asetaVanhempi(solmu.vanhempi());
        solmu.asetaVanhempi(solmu2);
        solmu.asetaVasen(solmu2.oikea());
        solmu2.asetaOikea(solmu);
        if(solmu.vasen() != null)
            solmu.vasen().asetaVanhempi(solmu);
        solmu.asetaKorkeus(maksimi(korkeus(solmu.oikea()), korkeus(solmu.vasen())) + 1);
        solmu2.asetaKorkeus(maksimi(korkeus(solmu2.vasen()), korkeus(solmu2.oikea())) + 1);
        return solmu2;
    }
    
    /**
     * Yksinkertainen kierto vasemmalle
     * @param solmu solmu, jota kierretään vasemmalle
     * @return palauttaa kierron jälkeisen alipuun juuren
     */
    private Solmu kiertoVasemmalle(Solmu solmu) {
        
        Solmu solmu2 = solmu.oikea();
        solmu2.asetaVanhempi(solmu.vanhempi());
        solmu.asetaVanhempi(solmu2);
        solmu.asetaOikea(solmu2.vasen());
        solmu2.asetaVasen(solmu);
        if(solmu.oikea() != null)
            solmu.oikea().asetaVanhempi(solmu);
        solmu.asetaKorkeus(maksimi(korkeus(solmu.oikea()), korkeus(solmu.vasen())) + 1);
        solmu2.asetaKorkeus(maksimi(korkeus(solmu2.oikea()), korkeus(solmu2.vasen())) + 1);
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
     * Lisätään puuhun arvoa vastaava solmu ja tasapainotetaan puu
     * @param arvo lisättävä arvo
     */
    public void lisaa(int arvo) {
        
        if(etsi(arvo))
            return;
        Solmu uusi = lisaa(juuri, arvo), vanhempi, alipuu;
        Solmu p = uusi.vanhempi();
        while(p != null) {
            if(korkeus(p.vasen()) - korkeus(p.oikea()) == 2) {
                vanhempi = p.vanhempi();
                if(korkeus(p.vasen().vasen()) > korkeus(p.vasen().oikea()))
                    alipuu = kiertoOikealle(p);
                else
                    alipuu = kaksoiskiertoOikealle(p);
                if(vanhempi == null)
                    this.juuri = alipuu;
                else if(vanhempi.vasen().equals(p))
                    vanhempi.asetaVasen(alipuu);
                else
                    vanhempi.asetaOikea(alipuu);
                if(vanhempi != null)
                    vanhempi.asetaKorkeus(maksimi(korkeus(vanhempi.vasen()), korkeus(vanhempi.oikea())) +1);
                return ;
            }
            if(korkeus(p.oikea()) - korkeus(p.vasen()) == 2) {
                vanhempi = p.vanhempi();
                if(korkeus(p.oikea().oikea()) > korkeus(p.oikea().vasen()))
                    alipuu = kiertoVasemmalle(p);
                else
                    alipuu = kaksoiskiertoVasemmalle(p);
                if(vanhempi == null)
                    this.juuri = alipuu;
                else if(vanhempi.vasen().equals(p))
                    vanhempi.asetaVasen(alipuu);
                else
                    vanhempi.asetaOikea(alipuu);
                if(vanhempi != null)
                    vanhempi.asetaKorkeus(maksimi(korkeus(vanhempi.vasen()), korkeus(vanhempi.oikea())) + 1);
                return ;
            }
            p.asetaKorkeus(maksimi(korkeus(p.vasen()), korkeus(p.oikea())) + 1);
            p = p.vanhempi();
        }
    }
    
    /**
     * Lisätään uusi solmu puuhun
     * @param solmu puun juuri
     * @param arvo lisättävä arvo
     * @return viite lisättyyn solmuun
     */
    private Solmu lisaa(Solmu solmu, int arvo) {
        
        Solmu uusi = new Solmu(arvo);
        if(solmu == null) {
            uusi.asetaKorkeus(0);
            this.juuri = uusi;
            return this.juuri;
        } else {
            Solmu vanhempi = juuri;
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
        uusi.asetaKorkeus(0);
        return uusi;
    }
    
    /**
     * Poistetaan arvoa vastaava solmu ja tasapainotetaan puu
     * @param arvo poistettava arvo
     */
    public void poista(int arvo) {
        
        Solmu vanhempi, poista = poista(juuri, arvo), alipuu;
        if(poista == null)
            return ;
        Solmu p = poista.vanhempi();
        while(p != null) {
            if(korkeus(p.vasen())-korkeus(p.oikea()) == 2) {
                vanhempi = p.vanhempi();
                if(korkeus(p.vasen().vasen()) > korkeus(p.vasen().oikea()))
                    alipuu = kiertoOikealle(p);
                else
                    alipuu = kaksoiskiertoOikealle(p);
                if(vanhempi == null) {
                    juuri = alipuu;
                    return ;
                }
                System.out.println(vanhempi);
                if(alipuu.tieto() < vanhempi.tieto())
                    vanhempi.asetaVasen(alipuu);
                else
                    vanhempi.asetaOikea(alipuu);
                p = vanhempi;
            }    
            if(korkeus(p.oikea())-korkeus(p.vasen()) == 2) { 
                vanhempi = p.vanhempi();
                if(korkeus(p.oikea().oikea()) > korkeus(p.oikea().vasen()))
                    alipuu = kiertoVasemmalle(p);
                else
                    alipuu = kaksoiskiertoVasemmalle(p);
                if(vanhempi == null) {
                    juuri = alipuu;
                    return ;
                }
                if(alipuu.tieto() < vanhempi.tieto())
                    vanhempi.asetaVasen(alipuu);
                else
                    vanhempi.asetaOikea(alipuu);
                p = vanhempi;
            } else {
                p.asetaKorkeus(maksimi(korkeus(p.vasen()), korkeus(p.oikea()))+1);
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
     * Tulostetaan puun solmujen tietojen arvot suuruusjärjestyksessä.
     * @param solmu puun T alkiot voidaan tulostaa kutsumalla rekursiivista 
     * algoritmia parametrilla T.juuri
     */
    public String tulosta(Solmu solmu) {
        
        if(solmu != null) {
            //System.out.println("SOLMU: " + solmu + ", VASEN: " + solmu.vasen() + ", OIKEA: " + solmu.oikea());
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
