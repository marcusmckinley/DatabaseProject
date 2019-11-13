/****************************************************************/
/* GradeBook Application: grade1.java (Section 5.6)             */
/* Needs grade2.java to be compiled                             */
/* Chapter 5; Oracle Programming -- A Primer                    */
/*            by R. Sunderraman                                 */
/****************************************************************/

import java.sql.*; 
import java.io.*; 

class grade1 { 

  void print_menu() {
    System.out.println("      GRADEBOOK PROGRAM\n");
    System.out.println("(1) Display Fighter");
    System.out.println("(2) Add Fighter");
    System.out.println("(3) Add Students");
    System.out.println("(4) Select Course");
    System.out.println("(q) Quit\n");
  }

  void add_catalog(Connection conn) 
    throws SQLException, IOException {
         
    Statement stmt = conn.createStatement(); 

    String cnum   = readEntry("Course Number: ");
    String ctitle = readEntry("Course Title : ");
    String query = "insert into catalog values (" +
            "'" + cnum + "','" + ctitle + "')";
    try {
      int nrows = stmt.executeUpdate(query);
    } catch (SQLException e) {
        System.out.println("Error Adding Catalog Entry");
        while (e != null) {
          System.out.println("Message     : " + e.getMessage());
          e = e.getNextException();
        }
        return;
      }
    stmt.close();
    System.out.println("Added Catalog Entry");
  }

  void add_fighter(Connection conn) 
        throws SQLException, IOException {

    String nickname = readEntry("Nickname: ");
    String champion_status = readEntry("Champion Status (C or N): ");
    String fname = readEntry("First Name: ");
    String lname = readEntry("Last Name: ");
    String wins = readEntry("Wins: ");
    String losses = readEntry("Losses: ");
    String draw = readEntry("Draw: ");
    String no_contest = readEntry("No Contest: ");

    String query = "insert into FIGHTER values (" +
            "'" + nickname + "'," + "'" + champion_status + "'" + "," + 
            "'" + fname + "','" + lname + "'," + 
            wins + "," + losses + "," + draw + "," + no_contest + ")";

    System.out.println(query + "\n");    
    Statement stmt = conn.createStatement (); 
    try {
      stmt.executeUpdate(query);
    } catch (SQLException e) {
      System.out.println("FIGHTER was not added! Failure!");
      return;
    }
    System.out.println("FIGHTER was added! Success!");
    stmt.close();
  }

  void add_students(Connection conn) 
      throws SQLException, IOException {

    String id, ln, fn, mi;
    PreparedStatement stmt = conn.prepareStatement(
      "insert into students values (?, ?, ?, ?)"  ); 
    do {
      id = readEntry("ID (0 to stop): ");
      if (id.equals("0"))
        break;
      ln = readEntry("Last  Name    : ");
      fn = readEntry("First Name    : ");
      mi = readEntry("Middle Initial: ");
      try {
        stmt.setString(1,id);
        stmt.setString(2,fn);
        stmt.setString(3,ln);
        stmt.setString(4,mi);
        stmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println("Student was not added! Error!");
      }
    } while (true);  
    stmt.close();
  }

  void display(Connection conn) 
    throws SQLException, IOException {

    String query1 = "select *" +
                    "from FIGHTER " +
                    "where wins = 18";
    String query;
    query = query1;
     
    Statement stmt = conn.createStatement (); 
    ResultSet rset = stmt.executeQuery(query);
    System.out.println("");
    while (rset.next ()) { 
      System.out.println(rset.getString(1) + "   " +
                         rset.getString(2) + "   " +
                         rset.getString(3));
    } 
    
    grade2 g2 = new grade2();

    

  }

  //readEntry function -- to read input string
  static String readEntry(String prompt) {
     try {
       StringBuffer buffer = new StringBuffer();
       System.out.print(prompt);
       System.out.flush();
       int c = System.in.read();
       while(c != '\n' && c != -1) {
         buffer.append((char)c);
         c = System.in.read();
       }
       return buffer.toString().trim();
     } catch (IOException e) {
       return "";
       }
   }
} 