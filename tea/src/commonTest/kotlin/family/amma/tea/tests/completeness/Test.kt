package family.amma.tea.tests.completeness

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import family.amma.tea.runTest
import family.amma.tea.notTakeAfter
import kotlin.test.Test
import kotlin.test.assertEquals

class CompletenessTest {
    @Test
    fun run() = runTest {
        val dispatchCount = 50
        val expected = CompletenessModel(
            flow1 = dispatchCount,
            flow2 = dispatchCount,
            flow3 = dispatchCount
        )

        val scope = CoroutineScope(Dispatchers.Default)
        val feature = feature(scope, dispatchCount)

        val lastModel = feature
            .states
            .notTakeAfter(timeMillis = 300, expected = expected)
            .last()
        try {
            assertEquals(actual = lastModel, expected = expected)
        } finally {
            scope.cancel()
        }
    }
}