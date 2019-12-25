package Kerhorekisteri;

import java.net.URL;
import java.util.ResourceBundle;

import Luokat.MaatInfo;
import Luokat.Rekisteri;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Tapio Saarnio
 * @version 19 Apr 2019
 *
 */
public class VisitInfoGController implements ModalControllerInterface<MaatInfo>, Initializable {

    
    
    @FXML
    private TextField countryText;

    @FXML
    private TextField firstTimeText;

    @FXML
    private TextField numberOfText;
    

    @FXML
    private Button cancelButton;

    @FXML
    private Button okButton;

    @FXML
    void handleCancel() {
         
        this.visit.setMaa(this.klooni.getMaa());
        this.visit.setVek(this.klooni.getVek());
        this.visit.setMk(this.klooni.getMk());
        ModalController.closeStage(countryText);
    }

    @FXML
    void handleOk() {
       ModalController.closeStage(countryText);
    }
    
    
    
   
    
    
   // ======================================================================================================================
    
    
     private Rekisteri rekisteri;
     private MaatInfo visit = new MaatInfo();
     private MaatInfo klooni = new MaatInfo();
     private TextField edits[];
    
    
    
    @Override
    public MaatInfo getResult() {
        
        return visit;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(MaatInfo visit2) {
        
        this.klooni.setMaa(visit2.getMaa());
        this.klooni.setVek(visit2.getMk());
        this.klooni.setMk(visit2.getMk());
                
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        alusta();
        
    }

    private void alusta() {
        
        this.edits = new TextField[] {countryText, firstTimeText, numberOfText};
        
        int i = 0;
        
        for (TextField edit : edits) {
            
            final int k = ++i;
            edit.setOnKeyReleased( e -> kasitteleMuutosVierailuun(k, (TextField)(e.getSource())));
        }
        
    }
        
        /**
         * P‰ivitet‰‰n j‰senen tiedot kun muutos tapahtuu
         * @param k
         * @param edit Mik‰ tekstikentt‰
         */
        private void kasitteleMuutosVierailuun(int k, TextField edit) {
       
        if (this.visit == null) return;
        
        String s = edit.getText();
        
        switch(k) {
        
        case 1 : this.visit.setMaa(s); break;
        case 2 : this.visit.setVek(s); break;
        case 3 : this.visit.setMk(s); break;
        default:
        
        }
        
    }

    /**
     * @param modalityStage sovellukselle
     * @param visit mit‰ dataan n‰ytet‰‰n oletuksena
     * @param rekisteri rekisteri
     * @return null jos painetaan Cancel muuten t‰ytetty vierailu
     */
    public static MaatInfo kysyVisit(Stage modalityStage, MaatInfo visit, Rekisteri rekisteri) {
        
        return  ModalController.<MaatInfo, VisitInfoGController>showModal(VisitInfoGController.class.getResource("VisitInfo.fxml"), "new visit", modalityStage, visit, ctrl -> {ctrl.setRekisteri(rekisteri);});     }

   private void setRekisteri(Rekisteri rekisteri) {
        
        this.rekisteri = rekisteri;
        
    }

    /**
     * @param modalityStage entied‰ 
     * @param selectedObject vierailu 
     * @param rekisteri2 rekisteri
     * @param visit vierailu
     * @return vierailu samanlaisena tai muutettuna
     */
    public static MaatInfo visit(Stage modalityStage, MaatInfo selectedObject, Rekisteri rekisteri2, MaatInfo visit) {
        
        return ModalController.<MaatInfo, VisitInfoGController>showModal(VisitInfoGController.class.getResource("VisitInfo.fxml"), "Visit", modalityStage, selectedObject, ctrl -> {ctrl.setRekisteri(rekisteri2); ctrl.setVierailu(visit);});
    }

    /**
     * Asetetaan vierailun tiedot 
     * @param visit2
     */
    private void setVierailu(MaatInfo visit2) {
        
        this.visit = visit2;
        countryText.setText(visit2.getMaa());
        firstTimeText.setText(visit2.getVek());
        numberOfText.setText(visit2.getMk());
        
    }
 
}
