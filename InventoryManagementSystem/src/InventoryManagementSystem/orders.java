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

public class orders {

	private JPanel p1;
	private JFrame frame;

	private JMenuItem miInsert2,miUpdate2,midelete2,miView2;
	private JLabel lbloid,lblpname,lblday,lblbill,lblnum;
	private JTextField txtoid,txtpname,txtday,txtbill,txtnum;
	private JButton btn;
	private JTextArea txtmsg;
	//private static Statement stmt;
	private Connection connection;
	private Statement statement;
	String s1;
	String s2;
	public orders(JPanel p1,JFrame frame,JMenuItem miInsert2,JMenuItem miUpdate2,JMenuItem midelete2,JMenuItem miView2) {
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
		this.miInsert2=miInsert2;
		this.midelete2=midelete2;
		this.miUpdate2=miUpdate2;
		this.miView2=miView2;
		txtoid=new JTextField(20);
		txtpname=new JTextField(20);
		txtday=new JTextField(20);
		txtbill=new JTextField(20);
		txtnum=new JTextField(20);
		txtmsg=new JTextArea(8,50);
		lbloid=new JLabel("Order Id:");
		lblpname=new JLabel("Product name:");
		lblday=new JLabel("Date:");
		lblnum = new JLabel("No. of products");
		lblbill=new JLabel("Total Amount");
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
		
		miInsert2.addActionListener(new ActionListener() {	
			 public void actionPerformed(ActionEvent ae) {	
				 
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				
				JPanel p=new JPanel();
				txtoid=new JTextField(20);
				txtpname=new JTextField(20);
				txtday=new JTextField(20);
				txtbill=new JTextField(20);
				txtnum=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				//a grid of lbl and txtfield
				p.add(lbloid);
				p.add(txtoid);
				p.add(lblpname);
				p.add(txtpname);
				p.add(lblday);
				p.add(txtday);
				p.add(lblnum);
				p.add(txtnum);
				p.add(lblbill);
				p.add(txtbill);
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
							String query= "INSERT INTO orders VALUES(" + txtoid.getText() + ",'"  + txtpname.getText()  + "','" + txtday.getText() +"'," + txtnum.getText() + "," + txtbill.getText() + ")";
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
		
		miUpdate2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
			//	p1.setBackground(Color.LIGHT_GRAY);
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtoid=new JTextField(20);
				txtpname=new JTextField(20);
				txtday=new JTextField(20);
				txtnum=new JTextField(20);
				txtbill=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				txtmsg.setEditable(false);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT oid FROM orders");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("oid"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lbloid);
				p.add(txtoid);
				p.add(lblpname);
				p.add(txtpname);
				p.add(lblday);
				p.add(txtday);
				p.add(lblnum);
				p.add(txtnum);
				p.add(lblbill);
				p.add(txtbill);
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
						   ResultSet rs = statement.executeQuery("SELECT * FROM orders where oid ="+idlist.getSelectedItem());
							rs.next();
							txtoid.setText(rs.getString("oid"));
							txtpname.setText(rs.getString("pname"));
							txtday.setText(rs.getString("odate"));
							txtnum.setText(rs.getString("nop"));
							txtbill.setText(rs.getString("total"));
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
							int i = statement.executeUpdate("UPDATE orders "
							+ "SET pname='" + txtpname.getText() + "', "
							+ "odate ='" + txtday.getText() + "', "
							+ "nop ='" + txtnum.getText() + "', "
							+ "total ='"+ txtbill.getText() + "' WHERE oid = "
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




		midelete2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
			//	p1.setBackground(Color.BLUE);
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtoid=new JTextField(20);
				txtpname=new JTextField(20);
				txtday=new JTextField(20);
				txtnum=new JTextField(20);
				txtbill=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT oid FROM orders");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("oid"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lbloid);
				p.add(txtoid);
				p.add(lblpname);
				p.add(txtpname);
				p.add(lblday);
				p.add(txtday);
				p.add(lblnum);
				p.add(txtnum);
				p.add(lblbill);
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
						   ResultSet rs = statement.executeQuery("SELECT * FROM orders where oid ="+idlist.getSelectedItem());
							rs.next();
							txtoid.setText(rs.getString("oid"));
							txtpname.setText(rs.getString("pname"));
							txtday.setText(rs.getString("odate"));
							txtnum.setText(rs.getString("nop"));
							txtbill.setText(rs.getString("total"));
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
							int i = statement.executeUpdate("delete from orders where oid=" + idlist.getSelectedItem());
							txtmsg.append("\nDeleted " + i + " rows successfully");
							//idlist.removeAll();
							//loadSailors();
							txtoid.setText(null);
							txtpname.setText(null);
							txtday.setText(null);
							txtnum.setText(null);
							txtbill.setText(null);
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
		
		miView2.addActionListener(new ActionListener() {
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
		        S.addColumn("OID");
				S.addColumn("PName"); 
				S.addColumn("ORDER DATE");
				S.addColumn("NO OF PRODUCT");
				S.addColumn("TOTAL AMOUNT"); 
				
				try {
					ResultSet rs=statement.executeQuery("select * from ORDERS");
					while(rs.next())  
						S.addRow(new  Object[]{rs.getString("oid"),rs.getString("pname"),rs.getString("odate"),rs.getString("nop"),rs.getString("total")});
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

