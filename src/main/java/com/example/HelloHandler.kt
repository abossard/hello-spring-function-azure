package com.example

import com.example.model.Greeting
import com.example.model.User
import com.microsoft.azure.functions.*
import com.microsoft.azure.functions.annotation.AuthorizationLevel
import com.microsoft.azure.functions.annotation.FunctionName
import com.microsoft.azure.functions.annotation.HttpTrigger
import org.springframework.cloud.function.adapter.azure.FunctionInvoker
import java.util.*

class HelloHandler : FunctionInvoker<User?, Greeting?>() {
    @FunctionName("hello")
    fun execute(
        @HttpTrigger(
            name = "request",
            methods = [HttpMethod.GET, HttpMethod.POST],
            authLevel = AuthorizationLevel.ANONYMOUS
        ) request: HttpRequestMessage<Optional<User>>,
        context: ExecutionContext
    ): HttpResponseMessage {
        val user = request.body
            .filter { u: User -> u.name != null }
            .orElseGet {
                User(
                    request.queryParameters
                        .getOrDefault("name", "world")
                )
            }
        context.logger.info("Greeting user name: " + user.name)
        return request
            .createResponseBuilder(HttpStatus.OK)
            .body(handleRequest(user, context))
            .header("Content-Type", "application/json")
            .build()
    }
}