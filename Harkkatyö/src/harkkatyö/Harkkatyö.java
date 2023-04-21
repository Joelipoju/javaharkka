package harkkatyö;

import java.util.Scanner;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Harkkatyö-luokka sisältää visailuohjelman, joka käynnistää tietovisailun.
 * Luokka käyttää Scanner- Random- BufferedReader- FileWriter-luokkia. Luokka
 * pitää sisällään myös input-poikkeuksien sekä tiedoston luku- ja kirjoitus-
 * poikkeuksiin tarvittavat try-catch työkalut. Luokka pitää sisällään
 * main-metodin, sekä viisi alempaa metodia, joista neljä liittyy visailuun, ja
 * yksi pyytää käyttäjältä palautetta visan jälkeen. Visassa on kolme osaa,
 * jotka pelaaja voi suorittaa haluamassaan järjestyksessä tai lopettaa
 * haluamassaan kohdassa. Visailuosan loputtua ohjelma tulostaa pelaajalle tämän
 * keräämät kokonaispisteet, sekä kysyy haluaako pelaaja jättää palautetta.
 * 
 * @author joeli
 * @date 20/4/2023
 */
public class Harkkatyö {

	/**
	 * Tämä on ohjelman päämetodi, joka hallinnoi sen kulkua. Metodi kysyy
	 * käyttäjältä, haluaako tämä aloittaa pelin, jonka jälkeen se kutsuu
	 * aloitusmetodin. Mikäli peli on suoritettu metodi ilmoittaa käyttäjän keräämät
	 * kokonaispisteet, ja kysyy vielä halukkuutta palautteen antamiseen.
	 * 
	 * @param args            ohjelmalle annettavat komentoriviparametrit, ei
	 *                        käytössä
	 * @param esko            Scanner-objekti, jota käytetään syötteen lukemiseen
	 * @param kokonaispisteet pistelaskuri, johon käyttäjän visassa keräämät pisteet
	 *                        kerätään
	 * @throws FileNotFoundException jos ohjelman tarvitsemaa tiedostoa ei löydy.
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner esko = new Scanner(System.in);
		int kokonaispisteet = 0;
		String input = "";
		System.out.println(
				"Tervetuloa tietovisailuun! \nVisa pitää sisällään 3 osaa, jotka voit suorittaa haluamassasi jarjestyksessa.\nVisa pelataan IDE:n konsolissa näppäimistöä käyttämällä.");
		do {
			System.out.println("Aloitetaanko, K/E?");
			input = esko.nextLine().trim();
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
				"Visa on nyt päättynyt. Kokonaispistemääräsi on " + kokonaispisteet + "/15. Oikein aurinkoista kesää!");
		System.out.println("Haluatko antaa palautetta? K/E");
		String input2 = esko.nextLine().trim();
		if (input2.equalsIgnoreCase("k")) {
			palauteBoksi(esko);
		} else {
			System.out.println("Kiitos ajastasi!");
		}

	}

	/**
	 * Tämä metodi mahdollistaa käyttäjän palautteen antamisen ja tallentaa annetun
	 * palautteen palaute.txt-tiedostoon
	 * 
	 * @param esko Scanner-objekti, jota käytetään syötteen lukemiseen
	 */
	public static void palauteBoksi(Scanner esko) {
		try {
			FileWriter kirjoittaja = new FileWriter("palaute.txt");
			System.out.println("Anna palautetta!");
			String palaute = esko.nextLine();
			kirjoittaja.write(palaute);
			kirjoittaja.close();
			System.out.println("Palaute on rekisteröity onnistuneesti.");
		} catch (IOException e) {
			System.out.println("Virhe tiedostoa luodessa: " + e.getMessage());
		}
	}

