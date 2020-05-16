package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);

				// Crea un nuovo JAVA Bean Corso
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				
				Corso c = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				corsi.add(c);
				
			}

			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public void getCorso(Corso corso) {
		// TODO
	}
	
	/*
	 * Data una matricola cerco nome e cognome
	 */
	public String getNomeCognomeDaMatricola(int matricola) {
		String pippo = null;
		String sql = "SELECT s.nome, s.cognome FROM studente AS s WHERE s.matricola = ? "; 
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				pippo =rs.getString("nome") + " " + rs.getString("cognome");
				
			}

			conn.close();
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	return pippo;
	}
	
	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {

		List<Studente> studenti = new LinkedList<Studente>();

		try {
			Connection conn = ConnectDB.getConnection();
			
			
			if(corso.getNome().equals("Tutti")) {
				final String sqlTutti = "SELECT s.matricola, s.nome, s.cognome, s.CDS FROM studente AS s";
				PreparedStatement st = conn.prepareStatement(sqlTutti);
				ResultSet res = st.executeQuery();
				
				while (res.next()) {
					Studente studente = new Studente(res.getInt("matricola"), res.getString("nome"), res.getString("nome"), res.getString("cds"));
					studenti.add(studente);
				}
				
			}else {
				final String sql = "SELECT s.matricola, s.nome, s.cognome, s.CDS FROM studente AS s, iscrizione AS i WHERE s.matricola = i.matricola AND i.codins = ?";
				PreparedStatement st = conn.prepareStatement(sql);
				st.setString(1, corso.getCodins());
				
				ResultSet rs = st.executeQuery();

				while (rs.next()) {

					String cognome = rs.getString("cognome");
					int matricola = rs.getInt("matricola");
					String nome = rs.getString("nome");
					String cds = rs.getString("CDS");

					System.out.println(matricola + " " + nome + " " + cognome + " " + cds);

					// Crea un nuovo JAVA Bean Corso
					// Aggiungi il nuovo oggetto Corso alla lista corsi
					
					Studente studente = new Studente(matricola, cognome, nome, cds);
					studenti.add(studente);
				}

				conn.close();
			}
		
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		return studenti;
	}
	
	/*
	 * Data una matricola, ritorna tutti i corsi a cui è iscritta
	 */
	public List<Corso> getCorsiDaMatricola(int matricola){
		final String sql = "SELECT c.codins, c.crediti, c.nome, c.pd FROM corso AS c, iscrizione AS i WHERE c.codins = i.codins AND i.matricola=?";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);

				// Crea un nuovo JAVA Bean Corso
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				
				Corso c = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				corsi.add(c);
				
			}

			conn.close();
			} catch (SQLException e) {
				// e.printStackTrace();
				throw new RuntimeException("Errore Db", e);
			}
			return corsi;
	}
	
	/*
	 * Dato un corso e una matricola, vedo se quello studente è iscritto a quel corso
	 */
	public boolean studenteIscrittoAlCorso(Corso corso, int matricola) {
		final String sql = "SELECT i.matricola FROM iscrizione AS i WHERE i.matricola=? AND i.codins=?";

		String pippo = "";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			st.setString(2, corso.getCodins());

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				pippo = rs.getString("matricola");
			}
			conn.close();
			} catch (SQLException e) {
				// e.printStackTrace();
				throw new RuntimeException("Errore Db", e);
			}
		if(pippo == "") {
			return false;
		}else {
			return true;
		}
	}
	
	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		// ritorna true se l'iscrizione e' avvenuta con successo
		
		String sql = "INSERT IGNORE INTO 'iscritticorsi'.'iscrizione' ('matricola', 'codins') VALUES(?,?)";
		boolean pippo = false;
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodins());
			
			int res = st.executeUpdate();	

			if (res == 1)
				pippo = true;

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		
		return pippo;
	}

}
