package reinforcement.algorithms

import reinforcement.model.State

fun valueIteration(
        states: Collection<State>, learningRate: Double = 0.9, numIterations: Int = 1000,
        afterValueChange: (Array<Double>, State, Double) -> Unit = { _, _, _ ->},
        afterIteration: (Array<Double>) -> Unit = {_ ->}): Array<Double> {

    val values = Array(states.size) {0.0}

    repeat(numIterations) {

        for (state in states) {

            var bestValue = 0.0
            for (action in state.getActions()) {

                val currentValue = action.getReward() + learningRate * values[action.getNextStateIndex()]
                if (currentValue > bestValue)
                    bestValue = currentValue
            }

            values[state.getIndex()] = bestValue
            afterValueChange(values, state, bestValue)
        }

        afterIteration(values)
    }

    return values
}