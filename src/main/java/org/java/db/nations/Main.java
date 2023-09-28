package org.java.db.nations;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {

	public static void main(String[] args) {
																 // sostituire 3306/nome_db con la porta da collegare e con il nome del database
		final String url = "jdbc:mysql://localhost:3306/dump_nations";
		// sostituire 'root' con il nome inserito in mamp o PHP Launcher
		final String user = "root";
		// sostituire '' con la password inserita in mamp o PHP Launcher
		final String password = "";
		
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			
			System.out.println("Connessione stabilita correttamente");
		} catch (Exception e) {
			
			System.out.println("Errore di connessione: " + e.getMessage());
		}
		
		System.out.println("\n----------------------------------\n");
		System.out.println("The end");
	}
}
