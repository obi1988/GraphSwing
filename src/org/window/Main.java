package org.window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.window.Window;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;

import org.window.ConnectDB;

import baza.Krawedzie;
import baza.Pracownicy;

/**
 * Główna klasa aplikacji odpowiedzialna za stworzenie okna oraz narysowanie
 * grafu
 */
public class Main {
	static Window window = null;
	/** Zmienna countEdge zawiera ilosc wierzecholkow */
	private static int countEdge = 0;
	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	static SessionFactory s = new Configuration().configure().buildSessionFactory();

	public static void main(String[] args) {
		window = new Window();
		window.test();
	}

	public static void wypisz() {
		String str = window.text;
		List<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(str, "\n");
		countEdge = Integer.parseInt(st.nextToken());
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}

		Session session = s.openSession();
		try {
		for (int i = 0; i < countEdge; i++) {
				Pracownicy pracownicy = new Pracownicy();
				pracownicy.setId(i);
				pracownicy.setNazwa(list.get(i));
				session.save(pracownicy);
		}
		} catch (Exception e) {
			System.out.println("Exception insert to Pracownicy: "
					+ e.getMessage());

		}
		String[] vertexXY = new String[2];
		try {
		for (int i = countEdge; i < list.size(); i++) {
				Krawedzie krawedzie = new Krawedzie();
				StringTokenizer strTok = new StringTokenizer(list.get(i)," ,\t");
				while (strTok.hasMoreElements()) {
					vertexXY[0] = strTok.nextToken();
					vertexXY[1] = strTok.nextToken();
				}
				krawedzie.setId(i);
				krawedzie.setId_pracownicy_in(Integer.parseInt(vertexXY[0]));
				krawedzie.setId_pracownicy_out(Integer.parseInt(vertexXY[1]));
				session.save(krawedzie);
		}
		} catch (Exception e) {
			System.out.println("Exception insert to Krawedzie: "
					+ e.getMessage());

		}
		try {
			session.beginTransaction().commit();
			session.flush();
			session.close();
		} catch (Exception e) {
			System.out.println("Exception inserts: " + e.getMessage());

		}

	}

	public static Graph<String, String> drawingGraph() {
		int i = 0;
		DirectedSparseGraph<String, String> g = new DirectedSparseGraph<String, String>();
		Session session = s.openSession();
		List<Pracownicy> pracownicy = session.createCriteria(Pracownicy.class).list();
		List<Krawedzie> krawedzie = session.createCriteria(Krawedzie.class).list();
		HashMap<Integer, String> hashMap = new HashMap<Integer, String>();

		for (Pracownicy p : pracownicy) {
			hashMap.put(p.getId(), p.getNazwa());
		}

		for (int k = 0; k < pracownicy.size(); k++) {
			g.addVertex(hashMap.get(k));
		}

		for (Krawedzie k : krawedzie) {
			g.addEdge("Edge-" + i, hashMap.get(k.getId_pracownicy_in()),hashMap.get(k.getId_pracownicy_out()));
			i++;
		}
		return g;
	}
}
