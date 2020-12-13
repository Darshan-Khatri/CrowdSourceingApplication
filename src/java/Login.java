/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author darsh
 */
//Ashish
@Named(value = "login")
@SessionScoped
@ManagedBean
public class Login implements Serializable {

    private String Id;
    private String Password;
    private String loggedIn_UserName;
    private String user_type;
    private Thread Object_thread;
    private DataBaseService Data;
    private List<Thread> Best_deal_list;
    private List<Thread> All_thread_list;
    private List<Thread> Front_page_deal_list;
    private String comment;
    private Comment Comment_object;
    private List<Comment> Comment_object_list;
    private int TotalCommentCount;
    private String DealCategory = "Choose";
    private String dealRatingFilter = "-1";
    private ArrayList<String> filterList = new ArrayList<>();

    public ArrayList<String> getFilterList() {
        return filterList;
    }

    public void setFilterList(ArrayList<String> filterList) {
        this.filterList = filterList;
    }

    public String getDealRatingFilter() {
        return dealRatingFilter;
    }

    public void setDealRatingFilter(String dealRatingFilter) {
        this.dealRatingFilter = dealRatingFilter;
    }

    public String getDealCategory() {
        return DealCategory;
    }

    public void setDealCategory(String DealCategory) {
        this.DealCategory = DealCategory;
    }

    public int getTotalCommentCount() {
        return TotalCommentCount;
    }

    public void setTotalCommentCount(int TotalCommentCount) {
        this.TotalCommentCount = TotalCommentCount;
    }

    public List<Comment> getComment_object_list() {
        return Comment_object_list;
    }

    public void setComment_object_list(List<Comment> Comment_object_list) {
        this.Comment_object_list = Comment_object_list;
    }

    public Comment getComment_object() {
        return Comment_object;
    }

    public void setComment_object(Comment Comment_object) {
        this.Comment_object = Comment_object;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Thread> getFront_page_deal_list() {
        return Front_page_deal_list;
    }

    public void setFront_page_deal_list(List<Thread> Front_page_deal_list) {
        this.Front_page_deal_list = Front_page_deal_list;
    }

    public String getLoggedIn_UserName() {
        return loggedIn_UserName;
    }

    public void setLoggedIn_UserName(String loggedIn_UserName) {
        this.loggedIn_UserName = loggedIn_UserName;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public List<Thread> getAll_thread_list() {
        return All_thread_list;
    }

    public void setAll_thread_list(List<Thread> All_thread_list) {
        this.All_thread_list = All_thread_list;
    }

    public List<Thread> getBest_deal_list() {
        return Best_deal_list;
    }

    public void setBest_deal_list(List<Thread> Best_deal_list) {
        this.Best_deal_list = Best_deal_list;
    }

    public Login() {
        filterList.clear();
        filterList.add(DealCategory);
        filterList.add(dealRatingFilter);
    }

    public DataBaseService getData() {
        return Data;
    }

    public void setData(DataBaseService Data) {
        this.Data = Data;
    }

    public Thread getObject_thread() {
        return Object_thread;
    }

    public void setObject_thread(Thread Object_thread) {
        this.Object_thread = Object_thread;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    //Destructure object.
    public String logOut() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index";
    }

    public String Login() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            return ("Error occured in Login");
        }
        Data = new DataBaseService();
        String urlRoute = Data.loginVerification(this.Id, this.Password);
        //When login is Unsuccessful.
        if (urlRoute.equals("LoginFailed")) {
            logOut();
            return urlRoute;
        } //When login is successful.
        else {
            //This contain logged in user details
            String str = Data.FetchLoggedInUserName(this.Id, this.Password);

            this.loggedIn_UserName = str.substring(0, str.lastIndexOf(","));
            this.user_type = str.substring((str.lastIndexOf(",") + 1));
            //Calling DataBase service method to insert thread into thread table.
            this.Object_thread = new Thread(1, "", "", "", "", this.Id, "", 0, "");
            Data = new DataBaseService();
            this.Best_deal_list = Data.getThreadObjectForEditor();
            return displayfrontPagedeals();
        }
    }

    public String createDealAndRestForm() {
        this.Object_thread.createThread(loggedIn_UserName);
        if (user_type.equals("editor") || user_type.equals("EDI")) {
            return "editor_forum_page";
        }
        return "forumPage";
    }

