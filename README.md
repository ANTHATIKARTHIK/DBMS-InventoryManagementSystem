# DBMS-InventoryManagementSystem
# INVENTORY MANAGEMENT SYSTEM
Inventory Management System is important to ensure quality control in businesses that handle 
transactions revolving around consumer goods. Without proper inventory control, a large retail 
store may run out of stock on an important item. A good Inventory Management System will alert 
the retailer when it is time to reorder. Inventory Management System is also an important means of 
automatically tracking large shipments. For example, if a business orders ten pairs of socks for 
retail resale, but only receives nine pairs, this will be obvious upon inspecting the contents of the 
package, and error is not likely. On the other hand, say a wholesaler orders 100,000 pairs of socks 
and 10,000 are missing. Manually counting each pair of socks is likely to result in error. An 
automated Inventory Management System helps to minimize the risk of error. In retail stores, an 
Inventory Management System also helps track theft of retail merchandise, providing valuable 
information about store profits and the need for theft-prevention systems. Automated Inventory 
Management System work by scanning a barcode either on the item. A barcode scanner is used to 
read the barcode, and the information encoded by the barcode is read by the machine. This 
information is then tracked by a central computer system. For example, a purchase order may 
contain a list of items to be pulled for packing and shipping. The Inventory Management System 
can serve a variety of functions in this case. It can help a worker locate the items on the order list in 
the warehouse, it can encode shipping information like tracking numbers and delivery addresses, 
and it can remove these purchased items from the inventory tally to keep an accurate count of instock items. All of this data works in tandem to provide businesses with real-time inventory 
tracking information. Inventory Management System make it simple to locate and analyze 
inventory information in real-time with a simple database search.

# SQL:
Structure Query Language (SQL) is a database query language used for storing 
and managing data in Relational DBMS. SQL was the first commercial 
language introduced for E.F Codd's Relational model of database. Today 
almost all RDBMS (MySql, Oracle, Infomix, Sybase, MS Access) use SQL as 
the standard database query language. SQL is used to perform all types of data 
operations in RDBMS.

# List of tables:
• BRANDS
• CATEGORY
• ORDERS
• PRODUCT
• USERS
![Tables](http://url/to/img.png)

# Java-SQL Connectivity using JDBC:
Java Database Connectivity (JDBC) is an application programming
interface (API) for the programming language Java, which defines how a client 
may access a database. It is a Java-based data access technology used for Java 
database connectivity. It is part of the Java Standard Edition platform,
from Oracle Corporation. It provides methods to query and update data in a 
database and is oriented towards relational databases.
The connection to the database can be performed using Java programming 
(JDBC API) as:
try
{
}
Class.forName("oracle.jdbc.driver.OracleDriver");
catch (Exception e)
{
System.err.println("Unable to find and load driver"); 
System.exit(1);
}
public void connectToDB()
{
try
{
connection = 
DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:ORCL","karthik","vasavi");
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
Thus, the connection from Java to Oracle database is performed and therefore, 
can be used for updating tables in the database directly.
