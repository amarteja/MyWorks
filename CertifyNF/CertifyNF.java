import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.*;
import java.io.*;
public class CertifyNF {
public static List<String[]> readSchema(String file) throws IOException
       {
         List<String[]> tokenList = new ArrayList<String[]>();
         Scanner line = new Scanner(new File(file));
         while(line.hasNext())
         {
             final String[] tokens = line.next().split(Pattern.quote(","));
             tokenList.add(tokens);
         }
         line.close();
         return  tokenList;
     }

   public static void main(String[] args) throws IOException {

       
	   		Properties properties = new Properties();
	   		try {
	   			properties.load(new FileInputStream("java.ini"));
	   		}
	   		catch (Exception e) {
	   			System.out.println(e);
	   		}
	   		// Load  Connection's properties from java.ini
	   		String strurl = properties.getProperty("url");
	   		String strdatabase = properties.getProperty("database");
	   		String struser= properties.getProperty("username");
	   		String strpassword = properties.getProperty("password");
	   		String connectionUrl  = strurl	+";database="+strdatabase+";username="+struser+";password="+strpassword ;
                        
                        String[] filename = args[0].split("=");

      List<String[]> tokenList = readSchema(filename[1]);
      List<String> tblnames = new ArrayList<String>();
      for(int i = 0; i< tokenList.size() ;i++)
      {
        String[] tbl = tokenList.get(i);
        String[] token1 = tbl[0].split(Pattern.quote("("));
        tblnames.add(token1[0]);
      }

      // Declare the JDBC objects.
      Connection con = null;
      Statement stmt = null;
      ResultSet rs = null;

      try {
         // Establish the connection.
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
         System.out.println("before connect");
         con = DriverManager.getConnection(connectionUrl);
         System.out.println("hello after connecting");

         // Create and execute an SQL statement that returns some data.
         for ( int i = 0; i< tblnames.size() ;i++)
         {
         String SQL = "SELECT COUNT(*) FROM " + tblnames.get(i);
         stmt = con.createStatement();
         rs = stmt.executeQuery(SQL);

         while (rs.next()) {

             int id = rs.getInt(1);

             System.out.println("For table "+tblnames.get(i)+" number of rows are " + id );
         }
         }
      }

      // Handle any errors that may have occurred.
      catch (Exception e) {
         System.out.println(e);
      }
      finally {
         if (rs != null) try { rs.close(); } catch(Exception e) {}
         if (stmt != null) try { stmt.close(); } catch(Exception e) {}
         if (con != null) try { con.close(); } catch(Exception e) {}
      }
   }
}
