/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author darsh
 */
public class Comment {
    
    private int deal_id;
    private String user_id;
    private String username;
    private String comment_text;
    private String comment_date;

    public int getDeal_id() {
        return deal_id;
    }

    public void setDeal_id(int deal_id) {
        this.deal_id = deal_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }
    
    public Comment(int _dealID, String _userID, String _username, String _commentText, String _commentDate)
    {
        this.deal_id = _dealID;
        this.user_id = _userID;
        this.username = _username;
        this.comment_text = _commentText;
        this.comment_date = _commentDate;
    }
}
