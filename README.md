[![Download](https://api.bintray.com/packages/pitcer/maven-public/confy/images/download.svg)](https://bintray.com/pitcer/maven-public/confy/_latestVersion)

# Confy

Configuration library.

## Add to project

### Gradle

```gradle
repositories {
    maven {
        url 'https://jcenter.bintray.com'
    }
}

dependencies {
    implementation 'pl.pitcer:confy:1.0.0'
}
```

### Gradle Kotlin DSL

```kotlin
repositories {
    jcenter()
}

dependencies {
    implementation("pl.pitcer:confy:1.0.0")
}
```

## Usage example

Create config POJO class. You can use annotations to change config and properties names:

```java
import pl.pitcer.confy.annotation.Config;
import pl.pitcer.confy.annotation.Property;

@Config("database")
public class DatabaseConfig {

    @Property("host")
    private String hostname;
    private Integer port;
    private String user;
    private String password;

    public DatabaseConfig(String hostname, Integer port, String user, String password) {
        this.hostname = hostname;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public String getHostname() {
        return this.hostname;
    }

    public Integer getPort() {
        return this.port;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }
}
```

Initialize the config POJO with default values:

```java
DatabaseConfig databaseConfig = new DatabaseConfig("localhost", 3306, "admin", "password");
```

Save the config to file:

```java
DatabaseConfig databaseConfig = ...
File configFile = new File("database.conf");
Config<DatabaseConfig> config = new Config<>(DatabaseConfig.class, configFile);
config.save(databaseConfig);
```

Result:

```hocon
database {
    host=localhost
    password=password
    port=3306
    user=admin
}
```

Now you can edit config file and read properties from it. They will be stored in config POJO:

```hocon
database {
    host=1.1.1.1
    ...
}
```

```java
Config<DatabaseConfig> config = ...
DatabaseConfig databaseConfig = config.load();
String hostname = databaseConfig.getHostname(); //1.1.1.1
```
