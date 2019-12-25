package Kerhorekisteri;





import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Luokat.DobCheck;
import Luokat.Jasen;
import Luokat.MaatInfo;
import Luokat.Rekisteri;
import Luokat.SailoException;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.StringGrid;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Tapio Saarnio
 * @version 14 Feb 2019
 *
 */
public class popupGController implements ModalControllerInterface<Jasen>, Initializable {

    @FXML
    private MenuItem mClose;

    @FXML
    private MenuItem mDelete;

    @FXML
    private MenuItem mHelp;

    @FXML
    private Button bSave;
    
    @FXML
    private MenuItem addCountryMenu;
    
    @FXML
    private TextField NameText;

    @FXML
    private TextField DOBText;

    @FXML
    private TextField NationalityText;

    @FXML
    private TextField AdressText;

    @FXML
    private TextField CityText;

    @FXML
    private TextField PostcodeText;

    @FXML
    private TextField EmailText;

    @FXML
    private TextField PhoneText;
    
    @FXML
    private StringGrid<MaatInfo> vierailutGrid;
    
    @FXML
    private Button cancelButton;
    
    @FXML
    private Button saveButton;
    
    @FXML
    void handleCancel() {
        
        this.jasen.setName(this.klooni.getName());
        this.jasen.setDob(this.klooni.getName());
        this.jasen.setNationality(this.klooni.getNationality());
        this.jasen.setAdress(this.klooni.getAdress());
        this.jasen.setCity(this.klooni.getCity());
        this.jasen.setPostcode(this.klooni.getPostCode());
        this.jasen.setEmail(this.klooni.getEmail());
        this.jasen.setPhone(this.klooni.getPhone());
        ModalController.closeStage(NameText);

    }
    
    @FXML
    void handleSave() throws SailoException {
        
        if(DobCheck.tarkista(jasen.getDob())) {
        ModalController.closeStage(NameText);
        rekisteri.tallennavierailut();
        return;
        }
        
        Dialogs.showMessageDialog("Tarkista p‰iv‰m‰‰r‰! P‰iv‰m‰‰r‰n tulee olla muotoa xx.xx.xxxx");
        
        
    }

    @FXML
    void handleClose() {
        Dialogs.showMessageDialog("Ei osata sulkea");
    }

    @FXML
    void handlePoista() {
        
        rekisteri.poista(this.jasen);
        ModalController.closeStage(NameText);
    }

    @FXML
    void handleHelp() {
        Dialogs.showMessageDialog("Ei osata auttaa");
    }
    
    @FXML
    void handleaddCountryMenu() {
        
        
        newVisit();
        //uusiVierailu();
        
        //Dialogs.showMessageDialog("Ei osata lis‰t‰ maata");

    }
    
    @FXML
    void handleVierailu() {
        
        MaatInfo maatinfo = VisitInfoGController.visit(null, vierailutGrid.getObject(), rekisteri, vierailutGrid.getObject());
        setVierailut(maatinfo.getJasenNro());
    }



    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }




    
