package geekCode02.test4;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * 使用 HttpClient 或 OkHttp 访问 http://localhost:8801
 */

public class OkHttpDemo {
    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient();
        try {
            Request request = new Request.Builder().url("http://localhost:8801/test").build();
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
