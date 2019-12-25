package Luokat;

/**
 * Poikkeusluokka tietorakenteesta aiheutuville poikkeuksille
 * @author Tapio Saarnio
 * @version 2 Apr 2019
 *
 */
public class SailoException extends Exception{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Poikkeuksen muodostaja jolle tuodaan poikkeuksessa käytettävä viesti
     * @param viesti Poikkeuksen viesti
     */
    public SailoException (String viesti) {
        
        super(viesti);
    }

}
