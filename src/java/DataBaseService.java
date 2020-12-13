
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author darsh
 */

public class DataBaseService implements IDataBaseOperation {

    static final String url = "jdbc:mysql://mis-sql.uhcl.edu/shuklaa4553?useSSL=false";
    static final String db_id = "shuklaa4553";
    static final String db_password = "1673380";

    // These are import varibles for database operations
    static Connection _Connection = null;
    static Statement _Statement = null;
    static ResultSet _ResultSet = null;

    private int CommentCount = 0;

    public int getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(int CommentCount) {
        this.CommentCount = CommentCount;
    }
    
    @Override
    public String insertUserAccount(String id, String password, String name) {
        try {
            _Connection = DriverManager.getConnection(url, db_id, db_password);
            _Statement = _Connection.createStatement();

            int r = _Statement.executeUpdate("insert into account values ('" + id + "','" + password + "','" + name + "','NOM')");

            System.err.println("Your account is created!!!");
        } catch (SQLException e) {
            System.err.println("Error occured in insertUserAccount");
            e.printStackTrace();
        } finally {
            try {
                _Connection.close();
                _Statement.close();
                _ResultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String loginVerification(String id, String password) {

        try {
            _Connection = DriverManager.getConnection(url, db_id, db_password);
            _Statement = _Connection.createStatement();

            _ResultSet = _Statement.executeQuery("select * from account "
                    + "where account_id = '" + id + "' AND password = '" + password + "'");

            if (_ResultSet.next()) {
                if (_ResultSet.getString("acc_type").equals("editor") || _ResultSet.getString("acc_type").equals("EDI")) {
                    return "editor_front_page";
                }
                return "FrontPage";
            } else {
                return "LoginFailed";
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Error occured in loginVarification");
            e.printStackTrace();
        } 
        finally 
        {
            try 
            {
                _Connection.close();
                _Statement.close();
                _ResultSet.close();
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String FetchLoggedInUserName(String id, String password) {
        try {
            ArrayList<String> userData = new ArrayList<>();
            _Connection = DriverManager.getConnection(url, db_id, db_password);
            _Statement = _Connection.createStatement();

            _ResultSet = _Statement.executeQuery("select * from account "
                    + "where account_id = '" + id + "' AND password = '" + password + "'");

            if (_ResultSet.next()) {
                String str = _ResultSet.getString("name") + "," + _ResultSet.getString("acc_type");
//                _ResultSet.getString("acc_type");
                return str;
            }

        } catch (SQLException e) {
            System.err.println("Error occured in FetchLoggedInUserName");
            e.printStackTrace();
        } finally {
            try {
                _Connection.close();
                _Statement.close();
                _ResultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void dealCreation(String title, String category, String content, String deal_creator_name, String account_id, String price, int rating, String deal_creation_date) {
        try {
            DateAndTime TheDate = new DateAndTime();
            deal_creation_date = TheDate.DateTime();
            int deal_id = 0;
            _Connection = DriverManager.getConnection(url, db_id, db_password);
            _Statement = _Connection.createStatement();
            _ResultSet = _Statement.executeQuery("SELECT deal_id from thread ORDER BY deal_id DESC LIMIT 1");

            if (_ResultSet.next()) {
                deal_id = Integer.parseInt(_ResultSet.getString("deal_id"));
                deal_id++;
            } //When you create thread for first time at that time thread id should be 1.
            else {
                deal_id = 1;
            }
            int i = _Statement.executeUpdate("INSERT INTO thread VALUES('" + deal_id + "','" + title + "','" + content + "'"
                    + ",'" + category + "','" + deal_creator_name + "','" + account_id + "','" + price + "','" + rating + "','" + deal_creation_date + "')");

        } catch (SQLException e) {
            System.err.println("Error occured in dealCreation");
            e.printStackTrace();
        } finally {
            try {
                _Connection.close();
                _Statement.close();
                _ResultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public List<Thread> getAllThread_ID_Title_and_Price() {
        try {
            List<Thread> allThread = new ArrayList<>();

            _Connection = DriverManager.getConnection(url, db_id, db_password);
            _Statement = _Connection.createStatement();
            _ResultSet = _Statement.executeQuery("SELECT * from thread");

            while (_ResultSet.next()) {
                Thread obj = new Thread(
                        (Integer.parseInt(_ResultSet.getString("deal_id"))),
                        _ResultSet.getString("title"), _ResultSet.getString("category"),
                        _ResultSet.getString("content"), _ResultSet.getString("deal_creator_name"),
                        _ResultSet.getString("account_id"), _ResultSet.getString("price"),
                        (Integer.parseInt(_ResultSet.getString("rating"))), _ResultSet.getString("deal_creation_date")
                );

                allThread.add(obj);
            }

            return allThread;

        } catch (SQLException e) {
            System.err.println("Error occured in getAllThread");
            e.printStackTrace();
        } finally {
            try {
                _Connection.close();
                _Statement.close();
                _ResultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public int thread_Like(int rating, int deal_id, String LoggedIn_user_id) {
        try {

            _Connection = DriverManager.getConnection(url, db_id, db_password);
            _Statement = _Connection.createStatement();
            _ResultSet = _Statement.executeQuery("SELECT * FROM rating WHERE deal_id = '" + deal_id + "' AND account_id = '" + LoggedIn_user_id + "'");

            String Dislike;

            if (_ResultSet.next()) {
                //Data Entry is present in rating table. i.e. If some loggined user has already liked or disliked the post.
                String like = _ResultSet.getString("like_status");
                if (like.equals("Yes")) {
                    return rating;
                } else {
                    rating++;
                    int UpdateRating = _Statement.executeUpdate("UPDATE rating SET like_status = 'Yes' WHERE deal_id = '" + deal_id + "' "
                            + "AND account_id = '" + LoggedIn_user_id + "'");

                    int updateThread = _Statement.executeUpdate("UPDATE thread SET rating = '" + rating + "' "
                            + "WHERE deal_id = '" + deal_id + "'");
                    return rating;
                }
            } else {
                //Data entry not present in rating table. i.e Deal doesn't have any like or dislike till now.
                Dislike = "No";
                rating++;
                int i = _Statement.executeUpdate("INSERT INTO rating VALUES ('" + deal_id + "','" + LoggedIn_user_id + "','Yes','" + Dislike + "')");

                int u = _Statement.executeUpdate("UPDATE thread SET rating = '" + rating + "' "
                        + "WHERE deal_id = '" + deal_id + "'");
                return rating;
            }

        } catch (SQLException e) {
            System.err.println("Error occured in thread_Like");
            e.printStackTrace();
        } finally {
            try {
                _Connection.close();
                _Statement.close();
                _ResultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rating;
    }

    @Override
    public int Get_current_thread_Like(int deal_id) {
        try {

            _Connection = DriverManager.getConnection(url, db_id, db_password);
            _Statement = _Connection.createStatement();
            _ResultSet = _Statement.executeQuery("SELECT * FROM thread WHERE deal_id = '" + deal_id + "'");

            if (_ResultSet.next()) {
                return Integer.parseInt(_ResultSet.getString("rating"));
            }

        } catch (SQLException e) {
            System.err.println("Error occured in Get_current_thread_Like");
            e.printStackTrace();
        } finally {
            try {
                _Connection.close();
                _Statement.close();
                _ResultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public int thread_Dislike(int rating, int deal_id, String LoggedIn_user_id) {
        try {

            _Connection = DriverManager.getConnection(url, db_id, db_password);
            _Statement = _Connection.createStatement();
            _ResultSet = _Statement.executeQuery("SELECT * FROM rating WHERE deal_id = '" + deal_id + "' AND account_id = '" + LoggedIn_user_id + "'");

            String like;
            if (_ResultSet.next()) {
                String dislike = _ResultSet.getString("dislike_status");
                if (dislike.equals("Yes")) {
                    return rating;
                } else {
                    rating--;
                    int UpdateRating = _Statement.executeUpdate("UPDATE rating SET dislike_status = 'Yes' WHERE deal_id = '" + deal_id + "' "
                            + "AND account_id = '" + LoggedIn_user_id + "'");

                    int updateThread = _Statement.executeUpdate("UPDATE thread SET rating = '" + rating + "' "
                            + "WHERE deal_id = '" + deal_id + "'");

                    return rating;
                }
            } else {
                like = "No";
                rating--;

                int i = _Statement.executeUpdate("INSERT INTO rating VALUES ('" + deal_id + "','" + LoggedIn_user_id + "','" + like + "','Yes')");

                int u = _Statement.executeUpdate("UPDATE thread SET rating = '" + rating + "' "
                        + "WHERE deal_id = '" + deal_id + "'");
                return rating;
            }

        } catch (SQLException e) {
            System.err.println("Error occured in thread_Dislike");
            e.printStackTrace();
        } finally {
            try {
                _Connection.close();
                _Statement.close();
                _ResultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rating;
    }

    public ArrayList<String> Get_thread_Date(int deal_id) {
        try {
            ArrayList<String> thread_rating_date = new ArrayList<>();
            _Connection = DriverManager.getConnection(url, db_id, db_password);
            _Statement = _Connection.createStatement();
            _ResultSet = _Statement.executeQuery("SELECT * FROM thread WHERE deal_id = '" + deal_id + "'");

            if (_ResultSet.next()) {
                thread_rating_date.add(_ResultSet.getString("rating"));
                thread_rating_date.add(_ResultSet.getString("deal_creation_date"));
                return thread_rating_date;
            }

        } catch (SQLException e) {
            System.err.println("Error occured in Get_thread_Date");
            e.printStackTrace();
        } finally {
            try {
                _Connection.close();
                _Statement.close();
                _ResultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<Thread> getThreadObjectForEditor() {
        try {
            List<Thread> allThread = new ArrayList<>();

            _Connection = DriverManager.getConnection(url, db_id, db_password);
            _Statement = _Connection.createStatement();
            _ResultSet = _Statement.executeQuery("SELECT * FROM thread "
                    + "WHERE rating >= '2' "
                    + "AND account_id NOT IN (SELECT editor_id FROM best_deal) "
                    + "AND deal_id NOT IN (SELECT deal_id FROM best_deal)");

            while (_ResultSet.next()) {
                Thread obj = new Thread(
                        Integer.parseInt(_ResultSet.getString("deal_id")),
                        _ResultSet.getString("title"), _ResultSet.getString("category"),
                        _ResultSet.getString("content"), _ResultSet.getString("deal_creator_name"),
                        _ResultSet.getString("account_id"), _ResultSet.getString("price"),
                        Integer.parseInt(_ResultSet.getString("rating")), _ResultSet.getString("deal_creation_date")
                );
                allThread.add(obj);
            }

            return allThread;

        } catch (SQLException e) {
            System.err.println("Error occured in getThreadObjectForEditor");
            e.printStackTrace();
        } finally {
            try {
                _Connection.close();
                _Statement.close();
                _ResultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String insertBestDeal(String editor_id, int deal_id) {
        try {

            _Connection = DriverManager.getConnection(url, db_id, db_password);
            _Statement = _Connection.createStatement();

            int insert_in_bestdeal = _Statement.executeUpdate("INSERT INTO best_deal VALUES('" + editor_id + "','" + deal_id + "')");
            return "editor_front_page";
        } catch (SQLException e) {
            System.err.println("Error occured in insertBestDeal");
            e.printStackTrace();
        } finally {
            try {
                _Connection.close();
                _Statement.close();
                _ResultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    @Override
    public List<Thread> frontPagedeals() {
        try {
            List<Thread> allThread = new ArrayList<>();

            _Connection = DriverManager.getConnection(url, db_id, db_password);
            _Statement = _Connection.createStatement();
            _ResultSet = _Statement.executeQuery("SELECT * FROM thread "
                    + "WHERE deal_id IN (SELECT deal_id FROM best_deal) "
                    + "ORDER BY deal_creation_date DESC");
            

            while (_ResultSet.next()) {
                Thread obj = new Thread(
                        Integer.parseInt(_ResultSet.getString("deal_id")),
                        _ResultSet.getString("title"), _ResultSet.getString("category"),
                        _ResultSet.getString("content"), _ResultSet.getString("deal_creator_name"),
                        _ResultSet.getString("account_id"), _ResultSet.getString("price"),
                        Integer.parseInt(_ResultSet.getString("rating")), _ResultSet.getString("deal_creation_date")
                );
                allThread.add(obj);
            }

            return allThread;

        } catch (SQLException e) {
            System.err.println("Error occured in frontPagedeals");
            e.printStackTrace();
        } finally {
            try {
                _Connection.close();
                _Statement.close();
                _ResultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void userComments(int deal_id, String user_id, String username, String comment_text, String comment_date) 
    {
        try {
            DateAndTime TheDate = new DateAndTime();
            comment_date = TheDate.DateTime();
            _Connection = DriverManager.getConnection(url, db_id, db_password);
            _Statement = _Connection.createStatement();

            int insert_in_comment_sd = _Statement.executeUpdate("INSERT INTO comment_sd VALUES('"+deal_id+"','"+user_id+"','"+username+"',"
                    + "'"+comment_text+"','"+comment_date+"')");
            
        } catch (SQLException e) {
            System.err.println("Error occured in userComments");
            e.printStackTrace();
        } finally {
            try {
                _Connection.close();
                _Statement.close();
                _ResultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }

    @Override
    public List<Comment> deal_comment_object(int deal_id) {
        try {
            CommentCount = 0;
            List<Comment> commentObject = new ArrayList<>();

            _Connection = DriverManager.getConnection(url, db_id, db_password);
            _Statement = _Connection.createStatement();
            _ResultSet = _Statement.executeQuery("SELECT * FROM comment_sd "
                    + "WHERE deal_id = '"+deal_id+"'");

            while (_ResultSet.next()) {
                Comment obj = new Comment
                (
                    Integer.parseInt(_ResultSet.getString("deal_id")),_ResultSet.getString("user_id"),
                    _ResultSet.getString("user_name"),_ResultSet.getString("comment_text"),
                    _ResultSet.getString("comment_date")    
                );
                commentObject.add(obj);
                CommentCount++;
            }

            return commentObject;

        } catch (SQLException e) {
            System.err.println("Error occured in deal_comment_object");
            e.printStackTrace();
        } finally {
            try {
                _Connection.close();
                _Statement.close();
                _ResultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<Thread> filter_thread_objectList(ArrayList<String> category) 
    {
        try {
            String dealCategory = category.get(0);
            int rating = Integer.parseInt(category.get(1));
            List<Thread> allThread = new ArrayList<>();

            _Connection = DriverManager.getConnection(url, db_id, db_password);
            _Statement = _Connection.createStatement();
            if (dealCategory.equals("Choose")) 
            {
                 _ResultSet = _Statement.executeQuery("SELECT * from thread WHERE rating >= '"+rating+"'");
            }
            else if (rating == -1) 
            {
                _ResultSet = _Statement.executeQuery("SELECT * from thread WHERE category = '"+dealCategory+"'");
            }
            else
            {
                _ResultSet = _Statement.executeQuery("SELECT * from thread WHERE category = '"+dealCategory+"' AND rating >= '"+rating+"'");
            }

            while (_ResultSet.next()) {
                Thread obj = new Thread(
                        (Integer.parseInt(_ResultSet.getString("deal_id"))),
                        _ResultSet.getString("title"), _ResultSet.getString("category"),
                        _ResultSet.getString("content"), _ResultSet.getString("deal_creator_name"),
                        _ResultSet.getString("account_id"), _ResultSet.getString("price"),
                        (Integer.parseInt(_ResultSet.getString("rating"))), _ResultSet.getString("deal_creation_date")
                );

                allThread.add(obj);
            }

            return allThread;

        } catch (SQLException e) {
            System.err.println("Error occured in filter_thread_objectList");
            e.printStackTrace();
        } finally {
            try {
                _Connection.close();
                _Statement.close();
                _ResultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    } 
}
