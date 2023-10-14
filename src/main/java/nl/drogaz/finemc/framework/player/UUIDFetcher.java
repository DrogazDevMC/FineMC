package nl.criminalmt.criminalcore.framework.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UUIDFetcher {

    private static final Map<String, String> uuidCache = new HashMap<>();
    private static final Map<UUID, String> nameCache = new HashMap<>();

    public static String fetchUUID(String playerName) throws IOException {
        if (uuidCache.containsKey(playerName)) return uuidCache.get(playerName);
        // Get response from Mojang API
        URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();

        if(connection.getResponseCode() == 400) {
            return "$error";
        }

        InputStream inputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        // Parse JSON response and get UUID
        JsonElement element = new JsonParser().parse(bufferedReader);
        if (!element.isJsonObject()) return "$error";

        JsonObject object = element.getAsJsonObject();
        String uuidAsString = object.get("id").getAsString();

        // Return UUID
        String result = parseUUIDFromString(uuidAsString);
        uuidCache.put(playerName, result);
        return result;
    }

    public static String fetchName(UUID uuid) throws IOException {
        if (nameCache.containsKey(uuid)) return nameCache.get(uuid);

        // Get response from Mojang API
        URL url = new URL("https://api.mojang.com/user/profiles/" + uuid.toString() + "/names");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();

        if(connection.getResponseCode() == 400) {
            return "$error";
        }

        InputStream inputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        // Parse JSON response and return name
        JsonElement element = new JsonParser().parse(bufferedReader);
        if (!element.isJsonArray()) return "$error";

        JsonArray array = element.getAsJsonArray();
        JsonObject object = array.get(array.size() - 1).getAsJsonObject();

        String name = object.get("name").getAsString();
        nameCache.put(uuid, name);
        return name;
    }

    private static String parseUUIDFromString(String uuidAsString) {
        String[] parts = {
                "0x" + uuidAsString.substring(0, 8),
                "0x" + uuidAsString.substring(8, 12),
                "0x" + uuidAsString.substring(12, 16),
                "0x" + uuidAsString.substring(16, 20),
                "0x" + uuidAsString.substring(20, 32)
        };

        long mostSigBits = Long.decode(parts[0]);
        mostSigBits <<= 16;
        mostSigBits |= Long.decode(parts[1]);
        mostSigBits <<= 16;
        mostSigBits |= Long.decode(parts[2]);

        long leastSigBits = Long.decode(parts[3]);
        leastSigBits <<= 48;
        leastSigBits |= Long.decode(parts[4]);

        return new UUID(mostSigBits, leastSigBits).toString();
    }

}