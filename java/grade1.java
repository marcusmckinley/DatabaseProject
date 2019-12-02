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
    System.out.println("");
    System.out.println("      FIGHTER PROGRAM\n");
    System.out.println("(1) Display Fighter");
    System.out.println("(2) Add Fighter");
    System.out.println("(3) Delete Fighter");
    System.out.println("(4) Update Fighter");
    System.out.println("(5) Fighter Report");
    System.out.println("(6) Coach Report");
    System.out.println("(q) Quit\n");
  }

  void coach_report(Connection conn) 
        throws SQLException, IOException {
          
    String fname = readEntry("");
    fname = readEntry("First Name: ");
    String lname = readEntry("Last Name: ");
    String id = readEntry("Id: ");
    String camp = readEntry("Camp: ");
    String expertise = readEntry("Expertise: ");

    String query = "insert into coach values (" +
            "'" + fname + "'," + 
            "'" + lname + "'," + 
            id + "," +
            "'" + camp + "'" +")";
 
    System.out.println(query);
    Statement stmt = conn.createStatement (); 
    try {
      stmt.executeUpdate(query);
    } catch (SQLException e) {
      System.out.println("COACH was not added! Failure!");
      return;
    }
    System.out.println("COACH was added! Success!");
    stmt.close();

    query = "insert into expertise values (" +
    id + "," + "'" + expertise + "'" + ")";

    System.out.println(query);
    stmt = conn.createStatement (); 
    try {
      stmt.executeUpdate(query);
    } catch (SQLException e) {
      System.out.println("EXPERTISE was not added! Failure!");
      return;
    }
    System.out.println("EXPERTISE was added! Success!");
    stmt.close();
  }

  void fighter_report(Connection conn) 
        throws SQLException, IOException {
          
    String nickname = readEntry("");
    nickname = readEntry("Fighter Nickname: ");

    String query = "select fname, lname from fighter where nickname = '" +nickname+ "'";
 
    Statement stmt = conn.createStatement (); 
    ResultSet rset = stmt.executeQuery(query);
    
    System.out.println("Fighter Nickname: " + nickname);
    System.out.print("Fighter Name: ");
    while (rset.next ()) { 
      System.out.print(rset.getString(1) + " " + rset.getString(2) + "\n");
    } 
    query = "Select state, country From fighter, fighter_camp, camp Where nickname = fnickname and name = camp_name and nickname = '" +nickname+ "'";
 
    stmt = conn.createStatement (); 
    rset = stmt.executeQuery(query);

    System.out.print("Fighting Out Of: ");
    while (rset.next ()) { 
      System.out.print(rset.getString(1) + ", " + rset.getString(2) + "\n");
    } 

    query = "select champion_status from fighter where nickname = '"+nickname+"'";
 
    stmt = conn.createStatement (); 
    rset = stmt.executeQuery(query);

    while (rset.next ()) { 
      if (rset.getString(1).indexOf("C") != -1) {
        System.out.println("Champion: Yes");
      }
      else {
        System.out.println("Champion: No");
      }
    } 

    query = "Select fc.camp_name From fighter f, fighter_camp fc Where f.nickname = fc.fnickname and f.nickname = '"+nickname+"'";
 
    stmt = conn.createStatement (); 
    rset = stmt.executeQuery(query);

    while (rset.next ()) { 
      System.out.println("Camp: " + rset.getString(1));
    } 

    query = "Select fs.style From fighter f, fighting_style fs Where f.nickname = fs.fnickname and f.nickname = '"+nickname+"'";
 
    stmt = conn.createStatement (); 
    rset = stmt.executeQuery(query);

    while (rset.next ()) { 
      System.out.println("Fighting Style: " + rset.getString(1));
    } 

    query = "Select wins, losses, draw, no_contest From fighter Where nickname = '"+nickname+"'";
 
    stmt = conn.createStatement (); 
    rset = stmt.executeQuery(query);

    while (rset.next ()) { 
      System.out.println("Record: " + rset.getString(1) + " Wins, " + rset.getString(2) + " Losses, " + rset.getString(3) + " Draw, " + rset.getString(4) + " No Contests");
    } 
  }

  void update_fighter(Connection conn) 
    throws SQLException, IOException {

    boolean done;
    String column_name = "", new_val = "", nickname = "";
    char ch;

    done = false;
    do {
      System.out.println("      What Do You Want To Update?\n");
      System.out.println("(1) Fighter Nickname");
      System.out.println("(2) Fighter First Name");
      System.out.println("(3) Fighter Last Name");
      System.out.println("(4) Fighter Wins");
      System.out.println("(5) Fighter Losses");
      System.out.println("(6) Fighter Draw");
      System.out.println("(7) Fighter No Contest");
      System.out.println("(q) Quit\n");
      System.out.print("Type in your option:");
      System.out.flush();
      ch = (char) System.in.read();
      switch (ch) {
        case '1' : 
          column_name = "nickname";
          nickname = readEntry("Fighter's current nickname: ");
          new_val = readEntry("New Nickname: ");
          done = true;
          break;
        case '2' : 
          column_name = "fname";
          nickname = readEntry("Fighter's current nickname: ");
          new_val = readEntry("New First Name: ");
          done = true;
          break;
        case '3' : 
          column_name = "lname";
          nickname = readEntry("Fighter's current nickname: ");
          new_val = readEntry("New Last Name: ");
          done = true;
          break;
        case '4' : 
          column_name = "wins";
          nickname = readEntry("Fighter's current nickname: ");
          new_val = readEntry("How many wins: ");
          done = true;
          break;
        case '5' : 
          column_name = "losses";
          nickname = readEntry("Fighter's current nickname: ");
          new_val = readEntry("How many losses: ");
          done = true;
          break;
        case '6' : 
          column_name = "draw";
          nickname = readEntry("Fighter's current nickname: ");
          new_val = readEntry("How many draws: ");
          done = true;
          break;
        case '7' : 
          column_name = "no_contest";
          nickname = readEntry("Fighter's current nickname: ");
          new_val = readEntry("How many no contests: ");
          done = true;
          break;
        case 'q' : done = true;
                   break;
        default  : System.out.println("Type in option again");
      }
    } while (!done);

    String query = "update FIGHTER set " + column_name + " = '" + new_val + "' where nickname = '" + nickname + "'";
   
    Statement stmt = conn.createStatement (); 
    try {
      stmt.executeUpdate(query);
    } catch (SQLException e) {
      System.out.println("FIGHTER was not updated! Failure!");
      return;
    }
    System.out.println("FIGHTER was updated! Success!");
    stmt.close();
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

  void delete_fighter(Connection conn) 
      throws SQLException, IOException {

      String nickname = readEntry("Nickname: ");
      

      String query = "delete from FIGHTER where nickname='" + nickname + "'";
  
      Statement stmt = conn.createStatement (); 
      try {
        stmt.executeUpdate(query);
      } catch (SQLException e) {
        System.out.println("FIGHTER was not deleted! Failure!");
        return;
      }
      System.out.println("FIGHTER was deleted! Success!");
      stmt.close();
  }

  void display(Connection conn) 
    throws SQLException, IOException {

    String query1 = "select *" +
                    "from FIGHTER ";
    String query;
    query = query1;
     
    Statement stmt = conn.createStatement (); 
    ResultSet rset = stmt.executeQuery(query);
    ResultSetMetaData rsmd = rset.getMetaData();
    int columnsNumber = rsmd.getColumnCount();

    System.out.println("");
    for (int i = 1; i < 185; i++) {
      System.out.print("-");
    }
    System.out.println("");

    for (int i = 1; i <= columnsNumber; i++) {
      String column_name = rsmd.getColumnName(i);
      System.out.print(column_name);
      for (int j = 1; j <= 22 - column_name.length(); j++) {
        System.out.print(" ");
      }
      System.out.print("|");
    }

    System.out.println("");
    for (int i = 1; i < 185; i++) {
      System.out.print("-");
    }
    System.out.println("");

    while (rset.next()) {
      for (int i = 1; i <= columnsNumber; i++) {
          String columnValue = rset.getString(i);
          System.out.print(columnValue);
          for (int j = 1; j <= 22 - columnValue.length(); j++) {
            System.out.print(" ");
          }
          System.out.print("|");
      }
      System.out.println("");
    }
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