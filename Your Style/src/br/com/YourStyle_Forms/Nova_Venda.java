package br.com.YourStyle_Forms;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumn;

import br.com.YourStyle_BD.ConexaoBD;
import br.com.YourStyle_Models.ModelNova_Venda;

@SuppressWarnings("serial")
public class Nova_Venda extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable tableCarrinhoCompras;
	private JScrollPane scrollPane;
	private JTextField txtTotalpagar, txtPago;
	private JComboBox<String> cbxCliente, cbxProduto;
	private JSpinner sQuantidade;
	private JButton btnAdicionar, btnRemover, okButton;
	private String linhasClientes[][] = {{}},
				   clientes[],				   
				   linhasProdutos[][],
				   produtos[], quantidade[], preco[],				   
				   carrinhoComprasColuna[] = {"Produto", "Quantidade", "Preço (R$)"},
				   carrinhoComprasLinhas[][] = new String[0][3],
				   caracteres="0987654321.";
	
	private ArrayList<String> carrinhoProduto = new ArrayList<String>(),
							  carrinhoQuantidade = new ArrayList<String>(),
							  carrinhoPreco = new ArrayList<String>();
	
	private boolean tabelaSelecionada, temNoCarrinho = false;
	
	private int contadorActionEvent = 0;
	
	private double totalPagar;
		
	private ConexaoBD connBD = new ConexaoBD();
	
	private ModelNova_Venda modelo = new ModelNova_Venda();

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
			Nova_Venda dialog = new Nova_Venda();			
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Nova_Venda() {
		setType(Type.UTILITY);
		setMinimumSize(new Dimension(450, 570));
		setModalityType(ModalityType.APPLICATION_MODAL);		
		setModal(true);
		setTitle("Nova Venda");
		setBounds(100, 100, 500, 570);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());		
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon(Nova_Venda.class.getResource("/br/com/YourStyle_Imagens/Flaticon_34330.png")));
		
		linhasClientes = connBD.ConsultaTabelaCliente("todos*","","");			
		
		clientes = dimensionar_duas_em_uma(linhasClientes);
		
				
		linhasProdutos = connBD.ConsultaTabelaProduto("todos*estoque","");
			
		produtos = dimensionar_duas_em_uma_separar_coluna(linhasProdutos, 0);
		quantidade = dimensionar_duas_em_uma_separar_coluna(linhasProdutos, 1);
		preco = dimensionar_duas_em_uma_separar_coluna(linhasProdutos, 2);
		
				
		JLabel lblCliente = new JLabel("Cliente");

		cbxCliente = new JComboBox<String>(clientes);		
		cbxCliente.setToolTipText("Selecione o cliente para habilitar os campos abaixo.");		
		cbxCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( cbxCliente.getSelectedItem() != null ){
					if( JOptionPane.showConfirmDialog(null, "Cliente: " + cbxCliente.getSelectedItem() + "\nCorreto?", "Confirmação do cliente", JOptionPane.YES_NO_OPTION) == 0 ){
						cbxCliente.setToolTipText("Cliente que realizará a compra.");
						cbxCliente.setEnabled(false);
						cbxProduto.setEnabled(true);
					}
					else
						cbxCliente.isRequestFocusEnabled();	
				}
			}
		});
		
		JLabel lblProduto = new JLabel("Produto");
				
		cbxProduto = new JComboBox<String>(produtos);
		cbxProduto.setAlignmentY(Component.TOP_ALIGNMENT);
		cbxProduto.setToolTipText("Produto");
		cbxProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( contadorActionEvent == 1 ){
					tableCarrinhoCompras.getSelectionModel().clearSelection();						
					btnRemover.setEnabled(false);
					tabelaSelecionada = false;
					contadorActionEvent = 0;
				}
				else
					contadorActionEvent++;					
				
				int maxQuantidade = Integer.parseInt(quantidade[cbxProduto.getSelectedIndex()]);											
				
				sQuantidade.setEnabled(true);
				sQuantidade.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(maxQuantidade), new Integer(1)));					
			}
		});
		cbxProduto.setEnabled(false);		
		
		btnAdicionar = new JButton("");
		btnAdicionar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAdicionar.setAlignmentY(Component.TOP_ALIGNMENT);
		btnAdicionar.setToolTipText("Adicionar(Selecione o produto)");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				sQuantidade.setEnabled(false);				
				quantidade[cbxProduto.getSelectedIndex()] = 
						String.valueOf(Integer.parseInt(quantidade[cbxProduto.getSelectedIndex()]) - 
								Integer.parseInt(sQuantidade.getValue().toString()));
				
				btnAdicionar.setEnabled(false);			
				
				temNoCarrinho = false;
				
				for( int cont = 0; cont < carrinhoProduto.size(); cont++ )					
						if( carrinhoProduto.get(cont).equals(cbxProduto.getSelectedItem().toString()) ){
							carrinhoQuantidade.set(cont, String.valueOf( (Integer.parseInt(carrinhoQuantidade.get(cont)) + 
									Integer.parseInt(sQuantidade.getValue().toString())) ) );
							carrinhoPreco.set(cont, String.valueOf( 
									(Double.parseDouble(carrinhoPreco.get(cont)) + 
									(Double.parseDouble(preco[cbxProduto.getSelectedIndex()]) * 
											Integer.parseInt(sQuantidade.getValue().toString())) ) ) );
							temNoCarrinho = true;
						}
				
				if( !temNoCarrinho ){
					carrinhoProduto.add(cbxProduto.getSelectedItem().toString());
					carrinhoQuantidade.add(sQuantidade.getValue().toString());	
					carrinhoPreco.add(String.valueOf(
							( Double.parseDouble(preco[cbxProduto.getSelectedIndex()]) *
									Integer.parseInt(sQuantidade.getValue().toString()) ) ));
				}			
				
				CarregaTabela();				
				txtTotalpagar.setText("R$ " + totalPagar);
				if(tableCarrinhoCompras.getRowCount() > 0)					
					txtPago.setEnabled(true);
				else
					txtPago.setEnabled(false);
				
				if( !carrinhoProduto.isEmpty() ){
					txtPago.setEnabled(true);
					okButton.setEnabled(true);
					tableCarrinhoCompras.setEnabled(true);
				}
				else{
					txtPago.setEnabled(false);
					okButton.setEnabled(false);
					tableCarrinhoCompras.setEnabled(false);
				}
			}
		});
		btnAdicionar.setEnabled(false);
		btnAdicionar.setIcon(new ImageIcon(Nova_Venda.class.getResource("/br/com/YourStyle_Imagens/Flaticon_36802.png")));
		
		btnRemover = new JButton("");
		btnRemover.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnRemover.setAlignmentY(Component.TOP_ALIGNMENT);
		btnRemover.setToolTipText("Remover(Selecione a linha na tabela abaixo)");
		btnRemover.setEnabled(false);
		btnRemover.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				temNoCarrinho = false;
				
				for( int cont = 0; cont < carrinhoProduto.size(); cont++ )					
					if( carrinhoProduto.get(cont).equals(cbxProduto.getSelectedItem().toString()) ){
						carrinhoQuantidade.set(cont, String.valueOf( (Integer.parseInt(carrinhoQuantidade.get(cont)) - 
								Integer.parseInt(sQuantidade.getValue().toString())) ) );
						quantidade[cbxProduto.getSelectedIndex()] = String.valueOf( (Integer.parseInt(quantidade[cbxProduto.getSelectedIndex()]) + 
								Integer.parseInt(sQuantidade.getValue().toString())) );
						carrinhoPreco.set(cont, String.valueOf( 
								(Double.parseDouble(carrinhoPreco.get(cont)) - 
								(Double.parseDouble(preco[cbxProduto.getSelectedIndex()]) * 
										Integer.parseInt(sQuantidade.getValue().toString())) ) ) );
						
						if( Integer.parseInt(carrinhoQuantidade.get(cont)) == 0){
							carrinhoProduto.remove(cont);
							carrinhoQuantidade.remove(cont);
							carrinhoPreco.remove(cont);
						}
						
						temNoCarrinho = true;
					}
				
				btnRemover.setEnabled(false);
				sQuantidade.setEnabled(false);
				
				CarregaTabela();
				txtTotalpagar.setText("R$ " + totalPagar);												
				
				if( !carrinhoProduto.isEmpty() ){
					txtPago.setEnabled(true);
					okButton.setEnabled(true);
					tableCarrinhoCompras.setEnabled(true);
				}
				else{
					txtPago.setEnabled(false);
					okButton.setEnabled(false);
					tableCarrinhoCompras.setEnabled(false);
				}
			}
		});
		btnRemover.setIcon(new ImageIcon(Nova_Venda.class.getResource("/br/com/YourStyle_Imagens/Flaticon_2777.png")));
		
		sQuantidade = new JSpinner();
		sQuantidade.setEnabled(false);
		sQuantidade.setAlignmentY(Component.TOP_ALIGNMENT);
		sQuantidade.setToolTipText("Quantidade do produto");
		sQuantidade.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {				
				if( Integer.parseInt(sQuantidade.getValue().toString()) >  0){
					if( tabelaSelecionada ){
						btnAdicionar.setEnabled(false);
						btnRemover.setEnabled(true);
					}
					else{
						btnRemover.setEnabled(false);
						btnAdicionar.setEnabled(true);					
					}
				}
				else{
					btnAdicionar.setEnabled(false);
					btnRemover.setEnabled(false);
				}
			}
		});

		sQuantidade.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		
		JLabel lblCarrinhoDeCompras = new JLabel("Carrinho de Compras");
		
		scrollPane = new JScrollPane();		
		
		CarregaTabela();
		
		JLabel lblTotalPagar = new JLabel("Total \u00E0 Pagar");
		
		txtTotalpagar = new JTextField();
		txtTotalpagar.setEditable(false);
		txtTotalpagar.setColumns(10);
		
		JLabel lblPago = new JLabel("Pago pelo cliente");
		
		txtPago = new JTextField();
		txtPago.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {				
				if(!caracteres.concat(".").contains(e.getKeyChar() + ""))					
					e.consume();
				else{					
					try{
						if(Double.parseDouble(txtPago.getText() + e.getKeyChar()) > Double.parseDouble(txtTotalpagar.getText().replace("R$ ", ""))){
							JOptionPane.showMessageDialog(null, "O valor pago pelo cliente não pode ultrapassar o valor total à pagar!", "Erro, ao inserir valor pago pelo cliente!", JOptionPane.ERROR_MESSAGE);
							e.consume();					
						}
					}
					catch(NumberFormatException erro){
						JOptionPane.showMessageDialog(null, "Digite o valor pago pelo cliente corretamente!", "Valor pago pelo cliente incorreto!", JOptionPane.ERROR_MESSAGE);
						e.consume();
					}
				}								
			}
		});
		txtPago.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				txtPago.setText("R$ " + txtPago.getText());
			}
			@Override
			public void focusGained(FocusEvent e) {
				txtPago.setText(txtPago.getText().replace("R$ ", ""));
			}
		});
		txtPago.setEnabled(false);
		txtPago.setColumns(10);
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTotalPagar)
						.addComponent(lblPago))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(txtTotalpagar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtPago, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(195, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(153)
					.addComponent(label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(143))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblCarrinhoDeCompras)
					.addContainerGap(298, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(cbxProduto, 0, 263, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(sQuantidade, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnAdicionar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnRemover)
					.addContainerGap())
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblProduto)
					.addContainerGap(375, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(cbxCliente, 0, 412, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblCliente)
					.addContainerGap(379, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblCliente)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbxCliente, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblProduto)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(cbxProduto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnRemover)
							.addComponent(sQuantidade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnAdicionar))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblCarrinhoDeCompras)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTotalPagar)
						.addComponent(txtTotalpagar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPago)
						.addComponent(txtPago, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);

		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Finalizar");
				okButton.setMnemonic(KeyEvent.VK_F);
				okButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
				okButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
				okButton.setEnabled(false);
				okButton.setToolTipText("Clique para finalizar a venda");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {						
						modelo.setCPFCliente(cbxCliente.getSelectedItem().toString().substring(
								cbxCliente.getSelectedItem().toString().indexOf("-") + 2, cbxCliente.getSelectedItem().toString().length()));						
						modelo.setNomesProdutos(carrinhoComprasLinhas);
						modelo.setTamanhosProdutos(carrinhoComprasLinhas);
						modelo.setQuantidadesProdutos(carrinhoComprasLinhas);
						modelo.setValoresProdutos(carrinhoComprasLinhas);						
						modelo.setValorTotal(Double.parseDouble("0" + txtTotalpagar.getText().replace("R$ ", "")));						
						modelo.setPagoTotal(Double.parseDouble("0" + txtPago.getText().replace("R$ ", "")));						
						
						connBD.InserirTabelaVenda_Produto_Cliente(modelo);
												
						if( modelo.getPagoTotal() < modelo.getValorTotal() )							
							JOptionPane.showMessageDialog(null, "Valor de R$ " + 
									String.format( "%.2f", (modelo.getValorTotal() - modelo.getPagoTotal()) ) + 									  
										" acrescido à dívida do cliente.", 
											"Dívida acrescida", JOptionPane.INFORMATION_MESSAGE);
						
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.setMnemonic(KeyEvent.VK_C);
				cancelButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
				cancelButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}		
	}
	
	static String [] dimensionar_duas_em_uma (String[][] matriz){
		
		String[] ret = new String[matriz.length * matriz[0].length];            

		int pos = 0;

		for(int j=0;j<matriz.length; j++) 
			for(int i=0;i<matriz[j].length; i++)
				ret[pos++] = matriz[j][i];

		return ret;		
	}
	
	static String [] dimensionar_duas_em_uma_separar_coluna (String[][] matriz, int coluna){

        String[] ret = new String[matriz.length];

        int pos = 0;
        
        if(coluna == 0)
        	for(int j=0;j<matriz.length; j++)        	
            	ret[pos++] = matriz[j][coluna] + " - R$ " + matriz[j][2] + " (U)";        
        else
            for(int j=0;j<matriz.length; j++)        	
            	ret[pos++] = matriz[j][coluna];       

        return ret;
	}
	
	public void CarregaTabela(){
		totalPagar = 0;
		
		carrinhoComprasLinhas = new String[carrinhoProduto.size()][3];	

		for( int cont = 0; cont < carrinhoComprasLinhas.length; cont++ )			
			for( int cont2 = 0; cont2 < carrinhoComprasLinhas[cont].length; cont2++ )					
					if(cont2 == 0)
						carrinhoComprasLinhas[cont][cont2] = carrinhoProduto.get(cont);
					else
						if(cont2 == 1)
							carrinhoComprasLinhas[cont][cont2] = carrinhoQuantidade.get(cont);
						else
							if(cont2 == 2){								
								carrinhoComprasLinhas[cont][cont2] = carrinhoPreco.get(cont);
								totalPagar += Double.parseDouble(carrinhoPreco.get(cont));
							}							
									
		tableCarrinhoCompras = new JTable(carrinhoComprasLinhas, carrinhoComprasColuna){
		    public boolean isCellEditable(int rowIndex, int vColIndex) {
	             return false;
		    }
		};	
		tableCarrinhoCompras.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableCarrinhoCompras.setEnabled(false);
		tableCarrinhoCompras.getTableHeader().setReorderingAllowed(false);
		tableCarrinhoCompras.setToolTipText("Clique na linha que deseja alterar.");
		tableCarrinhoCompras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				contadorActionEvent = 0;
				tabelaSelecionada = true;
				btnAdicionar.setEnabled(false);
				btnRemover.setEnabled(true);
				cbxProduto.setSelectedItem(tableCarrinhoCompras.getValueAt(tableCarrinhoCompras.getSelectedRow(), 0));				
				sQuantidade.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), 
						new Integer(Integer.parseInt(tableCarrinhoCompras.getValueAt(tableCarrinhoCompras.getSelectedRow(), 1).toString())), new Integer(1)));
				sQuantidade.setValue(Integer.parseInt(tableCarrinhoCompras.getValueAt(tableCarrinhoCompras.getSelectedRow(), 1).toString()));				
			}
		});		
		tableCarrinhoCompras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableCarrinhoCompras.putClientProperty("terminateEditOnFocusLost", true);
		tableCarrinhoCompras.setShowVerticalLines(true);
		tableCarrinhoCompras.setShowHorizontalLines(true);	
		
		TableColumn col = tableCarrinhoCompras.getColumnModel().getColumn(0);
		TableColumn col2 = tableCarrinhoCompras.getColumnModel().getColumn(1);
		TableColumn col3 = tableCarrinhoCompras.getColumnModel().getColumn(2);
		
		col.setMinWidth(55);
		col.setPreferredWidth(311);
		col2.setPreferredWidth(75);
		col2.setMinWidth(75);				
		col3.setPreferredWidth(70);
		col3.setMinWidth(70);
		
		scrollPane.setViewportView(tableCarrinhoCompras);	
	}
}