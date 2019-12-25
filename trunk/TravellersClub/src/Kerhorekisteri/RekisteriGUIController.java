package Kerhorekisteri;


import java.util.Collection;


import Luokat.Jasen;
import Luokat.Rekisteri;
import Luokat.SailoException;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

/**
 * @author Tapio Saarnio
 * @version 10 Feb 2019
 *
 */
public class RekisteriGUIController implements ModalControllerInterface<String> {

    @FXML
    private Button registerButton;
    
    @FXML
    private MenuItem saveMenu;
    
    @FXML
    private ScrollPane Jnimi;

    @FXML
    private MenuItem quitMenu;

    @FXML
    private MenuItem editmemberMenu;

    @FXML
    private MenuItem deleteMemberMenu;

    @FXML
    private MenuItem aboutMenu;

    @FXML
    private MenuItem infoMenu;

    @FXML
    private RadioButton membersRB;

    @FXML
    private RadioButton countriesRB;

    @FXML
    private TextField hakuKentta;

    @FXML
    private Button searchButton;

    @FXML
    private Button newMemberButton;

    @FXML
    private ListChooser<Jasen> tiedotList;

    @FXML
    private void handleNewMember() {
        uusiJasen();
    }
    
    

    @FXML
    private void handleSearch() {
         
        hae(tiedotList.getSelectedObject().getId());
    }
    
    
    @FXML
    
    private void handleRegister() {
       ModalController.showModal(RekisteriGUIController.class.getResource("RekisteriGUIView.fxml"), "Rekisteri", null, "");
       //avaa();
    }
    
    @FXML
    private void handleQuit() {
        
        //
    }
     
    @FXML
    private void handleAbout() {
        Dialogs.showMessageDialog("Ei ole vielä tietoa");
    }
    
    @FXML
    private void handleProfiili() {
        
         Jasen jasen = popupGController.profiili(null, tiedotList.getSelectedObject(), rekisteri, tiedotList.getSelectedObject());
         hae(jasen.getId());
        //ModalController.showModal(RekisteriGUIController.class.getResource("popupG.fxml"), "Profiili", null, tiedotList.getSelectedObject());
               
    }
    
    @FXML
    private void handleInfo() {
        Dialogs.showMessageDialog("Ei ole vielä infoa");
    }
    
    
    @FXML
    private void handleSave() {
        
        tallenna();
    }




    @Override
    public void handleShown() {
    	//
        
        
    }
    

//==============================================================================

    private Rekisteri rekisteri=new Rekisteri();
    
    

    /**
     * Hakee jäsenten tiedot listaan
     * @param jnro jäsennumero
     */
    public void hae(final int jnro) {
        
       /*if(tiedotList!=null) {
        tiedotList.clear();
        } else {
            return;
        }
        */
        
        
        String ehto = hakuKentta.getText();
        if (ehto.indexOf('*')<0) {
            
            ehto = "*" + ehto + "*";
        }
        
        tiedotList.clear();
        int index = 0;
        Collection<Jasen> jasenet;
        try {
            jasenet = rekisteri.etsi(ehto);
            int i=0;
        for (Jasen jasen:jasenet) {
            if (jasen.getId() == jnro) index =i;
           tiedotList.add(jasen.getName(), jasen);
           i++;
        }
        } catch (SailoException ex) {
            Dialogs.showMessageDialog(ex.getMessage());
        }
        tiedotList.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää jäsenen
    }
    
    
    /**
     * Luo uuden jäsenen jota aletaan editoimaan 
     */
    public void uusiJasen() {
        
    	
        Jasen uusi = new Jasen();
        uusi = popupGController.kysyJasen(null, uusi, this.rekisteri);
        rekisteri.lisaa(uusi);
        hae(uusi.getId());
    }   

    /**
     * Asetetaan viite käytettävään rekisteriin
     * @param rekisteri mikä rekisteri asetetaan
     */
    void setRekisteri(Rekisteri rekisteri) {
        
        this.rekisteri=rekisteri;
        
    }
    



    /**
     * Määritellään mitä palautetaan kun ikkunasta palataan
     */
    @Override
    public String getResult() {
        // TODO Auto-generated method stub
        return null;
    }


    
    @Override
    public void setDefault(String arg0) {
    	avaa();
    	//tiedotList.clear();
        //tiedotList.addSelectionListener(e -> handleProfiili());
       
    }
    
    /**
     * Tallennetaan muutetut tiedot
     */
    public void tallenna() {
        
        try {
            
           rekisteri.tallenna();
           
        } catch (SailoException ex){
            Dialogs.showMessageDialog("Tallennuksessa ongelmia!" + ex.getMessage());
            
        }
        
    }
    

    /**
     * Luetaan tiedosto
     */
    public void avaa() {
        
        lueTiedosto("TravellersClub");
        
    }
    
    /**
     * @param nimi tiedosto josta luetaan
     */
    protected void lueTiedosto(String nimi) {
        
        try {
            rekisteri.lueTiedostosta(nimi);
            hae(0);
        } catch (SailoException e) {
            String virhe=e.getMessage();
            if(virhe!=null) Dialogs.showMessageDialog(virhe);
        }
    }

   
}


