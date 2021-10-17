package ua.hillel.freelance.api.core.comment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ua.hillel.freelance.api.core.ApiSettings;
import ua.hillel.freelance.api.core.exception.ApiException;
import ua.hillel.freelance.commons.entity.Comment;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class CommentController {
    @SneakyThrows
    public Comment createComment(String token, int jobId, Comment comment) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(gson.toJson(comment).getBytes(StandardCharsets.UTF_8));

        Request request = new Request.Builder()
                .post(requestBody)
                .url(ApiSettings.URL + "/api/comment/" +jobId + "/create" )
                .header(ApiSettings.CONTENT_TYPE, ApiSettings.TYPE_JSON)
                .header(ApiSettings.AUTH, token)
                .build();

        Response response = new OkHttpClient().newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new ApiException(response.code(), "Cannot create comment for job with ID " + jobId, response.body().string());
        }

        return gson.fromJson(response.body().string(), Comment.class);
    }

    @SneakyThrows
    public List<Comment> getAllCommentsForJob(String token, int jobId) {
        Request request = new Request.Builder()
                .get()
                .url(ApiSettings.URL + "/api/comment/" +jobId + "/all" )
                .header(ApiSettings.AUTH, token)
                .build();

        Response response = new OkHttpClient().newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new ApiException(response.code(), "Cannot create comment for job with ID " + jobId, response.body().string());
        }

        String body = response.body().string();
        return new Gson().fromJson(body, new TypeToken<List<Comment>>(){}.getType());
    }
}
