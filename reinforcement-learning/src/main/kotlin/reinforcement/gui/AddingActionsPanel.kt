package reinforcement.gui

import reinforcement.model.SimpleAction
import reinforcement.model.SimpleState
import reinforcement.model.State
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.*
import java.awt.event.KeyEvent.VK_ENTER
import java.awt.event.MouseEvent.BUTTON1
import java.awt.event.MouseEvent.BUTTON3
import java.lang.Double.POSITIVE_INFINITY
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.math.sqrt

internal class AddingActionsPanel(
        private val frame: JFrame,
        private val nextPanel: (List<PointState>) -> JPanel,
        pointStates: List<PointStateBuilder>) : JPanel(),
        MouseListener, MouseWheelListener, KeyListener {

    val states: List<ActionsStateBuilder>

    var stateToConnect: ActionsStateBuilder? = null

    init {
        states = pointStates.map { point -> ActionsStateBuilder(point.x, point.y) }

        addMouseListener(this)
        addMouseWheelListener(this)
        frame.addKeyListener(this)
    }

    private fun getNearestState(x: Int, y: Int): ActionsStateBuilder? {

        var nearest: ActionsStateBuilder? = null

        for (state in states)
            if (nearest == null || state.distance(x, y) < nearest.distance(x, y))
                nearest = state

        return nearest
    }

    private fun getNearestAction(x: Int, y: Int): ActionBuilder? {

        var nearest: ActionBuilder? = null
        var nearestDistanceSq = POSITIVE_INFINITY

        for (state in states) {
            for (action in state.actions) {

                val actionDX = action.nextState.x - state.x
                val actionDY = action.nextState.y - state.y

                val actionLength = sqrt((actionDX * actionDX + actionDY * actionDY).toDouble())

                val dx = x - state.x
                val dy = y - state.y

                val length = sqrt((dx * dx + dy * dy).toDouble())

                if (length == 0.0)
                    return action

                val nearestX = state.x + actionDX * (length / actionLength).coerceIn(0.0, 1.0)
                val nearestY = state.y + actionDY * (length / actionLength).coerceIn(0.0, 1.0)

                val distanceSq = (x - nearestX) * (x - nearestX) + (y - nearestY) * (y - nearestY)
                if (distanceSq < nearestDistanceSq || nearest == null) {
                    nearest = action
                    nearestDistanceSq = distanceSq
                }
            }
        }

        return nearest
    }

    override fun mouseEntered(e: MouseEvent?) {}

    override fun mouseClicked(event: MouseEvent?) {
        if (event!!.button == BUTTON1) {
            if (stateToConnect != null) {

                val destState = getNearestState(event.x, event.y)
                var shouldAdd = true
                if (destState != stateToConnect) {

                    // Check if we already have that action
                    for (action in stateToConnect!!.actions) {
                        if (action.nextState == destState) {
                            shouldAdd = false
                            break
                        }
                    }

                    if (shouldAdd)
                        stateToConnect!!.actions.add(ActionBuilder(destState!!, 0))
                }

                stateToConnect = null

                repaint()
            } else {

                stateToConnect = getNearestState(event.x, event.y)
                if (stateToConnect != null)
                    repaint()
            }
        }

        if (event.button == BUTTON3) {

            val action = getNearestAction(event.x, event.y)

            if (action != null) {

                for (state in states)
                    state.actions.remove(action)

                repaint()
            }
        }
    }

    override fun mouseExited(e: MouseEvent?) {}

    override fun mousePressed(e: MouseEvent?) {}

    override fun mouseWheelMoved(event: MouseWheelEvent?) {
        val action = getNearestAction(event!!.x, event.y)
        if (action != null) {

            action.reward += event.unitsToScroll * 5
            if (action.reward > 255)
                action.reward = 255
            else if (action.reward < -255)
                action.reward = -255
            else
                repaint()
        }
    }

    override fun keyTyped(e: KeyEvent?) {}

    override fun keyPressed(event: KeyEvent?) {
        if (event!!.keyCode == VK_ENTER) {

            val finalStates = states.mapIndexed { index, state ->
                PointState(state.x, state.y, index, state.actions.map { action ->
                    SimpleAction(states.indexOf(action.nextState), action.reward.toDouble())
                })
            }

            frame.remove(this)
            frame.removeKeyListener(this)
            frame.add(nextPanel(finalStates))
            frame.revalidate()
        }
    }

    override fun keyReleased(e: KeyEvent?) {}

    override fun mouseReleased(e: MouseEvent?) {}

    override fun paint(graph: Graphics) {
        graph.color = Color(182, 182, 182)
        graph.fillRect(0, 0, width, height)

        for (state in states) {
            if (state == stateToConnect)
                graph.color = Color(150, 200, 100)
            else
                graph.color = Color(60, 153, 200)
            graph.fillOval(state.x - 20, state.y - 20, 40, 40)
        }

        val graph2d = graph as Graphics2D
        graph2d.stroke = BasicStroke(3f)

        for (state in states) {
            for (action in state.actions) {

                if (action.reward >= 0)
                    graph.color = Color(0, (16.0 * sqrt(action.reward.toDouble())).toInt(), 0)
                else
                    graph.color = Color((16.0 * sqrt(-action.reward.toDouble())).toInt(), 0, 0)

                graph.drawLine(state.x, state.y, action.nextState.x, action.nextState.y)

                val dx = action.nextState.x - state.x
                val dy = action.nextState.y - state.y

                val length = sqrt((dx * dx + dy * dy).toDouble())
                val dirX = dx / length
                val dirY = dy / length

                val baseX = state.x + 0.9 * dx
                val baseY = state.y + 0.9 * dy
                val arrowSize = 15
                val arrowX1 = baseX + dirY * arrowSize
                val arrowY1 = baseY - dirX * arrowSize
                val arrowX2 = baseX - dirY * arrowSize
                val arrowY2 = baseY + dirX * arrowSize

                graph.drawLine(action.nextState.x, action.nextState.y, arrowX1.toInt(), arrowY1.toInt())
                graph.drawLine(action.nextState.x, action.nextState.y, arrowX2.toInt(), arrowY2.toInt())
            }
        }
    }
}

internal class ActionsStateBuilder(val x: Int, val y: Int) {

    val actions = ArrayList<ActionBuilder>()

    fun distance(destX: Int, destY: Int) = sqrt(((destX - x) * (destX - x) + (destY - y) * (destY - y)).toDouble())
}

internal class ActionBuilder(val nextState: ActionsStateBuilder, var reward: Int)