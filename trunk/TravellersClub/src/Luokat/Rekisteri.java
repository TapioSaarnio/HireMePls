package Luokat;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * @author Tapio Saarnio
 * @version 10 Mar 2019
 *
 */
public class Rekisteri {
    
    private  Jasenet jasenet = new Jasenet();
    private  MaaInfot maainfot=new MaaInfot();
    

    
    /**
     * @param hakuehto hakuehto
     * @return tietorakenteen lˆytyneist‰ j‰senist‰
     * @throws SailoException jos jotain vikaan
     */
    public Collection<Jasen> etsi(String hakuehto) throws SailoException {
            
        return jasenet.etsi(hakuehto);
    }
    
    /**
     * Poistaa j‰senen sek‰ j‰senen vierailut
     * @param jasen joka poistetaan
     */
    public void poista(Jasen jasen) {
        
        if ( jasen == null) return;
        jasenet.poistaJasen(jasen.getId());
        maainfot.poistaVierailut(jasen.getId());
        
    }

    /**
     * @return lkm
     */
    public int getLkm() {
        
        return jasenet.getLkm();
    }
    
    /**
     * @param i mones j‰sen
     * @return j‰sen
     */
    public Jasen getJasen(int i) {
        
        return jasenet.getJasen(i);
    }

    /**
     * @param trevor lis‰tt‰v‰ j‰sen
     * @example
     * <pre name="test">
     * Jasenet jasenet = new Jasenet();
     * Jasen trevor1 = new Jasen(), trevor2 = new Jasen();
     * jasenet.getLkm()===0;
     * jasenet.lisaa(trevor1);
     * jasenet.getLkm()===1;
     * jasenet.lisaa(trevor2);
     * jasenet.getLkm()===2; 
     * jasenet.getJasen(0)===trevor1;
     * jasenet.getJasen(1)===trevor2;
     * </pre>
     */
    public void lisaa(Jasen trevor) {
        
       jasenet.lisaa(trevor);
        
    }
    
    /**
     * Siirret‰‰n toimenpide jasenet luokalle
     * @param maatinfo kenelle lis‰t‰‰n
     */
    public void addVisit(MaatInfo maatinfo) {
        
        maainfot.addVisit(maatinfo);
    }

    /**
     * @param jsennro kenen vierailut haetaan
     * @return j‰senen kaikki vierailut
     * @example
     * <pre name="test">
     * #import java.util.*;
     * Rekisteri rekisteri = new Rekisteri();
     * Jasen trevor1 = new Jasen();
     * Jasen trevor2 = new Jasen();
     * Jasen trevor3 = new Jasen();
     * trevor1.rekisteroi();
     * trevor2.rekisteroi();
     * trevor3.rekisteroi();
     * int id1 = trevor1.getId();
     * int id2 = trevor2.getId();
     * MaatInfo vierailu1 = new MaatInfo(id1);
     * MaatInfo vierailu12 = new MaatInfo(id1);
     * vierailu1.taytaTiedoilla();
     * vierailu12.taytaTiedoilla();
     * MaatInfo vierailu2 = new MaatInfo(id2);
     * vierailu2.taytaTiedoilla();
     * rekisteri.addVisit(vierailu1);
     * rekisteri.addVisit(vierailu12);
     * rekisteri.addVisit(vierailu2);
     * List<MaatInfo> loytyneet;
     * loytyneet = rekisteri.getVierailut(trevor1.getId());
     * loytyneet.size() === 2;
     * loytyneet = rekisteri.getVierailut(trevor2.getId());
     * loytyneet.size() === 1;
     * loytyneet = rekisteri.getVierailut(trevor3.getId());
     *  </pre>
     */   
    public List<MaatInfo> getVierailut(int jsennro) {
        
        return maainfot.getVierailut(jsennro);
    }

    /**
     * Lukee tiedot tiedostosta
     * @param nimi tiedostonnimi
     * @throws SailoException virhe
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        
        this.jasenet = new Jasenet();
        this.maainfot = new MaaInfot();
        
        setTiedosto(nimi);
        
        
        jasenet.lueTiedostosta();
        maainfot.lueTiedostosta();
        
    }

    /**
     * Asettaa tiedostojen perusnimet
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "TravellersClub";
        if( !nimi.isEmpty()) hakemistonNimi = nimi +"/";
        jasenet.setTiedostonPerusNimi(hakemistonNimi + "nimet");
        maainfot.setTiedostonPerusNimi(hakemistonNimi + "vierailut");
        
        
        
    }

    /**
     * Lis‰t‰‰n lis‰tyt asiat tiedostoihin
     * @throws SailoException Jos virhe
     */
    public void tallenna() throws SailoException {
        
        
        String virhe = "";
        setTiedosto("TravellersClub");
        try {
        jasenet.tallenna();
        } catch (SailoException ex) {
            virhe=ex.getMessage();
        }
        
        try {
            maainfot.tallenna();
            
        } catch (SailoException ex) {
            virhe +=ex.getMessage();
        }
        
        if (!virhe.equals("")) throw new SailoException(virhe);
    }
    
    
    
    
    
    /**
     * @throws SailoException  Jos virhe 
     * 
     */
    public void tallennavierailut() throws SailoException  {
        
       maainfot.tallenna();
        
    }


}
