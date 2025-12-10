package com.example.musicapp.data

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ktorfitClient {
    val httpClient = HttpClient{
        expectSuccess = false //para permitir status code de error en la app
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true //Permite JSONs imperfectos
                    ignoreUnknownKeys = true // ignora las propiedades que no se quieren mapear
                }

            )
        }
        install(HttpTimeout){
            requestTimeoutMillis = 40000
            socketTimeoutMillis = 40000
            connectTimeoutMillis = 40000
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    val baseUrl = "https://music-api-u681.onrender.com/"
    private val ktorfit = Ktorfit
        .Builder()
        .baseUrl(baseUrl)
        .httpClient(httpClient)
        .build()
}