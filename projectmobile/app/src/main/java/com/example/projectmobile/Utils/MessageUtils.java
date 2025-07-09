// File: MessageUtils.java
package com.example.projectmobile.Utils;

import com.example.projectmobile.Notification.model.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MessageUtils {
    public static List<Message> groupMessages(List<Message> messages) {
        Map<Integer, Message> grouped = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());

        for (Message msg : messages) {
            int partnerId;
            if (msg.getMyself() == 1) {
                partnerId = msg.getReceiver_id();
            } else {
                partnerId = msg.getSender_id();
            }

            if (!grouped.containsKey(partnerId)) {
                grouped.put(partnerId, msg);
            } else {
                try {
                    Date existingDate = sdf.parse(grouped.get(partnerId).getSent_at());
                    Date newDate = sdf.parse(msg.getSent_at());
                    if (newDate.after(existingDate)) {
                        grouped.put(partnerId, msg);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ArrayList<>(grouped.values());
    }
}
