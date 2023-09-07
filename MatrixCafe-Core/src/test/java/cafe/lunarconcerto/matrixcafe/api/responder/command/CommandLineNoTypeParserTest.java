package cafe.lunarconcerto.matrixcafe.api.responder.command;

import cafe.lunarconcerto.matrixcafe.api.data.message.SessionMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LunarConcerto
 * @time 2023/6/22
 */
class CommandLineNoTypeParserTest {

    @Test
    void parse() {
        // 两个参数, 一个选项测试
        CommandLineNoTypeParser parser = new CommandLineNoTypeParser(2, Option.booleanOption("test01", "--o"));

        CommandActionData data = parser.parse(SessionMessage.simpleText("a foo bar --o"));

        System.out.println(data);
    }

    @Test
    void parseError() {
        // 超长参数
        CommandLineNoTypeParser parser = new CommandLineNoTypeParser(2, 4, Option.argumentOption("opt1", "-a"));
        CommandActionData data = parser.parse(SessionMessage.simpleText("a foo bar 114 514 1919 810"));

        System.out.println(data);

        assertTrue(data.isDissatisfy());
        assertEquals(data.getDissatisfyReason(), CommandActionData.DissatisfyReason.ARGUMENT_TOO_LONG);

        // 过短参数
        CommandActionData data2 = parser.parse(SessionMessage.simpleText("a foo"));

        System.out.println(data2);

        assertTrue(data2.isDissatisfy());
        assertEquals(data2.getDissatisfyReason(), CommandActionData.DissatisfyReason.ARGUMENT_TOO_SHORT);

        // 格式错误
        CommandActionData data3 = parser.parse(SessionMessage.simpleText("a foo bar -a"));

        System.out.println(data3);

        assertTrue(data3.isDissatisfy());
        assertEquals(data3.getDissatisfyReason(), CommandActionData.DissatisfyReason.PATTERN_ERROR);
    }


}