/****************************************************************/
/* UFC Application: ufc1.java                                   */
/****************************************************************/

import java.sql.*; 
import java.io.*; 

class ufc1 { 

  
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
          Double a = 0.0;
          String id = readEntry("");
          id = readEntry("Coach ID: ");
      
          String query = "select fname, lname from coach where id = " +id+ "";
       
          Statement stmt = conn.createStatement (); 
          ResultSet rset = stmt.executeQuery(query);
          
          System.out.println("\nCOACH REPORT");
          System.out.print("Coach Name: ");
          while (rset.next ()) { 
            System.out.print(rset.getString(1) + " " + rset.getString(2) + "\n");
          } 
          query = "Select state, country From coach, camp Where name = camp_name and id = "+id+"";
       
          stmt = conn.createStatement (); 
          rset = stmt.executeQuery(query);
      
          System.out.print("Coaches In: ");
          while (rset.next ()) { 
            System.out.print(rset.getString(1) + ", " + rset.getString(2) + "\n");
          } 
      
          query = "Select count(*) from fighter f, coach c, fighter_camp fc Where f.nickname = fc.fnickname and fc.camp_name = c.camp_name and f.champion_status = 'C' and c.id = "+id;
       
          stmt = conn.createStatement (); 
          rset = stmt.executeQuery(query);
      
          while (rset.next ()) { 
            System.out.println("Number of Champions Under Coach: " + rset.getString(1));
          } 
      
          query = "Select name From coach, camp Where name = camp_name and id = "+id;
       
          stmt = conn.createStatement (); 
          rset = stmt.executeQuery(query);
      
          while (rset.next ()) { 
            System.out.println("Camp: " + rset.getString(1));
          } 
      
          query = "Select style From coach, expertise Where id = coach_id and id = "+id;
       
          stmt = conn.createStatement (); 
          rset = stmt.executeQuery(query);
      
          while (rset.next ()) { 
            System.out.println("Expertise: " + rset.getString(1));
          } 
      
          query = "select sum(wins), sum(losses), sum(draw), sum(no_contest) from fighter f, fighter_camp fc, coach c where f.nickname = fc.fnickname and fc.camp_name = c.camp_name and id = "+id;
       
          stmt = conn.createStatement (); 
          rset = stmt.executeQuery(query);
      
          while (rset.next ()) { 
            System.out.println("Record: " + rset.getString(1) + " Wins, " + rset.getString(2) + " Losses, " + rset.getString(3) + " Draw, " + rset.getString(4) + " No Contests");
          } 
      
          query = "select (sum(wins) / (sum(wins) + sum(losses))) * 100 win_percentage from fighter f, fighter_camp fc, coach c where f.nickname = fc.fnickname and fc.camp_name = c.camp_name and id = "+id;
       
          stmt = conn.createStatement (); 
          rset = stmt.executeQuery(query);
      
          while (rset.next ()) { 
            a = Double.parseDouble(rset.getString(1));
            a = Math.round(a * 100.0) / 100.0;
            System.out.println("Coach Win Percentage: " + Double.toString(a) + "%");
          } 
      
          query = "Select f.fname, f.lname from fighter f, coach c, fighter_camp fc Where f.nickname = fc.fnickname and fc.camp_name = c.camp_name and c.id = "+id;
       
          stmt = conn.createStatement (); 
          rset = stmt.executeQuery(query);
      
