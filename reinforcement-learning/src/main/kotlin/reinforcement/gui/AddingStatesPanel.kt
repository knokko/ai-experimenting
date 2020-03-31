package reinforcement.gui

import reinforcement.model.State
import java.awt.Color
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.JFrame
import javax.swing.JPanel

internal class AddingStatesPanel(
        private val frame: JFrame, private val finalPanel: (List<PointState>) -> JPanel) : JPanel(), MouseListener, KeyListener {

    private val states: MutableList<PointStateBuilder> = ArrayList()

    init {
        addMouseListener(this)
        frame.addKeyListener(this)
    }

    override fun paint(graph: Graphics) {
        graph.color = Color(182, 182, 182)
        graph.fillRect(0, 0, width, height)

        graph.color = Color(60, 153, 200)
        for (state in states)
            graph.fillOval(state.x - 20, state.y - 20, 40, 40)
    }

    override fun mouseReleased(e: MouseEvent?) {}

    override fun mouseEntered(e: MouseEvent?) {}

    override fun mouseClicked(event: MouseEvent?) {
        if (event!!.button == MouseEvent.BUTTON1) {
            states.add(PointStateBuilder(event.x, event.y))
            repaint()
        }
        if (event.button == MouseEvent.BUTTON3) {

            var nearestState: PointStateBuilder? = null
            for (state in states) {
                if (nearestState == null || state.distance(event.x, event.y) < nearestState.distance(event.x, event.y))
                    nearestState = state
            }

            if (nearestState != null) {
                states.remove(nearestState)
                repaint()
            }
        }
    }

    override fun mouseExited(e: MouseEvent?) {}

    override fun mousePressed(e: MouseEvent?) {}

    override fun keyTyped(e: KeyEvent?) {}

    override fun keyPressed(event: KeyEvent?) {
        if (event!!.keyCode == KeyEvent.VK_ENTER) {
            frame.remove(this)
            frame.removeKeyListener(this)
            frame.add(AddingActionsPanel(frame, finalPanel, states))
            frame.revalidate()
        }
    }

    override fun keyReleased(e: KeyEvent?) {}
}