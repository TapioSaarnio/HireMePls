package Luokat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import fi.jyu.mit.ohj2.WildChars;

/**
 * Yll‰pit‰‰ j‰seni‰, osaa esim lis‰t‰ ja poistaa j‰senen
 * @author Tapio Saarnio
 * @version 9 Mar 2019
 *
 */
public class Jasenet implements Iterable<Jasen> {
    
    private static final int MAX_JASENIA=5;
    private int lkm = 0;
    private String tiedostonPerusNimi;
    private Jasen[] alkiot = new Jasen[MAX_JASENIA];
    
    /**
     * Oletusmuodostaja
     */
    public Jasenet() {
        
        //Oma alustus riitt‰‰
    }
    
    /**
     * Lis‰‰ uuden j‰senen
     * @param jasen lis‰tt‰v‰n j‰senen viite
     * @example
     * <pre name="test">
     *  Jasenet jasenet=new Jasenet();
     *  Jasen trevor1 = new Jasen(), trevor2=new Jasen();
     *  jasenet.getLkm() === 0;
     *  jasenet.lisaa(trevor1); jasenet.getLkm()===1;
     *  jasenet.lisaa(trevor2); jasenet.getLkm()===2;
     *  jasenet.lisaa(trevor1); jasenet.getLkm()===3;
     *  jasenet.getJasen(0)===trevor1;
     *  jasenet.getJasen(1)===trevor2;
     *  jasenet.getJasen(2)===trevor1;
     *  jasenet.getJasen(1) == trevor1===false;
     *  jasenet.getJasen(1) == trevor2===true;
     *  jasenet.lisaa(trevor1); jasenet.getLkm() === 4;
     *  jasenet.lisaa(trevor1); jasenet.getLkm() === 5;
     * </pre>
     */
    public void lisaa(Jasen jasen) {
        
        if (this.lkm>=alkiot.length) {
            Jasen[] alkiot2= new Jasen[alkiot.length*2];
            for (int i=0;i<alkiot.length;i++) {
                
                alkiot2[i]=alkiot[i];
            }
            this.alkiot=alkiot2;
        }
        this.alkiot[lkm] = jasen;
        lkm++;
        
    }
    /**
     * Palauttaa j‰senten lukum‰‰r‰n
     * @return j‰senten lukum‰‰r‰
     * @example
     * <pre name="test">
     * Jasenet jasenettest=new Jasenet();
     * Jasen Daquan=new Jasen();
     * jasenettest.lisaa(Daquan);
     * jasenettest.getLkm()===1;
     * Jasen Karen=new Jasen();
     * jasenettest.lisaa(Karen);
     * jasenettest.getLkm()===2;
     * </pre>
     */
    public int getLkm() {
        
        return this.lkm;
    }
    
    /**
     * @param idnro J‰senen tunnusluku joka poistetaan
     * @example
     * <pre name="test">
     * Jasenet jasenettest=new Jasenet();
     * Jasen Bob=new Jasen();
     * jasenettest.lisaa(Bob);
     * jasenettest.poistaJasen(Bob.getId());
     * jasenettest.getLkm()===0;
     * </pre>
     */
    public void poistaJasen(int idnro) {
        
        for (int i=0;i<this.lkm;i++) {
            
            if (this.alkiot[i].getId() == idnro ) {
                this.lkm-=1;
                for (int j=i;j<=this.lkm-1;j++) {
                    
                    this.alkiot[j]=this.alkiot[j+1];
                }
            }
        }
        
    }
    
    /**
     * 
     * @param i mist‰ taulukon indeksist‰ otetaan j‰sen
     * @return alkiot taulukon i j‰senen viite
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     * @example
     * <pre name="test">
     * Jasenet jasenettest=new Jasenet();
     * Jasen trevor=new Jasen();
     * jasenettest.lisaa(trevor);
     * jasenettest.getJasen(0)===trevor;
     * </pre>
     */
    public Jasen getJasen(int i) throws IndexOutOfBoundsException {
        if (i<0||lkm<=i) {
            throw new IndexOutOfBoundsException("Laiton indeksi:" + i);
        }
        return alkiot[i];
    }
    
    /**
     * @param tied tiedosto josta luetaan
     * @throws SailoException virhe
     */
    public void lueTiedostosta(String tied) throws SailoException {
        
        try (Scanner fi=new Scanner(new FileInputStream(new File(tied)));){
            
            
            while ((fi.hasNext())) {
            	String rivi=fi.nextLine();
                rivi = rivi.trim();
                if(rivi.equals("")||rivi.charAt(0) == ';') continue;
                Jasen jasen = new Jasen();
                jasen.parse(rivi);
                lisaa(jasen);
                    
            }

            
        } catch (IOException e) {
            throw new SailoException("Ongelmia tiedoston kanssa: "+ e.getMessage());
        }
             
    }

