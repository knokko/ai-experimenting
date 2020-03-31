package reinforcement.gui

import javax.swing.JFrame
import javax.swing.WindowConstants.DISPOSE_ON_CLOSE

fun main() {
    val frame = JFrame()
    frame.setSize(1000, 800)
    frame.defaultCloseOperation = DISPOSE_ON_CLOSE
    frame.title = "Value Iteration"
    frame.add(AddingStatesPanel(frame) { states -> ValueIterationPanel(states) })
    frame.isVisible = true
}