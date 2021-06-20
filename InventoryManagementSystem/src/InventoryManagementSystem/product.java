package InventoryManagementSystem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class product {

	private JPanel p1;
	private JFrame frame;

	private JMenuItem miInsert5,miUpdate5,midelete5,miView5;
	private JLabel lblpid,lblpname,lblquantity,lblstatus,lblrate;
	private JTextField txtpid,txtpname,txtquantity,txtstatus,txtrate;
	private JButton btn;
	private JTextArea txtmsg;
	//private static Statement stmt;
	private Connection connection;
	private Statement statement;
	String s1;
	String s2;
	public product(JPanel p1,JFrame frame,JMenuItem miInsert5,JMenuItem miUpdate5,JMenuItem midelete5,JMenuItem miView5) {
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} 
		catch (Exception e) 
		{
			System.err.println("Unable to find and load driver");
			System.exit(1);
		}
		connectToDB();
		this.frame=frame;
		this.p1=p1;
		this.miInsert5=miInsert5;
		this.midelete5=midelete5;
		this.miUpdate5=miUpdate5;
		this.miView5=miView5;
		txtpid=new JTextField(20);
		txtpname=new JTextField(20);
		txtquantity=new JTextField(20);
		txtstatus=new JTextField(20);
		txtrate=new JTextField(20);
		txtmsg=new JTextArea(8,50);
		lblpid=new JLabel("Product Id:");
		lblpname=new JLabel("Product name:");
		lblquantity=new JLabel("Quantity:");
		lblrate = new JLabel("Rate:");
		lblstatus=new JLabel("Status:");
		//queryHandler();
	}
	public void connectToDB() 
    {
		try 
		{
		  connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","karthik","vasavi");
		  statement = connection.createStatement();

		} 
		catch (SQLException connectException) 
		{
		  System.out.println(connectException.getMessage());
		  System.out.println(connectException.getSQLState());
		  System.out.println(connectException.getErrorCode());
		  System.exit(1);
		}
    }
	public void regis_inventory() {
		
		miInsert5.addActionListener(new ActionListener() {	
			 public void actionPerformed(ActionEvent ae) {	
				 
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				
				JPanel p=new JPanel();
				txtpid=new JTextField(20);
				txtpname=new JTextField(20);
				txtquantity=new JTextField(20);
				txtstatus=new JTextField(20);
				txtrate=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				//a grid of lbl and txtfield
				p.add(lblpid);
				p.add(txtpid);
				p.add(lblpname);
				p.add(txtpname);
				p.add(lblquantity);
				p.add(txtquantity);
				p.add(lblrate);
				p.add(txtrate);
				p.add(lblstatus);
				p.add(txtstatus);
				p.setLayout(new GridLayout(6,2));
				
				p1.add(p);
				p1.add(btn);
				p1.add(txtmsg);//msg text area added to panel
				btn.setText("SUBMIT");
				p1.setLayout(new FlowLayout());
				frame.add(p1,BorderLayout.CENTER);
				frame.validate();
				
				//register listener
				btn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						try {
							Statement statement = connection.createStatement();
							String query= "INSERT INTO product VALUES(" + txtpid.getText() + ",'"  + txtpname.getText()  + "','" + txtquantity.getText() +"','" + txtrate.getText() + "','" + txtstatus.getText() + "')";
							int i = statement.executeUpdate(query);
								txtmsg.append("\nInserted " + i + " rows successfully");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
						//	e1.printStackTrace();
							txtmsg.append(e1.getMessage());
						}  
					}
				});
				
			}
		});
		
		miUpdate5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
			//	p1.setBackground(Color.LIGHT_GRAY);
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtpid=new JTextField(20);
				txtpname=new JTextField(20);
				txtquantity=new JTextField(20);
				txtrate=new JTextField(20);
				txtstatus=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				txtmsg.setEditable(false);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT pid FROM product");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("pid"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lblpid);
				p.add(txtpid);
				p.add(lblpname);
				p.add(txtpname);
				p.add(lblquantity);
				p.add(txtquantity);
				p.add(lblrate);
				p.add(txtrate);
				p.add(lblstatus);
				p.add(txtstatus);
				p.setLayout(new GridLayout(6,2));
				p1.add(p);
				p1.add(btn);
				p1.add(txtmsg);
				btn.setText("MODIFY");
				p1.setLayout(new FlowLayout());
				
				frame.add(p1,BorderLayout.CENTER);
				frame.validate();
			
				idlist.addItemListener(new ItemListener()
				{
					@Override
					public void itemStateChanged(ItemEvent arg0) {
						// TODO Auto-generated method stub
				
						try 
						{
						   ResultSet rs = statement.executeQuery("SELECT * FROM product where pid ="+idlist.getSelectedItem());
							rs.next();
							txtpid.setText(rs.getString("pid"));
							txtpname.setText(rs.getString("pname"));
							txtquantity.setText(rs.getString("quantity"));
							txtrate.setText(rs.getString("rate"));
							txtstatus.setText(rs.getString("status"));
						} 
						catch (SQLException selectException) 
						{
							txtmsg.append(selectException.getMessage());
						}
					}
				});		
				
				
				btn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						try 
						{
							Statement statement = connection.createStatement();
							int i = statement.executeUpdate("UPDATE product "
							+ "SET pname='" + txtpname.getText() + "', "
							+ "quantity='" + txtquantity.getText() + "', "
							+ "rate ='" + txtrate.getText() + "', "
							+ "status ='"+ txtstatus.getText() + "' WHERE pid = "
							+ idlist.getSelectedItem());
							txtmsg.append("\nUpdated " + i + " rows successfully");
							//idlist.removeAll();
							//loadSailors();
						} 
						catch (SQLException insertException) 
						{
							txtmsg.append(insertException.getMessage());
						}
						
					}
				});
			}
		});


		midelete5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
			//	p1.setBackground(Color.BLUE);
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtpid=new JTextField(20);
				txtpname=new JTextField(20);
				txtquantity=new JTextField(20);
				txtrate=new JTextField(20);
				txtstatus=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT pid FROM product");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("pid"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lblpid);
				p.add(txtpid);
				p.add(lblpname);
				p.add(txtpname);
				p.add(lblquantity);
				p.add(txtquantity);
				p.add(lblrate);
				p.add(txtrate);
				p.add(lblstatus);
				p.add(txtstatus);
				p.setLayout(new GridLayout(6,2));
				p1.add(p);
				p1.add(btn);
				p1.add(txtmsg);
				btn.setText("Delete");
				p1.setLayout(new FlowLayout());
				
				frame.add(p1,BorderLayout.CENTER);
				frame.validate();
				
				idlist.addItemListener(new ItemListener()
				{
					@Override
					public void itemStateChanged(ItemEvent arg0) {
						// TODO Auto-generated method stub
				
						try 
						{
						   ResultSet rs = statement.executeQuery("SELECT * FROM product where pid ="+idlist.getSelectedItem());
							rs.next();
							txtpid.setText(rs.getString("pid"));
							txtpname.setText(rs.getString("pname"));
							txtquantity.setText(rs.getString("quantity"));
							txtrate.setText(rs.getString("rate"));
							txtstatus.setText(rs.getString("status"));
						} 
						catch (SQLException selectException) 
						{
							txtmsg.append(selectException.getMessage());
						}
					}
				});		
				
				
				btn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						try 
						{
							Statement statement = connection.createStatement();
							int i = statement.executeUpdate("delete from product where pid=" + idlist.getSelectedItem());
							txtmsg.append("\nDeleted " + i + " rows successfully");
							//idlist.removeAll();
							//loadSailors();
							txtpid.setText(null);
							txtpname.setText(null);
							txtquantity.setText(null);
							txtrate.setText(null);
							txtstatus.setText(null);
							idlist.removeAll();
							
						} 
						catch (SQLException insertException) 
						{
							txtmsg.append(insertException.getMessage());
						}
						
					}
				});
			}
		});
		
		miView5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				//p1.add(txtmsg);
				frame.add(p1,BorderLayout.NORTH);
				frame.validate();
				JTable  j;
				DefaultTableModel  S  =  new  DefaultTableModel();
				j  =  new  JTable(S);
		        S.addColumn("PID");
				S.addColumn("PName"); 
				S.addColumn("QUANTITY");
				S.addColumn("RATE");
				S.addColumn("STATUS"); 
				
				try {
					ResultSet rs=statement.executeQuery("select * from product");
					while(rs.next())  
						S.addRow(new  Object[]{rs.getString("pid"),rs.getString("pname"),rs.getString("quantity"),rs.getString("rate"),rs.getString("status")});
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame,"Something Went Wrong");
				}  
				JScrollPane  sp  =  new  JScrollPane(j);
				 
				p1.add(sp,BorderLayout.NORTH);
				frame.invalidate();
				frame.validate();
				frame.repaint();
				
			}
		});
	}

}
