
import java.awt.Graphics;
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
//Roland and Ashish
public class Thread {

    private int deal_id;
    private String title;
    private String category;
    private String content;
    private String deal_creator_name;
    private String account_id;
    private String price;
    private int rating;
    private String deal_creation_date;

    public int getRating() {
            return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDeal_creation_date() {
            return this.deal_creation_date;
    }

    public void setDeal_creation_date(String deal_creation_date) {
        this.deal_creation_date = deal_creation_date;
    }
    
    
    private DataBaseService Data;

    public DataBaseService getData() {
        return Data;
    }

    public void setData(DataBaseService Data) {
        this.Data = Data;
    }

    public Thread(int deal_id,String title, String category, String content, String deal_creator_name, String account_id, String price, int rating, String deal_creation_date) {
        this.deal_id = deal_id;
        this.title = title;
        this.category = category;
        this.content = content;
        this.deal_creator_name = deal_creator_name;
        this.account_id = account_id;
        this.price = price;
        this.rating = rating;
        this.deal_creation_date = deal_creation_date;
    }
    

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getDeal_id() {
        return deal_id;
    }

    public void setDeal_id(int deal_id) {
        this.deal_id = deal_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDeal_creator_name() {
        return deal_creator_name;
    }

    public void setDeal_creator_name(String deal_creator_name) {
        this.deal_creator_name = deal_creator_name;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public void createThread(String LoggedInUserName) {
        this.Data = new DataBaseService();
         Data.dealCreation(
                this.title,
                this.category,
                this.content,
                this.deal_creator_name = LoggedInUserName,
                this.account_id,
                this.price,
                this.rating = 0,
                this.deal_creation_date// rating
        );
    }
    
    public void resettingValues(){
        this.title = "";this.category = "";this.content="";this.price= "";
    }
    
    public List<Thread> threadTitle(){
        List<Thread> threadTitle = new ArrayList<>();
        this.Data = new DataBaseService();
        threadTitle = this.Data.getAllThread_ID_Title_and_Price();
        return threadTitle;
    }
    
    public int thread_Like(String LoggedIn_user_id){
        this.Data = new DataBaseService();
        return Data.thread_Like(this.rating, this.deal_id, LoggedIn_user_id);
    }
    
    public int thread_Dislike(String LoggedIn_user_id){
        Data = new DataBaseService();
        return Data.thread_Dislike(this.rating, this.deal_id, LoggedIn_user_id);
    }
    
    public int get_rating_count(){
        this.Data = new DataBaseService();
        return this.Data.Get_current_thread_Like(this.deal_id);
    }

    public List<Thread> displayFrontPageDeals(){
        this.Data = new DataBaseService();
        return Data.frontPagedeals();
    }
}
