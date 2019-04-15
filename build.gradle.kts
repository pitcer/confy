import com.jfrog.bintray.gradle.BintrayExtension

plugins {
	`java-library`
	`maven-publish`
	id("com.jfrog.bintray") version "1.8.4"
}

repositories {
	jcenter()
}

dependencies {
	api("com.typesafe:config:1.3.3")
	compileOnly("org.jetbrains:annotations:17.0.0")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")
	testRuntime("org.junit.jupiter:junit-jupiter-engine:5.4.2")
}

java {
	sourceCompatibility = JavaVersion.VERSION_11
	targetCompatibility = JavaVersion.VERSION_11
}

task<Jar>("sourcesJar") {
	from(sourceSets["main"].allSource)
	archiveClassifier.set("sources")
}


publishing {
	publications {
		create<MavenPublication>("maven") {
			from(components["java"])
			artifact(tasks["sourcesJar"])
		}
	}
}

bintray {
	user = properties["bintray-user"] as String?
	key = properties["bintray-api-key"] as String?
	publish = true
	setPublications("maven")
	pkg(closureOf<BintrayExtension.PackageConfig> {
		repo = properties["bintray-repository"] as String?
		name = project.name
		desc = project.description
		websiteUrl = properties["vcs-url"] as String
		issueTrackerUrl = "$websiteUrl/issues"
		vcsUrl = "$websiteUrl.git"
		setLicenses(properties["license"] as String)
		setLabels(*(properties["labels"] as String).split(",").toTypedArray())
	})
}

tasks {
	test {
		useJUnitPlatform {
			includeEngines("junit-jupiter")
		}
	}
}
