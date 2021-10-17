package ua.hillel.freelance.api.core.auth;

import lombok.SneakyThrows;
import okhttp3.*;
import org.json.JSONObject;
import ua.hillel.freelance.api.core.ApiSettings;
import ua.hillel.freelance.api.core.exception.ApiException;
import ua.hillel.freelance.commons.entity.User;

import java.nio.charset.StandardCharsets;

public class AuthController {
    @SneakyThrows
    public void registerUser(User user) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", user.getUsername());
        jsonObject.put("password", user.getPassword());
        jsonObject.put("confirmPassword", user.getConfirmPassword());

        RequestBody requestBody = RequestBody.create(jsonObject.toString().getBytes(StandardCharsets.UTF_8));

        Request request = new Request.Builder()
                .post(requestBody)
                .url(ApiSettings.URL + "/api/auth/signup")
                .header(ApiSettings.CONTENT_TYPE, ApiSettings.TYPE_JSON)
                .build();

        OkHttpClient client = new OkHttpClient();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new ApiException(response.code(), "User registration failed", response.body().string());
        }
    }

    @SneakyThrows
    public String login(User user) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", user.getUsername());
        jsonObject.put("password", user.getPassword());

        RequestBody requestBody = RequestBody.create(jsonObject.toString().getBytes(StandardCharsets.UTF_8));

        Request request = new Request.Builder()
                .post(requestBody)
                .url(ApiSettings.URL + "/api/auth/signin")
                .header(ApiSettings.CONTENT_TYPE, ApiSettings.TYPE_JSON)
                .build();

        OkHttpClient client = new OkHttpClient();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new ApiException(response.code(), "User login failed", response.body().string());
        }

        return new JSONObject(response.body().string()).get("token").toString();
    }
}
