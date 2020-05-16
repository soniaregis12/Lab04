package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;

public class Model {

	CorsoDAO dao;
	
	public Model() {
		dao = new CorsoDAO();
	}
	
	public List<Corso> getTuttiICorsi(){
		return dao.getTuttiICorsi();
	}
	
	public List<Studente> getStudentiCorso(Corso corso){
		return dao.getStudentiIscrittiAlCorso(corso);
	}
	
	public String getNomeCognomeDaMatricola(int matricola) {
		return dao.getNomeCognomeDaMatricola(matricola);
	}
	
	public List<Corso> getCorsiDaMatricola(int matricola){
		return dao.getCorsiDaMatricola(matricola);
	}
	
	public boolean ifIscrittoAlCorso(Corso corso, int matricola) {
		return dao.studenteIscrittoAlCorso(corso, matricola);
	}
	
	public boolean iscriviStudenteACorso(Studente s, Corso c) {
		return dao.inscriviStudenteACorso(s, c);
	}
}
