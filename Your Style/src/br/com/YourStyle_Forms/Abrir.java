package br.com.YourStyle_Forms;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class Abrir {
	
	public void Mostrar(String endArquivo) throws IOException{		  
		Desktop.getDesktop().open(new File(endArquivo));
	}
	
}
