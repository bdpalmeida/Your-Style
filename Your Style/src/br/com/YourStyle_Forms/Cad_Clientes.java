package br.com.YourStyle_Forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;

import br.com.YourStyle_BD.ConexaoBD;
import br.com.YourStyle_Models.ModelCad_Clientes;

@SuppressWarnings("serial")
public class Cad_Clientes extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNome, txtEndereco, txtNumero, txtBairro, txtCidade, 
					   txtValordivida, txt = new JTextField();
	private JFormattedTextField frmtdtxtfldRg, frmtdtxtfldCpf, frmtdtxtfldCep, frmtdtxtfldTelefone, 
								frmtdtxtfldCelular;
	private JComboBox<String> cbxEstado, comboBox = new JComboBox<String>();
	private JCheckBox chckbxDivida;	
	private JLabel lblValor;
	private String caracteres="0987654321";
	private boolean camposInvalidos;
	private MaskFormatter rg, cpf, cep, telefone, celular;

	private ConexaoBD connBD = new ConexaoBD();
	
	private ModelCad_Clientes modelo = new ModelCad_Clientes();	
	
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
			Cad_Clientes dialog = new Cad_Clientes();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Cad_Clientes() {
		setType(Type.UTILITY);
		setResizable(false);
		setModal(true);
		setTitle("Cadastro de Cliente");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 434, 502);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Cad_Clientes.class.getResource("/br/com/YourStyle_Imagens/Flaticon_3673.png")));
		JLabel lblNome = new JLabel("Nome*");		
		
		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {				
				if(caracteres.contains(e.getKeyChar()+""))
					e.consume();				
			}
		});
		txtNome.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {				
				if( txtNome.getText().replace(" ", "").length() < 3 )
					txtNome.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));			
				else
					txtNome.setBorder(txt.getBorder());				
			}
		});
		txtNome.setToolTipText("Nome do cliente");
		txtNome.setColumns(10);
		
		JLabel lblRg = new JLabel("RG*");
		
		try {
			rg = new MaskFormatter("##.###.###-#");
			rg.setPlaceholderCharacter('_'); 
		} catch (ParseException e1) {			
			e1.printStackTrace();
		}
		
		frmtdtxtfldRg = new JFormattedTextField(rg);
		frmtdtxtfldRg.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {				
				if( frmtdtxtfldRg.getText().replace("_","").length() < 11 ){					
					frmtdtxtfldRg.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));
					frmtdtxtfldRg.setValue("");
				}
				else
					frmtdtxtfldRg.setBorder(txt.getBorder());	
			}
		});		
		frmtdtxtfldRg.setToolTipText("RG do cliente");		
		
		JLabel lblCpf = new JLabel("CPF*");
		
		try {
			 cpf = new MaskFormatter("###.###.###-##");
			 cpf.setPlaceholderCharacter('_'); 
		} catch (ParseException e1) {			
			e1.printStackTrace();
		}
		
		frmtdtxtfldCpf = new JFormattedTextField(cpf);		
		frmtdtxtfldCpf.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {				
				if( !ValidarCPF(frmtdtxtfldCpf.getText()) )					{
					frmtdtxtfldCpf.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));
					frmtdtxtfldCpf.setValue("");
				}
				else
					frmtdtxtfldCpf.setBorder(txt.getBorder());
			}
		});
		frmtdtxtfldCpf.setToolTipText("CPF do cliente");		
		
		JLabel lblEndereco = new JLabel("Endere\u00E7o*");
		
		txtEndereco = new JTextField();
		txtEndereco.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if( txtEndereco.getText().length() < 3 )
					txtEndereco.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));
				else
					txtEndereco.setBorder(txt.getBorder());				
			}
		});
		txtEndereco.setColumns(10);
		
		txtNumero = new JTextField();
		txtNumero.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if( txtNumero.getText().isEmpty() )
					txtNumero.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));
				else
					txtNumero.setBorder(txt.getBorder());
			}
		});
		txtNumero.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!caracteres.contains(e.getKeyChar()+""))
					e.consume();
			}
		});
		txtNumero.setColumns(10);
		
		JLabel lblN = new JLabel("N\u00BA*");
		
		JLabel lblBairro = new JLabel("Bairro*");
		
		txtBairro = new JTextField();		
		txtBairro.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if( txtBairro.getText().replace(" ", "").length() < 3 )
					txtBairro.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));
				else
					txtBairro.setBorder(txt.getBorder());			
			}
		});
		txtBairro.setColumns(10);
		
		JLabel lblCidade = new JLabel("Cidade*");
		
		txtCidade = new JTextField();
		txtCidade.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(caracteres.contains(e.getKeyChar()+""))
					e.consume();			
			}
		});
		txtCidade.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if( txtCidade.getText().replace(" ", "").length() < 3 )
					txtCidade.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));				
				else
					txtCidade.setBorder(txt.getBorder());			
			}
		});
		txtCidade.setColumns(10);
		
		JLabel lblCep = new JLabel("CEP*");
		
		try {
			 cep = new MaskFormatter("#####-###");
			 cep.setPlaceholderCharacter('_'); 
		} catch (ParseException e1) {			
			e1.printStackTrace();
		}
		
		frmtdtxtfldCep = new JFormattedTextField(cep);
		frmtdtxtfldCep.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {				
				if( frmtdtxtfldCep.getText().replace("_", "").length() < 9 ){
					frmtdtxtfldCep.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));	
					frmtdtxtfldCep.setValue("");
				}
				else
					frmtdtxtfldCep.setBorder(txt.getBorder());
			}
		});
		frmtdtxtfldCep.setToolTipText("CEP do cliente");		
		
		JLabel lblTelefone = new JLabel("Telefone*");
		
		try {
			 telefone = new MaskFormatter("(##) ####-####");
			 telefone.setPlaceholderCharacter('_'); 
		} catch (ParseException e1) {			
			e1.printStackTrace();
		}
		
		frmtdtxtfldTelefone = new JFormattedTextField(telefone);
		frmtdtxtfldTelefone.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(!ValidarTelefone(frmtdtxtfldTelefone.getText()))	{			
					frmtdtxtfldTelefone.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));
					frmtdtxtfldTelefone.setValue("");
				}
				else
					frmtdtxtfldTelefone.setBorder(txt.getBorder());
			}
		});
		frmtdtxtfldTelefone.setToolTipText("Telefone do cliente");		
		
		JLabel lblCelular = new JLabel("Celular");
		
		try {
			 celular = new MaskFormatter("(##) #-####-####");
			 celular.setPlaceholderCharacter('_'); 
		} catch (ParseException e1) {			
			e1.printStackTrace();
		}
		
		frmtdtxtfldCelular = new JFormattedTextField(celular);
		frmtdtxtfldCelular.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!caracteres.contains(e.getKeyChar()+""))
					e.consume();				
			}
		});
		frmtdtxtfldCelular.setToolTipText("Celular do cliente");		
		
		chckbxDivida = new JCheckBox("D\u00EDvida?");
		chckbxDivida.setToolTipText("O cliente t\u00EAm d\u00EDvida na loja?");
		chckbxDivida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( chckbxDivida.isSelected() ){
					lblValor.setEnabled(true);
					txtValordivida.setEnabled(true);
				}
				else{
					lblValor.setEnabled(false);
					txtValordivida.setEnabled(false);
					txtValordivida.setBorder(txt.getBorder());
				}
			}
		});

		lblValor = new JLabel("Valor");
		lblValor.setEnabled(false);
		
		txtValordivida = new JTextField();
		txtValordivida.setText("R$ ");
		txtValordivida.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(!txtValordivida.getText().startsWith("R$"))
					txtValordivida.setText("R$ " + txtValordivida.getText());
				
				if( txtValordivida.getText().replace("R$ ", "").isEmpty() )
					txtValordivida.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));				
				else
					txtValordivida.setBorder(txt.getBorder());
			}
			@Override
			public void focusGained(FocusEvent e) {
				txtValordivida.setText(txtValordivida.getText().replace("R$ ", ""));
			}
		});
		txtValordivida.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!caracteres.concat(".").contains(e.getKeyChar()+""))
					e.consume();
				try{
					Double.parseDouble(txtValordivida.getText() + e.getKeyChar());
				}
				catch(NumberFormatException erro){
					JOptionPane.showMessageDialog(null, "Insira o valor corretamente.","Valor incorreto!",JOptionPane.ERROR_MESSAGE);
					e.consume();
				}
			}
		});
		txtValordivida.setToolTipText("Valor da d\u00EDvida do cliente na loja");
		txtValordivida.setEnabled(false);
		txtValordivida.setColumns(10);
		
		JLabel lblEstado = new JLabel("Estado*");
		
		cbxEstado = new JComboBox<String>();
		cbxEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( cbxEstado.getSelectedItem().toString().isEmpty() )
					cbxEstado.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));								
				else
					cbxEstado.setBorder(comboBox.getBorder());	
			}
		});
		cbxEstado.setModel(new DefaultComboBoxModel<String>(new String[] 
				{"", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", 
				"PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"}));
		
		JLabel label_1 = new JLabel("* Campos Obrigat\u00F3rios");
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(12)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTelefone)
						.addComponent(lblEstado)
						.addComponent(lblBairro)
						.addComponent(lblEndereco)
						.addComponent(lblRg)
						.addComponent(lblNome))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(frmtdtxtfldRg, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblCpf)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(frmtdtxtfldCpf, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(txtEndereco, GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblN)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtNumero, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
						.addComponent(txtNome, 323, 323, 323)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(txtBairro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(lblCidade)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtCidade, 143, 143, 143))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(cbxEstado, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblCep)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(frmtdtxtfldCep, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(frmtdtxtfldTelefone, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblCelular)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(frmtdtxtfldCelular, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)))
					.addGap(8))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(159)
					.addComponent(label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(131))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(74)
							.addComponent(txtValordivida, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
						.addComponent(chckbxDivida)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(40)
							.addComponent(lblValor)))
					.addContainerGap(258, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(14)
					.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(277, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(12)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtNome, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNome))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRg)
						.addComponent(frmtdtxtfldRg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCpf)
						.addComponent(frmtdtxtfldCpf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtEndereco, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtNumero, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblEndereco)
						.addComponent(lblN))
					.addGap(6)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(txtBairro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblBairro))
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(txtCidade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblCidade)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEstado)
						.addComponent(cbxEstado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCep)
						.addComponent(frmtdtxtfldCep, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTelefone)
						.addComponent(frmtdtxtfldTelefone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCelular)
						.addComponent(frmtdtxtfldCelular, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxDivida)
					.addGap(2)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblValor)
						.addComponent(txtValordivida, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_1)
					.addGap(7))
		);
		gl_contentPanel.linkSize(SwingConstants.VERTICAL, new Component[] {txtNome, frmtdtxtfldRg, frmtdtxtfldCpf, txtEndereco, txtNumero, txtBairro, txtCidade, frmtdtxtfldCep, frmtdtxtfldTelefone, frmtdtxtfldCelular, txtValordivida});
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnCadastrar = new JButton("Cadastrar");
				btnCadastrar.setMnemonic(KeyEvent.VK_C);
				btnCadastrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if( ColoreCamposInvalidos() )							
							JOptionPane.showMessageDialog(null, 
									"Preencha corretamente os campos obrigatórios", "Erro, campos inválidos!", 
									JOptionPane.ERROR_MESSAGE);						
						else{							
							modelo.setNome(txtNome.getText().trim());
							modelo.setRg(frmtdtxtfldRg.getText());
							modelo.setCpf(frmtdtxtfldCpf.getText());
							modelo.setEndereco(txtEndereco.getText().trim());
							modelo.setN_endereco(Integer.parseInt(txtNumero.getText()));
							modelo.setBairro(txtBairro.getText().trim());
							modelo.setCidade(txtCidade.getText().trim());
							modelo.setEstado(String.valueOf(cbxEstado.getSelectedItem()));
							modelo.setCep(frmtdtxtfldCep.getText());
							modelo.setTelefone(frmtdtxtfldTelefone.getText());
							modelo.setCelular(frmtdtxtfldCelular.getText());
							modelo.setVl_divida(Double.parseDouble("0"+ txtValordivida.getText().replace("R$ ", "")));
							
							if( connBD.InserirTabelaCliente(
									modelo.getNome(), modelo.getRg(), modelo.getCpf(), modelo.getEndereco(),
									modelo.getN_endereco(), modelo.getBairro(), modelo.getCidade(), 
									modelo.getEstado(), modelo.getCep(), modelo.getTelefone(), modelo.getCelular(), 
									modelo.getVl_divida()) )								
								dispose();
							
							else
								LimparRgCpf();								
						}													
					}
				});
				btnCadastrar.setActionCommand("OK");
				buttonPane.add(btnCadastrar);
				getRootPane().setDefaultButton(btnCadastrar);				
			}
			{
				JButton btnCancelar = new JButton("Cancelar");
				btnCancelar.setMnemonic(KeyEvent.VK_A);
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();						
					}
				});
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}			
	}
	protected void LimparRgCpf() {			
		frmtdtxtfldRg.setValue("");
		frmtdtxtfldCpf.setValue("");
	}

	public boolean ValidarTelefone(String tel) {  
		  
	    String formato = "\\([1-9][0-9]\\) [2-9][0-9]{3}-[0-9]{4}";
	    /*Ou usar "[1-9][1-9][2-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]"
	     * Ou usar "\\((10)|([1-9][1-9])\\) [2-9][0-9]{3}-[0-9]{4}"
	     * \d: Um dígito \s: Um caractere de espaço em branco \w: Um caractere de palavra(letras, dígitos ou “_”) . : Qualquer caractere - S
	     * ee more at: http://www.botecodigital.info/java/validando-dados-com-expressoes-regulares-em-java/#sthash.53LEtTfu.dpuf
	     */
	    	      
	    if((tel == null) || (tel.length()!=14) || (!tel.matches(formato)))  
	        return false;		      
	    else  
	        return true; 	    
	}  
	 	
	public boolean ValidarCPF(String strCPF)
	{		
	    int dig1; int dig2; int dig3; int dig4; int dig5; int dig6; int dig7; int dig8; int dig9;
	    int dig10; int dig11; int dv1; int dv2; int qDig;
	    
	    strCPF = strCPF.replace(".", "").replace("-", "").replace("_", ""); 
	    
	    if (strCPF.length() == 0 || //Validação do preenchimento
	    		strCPF.equals("00000000000") || strCPF.equals("11111111111") || strCPF.equals("22222222222") || 
	    		strCPF.equals("33333333333") || strCPF.equals("44444444444") || strCPF.equals("55555555555") ||
	    		strCPF.equals("66666666666") || strCPF.equals("77777777777") || strCPF.equals("88888888888") || 
	    		strCPF.equals("99999999999") )	    		
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
	
	public boolean ColoreCamposInvalidos(){
		camposInvalidos = false;
		
		if( txtNome.getText().length() < 3 ){
			txtNome.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));
			camposInvalidos = true;
		}		
		if( frmtdtxtfldRg.getText().replace("_","").length() < 11 ){			
			frmtdtxtfldRg.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));	
			camposInvalidos = true;
		}
		if( !ValidarCPF(frmtdtxtfldCpf.getText()) ){					
			frmtdtxtfldCpf.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));
			camposInvalidos = true;
		}
		if( txtEndereco.getText().length() < 3 ){
			txtEndereco.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));
			camposInvalidos = true;
		}
		if( txtNumero.getText().length() == 0 ){
			txtNumero.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));
			camposInvalidos = true;
		}
		if( txtBairro.getText().length() < 3 ){
			txtBairro.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));
			camposInvalidos = true;
		}
		if( txtCidade.getText().length() < 3 ){
			txtCidade.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));
			camposInvalidos = true;
		}
		if( cbxEstado.getSelectedItem().toString().isEmpty() ){
			cbxEstado.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));								
			camposInvalidos = true;		
		}		
		if( frmtdtxtfldCep.getText().replace("_", "").length() < 9 ){
			frmtdtxtfldCep.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));
			camposInvalidos = true;
		}
		if(!ValidarTelefone(frmtdtxtfldTelefone.getText())){
			frmtdtxtfldTelefone.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));
			camposInvalidos = true;
		}		
		
		if( chckbxDivida.isSelected() && txtValordivida.getText().replace("R$ ", "").isEmpty() ){
			txtValordivida.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));
			camposInvalidos = true;
		}
		return camposInvalidos;		
	}
}
