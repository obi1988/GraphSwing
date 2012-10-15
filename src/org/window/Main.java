package org.window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.window.Window;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;

import org.window.ConnectDB;
/**
 * Główna klasa aplikacji odpowiedzialna za stworzenie okna oraz narysowanie grafu
 */
public class Main {
	static Window window = null;
	/**Zmienna countEdge zawiera ilosc wierzecholkow */
	private static int countEdge = 0;
    public static void main(String[] args) {
      
     window = new Window();
     window.test();
    }
    public static void wypisz(){
    	String str = window.text;
    	List <String> list = new ArrayList<String>();
    	StringTokenizer st = new StringTokenizer(str, "\n");
    	countEdge = Integer.parseInt(st.nextToken());
    	while(st.hasMoreTokens() ){
    		list.add(st.nextToken());
    	}
    	ConnectDB.insertVertex(countEdge, list);
    	ConnectDB.insertEdge(countEdge, list);
    }
    
    public static Graph<String, String> drawingGrapg(List<String> lista){
    	DirectedSparseGraph<String, String> g = new DirectedSparseGraph<String, String>();
    	List<String> lista_prac = ConnectDB.odczyt_prac();
    	//odczyt_pracownikow
    	HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
    	for(int i=0;i<lista_prac.size();i++){
    		String [] vertexXY = new String[2];
    		StringTokenizer st = new StringTokenizer(lista_prac.get(i)," ");
    		while (st.hasMoreElements()) {
    			vertexXY[0] = st.nextToken();
    			vertexXY[1] = st.nextToken();
			} 
    		hashMap.put(Integer.parseInt(vertexXY[0]), vertexXY[1]);
    	}
    	
    	String [] vertexXY = new String[2];
    	for(int i=0;i<countEdge;i++){
    		g.addVertex(hashMap.get(i));
    	}
    	
    	for(int i=0;i<lista.size();i++){
    		StringTokenizer st = new StringTokenizer(lista.get(i)," ");
    		while (st.hasMoreElements()) {
    			vertexXY[0] = st.nextToken();
    			vertexXY[1] = st.nextToken();
			} 
    		g.addEdge("Edge-"+i, hashMap.get(Integer.parseInt(vertexXY[0])), hashMap.get(Integer.parseInt(vertexXY[1])));  
    	}
    	return g;
    }
}
