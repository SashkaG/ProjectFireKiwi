package nano.projects.firekiwi;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

/**
 * Created by Никита on 28.06.2017.
 */

@IgnoreExtraProperties
public class ChatMessage {

    public String id;
    public String type;
    public String sender;//user1 or user2
    public ChatMessage(String messageTexts, String messageUsers, String senders, String recievers,String types) {
        this.messageText = messageTexts;
        this.messageUser = messageUsers;
        this.messageTime = new Date().getTime();
        this.sender=senders;
        this.reciever = recievers;
        this.type=types;
    }
    public ChatMessage()
    {
    }
}