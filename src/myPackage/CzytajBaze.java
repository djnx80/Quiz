package myPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CzytajBaze {
	// dane do po³¹czenia
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/quiz";
	private static final String USER = "root";
	private static final String PASS = "test123test";
	private static Connection polaczenieZBaza = null;
	private static Statement bazaDane = null;

	public CzytajBaze() {

	}

	public void otworzBaze() {
		try {
			Class.forName(JDBC_DRIVER); // rejestruj sterownik i po³¹cz siê z baz¹
			polaczenieZBaza = DriverManager.getConnection(DB_URL, USER, PASS);
			bazaDane = polaczenieZBaza.createStatement();
		} catch (SQLException se) {
			System.out.println("B³êdy od JDBC");
			se.printStackTrace();
		} catch (Exception e) {
			System.out.println("B³êdy od Class.forName");
			e.printStackTrace();
		}
	}

	public void zamknijBaze() {
		try {
			bazaDane.close(); // zamknij bazê
		} catch (Exception e) {
			System.out.println("B³¹d przy zamykaniu bazy");
		}
		try {
			polaczenieZBaza.close(); // zamknij po³¹czenie
		} catch (Exception e) {
			System.out.println("B³¹d przy zamykaiu po³¹czenia");
		}
	}

	public ResultSet getPoString(String coPytasz) {
		try {
			String zapytaj = "SELECT " + coPytasz + " FROM quiz.pytania";
			ResultSet wynikZapytania = bazaDane.executeQuery(zapytaj);
			return wynikZapytania;
		} catch (Exception e) { // nie uda³o siê pobraæ danych
			return null;
		}
	}

	public void pokazWyniki(ResultSet wyniki, String coPytasz)	{		// wyniki to musza byc stringi
		try	{
		while (wyniki.next()) {
			// odczytaj wartoœci z kolumn
			String wynik = wyniki.getString(coPytasz);
			System.out.println(wynik);
		}
		} catch (Exception e)
		{
			// b³¹d przy odczycie 
		}
	}

	public ResultSet getPoID(int id) {
		try {
			String zapytaj = "SELECT * FROM quiz.pytania WHERE id="+id;
			ResultSet wynikZapytania = bazaDane.executeQuery(zapytaj);
			return wynikZapytania;
		} catch (Exception e) { // nie uda³o siê pobraæ danych
			return null;
		}
	}

	public void pokazPoID(ResultSet wyniki)	{		// wyniki to musza byc stringi
		try	{
		while (wyniki.next()) {
			// odczytaj wartoœci z kolumn
			int id = wyniki.getInt("id");
			String pytanie = wyniki.getString("pytanie");
			String odpa = wyniki.getString("odpa");
			String odpb = wyniki.getString("odpb");
			String odpc = wyniki.getString("odpc");
			String odpowiedz = wyniki.getString("odpowiedz");

			System.out.print("ID: " + id);
			System.out.print("Pytanie: " + pytanie);
			System.out.print(", " + odpa);
			System.out.print(", " + odpb);
			System.out.print(", " + odpc);
			System.out.println(", " + odpowiedz);
		}
		} catch (Exception e)
		{
			// b³¹d przy odczycie 
		}
	}

	
	
	public void odczytajPoID(ResultSet wyniki, String[] zestaw, int []odp)	{		// wyniki to musza byc stringi
		try	{
		while (wyniki.next()) {
			// odczytaj wartoœci z kolumn
			zestaw[0] = wyniki.getString("pytanie");
			zestaw[1] = wyniki.getString("odpa");
			zestaw[2] = wyniki.getString("odpb");
			zestaw[3] = wyniki.getString("odpc");
			zestaw[4] = wyniki.getString("odpd");
			odp[0] = wyniki.getInt("odpowiedz");
		}
		} catch (Exception e)
		{
			// b³¹d przy odczycie 
		}
	}


	public void wyswietlZapytanie(String zapytanie) {
		try {
			System.out.println("Wysy³am zapytanie");
			ResultSet wynikZapytania = bazaDane.executeQuery(zapytanie);

			// przeleæ przez ca³¹ listê (z zapytania)
			while (wynikZapytania.next()) {
				// odczytaj wartoœci z kolumn
				int id = wynikZapytania.getInt("id");
				String pytanie = wynikZapytania.getString("pytanie");
				System.out.print("ID: " + id);
				System.out.print("Pytanie: " + pytanie);
				}

			wynikZapytania.close();

		} catch (Exception e) {
			System.out.println("B³êdy od wyniku zapytania");
			e.printStackTrace();
		}

	}

}