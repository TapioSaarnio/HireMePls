package Luokat;

/**
 * Tarkistaa henkilötunnuksen oikeellisuuden
 * @author Tapio Saarnio
 * @version 8 Mar 2019
 *
 */
public class DobCheck {
    
    /**
     * arvoo kokonaisluvun annettujen parametrejen väliltä
     * @param ala alaraja
     * @param yla ylaraja 
     * @return satunnainen arvo
     */
    public static int rand(int ala, int yla) {
        

        double n = (yla-ala)*Math.random() + ala;
        return (int)Math.round(n);
    }
    
    /**
     * Arpoo syntymäajan
     * @return syntymäaika
     */
    public static String arvodob() {
        
        String apudob=String.format("%02d.%02d.%04d", rand(1, 31), rand(1, 12), rand(1900, 2019));
        return apudob;
    }
    
    /**
     * @param syote kentässä oleva syöte
     * @return true jos syöte oikein, false jos ei
     * @example
     * <pre name="test">
     * String syote = "04.01.2019";
     * tarkista(syote)===true;
     * syote = "33.00.0000";
     * tarkista(syote)===false;
     * </pre>
     */
    public static boolean tarkista (String syote) {
        
        
        if(syote.matches("^(0[1-9]|1[012]).(0[1-9]|[12][0-9]).[0-9]{4}")){
            
            return true;
            
        }
        return false;
        
        
    }

}