          System.out.println("Fighters Coached:");
          while (rset.next ()) { 
            System.out.println("  " + rset.getString(1) + " " + rset.getString(2));
          }
      
          
  }

  void fighter_report(Connection conn) 
        throws SQLException, IOException {
          
    Double a = 0.0;
    String country = "";
    String style = "";
    String nickname = readEntry("");
    nickname = readEntry("Fighter Nickname: ");

    String query = "select fname, lname from fighter where nickname = '" +nickname+ "'";
 
    Statement stmt = conn.createStatement (); 
    ResultSet rset = stmt.executeQuery(query);
    
    System.out.println("\nFIGHTER REPORT \n\nFighter Nickname: " + nickname);
    System.out.print("Fighter Name: ");
    while (rset.next ()) { 
      System.out.print(rset.getString(1) + " " + rset.getString(2) + "\n");
    } 
    query = "Select state, country From fighter, fighter_camp, camp Where nickname = fnickname and name = camp_name and nickname = '" +nickname+ "'";
 
    stmt = conn.createStatement (); 
    rset = stmt.executeQuery(query);

    System.out.print("Fighting Out Of: ");
    while (rset.next ()) { 
      country = rset.getString(2);
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
      style = rset.getString(1);
      System.out.println("Fighting Style: " + rset.getString(1));
    } 

    query = "Select wins, losses, draw, no_contest From fighter Where nickname = '"+nickname+"'";
 
    stmt = conn.createStatement (); 
    rset = stmt.executeQuery(query);

    while (rset.next ()) { 
      System.out.println("Record: " + rset.getString(1) + " Wins, " + rset.getString(2) + " Losses, " + rset.getString(3) + " Draw, " + rset.getString(4) + " No Contests");
    } 

    query = "select wins / (wins + losses) * 100 win_percentage from fighter where nickname = '"+nickname+"'";
 
    stmt = conn.createStatement (); 
    rset = stmt.executeQuery(query);

    while (rset.next ()) { 
      a = Double.parseDouble(rset.getString(1));
      a = Math.round(a * 100.0) / 100.0;
      System.out.println("Win Percentage: " + Double.toString(a) + "%");
    } 

    query = "Select c.fname, c.lname From fighter f, coach c, fighter_camp fc Where f.nickname = fc.fnickname and fc.camp_name = c.camp_name and f.nickname = '"+nickname+"'";
 
    stmt = conn.createStatement (); 
    rset = stmt.executeQuery(query);

    System.out.println("Coaches:");
    while (rset.next ()) { 
      System.out.println("  " + rset.getString(1) + " " + rset.getString(2));
    }

    query = "select (sum(wins) / (sum(wins) + sum(losses))) * 100 striker_win_percentage from fighter f, fighting_style fs where f.nickname = fs.fnickname and fs.style = '"+style+"'";
 
    stmt = conn.createStatement (); 
    rset = stmt.executeQuery(query);

    while (rset.next ()) { 
      a = Double.parseDouble(rset.getString(1));
      a = Math.round(a * 100.0) / 100.0;
      System.out.println("\nAverage "+style+" Win Percentage: " + Double.toString(a) + "%");
    } 

    query = "Select count(*) From fighter, fighting_style Where champion_status = 'C' and nickname = fnickname and style = '"+style+"'";
 
    stmt = conn.createStatement (); 
    rset = stmt.executeQuery(query);

    while (rset.next ()) { 
      System.out.println("Number of "+style+" Champions: " + rset.getString(1));
    } 

    query = "select (sum(wins) / (sum(wins) + sum(losses))) * 100 striker_win_percentage from fighter f, fighter_camp fc, camp c where c.name = fc.camp_name and fc.fnickname = f.nickname and c.country = '"+country+"'";
 
    stmt = conn.createStatement (); 
    rset = stmt.executeQuery(query);

    while (rset.next ()) { 
      a = Double.parseDouble(rset.getString(1));
      a = Math.round(a * 100.0) / 100.0;
      System.out.println("\n"+country+" Average Win Percentage: " + Double.toString(a) + "%");
    } 
  }

  void update_fighter(Connection conn) 
    throws SQLException, IOException {

    String eat = readEntry("");
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
          nickname = readEntry("");
          nickname = readEntry("Fighter's current nickname: ");
          new_val = readEntry("New Nickname: ");
          done = true;
          break;
        case '2' : 
          column_name = "fname";
          nickname = readEntry("");
          nickname = readEntry("Fighter's current nickname: ");
          new_val = readEntry("New First Name: ");
          done = true;
          break;
        case '3' : 
          column_name = "lname";
          nickname = readEntry("");
          nickname = readEntry("Fighter's current nickname: ");
          new_val = readEntry("New Last Name: ");
          done = true;
          break;
        case '4' : 
          column_name = "wins";
          nickname = readEntry("");
          nickname = readEntry("Fighter's current nickname: ");
          new_val = readEntry("How many wins: ");
          done = true;
          break;
        case '5' : 
          column_name = "losses";
          nickname = readEntry("");
          nickname = readEntry("Fighter's current nickname: ");
          new_val = readEntry("How many losses: ");
          done = true;
          break;
        case '6' : 
          column_name = "draw";
          nickname = readEntry("");
          nickname = readEntry("Fighter's current nickname: ");
          new_val = readEntry("How many draws: ");
          done = true;
          break;
        case '7' : 
          column_name = "no_contest";
          nickname = readEntry("");
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
    
    String nickname = readEntry("");
    nickname = readEntry("Nickname: ");
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

        String nickname = readEntry("");
        nickname = readEntry("Nickname: ");
      

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