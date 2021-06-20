package InventoryManagementSystem;

import java.awt.BorderLayout;
import  javax.swing.JTable;
import  javax.swing.JScrollPane;
import  javax.swing.table.DefaultTableModel;
import java.awt.Color;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import  javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class brand {

	private JPanel p1;
	private JFrame frame;

	private JMenuItem miInsert1,miUpdate1,midelete1,miView1;
	private JLabel lblbid,lblbname,lblbactive,lblbstatus;
	private JTextField txtbid,txtbname,txtbactive,txtbstatus;
	private JButton btn;
	private JTextArea txtmsg;
	//private static Statement stmt;
	private Connection connection;
	private Statement statement;
	public brand(JPanel p1,JFrame frame,JMenuItem miInsert1,JMenuItem miUpdate1,JMenuItem midelete1,JMenuItem miView1) {
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
		this.miInsert1=miInsert1;
		this.midelete1=midelete1;
		this.miUpdate1=miUpdate1;
		this.miView1=miView1;
		txtbid=new JTextField(20);
		txtbname=new JTextField(20);
		txtbactive=new JTextField(20);
		txtbstatus=new JTextField(20);
		txtmsg=new JTextArea(8,50);
		lblbid=new JLabel("Brand Id:");
		lblbname=new JLabel("Brand Name:");
		lblbactive=new JLabel("Brand active:");
		lblbstatus=new JLabel("Brand status:");
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
		miInsert1.addActionListener(new ActionListener() {	
			 public void actionPerformed(ActionEvent ae) {	
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
			//	p1.setBackground(Color.CYAN);
				JPanel p=new JPanel();
				txtbid=new JTextField(20);
				txtbname=new JTextField(20);
				txtbactive=new JTextField(20);
				txtbstatus=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				//a grid of lbl and txtfield
				p.add(lblbid);
				p.add(txtbid);
				p.add(lblbname);
				p.add(txtbname);
				p.add(lblbactive);
				p.add(txtbactive);
				p.add(lblbstatus);
				p.add(txtbstatus);
				p.setLayout(new GridLayout(4,2));
				
				p1.add(p);
				p1.add(btn);
				p1.add(txtmsg);//msg text area added to panel
				btn.setText("SUBMIT");
				p1.setLayout(new FlowLayout());
				frame.add(p1,BorderLayout.NORTH);
				frame.validate();
				
				//register listener
				btn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						try {
							Statement statement = connection.createStatement();
							String query= "INSERT INTO brand VALUES(" + txtbid.getText() + ", " + "'" + txtbname.getText() + "'," + "'" + txtbactive.getText() + "','" + txtbstatus.getText() + "')";
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
		
		miUpdate1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
			//	p1.setBackground(Color.LIGHT_GRAY);
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtbid=new JTextField(20);
				txtbname=new JTextField(20);
				txtbactive=new JTextField(20);
				txtbstatus=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				txtmsg.setEditable(false);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT bid FROM BRAND");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("bid"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lblbid);
				p.add(txtbid);
				p.add(lblbname);
				p.add(txtbname);
				p.add(lblbactive);
				p.add(txtbactive);
				p.add(lblbstatus);
				p.add(txtbstatus);
				p.setLayout(new GridLayout(4,2));
				p1.add(p);
				p1.add(btn);
				p1.add(txtmsg);
				btn.setText("MODIFY");
				p1.setLayout(new FlowLayout());
				
				frame.add(p1,BorderLayout.NORTH);
				frame.validate();
			
				idlist.addItemListener(new ItemListener()
				{
					@Override
					public void itemStateChanged(ItemEvent arg0) {
						// TODO Auto-generated method stub
				
						try 
						{
						   ResultSet rs = statement.executeQuery("SELECT * FROM BRAND where bid ="+idlist.getSelectedItem());
							rs.next();
							txtbid.setText(rs.getString("bid"));
							txtbname.setText(rs.getString("bname"));
							txtbactive.setText(rs.getString("bactive"));
							txtbstatus.setText(rs.getString("bstatus"));
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
							int i = statement.executeUpdate("UPDATE BRAND "
							+ "SET bname='" + txtbname.getText() + "', "
							+ "bactive ='" + txtbactive.getText() + "', "
							+ "bstatus ='"+ txtbstatus.getText() + "' WHERE bid = "
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


		midelete1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
			//	p1.setBackground(Color.BLUE);
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtbid=new JTextField(20);
				txtbname=new JTextField(20);
				txtbactive=new JTextField(20);
				txtbstatus=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT bid FROM BRAND");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("bid"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lblbid);
				p.add(txtbid);
				p.add(lblbname);
				p.add(txtbname);
				p.add(lblbactive);
				p.add(txtbactive);
				p.add(lblbstatus);
				p.add(txtbstatus);
				p.setLayout(new GridLayout(4,2));
				p1.add(p);
				p1.add(btn);
				p1.add(txtmsg);
				btn.setText("Delete");
				p1.setLayout(new FlowLayout());
				
				frame.add(p1,BorderLayout.NORTH);
				frame.validate();
				
				idlist.addItemListener(new ItemListener()
				{
					@Override
					public void itemStateChanged(ItemEvent arg0) {
						// TODO Auto-generated method stub
				
						try 
						{
						   ResultSet rs = statement.executeQuery("SELECT * FROM BRAND where bid ="+idlist.getSelectedItem());
							rs.next();
							txtbid.setText(rs.getString("bid"));
							txtbname.setText(rs.getString("bname"));
							txtbactive.setText(rs.getString("bactive"));
							txtbstatus.setText(rs.getString("bstatus"));
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
							int i = statement.executeUpdate("delete from BRAND where bid=" + idlist.getSelectedItem());
							txtmsg.append("\nDeleted " + i + " rows successfully");
							//idlist.removeAll();
							//loadSailors();
							txtbid.setText(null);
							txtbname.setText(null);
							txtbactive.setText(null);
							txtbstatus.setText(null);
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
		
         miView1.addActionListener(new ActionListener() {
			
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
		        S.addColumn("BID");
				S.addColumn("BName"); 
				S.addColumn("BACTIVE");
				S.addColumn("BSTATUS");
				
				try {
					ResultSet rs=statement.executeQuery("select * from BRAND");
					while(rs.next())  
						S.addRow(new  Object[]{rs.getString("bid"),rs.getString("bname"),rs.getString("bactive"),rs.getString("bstatus")});
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
