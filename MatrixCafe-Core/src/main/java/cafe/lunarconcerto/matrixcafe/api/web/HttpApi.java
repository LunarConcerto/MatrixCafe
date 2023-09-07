package cafe.lunarconcerto.matrixcafe.api.web;

import cafe.lunarconcerto.matrixcafe.api.util.Strings;
import cafe.lunarconcerto.matrixcafe.api.web.http.HttpMethod;
import io.javalin.http.Handler;
import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * @author LunarConcerto
 * @time 2023/6/14
 */
@Getter
public final class HttpApi {

    private String name = "unnamed_http_api" ;

    private HttpMethod method = HttpMethod.NONE ;

    private String path ;

    private Handler handler ;

    private String description ;

    public HttpApi(String name, HttpMethod method, String path, Handler handler) {
        this(name, method, path, handler, Strings.EMPTY);
    }

    public HttpApi(String name, HttpMethod method, String path, Handler handler, String description) {
        this.name = name;
        this.method = method;
        this.path = path;
        this.handler = handler;
        this.description = description;
    }

    @Contract(pure = true)
    private HttpApi(@NotNull Builder builder) {
        name = builder.name;
        method = builder.method;
        path = builder.path;
        handler = builder.handler;
        description = builder.description;
    }

    @Contract("_, _, _ -> new")
    public static @NotNull HttpApi getAPI(String name, String path, Handler handler){
        return new HttpApi(name, HttpMethod.GET, path, handler);
    }

    public static @NotNull HttpApi getAPI(String name, String path, Handler handler, String description){
        return new HttpApi(name, HttpMethod.GET, path, handler, description);
    }

    @Contract("_, _, _ -> new")
    public static @NotNull HttpApi postAPI(String name, String path, Handler handler){
        return new HttpApi(name, HttpMethod.POST, path, handler);
    }

    public static @NotNull HttpApi postAPI(String name, String path, Handler handler, String description){
        return new HttpApi(name, HttpMethod.POST, path, handler, description);
    }

    public static @NotNull Builder builder(@Nonnull HttpApi copy) {
        Builder builder = new Builder();
        builder.name = copy.getName();
        builder.method = copy.getMethod();
        builder.path = copy.getPath();
        builder.handler = copy.getHandler();
        builder.description = copy.getDescription();
        return builder;
    }

    public static @NotNull Builder builder() {
        return new Builder();
    }


    /**
     * {@code HttpApi} builder static inner class.
     */
    public static final class Builder {
        private String name;
        private HttpMethod method;
        private String path;
        private Handler handler;
        private String description;

        private Builder() {
        }

        @Contract(value = " -> new", pure = true)
        public static @NotNull Builder builder() {
            return new Builder();
        }

        /**
         * Sets the {@code name} and returns a reference to this Builder enabling method chaining.
         *
         * @param name the {@code name} to set
         * @return a reference to this Builder
         */
        @Nonnull
        public Builder withName(@Nonnull String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the {@code method} and returns a reference to this Builder enabling method chaining.
         *
         * @param method the {@code method} to set
         * @return a reference to this Builder
         */
        @Nonnull
        public Builder withMethod(@Nonnull HttpMethod method) {
            this.method = method;
            return this;
        }

        /**
         * Sets the {@code path} and returns a reference to this Builder enabling method chaining.
         *
         * @param path the {@code path} to set
         * @return a reference to this Builder
         */
        @Nonnull
        public Builder withPath(@Nonnull String path) {
            this.path = path;
            return this;
        }

        /**
         * Sets the {@code handler} and returns a reference to this Builder enabling method chaining.
         *
         * @param handler the {@code handler} to set
         * @return a reference to this Builder
         */
        @Nonnull
        public Builder withHandler(@Nonnull Handler handler) {
            this.handler = handler;
            return this;
        }

        /**
         * Sets the {@code description} and returns a reference to this Builder enabling method chaining.
         *
         * @param description the {@code description} to set
         * @return a reference to this Builder
         */
        @Nonnull
        public Builder withDescription(@Nonnull String description) {
            this.description = description;
            return this;
        }

        /**
         * Returns a {@code HttpApi} built from the parameters previously set.
         *
         * @return a {@code HttpApi} built with parameters of this {@code HttpApi.Builder}
         */
        @Nonnull
        public HttpApi build() {
            return new HttpApi(this);
        }
    }
}
