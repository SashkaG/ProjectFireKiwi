package nano.projects.firekiwi;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.Map;

/**
 * Created by Никита on 28.06.2017.
 */

@IgnoreExtraProperties
public class ChatMessage {

    public String id;
    public String type;
    public boolean fromTo;
    public Map<String,String> data;
    public long time;
    public ChatMessage(String p_id,boolean p_fromTo,Map<String,String> p_data,String p_type) {
        this.id=p_id;
        this.fromTo=p_fromTo;
        this.data=p_data;
        this.type=p_type;
        this.time = new Date().getTime();
    }
    public ChatMessage()
    {

    }
}