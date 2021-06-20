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

public class category {

	private JPanel p1;
	private JFrame frame;

	private JMenuItem miInsert3,miUpdate3,midelete3,miView3;
	private JLabel lblcid,lblcname,lblcactive,lblcstatus;
	private JTextField txtcid,txtcname,txtcactive,txtcstatus;
	private JButton btn;
	private JTextArea txtmsg;
	//private static Statement stmt;
	private Connection connection;
	private Statement statement;
	public category(JPanel p1,JFrame frame,JMenuItem miInsert3,JMenuItem miUpdate3,JMenuItem midelete3,JMenuItem miView3) {
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
		this.miInsert3=miInsert3;
		this.midelete3=midelete3;
		this.miUpdate3=miUpdate3;
		this.miView3=miView3;
		txtcid=new JTextField(20);
		txtcname=new JTextField(20);
		txtcactive=new JTextField(20);
		txtcstatus=new JTextField(20);
		txtmsg=new JTextArea(8,50);
		lblcid=new JLabel("Category Id:");
		lblcname=new JLabel("category Name:");
		lblcactive=new JLabel("category active:");
		lblcstatus=new JLabel("Category status:");
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
		miInsert3.addActionListener(new ActionListener() {	
			 public void actionPerformed(ActionEvent ae) {	
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
			//	p1.setBackground(Color.CYAN);
				JPanel p=new JPanel();
				txtcid=new JTextField(20);
				txtcname=new JTextField(20);
				txtcactive=new JTextField(20);
				txtcstatus=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				//a grid of lbl and txtfield
				p.add(lblcid);
				p.add(txtcid);
				p.add(lblcname);
				p.add(txtcname);
				p.add(lblcactive);
				p.add(txtcactive);
				p.add(lblcstatus);
				p.add(txtcstatus);
				p.setLayout(new GridLayout(4,2));
				
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
							String query= "INSERT INTO category VALUES(" + txtcid.getText() + ", " + "'" + txtcname.getText() + "'," + "'" + txtcactive.getText() + "','" + txtcstatus.getText() + "')";
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
		
		miUpdate3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
			//	p1.setBackground(Color.LIGHT_GRAY);
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtcid=new JTextField(20);
				txtcname=new JTextField(20);
				txtcactive=new JTextField(20);
				txtcstatus=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				txtmsg.setEditable(false);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT cid FROM category");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("cid"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lblcid);
				p.add(txtcid);
				p.add(lblcname);
				p.add(txtcname);
				p.add(lblcactive);
				p.add(txtcactive);
				p.add(lblcstatus);
				p.add(txtcstatus);
				p.setLayout(new GridLayout(4,2));
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
						   ResultSet rs = statement.executeQuery("SELECT * FROM category where cid ="+idlist.getSelectedItem());
							rs.next();
							txtcid.setText(rs.getString("cid"));
							txtcname.setText(rs.getString("cname"));
							txtcactive.setText(rs.getString("cactive"));
							txtcstatus.setText(rs.getString("cstatus"));
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
							int i = statement.executeUpdate("UPDATE category "
							+ "SET cname='" + txtcname.getText() + "', "
							+ "cactive ='" + txtcactive.getText() + "', "
							+ "cstatus ='"+ txtcstatus.getText() + "' WHERE cid = "
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


		midelete3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
			//	p1.setBackground(Color.BLUE);
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtcid=new JTextField(20);
				txtcname=new JTextField(20);
				txtcactive=new JTextField(20);
				txtcstatus=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT cid FROM category");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("cid"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lblcid);
				p.add(txtcid);
				p.add(lblcname);
				p.add(txtcname);
				p.add(lblcactive);
				p.add(txtcactive);
				p.add(lblcstatus);
				p.add(txtcstatus);
				p.setLayout(new GridLayout(4,2));
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
						   ResultSet rs = statement.executeQuery("SELECT * FROM category where cid ="+idlist.getSelectedItem());
							rs.next();
							txtcid.setText(rs.getString("cid"));
							txtcname.setText(rs.getString("cname"));
							txtcactive.setText(rs.getString("cactive"));
							txtcstatus.setText(rs.getString("cstatus"));
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
							int i = statement.executeUpdate("delete from category where cid=" + idlist.getSelectedItem());
							txtmsg.append("\nDeleted " + i + " rows successfully");
							//idlist.removeAll();
							//loadSailors();
							txtcid.setText(null);
							txtcname.setText(null);
							txtcactive.setText(null);
							txtcstatus.setText(null);
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
		
		miView3.addActionListener(new ActionListener() {
			
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
		        S.addColumn("CID");
				S.addColumn("CName"); 
				S.addColumn("CACTIVE");
				S.addColumn("CSTATUS");
				
				try {
					ResultSet rs=statement.executeQuery("select * from category");
					while(rs.next())  
						S.addRow(new  Object[]{rs.getString("cid"),rs.getString("cname"),rs.getString("cactive"),rs.getString("cstatus")});
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



