package reinforcement.gui

import reinforcement.model.Action
import reinforcement.model.State

class PointState(val x: Int, val y: Int, private val index: Int, private val actions: Iterable<Action>) : State {

    override fun getIndex() = index

    override fun getActions() = actions
}