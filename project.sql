use ghanari;
create table user(firstname varchar(20) not null, lastname varchar(20) not null, username varchar(20) primary key, passwd varchar(128) not null, birthday date not null, membership_date datetime default current_timestamp, biography varchar(64) not null);
create table tweet(content varchar(256) not null, sender varchar(20) not null, send_date datetime default current_timestamp, ID int auto_increment primary key, reply_id int, foreign key(sender)references user(username), foreign key(reply_id) references tweet(ID));
create table blocking(username varchar(20) not null, blocked_username varchar(20) not null, foreign key(username) references user(username), foreign key(blocked_username) references user(username), unique(username, blocked_username));
create table follow(username varchar(20) not null, followed_username varchar(20) not null, foreign key(username) references user(username), foreign key(followed_username) references user(username), unique(username, followed_username));
create table hashtag(txt char(6) primary key);
create table log_records(username varchar(20) not null, login_time datetime default current_timestamp, foreign key(username) references user(username));
create table message(ID int auto_increment primary key,sender varchar(20) not null, receiver varchar(20) not null, send_date  datetime default current_timestamp, foreign key(sender) references user(username), foreign key(receiver) references user(username));
create table message_tweet(tweet_id int not null, ID int not null, foreign key(tweet_id) references tweet(ID), foreign key(ID) references message(ID));
create table message_txt(txt varchar(256) not null, ID int not null, foreign key(ID) references message(ID));
create table tweet_like(tweet_id int not null, username varchar(20) not null, foreign key(tweet_id) references tweet(ID), foreign key(username) references user(username), unique(tweet_id, username));
create table tweet_hashtag(tweet_id int not null, txt char(6) not null, foreign key(tweet_id) references tweet(ID), foreign key(txt) references hashtag(txt));
create table log_send_tweet(tweet_sender varchar(20) not null, tweet_id int, tweet_date datetime not null, primary key(tweet_id));
create table log_create_account(username varchar(20), create_time datetime not null, primary key(username));

DELIMITER //
create procedure send_tweet(in tweet_content varchar(256))
begin
    call get_user(@usrname);
	insert into tweet(content, sender) values(tweet_content, @usrname);
end//

DELIMITER //
create procedure send_tweet_message(in tweet_content varchar(256), in tweet_sender varchar(20), in tweet_send_date datetime, in msg_receiver varchar(20), out done int)
begin
	call get_user(@sender);
    set done = 0;
	if @sender not in(select blocked_username from blocking where username = msg_receiver) then
		if @sender not in(select blocked_username from blocking where username = tweet_sender) then
			insert into message(sender, receiver) values (@sender, msg_receiver);
			if exists(select * from tweet, message where content = tweet_content and tweet.sender = tweet_sender and tweet.send_date = tweet_send_date and message.sender = @sender and message.receiver = msg_receiver and message.send_date = current_timestamp()) then
				insert into message_tweet(tweet_id, ID)
				select tweet.ID, message.ID
				from tweet, message 
				where content = tweet_content and tweet.sender = tweet_sender and tweet.send_date = tweet_send_date
				and message.sender = @sender and message.receiver = msg_receiver and message.send_date = current_timestamp();
				set done = 1;
			else 
				delete from message order by ID desc limit 1;
			end if;
		end if;
	end if;
end //

DELIMITER //
create procedure send_txt_message(in msg_receiver varchar(20), in msg_content varchar(256), out done int)
begin
	call get_user(@sender);
    set done = 0;
    if @sender not in(select blocked_username from blocking where username = msg_receiver) then
		insert into message(sender, receiver) values (@sender, msg_receiver);
		insert into message_txt(txt, ID)
		select msg_content, ID
		from message
		where sender = @sender and receiver = msg_receiver and send_date = current_timestamp();
		set done = 1;
	end if;
end //

DELIMITER //
create procedure add_hashtag(in hashtag_content char(6), in tweet_content varchar(256), in tweet_sender varchar(20))
begin
	insert into hashtag(txt) 
	select hashtag_content
	where not exists( select txt from hashtag where txt like hashtag_content);

	insert into tweet_hashtag(tweet_id, txt)
	select ID, hashtag_content from tweet where content = tweet_content and sender = tweet_sender and send_date = current_timestamp();
