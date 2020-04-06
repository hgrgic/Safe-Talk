package io.safe.talk.core.commands.impl.keys;

import static org.junit.Assert.*;

import org.junit.Test;

public class InspectSecureKeyLocationCommandTest {

    @Test
    public void execute() {
        assertTrue(new InspectSecureKeyLocationCommand().execute());
    }
}
