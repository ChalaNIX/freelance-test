package ua.hillel.freelance.api.core.image;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import okhttp3.*;
import org.json.JSONObject;
import ua.hillel.freelance.api.core.ApiSettings;
import ua.hillel.freelance.api.core.exception.ApiException;
import ua.hillel.freelance.commons.entity.Image;

import java.io.File;

public class ImageController {
    @SneakyThrows
    public String uploadImage(String token, File file) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("image/jpeg"), file))
                .build();

        Request request = new Request.Builder()
                .post(requestBody)
                .url(ApiSettings.URL + "/api/image/upload")
                .header(ApiSettings.AUTH, token)
                .header(ApiSettings.CONTENT_TYPE, "multipart/form-data")
                .build();

        Response response = new OkHttpClient().newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new ApiException(response.code(), "Cannot upload image");
        }

        return new JSONObject(response.body().string()).get("message").toString();
    }

    @SneakyThrows
    public Image getUserImage(String token) {
        Request request = new Request.Builder()
                .get()
                .url(ApiSettings.URL + "/api/image/profile")
                .header(ApiSettings.AUTH, token)
                .build();

        Response response = new OkHttpClient().newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new ApiException(response.code(), "Cannot upload image");
        }

        return new Gson().fromJson(response.body().string(), Image.class);
    }
}
