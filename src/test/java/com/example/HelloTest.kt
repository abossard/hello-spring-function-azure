package com.example

import com.example.model.Greeting
import com.example.model.User
import com.microsoft.azure.functions.ExecutionContext
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.cloud.function.adapter.azure.FunctionInvoker
import reactor.core.publisher.Mono
import java.util.logging.Logger

class HelloTest {
    @Test
    fun test() {
        val result = Hello().apply(Mono.just(User("foo")))
        Assertions.assertThat(result.block().message).isEqualTo("Hello, foo!\n")
    }

    @Test
    fun start() {
        val handler = FunctionInvoker<User, Greeting>(
            Hello::class.java
        )
        val result = handler.handleRequest(User("foo"), object : ExecutionContext {
            override fun getLogger(): Logger {
                return Logger.getLogger(HelloTest::class.java.name)
            }

            override fun getInvocationId(): String {
                return "id1"
            }

            override fun getFunctionName(): String {
                return "hello"
            }
        })
        handler.close()
        Assertions.assertThat(result.message).isEqualTo("Hello, foo!\n")
    }
}