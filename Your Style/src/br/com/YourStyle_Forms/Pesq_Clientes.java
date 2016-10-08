package br.com.YourStyle_Forms;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
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
import javax.swing.text.MaskFormatter;

import br.com.YourStyle_BD.ConexaoBD;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class Pesq_Clientes extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNome;
	private JFormattedTextField frmtdtxtfldRg, frmtdtxtfldCpf;
	private JTable tableClientes;
	private JScrollPane scrollPane;
	private String linhas[][],
				   colunas[] = {"Código", "Nome","RG","CPF","Endereço","Nº","Bairro","Cidade",
						"Estado","CEP","Telefone","Celular","Dívida (R$)","Data do cadastro"};
	private MaskFormatter rg, cpf;	
	
	private ConexaoBD connBD = new ConexaoBD();

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
			Pesq_Clientes dialog = new Pesq_Clientes();			
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Pesq_Clientes() {
		setMinimumSize(new Dimension(450, 348));
		setType(Type.UTILITY);		
		setModal(true);
		setTitle("Pesquisa Clientes");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 1024, 480);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon(Pesq_Clientes.class.getResource("/br/com/YourStyle_Imagens/Flaticon_34202.png")));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("SansSerif", Font.PLAIN, 12));
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setMnemonic(KeyEvent.VK_P);
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				if( txtNome.isShowing() )					
					PesquisarNome( txtNome.getText().trim() );
				if( frmtdtxtfldRg.isShowing() )					
					PesquisarRg( frmtdtxtfldRg.getText().trim() );
				if( frmtdtxtfldCpf.isShowing() )
					PesquisarCpf( frmtdtxtfldCpf.getText().trim() );
			}
		});
		
		scrollPane = new JScrollPane();
		
		JButton btnFechar = new JButton("Fechar");
		btnFechar.setMnemonic(KeyEvent.VK_F);
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(153)
					.addComponent(label, GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE)
					.addGap(143))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 895, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPesquisar)
					.addContainerGap())
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap(932, Short.MAX_VALUE)
					.addComponent(btnFechar))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 986, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(label)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnPesquisar))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
					.addGap(12)
					.addComponent(btnFechar))
		);
				
		linhas = connBD.ConsultaTabelaCliente("","","");	
		
		CarregarTabela();		
		
		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if( e.getKeyChar() == KeyEvent.VK_ENTER )
					PesquisarNome( txtNome.getText().trim() );								     
			}
		});

		txtNome.setToolTipText("Pesquisa por nome");
		tabbedPane.addTab("Nome", null, txtNome, null);
		tabbedPane.setEnabledAt(0, true);
		txtNome.setColumns(10);
		
		try {
			rg = new MaskFormatter("##.###.###-#");
			rg.setPlaceholderCharacter('_');
		} catch (ParseException e1) {			
			e1.printStackTrace();
		}
		
		frmtdtxtfldRg = new JFormattedTextField(rg);
		frmtdtxtfldRg.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if( e.getKeyChar() == KeyEvent.VK_ENTER )
					PesquisarRg( frmtdtxtfldRg.getText().trim() );					
			}
		});
		frmtdtxtfldRg.setToolTipText("Pesquisar por RG");
		tabbedPane.addTab("RG", null, frmtdtxtfldRg, null);
		tabbedPane.setEnabledAt(1, true);
		
		try {
			 cpf = new MaskFormatter("###.###.###-##");
			 cpf.setPlaceholderCharacter('_');
		} catch (ParseException e1) {			
			e1.printStackTrace();
		}
		
		frmtdtxtfldCpf = new JFormattedTextField(cpf);
		frmtdtxtfldCpf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if( e.getKeyChar() == KeyEvent.VK_ENTER )
					PesquisarCpf( frmtdtxtfldCpf.getText().trim() );
			}
		});
		frmtdtxtfldCpf.setToolTipText("Pesquisa por CPF");
		tabbedPane.addTab("CPF", null, frmtdtxtfldCpf, null);
		contentPanel.setLayout(gl_contentPanel);
	}
	
	private void CarregarTabela(){
		tableClientes = new JTable(linhas,colunas){
		    public boolean isCellEditable(int rowIndex, int vColIndex) {
	             return false;
		    }
		};		
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
	
	public void PesquisarNome( String nome ){		
		linhas = connBD.ConsultaTabelaCliente(nome,"","");
		
		CarregarTabela();
	}
	
	public void PesquisarRg( String rg ){
		linhas = connBD.ConsultaTabelaCliente("",rg,"");
		
		CarregarTabela();
	}
	
	public void PesquisarCpf( String cpf ){		
		linhas = connBD.ConsultaTabelaCliente("","",cpf);
		
		CarregarTabela();
	}
}
