package it.polito.tdp.lab04;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Corso> comboBoxCorsi;					

    @FXML
    private Button btnCercaIscrittiCorso;

    @FXML
    private TextField txtMatricola;

    @FXML
    private Button btnVerde;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button btnCercaCorsi;

    @FXML
    private Button btnIscrivi;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnReset;

    @FXML
    void Iscrivi(ActionEvent event) {

    }

    @FXML
    void cercaCorsi(ActionEvent event) {
    	String pippo1 = txtMatricola.getText().toString();
    	if(!isAMatricola(pippo1)) {
    		txtResult.setText("ERRORE! Devi inserire correttamente la matricola dello studente!");
    	}else if(comboBoxCorsi.getValue() == null) {
    		
    		int pippo = Integer.parseInt(txtMatricola.getText());
    		StringBuilder sb = new StringBuilder();
    		
        	for(Corso c : model.getCorsiDaMatricola(pippo)) {
        		sb.append(String.format("%-8s ", c.getCodins()));
				sb.append(String.format("%-4s ", c.getCrediti()));
				sb.append(String.format("%-45s ", c.getNome()));
				sb.append(String.format("%-4s ", c.getPd()));
				sb.append("\n");
        	}
        	txtResult.setText(sb.toString());
    	}else{
    		if(model.ifIscrittoAlCorso(comboBoxCorsi.getValue(),Integer.parseInt(txtMatricola.getText()))) {
    			txtResult.setText("Studente già iscritto a questo corso");
    		}else {
    			txtResult.setText("Studente non ancora iscritto a questo corso");
    		}
    	}
    }
   
    private boolean isAMatricola(String pippo1) {
    	boolean result = true;
    	if(pippo1.length() == 6) {
			for (int i=0; i<pippo1.length(); i++) {
				result = result & Character.isDigit(pippo1.charAt(i));
			}
		}
		return result;
	}

	@FXML
    void cercaIscrittiCorso(ActionEvent event) {
    	
    	Corso c = comboBoxCorsi.getValue();
    	
    	if(c == null) {
    		txtResult.setText("ERRORE! Seleziona almeno un corso!");
    	}else {
    		for(Studente s :model.getStudentiCorso(c)) {
        		txtResult.appendText(s.getMatricola() + " " + s.getNome() + " " + s.getCognome() + " " + s.getCDS() + "\n" );
        	}
    	}
    	
    	
    	
    }

    @FXML
    void completamentoAutomatico(ActionEvent event) {

    	String pippo = model.getNomeCognomeDaMatricola(Integer.parseInt(txtMatricola.getText()));
    	
    	if(pippo == null) {
    		txtResult.setText("ERRORE! Matricola non presente!");
    	}else {
    		String array[] = pippo.split(" ");
        	txtNome.setText(array[0]);
        	txtCognome.setText(array[1]);
    	}
    	
    }

    @FXML
    void reset(ActionEvent event) {
    	txtResult.clear();
    	txtCognome.clear();
    	txtMatricola.clear();
    	txtNome.clear();
    	comboBoxCorsi.getSelectionModel().select(-1);
    }

    @FXML
    void initialize() {
        assert comboBoxCorsi != null : "fx:id=\"comboBoxCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaIscrittiCorso != null : "fx:id=\"btnCercaIscrittiCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnVerde != null : "fx:id=\"btnVerde\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		comboBoxCorsi.getItems().add(new Corso("00AAAAA", 0, "Tutti", 0));
		comboBoxCorsi.getItems().addAll(this.model.getTuttiICorsi());
		
		
	}
}
