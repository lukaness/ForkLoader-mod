plugins {
    kotlin("jvm") version "1.9.10"
    java
}

group = "com.fork"
version = "b1.0-010"

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("net.fabricmc:sponge-mixin:0.12.5+mixin.0.8.5")
    implementation("net.fabricmc:tiny-remapper:0.8.6")
}
