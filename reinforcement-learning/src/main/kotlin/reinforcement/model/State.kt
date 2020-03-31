package reinforcement.model

interface State {

    fun getIndex(): Int

    fun getActions(): Iterable<Action>
}

class SimpleState(private val index: Int, private val actions: Iterable<Action>) : State {

    override fun getIndex() = index

    override fun getActions() = actions
}

class StateBuilder(val index: Int) {

    private val actions: MutableCollection<Action> = ArrayList()

    fun addAction(action: Action) {
        actions.add(action)
    }

    fun build(): State {
        return SimpleState(index, actions.toList())
    }
}