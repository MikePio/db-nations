package org.java.db.nations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import com.mysql.cj.xdevapi.PreparableStatement;

public class Main {

	public static void main(String[] args) {
		// sostituire 3306/nome_db con la porta da collegare e con il nome del database
		final String url = "jdbc:mysql://localhost:3306/dump_nations";
		// sostituire 'root' con il nome inserito in mamp o PHP Launcher
		final String user = "root";
		// sostituire '' con la password inserita in mamp o PHP Launcher
		final String password = "";

		//cercare una nazione nel terminale
		Scanner sc = new Scanner(System.in);
		System.out.print("\nInserisci il nome della nazione da cercare: ");
		String countryToSearch = sc.nextLine();

		// variabile da inserire nella query
		String wordToSearch = "'%" + countryToSearch + "%'";

		// Query in SQL
		final String sql = " SELECT c.name, c.country_id, r.name, c2.name"
		+ " FROM countries c" 
		+ " JOIN regions r "
		+ " ON c.region_id = r.region_id "
		+ " JOIN continents c2 "
		+ " ON r.continent_id = c2.continent_id"
		+ " WHERE c.name LIKE " +  wordToSearch
		+ " ORDER BY c.name ASC;";

		final String statsByCountryId = " SELECT * "
		+ " FROM country_stats cs "
		+ " WHERE cs.country_id = ? "
		+ " ORDER BY `year` DESC "
		+ " LIMIT 1; ";

	final String languagesByCountryId = " SELECT * "
		+ " FROM languages l "
		+ "	JOIN country_languages cl "
		+ "		ON l.language_id = cl.language_id "
		+ " WHERE cl.country_id = ? ";

		try (Connection conn = DriverManager.getConnection(url, user, password)) {

			// System.out.println("\nConnessione stabilita correttamente");

				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					// colonna 1
					String countryName = rs.getString(1); 
					// colonna 2
					int countryId = rs.getInt(2);
					// colonna 3
					String regionName = rs.getString(3);
					// colonna 4
					String continentName = rs.getString(4);

					System.out.println("\n----------------------------------\n");

					// stampare nel terminale la query
					System.out.println("Nazione: " + countryName 
					+ "\nID Nazione: " + countryId 
					+ "\nRegione: " + regionName 
					+ "\nContinente: " + continentName);

				}

				System.out.println("\n----------------------------------\n");

				System.out.print("\nScegli una nazione inserendo l'id: ");
				int countryId = Integer.valueOf(sc.nextLine());

				ps = conn.prepareStatement(languagesByCountryId);
				ps.setInt(1, countryId);
				rs = ps.executeQuery();

				String langs = "";

				while (rs.next()) {

					String lang = rs.getString("language");
					langs += lang + " | ";

				}

				System.out.println("\n----------------------------------\n");

				ps = conn.prepareStatement(statsByCountryId);
				ps.setInt(1, countryId);
				rs = ps.executeQuery();
				// prima di accedere ai dati, deve spostarsi al primo record quindi sposta il cursore del risultato per la seconda query
				rs.next();

				// stampare nel terminale la query
				System.out.println("Dettagli nazione " + countryId + ":"
				+ "\nLingue: " +  langs
				+ "\nAnni: " + rs.getString("year") 
				+ "\nPopolazione: " + rs.getString("population")
				+ "\nGDP: " + rs.getString("gdp"));

		} catch (Exception e) {

			System.out.println("Errore: " + e.getMessage());
		}

		System.out.println("\n----------------------------------\n");
		System.out.println("The end\n");
	}
}
