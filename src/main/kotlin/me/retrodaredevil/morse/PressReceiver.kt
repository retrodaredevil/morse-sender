package me.retrodaredevil.morse

class PressReceiver {
    var currentString: String = ""
        private set
    var currentChar: MorseChar? = null
        private set

    fun onDot() {
        val currentChar = this.currentChar ?: MorseChar()
        this.currentChar = currentChar + false
    }

    fun onDash() {
        val currentChar = this.currentChar ?: MorseChar()
        this.currentChar = currentChar + true
    }

    fun finishCharacter(): Boolean {
        val currentChar = this.currentChar ?: throw IllegalStateException()
        this.currentChar = null

        val char = currentChar.charOrNull ?: return false
        currentString += char
        return true
    }
    fun backspace(): Boolean {
        if(currentString.isEmpty()){
            return false
        }
        currentString = currentString.substring(0, currentString.length - 1)
        return true
    }
    fun add(string: String){
        currentString += string
    }
    fun clear(clearCurrentChar: Boolean) {
        currentString = ""
        if(clearCurrentChar){
            clearCurrentChar()
        }
    }
    fun clearCurrentChar(){
        currentChar = null
    }

    val isTyping: Boolean
        get() = currentChar != null

}