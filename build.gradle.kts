plugins {
    java
}

repositories {
    jcenter()
}

dependencies {
	implementation("com.typesafe:config:1.3.3")
	compileOnly("org.jetbrains:annotations:17.0.0")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.1")
	testRuntime("org.junit.jupiter:junit-jupiter-engine:5.4.1")
}

java {
	sourceCompatibility = JavaVersion.VERSION_11
	targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<Test> {
	useJUnitPlatform {
		includeEngines("junit-jupiter")
	}
}
