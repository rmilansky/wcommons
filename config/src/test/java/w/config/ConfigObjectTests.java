package w.config;

import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author whilein
 */
final class ConfigObjectTests {

    static ConfigParser parser;

    @BeforeAll
    static void setup() {
        parser = YamlConfigParser.create();
    }

    @Test
    void booleanKey() {
        val object = parser.parse("keys:\n" +
                "  yes: 111\n");

        assertFalse(object.isEmpty());
        assertEquals(1, object.size());

        val keys = object.findObject("keys")
                .map(ConfigObject::getKeys)
                .orElse(Collections.emptySet());

        assertEquals(Set.of("yes").toString(), keys.toString());
    }

    @Test
    void integerKey() {
        val object = parser.parse("keys:\n" +
                "  123: 111\n");

        assertFalse(object.isEmpty());
        assertEquals(1, object.size());

        val keys = object.findObject("keys")
                .map(ConfigObject::getKeys)
                .orElse(Collections.emptySet());

        assertEquals(Set.of("123").toString(), keys.toString());
    }

    @Test
    void parseString() {
        val object = parser.parse("message: 'Hello world!'\n");

        assertFalse(object.isEmpty());
        assertEquals(1, object.size());
        assertEquals("Hello world!", object.getString("message"));
    }

    @Test
    void parseInteger() {
        val object = parser.parse("counter: 1234567890\n");

        assertFalse(object.isEmpty());
        assertEquals(1, object.size());
        assertEquals(1234567890, object.getInt("counter"));
    }

    @Test
    void objectList() {
        val object = parser.parse(
                "items:\n" +
                        "  - id: 'a'\n" +
                        "    counter: 0\n" +
                        "  - id: 'b'\n" +
                        "    counter: 1\n" +
                        "  - id: 'c'\n" +
                        "    counter: 2\n"
        );

        assertFalse(object.isEmpty());
        assertEquals(1, object.size());

        val list = object.getObjectList("items");
        assertEquals(3, list.size());

        for (int i = 0; i < list.size(); i++) {
            val item = list.get(i);

            val id = item.getString("id");
            val counter = item.getInt("counter");

            assertEquals(Character.toString('a' + i), id);
            assertEquals(i, counter);
        }
    }

}