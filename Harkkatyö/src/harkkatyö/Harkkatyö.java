package harkkatyö;

import java.util.Scanner;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Harkkatyö {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner esko = new Scanner(System.in);
		int kokonaispisteet = 0;
		String input = "";
		System.out.println(
				"Tervetuloa tietovisailuun! \nVisa pitää sisällään X osiota, jotka voit suorittaa haluamassasi jarjestyksessa.");
		do {
			System.out.println("Aloitetaanko, K/E?");
			input = esko.nextLine();
			if (input.equalsIgnoreCase("k")) {
				kokonaispisteet = +aloitusmetodi(esko);
			} else if (input.equalsIgnoreCase("e")) {
				System.out.println("Kokonaispistemaarasi on " + kokonaispisteet
						+ ". Kiitos hyvasta yrityksesta ja aurinkoista kesaa!");
			} else {
				System.out.println("Antamasi merkki ei kelpaa. Ole hyvä, ja yritä uudelleen");
			}
		} while (!input.equalsIgnoreCase("k") && !input.equalsIgnoreCase("e"));
		System.out.println(
				"Visa on nyt päättynyt. Kokonaispistemääräsi on " + kokonaispisteet + ". Oikein aurinkoista kesää!");
	}

	public static int aloitusmetodi(Scanner esko) throws FileNotFoundException {
		int pisteet = 0;
		Boolean[] metodiValinnat = { false, false, false, false, false };
		boolean kaikkiSuoritettu = false;
		while (!kaikkiSuoritettu) {
			System.out.println(
					"Valitse aihealue. Valinta tehdään numeroin.\nAihe 1: Maantieto\nAihe 2: Matematiikka\nAihe 3: Suomen kieli\nVoit lopettaa visan etuajassa kirjoittamalla valintakenttaan \"Valmis\"");
			String valintaStr = esko.nextLine();
			if (valintaStr.equalsIgnoreCase("valmis")) {
				return pisteet;
			}
			int valinta = Integer.parseInt(valintaStr);
			if (valinta >= 1 && valinta <= 5) {
				if (!metodiValinnat[valinta - 1]) {
					metodiValinnat[valinta - 1] = true;
					switch (valinta) {
					case 1:
						pisteet += maanTieto(esko);
						esko.nextLine();
						break;
					case 2:
						pisteet += mateMatiikka(esko);
						esko.nextLine();
						break;
					case 3:
						pisteet += suomenKieli(esko);
						esko.nextLine();
						break;
					}
					if (Arrays.stream(metodiValinnat).allMatch(valittu -> valittu)) {
						kaikkiSuoritettu = true;
					}
				} else {
					System.out.println("Olet jo suorittanut aihealueen. Valitse jokin toinen alue.");
				}
			} else {
				System.out.println("Valitseppa uudestaan.");
			}
		}
		return pisteet;
	}

	public static int suomenKieli() {
		return 0;
		// TODO Auto-generated method stub

	}

	public static int mateMatiikka(Scanner esko) {
		int pisteet = 0;
		System.out.println(
				"Tervetuloa testin matematiikka-osioon.\nOsio sisältää viisi yksinkertaista laskutoimitusta. Laskuissa käsitellään samoja lukuja");
		int[] numerot = { 12, 23, 5, 6, 9 };
		System.out.println("Luvut, joilla pelaillaan: " + Arrays.toString(numerot));
		System.out.println("Kuinka paljon on lukujen" + Arrays.toString(numerot) + " summa?");
		int vastaus = esko.nextInt();
		if (vastaus == Arrays.stream(numerot).sum()) {
			System.out.println("Oikein meni! Hienoa!");
			pisteet++;
		} else {
			System.out.println("Väärin meni :(");
		}
		System.out.println("Mikä on luvuista " + Arrays.toString(numerot) + " suurin?");
		int vastaus1 = esko.nextInt();
		if (vastaus1 == Arrays.stream(numerot).max().getAsInt()) {
			System.out.println("Oikein meni! Hienoa!");
			pisteet++;
		} else {
			System.out.println("Väärin meni :(");
		}
		System.out.println("Mikä on lukujen " + Arrays.toString(numerot) + " keskiarvo?");
		int vastaus2 = esko.nextInt();
		if (vastaus2 == Arrays.stream(numerot).average().orElse(Double.NaN)) {
			System.out.println("Oikein meni! Hienoa!");
			pisteet++;
		} else {
			System.out.println("Väärin meni! :(");
		}
		System.out.println("Kuinka paljon on lukujen " + numerot[1] + " ja " + numerot[4] + " tulo?");
		int vastaus3 = esko.nextInt();
		if (vastaus3 == numerot[1] * numerot[4]) {
			System.out.println("Oikein meni! Hienoa!");
			pisteet++;
		} else {
			System.out.println("Väärin meni :(");
		}
		System.out.println("Viimeinen kysymys! Montako lukua on listalla? " + Arrays.toString(numerot));
		int vastaus4 = esko.nextInt();
		esko.nextLine();
		if (vastaus4 == numerot.length) {
			System.out.println(
					"Oikein meni! Matematiikkaosio on nyt ohi. Sait " + pisteet + "/5 oikein. Palataan alkuun!");
			pisteet++;
		} else {
			System.out.println(
					"Väärin meni :( Matematiikkaosa on nyt ohi. Sait " + pisteet + "/5 oikein. Palataan alkuun");
		}
		return pisteet;

	}

	public static int maanTieto(Scanner esko) throws FileNotFoundException {
		int määrä = 5;
		String tiedostonimi = "maat.txt";

		String maat[] = new String[määrä];
		String kaupungit[] = new String[määrä];

		BufferedReader lukija = null;
		try {
			lukija = new BufferedReader(new FileReader(tiedostonimi));
			String rivi = "";
			int i = 0;
			while ((rivi = lukija.readLine()) != null && i < määrä) {
				String[] osat = rivi.split("-");
				if (osat.length == 2) {
					maat[i] = osat[0];
					kaupungit[i] = osat[1];
					i++;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Virhe tiedostoa luettaessa: " + e.getMessage());
			throw e;
		} catch (IOException e) {
			System.out.println("Virhe tiedoston lukemisessa: " + e.getMessage());
		} finally {
			if (lukija != null) {
				try {
					lukija.close();
				} catch (IOException e) {
					System.out.println("Virhe lukijaa suljettaessa: " + e.getMessage());
				}
			}
		}

		int oikein = 0;
		for (int i = 0; i < 5; i++) {
			System.out.println("Mikä on maan " + maat[i] + " pääkaupunki?");
			String vastaus = esko.nextLine();
			if (vastaus.equalsIgnoreCase(kaupungit[i])) {
				System.out.println("Oikein meni!");
				oikein++;
			} else {
				System.out.println("Väärä vastaus! Oikea vastaus on " + kaupungit[i]);
			}
		}
		System.out.println("Visan maantieto-osa on ohi! Sait " + oikein + "/5 oikein!");
		return oikein;
	}

}
