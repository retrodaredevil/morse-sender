package me.retrodaredevil.morse

private val map: Map<List<Boolean>, Char> = mapOf(
    Pair(listOf(false, true), 'a'),
    Pair(listOf(true, false, false, false), 'b'),
    Pair(listOf(true, false, true, false), 'c'),
    Pair(listOf(true, false, false), 'd'),
    Pair(listOf(false), 'e'),
    Pair(listOf(false, false, true, false), 'f'),
    Pair(listOf(true, true, false), 'g'),
    Pair(listOf(false, false, false, false), 'h'),
    Pair(listOf(false, false), 'i'),
    Pair(listOf(false, true, true, true), 'j'),
    Pair(listOf(true, false, true), 'k'),
    Pair(listOf(false, true, false, false), 'l'),
    Pair(listOf(true, true), 'm'),
    Pair(listOf(true, false), 'n'),
    Pair(listOf(true, true, true), 'o'),
    Pair(listOf(false, true, true, false), 'p'),
    Pair(listOf(true, true, false, true), 'q'),
    Pair(listOf(false, true, false), 'r'),
    Pair(listOf(false, false, false), 's'),
    Pair(listOf(true), 't'),
    Pair(listOf(false, false, true), 'u'),
    Pair(listOf(false, false, false, true), 'v'),
    Pair(listOf(false, true, true), 'w'),
    Pair(listOf(true, false, false, true), 'x'),
    Pair(listOf(true, false, true, true), 'y'),
    Pair(listOf(true, true, false, false), 'z'),


    Pair(listOf(false, true, true, true, true), '1'),
    Pair(listOf(false, false, true, true, true), '2'),
    Pair(listOf(false, false, false, true, true), '3'),
    Pair(listOf(false, false, false, false, true), '4'),

    Pair(listOf(false, false, false, false, false), '5'),

    Pair(listOf(true, false, false, false, false), '6'),
    Pair(listOf(true, true, false, false, false), '7'),
    Pair(listOf(true, true, true, false, false), '8'),
    Pair(listOf(true, true, true, true, false), '9'),
    Pair(listOf(true, true, true, true, true), '0')
)

/**
 * @param codes An array of booleans. Each boolean represents either a dot (".") or a dash ("-"). A true value represents a dash. A false value represents a dot.
 */
inline class MorseChar(
    private val codes: List<Boolean>
){
    constructor(stringValue: String) : this(
        stringValue.toCharArray().map { when(it){
            '-' -> true
            '.' -> false
            else -> throw IllegalArgumentException()
        } }
    )
    constructor(vararg codes: Boolean) : this(listOf<Boolean>(*codes.toTypedArray()))

    override fun toString(): String {
        return codes.joinToString(""){ if(it) "-" else "." }
    }
    val charOrNull: Char?
        get() = map[codes]
    val char: Char
        get() = map[codes] ?: throw MorseCodeParseException(toString() + " is not a valid character!")

    val isValid: Boolean
        get() = codes in map

    operator fun plus(codes: List<Boolean>): MorseChar {
        return MorseChar(mutableListOf<Boolean>().apply{
            addAll(this@MorseChar.codes)
            addAll(codes)
        })
    }
    operator fun plus(char: MorseChar): MorseChar {
        return plus(char.codes)
    }
    operator fun plus(code: Boolean): MorseChar{
        return plus(listOf(code))
    }
    fun plus(vararg codes: Boolean): MorseChar {
        return plus(listOf(*codes.toTypedArray()))
    }
}