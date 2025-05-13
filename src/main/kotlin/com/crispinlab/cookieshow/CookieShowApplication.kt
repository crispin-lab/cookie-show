package com.crispinlab.cookieshow

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CookieShowApplication

fun main(args: Array<String>) {
    runApplication<CookieShowApplication>(*args)
}
