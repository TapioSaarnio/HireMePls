package Luokat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Pit‰‰ yll‰ taulukkoa vierailuista
 * @author Tapio Saarnio
 * @version 19 Mar 2019
 *
 */
public class MaaInfot implements Iterable<MaatInfo> {
    
    @SuppressWarnings("unused")
    private String tiedostonNimi="";
    private final Collection<MaatInfo> alkiot = new ArrayList<MaatInfo>();
    private String tiedostonPerusNimi = "";
    
    
    /**
     * Muodostaja
     */
    public MaaInfot() {
        
        //Oma alustus riitt‰‰
    }
    
  
    /**
     * Iteraattori l‰pik‰ymiseen
     * @return harrastusiteraattori
     * 
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * MaaInfot vierailut = new MaaInfot();
     * MaatInfo vierailu1 = new MaatInfo(1);
     * vierailut.addVisit(vierailu1);
     * MaatInfo vierailu2 = new MaatInfo(2);
     * vierailut.addVisit(vierailu2);
     * MaatInfo vierailu3 = new MaatInfo(1);
     * vierailut.addVisit(vierailu3);
     * Iterator<MaatInfo> i2 = vierailut.iterator();
     * i2.next() === vierailu1;
     * i2.next() === vierailu2;
     * i2.next() === vierailu3;
     * </pre>
     */
    @Override
    public Iterator<MaatInfo> iterator() {
        
        return alkiot.iterator();
    }
    
    /**
     * 
     * @param id MaatInfon id
     * @return kaikki lˆyudetyt MaatInfo(t)
     * 
     */
    public List<MaatInfo> annakaikki(int id){
        
        List<MaatInfo> loydetyt = new ArrayList<MaatInfo>();
        for(MaatInfo mi:alkiot) {
            if (mi.getJasenNro()==id) loydetyt.add(mi);
        }
        
        return loydetyt;
    }
    
    
    /**
     * Poistaa kaikki tietyn j‰senen harrastukset
     * @param nro id
     * @example
     * <pre name="test">
     * Jasen trevor1 = new Jasen();
     * Jasen trevor2 = new Jasen();
     * trevor1.rekisteroi();
     * trevor2.rekisteroi();
     * MaaInfot vierailut = new MaaInfot();
     * MaatInfo vierailu1 = new MaatInfo(trevor1.getId());
     * vierailu1.taytaTiedoilla();
     * MaatInfo vierailu2 = new MaatInfo(trevor1.getId());
     * vierailu2.taytaTiedoilla();
     * vierailut.addVisit(vierailu1);
     * MaatInfo vierailu3 = new MaatInfo(trevor2.getId());
     * vierailu3.taytaTiedoilla();
     * vierailut.addVisit(vierailu3);
     * vierailut.poistaVierailut(trevor2.getId());
     * List<MaatInfo> h = vierailut.annakaikki(trevor2.getId());
     * h.size()===0;
     * </pre>
     */
    public void poistaVierailut (int nro) {
        
        for (Iterator<MaatInfo> vr = alkiot.iterator(); vr.hasNext();) {
            
            MaatInfo vierailu = vr.next();
            if (vierailu.getJasenNro() == nro ) {
                
                vr.remove();
            }
            
        }
    }




    /**
     * @param maatinfo vierailu
     */
    public void addVisit(MaatInfo maatinfo) {
        
        alkiot.add(maatinfo);
    }


    /**
     * Palautetaan monta alkiota on taulukossa
     * @return monta alkiota on taulukossa
     */
    public int getLkm() {
        
        return alkiot.size();
    }
    
    /**
     * Luetaan harrastukset tiedostosta.
     * @param tied tiedoston nimen alkuosa
     * @throws SailoException Jos jotain ep‰onnistuu
     */
    public void lueTiedostosta(String tied) throws SailoException {
        
        setTiedostonPerusNimi(tied);
        
        try (Scanner fi = new Scanner(new FileInputStream(new File(tied)));){
            
           
            
            while(fi.hasNext()) {
                
                String rivi=fi.nextLine();
                if (rivi.equals("") || rivi.charAt(0) == ';') continue;
                MaatInfo maainfo = new MaatInfo();
                maainfo.parse(rivi); 
                addVisit(maainfo);
                
            }
            
        } catch (FileNotFoundException f) {
            
            throw new SailoException("Tiedostoa " + tied + "ei lˆydy.");
        }
        
    }




    /**
     * @param jsennro kenen j‰senen vierailut palautetaan
     * @return j‰senen vierailut
     * @example
     * <pre name="test">
     * #import java.util.*;
     * MaaInfot maainfot = new MaaInfot();
     * MaatInfo vierailu1 = new MaatInfo(1);
     * maainfot.addVisit(vierailu1);
     * MaatInfo vierailu2 = new MaatInfo(1);
     * MaatInfo vierailu3 = new MaatInfo(3);
     * maainfot.addVisit(vierailu2);
     * maainfot.addVisit(vierailu3);
     * List<MaatInfo> loytyneet;
     * loytyneet = maainfot.getVierailut(1);
     * loytyneet.size() === 2;
     * loytyneet = maainfot.getVierailut(3);
     * loytyneet.size() === 1;
     * </pre>
     */
    public List<MaatInfo> getVierailut(int jsennro) {
        
        List<MaatInfo> loydetyt = new ArrayList<MaatInfo>();
        
        for(MaatInfo vierailu:alkiot) {
            
            if (vierailu.getJasenNro()==jsennro) {
                loydetyt.add(vierailu);
            }
        }
        
        return loydetyt;
    }


    /**
     * Asetetaan tiedostonnimi attribuuttiin
     * @param tied tiedoston nimi
     */
    public void setTiedostonPerusNimi(String tied) {
        
        this.tiedostonPerusNimi=tied;
        
    }
    
    /**
     * Palautetaan tiedoston nimi ilman p‰‰tett‰
     * @return tiedoston nimi ilman p‰‰tett‰
     */
    public String getTiedostonPerusNimi() {
        
        return this.tiedostonPerusNimi;
    }


     /**
     * Palautetaan tiedoston nimi p‰‰tteell‰
     * @return tiedoston nimi p‰‰tteell‰
     */
    public String getTiedostonNimi() {
         
         return this.tiedostonPerusNimi + ".dat"; 
     }

    /**
     * Palautetaan backup tiedoston nimi
     * @return backup tiedoston nimi
     */
    public String getBakNimi() {
        
        return tiedostonPerusNimi + ".bak";
    }



    /**
     * Kuormitusta
     * @throws SailoException Jos jotain menee vikaan
     */
    public void lueTiedostosta() throws SailoException {
        
        lueTiedostosta(getTiedostonNimi());
        
    }


    /**
     * Lis‰t‰‰n lis‰tyt tiedot tiedostoon
     * @throws SailoException jos ongelmia
     */
    public void tallenna() throws SailoException {
        
        
        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete();
        ftied.renameTo(fbak);
        
        try (PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath()))){
            
            for (MaatInfo vierailu : this) {
                fo.println(vierailu.toString());
            }
        } catch (IOException ex) {
                throw new SailoException("Tiedoston" +ftied.getName() + "ei aukea" );
         }
      }
    
        
}


