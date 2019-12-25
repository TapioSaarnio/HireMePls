
package Luokat;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Tapio Saarnio
 * @version 25 Feb 2019
 *
 */
public class Jasen {
    
    private int id;
    
    private static int seuraavaNro=1;
    
    private String name="";
    private String dob="";
    private String nationality="";
    private String adress="";
    private String city="";
    private String postcode;
    private String email="";
    private String phone;
    
    /**
     * @param args Ei k�yt�ss�
     */
    public static void main(String[] args) {
        
        Jasen maija=new Jasen();
        Jasen per=new Jasen();
        maija.rekisteroi();
        per.rekisteroi();
        
        maija.tulosta(System.out);
        per.tulosta(System.out);
        
        maija.taytaTiedoilla();
        per.taytaTiedoilla();
        
        maija.tulosta(System.out);
        per.tulosta(System.out);
        
             
    
    }
    
    /**
     * T�ytt�� j�senen oletustiedoilla
     */
     public void taytaTiedoilla() {
        
        this.name = "Trevor Traveller";
        this.dob=DobCheck.arvodob();
        this.nationality="The Yellow State";
        this.adress="Evergreen Terrace 1";
        this.city="Yellow city";
        this.postcode="0";
        this.email="TrevorTheTraveller@gmail.com";
        this.phone="1010101010";
        
        
    }
    
    

    /**
     * Antaa j�senelle seuraavan id-numeron
     * @return J�senen id
     * @example
     * <pre name="test">
     *  Jasen riku=new Jasen();
     *  riku.getId()===0;
     *  riku.rekisteroi();
     *  Jasen tunna=new Jasen();
     *  tunna.rekisteroi();
     *  tunna.rekisteroi();
     *  int id1=riku.getId();
     *  int id2=tunna.getId();
     *  id1===id2-1;
     * </pre>
     */
    public int rekisteroi(){
        
        if(this.id!=0) return this.id;
        
        this.id=seuraavaNro;
        seuraavaNro++;
        
        
        return this.id;
    }
    
    /**
     * Asettaa tunnusnumeron ja kasvattaa seuraavaa numeroa yhdell�
     * @param id asetettava tunnusnumero
     */
    private void setId(int id) {
        
        this.id=id;
        if (this.id>=seuraavaNro) {
            seuraavaNro = this.id+1;
        }
        
    }
    
    /**
     * Palautetaan j�senen id numero
     * @return J�senen id numero
     * @example
     * <pre name="test">
     * Jasen matti=new Jasen();
     * matti.rekisteroi();
     * matti.getId()===1;
     * Jasen sami=new Jasen();
     * sami.rekisteroi();
     * sami.getId()===2;
     * </pre>
     */
    public int getId() {
        
        return this.id;
    }
    
    /**
     * Palautetaan syntym�aika
     * @return syntym�aika
     */
    public String getDob() {
        
        return this.dob;
    }
    
    /**
     * Palautetaan j�senen kansallisuus
     * @return Kansallisuus
     * @example
     * <pre name="test">
     * Jasen Reijo=new Jasen();
     * Reijo.taytaTiedoilla();
     * Reijo.getNationality()==="The Yellow State";
     * </pre>
     */
    public String getNationality() {
        
        return this.nationality;
    }
    
    /**
     * Palautetaan j�senen kotikaupunki
     * @return Kaupunki
     * @example
     * <pre name="test">
     * Jasen Perttu=new Jasen();
     * Perttu.taytaTiedoilla();
     * Perttu.getCity()==="Yellow city";
     * </pre>
     */
    public String getCity() {
        
        return this.city;
    }
    
    
    
    /**
     * Palautetaan j�senen nimi
     * @return J�senen nimi
     * @example
     * <pre name="test">
     * Jasen Toni = new Jasen();
     * Toni.taytaTiedoilla();
     * Toni.getName()==="Trevor Traveller";
     * </pre>
     */
    public String getName() {
    	return this.name;
    }
    
    /**
     * Palautetaan j�senen osoite
     * @return j�senen osoite
     * @example
     * <pre name="test">
     * Jasen Biggie=new Jasen();
     * Biggie.taytaTiedoilla();
     * Biggie.getAdress()==="Evergreen Terrace 1";
     * </pre> 
     */
    public String getAdress() {
        
        return this.adress;
    }
    
