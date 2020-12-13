
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
public interface IDataBaseOperation {
    
    public String insertUserAccount(String id, String password, String name);
    public String loginVerification(String id, String password);
    public String FetchLoggedInUserName(String id, String password);
    public void dealCreation(
            String title,
            String category,
            String content,
            String deal_creator_name,
            String account_id,
            String price,
            int rating,
            String deal_creation_date
            );
    public List<Thread> getAllThread_ID_Title_and_Price();
    public List<Thread> getThreadObjectForEditor();
    
    public int thread_Like(int rating, int deal_id, String LoggedIn_user_id);
    public int Get_current_thread_Like(int deal_id);
    public int thread_Dislike(int rating, int deal_id, String LoggedIn_user_id);
    
    public String insertBestDeal(String editor_id, int deal_id);
    
    public List<Thread> frontPagedeals();
    
    public void userComments(int deal_id, String user_id, String username, String comment_text, String comment_date);
    
    public List<Comment> deal_comment_object(int deal_id);
    
    public List<Thread> filter_thread_objectList(ArrayList<String> category);
}
