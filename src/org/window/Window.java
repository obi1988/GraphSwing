package org.window;

import java.io.*;
import java.nio.charset.*;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;

import org.window.Main;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

import org.window.ConnectDB;
public class Window implements ActionListener {
   JFrame myFrame = null;
   JPanel myPane = null;
   JMenuItem cmdOpen = null;
   JMenuItem cmdExit = null;
   String dirName = "\\workspace_asseco\\";
   String fileName = "plik.txt";
   public String text = "";
   public static void main(String[] a) {
      (new Window()).test();
   }
   public void test() {
      myFrame = new JFrame("JEditorPane JFileChooser Test");
      myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      myFrame.setSize(800,500);
      myPane = new JPanel();
      myFrame.setContentPane(myPane);
      JMenuBar myBar = new JMenuBar();
      JMenu myMenu = getFileMenu();
      myBar.add(myMenu); 
      myFrame.setJMenuBar(myBar);
      myFrame.setVisible(true);
   }
   private JMenu getFileMenu() {
      JMenu myMenu = new JMenu("File");
      cmdOpen = new JMenuItem("Open...");
      cmdOpen.addActionListener(this);
      myMenu.add(cmdOpen);
      
      cmdExit = new JMenuItem("Exit");
      cmdExit.addActionListener(this);
      myMenu.add(cmdExit);
      
      return myMenu;
   }
   public void actionPerformed(ActionEvent e) {
      JFileChooser chooser = new JFileChooser();
      chooser.setCurrentDirectory(new File(dirName));
      chooser.setSelectedFile(new File(fileName));
      chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

      FileNameExtensionFilter filter = new FileNameExtensionFilter(
        ".txt ", "txt", "java");
      chooser.setFileFilter(filter);
      Object cmd = e.getSource();
      try {
    	  if (cmd == cmdOpen) {
            int code = chooser.showOpenDialog(myPane);
            if (code == JFileChooser.APPROVE_OPTION) {
               File selectedFile = chooser.getSelectedFile();
               fileName = selectedFile.getName();
               FileInputStream fis = new FileInputStream(selectedFile);
               InputStreamReader in = new InputStreamReader(fis, Charset.forName("UTF-8")); 
               char[] buffer = new char[1024];
               int n = in.read(buffer);
               text = new String(buffer, 0, n);
               Main.wypisz();
               in.close();
               Layout<Integer, String> layout = new CircleLayout(Main.drawingGrapg(ConnectDB.odczyt()));
               layout.setSize(new Dimension(800,500));
               VisualizationViewer<Integer,String> vv = new VisualizationViewer<Integer,String>(layout);
               vv.setPreferredSize(new Dimension(800,500));
               vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
               vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
               DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
               gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
               vv.setGraphMouse(gm); 
               myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               myFrame.getContentPane().add(vv);
               myFrame.pack();
               myFrame.setVisible(true); 
            }
    	  }
    	  else
    		 myFrame.dispose();
      } catch (Exception f) {
      	 f.printStackTrace();
      }
   }
}