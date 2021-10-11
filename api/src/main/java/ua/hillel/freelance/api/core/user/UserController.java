package ua.hillel.freelance.api.core.user;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;
import ua.hillel.freelance.api.core.ApiSettings;
import ua.hillel.freelance.api.core.exception.ApiException;
import ua.hillel.freelance.commons.entity.User;

import java.nio.charset.StandardCharsets;

public class UserController {
    @SneakyThrows
    public User getCurrentUser(String token) {
        Request request = new Request.Builder()
                .url(ApiSettings.URL + "/api/user/")
                .header(ApiSettings.AUTH, " " + token)
                .get()
                .build();

        OkHttpClient client = new OkHttpClient();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new ApiException(response.code(), "Cannot get current user info");
        }

        return new Gson().fromJson(response.body().string(), User.class);
    }

    @SneakyThrows
    public User getUserById(String token, int userId) {
        Request request = new Request.Builder()
                .url(ApiSettings.URL + "/api/user/" + userId)
                .header(ApiSettings.AUTH, token)
                .get()
                .build();

        OkHttpClient client = new OkHttpClient();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new ApiException(response.code(), "Cannot get user info by ID " + userId);
        }

        return new Gson().fromJson(response.body().string(), User.class);
    }

    @SneakyThrows
    public User updateUser(String token, User user) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", user.getId());
        jsonObject.put("username", user.getUsername());
        jsonObject.put("name", user.getName());
        jsonObject.put("lastname", user.getLastname());

        RequestBody requestBody = RequestBody.create(jsonObject.toString().getBytes(StandardCharsets.UTF_8));

        Request request = new Request.Builder()
                .post(requestBody)
                .url(ApiSettings.URL + "/api/user/update")
                .header(ApiSettings.CONTENT_TYPE, ApiSettings.TYPE_JSON)
                .header(ApiSettings.AUTH, token)
                .build();

        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new ApiException(response.code(), "Cannot update user");
        }

        return new Gson().fromJson(response.body().string(), User.class);
    }
}
