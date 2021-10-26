import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

public class GUI extends JFrame {
    private DataBase db;
    private JPanel main;

    public GUI(Connection connection){
        db = new DataBase(connection);
        main = new JPanel();
        add(main, BorderLayout.CENTER);
        createMenu();
        setSize(1000, 200);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void createMenu(){
        JMenuBar mb = new JMenuBar();
        JMenu login = new JMenu("Login");
        JMenu tweet = new JMenu("Tweet");
        JMenu followBlock = new JMenu("Follow/Block");
        JMenu activity = new JMenu("Activity");
        JMenu like = new JMenu("Like");
        JMenu create = new JMenu("Create");
        createAccountAction(create);
        JMenu comment = new JMenu("Comment");
        commentAction(comment);
        JMenu message = new JMenu("Message");
        mb.add(login); mb.add(tweet); mb.add(followBlock); mb.add(activity);
        mb.add(like); mb.add(create); mb.add(comment); mb.add(message);
        add(mb, BorderLayout.NORTH);

        JMenuItem itemLogin1 = new JMenuItem("Login");
        loginAction(itemLogin1);
        JMenuItem itemLogin2 = new JMenuItem("History");
        loginHistoryAction(itemLogin2);
        login.add(itemLogin1); login.add(itemLogin2);

        JMenuItem itemTweet1 = new JMenuItem("Send");
        sendTweetAction(itemTweet1);
        JMenuItem itemTweet2 = new JMenuItem("Hashtag");
        getHashtagTweetAction(itemTweet2);
        JMenuItem itemTweet3 = new JMenuItem("Popular");
        popularTweetsAction(itemTweet3);
        JMenuItem itemTweet4 = new JMenuItem("Personal");
        personalTweetsAction(itemTweet4);
        JMenuItem itemTweet5 = new JMenuItem("Comment");
        getTweetCommentsAction(itemTweet5);
        tweet.add(itemTweet1); tweet.add(itemTweet2); tweet.add(itemTweet3); tweet.add(itemTweet4); tweet.add(itemTweet5);

        JMenuItem itemFB1 = new JMenuItem("follow");
        followAction(itemFB1);
        JMenuItem itemFB2 = new JMenuItem("unfollow");
        unFollowAction(itemFB2);
        JMenuItem itemFB3 = new JMenuItem("block");
        blockAction(itemFB3);
        JMenuItem itemFB4 = new JMenuItem("unblock");
        unBlockAction(itemFB4);
        followBlock.add(itemFB1); followBlock.add(itemFB2); followBlock.add(itemFB3); followBlock.add(itemFB4);

        JMenuItem itemActivity1 = new JMenuItem("Followings");
        followingActivityAction(itemActivity1);
        JMenuItem itemActivity2 = new JMenuItem("Special User");
        userActivityAction(itemActivity2);
        activity.add(itemActivity1); activity.add(itemActivity2);

        JMenuItem itemLike1 = new JMenuItem("Like");
        likeAction(itemLike1);
        JMenuItem itemLike2 = new JMenuItem("Like number");
        likeNumberAction(itemLike2);
        JMenuItem itemLike3 = new JMenuItem("Likers list");
        likersListAction(itemLike3);
        like.add(itemLike1); like.add(itemLike2); like.add(itemLike3);

        JMenuItem itemMessage1 = new JMenuItem("Send Tweet");
        sendTweetMessage(itemMessage1);
        JMenuItem itemMessage2 = new JMenuItem("Send Text");
        sendTextMessage(itemMessage2);
        JMenuItem itemMessage3 = new JMenuItem("Special User");
        specialUserMessagesAction(itemMessage3);
        JMenuItem itemMessage4 = new JMenuItem("Users List");
        messageUserListAction(itemMessage4);
        message.add(itemMessage1); message.add(itemMessage2); message.add(itemMessage3); message.add(itemMessage4);
    }
    public void createAccountAction(JMenu menu){
        menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                main.removeAll();
                JPanel panel = new JPanel();
                panel.setLayout(new GridBagLayout());
                GridBagConstraints constr = new GridBagConstraints();
                constr.insets = new Insets(5, 5, 5, 5);
                constr.anchor = GridBagConstraints.WEST;
                JLabel firstnameLabel = new JLabel("firstname");
                constr.gridx = 0;
                constr.gridy = 0;
                panel.add(firstnameLabel, constr);
                JLabel lastnameLabel = new JLabel("lastname");
                constr.gridy = 1;
                panel.add(lastnameLabel, constr);
                JLabel usernameLabel = new JLabel("username");
                constr.gridy = 2;
                panel.add(usernameLabel, constr);
                JLabel passwordLabel = new JLabel("password");
                constr.gridy = 3;
                panel.add(passwordLabel, constr);
                JLabel birthdayLabel = new JLabel("birthday");
                constr.gridy = 4;
                panel.add(birthdayLabel, constr);
                JLabel bioLabel = new JLabel("bio");
                constr.gridy = 5;
                panel.add(bioLabel, constr);
                JTextField firstname = new JTextField(50);
                JTextField lastname = new JTextField(50);
                JTextField username = new JTextField(50);
                JTextField password = new JTextField(50);
                JTextField birthday = new JTextField(50);
                JTextField bio = new JTextField(50);
                constr.gridx = 1;
                constr.gridy = 0;
                panel.add(firstname, constr);
                constr.gridy = 1;
                panel.add(lastname, constr);
                constr.gridy = 2;
                panel.add(username, constr);
                constr.gridy = 3;
                panel.add(password, constr);
                constr.gridy = 4;
                panel.add(birthday, constr);
                constr.gridy = 5;
                panel.add(bio, constr);
                constr.gridx=1; constr.gridy=6;
                constr.anchor = GridBagConstraints.CENTER;
                JButton sendButton = new JButton("Create");
                sendButton.addActionListener(e1 -> db.create_account(firstname.getText(), lastname.getText(), username.getText(), password.getText(), birthday.getText(), bio.getText()));
                panel.add(sendButton, constr);
                main.add(panel);
                pack();
            }
        });
    }
    public void commentAction(JMenu menu){
        menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                main.removeAll();
                JPanel panel = new JPanel();
                panel.setLayout(new GridBagLayout());
                GridBagConstraints constr = new GridBagConstraints();
                constr.insets = new Insets(5, 5, 5, 5);
                constr.anchor = GridBagConstraints.WEST;
                JLabel tweetContentLabel = new JLabel("tweet content");
                constr.gridx = 0;
                constr.gridy = 0;
                panel.add(tweetContentLabel, constr);
                JLabel tweetSenderLabel = new JLabel("tweet sender");
                constr.gridy = 1;
                panel.add(tweetSenderLabel, constr);
                JLabel tweetSendDateLabel = new JLabel("tweet send date");
                constr.gridy = 2;
                panel.add(tweetSendDateLabel, constr);
                JLabel commentLabel = new JLabel("comment");
                constr.gridy = 3;
                panel.add(commentLabel, constr);
                JTextField tweetContent = new JTextField(50);
                JTextField tweetSender = new JTextField(50);
                JTextField tweetSendDate = new JTextField(50);
                JTextField comment = new JTextField(50);
                constr.gridx = 1;
                constr.gridy = 0;
                panel.add(tweetContent, constr);
                constr.gridy = 1;
                panel.add(tweetSender, constr);
                constr.gridy = 2;
                panel.add(tweetSendDate, constr);
                constr.gridy = 3;
                panel.add(comment, constr);
                constr.gridx=1; constr.gridy=4;
                constr.anchor = GridBagConstraints.CENTER;
                JButton sendButton = new JButton("Send comment");
                sendButton.addActionListener(e1 -> db.add_comment(tweetContent.getText(), tweetSender.getText(), tweetSendDate.getText(), comment.getText()));
                panel.add(sendButton, constr);
                main.add(panel);
                pack();
            }
        });
    }
    public void sendTweetMessage(JMenuItem item){
        item.addActionListener(e -> {
            main.removeAll();
            JPanel panel = new JPanel();
            panel.setLayout(new GridBagLayout());
            GridBagConstraints constr = new GridBagConstraints();
            constr.insets = new Insets(5, 5, 5, 5);
            constr.anchor = GridBagConstraints.WEST;
            JLabel tweetContentLabel = new JLabel("tweet content");
            constr.gridx = 0;
            constr.gridy = 0;
            panel.add(tweetContentLabel, constr);
            JLabel tweetSenderLabel = new JLabel("tweet sender");
            constr.gridy = 1;
            panel.add(tweetSenderLabel, constr);
            JLabel tweetSendDateLabel = new JLabel("tweet send date");
            constr.gridy = 2;
            panel.add(tweetSendDateLabel, constr);
            JLabel receiverLabel = new JLabel("receiver");
            constr.gridy = 3;
            panel.add(receiverLabel, constr);
            JTextField tweetContent = new JTextField(50);
            JTextField tweetSender = new JTextField(50);
            JTextField tweetSendDate = new JTextField(50);
            JTextField receiver = new JTextField(50);
            constr.gridx = 1;
            constr.gridy = 0;
            panel.add(tweetContent, constr);
            constr.gridy = 1;
            panel.add(tweetSender, constr);
            constr.gridy = 2;
            panel.add(tweetSendDate, constr);
            constr.gridy = 3;
            panel.add(receiver, constr);
            constr.gridx=1; constr.gridy=4;
            constr.anchor = GridBagConstraints.CENTER;
            JButton sendButton = new JButton("Send message");
            sendButton.addActionListener(e1 -> db.send_tweet_message(tweetContent.getText(), tweetSender.getText(), tweetSendDate.getText(), receiver.getText()));
            panel.add(sendButton, constr);
            main.add(panel);
            pack();
        });
    }
    public void sendTextMessage(JMenuItem item){
        item.addActionListener(e -> {
            main.removeAll();
            JPanel panel = new JPanel();
            panel.setLayout(new GridBagLayout());
            GridBagConstraints constr = new GridBagConstraints();
            constr.insets = new Insets(5, 5, 5, 5);
            constr.anchor = GridBagConstraints.WEST;
            JLabel contentLabel = new JLabel("content");
            constr.gridx = 0;
            constr.gridy = 0;
            panel.add(contentLabel, constr);
            JLabel receiverLabel = new JLabel("receiver");
            constr.gridx = 0;
            constr.gridy = 1;
            panel.add(receiverLabel, constr);
            JTextField content = new JTextField(50);
            JTextField receiver = new JTextField(50);
            constr.gridx = 1;
            constr.gridy = 0;
            panel.add(content, constr);
            constr.gridy = 1;
            panel.add(receiver, constr);
            constr.gridx=1; constr.gridy=2;
            constr.anchor = GridBagConstraints.CENTER;
            JButton sendButton = new JButton("Send message");
            sendButton.addActionListener(e1 -> db.send_txt_message(receiver.getText(), content.getText()));
            panel.add(sendButton, constr);
            main.add(panel);
            pack();
        });
    }
    public void getTweetCommentsAction(JMenuItem item){
        item.addActionListener(e -> {
            JTextField textField1 = new JTextField(50);
            JTextField textField2 = new JTextField(50);
            JTextField textField3 = new JTextField(50);
            JButton button = new JButton("Show comments");
            GridBagConstraints constr = new GridBagConstraints();
            JPanel panel = addThreeFields(textField1, textField2, textField3, "content", "sender", "send date", constr);
            panel.add(button, constr);
            main.add(panel);
            button.addActionListener(e1 -> db.getTweetComment(textField1.getText(), textField2.getText(), textField3.getText()));
            pack();
        });
    }
    public void specialUserMessagesAction(JMenuItem item){
        item.addActionListener(e -> {
            JTextField textField = new JTextField(50);
            JButton button = new JButton("Show user messages");
            GridBagConstraints constr = new GridBagConstraints();
            JPanel panel = addOneField(textField, "username", constr);
            panel.add(button, constr);
            main.add(panel);
            button.addActionListener(e1 -> db.get_user_message(textField.getText()));
            pack();
        });
    }
    public void messageUserListAction(JMenuItem item){
        item.addActionListener(e -> {
            JButton button = new JButton("Users message");
            main.removeAll();
            main.add(button, BorderLayout.CENTER);
            button.addActionListener(e1 -> db.get_msg_sender_list());
            pack();
        });
    }
    public void likersListAction(JMenuItem item){
        item.addActionListener(e -> {
            JTextField textField1 = new JTextField(50);
            JTextField textField2 = new JTextField(50);
            JTextField textField3 = new JTextField(50);
            JButton button = new JButton("Show likers list");
            GridBagConstraints constr = new GridBagConstraints();
            JPanel panel = addThreeFields(textField1, textField2, textField3, "content", "sender", "send date", constr);
            panel.add(button, constr);
            main.add(panel);
            button.addActionListener(e1 -> db.getLikersList(textField1.getText(), textField2.getText(), textField3.getText()));
            pack();
        });
    }
    public void likeNumberAction(JMenuItem item){
        item.addActionListener(e -> {
            JTextField textField1 = new JTextField(50);
            JTextField textField2 = new JTextField(50);
            JTextField textField3 = new JTextField(50);
            JButton button = new JButton("Show like number");
            GridBagConstraints constr = new GridBagConstraints();
            JPanel panel = addThreeFields(textField1, textField2, textField3, "content", "sender", "send date", constr);
            panel.add(button, constr);
            main.add(panel);
            button.addActionListener(e1 -> db.getLikeNumber(textField1.getText(), textField2.getText(), textField3.getText()));
            pack();
        });
    }
    public void likeAction(JMenuItem item){
        item.addActionListener(e -> {
            JTextField textField1 = new JTextField(50);
            JTextField textField2 = new JTextField(50);
            JTextField textField3 = new JTextField(50);
            JButton button = new JButton("Like");
            GridBagConstraints constr = new GridBagConstraints();
            JPanel panel = addThreeFields(textField1, textField2, textField3, "content", "sender", "send date", constr);
            panel.add(button, constr);
            main.add(panel);
            button.addActionListener(e1 -> db.likeTweet(textField1.getText(), textField2.getText(), textField3.getText()));
            pack();
        });
    }
    public void userActivityAction(JMenuItem item){
        item.addActionListener(e -> {
            JTextField textField = new JTextField(50);
            JButton button = new JButton("Show activity");
            GridBagConstraints constr = new GridBagConstraints();
            JPanel panel = addOneField(textField, "username", constr);
            panel.add(button, constr);
            main.add(panel);
            button.addActionListener(e1 -> db.getUsersActivity(textField.getText()));
            pack();
        });
    }
    public void followingActivityAction(JMenuItem item){
        item.addActionListener(e -> {
            JButton button = new JButton("Show activity");
            main.removeAll();
            main.add(button, BorderLayout.CENTER);
            button.addActionListener(e1 -> db.getFollowingActivity());
            pack();
        });
    }
    public void unBlockAction(JMenuItem item){
        item.addActionListener(e -> {
            JTextField textField = new JTextField(50);
            JButton button = new JButton("UnBlock");
            GridBagConstraints constr = new GridBagConstraints();
            JPanel panel = addOneField(textField, "username", constr);
            panel.add(button, constr);
            main.add(panel);
            button.addActionListener(e1 -> db.unblocking(textField.getText()));
            pack();
        });
    }
    public void blockAction(JMenuItem item){
        item.addActionListener(e -> {
            JTextField textField = new JTextField(50);
            JButton button = new JButton("Block");
            GridBagConstraints constr = new GridBagConstraints();
            JPanel panel = addOneField(textField, "username", constr);
            panel.add(button, constr);
            main.add(panel);
            button.addActionListener(e1 -> db.blocking(textField.getText()));
            pack();
        });
    }
    public void unFollowAction(JMenuItem item){
        item.addActionListener(e -> {
            JTextField textField = new JTextField(50);
            JButton button = new JButton("UnFollow");
            GridBagConstraints constr = new GridBagConstraints();
            JPanel panel = addOneField(textField, "username", constr);
            panel.add(button, constr);
            main.add(panel);
            button.addActionListener(e1 -> db.unFollow(textField.getText()));
            pack();
        });
    }
    public void followAction(JMenuItem item){
        item.addActionListener(e -> {
            JTextField textField = new JTextField(50);
            JButton button = new JButton("Follow");
            GridBagConstraints constr = new GridBagConstraints();
            JPanel panel = addOneField(textField, "username", constr);
            panel.add(button, constr);
            main.add(panel);
            button.addActionListener(e1 -> db.follow(textField.getText()));
            pack();
        });
    }
    public void personalTweetsAction(JMenuItem item){
        item.addActionListener(e -> {
            JButton button = new JButton("See personal tweets");
            main.removeAll();
            main.add(button, BorderLayout.CENTER);
            button.addActionListener(e1 -> db.getPersonalTweets());
            pack();
        });
    }
    public void popularTweetsAction(JMenuItem item){
        item.addActionListener(e -> {
            JButton button = new JButton("See popular tweets");
            main.removeAll();
            main.add(button, BorderLayout.CENTER);
            button.addActionListener(e1 -> db.getPopularTweets());
            pack();
        });
    }
    public void getHashtagTweetAction(JMenuItem item){
        item.addActionListener(e -> {
            JTextField textField = new JTextField(50);
            JButton button = new JButton("See tweets of hashtag");
            GridBagConstraints constr = new GridBagConstraints();
            JPanel panel = addOneField(textField, "hashtag", constr);
            panel.add(button, constr);
            main.add(panel);
            button.addActionListener(e1 -> db.get_hashtag_tweets(textField.getText()));
            pack();
        });
    }

    public void sendTweetAction(JMenuItem item){
        item.addActionListener(e -> {
            JTextField textField = new JTextField(50);
            JButton button = new JButton("Send tweet");
            GridBagConstraints constr = new GridBagConstraints();
            JPanel panel = addOneField(textField, "content", constr);
            panel.add(button, constr);
            main.add(panel);
            button.addActionListener(e1 -> db.send_tweet(textField.getText()));
            pack();
        });
    }

    public void loginHistoryAction(JMenuItem item){
        item.addActionListener(e -> {
            JTextField textField = new JTextField(50);
            JButton button = new JButton("Show login history");
            GridBagConstraints constr = new GridBagConstraints();
            JPanel panel = addOneField(textField, "username", constr);
            panel.add(button, constr);
            main.add(panel);
            button.addActionListener(e1 -> db.userLoginHistory(textField.getText()));
            pack();
        });
    }

    public void loginAction(JMenuItem item){
        item.addActionListener(e -> {
            main.removeAll();
            JPanel login = new JPanel();
            login.setLayout(new GridBagLayout());
            GridBagConstraints constr = new GridBagConstraints();
            constr.insets = new Insets(5, 5, 5, 5);
            constr.anchor = GridBagConstraints.WEST;
            JLabel usernameLabel = new JLabel("username");
            constr.gridx = 0;
            constr.gridy = 0;
            login.add(usernameLabel, constr);
            JLabel passwordLabel = new JLabel("password");
            constr.gridx = 0;
            constr.gridy = 1;
            login.add(passwordLabel, constr);
            JTextField username = new JTextField(50);
            JTextField password = new JTextField(50);
            constr.gridx = 1;
            constr.gridy = 0;
            login.add(username, constr);
            constr.gridy = 1;
            login.add(password, constr);
            constr.gridx=1; constr.gridy=2;
            constr.anchor = GridBagConstraints.CENTER;
            JButton loginButton = new JButton("Login");
            loginButton.addActionListener(e1 -> db.login(username.getText(), password.getText()));
            login.add(loginButton, constr);
            main.add(login);
            pack();
        });
    }

    public JPanel addOneField(JTextField textField, String fieldName, GridBagConstraints constr) {
        main.removeAll();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        constr.insets = new Insets(5, 5, 5, 5);
        constr.anchor = GridBagConstraints.WEST;
        JLabel label = new JLabel(fieldName);
        constr.gridx = 0;
        constr.gridy = 0;
        panel.add(label, constr);
        constr.gridx = 1;
        constr.gridy = 0;
        panel.add(textField, constr);
        constr.gridx = 1;
        constr.gridy = 2;
        constr.anchor = GridBagConstraints.CENTER;
        return panel;
    }
    public JPanel addThreeFields(JTextField textField1, JTextField textField2, JTextField textField3, String fieldName1, String fieldName2, String fieldName3, GridBagConstraints constr) {
        main.removeAll();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        constr.insets = new Insets(5, 5, 5, 5);
        constr.anchor = GridBagConstraints.WEST;
        JLabel label1 = new JLabel(fieldName1);
        JLabel label2 = new JLabel(fieldName2);
        JLabel label3 = new JLabel(fieldName3);
        constr.gridx = 0;
        constr.gridy = 0;
        panel.add(label1, constr);
        constr.gridy = 1;
        panel.add(label2, constr);
        constr.gridy = 2;
        panel.add(label3, constr);
        constr.gridx = 1;
        constr.gridy = 0;
        panel.add(textField1, constr);
        constr.gridy = 1;
        panel.add(textField2, constr);
        constr.gridy = 2;
        panel.add(textField3, constr);
        constr.gridx = 1;
        constr.gridy = 3;
        constr.anchor = GridBagConstraints.CENTER;
        return panel;
    }
}