    /**
     * Palautetaan j�senen s�hk�postiosoite
     * @return j�senen s�hk�postiosoite
     * @example
     * <pre name="test">
     * Jasen Peik=new Jasen();
     * Peik.taytaTiedoilla();
     * Peik.getEmail()==="TrevorTheTraveller@gmail.com";
     * </pre>
     */
    public String getEmail() {
        
        return this.email;
    }
    
    /**
     * Palautetaan j�senen postikoodi
     * @return j�senen postikoodi
     * @example
     * <pre name="test">
     * Jasen Miikka=new Jasen();
     * Miikka.taytaTiedoilla();
     * Miikka.getPostCode()==="0";
     * </pre>
     */
    public String getPostCode() {
        
        return this.postcode;
    
    }
    
    /**
     * Palautetaan j�senen puhelinnumero
     * @return j�senen puhelinnumero
     * @example
     * <pre name="test">
     * Jasen Jerry=new Jasen();
     * Jerry.taytaTiedoilla();
     * Jerry.getPhone()==="1010101010";
     * </pre>
     */
    public String getPhone() {
        
        return this.phone;
    }
    
    
    /**
     * Tulostetaan tiedot jotta n�hd��n k�ytt�ytyyk� ohjelma oikein
     * @param out minne tulostetaan
     */
    public void tulosta(PrintStream out) {
        
        out.println("Name: " + this.name);
        out.println("Date Of Birth: " + this.dob);
        out.println("Nationality: " + this.nationality);
        out.println("Adress: " + this.adress);
        out.println("City: " + this.city);
        out.println("Postcode: " + this.postcode);
        out.println("Email: " + this.email);
        out.println("Phone: " + this.phone);
    }
    
    /**
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        
        tulosta(new PrintStream(os));
    }
    
    /**
     * 
     */
    @Override
    public String toString() {
        
        return String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s|%s", this.getId(), this.getName(), this.getDob(), this.getNationality(), this.getAdress(), this.getCity(), this.getCity(), this.getPostCode(), this.getEmail(), this.getPhone());
    }

    /**
     * Selvitt�� j�senen tiedot tolpalla erotellusta merkkijonosta
     * @param rivi rivi josta selvitet��n tietoa
     * @example
     * <pre name ="test">
     * Jasen trevor = new Jasen();
     * trevor.parse("1|TrevorTheTraveller|66.66.666|Murica|jokuosote|jokukaupunki|40100|TrevorTheTraveller@gmail.com|00000");
     * trevor.toString()==="1|TrevorTheTraveller|66.66.666|Murica|jokuosote|jokukaupunki|40100|TrevorTheTraveller@gmail.com|00000";
     * </pre>
     */
    public void parse(String rivi) {
        
        StringBuilder sb = new StringBuilder(rivi);
        setId(Mjonot.erota(sb, '|', getId()));
        this.name = Mjonot.erota(sb, '|', this.name);
        this.dob = Mjonot.erota(sb, '|', this.dob);
        this.nationality = Mjonot.erota(sb, '|', this.nationality);
        this.adress = Mjonot.erota(sb, '|', this.adress);
        this.city = Mjonot.erota(sb, '|', this.city);
        this.postcode = Mjonot.erota(sb, '|', this.postcode);
        this.email = Mjonot.erota(sb, '|', this.email);
        this.phone = Mjonot.erota(sb, '|', this.phone);
    }

    /**
     * @param s nimi
     */
    public void setName(String s) {
        
        this.name = s;
    }

    /**
     * @param s syntym�aika
     */
    public void setDob(String s) {
        
        this.dob = s;
        
    }

    /**
     * @param s kansallisuus
     */
    public void setNationality(String s) {
        
        this.nationality = s;
        
    }

    /**
     * @param s osoite
     */
    public void setAdress(String s) {
        
        this.adress = s;
    }

    /**
     * @param s kaupunki
     */
    public void setCity(String s) {
        
        this.city = s;
        
    }
    
    /**
     * @param s postiosoite
     */
    public void setPostcode(String s) {
        
        this.postcode = s;
    }
    
    /**
     * @param s s�hk�postiosoite
     */
    public void setEmail(String s) {
    
        this.email = s;
     }
    
    /**
     * @param s puhelinnumero
     */
    public void setPhone(String s) {
        
        this.phone = s;
    }

  
}
