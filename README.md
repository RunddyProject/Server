# Server
Momodal Server

# runmap

A Spring Boot REST application scaffolded from Spring Initializr.

## 📦 Project Metadata
| Key | Value |
|---|---|
| **Group** | `com.momodal` |
| **Artifact / Name** | `runmap` |
| **Base Package** | `com.momodal.runmap` |
| **Build Tool** | Gradle (Groovy DSL) |
| **Language** | Java |
| **Spring Boot** | 3.5.4 |
| **Packaging** | Jar |
| **Java** | 17 |

---

## 🚀 Getting Started

### Prerequisites
- **JDK 17** installed and on your `PATH` (`java -version` should print 17)
- **Git** (optional)
- No need to install Gradle separately — the project uses **Gradle Wrapper**

### Clone
```bash
git clone <your-repo-url>.git
cd runmap
```

### Run (Dev)
```bash
# macOS/Linux
./gradlew bootRun

# Windows (PowerShell/CMD)
gradlew.bat bootRun
```

App will start on **http://localhost:8080** by default.

### Build (Jar)
```bash
# macOS/Linux
./gradlew clean build

# Windows
gradlew.bat clean build
```
Jar output: `build/libs/runmap-<version>.jar`

### Run (Jar)
```bash
java -jar build/libs/runmap-*.jar
```
You can override the server port:
```bash
java -Dserver.port=8081 -jar build/libs/runmap-*.jar
```

---

## 🗂️ Suggested Project Structure
```
src
├─ main
│  ├─ java
│  │  └─ com
│  │     └─ momodal
│  │        └─ runmap
│  │           ├─ RunmapApplication.java
│  │           ├─ controller
│  │           ├─ service
│  │           ├─ domain
│  │           └─ repository
│  └─ resources
│     ├─ application.yml
│     └─ static/ templates/
└─ test
   └─ java
      └─ com.momodal.runmap
```

> Generate a starter controller for a quick sanity check:
```java
// src/main/java/com/momodal/runmap/controller/HealthController.java
package com.momodal.runmap.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }
}
```

---

## ⚙️ Configuration

Application properties live in `src/main/resources/application.yml` (or `application.properties`).  
Common examples:
```yaml
server:
  port: 8080

spring:
  application:
    name: runmap
```

### Profiles
Use Spring profiles for env-specific config:
- `application-dev.yml`
- `application-prod.yml`

Run with a profile:
```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
# or
java -Dspring.profiles.active=dev -jar build/libs/runmap-*.jar
```

---

## 🧪 Testing
```bash
./gradlew test
```
Test classes go under `src/test/java` mirroring main package names.

---

## 🔐 Java Version Troubleshooting
- Ensure Gradle is using JDK 17:
  - Set `JAVA_HOME` to a JDK 17 install
  - Or use Gradle Toolchains in `build.gradle`:
    ```groovy
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }
    ```


