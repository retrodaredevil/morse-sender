package me.retrodaredevil.morse

import org.junit.Assert.*
import org.junit.Test

class MorseTest {

    @Test
    fun `test morse char`() {
        assertEquals("---...---", MorseChar("---...---").toString())

        // region alphabet
        assertEquals('a', MorseChar(".-").char)
        assertEquals('b', MorseChar("-...").char)
        assertEquals('c', MorseChar("-.-.").char)
        assertEquals('d', MorseChar("-..").char)
        assertEquals('e', MorseChar(".").char)
        assertEquals('f', MorseChar("..-.").char)
        assertEquals('g', MorseChar("--.").char)
        assertEquals('h', MorseChar("....").char)
        assertEquals('i', MorseChar("..").char)
        assertEquals('j', MorseChar(".---").char)
        assertEquals('k', MorseChar("-.-").char)
        assertEquals('l', MorseChar(".-..").char)
        assertEquals('m', MorseChar("--").char)
        assertEquals('n', MorseChar("-.").char)
        assertEquals('o', MorseChar("---").char)
        assertEquals('p', MorseChar(".--.").char)
        assertEquals('q', MorseChar("--.-").char)
        assertEquals('r', MorseChar(".-.").char)
        assertEquals('s', MorseChar("...").char)
        assertEquals('t', MorseChar("-").char)
        assertEquals('u', MorseChar("..-").char)
        assertEquals('v', MorseChar("...-").char)
        assertEquals('w', MorseChar(".--").char)
        assertEquals('x', MorseChar("-..-").char)
        assertEquals('y', MorseChar("-.--").char)
        assertEquals('z', MorseChar("--..").char)
        // endregion

        assertFalse(MorseChar("---...---").isValid)
        assertTrue(MorseChar(".").isValid)
    }
    @Test
    fun `press receiver test`(){
        val receiver = MorsePressReceiver()
        receiver.onDot()
        receiver.onDot()
        receiver.onDot()
        assertNotNull(receiver.currentChar)
        assertTrue(receiver.finishCharacter())

        receiver.onDash()
        receiver.onDash()
        receiver.onDash()
        assertNotNull(receiver.currentChar)
        assertTrue(receiver.finishCharacter())

        receiver.onDot()
        receiver.onDot()
        receiver.onDot()
        assertNotNull(receiver.currentChar)
        assertTrue(receiver.finishCharacter())

        assertEquals("SOS", receiver.currentString.toUpperCase())
        assertNull(receiver.currentChar)
    }
    @Test
    fun `press receiver test finish character fail`(){
        val receiver = MorsePressReceiver()
        receiver.onDot()
        receiver.onDot()
        receiver.onDash()
        receiver.onDash()
        receiver.onDot()
        assertFalse(receiver.finishCharacter())

        assertEquals("", receiver.currentString)
    }

}