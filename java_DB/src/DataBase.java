import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;

public class DataBase {
    private Connection connection;
    public DataBase(Connection connection){
        this.connection = connection;
    }
    public void userLoginHistory(String username){
        try {
            CallableStatement statement = connection.prepareCall("{call userLoginHistory(?)}");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            System.out.println("login_time");
            while (rs.next()) {
                System.out.println(rs.getString("login_time"));
            }
        }catch (Exception e){
            System.out.println("userLoginHistory: "+e.getMessage());
        }
    }
    public void getPersonalTweets(){
        try {
            CallableStatement statement = connection.prepareCall("{call getPersonalTweets()}");
            ResultSet rs = statement.executeQuery();
            System.out.println("content");
            while (rs.next()) {
                System.out.println(rs.getString("content"));
            }
        }catch (Exception e){
            System.out.println("getPersonalTweets: "+e.getMessage());
        }
    }
    public void follow(String followed_username){
        try {
            CallableStatement statement = connection.prepareCall("{call follow(?)}");
            statement.setString(1, followed_username);
            ResultSet rs = statement.executeQuery();
            System.out.println(followed_username + " followed.");
        }catch (Exception e){
            System.out.println("follow: "+e.getMessage());
        }
    }
    public void unFollow(String unFollowed_username){
        try {
            CallableStatement statement = connection.prepareCall("{call unfollow(?, ?)}");
            statement.setString(1, unFollowed_username);
            statement.registerOutParameter(2, Types.INTEGER);
            ResultSet rs = statement.executeQuery();
            if(statement.getInt(2) == 1){
                System.out.println(unFollowed_username + " unfollowed.");
            }else {
                System.out.println("You did not follow "+unFollowed_username +".");
            }
        }catch (Exception e){
            System.out.println("unFollow: "+e.getMessage());
        }
    }
    public void blocking(String blocked_username){
        try {
            CallableStatement statement = connection.prepareCall("{call blocking(?)}");
            statement.setString(1, blocked_username);
            ResultSet rs = statement.executeQuery();
            System.out.println( blocked_username + " blocked.");
        }catch (Exception e){
            System.out.println("blocking: "+e.getMessage());
        }
    }
    public void unblocking(String unblocked_username){
        try {
            CallableStatement statement = connection.prepareCall("{call unblocking(?, ?)}");
            statement.setString(1, unblocked_username);
            statement.registerOutParameter(2, Types.INTEGER);
            ResultSet rs = statement.executeQuery();
            if(statement.getInt(2) == 1){
                System.out.println( unblocked_username + " unblocked.");
            }else {
                System.out.println("You did not block "+unblocked_username +".");
            }
        }catch (Exception e){
            System.out.println("unblocking: "+e.getMessage());
        }
    }
    public void getFollowingActivity(){
        try {
            CallableStatement statement = connection.prepareCall("{call getFollowingActivity()}");
            ResultSet rs = statement.executeQuery();
            System.out.printf("%-50s%-22s%-17s%n", "content", "sender", "send_date");
            while (rs.next()) {
                System.out.printf("%-50s%-22s%-17s%n", rs.getString("content"), rs.getString("sender"), rs.getString("send_date"));
            }
        }catch (Exception e){
            System.out.println("getFollowingActivity: "+e.getMessage());
        }
    }
    public void getUsersActivity(String sender_user){
        try {
            CallableStatement statement = connection.prepareCall("{call getUsersActivity(?)}");
            statement.setString(1, sender_user);
            ResultSet rs = statement.executeQuery();
            System.out.printf("%-50s%-22s%-17s%n", "content", "sender", "send_date");
            while (rs.next()) {
                System.out.printf("%-50s%-22s%-17s%n", rs.getString("content"), rs.getString("sender"), rs.getString("send_date"));
            }
        }catch (Exception e){
            System.out.println("getUsersActivity: "+e.getMessage());
        }
    }
    public void getTweetComment(String content, String sender, String send_date){
        try {
            CallableStatement statement = connection.prepareCall("{call getTweetComment(?, ?, ?)}");
            statement.setString(1, content);
            statement.setString(2, sender);
            statement.setString(3, send_date);
            ResultSet rs = statement.executeQuery();
            System.out.printf("%-50s%-22s%-17s%n", "content", "sender", "send_date");
            while (rs.next()) {
                System.out.printf("%-50s%-22s%-17s%n", rs.getString("content"), rs.getString("sender"), rs.getString("send_date"));
            }
        }catch (Exception e){
            System.out.println("getTweetComment: "+e.getMessage());
        }
    }
    public void likeTweet(String content, String sender, String send_date){
        try {
            CallableStatement statement = connection.prepareCall("{call likeTweet(?, ?, ?)}");
            statement.setString(1, content);
            statement.setString(2, sender);
            statement.setString(3, send_date);
            ResultSet rs = statement.executeQuery();
            //System.out.println("\""+content +"\" tweet from "+sender+" liked.");
        }catch (Exception e){
            System.out.println("likeTweet: "+e.getMessage());
        }
    }
    public void getLikeNumber(String content, String sender, String send_date){
        try {
            CallableStatement statement = connection.prepareCall("{call getLikeNumber(?, ?, ?)}");
            statement.setString(1, content);
            statement.setString(2, sender);
            statement.setString(3, send_date);
            ResultSet rs = statement.executeQuery();
            System.out.printf("%-10s%n", "like_numbers");
            while (rs.next()) {
                System.out.printf("%-10s%n", rs.getString("like_numbers"));
            }
        }catch (Exception e){
            System.out.println("getLikeNumber: "+e.getMessage());
        }
    }
    public void getLikersList(String content, String sender, String send_date){
        try {
            CallableStatement statement = connection.prepareCall("{call getLikersList(?, ?, ?)}");
            statement.setString(1, content);
            statement.setString(2, sender);
            statement.setString(3, send_date);
            ResultSet rs = statement.executeQuery();
            System.out.printf("%-20s%n", "username");
            while (rs.next()) {
                System.out.printf("%-20s%n", rs.getString("username"));
            }
        }catch (Exception e){
            System.out.println("getLikersList: "+e.getMessage());
        }
    }
    public void getPopularTweets(){
        try {
            CallableStatement statement = connection.prepareCall("{call getPopularTweets()}");
            ResultSet rs = statement.executeQuery();
            System.out.printf("%-50s%-10s%n", "content", "like_numbers");
            while (rs.next()) {
                System.out.printf("%-50s%-10s%n", rs.getString("content"), rs.getString("like_numbers"));
            }
        }catch (Exception e){
            System.out.println("getPopularTweets: "+e.getMessage());
        }
    }
    public void create_account(String firstname, String lastname, String username, String passwd, String birthday, String biography){
        try {
            CallableStatement statement = connection.prepareCall("{call create_account(?, ?, ?, ?, ?, ?)}");
            statement.setString(1, firstname);
            statement.setString(2, lastname);
            statement.setString(3, username);
            statement.setString(4, passwd);
            statement.setString(5, birthday);
            statement.setString(6, biography);
            ResultSet rs = statement.executeQuery();
            System.out.println(username+" account create successfully.");
        }catch (Exception e){
            System.out.println("getPopularTweets: "+e.getMessage());
        }
    }
    public void login(String username, String password){
        try {
            CallableStatement statement = connection.prepareCall("{call login(?, ?, ?)}");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.registerOutParameter(3, Types.INTEGER);
            statement.execute();
            if(statement.getInt(3) == 1){
                System.out.println(username +" login.");
            }else {
                System.out.println(username +" can not login.");
            }
        }catch (Exception e){
            System.out.println("login: "+e.getMessage());
        }
    }
    public void send_tweet(String content){
        try {
            CallableStatement statement = connection.prepareCall("{call send_tweet(?)}");
            statement.setString(1, content);
            statement.execute();
            System.out.println("tweet \""+content+"\" send.");
        }catch (Exception e){
            System.out.println("send_tweet: "+e.getMessage());
        }
    }
    public void add_comment(String tweet_content, String tweet_sender, String tweet_send_date, String comment_content){
        try {
            CallableStatement statement = connection.prepareCall("{call add_comment(?, ?, ?, ?)}");
            statement.setString(1, tweet_content);
            statement.setString(2, tweet_sender);
            statement.setString(3, tweet_send_date);
            statement.setString(4, comment_content);
            statement.execute();
        }catch (Exception e){
            System.out.println("add_comment: "+e.getMessage());
        }
    }
    public void get_hashtag_tweets(String hashtag){
        try {
            CallableStatement statement = connection.prepareCall("{call get_hashtag_tweets(?)}");
            statement.setString(1, hashtag);
            ResultSet rs = statement.executeQuery();
            System.out.printf("%-50s%-22s%-17s%n", "content", "sender", "send_date");
            while (rs.next()) {
                System.out.printf("%-50s%-22s%-17s%n", rs.getString("content"), rs.getString("sender"), rs.getString("send_date"));
            }
        }catch (Exception e){
            System.out.println("get_hashtag_tweets: "+e.getMessage());
        }
    }
    public int send_txt_message(String receiver, String content){
        try {
            CallableStatement statement = connection.prepareCall("{call send_txt_message(?, ?, ?)}");
            statement.setString(1, receiver);
            statement.setString(2, content);
            statement.registerOutParameter(3, Types.INTEGER);
            statement.execute();
            return  statement.getInt(3);
        }catch (Exception e){
            System.out.println("send_txt_message: "+e.getMessage());
            return 0;
        }
    }
    public int send_tweet_message(String tweet_content, String tweet_sender, String tweet_send_date, String msg_receiver){
        try {
            CallableStatement statement = connection.prepareCall("{call send_tweet_message(?, ?, ?, ?, ?)}");
            statement.setString(1, tweet_content);
            statement.setString(2, tweet_sender);
            statement.setString(3, tweet_send_date);
            statement.setString(4, msg_receiver);
            statement.registerOutParameter(5, Types.INTEGER);
            statement.execute();
            return  statement.getInt(5);
        }catch (Exception e){
            System.out.println("send_tweet_message: "+e.getMessage());
            return 0;
        }
    }
    public void get_user_message(String msg_sender){
        try {
            CallableStatement statement = connection.prepareCall("{call get_user_message(?)}");
            statement.setString(1, msg_sender);
            ResultSet rs = statement.executeQuery();
            System.out.printf("%-50s%-17s%n", "message_txt", "message_send_date");
            while (rs.next()) {
                System.out.printf("%-50s%-17s%n", rs.getString("message_txt"), rs.getString("message_send_date"));
            }
        }catch (Exception e){
            System.out.println("get_user_message: "+e.getMessage());
        }
    }
    public void get_msg_sender_list(){
        try {
            CallableStatement statement = connection.prepareCall("{call get_msg_sender_list()}");
            ResultSet rs = statement.executeQuery();
            System.out.printf("%-22s%-17s%n", "message_sender", "message_send_date");
            while (rs.next()) {
                System.out.printf("%-22s%-17s%n", rs.getString("message_sender"), rs.getString("message_send_date"));
            }
        }catch (Exception e){
            System.out.println("get_msg_sender_list: "+e.getMessage());
        }
    }
    public String get_user(){
        try {
            CallableStatement statement = connection.prepareCall("{call get_user(?)}");
            statement.registerOutParameter(1, Types.VARCHAR);
            statement.execute();
            return  statement.getString(1);
        }catch (Exception e){
            System.out.println("get_user: "+e.getMessage());
            return "";
        }
    }
}
