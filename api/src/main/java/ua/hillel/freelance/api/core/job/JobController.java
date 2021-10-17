package ua.hillel.freelance.api.core.job;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;
import ua.hillel.freelance.api.core.ApiSettings;
import ua.hillel.freelance.api.core.exception.ApiException;
import ua.hillel.freelance.commons.entity.Job;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class JobController {
    @SneakyThrows
    public Job createJob(String token, Job job) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(gson.toJson(job).getBytes(StandardCharsets.UTF_8));

        Request request = new Request.Builder()
                .post(requestBody)
                .header(ApiSettings.AUTH, token)
                .header(ApiSettings.CONTENT_TYPE, ApiSettings.TYPE_JSON)
                .url(ApiSettings.URL + "/api/job/create")
                .build();

        Response response = new OkHttpClient().newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new ApiException(response.code(), "Cannot create job", response.body().string());
        }

        return gson.fromJson(response.body().string(), Job.class);
    }

    @SneakyThrows
    public Job getJobById(String token, int jobId) {
        Request request = new Request.Builder()
                .get()
                .header(ApiSettings.AUTH, token)
                .url(ApiSettings.URL + "/api/job/" + jobId)
                .build();

        Response response = new OkHttpClient().newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new ApiException(response.code(), "Cannot create job by ID " + jobId, response.body().string());
        }

        return new Gson().fromJson(response.body().string(), Job.class);
    }

    @SneakyThrows
    public List<Job> getAllJobs(String token) {
        Request request = new Request.Builder()
                .get()
                .header(ApiSettings.AUTH, token)
                .url(ApiSettings.URL + "/api/job/all")
                .build();

        Response response = new OkHttpClient().newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new ApiException(response.code(), "Cannot get list of jobs", response.body().string());
        }

        return new Gson().fromJson(response.body().string(), new TypeToken<List<Job>>(){}.getType());
    }

    @SneakyThrows
    public List<Job> getAllUserJobs(String token) {
        Request request = new Request.Builder()
                .get()
                .header(ApiSettings.AUTH, token)
                .url(ApiSettings.URL + "/api/job/user/jobs")
                .build();

        Response response = new OkHttpClient().newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new ApiException(response.code(), "Cannot get list of jobs for current user", response.body().string());
        }

        return new Gson().fromJson(response.body().string(), new TypeToken<List<Job>>(){}.getType());
    }

    @SneakyThrows
    public String deleteJob(String token, int jobId) {
        RequestBody requestBody = RequestBody.create("".getBytes(StandardCharsets.UTF_8));

        Request request = new Request.Builder()
                .post(requestBody)
                .header(ApiSettings.AUTH, token)
                .header(ApiSettings.CONTENT_TYPE, ApiSettings.TYPE_JSON)
                .url(ApiSettings.URL + "/api/job/delete/" + jobId)
                .build();

        Response response = new OkHttpClient().newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new ApiException(response.code(), "Cannot delete job by ID " + jobId, response.body().string());
        }

        return new JSONObject(response.body().string()).get("message").toString();
    }
}
