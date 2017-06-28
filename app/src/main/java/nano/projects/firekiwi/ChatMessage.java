package nano.projects.firekiwi;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

/**
 * Created by Никита on 28.06.2017.
 */

@IgnoreExtraProperties
public class ChatMessage {

    public String messageText;
    public String messageUser;
    public long messageTime;
    public String email;
    public String type;
    public String reciever;
    public ChatMessage(String messageTexts, String messageUsers, String emails, String recievers,String types) {
        this.messageText = messageTexts;
        this.messageUser = messageUsers;
        this.messageTime = new Date().getTime();
        this.email=emails;
        this.reciever = recievers;
        this.type=types;
    }
    public ChatMessage()
    {
    }
}