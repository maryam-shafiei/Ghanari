import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInterface {
    private DataBase db;

    public UserInterface(Connection connection){
        db = new DataBase(connection);
    }
    public void help(){
        System.out.println("1) login <username> <password>");
        System.out.println("2) login -h <username>: get username's login history");
        System.out.println("3) tweet <\"content\">: send tweet");
        System.out.println("4) tweet -h <hashtag>: get tweets of given hashtag");
        System.out.println("5) tweet -p: get popular tweets");
        System.out.println("6) tweet -u: get personal tweets");
        System.out.println("7) tweet -c <\"content\"> <sender> <\"send_date\">: get comments of given tweet");
        System.out.println("8) follow <followed_username>");
        System.out.println("9) unfollow <unFollowed_username>");
        System.out.println("10) block <blocked_username>");
        System.out.println("11) unblock <unblocked_username>");
        System.out.println("12) activity -f: get following's tweet");
        System.out.println("13) activity -u <username>: get tweets of given username");
        System.out.println("14) like <\"content\"> <sender> <\"send_date\">");
        System.out.println("15) like -n <\"content\"> <sender> <\"send_date\">: get number of given tweet's like");
        System.out.println("16) like -l <\"content\"> <sender> <\"send_date\">: get list of likers of given tweet");
        System.out.println("17) create <firstname> <lastname> <username> <password> <birthday> <\"biography\">: create new account");
        System.out.println("18) comment <\"tweet_content\"> <tweet_sender> <\"tweet_send_date\"> <\"comment_content\">: add comment on given tweet");
        System.out.println("19) message -t <\"tweet_content\"> <tweet_sender> <\"tweet_send_date\"> <msg_receiver>: send tweet as message");
        System.out.println("20) message -x <receiver> <message_content>: send text message");
        System.out.println("21) message -u <msg_sender>: get messages which is sent by given user");
        System.out.println("22) message -l: get list of users which send message");
        System.out.println("23) help");
    }
    public void condition(String input){
        List<String> inputs = new ArrayList<String>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(input);
        while (m.find()) {
            inputs.add(m.group(1));
        }
        String instruction = inputs.get(0);
        switch (instruction){
            case "login":
                if(inputs.get(1).equals("-h")){
                    db.userLoginHistory(inputs.get(2));
                }else {
                    db.login(inputs.get(1), inputs.get(2));
                }
                break;
            case "tweet":
                switch (inputs.get(1)) {
                    case "-h":
                        db.get_hashtag_tweets(inputs.get(2));
                        break;
                    case "-p":
                        db.getPopularTweets();
                        break;
                    case "-u":
                        db.getPersonalTweets();
                        break;
                    case "-c":
                        db.getTweetComment(inputs.get(2).replaceAll("[\"]", ""), inputs.get(3), inputs.get(4).replaceAll("[\"]", ""));
                        break;
                    default:
                        db.send_tweet(inputs.get(1).replaceAll("[\"]", ""));
                        break;
                }
                break;
            case "follow":
                db.follow(inputs.get(1));
                break;
            case "unfollow":
                db.unFollow(inputs.get(1));
                break;
            case "block":
                db.blocking(inputs.get(1));
                break;
            case "unblock":
                db.unblocking(inputs.get(1));
                break;
            case "activity":
                if(inputs.get(1).equals("-f")){
                    db.getFollowingActivity();
                }else if(inputs.get(1).equals("-u")){
                    db.getUsersActivity(inputs.get(2));
                }
                break;
            case "like":
                if(inputs.get(1).equals("-n")){
                    db.getLikeNumber(inputs.get(2).replaceAll("[\"]", ""),inputs.get(3), inputs.get(4).replaceAll("[\"]", ""));
                }else if(inputs.get(1).equals("-l")){
                    db.getLikersList(inputs.get(2).replaceAll("[\"]", ""),inputs.get(3), inputs.get(4).replaceAll("[\"]", ""));
                }else {
                    db.likeTweet(inputs.get(1).replaceAll("[\"]", ""), inputs.get(2), inputs.get(3).replaceAll("[\"]", ""));
                }
                break;
            case "create":
                db.create_account(inputs.get(1), inputs.get(2), inputs.get(3), inputs.get(4), inputs.get(5), inputs.get(6).replaceAll("[\"]", ""));
                break;
            case "comment":
                db.add_comment(inputs.get(1).replaceAll("[\"]", ""), inputs.get(2), inputs.get(3).replaceAll("[\"]", ""), inputs.get(4).replaceAll("[\"]", ""));
                break;
            case "help":
                help();
                break;
            case "message":
                switch (inputs.get(1)) {
                    case "-t":
                        db.send_tweet_message(inputs.get(2).replaceAll("[\"]", ""), inputs.get(3), inputs.get(4).replaceAll("[\"]", ""), inputs.get(5));
                        break;
                    case "-x":
                        db.send_txt_message(inputs.get(2), inputs.get(3).replaceAll("[\"]", ""));
                        break;
                    case "-u":
                        db.get_user_message(inputs.get(2));
                        break;
                    case "-l":
                        db.get_msg_sender_list();
                        break;
                }
                break;
            default:
                System.out.println("incorrect instruction!");
                break;
        }
    }
    public void start(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.printf(">>");
            String input = scanner.nextLine();
            condition(input);
            System.out.println();
        }
    }

}
