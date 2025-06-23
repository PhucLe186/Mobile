package com.example.projectmobile.Module;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

public class DecodeToken {
    public String getUserIdFromToken(String token){
        try{
            String[] parts = token.split("\\.");
            if(parts.length < 2) return null;

            byte[] decodedBytes = Base64.decode(parts[1],Base64.URL_SAFE);
            String decodedPayload = new String(decodedBytes);

            JSONObject jsonObject = new JSONObject(decodedPayload);
            return jsonObject.getString("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
