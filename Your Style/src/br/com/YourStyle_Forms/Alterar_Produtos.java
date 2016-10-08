package br.com.YourStyle_Forms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;

import br.com.YourStyle_BD.ConexaoBD;

@SuppressWarnings("serial")
public class Alterar_Produtos extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable tableProdutos;
	private JScrollPane scrollPane = new JScrollPane();
	private String linhas[][],
				   colunas[] = {"Código","Nome", "Tamanho", "Quantidade", "Preço", "Detalhes"},
				   auxPesquisa = "";
	
	private ConexaoBD connBD = new ConexaoBD();
	private JTextField txtNome;
	private JButton btnAlterar, btnExcluir;

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
			Alterar_Produtos dialog = new Alterar_Produtos();			
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Alterar_Produtos() {
		setType(Type.UTILITY);
		setMinimumSize(new Dimension(892, 453));
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setTitle("Alterar Produtos");
		setBounds(100, 100, 892, 453);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon(Alterar_Produtos.class.getResource("/br/com/YourStyle_Imagens/Flaticon_7706.png")));		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnExcluir.setEnabled(false);
				
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
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(153)
					.addComponent(label, GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
					.addGap(143))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPesquisar)
					.addContainerGap())
				.addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup()
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
							.addComponent(label)
							.addGap(13)
							.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnPesquisar))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
					.addGap(0))
		);
		{
			txtNome = new JTextField();
			tabbedPane.addTab("Nome", null, txtNome, null);
			txtNome.setColumns(10);
		}
		{
			linhas = connBD.ConsultaTabelaProduto("","");		
						
			CarregarTabela();
		}
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnExcluir = new JButton("Excluir");				
				btnExcluir.setEnabled(false);
				btnExcluir.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						connBD.DeletarProduto(Integer.parseInt(tableProdutos.getValueAt(tableProdutos.getSelectedRow(), 0).toString()));
						
						linhas = connBD.ConsultaTabelaProduto(auxPesquisa, "");
						CarregarTabela();
					}
				});
				buttonPane.add(btnExcluir);
			}
			{
				btnAlterar = new JButton("Alterar");
				btnAlterar.setMnemonic(KeyEvent.VK_A);
				btnAlterar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {						
						if( CamposCorretos() )
							connBD.AlterarTabelaProduto(linhas, tableProdutos);				
					}
				});							
				buttonPane.add(btnAlterar);
				getRootPane().setDefaultButton(btnAlterar);
			}
			{
				JButton btnFechar = new JButton("Fechar");
				btnFechar.setMnemonic(KeyEvent.VK_F);
				btnFechar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnFechar.setActionCommand("Cancel");
				buttonPane.add(btnFechar);
			}
		}
	}

	private void CarregarTabela(){		
		tableProdutos = new JTable(linhas,colunas){
		    public boolean isCellEditable(int rowIndex, int vColIndex) {
	             if( vColIndex == 0 )
	            	 return false;
	             else
	            	 return true;
		    }
		};	
		tableProdutos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnExcluir.setEnabled(true);
			}
		});
		tableProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableProdutos.getTableHeader().setReorderingAllowed(false); //Não permite o deslocamento das colunas.
		tableProdutos.putClientProperty("terminateEditOnFocusLost", true);
		tableProdutos.setToolTipText("Clique duas vezes no campo que deseja alterar.");
		tableProdutos.setShowVerticalLines(true);
		tableProdutos.setShowHorizontalLines(true);
		tableProdutos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		TableColumn col = tableProdutos.getColumnModel().getColumn(0);
		TableColumn col1 = tableProdutos.getColumnModel().getColumn(1);
		TableColumn col2 = tableProdutos.getColumnModel().getColumn(2);
		TableColumn col3 = tableProdutos.getColumnModel().getColumn(3);
		TableColumn col4 = tableProdutos.getColumnModel().getColumn(4);
		TableColumn col5 = tableProdutos.getColumnModel().getColumn(5);
		
		col.setPreferredWidth(50);
		col.setMinWidth(50);
		col1.setPreferredWidth(90);
		col2.setPreferredWidth(63);
		col2.setResizable(false);
		col3.setPreferredWidth(75);
		col3.setResizable(false);
		col4.setPreferredWidth(120);	
		col5.setPreferredWidth(450);		

		scrollPane.setViewportView(tableProdutos);
	}
	
	public boolean CamposCorretos(){
		boolean camposCorretos = true;
		
		String camposIncorretos = "";		
		
		for( int contador = 0; contador < linhas.length; contador++ ){
			if( tableProdutos.getValueAt(contador, 1).toString().length() < 2 ){
				camposIncorretos = "Preencha o nome corretamente no código " + 
						tableProdutos.getValueAt(contador, 0) + "\n";
				camposCorretos = false;
			}
			try{
				if( Integer.parseInt(tableProdutos.getValueAt(contador, 3).toString()) < 0 || 
						tableProdutos.getValueAt(contador, 2).toString().contains("0987654321") ){
					camposIncorretos = "Preencha a quantidade corretamente no código " + 
						tableProdutos.getValueAt(contador, 0) + "\n";
					camposCorretos = false;
				}
			}
			catch(NumberFormatException erro){
				camposIncorretos = "Preencha a quantidade corretamente no código " + 
						tableProdutos.getValueAt(contador, 0) + "\n";
				camposCorretos = false;
			}
			if( tableProdutos.getValueAt(contador, 4).toString().length() < 1 || 
					tableProdutos.getValueAt(contador, 3).toString().contains("0987654321")){					
				camposIncorretos = "Preencha o preço corretamente no código " + 
					tableProdutos.getValueAt(contador, 0) + "\n";
				camposCorretos = false;
			}
			else
				if( Double.parseDouble(tableProdutos.getValueAt(contador, 4).toString()) < 0 ){
					camposIncorretos = "Preencha o preço corretamente no código " + 
						tableProdutos.getValueAt(contador, 0) + "\n";
					camposCorretos = false;
				}
		}	
		
		if( !camposCorretos ){
			linhas = connBD.ConsultaTabelaProduto(auxPesquisa,"");
			CarregarTabela();
			JOptionPane.showMessageDialog(null, camposIncorretos, "Campos inválidos!", JOptionPane.ERROR_MESSAGE);
		}
		
		return camposCorretos;
	}
	
	public void PesquisarNome( String nome ){		
		linhas = connBD.ConsultaTabelaProduto(nome, "");
		
		CarregarTabela();
	}
	
	protected void DeletarProduto( int codigo ){	
		connBD.DeletarProduto(codigo);
		
		linhas = connBD.ConsultaTabelaProduto(auxPesquisa, "");		
	}
}
