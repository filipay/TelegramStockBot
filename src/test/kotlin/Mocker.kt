import io.mockk.MockK
import io.mockk.MockKGateway

class Mocker {
    companion object {
        @JvmStatic
        @JvmOverloads
        fun <T : Any> mockk(
            mockType: Class<T>,
            relaxed: Boolean = false,
        ): T = MockK.useImpl {
            MockKGateway.implementation().mockFactory.mockk(
                mockType.kotlin,
                null,
                relaxed,
                arrayOf(),
                false
            )
        }

        @JvmStatic
        @JvmOverloads
        fun <T : Any> spyk(
            objToCopy: T,
            recordPrivateCalls: Boolean = false
        ): T = MockK.useImpl {
            MockKGateway.implementation().mockFactory.spyk(
                objToCopy.javaClass.kotlin,
                objToCopy,
                null,
                arrayOf(),
                recordPrivateCalls
            )
        }
    }
}