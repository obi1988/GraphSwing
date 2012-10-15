package org.window;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectDB {
    
	static String url = "jdbc:postgresql://localhost/test";
	static String user = "postgres";
    static String password = "postgres";
	
	public static List <String> insertVertex(int count, List<String> prac){
		Connection con = null;
        Statement st = null;
        List <String> lista = new ArrayList<String>();
        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            con.setAutoCommit(false);
            
            for(int i = 0 ;i <count ; i++){
            st.addBatch("INSERT INTO pracownicy(id,nazwa) VALUES ("+i+",'"+prac.get(i)+"')");   
            lista.add(i+" "+prac.get(i));
            }
            int counts[] = st.executeBatch();
            con.commit();
            System.out.println("Committed " + counts.length + " updates");

          } catch (SQLException ex) {

              System.out.println(ex.getNextException());
              
              if (con != null) {
                  try {
                      con.rollback();
                  } catch (SQLException ex1) {
                  }
              }

          } finally {

              try {
   
                  if (st != null) {
                      st.close();
                  }
                  if (con != null) {
                      con.close();
                  }

              } catch (SQLException ex) {
            	  Logger lgr = Logger.getLogger(ConnectDB.class.getName());
                  lgr.log(Level.WARNING, ex.getMessage(), ex);
              }
          }
        return lista;
	}
	
	public static void insertEdge(int count, List<String> prac){
		Connection con = null;
        Statement st = null;

        try {

            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            con.setAutoCommit(false);
            int id=0;
            String [] vertexXY = new String[2];
            for(int i = count ; i < prac.size(); i++){
            	StringTokenizer strTok = new StringTokenizer(prac.get(i)," ,\t");
        		while (strTok.hasMoreElements()) {
        			vertexXY[0] = strTok.nextToken();
        			vertexXY[1] = strTok.nextToken();
    			}
        		st.addBatch("INSERT INTO krawedzie(id,id_pracownicy_in, id_pracownicy_out) VALUES ("+id+",'"+vertexXY[0]+"','"+vertexXY[1]+"')");
        		id++;
            }
            int counts[] = st.executeBatch();
            con.commit();

            System.out.println("Committed " + counts.length + " updates");

          } catch (SQLException ex) {

              System.out.println(ex.getNextException());
              
              if (con != null) {
                  try {
                      con.rollback();
                  } catch (SQLException ex1) {
                	  Logger lgr = Logger.getLogger(ConnectDB.class.getName());
                      lgr.log(Level.WARNING, ex.getMessage(), ex1);
                  }
              }


          } finally {
              try {
                  if (st != null) {
                      st.close();
                  }
                  if (con != null) {
                      con.close();
                  }

              } catch (SQLException ex) {
              }
          }
	}
	
    public static List<String> odczyt() {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<String> array = new ArrayList<String>();
        try {
            
            con = DriverManager.getConnection(url, user, password);
            pst = con.prepareStatement("SELECT id_pracownicy_in, id_pracownicy_out FROM krawedzie");
            rs = pst.executeQuery();

            while (rs.next()) {
                array.add(rs.getString(1)+" "+rs.getString(2));
            }

        } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(ConnectDB.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(ConnectDB.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return array;
    }
    
    public static List<String> odczyt_prac() {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<String> array = new ArrayList<String>();
        try {
            
            con = DriverManager.getConnection(url, user, password);
            pst = con.prepareStatement("select id,nazwa from pracownicy ");
            rs = pst.executeQuery();

            while (rs.next()) {
                array.add(rs.getString(1)+" "+rs.getString(2));
            }

        } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(ConnectDB.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(ConnectDB.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return array;
    }
}