end //

DELIMITER //
create procedure create_account(in firstname varchar(20), in lastname varchar(20), in username varchar(20), in passwd varchar(128), in birthday date, in biography varchar(64))
begin
	insert into user(firstname, lastname, username, passwd, birthday, biography) 
	values(firstname, lastname, username, md5(passwd), birthday, biography);
end //

DELIMITER //
create procedure unblocking(in unblocked_username varchar(20), out done int)
begin
	set done = 0;
	call get_user(@usrname);
    if exists(select * from blocking where username = @usrname and blocked_username = unblocked_username) then
		delete from blocking where username = @usrname and blocked_username = unblocked_username;
        set done = 1;
	end if;
end //

DELIMITER //
create procedure userLoginHistory(in usrname varchar(20))
begin
	select login_time from log_records where username = usrname order by login_time desc;
end //

DELIMITER //
create procedure likeTweet(in content varchar(256), in sender varchar(20), in send_date datetime)
begin
	call get_user(@usrname);
	insert into tweet_like(tweet_id, username)
	select x.ID, @usrname
	from tweet as x
	where x.content = content and x.sender = sender and x.send_date = send_date
		  and @usrname not in (select blocked_username
								from blocking
								where username = sender);
end //

DELIMITER //
create procedure unfollow(in unfollow_username varchar(20), out done int)
begin
	set done = 0;
	call get_user(@usrname);
    if exists(select * from follow where username = @usrname and followed_username = unfollow_username) then
		delete from follow where username = @usrname and followed_username = unfollow_username;
        set done = 1;
	end if;
end //

DELIMITER //
create procedure getPopularTweets()
begin
	call get_user(@usrname);
	select content, count(x.username) as like_numbers
	from tweet_like as x right outer join tweet as y
	on y.ID = x.tweet_id where @usrname not in (select blocked_username from blocking where username = sender)
	group by y.ID
	order by like_numbers desc;
end //

DELIMITER //
create procedure getPersonalTweets()
begin
	call get_user(@usrname);
	select content from tweet where sender = @usrname;
end //

DELIMITER //
create procedure get_hashtag_tweets(in hashtag char(6))
begin
	call get_user(@usrname);
	select content, sender, send_date
	from tweet_hashtag, tweet
	where tweet_hashtag.txt = hashtag and
	tweet.ID = tweet_hashtag.tweet_id and
	@usrname not in (select blocked_username
					from blocking
					where sender = username)
	order by send_date desc;
end //

DELIMITER //
create procedure getLikeNumber(in content varchar(256), in sender varchar(20), in send_date datetime)
begin
	call get_user(@usrname);
	select count(*) as like_numbers
	from tweet_like as x, tweet as y
	where y.ID = x.tweet_id and @usrname not in (select blocked_username from blocking where username = sender)
		  and y.content = content and y.sender = sender and y.send_date = send_date;
end //

DELIMITER //
create procedure getFollowingActivity()
begin
	call get_user(@usrname);
	select content, sender, send_date
	from tweet, follow
	where follow.username = @usrname and 
		  sender = follow.followed_username and
		  sender not in(select username
						from blocking
						where blocked_username = @usrname)
	order by send_date desc;
end //

DELIMITER //
create procedure getUsersActivity(in sender_user varchar(20))
begin
	call get_user(@usrname);
	select content, sender, send_date
	from tweet
	where sender = sender_user and
		  sender not in(select username
						from blocking
						where blocked_username = @usrname);
end //

DELIMITER //
create procedure get_msg_sender_list()
begin
	call get_user(@viewer);
	select message.sender as message_sender, max(message.send_date) as message_send_date
	from message
	where receiver = @viewer and receiver not in(select blocked_username from blocking where username = message.sender)
	group by message.sender
	order by message_send_date desc;
end //

