package io.github.amerebagatelle.mods.nuit.components;

import com.mojang.serialization.JsonOps;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MinMaxTest {
    @ParameterizedTest
    @ValueSource(strings = {
            """
                    {
                        "min": 1,
                        "max": 2
                    }
                    """
    })
    public void testCorrectParse(String json) {
        var jsonOb = JsonTestHelper.readJson(json);
        assertDoesNotThrow(() -> MinMaxEntry.CODEC.decode(JsonOps.INSTANCE, jsonOb).getOrThrow().getFirst());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            """
                    {
                    }
                    """,
            """
                    {
                        "min": 1
                    }
                    """,
            """
                    {
                        "max": 1
                    }
                    """,
            """
                    {
                        "min": 2,
                        "max": 1
                    }
                    """
    })
    public void testIncorrectParse(String json) {
        var jsonOb = JsonTestHelper.readJson(json);
        assertThrows(IllegalStateException.class, () -> MinMaxEntry.CODEC.decode(JsonOps.INSTANCE, jsonOb).getOrThrow().getFirst());
    }
}
