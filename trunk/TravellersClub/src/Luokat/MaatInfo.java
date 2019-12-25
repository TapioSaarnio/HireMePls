package Luokat;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;


/**
 * Luokka joka tallentaa tietoja vierailusta
 * @author Tapio Saarnio
 * @version 9 Mar 2019
 *
 */
public class MaatInfo {
    
    private int id;
    private int jasenNro;
    private String maa;
    private String vek;
    private String mk;
    
    private static int seuraavaNro=1;
    
    
    
    /**
     * Muodostaja
     */
    public MaatInfo() {
        
        //Oma alustus riitt‰‰
    }
    
    /**
     * Selvitt‰‰ vierailun tiedot
     * @param rivi rivi josta tiedot parsitaan
     * MaatInfo vierailu = new MaatInfo();
     * vierailu.parse("1|1|Finland|1|1");
     * vierailu.toString()==="1|1|Finland|1|1";
     * </pre>
     */
    public void parse(String rivi) {
        
        StringBuilder sb = new StringBuilder(rivi);
        setId(Mjonot.erota(sb, '|', getId()));
        this.jasenNro = Mjonot.erota(sb, '|', this.jasenNro);
        this.maa = Mjonot.erota(sb, '|', this.maa);
        this.vek = Mjonot.erota(sb, '|', this.vek);
        this.mk = Mjonot.erota(sb, '|', this.mk);
        
    }
    
    private void setId(int nro) {
        
        this.id = nro;
        if (this.id>=seuraavaNro) {
            seuraavaNro = this.id+1;
        }
        
    }

    /**
     * @example
     * <pre name="test">
     * MaatInfo maatinfo=new MaatInfo();
     * maatinfo.rekisteroi();
     * MaatInfo maatinfo2=new MaatInfo();
     * maatinfo2.rekisteroi();
     * int n1=maatinfo.getId();
     * int n2=maatinfo2.getId();
     * n1===n2-1;
     * </pre>
     */
    public void rekisteroi() {
        
        this.id=seuraavaNro;
        seuraavaNro++;
    }
    
    /**
     * @param nro (jasenNro) kenen vierailusta kyse
     */
    public void taytaTiedoilla(int nro) {
        
        this.jasenNro=nro;
        this.maa="Yellow nation";
        this.vek=""+DobCheck.rand(1900, 2000);
        this.mk=""+DobCheck.rand(1, 20);
    }
    
    /**
     * @param nro mille j‰senelle lis‰t‰‰n MaatInfo
     */
    public MaatInfo(int nro) {
        
        this.jasenNro=nro;
    }
    
    /**
     * tulostetaan j‰senen tiedot
     * @param out minne tulostetaan
     */
    public void tulosta(PrintStream out) {
        
        out.print(this.maa + "|" + this.vek + "|" + this.mk);
        out.println();
    }
    
    @Override
    public String toString() {
        
         String palautus=String.format("%s|%s|%s|%s|%s" , this.getId(), this.getJasenNro(), this.getMaa(), this.getVek(), this.getMk());
         return palautus;      
    }
    
    /**
     * Laittaa vierailun tiedot taulukoksi jotta voidaan tallentaa stringgridiin
     * @return taulukko olion tiedoista
     */
    public String[] toTaulukko() {
        
        String[] palautus=new String[3];
        palautus[0]=this.getMaa();
        palautus[1]=this.getVek();
        palautus[2]=this.getMk();
        return palautus;
    }
    
    /**
     * @return Vierailtu maa
     */
    public String getMaa() {
        return this.maa;
    }
    
    /**
     * @return Vierailun eka kerta vuosiluku
     */
    public String getVek() {
        return this.vek;
    }
    
    /**
     * @return monta kertaa vierailtu
     */
    public String getMk() {
        return this.mk;
    }
    
    
    
    /**
     * @return id numero
     * @example
     * <pre name="test">
     * MaatInfo ygone=new MaatInfo();
     * MaatInfo kagone=new MaatInfo();
     * ygone.rekisteroi();
     * kagone.rekisteroi();
     * int id1=ygone.getId();
     * int id2=kagone.getId();
     * id1===id2-1;
     * </pre>
     */
    public int lisaa() {
        
        if(this.id!=0) return this.id;
        
        this.id=seuraavaNro;
        seuraavaNro++;
        
        return this.id;
    }
    
    /**
     * T‰ytet‰‰n vierailun tiedot 
     */
    public void taytaTiedoilla() {
        
        this.maa="Imaginationland";
        this.vek=""+ DobCheck.rand(1950, 2019);
        this.mk=""+ DobCheck.rand(1, 100);
    }
    
    /**
     * antaa tietyn vierailun id:n
     * @return tietyn vierailun id
     * @example
     * <pre name="test">
     * MaatInfo maatinfo=new MaatInfo();
     * maatinfo.lisaa();
     * maatinfo.getId()===1;
     * </pre>
     */
    public int getId() {
        
        return this.id;
    }
    
    /**
     * Palautetaan MaatInfon j‰sennumero
     * @return MaatInfon j‰sennumero
     * @example
     * <pre name="test">
     * Jasen trevor=new Jasen();
     * trevor.rekisteroi();
     * MaatInfo maatinfo=new MaatInfo(1);
     * maatinfo.getJasenNro()===1;
     * </pre>
     * 
     */
    public int getJasenNro() {
        return this.jasenNro;
    }
    
    /**
     * @param maa2 maa
     */
    public void setMaa(String maa2) {
        
        this.maa = maa2;
        
    }
    
    /**
     * @param vek2 vieraili ekan kerran
     */
    public void setVek(String vek2) {
        
        this.vek = vek2;
    }
    
    /**
     * @param mk2 monta kertaa vieraili
     */
    public void setMk(String mk2) {
        
        this.mk = mk2;
    }
    

    
    /**
     * @param os Tietovirta minne tulostetaan
     */
    public void tulosta(OutputStream os) {
        
        tulosta(new PrintStream(os));
        
    }

    /**
     * Asetetaan kenelle vierailu kuuluu
     * @param id2 J‰senen id
     */
    public void setJasenNro(int id2) {
        
        this.jasenNro = id2;
        
    }

}
