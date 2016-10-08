package br.com.YourStyle_Forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class Sobre extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final JLabel lblIconDoSoftware = new JLabel("");
	private final JLabel lblJudicativo = new JLabel("Direitos de Autor \u00A9 2014 - Brenno Almeida, Beatriz Lima e Elaine Nascimento.");
	private final JLabel lblYourStyle = new JLabel("");
	private final JLabel lblIconempresa = new JLabel("");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			Sobre dialog = new Sobre();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Sobre() {
		setType(Type.POPUP);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				dispose();
			}
		});
		lblIconempresa.setBounds(419, 268, 132, 29);
		lblIconempresa.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblIconempresa.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblIconempresa.setIcon(new ImageIcon(Sobre.class.getResource("/br/com/YourStyle_Imagens/conexao 3(pequeno).png")));
		lblIconempresa.setHorizontalAlignment(SwingConstants.RIGHT);
		setResizable(false);
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				dispose();
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				dispose();
			}
		});
		lblJudicativo.setBounds(6, 282, 372, 15);
		lblJudicativo.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblJudicativo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				dispose();
			}
		});
		lblJudicativo.setFont(new Font("SansSerif", Font.PLAIN, 11));
		setUndecorated(true);
		setTitle("Sobre Your Style");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		lblIconDoSoftware.setBounds(6, 56, 134, 173);
		lblIconDoSoftware.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblIconDoSoftware.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblIconDoSoftware.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				dispose();
			}
		});
		lblIconDoSoftware.setIcon(new ImageIcon(Sobre.class.getResource("/br/com/YourStyle_Imagens/IconeYourStyle.png")));
		lblIconDoSoftware.setHorizontalAlignment(SwingConstants.CENTER);
		setBounds(100, 100, 557, 303);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JTextPane txtpnSobreYourStyle = new JTextPane();
		txtpnSobreYourStyle.setBounds(152, 91, 318, 120);
		txtpnSobreYourStyle.setAlignmentY(Component.TOP_ALIGNMENT);
		txtpnSobreYourStyle.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				dispose();
			}
		});
		txtpnSobreYourStyle.setDisabledTextColor(Color.BLACK);
		txtpnSobreYourStyle.setEnabled(false);
		txtpnSobreYourStyle.setOpaque(false);
		txtpnSobreYourStyle.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		txtpnSobreYourStyle.setEditable(false);
		txtpnSobreYourStyle.setText("Desenvolvido para estabelecimento comercial, \r\narmarinho voltado para o ramo de roupas e acess\u00F3rios.\r\n\r\nT\u00EAm o objetivo de registrar clientes, registrar produtos, \r\ncontrolar estoque, pesquisar cliente, pesquisar produto, pesquisar vendas realizadas, pertinentes ao estabelecimento comercial.");
		lblYourStyle.setBounds(218, 8, 125, 68);
		lblYourStyle.setAlignmentY(Component.TOP_ALIGNMENT);
		lblYourStyle.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblYourStyle.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourStyle.setIcon(new ImageIcon(Sobre.class.getResource("/br/com/YourStyle_Imagens/YourStyle.png")));
		lblYourStyle.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				dispose();
			}
		});
		lblYourStyle.setFont(new Font("SansSerif", Font.BOLD, 27));
		contentPanel.setLayout(null);
		contentPanel.add(lblIconDoSoftware);
		contentPanel.add(txtpnSobreYourStyle);
		contentPanel.add(lblJudicativo);
		contentPanel.add(lblIconempresa);
		contentPanel.add(lblYourStyle);
	}
}
