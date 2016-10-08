package br.com.YourStyle_Forms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;

import br.com.YourStyle_BD.ConexaoBD;

@SuppressWarnings("serial")
public class Alterar_Clientes extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton btnFechar;
	private JTable tableClientes;
	private JScrollPane scrollPane;
	private String linhas[][],				   
				   colunas[] = {"Código", "Nome","RG","CPF","Endereço","Nº","Bairro","Cidade",
								"Estado","CEP","Telefone","Celular","Dívida (R$)","Data do cadastro"},
				   auxPesquisa = "";	

	private ConexaoBD connBD = new ConexaoBD(); 
	private JTextField txtNome;
	private JButton btnPesquisar, btnAlterar;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {					
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {			
			Alterar_Clientes dialog = new Alterar_Clientes();			
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);			
		} catch (Exception e) {
			e.printStackTrace();
		}				
	}

	/**
	 * Create the dialog.
	 */
	public Alterar_Clientes() {
		setType(Type.UTILITY);
		setMinimumSize(new Dimension(892, 453));
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setTitle("Alterar Clientes");
		setBounds(100, 100, 892, 453);		
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon(Alterar_Clientes.class.getResource("/br/com/YourStyle_Imagens/Flaticon_7706.png")));
		scrollPane = new JScrollPane();
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				auxPesquisa = txtNome.getText().trim();
				PesquisarNome(txtNome.getText().trim());
				if( linhas.length == 0 )
					btnAlterar.setEnabled(false);
				else
					btnAlterar.setEnabled(true);
			}
		});
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(373)
					.addComponent(label, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
					.addGap(362))
				.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPesquisar)
					.addContainerGap())
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 854, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnPesquisar))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		txtNome = new JTextField();		
		txtNome.setToolTipText("Pesquisa por nome");
		tabbedPane.addTab("Nome", null, txtNome, null);
		tabbedPane.setEnabledAt(0, true);
		txtNome.setColumns(10);
		{				
			linhas = connBD.ConsultaTabelaCliente("","","");	
			
			CarregarTabela();
		}
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnAlterar = new JButton("Alterar");
				btnAlterar.setMnemonic(KeyEvent.VK_A);
				btnAlterar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {						
						if( CamposCorretos() )
							connBD.AlterarTabelaCliente(linhas, getTableClientes());						
					}
				});				
				buttonPane.add(btnAlterar);
				getRootPane().setDefaultButton(btnAlterar);
			}
			{
				btnFechar = new JButton("Fechar");
				btnFechar.setMnemonic(KeyEvent.VK_F);
				btnFechar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});				
				buttonPane.add(btnFechar);
			}
		}
	}
	
	protected void CarregarTabela(){
		tableClientes = new JTable(linhas,colunas){
		    public boolean isCellEditable(int rowIndex, int vColIndex) {
		    	if(vColIndex == 0 || vColIndex == 12 || vColIndex == 13)
		    		return false;
		    	else
		    		return true;
		    }
		};
		tableClientes.getTableHeader().setReorderingAllowed(false);
		tableClientes.putClientProperty("terminateEditOnFocusLost", true);
		tableClientes.setToolTipText("Clique duas vezes no campo que deseja alterar.");
		tableClientes.setShowVerticalLines(true);
		tableClientes.setShowHorizontalLines(true);
		tableClientes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		TableColumn col = tableClientes.getColumnModel().getColumn(0);
		TableColumn col1 = tableClientes.getColumnModel().getColumn(1);
		TableColumn col2 = tableClientes.getColumnModel().getColumn(2);
		TableColumn col3 = tableClientes.getColumnModel().getColumn(3);
		TableColumn col4 = tableClientes.getColumnModel().getColumn(4);
		TableColumn col5 = tableClientes.getColumnModel().getColumn(5);
		TableColumn col6 = tableClientes.getColumnModel().getColumn(6);
		TableColumn col7 = tableClientes.getColumnModel().getColumn(7);
		TableColumn col8 = tableClientes.getColumnModel().getColumn(8);
		TableColumn col9 = tableClientes.getColumnModel().getColumn(9);
		TableColumn col10 = tableClientes.getColumnModel().getColumn(10);
		TableColumn col11 = tableClientes.getColumnModel().getColumn(11);
		TableColumn col12 = tableClientes.getColumnModel().getColumn(12);
		TableColumn col13 = tableClientes.getColumnModel().getColumn(13);
		
		col.setPreferredWidth(50);
		col.setMinWidth(50);	
		col1.setPreferredWidth(100);
		col1.setMinWidth(50);
		col2.setPreferredWidth(85);
		col2.setResizable(false);
		col3.setPreferredWidth(100);
		col3.setResizable(false);
		col4.setPreferredWidth(120);
		col4.setMinWidth(70);
		col5.setPreferredWidth(45);	
		col5.setMinWidth(30);
		col6.setPreferredWidth(70);
		col6.setMinWidth(50);
		col7.setPreferredWidth(90);	
		col7.setMinWidth(60);
		col8.setPreferredWidth(50);
		col8.setResizable(false);			
		col9.setPreferredWidth(70);
		col9.setResizable(false);
		col10.setPreferredWidth(95);
		col10.setResizable(false);
		col11.setPreferredWidth(110);
		col11.setResizable(false);		
		col12.setMinWidth(73);
		col13.setPreferredWidth(125);
		col13.setResizable(false);	

		scrollPane.setViewportView(tableClientes);
	}
	
	public JTable getTableClientes(){
		return tableClientes;
	}
	
	public boolean CamposCorretos(){
		
		boolean camposCorretos = true;
		
		String camposIncorretos = "";		
		
		for( int contador = 0; contador < linhas.length; contador++ ){
			if( tableClientes.getValueAt(contador, 1).toString().length() < 3 ){
				camposIncorretos = "Preencha o nome corretamente no código " + 
						tableClientes.getValueAt(contador, 0) + "\n";
				camposCorretos = false;
			}			
			if( tableClientes.getValueAt(contador, 2).toString().length() < 12 ){
				camposIncorretos = "Preencha o RG corretamente no código " + 
						tableClientes.getValueAt(contador, 0) + "\n";	
				camposCorretos = false;
			}	
			if( !ValidarCPF(tableClientes.getValueAt(contador, 3).toString()) ){
				camposIncorretos = "Preencha o CPF corretamente no código " + 
						tableClientes.getValueAt(contador, 0) + "\n";
				camposCorretos = false;
			}	
			if( tableClientes.getValueAt(contador, 4).toString().length() < 3 ){
				camposIncorretos = "Preencha o endereço corretamente no código " + 
						tableClientes.getValueAt(contador, 0) + "\n";
				camposCorretos = false;
			}
			if( tableClientes.getValueAt(contador, 5).toString().length() < 1 ){
				camposIncorretos = "Preencha o Nº do endereço corretamente no código " + 
						tableClientes.getValueAt(contador, 0) + "\n";
				camposCorretos = false;
			}			
			if( tableClientes.getValueAt(contador, 6).toString().length() < 3 ){
				camposIncorretos = "Preencha o bairro corretamente no código " + 
						tableClientes.getValueAt(contador, 0) + "\n";
				camposCorretos = false;
			}
			if( tableClientes.getValueAt(contador, 7).toString().length() < 3 ){
				camposIncorretos = "Preencha a cidade corretamente no código " + 
						tableClientes.getValueAt(contador, 0) + "\n";
				camposCorretos = false;
			}
			if( tableClientes.getValueAt(contador, 8).toString().length() < 2 ){
				camposIncorretos = "Preencha o estado corretamente no código " + 
						tableClientes.getValueAt(contador, 0) + "\n";
				camposCorretos = false;		
			}
			if( tableClientes.getValueAt(contador, 9).toString().length() < 9 ){
				camposIncorretos = "Preencha o CEP corretamente no código " + 
						tableClientes.getValueAt(contador, 0) + "\n";
				camposCorretos = false;
			}
			if( !ValidarTelefone(tableClientes.getValueAt(contador, 10).toString()) ){
				camposIncorretos = "Preencha o telefone corretamente no código " + 
						tableClientes.getValueAt(contador, 0) + "\n";
				camposCorretos = false;
			}		
		}
		
		if( !camposCorretos ){
			linhas = connBD.ConsultaTabelaCliente(auxPesquisa,"","");
			CarregarTabela();
			JOptionPane.showMessageDialog(null, camposIncorretos, "Campos inválidos!", JOptionPane.ERROR_MESSAGE);
		}
		
		return camposCorretos;
	}
	
	public static boolean ValidarCPF(String strCPF)
	{		
	    int dig1; int dig2; int dig3; int dig4; int dig5; int dig6; int dig7; int dig8; int dig9;
	    int dig10; int dig11; int dv1; int dv2; int qDig;
	    
	    strCPF = strCPF.replace(".", "").replace("-", "");	    
	    
	    if (strCPF.length() == 0) //Validação do preenchimento
	    {
	        return false; //Caso não seja informado o CPF
	    }
	    else
	    {
	        if (strCPF.length() < 11)
	        {
	            strCPF = "00000000000" + strCPF;
	            strCPF = strCPF.substring(strCPF.length() - 11); //Completar 11 dígitos
	        }
	        else if (strCPF.length() > 11)
	        {
	            return false; //Caso tenha mais que 11 dígitos
	        }
	    }
	    
	    try{	    	
	    	qDig = strCPF.length(); //Quantidade total de caracteres
	   	 
		    //Gravar posição dos caracteres
		    dig1 = Integer.parseInt(Mid(strCPF, qDig - 10, 1));
		    dig2 = Integer.parseInt(Mid(strCPF, qDig - 9, 2));
		    dig3 = Integer.parseInt(Mid(strCPF, qDig - 8, 3));
		    dig4 = Integer.parseInt(Mid(strCPF, qDig - 7, 4));
		    dig5 = Integer.parseInt(Mid(strCPF, qDig - 6, 5));
		    dig6 = Integer.parseInt(Mid(strCPF, qDig - 5, 6));
		    dig7 = Integer.parseInt(Mid(strCPF, qDig - 4, 7));
		    dig8 = Integer.parseInt(Mid(strCPF, qDig - 3, 8));
		    dig9 = Integer.parseInt(Mid(strCPF, qDig - 2, 9));
		    dig10 = Integer.parseInt(Mid(strCPF, qDig - 1, 10));
		    dig11 = Integer.parseInt(Mid(strCPF, qDig, 11));
		    
		    //Cálculo para o primeiro dígito validador
		    dv1 = dig1 + (dig2 * 2) + (dig3 * 3) + (dig4 * 4) + (dig5 * 5) + (dig6 * 6) + (dig7 * 7) + (dig8 * 8) + (dig9 * 9);
		    dv1 %= 11;
		 
		    if (dv1 == 10)	    
		        dv1 = 0; //Se o resto for igual a 10, dv1 igual a zero		    
		 
		    //Cálculo para o segundo dígito validador
		    dv2 = dig2 + (dig3 * 2) + (dig4 * 3) + (dig5 * 4) + (dig6 * 5) + (dig7 * 6) + (dig8 * 7) + (dig9 * 8) + (dv1 * 9);
		    dv2 %= 11;
		 
		    if (dv2 == 10)		    
		        dv2 = 0; //Se o resto for igual a 10, dv2 igual a zero		    
		 
		    //Validação dos dígitos validadores, após o cálculo realizado
		    if ((dig10 == dv1) && (dig11 == dv2))		    
		        return true;		    
		    else		    
		        return false;
	    }
	    catch( NumberFormatException erro ){
	    	erro.printStackTrace();
	    	return false;
	    } 
	}
	
	public static String Mid(String valor, int inicio, int quant)
	{
	    String strMid = valor.substring(inicio - 1, quant);
	    return strMid;       
	}
	
	public boolean ValidarTelefone(String tel) {  
		  
	    String formato = "\\([1-9][0-9]\\) [2-9][0-9]{3}-[0-9]{4}";
	    	      
	    if((tel == null) || (tel.length()!=14) || (!tel.matches(formato)))  
	        return false;		      
	    else  
	        return true; 	    
	}
	
	public void PesquisarNome( String nome ){		
		linhas = connBD.ConsultaTabelaCliente(nome,"","");
		
		CarregarTabela();
	}
}
