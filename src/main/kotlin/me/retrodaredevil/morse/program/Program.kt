package me.retrodaredevil.morse.program

import me.retrodaredevil.morse.PressReceiver
import java.awt.Toolkit
import java.awt.event.KeyEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.util.concurrent.atomic.AtomicInteger
import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.KeyStroke

class Program {
    private val receiver = PressReceiver()

    @Volatile var pressed = false
    private var wasPressed = false
    private var timePressed = 0L

    val backspaceCount = AtomicInteger()
    val spaceCount = AtomicInteger()

    fun update(){
        val pressed = this.pressed
        val now = System.currentTimeMillis()
        val period = now - timePressed
        if(pressed && !wasPressed){ // just pressed
            timePressed = now
        } else if(!pressed && wasPressed){ // just released
            onRelease(period)
            timePressed = now
        } else if(!pressed && period > 330 && receiver.isTyping){
            receiver.finishCharacter()
        }
        wasPressed = pressed

        val backspace = backspaceCount.get()
        for(i in 0 until backspace){
            receiver.backspace()
        }
        backspaceCount.addAndGet(-backspace)

        val space = spaceCount.get()
        for(i in 0 until space){
            receiver.add(" ")
        }
        spaceCount.addAndGet(-space)

        println(receiver.currentString)
        println(receiver.currentChar)
    }
    private fun onRelease(time: Long){
        if(time < 150){
            receiver.onDot()
        } else {
            receiver.onDash()
        }
    }
}

fun main(){
    val program = Program()
    val frame = JFrame()

    frame.setSize(400, 350)
    frame.addWindowListener(object : WindowAdapter(){
        override fun windowClosing(e: WindowEvent?) {
            println("closed")
            System.exit(0)
        }
    })
    val panel = frame.contentPane as JPanel
    panel.registerKeyboardAction({
        program.pressed = true
    }, KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), JComponent.WHEN_IN_FOCUSED_WINDOW)
    panel.registerKeyboardAction({
        program.pressed = false
    }, KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), JComponent.WHEN_IN_FOCUSED_WINDOW)

    panel.registerKeyboardAction({
        program.backspaceCount.incrementAndGet()
    }, KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0, false), JComponent.WHEN_IN_FOCUSED_WINDOW)
    panel.registerKeyboardAction({
        program.spaceCount.incrementAndGet()
    }, KeyStroke.getKeyStroke(KeyEvent.VK_C, 0, false), JComponent.WHEN_IN_FOCUSED_WINDOW)

    frame.isVisible = true
    println("finished initializing")
    while(true){
        program.update()
        Thread.sleep(10)
    }
}