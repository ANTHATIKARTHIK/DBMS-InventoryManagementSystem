package InventoryManagementSystem;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ProjectUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenu mnubrand,mnuorders,mnucategory,mnuusers,mnuproduct;
	private JMenuBar mnuBar;
	private JMenuItem miInsert1,miUpdate1,midelete1,miView1;
	private JMenuItem miInsert2,miUpdate2,midelete2,miView2;
	private JMenuItem miInsert3,miUpdate3,midelete3,miView3;
	private JMenuItem miInsert4,miUpdate4,midelete4,miView4;
	private JMenuItem miInsert5,miUpdate5,midelete5,miView5;
	
	private JTextField txtField;
	
	static JPanel p1;
	
	void initialize()
	{
		//po=new JPanel();
		p1=new JPanel();
		mnubrand=new JMenu("Brand");
		mnucategory=new JMenu("Category");
		mnuorders=new JMenu("Orders");
		mnuproduct=new JMenu("Product");
		mnuusers=new JMenu("Users");
		mnuBar=new JMenuBar();
		
		miInsert1=new JMenuItem("Insert");
		miUpdate1=new JMenuItem("Update");
		midelete1=new JMenuItem("Delete");
		miView1=new JMenuItem("View");
	
		miInsert2=new JMenuItem("Insert");
		miUpdate2=new JMenuItem("Update");
		midelete2=new JMenuItem("Delete");
		miView2=new JMenuItem("View");
		
		miInsert3=new JMenuItem("Insert");
		miUpdate3=new JMenuItem("Update");
		midelete3=new JMenuItem("Delete");
		miView3=new JMenuItem("View");
		
		miInsert4=new JMenuItem("Insert");
		miUpdate4=new JMenuItem("Update");
		midelete4=new JMenuItem("Delete");
		miView4=new JMenuItem("View");
		
		miInsert5=new JMenuItem("Insert");
		miUpdate5=new JMenuItem("Update");
		midelete5=new JMenuItem("Delete");
		miView5=new JMenuItem("View");
		
		
		
		
		
		
		
		txtField = new JTextField(" 		 		 Inventory Management System  				 "); 
		txtField.setFont(new Font("Serif", Font.PLAIN, 25));
		txtField.setEditable(false);
	//	po.setBackground(Color.MAGENTA);
	}
	void addComponentsToFrame()
	{
		mnubrand.add(miInsert1);
		mnubrand.add(miUpdate1);
		mnubrand.add(midelete1);
		mnubrand.add(miView1);
		
		
		mnuorders.add(miInsert2);
		mnuorders.add(miUpdate2);
		mnuorders.add(midelete2);
		mnuorders.add(miView2);
		
		mnucategory.add(miInsert3);
		mnucategory.add(miUpdate3);
		mnucategory.add(midelete3);
		mnucategory.add(miView3);
		
		
		
		
		mnuusers.add(miInsert4);
		mnuusers.add(miUpdate4);
		mnuusers.add(midelete4);
		mnuusers.add(miView4);
		
		mnuproduct.add(miInsert5);
		mnuproduct.add(miUpdate5);
		mnuproduct.add(midelete5);
		mnuproduct.add(miView5);
		
		
		
		
		
		mnuBar.add(mnubrand);
		mnuBar.add(mnuorders);
		mnuBar.add(mnucategory);
		mnuBar.add(mnuusers);
		mnuBar.add(mnuproduct);
		
		
		setJMenuBar(mnuBar);
		
		
		p1.setLayout(new BorderLayout());
		p1.add(txtField,BorderLayout.CENTER);
		
		this.setLayout(new BorderLayout());
		add(p1,BorderLayout.CENTER);
		
	}
	/**
	 * 
	 */
	void register()
	{
		brand t1=new brand(p1,ProjectUI.this,miInsert1,miUpdate1,midelete1,miView1);
		t1.regis_inventory();
		orders t2=new orders(p1,ProjectUI.this,miInsert2,miUpdate2,midelete2,miView2);
		t2.regis_inventory();
		category t3=new category(p1,ProjectUI.this,miInsert3,miUpdate3,midelete3,miView3);
		t3.regis_inventory();
		Users t4=new Users(p1, ProjectUI.this, miInsert4, miUpdate4, midelete4, miView4);
		t4.regis_inventory();
		product t5=new product(p1,ProjectUI.this,miInsert5,miUpdate5,midelete5,miView5);
		t5.regis_inventory();
		
		
		 addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent evt) 
				{
					
						ImageIcon icon = new ImageIcon("src/t2s1.jpg");//C:\Users\DELL\Desktop\dbms\project\airlines Quality and Inf mgmt system\src
					  int a=JOptionPane.showConfirmDialog(ProjectUI.this,"Are you sure?", "This will close", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);  
					  if(a==JOptionPane.YES_OPTION)
					      ProjectUI.this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
						
				}   
			 });

		
	}
	
	
	public ProjectUI()
	{
		initialize();
		addComponentsToFrame();
		register();
		pack();
		setTitle("Inventory Management System");
		setSize(600,500);
		setVisible(true);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

