package org.openup.apps.form;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class TestHtmlForm {

	public TestHtmlForm() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 04/03/2013
	 * @see
	 * @param args
	 */
	public static void main(String[] args) {
		
		JEditorPane jep = new JEditorPane();
		jep.setEditable(false);   

		try {
		  jep.setPage("http://200.40.80.6:8074/redmine/issues/445");
		}catch (IOException e) {
		  jep.setContentType("text/html");
		  jep.setText("<html>Could not load</html>");
		} 

		JScrollPane scrollPane = new JScrollPane(jep);     
		JFrame f = new JFrame("Test HTML");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(scrollPane);
		f.setPreferredSize(new Dimension(800,600));
		f.setVisible(true);
	}

}
