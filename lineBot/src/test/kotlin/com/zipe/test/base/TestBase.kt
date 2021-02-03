package com.zipe.test.base

import com.zipe.Application
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(classes = [Application::class])
@ExtendWith(SpringExtension::class)
class TestBase {
}
