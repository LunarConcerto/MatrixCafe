package cafe.lunarconcerto.matrixcafe.api.web.http;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

@Slf4j
public class HttpDownloader {

    public static final OkHttpClient HTTP_CLIENT = new OkHttpClient();

    private static final int DEFAULT_BUFFER_SIZE = 2048;

    private final String url;
    private final @NotNull File savePath ;

    public static @NotNull HttpResult newDownload(String url, @NotNull File savePath) throws IOException {
        return new HttpDownloader(url, savePath).download();
    }

    public HttpDownloader(String url, @NotNull File savePath) {
        this.url = url;
        this.savePath = savePath;
    }


    public @NotNull HttpResult download() throws IOException {
        long downloadedFileSize = 0 ;
        if (savePath.exists()){
            downloadedFileSize = savePath.length();
        }

        Request request = new Request.Builder()
                .url(url)
                .header("RANGE", "bytes="+downloadedFileSize+"-")
                .build();

        Response response = HTTP_CLIENT.newCall(request).execute();
        if (!response.isSuccessful()) {
            return HttpResult.of(HttpStatus.valueOf(response.code()), false) ;
        }

        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            return HttpResult.of(HttpStatus.valueOf(response.code()), false, "下载失败, 没有收到响应体.") ;
        }

        if (downloadedFileSize == responseBody.contentLength()){
            return HttpResult.of(HttpStatus.OK, true, "下载已完成, 无需继续下载.");
        }


        RandomAccessFile raf = new RandomAccessFile(savePath, "rw");
        raf.seek(downloadedFileSize);

        InputStream inputStream = responseBody.byteStream();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int len;


        while ((len = inputStream.read(buffer)) != -1) {
            raf.write(buffer, 0, len);
        }


        raf.close();
        inputStream.close();
        response.close();

        return HttpResult.of(HttpStatus.valueOf(response.code()), true, "下载完成.") ;
    }

}
