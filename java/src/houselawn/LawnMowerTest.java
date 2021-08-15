package houselawn;

import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class LawnMowerTest {
    @org.junit.jupiter.api.Test
    void doesNotThrowOnValidLine() throws LawnMowerParseException {
        LawnMower.fromLine("a,1,1,1,1");
    }

    @org.junit.jupiter.api.Test
    void emptyLine() {
        Assertions.assertThrows(LawnMowerParseException.class, () -> LawnMower.fromLine(""));
    }

    @org.junit.jupiter.api.Test
    void tooManyParameters() {
        Assertions.assertThrows(LawnMowerParseException.class, () -> LawnMower.fromLine("a,1,1,1,1,1"));
    }

    @org.junit.jupiter.api.Test
    void notANumber() {
        Assertions.assertThrows(LawnMowerParseException.class, () -> LawnMower.fromLine("a,a,1,1,1"));
    }

    @org.junit.jupiter.api.Test
    void lowerBound() {
        Assertions.assertThrows(LawnMowerParseException.class, () -> LawnMower.fromLine("a,0,1,1,1"));
    }

    @org.junit.jupiter.api.Test
    void upperBound() {
        Assertions.assertThrows(LawnMowerParseException.class, () -> LawnMower.fromLine("a,1000000,1,1,1"));
    }

    @org.junit.jupiter.api.Test
    void overflow() {
        Assertions.assertThrows(LawnMowerParseException.class, () -> LawnMower.fromLine("a,10000000000000000,1,1,1"));
    }

    @org.junit.jupiter.api.Test
    void willCutLawnExactlyOneWeek() throws LawnMowerParseException {
        assertTrue(LawnMower.fromLine("tt,100,2,10080,10080").willCutLawnFasterThanOneWeek(10080));
    }

    @org.junit.jupiter.api.Test
    void willCutLawnSlowerThanOneWeek() throws LawnMowerParseException {
        assertFalse(LawnMower.fromLine("tt,100,1,10080,10080").willCutLawnFasterThanOneWeek(10080));
    }

    @org.junit.jupiter.api.Test
    void willCutLawnFasterThanOneWeek() throws LawnMowerParseException {
        assertTrue(LawnMower.fromLine("tt,100,3,10080,10080").willCutLawnFasterThanOneWeek(10080));
    }

    @org.junit.jupiter.api.Test
    void intermediateIntegerOverflow() throws LawnMowerParseException {
        assertTrue(LawnMower.fromLine("tt,1000,56,7453,6535").willCutLawnFasterThanOneWeek(98955));
    }
}