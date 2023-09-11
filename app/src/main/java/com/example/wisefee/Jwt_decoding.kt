package com.example.wisefee
import android.util.Base64
import org.json.JSONObject


fun Jwt_decoding(jwtToken: String?): JSONObject? {
    try {
        if (jwtToken != null) {
            val parts = jwtToken.split(".")
            if (parts.size == 3) {
                val payloadBase64 = parts[1]
                val payloadBytes = Base64.decode(payloadBase64, Base64.URL_SAFE)
                val payloadJsonString = String(payloadBytes, Charsets.UTF_8)
                return JSONObject(payloadJsonString)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


