package br.com.YourStyle_Forms;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
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

import br.com.YourStyle_BD.ConexaoBD;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class Pesq_Produtos extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNome;
	private JTable tableProdutos;
	private JScrollPane scrollPane;
	private String linhas[][],
	   			   colunas[] = {"Código", "Nome", "Tamanho", "Quantidade", "Preço", "Detalhes"};
	
	private ConexaoBD connBD = new ConexaoBD();
	private JTextField txtTamanho;

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
			Pesq_Produtos dialog = new Pesq_Produtos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Pesq_Produtos() {
		setMinimumSize(new Dimension(450, 354));
		setType(Type.UTILITY);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setTitle("Pesquisar Produtos");
		setBounds(100, 100, 892, 480);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon(Pesq_Produtos.class.getResource("/br/com/YourStyle_Imagens/Flaticon_34202.png")));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("SansSerif", Font.PLAIN, 12));
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setMnemonic(KeyEvent.VK_P);
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( txtNome.isShowing() )
					PesquisarNome( txtNome.getText().trim() );
				if( txtTamanho.isShowing() )
					PesquisarTamanho( txtTamanho.getText().trim() );
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
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(153)
					.addComponent(label, GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
					.addGap(143))
				.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPesquisar)
					.addContainerGap())
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap(794, Short.MAX_VALUE)
					.addComponent(btnFechar)
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
							.addComponent(label)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnPesquisar))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnFechar)
					.addContainerGap())
		);
		
		linhas = connBD.ConsultaTabelaProduto("","");
		
		CarregarTabela();
		
		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if( e.getKeyChar() == KeyEvent.VK_ENTER )
					PesquisarNome( txtNome.getText().trim() );
			}
		});
		tabbedPane.addTab("Nome", null, txtNome, null);
		tabbedPane.setEnabledAt(0, true);
		txtNome.setColumns(10);
		
		txtTamanho = new JTextField();
		txtTamanho.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {	
				if( e.getKeyChar() == KeyEvent.VK_ENTER )
					PesquisarTamanho( txtTamanho.getText().trim() );				
			}
		});
		tabbedPane.addTab("Tamanho", null, txtTamanho, null);
		tabbedPane.setEnabledAt(1, true);
		txtTamanho.setColumns(10);
		contentPanel.setLayout(gl_contentPanel);
	}

	private void CarregarTabela(){					
		tableProdutos = new JTable(linhas,colunas){
		    public boolean isCellEditable(int rowIndex, int vColIndex) {
	             return false;
		    }
		};		
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
	
	public JTable getTableClientes(){
		return tableProdutos;
	}
	
	public void PesquisarNome( String nome ){		
		linhas = connBD.ConsultaTabelaProduto(nome, "");
		
		CarregarTabela();
	}
	
	public void PesquisarTamanho(String tamanho) {
		linhas = connBD.ConsultaTabelaProduto("", tamanho);
		
		CarregarTabela();
	}
}
