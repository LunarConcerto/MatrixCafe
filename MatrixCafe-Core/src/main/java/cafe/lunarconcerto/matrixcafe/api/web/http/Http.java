package cafe.lunarconcerto.matrixcafe.api.web.http;


import cafe.lunarconcerto.matrixcafe.api.util.Json;
import cafe.lunarconcerto.matrixcafe.api.util.Strings;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class Http {
    public static final MediaType JSON_TYPE = MediaType.get("application/json;charset=utf-8");
    public static final OkHttpClient HTTP_CLIENT = new OkHttpClient();

    private Http() {
    }

    public static @NotNull String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            return response.body() != null ? response.body().string() : Strings.EMPTY;
        }
    }

    /**
     * TODO: iter params
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    public static @NotNull String get(String url, String... params) throws IOException {
        return get(url);
    }

    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON_TYPE);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            return response.body() != null ? response.body().string() : Strings.EMPTY;
        }
    }

    public static String post(String url, Object bean) throws IOException {
        return post(url, Json.write(bean));
    }

}
