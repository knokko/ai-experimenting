package reinforcement.gui

import reinforcement.algorithms.valueIteration
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.lang.Thread.sleep
import javax.swing.JPanel
import kotlin.math.min
import kotlin.math.sqrt

import kotlinx.coroutines.*
import kotlin.math.max

class ValueIterationPanel(private val states: List<PointState>) : JPanel() {

    private val panelScores = Array(states.size) {0.0}

    init {
        GlobalScope.launch {
            valueIteration(states, afterIteration = {scores ->
                scores.copyInto(panelScores)
                sleep(100)
                repaint()
            })
        }
    }

    override fun paint(graph: Graphics) {
        graph.color = Color(182, 182, 182)
        graph.fillRect(0, 0, width, height)

        val minScore = panelScores.min()
        val maxScore = panelScores.max()
        if (minScore != null && minScore != maxScore) {

            val avgScore = (maxScore!! + minScore) / 2.0
            val deltaScore = maxScore - avgScore

            for (state in states) {
                val score = panelScores[state.getIndex()]

                if (score >= avgScore)
                    graph.color = Color(0, max(min((255.0 * sqrt((score - avgScore) / deltaScore)).toInt(), 255), 0), 0)
                else
                    graph.color = Color(max(min((255.0 * sqrt((avgScore - score) / deltaScore)).toInt(), 255), 0), 0, 0)
                graph.fillOval(state.x - 20, state.y - 20, 40, 40)
            }
        }

        val graph2d = graph as Graphics2D
        graph2d.stroke = BasicStroke(3f)

        for (state in states) {
            for (action in state.getActions()) {

                if (action.getReward() >= 0)
                    graph.color = Color(0, (16.0 * sqrt(action.getReward())).toInt(), 0)
                else
                    graph.color = Color((16.0 * sqrt(-action.getReward())).toInt(), 0, 0)

                val nextState = states[action.getNextStateIndex()]

                graph.drawLine(state.x, state.y, nextState.x, nextState.y)


                val dx = nextState.x - state.x
                val dy = nextState.y - state.y

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

                graph.drawLine(nextState.x, nextState.y, arrowX1.toInt(), arrowY1.toInt())
                graph.drawLine(nextState.x, nextState.y, arrowX2.toInt(), arrowY2.toInt())
            }
        }
    }
}