//===========================================================================

    private Jasen jasen = new Jasen();
    private Jasen klooni = new Jasen();
    private Rekisteri rekisteri;
    private TextField edits[];
    
    /**
     * M‰‰ritet‰‰n mit‰ palautetaan kun ikkunasta poistutaan
     */
    @Override
    public Jasen getResult() {
        
        return this.jasen;
    }
    
    /**
     * Kloonataan tiedot talteen jotta voidaan palauttaa jos painetaan cancel ja rekisteroidaan jasen.
     */
    @Override
    public void setDefault(Jasen trevor) {
        
        this.klooni.setName(trevor.getName());
        this.klooni.setDob(trevor.getName());
        this.klooni.setNationality(trevor.getNationality());
        this.klooni.setAdress(trevor.getAdress());
        this.klooni.setCity(trevor.getCity());
        this.klooni.setPostcode(trevor.getPostCode());
        this.klooni.setEmail(trevor.getEmail());
        this.klooni.setPhone(trevor.getPhone());
        jasen.rekisteroi();
        
    }
        
        /**
         * Lis‰‰ uuden vierailun rekisteriin ja listaan
         */
        private void newVisit() {
           
        MaatInfo visit = new MaatInfo();
        visit = VisitInfoGController.kysyVisit(null, visit, this.rekisteri);
        if (visit == null ) return;
        visit.setJasenNro(jasen.getId());
        visit.rekisteroi();
        rekisteri.addVisit(visit);
        vierailutGrid.add(visit, visit.toTaulukko());
        
     }
    
    
    /**
     * @param jsennro j‰sennumero
     * P‰ivtet‰‰n vierailut listaan
     */
    public void setVierailut(int jsennro){
        
        vierailutGrid.clear();
        List<MaatInfo> vierailut=new ArrayList<MaatInfo>();
        vierailut=rekisteri.getVierailut(jsennro);
        for (MaatInfo vierailu:vierailut) {
            
            vierailutGrid.add(vierailu, vierailu.toTaulukko());           
        }
     }

    /**
     * @param modalityStage entied‰
     * @param selectedObject j‰sen
     * @param rekisteri2 rekisteri
     * @param trevor j‰sen
     * @return jasen
     */
    public static Jasen profiili(Stage modalityStage, Jasen selectedObject, Rekisteri rekisteri2, Jasen trevor) {
        
           return ModalController.<Jasen, popupGController>showModal(popupGController.class.getResource("popupG.fxml"), "Rekisteri", modalityStage, selectedObject, ctrl -> {ctrl.setRekisteri(rekisteri2); ctrl.setJasen(trevor); ctrl.setVierailut(trevor.getId());});
        
    } 

    /**
     * Laitetaan j‰senen tiedot lomakkeen tekstikenttiin
     * @param trevor j‰sen jonka tiedot laitetaan
     */
    private void setJasen(Jasen trevor) {
        
        this.jasen=trevor;
        this.NameText.setText(jasen.getName());
        this.DOBText.setText(jasen.getDob());
        this.NationalityText.setText(jasen.getNationality());
        this.AdressText.setText(jasen.getNationality());
        this.CityText.setText(jasen.getCity());
        this.PostcodeText.setText(jasen.getPostCode());
        this.EmailText.setText(jasen.getEmail());
        this.PhoneText.setText(jasen.getPhone());
        
    }
    
    /**
     * Asetetaan rekisteri
     * @param rekisteri2
     */
    private void setRekisteri(Rekisteri rekisteri2) {
        
        this.rekisteri=rekisteri2;
        
    }

    /**
     * @param modalityStage sovellukselle
     * @param uusi mit‰ dataan n‰ytet‰‰n oletuksena
     * @param rekisteri2 rekisteri
     * @return null jos painetaan Cancel muuten t‰ytetty j‰sen 
     */
    public static Jasen kysyJasen(Stage modalityStage, Jasen uusi, Rekisteri rekisteri2) {
         
         return ModalController.<Jasen, popupGController>showModal(popupGController.class.getResource("popupG.fxml"), "Uusi j‰sen", modalityStage, uusi, ctrl -> {ctrl.setRekisteri(rekisteri2);});
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		alusta();
		
	}
	
	private void alusta() {
		
		this.edits = new TextField[] {NameText, DOBText, NationalityText, AdressText, CityText, PostcodeText, EmailText, PhoneText};
		int i = 0;
		for (TextField edit: edits) {
			final int k = ++i;
			edit.setOnKeyReleased( e -> kasitteleMuutosJaseneen(k, (TextField)(e.getSource())));
		}
		
		
	}
	
    
	/**
	 * P‰ivitt‰‰ j‰senen tietoja aina kun muutos tapahtuu
	 * @param k 
	 * @param edit
	 */
	private void kasitteleMuutosJaseneen(int k, TextField edit) {
		
		if (this.jasen == null) return;
		
		String s = edit.getText();
		
		switch(k) {
		
		     case 1 : this.jasen.setName(s); break;
		     case 2 : this.jasen.setDob(s); break;
		     case 3 : this.jasen.setNationality(s); break;
		     case 4 : this.jasen.setAdress(s); break; 
		     case 5 : this.jasen.setCity(s); break;
		     case 6 : this.jasen.setPostcode(s); break;
		     case 7 : this.jasen.setEmail(s); break;
		     case 8 : this.jasen.setPhone(s); break;
		     default:
		     
		     
		}
		
	}
	


}