    public List<Thread> displayThreadTitle() {
        if (DealCategory.equals("Choose") && dealRatingFilter.equals("-1")) {
            return this.All_thread_list = Object_thread.threadTitle();
        }
        filterList.set(0, this.DealCategory);
        filterList.set(1, this.dealRatingFilter);
        Data = new DataBaseService();
        return Data.filter_thread_objectList(filterList);
    }

    public String clearFilter() {
        this.DealCategory = "Choose";
        this.dealRatingFilter = "-1";
        filterList.set(0, this.DealCategory);
        filterList.set(1, this.dealRatingFilter);
        if (user_type.equals("editor") || user_type.equals("EDI")) {
            return "editor_forum_page";
        }
        return "forumPage";
    }

    public String clearRatingFilter() {
        this.dealRatingFilter = "-1";
        filterList.set(1, this.dealRatingFilter);
        if (user_type.equals("editor") || user_type.equals("EDI")) {
            return "editor_forum_page";
        }
        return "forumPage";
    }

    public String clearCategoryFilter() {
        this.DealCategory = "Choose";
        filterList.set(0, this.DealCategory);
        if (user_type.equals("editor") || user_type.equals("EDI")) {
            return "editor_forum_page";
        }
        return "forumPage";
    }

    public String threadBody(Thread obj) {
        Object_thread = obj;
        Comment_object_list = fetch_Comment_Obj_list();

        if (user_type.equals("editor") || user_type.equals("EDI")) {
            return "editor_threadBody";
        }
        return "threadBody";
    }

    public List<Comment> fetch_Comment_Obj_list() {
        Comment_object_list = new ArrayList<>();
        Data = new DataBaseService();
        Comment_object_list = Data.deal_comment_object(Object_thread.getDeal_id());
        TotalCommentCount = Data.getCommentCount();
        return Comment_object_list;
    }

    public String thread_Like() {
        int rating = Object_thread.thread_Like(this.Id);
        Object_thread.setRating(rating);
        if (user_type.equals("editor") || user_type.equals("EDI")) {
            Data = new DataBaseService();
            this.Best_deal_list = Data.getThreadObjectForEditor();
            return "editor_threadBody";
        }
        return "threadBody";
    }

    public String thread_Dislike() {
        int rating = Object_thread.thread_Dislike(this.Id);
        Object_thread.setRating(rating);
        if (user_type.equals("editor") || user_type.equals("EDI")) {
            Data = new DataBaseService();
            this.Best_deal_list = Data.getThreadObjectForEditor();
            return "editor_threadBody";
        }
        return "threadBody";
    }

    public int get_rating_count() {
        return this.Object_thread.get_rating_count();
    }

    public String get_best_deal() {
        Data = new DataBaseService();
        this.Best_deal_list = Data.getThreadObjectForEditor();
        return "editor_best_deal";
    }

    public String insertIntoBestDeal(String editor_id, int deal_id) {
        Data = new DataBaseService();
        Data.insertBestDeal(editor_id, deal_id);
        Data = new DataBaseService();
        this.Best_deal_list = Data.getThreadObjectForEditor();
        return displayfrontPagedeals();
    }

    public String displayfrontPagedeals() {
        this.Front_page_deal_list = Object_thread.displayFrontPageDeals();
        if (user_type.equals("editor") || user_type.equals("EDI")) {
            return "editor_front_page";
        }
        return "FrontPage";
    }

    public String userComment(String deal_id) {

        String comment_date = "";
        int thread_id = Integer.parseInt(deal_id);

        Data.userComments(thread_id, this.Id, this.loggedIn_UserName, this.comment, comment_date);
        this.comment = "";
        Comment_object_list = fetch_Comment_Obj_list();
        if (user_type.equals("editor") || user_type.equals("EDI")) {
            return "editor_threadBody";
        }
        return "threadBody";
    }

    public String frontPageLike(int _rating, int _dealID, String _userID) {

        Data = new DataBaseService();
        Data.thread_Like(_rating, _dealID, _userID);
        return displayfrontPagedeals();

    }

    public String frontPageDisLike(int _rating, int _dealID, String _userID) {

        Data = new DataBaseService();
        Data.thread_Dislike(_rating, _dealID, _userID);
        return displayfrontPagedeals();

    }
}
