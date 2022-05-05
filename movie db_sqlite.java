import java.sql.*;

public class Movies {
    private Connection connect() //connect function used to create m connection
    {
        Connection c=null;

        try
        {
            Class.forName("org.sqlite.JDBC");
            c=DriverManager.getConnection("jdbc:sqlite:movies.db");
            c.setAutoCommit(false);
        }
        catch(SQLException | ClassNotFoundException e)
        {
            System.err.println(e.getClass().getName()+":"+e.getMessage());
            System.exit(0);
        }
        return c;
    }

    public void createTable() //createTable is used to create m table in the database
    {
        String cr="CREATE TABLE MOVIES (MOVID INT PRIMARY KEY NOT NULL,"+"NAME TEXT NOT NULL,"+"ACTOR TEXT NOT NULL,"+"ACTRESS TEXT NOT NULL,"+"YEAR CHAR(4),"+"DIRNAME TEXT)";
        try
        {
            Connection c = this.connect();
            Statement stmt =c.createStatement();
            stmt.execute(cr);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void insert(int movid,String name,String actor,String actress,String year,String dir_name) //insert function is used to insert the values into the table movies
    {
        String insr ="INSERT INTO MOVIES(MOVID,NAME,ACTOR,ACTRESS,YEAR,DIRNAME) VALUES(?,?,?,?,?,?)";
        try
        {
            Connection c=this.connect();

            PreparedStatement pstmt=c.prepareStatement(insr);
            pstmt.setInt(1,mov_id);
            pstmt.setString(2,name);
            pstmt.setString(3,actor);
            pstmt.setString(4,actress);
            pstmt.setString(5,year);
            pstmt.setString(6,dir_name);
            pstmt.executeUpdate();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void selectAll() // SelectAll function used to select all the columns in the table movies
    {

        try
        {
            Connection c =this.connect();
            Statement stmt = c.createStatement();
            ResultSet rs= stmt.executeQuery("SELECT * FROM MOVIES;");
            while(rs.next())
            {
                int id=rs.getInt("MOVID");
                String movie_name=rs.getString("NAME");
                String actor=rs.getString("ACTOR");
                String actress=rs.getString("ACTRESS");
                String year=rs.getString("YEAR");
                String dir_name=rs.getString("DIRNAME");

                System.out.println("MOVIE ID = "+id);
                System.out.println("MOVIE NAME = "+movie_name);
                System.out.println("ACTOR NAME = "+actor);
                System.out.println("ACTRESS NAME = "+actress);
                System.out.println("YEAR = "+year);
                System.out.println("Director Name = "+dir_name);
                System.out.println();
            }
            rs.close();
            stmt.close();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }

    public void selectparam() //selectparam is used to select the movie name based on the actor's name
    {

        try
        {
            Connection c =this.connect();
            Statement stmt = c.createStatement();
            ResultSet rs= stmt.executeQuery("SELECT NAME FROM MOVIES WHERE ACTOR='Tom Cruise';");
            System.out.println("The movies in which Tom Cruise acted are: ");
            while(rs.next())
            {
                String movie_name=rs.getString("NAME");
                System.out.println(movie_name);
            }
            rs.close();
            stmt.close();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }


    public static void main(String[] args) {
        Movies m =new Movies(); //creating an object of the class Movies
        m.createTable();
        m.insert(101,"RRR","JR.NTR","ALIA BHATT","2022","RAJAMOULI");
        m.insert(102,"BAAHUBALI","PRABHAS","ANUSHKA","2015","RAJAMOULI");
        m.insert(103,"The Pursuit of Happyness","Will Smith","Thandiwe Newton","2006","Gabriele Muccino");
        m.insert(104,"Mission: Impossible 2","Tom Cruise","Thandiwe Newton","2000","John Woo");
        m.insert(105,"K.G.F: Chapter 2","Yash","Srinidhi Shetty","2022","Prashanth Neel");
        m.insert(106,"Top Gun","Tom Cruise","Kelly McGillis","1986","Tony Scott");
        m.insert(107,"The Mummy","Tom Cruise","Sofia Boutella","2017","Alex Kurtzman");
        m.selectAll();
        m.selectparam();
    }
}