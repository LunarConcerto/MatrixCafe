package cafe.lunarconcerto.matrixcafe.api.config.model;

import cafe.lunarconcerto.matrixcafe.api.common.DirectoryConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseConnectionInfo {

    public static final String SQLITE_FILE_NAME = "data.sqlite";

    /**
     * 默认使用SQLITE,
     * 以保证大多数时候可以正常启动
     */
    private DatabaseType type = DatabaseType.SQLITE;

    private String url = defaultSqlUrl();

    private String username = "";

    private String password = "";


    @Contract(pure = true)
    public static @NotNull String defaultSqlUrl(){
        return "jdbc:sqlite:" + DirectoryConstants.CONFIG_DIR.get() + SQLITE_FILE_NAME ;
    }

}
