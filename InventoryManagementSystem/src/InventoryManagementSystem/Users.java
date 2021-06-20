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

public class Users {

	private JPanel p1;
	private JFrame frame;

	private JMenuItem miInsert4,miUpdate4,midelete4,miView4;
	private JLabel lblusid,lbluname,lblpwd;
	private JTextField txtusid,txtuname,txtpwd;
	private JButton btn;
	private JTextArea txtmsg;
	//private static Statement stmt;
	private Connection connection;
	private Statement statement;
	public Users(JPanel p1,JFrame frame,JMenuItem miInsert4,JMenuItem miUpdate4,JMenuItem midelete4,JMenuItem miView4) {
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
		this.miInsert4=miInsert4;
		this.midelete4=midelete4;
		this.miUpdate4=miUpdate4;
		this.miView4=miView4;
		txtusid=new JTextField(20);
		txtuname=new JTextField(20);
		txtpwd=new JTextField(20);
		
		txtmsg=new JTextArea(8,50);
		lblusid=new JLabel("User Id:");
		lbluname=new JLabel("UserName:");
		lblpwd=new JLabel("Password:");
		
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
		miInsert4.addActionListener(new ActionListener() {	
			 public void actionPerformed(ActionEvent ae) {	
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
			//	p1.setBackground(Color.CYAN);
				JPanel p=new JPanel();
				txtusid=new JTextField(20);
				txtuname=new JTextField(20);
				txtpwd=new JTextField(20);
				
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				//a grid of lbl and txtfield
				p.add(lblusid);
				p.add(txtusid);
				p.add(lbluname);
				p.add(txtuname);
				p.add(lblpwd);
				p.add(txtpwd);
				
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
							String query= "INSERT INTO users VALUES(" + txtusid.getText() + ", " + "'" + txtuname.getText() + "'," + "'" + txtpwd.getText() + "')";
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
		
		miUpdate4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
			//	p1.setBackground(Color.LIGHT_GRAY);
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtusid=new JTextField(20);
				txtuname=new JTextField(20);
				txtpwd=new JTextField(20);
				
				txtmsg=new JTextArea(8,50);
				txtmsg.setEditable(false);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT usid FROM users");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("usid"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lblusid);
				p.add(txtusid);
				p.add(lbluname);
				p.add(txtuname);
				p.add(lblpwd);
				p.add(txtpwd);
				
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
						   ResultSet rs = statement.executeQuery("SELECT * FROM users where usid ="+idlist.getSelectedItem());
							rs.next();
							txtusid.setText(rs.getString("usid"));
							txtuname.setText(rs.getString("uname"));
							txtpwd.setText(rs.getString("pwd"));
							
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
							int i = statement.executeUpdate("UPDATE users "
							+ "SET uname='" + txtuname.getText() + "', "
							+ "pwd='"+ txtpwd.getText() + "' WHERE usid = "
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


		midelete4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
			//	p1.setBackground(Color.BLUE);
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtusid=new JTextField(20);
				txtuname=new JTextField(20);
				txtpwd=new JTextField(20);
				
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT usid FROM users");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("usid"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lblusid);
				p.add(txtusid);
				p.add(lbluname);
				p.add(txtuname);
				p.add(lblpwd);
				p.add(txtpwd);
				
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
						   ResultSet rs = statement.executeQuery("SELECT * FROM users where usid ="+idlist.getSelectedItem());
							rs.next();
							txtusid.setText(rs.getString("usid"));
							txtuname.setText(rs.getString("uname"));
							txtpwd.setText(rs.getString("pwd"));
	
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
							int i = statement.executeUpdate("delete from users where usid=" + idlist.getSelectedItem());
							txtmsg.append("\nDeleted " + i + " rows successfully");
							//idlist.removeAll();
							//loadSailors();
							txtusid.setText(null);
							txtuname.setText(null);
							txtpwd.setText(null);
				
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
		
		miView4.addActionListener(new ActionListener() {
			
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
		        S.addColumn("USID");
				S.addColumn("USERNAME"); 
				S.addColumn("PASSWORD"); 
				
				try {
					ResultSet rs=statement.executeQuery("select * from users");
					while(rs.next())  
						S.addRow(new  Object[]{rs.getString("usid"),rs.getString("uname"),rs.getString("pwd")});
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



