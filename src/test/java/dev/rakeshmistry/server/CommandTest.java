package dev.rakeshmistry.server;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandTest {
    @Test
    void should_parse_command() {
        Command parsedCommand = Command.parse("NICK nickname");

        assertThat(parsedCommand.command).isEqualTo(Commands.NICK);
        assertThat(parsedCommand.value).isEqualTo("nickname");
    }
}