    /**
     * Asettaa tiedoston perusnimen
     * @param string nimi 
     */
    public void setTiedostonPerusNimi(String string) {
        
        this.tiedostonPerusNimi=string;
        
    }
    
    /**
     * Tallentaa j‰senistˆn tiedostoon
     * @throws SailoException Jos tallennus ep‰onnistuu
     * 
     */
    public void tallenna() throws SailoException {
        
        
        
        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete();
        ftied.renameTo(fbak);
        
        try (PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath()))) {
            
            for (int i=0;i<this.lkm;i++) {
                
                fo.println(alkiot[i].toString());
                
            }
        } catch (IOException e) {
            throw new SailoException("Virhe j‰senten tallentamisessa");
            
        }
        
        
    }
    
    private String getBakNimi() {
        
        return tiedostonPerusNimi + ".bak";
    }

    /**
     * Palauttaa tiedoston nimen, jota k‰ytet‰‰n tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        
        return getTiedostonPerusNimi() + ".dat";
    }

    /**
     * @throws SailoException Jos jotain menee pieleen
     * 
     */
    public void lueTiedostosta() throws SailoException {
        
        lueTiedostosta(getTiedostonNimi());
        
    }

    /**
     * Palauttaa tiedoston nimen
     * @return tiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        
        return this.tiedostonPerusNimi;
    }
    
    /**
     * Palautetaan hakuehtoa vastaavat j‰senet
     * @param hakuehto hakuehto
     * @return tietorakenteen lˆytyneist‰ j‰senist‰
     */
    public Collection<Jasen> etsi(String hakuehto) {
        String ehto = "*";
        if (hakuehto!= null && hakuehto.length()>0 ) ehto = hakuehto ;
        Collection<Jasen> loytyneet = new ArrayList<Jasen>();
        for (Jasen jasen : this) {
            if (WildChars.onkoSamat(jasen.getName(), ehto))
            loytyneet.add(jasen);
        }
       
        return loytyneet;
    }
    
    
    @Override
    public Iterator<Jasen> iterator() {
        
        return new JasenetIterator();
    }
    
    /**
     * @author Tapio Saarnio
     * @version 13 Apr 2019
     * Luokka j‰senten iteroimiseksi
     * @example 
     * <pre name="test">
     * #THROWS SailoException
     * #PACKAGEIMPORT
     * #import java.util.*;
     * Jasenet jasenet = new Jasenet();
     * Jasen trevor1 = new Jasen();
     * trevor1.rekisteroi();
     * jasenet.lisaa(trevor1);
     * Jasen trevor2 = new Jasen();
     * trevor2.rekisteroi();
     * jasenet.lisaa(trevor2);
     * jasenet.lisaa(trevor1);
     * StringBuffer ids = new StringBuffer(30);
     * for(Jasen jasen:jasenet)
     *   ids.append(" "+jasen.getId());
     *   
     * String tulos = " " + trevor1.getId() + " " + trevor2.getId() + " " + trevor1.getId();
     * 
     * ids.toString() === tulos;
     * 
     * ids = new StringBuffer(30);
     * for (Iterator<Jasen> i=jasenet.iterator(); i.hasNext();) {
     *   Jasen jasen=i.next();
     *   ids.append(" "+jasen.getId());
     * }
     * ids.toString() === tulos;
     * Iterator<Jasen> i=jasenet.iterator();
     * i.next() == trevor1 === true;
     * i.next() == trevor2 === true;
     * i.next() == trevor1 === true;
     * i.next(); #THROWS NoSuchElementException
     * 
     * </pre>
     */
    public class JasenetIterator implements Iterator<Jasen>{
        
        private int kohdalla=0;
        
        /**
         * Onko olemassa seuraavaa j‰sent‰
         * @return true jos on viel‰ j‰seni‰
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }

        /**
         * Annetaan seuraava j‰sen 
         * @return seuraava j‰sen
         * @throws NoSuchElementException jos seuraavaa alkiota ei ole
         * 
         */
        @Override
        public Jasen next() throws NoSuchElementException {
            
            if ( !hasNext()) throw new NoSuchElementException("Ei oo");
            return getJasen(kohdalla++);
        }
        
        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         */
         @Override
         public void remove() throws UnsupportedOperationException {
             
             throw new UnsupportedOperationException("Ei poisteta");
         }
        
        
    }
    
    

}