DELIMITER //
create procedure getLikersList(in content varchar(256), in sender varchar(20), in send_date datetime)
begin
	call get_user(@usrname);
	select username
	from tweet_like as x, tweet as y
	where y.ID = x.tweet_id and @usrname not in (select blocked_username from blocking where username = sender)
		  and y.content = content and y.sender = sender and y.send_date = send_date
		  and x.username not in (select username
							    from blocking
							    where blocked_username = @usrname);
end //

DELIMITER //
create procedure get_user_message(in msg_sender varchar(20))
begin
	call get_user(@viewer);
    select content as message_txt, message.send_date as message_send_date
	from message, message_tweet, tweet
	where message.ID = message_tweet.ID and tweet.ID = message_tweet.tweet_id and receiver = @viewer and message.sender = msg_sender
	and receiver not in(select blocked_username from blocking where username = tweet.sender)
	union
	select txt as message_txt, message.send_date as message_send_date
	from message, message_txt
	where  message.ID = message_txt.ID and receiver = @viewer and message.sender = msg_sender
	order by message_send_date desc;
end //

DELIMITER //
create procedure getTweetComment(in content varchar(256), in sender varchar(20), in send_date datetime)
begin
	call get_user(@usrname);
	select y.content, y.sender, y.send_date
	from tweet as y
	where y.reply_id = (select x.ID from tweet as x where x.content = content and x.sender = sender and x.send_date = send_date and @usrname not in(select blocked_username from blocking where username = x.sender))
		  and @usrname not in(select blocked_username from blocking where username = y.sender);
end //

DELIMITER //
create procedure follow(in followed_username varchar(20))
begin
	call get_user(@usrname);
	insert into follow(username, followed_username) values(@usrname, followed_username);
end //

DELIMITER //
create procedure get_user(out usrname varchar(20))
begin
	select username into usrname from log_records order by login_time desc limit 1 ;
end //

DELIMITER //
create procedure blocking(in blocked_username varchar(20))
begin
	call get_user(@usrname);
	insert into blocking(username, blocked_username) values(@usrname, blocked_username);
end //

DELIMITER //
create procedure add_comment(in tweet_content varchar(256), in tweet_sender varchar(20), in tweet_send_date datetime, in comment_content varchar(256))
begin
	call get_user(@usrname);
	insert into tweet(content, sender, reply_id)
	select comment_content, @usrname, x.ID from tweet as x where @usrname not in (select blocked_username from blocking where username = tweet_sender)
	and x.content = tweet_content and x.sender = tweet_sender and x.send_date = tweet_send_date;
end //

create procedure login(in usrname varchar(20), in pssword varchar(128), out done int)
begin
	declare count int default 0;
	select count(*)
    into count
    from user
    where username = usrname and passwd = md5(pssword);
	if count = 1 then
		insert into log_records(username) values(usrname);
        set done = 1;
	else
		set done = 0;
    end if;
end //

DELIMITER //
create trigger add_hashtags after insert on tweet for each row
begin
	DECLARE sub_char char(1);
    DECLARE hashtag_content varchar(256);
	DECLARE len INT DEFAULT 0;
	set len = CHAR_LENGTH(new.content);
	while len > 0 do
		set sub_char = substring(new.content, CHAR_LENGTH(new.content) + 1 - len, 1);
        if sub_char like '#' then
			set hashtag_content = substring(new.content, CHAR_LENGTH(new.content) + 1 - len, 6);
			if substring(hashtag_content, 2, 5) like '_____' and substring(hashtag_content, 2, 5) regexp '^[a-zA-Z]+$' then
				call add_hashtag(hashtag_content, new.content, new.sender);
			end if;
		end if;
        set len = len - 1;
	end while;
end //

DELIMITER //
create trigger create_tweet after insert on tweet for each row
begin
	insert into log_send_tweet(tweet_sender, tweet_id, tweet_date) values (new.sender, new.ID, new.send_date);
end //

DELIMITER //
create trigger create_account after insert on user for each row
begin
	insert into log_create_account(username, create_time) values (new.username, new.membership_date);
end //