package io.safe.talk.core.commands.impl.keys;

import static org.junit.Assert.*;

import org.junit.Test;

public class InspectSecureKeyLocationTest {

    @Test
    public void execute() {
        assertTrue(new InspectSecureKeyLocation().execute());
    }
}