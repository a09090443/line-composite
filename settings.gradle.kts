rootProject.name = "line-composite"
include(":core")
project(":core").projectDir = File("../core")
include("lineBot")
