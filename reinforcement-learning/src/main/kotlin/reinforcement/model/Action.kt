package reinforcement.model

interface Action {

    fun getNextStateIndex(): Int

    fun getReward(): Double
}

class SimpleAction(private val nextStateIndex: Int, private val reward: Double) : Action {

    override fun getNextStateIndex() = nextStateIndex

    override fun getReward() = reward
}