package data.repository

import com.berlin.data.mapper.IpGeolocationMapperImpl
import com.berlin.data.repository.CoordinateRepositoryImpl
import com.berlin.domain.exepction.GeolocationFetchException
import com.berlin.domain.model.Coordinates
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CoordinateRepositoryImplTest {

    private lateinit var client: HttpClient
    private lateinit var mapper: IpGeolocationMapperImpl
    private lateinit var repository: CoordinateRepositoryImpl

    private val ipApiResponseJson = """{
        "status": "success",
        "country": "Egypt",
        "countryCode": "EG",
        "region": "C",
        "regionName": "Cairo",
        "city": "Cairo",
        "zip": "12345",
        "lat": 30.0444,
        "lon": 31.2357,
        "timezone": "Africa/Cairo",
        "isp": "ISP",
        "org": "ORG",
        "as": "AS12345",
        "query": "1.2.3.4"
    }"""

    @BeforeAll
    fun setup() {
        // MockEngine يرد على طلبات HTTP
        val mockEngine = MockEngine { request ->
            when (request.url.toString()) {
                "http://ip-api.com/json" -> respond(
                    content = ipApiResponseJson,
                    status = HttpStatusCode.OK,
                    headers = headersOf("Content-Type", "application/json")
                )
                else -> respondError(HttpStatusCode.NotFound)
            }
        }

        // نهيئ الـ HttpClient مع الـ MockEngine و ContentNegotiation للـ JSON
        client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        // Mock للـ mapper
        mapper = mockk()

        // انشاء repository باستخدام الـ client و mapper
        repository = CoordinateRepositoryImpl(client, mapper)
    }

    @Test
    fun `fetchCoordinates returns mapped Coordinates on success`() = runBlocking {
        // جهز بيانات مخرجات الـ mapper متوقعة
        val expectedCoordinates = Coordinates(30.0444, 31.2357)

        // لما mapper.map() ينادي يرجع expectedCoordinates
        every { mapper.map(any()) } returns expectedCoordinates

        // نفذ الفانكشن
        val result = repository.fetchCoordinates()

        // تحقق من النتيجة
        assertEquals(expectedCoordinates.latitude, result.latitude, 0.0001)
        assertEquals(expectedCoordinates.longitude, result.longitude, 0.0001)
    }

    @Test
    fun `fetchCoordinates throws GeolocationFetchException on client failure`() = runBlocking {
        // Client مع MockEngine يرد خطأ
        val errorClient = HttpClient(MockEngine { respondError(HttpStatusCode.InternalServerError) }) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        val repoWithErrorClient = CoordinateRepositoryImpl(errorClient, mapper)

        val exception = assertThrows<GeolocationFetchException> {
            runBlocking {
                repoWithErrorClient.fetchCoordinates()
            }
        }

        assertEquals("Failed to fetch coordinates", exception.message)
    }
}
