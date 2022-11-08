package com.example

import com.example.model.Greeting
import com.example.model.User
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.function.Function

@Component
class Hello : Function<Mono<User>, Mono<Greeting>> {
    override fun apply(mono: Mono<User>): Mono<Greeting> {
        return mono.map { user: User ->
            Greeting(
                """
    Hello, ${user.name}!
    
    """.trimIndent()
            )
        }
    }
}