	/**
	 * Tämä metodi mahdollistaa valitsemisen visailun kolmen eri aihealueen välillä.
	 * Metodi pitää kirjaa siitä, mitkä aihealueet on tehty boolean arrayn avulla.
	 * Arrayn elementit ovat samassa järjestyksessä kuin metodit, joilla aihealueita
	 * kutsutaan. Mikäli aihealue on jo valittu, metodi pyytää käyttäjää valitsemaan
	 * uudestaan. Jos kaikki aihealueet on suoritettu tai käyttäjä kirjoittaa
	 * "valmis", metodi palaa main-metodiin
	 * 
	 * @param esko Scanner-objekti, jolla luetaan käyttäjän syötettä
	 * @return palauttaa käyttäjän keräämät kokonaispisteet
	 * @throws FileNotFoundException mikäli ohjelman tarvitsemaa tiedostoa ei löydy.
	 * @throws NumberFormatException mikäli käyttäjä syöttää jotain muuta, kuin numeron.
	 */
	public static int aloitusmetodi(Scanner esko) throws FileNotFoundException, NumberFormatException {
		int pisteet = 0;
		Boolean[] metodiValinnat = { false, false, false, };
		boolean kaikkiSuoritettu = false;
		while (!kaikkiSuoritettu) {
			System.out.println(
					"Valitse aihealue. Valinta tehdään numeroin.\nAihe 1: Maantieto\nAihe 2: Matematiikka\nAihe 3: Suomen kieli\nVoit lopettaa visan etuajassa kirjoittamalla valintakenttaan \"Valmis\"");
			String valintaStr = esko.nextLine();
			if (valintaStr.equalsIgnoreCase("valmis")) {
				return pisteet;
			}
			try {
				int valinta = Integer.parseInt(valintaStr);
				if (valinta >= 1 && valinta <= 3) {
					if (!metodiValinnat[valinta - 1]) {
						metodiValinnat[valinta - 1] = true;
						switch (valinta) {
						case 1:
							pisteet += maanTieto(esko);
							break;
						case 2:
							pisteet += mateMatiikka(esko);
							break;
						case 3:
							pisteet += suomenKieli(esko);
							break;
						}
						if (Arrays.stream(metodiValinnat).allMatch(valittu -> valittu)) {
							kaikkiSuoritettu = true;
						}
					} else {
						System.out.println("Olet jo suorittanut aihealueen. Valitse jokin toinen aihe.");
					}
				} else {
					System.out.println("Valitseppa uudestaan.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Valitseppa uudestaan ja anna numeroarvo väliltä 1-3.");
			}
		}
		return pisteet;
	}

	/**
	 * Tämä metodi on kyselee käyttäjältä kieleen liittyviä kysymyksiä, ja jakaa
	 * pisteitä oikein menneiden vastausten mukaan. Jokainen oikea vastaus on yhden
	 * pisteen arvoinen, ja lopulta metodi palauttaa kokonaispistemäärän.
	 * 
	 * @param esko Scanner-objekti, jolla käyttäjän syötettä luetaan
	 * @return Käyttäjän osiosta keräämät pisteet
	 */
	public static int suomenKieli(Scanner esko) {
		System.out.println("Tervetuloa visan äidinkieli-osioon. Sinulta kysytään viisi kysymystä lorusta,\njoka tulostetaan näytölle. Vastaathan vain kirjaimin.");
		int pisteet = 0;
		String rivi = "";
		File filu = new File("loru.txt");
		try {
			BufferedReader lukija = new BufferedReader(new FileReader(filu));
			while ((rivi = lukija.readLine()) != null) {
				System.out.println(rivi);
			}
		} catch (IOException e) {
			System.out.println("Virhe tiedostoa luettaessa" + e.getMessage());
		}
		System.out.println("Miten Pablo saapui paikalle?");
		String vastaus1 = esko.nextLine().trim();
		if (vastaus1.equalsIgnoreCase("hyppien")) {
			System.out.println("Oikein meni! Hienoa!");
			pisteet++;
		} else {
			System.out.println("Väärin meni :(");
		}
		System.out.println(
				"Mihin aikaan loru tapahtuu? Syötä sana perusmuodossa\n(Huom. Oikea vastaus on vain yksi sana!)");
		String vastaus2 = esko.nextLine().trim();
		if (vastaus2.equalsIgnoreCase("aamurusko")) {
			System.out.println("Oikein meni! Hienoa");
			pisteet++;
		} else {
			System.out.println("Väärin meni :(");
		}
		System.out.println("Kuka söi ankanpoikasen? (Koko nimi)");
		String vastaus3 = esko.nextLine().trim();
		if (vastaus3.equalsIgnoreCase("Pablo Pallopää")) {
			System.out.println("Oikein meni! Hienoa!");
			pisteet++;
		} else {
			System.out.println("Väärin meni :(");
		}
		System.out.println("Oliko Pablolla nälkä?");
		String vastaus4 = esko.nextLine().trim();
		if (vastaus4.equalsIgnoreCase("Oli")) {
			System.out.println("Oikein meni! Hienoa!");
			pisteet++;
		} else {
			System.out.println("Väärin meni :(");
		}
		System.out.println("Mikä ankalla jäi juomatta?");
		String vastaus5 = esko.nextLine().trim();
		if (vastaus5.equalsIgnoreCase("Aamukahvi")) {
			System.out.println("Oikein meni! Hienoa!");
			pisteet++;
		} else {
			System.out.println("Väärin meni :(");
		}
		System.out
				.println("Visan aidinkieliosa on nyt ohi! Sait osiosta " + pisteet + "/5 pistettä. Poistutaan osiosta");
		return pisteet;
	}

	/**
	 * Tämä metodi kysyy käyttäjältä viisi laskutoimitusta. Käyttäjän tulee syöttää
	 * vastaukset numeroina Jokainen kysymys on kääritty try-catchiin, jotta
	 * MissmatcInput-errorilta vältytään ja ohjelman toiminta ei keskeydy. Lopussa
	 * metodi palauttaa käyttäjän osiosta keräämät pisteet
	 * 
	 * @param esko Scanner-objekti, jota käytetään syötteen lukemiseen
	 * @return Käyttäjän osiosta keräämät pisteet
	 */
	public static int mateMatiikka(Scanner esko) {
		int pisteet = 0;
		System.out.println(
				"Tervetuloa testin matematiikka-osioon.\nOsio sisältää viisi yksinkertaista laskutoimitusta. Laskuissa käsitellään samoja lukuja.\nHUOM: desimaalin erotukseen käytetään pilkkua, ei pistettä");
		int[] numerot = new int[5];
		Random random = new Random();
		for (int i = 0; i < numerot.length; i++) {
			numerot[i] = random.nextInt(25) + 1;
		}
		System.out.println("Luvut, joilla pelaillaan: " + Arrays.toString(numerot));
		try {
			System.out.println("Kuinka paljon on lukujen" + Arrays.toString(numerot) + " summa?");
			double vastaus = esko.nextDouble();
			if (vastaus == Arrays.stream(numerot).sum()) {
				System.out.println("Oikein meni! Hienoa!");
				pisteet++;
			} else {
				System.out.println("Väärin meni :(");
			}
		} catch (InputMismatchException e) {
			System.out.println("Syötä vain numeroita!");
			esko.nextLine();
		}
		try {
			System.out.println("Mikä on luvuista " + Arrays.toString(numerot) + " suurin?");
			double vastaus1 = esko.nextDouble();
			if (vastaus1 == Arrays.stream(numerot).max().getAsInt()) {
				System.out.println("Oikein meni! Hienoa!");
				pisteet++;
			} else {
				System.out.println("Väärin meni :(");
			}
		} catch (InputMismatchException e) {
			System.out.println("Syötä vain numeroita!");
			esko.nextLine();
		}
		try {
			System.out.println("Mikä on lukujen " + Arrays.toString(numerot) + " keskiarvo?");
			double vastaus2 = esko.nextDouble();
			if (vastaus2 == Arrays.stream(numerot).average().orElse(Double.NaN)) {
				System.out.println("Oikein meni! Hienoa!");
				pisteet++;
			} else {
				System.out.println("Väärin meni! :(");
			}
		} catch (InputMismatchException e) {
			System.out.println("Syötä vain numeroita!");
			esko.nextLine();
		}
		try {
			System.out.println("Kuinka paljon on lukujen " + numerot[1] + " ja " + numerot[4] + " tulo?");
			double vastaus3 = esko.nextDouble();
			if (vastaus3 == numerot[1] * numerot[4]) {
				System.out.println("Oikein meni! Hienoa!");
				pisteet++;
			} else {
				System.out.println("Väärin meni :(");
			}
		} catch (InputMismatchException e) {
			System.out.println("Syötä vain numeroita!");
			esko.nextLine();
		}
		try {
			System.out.println("Viimeinen kysymys! Montako lukua on listalla? " + Arrays.toString(numerot));
			double vastaus4 = esko.nextDouble();
			esko.nextLine();
			if (vastaus4 == numerot.length) {
				System.out.println("Oikein meni! ");
				pisteet++;
			} else {
				System.out.println(
						"Väärin meni :( Matematiikkaosa on nyt ohi. Sait " + pisteet + "/5 oikein. Palataan alkuun");
			}
		} catch (InputMismatchException e) {
			System.out.println("Syötä vain numeroita!");
		}
		System.out.println("Matematiikkaosio on nyt ohi. Sait " + pisteet + "/5 oikein. Palataan alkuun!");

		return pisteet;

	}

	/**
	 * Tämä metodi lukee tiedostosta "maat.txt" listan maista ja niiden
	 * pääkaupungeista. Sen jälkeen ohjelma kyselee käyttäjältä maita ja niiden
	 * pääkaupunkeja.
	 * 
	 * @param esko Scanner-objekti, jota käytetään syötteen lukemiseen
	 * @return pisteet, jotka käyttäjä kerää osiosta.
	 * @throws FileNotFoundException mikäli ohjelman vaatimaa tiedostoa ei löydetä.
	 */
	public static int maanTieto(Scanner esko) throws FileNotFoundException {
		System.out.println("Tervetuloa visan maantieto-osaan.\nTässä osassa sinulta kysytään maita, ja niiden pääkaupunkeja.");
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
