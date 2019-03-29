plugins {
    java
}

repositories {
    jcenter()
}

dependencies {
	implementation("com.typesafe:config:1.3.3")
}

java {
	sourceCompatibility = JavaVersion.VERSION_11
	targetCompatibility = JavaVersion.VERSION_11
}
