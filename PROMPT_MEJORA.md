# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/pragma/paymentservice/domain/port/out/PaymentPersistencePort.java` — `com.pragma.paymentservice.domain.model.Payment`: El import com.pragma.paymentservice.domain.model.Payment usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/pragma/paymentservice/application/PaymentService.java` — `com.pragma.paymentservice.domain.model.Payment`: El import com.pragma.paymentservice.domain.model.Payment usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/pragma/paymentservice/infrastructure/config/IdempotencyConfig.java` — `com.pragma.paymentservice.infrastructure.adapter.entity.IdempotencyEntity`: El import com.pragma.paymentservice.infrastructure.adapter.entity.IdempotencyEntity usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/pragma/paymentservice/infrastructure/config/IdempotencyConfig.java` — `com.pragma.paymentservice.infrastructure.adapter.repository.IdempotencyRepository`: El import com.pragma.paymentservice.infrastructure.adapter.repository.IdempotencyRepository usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/pragma/paymentservice/application/PaymentService.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/paymentservice/infrastructure/config/IdempotencyConfig.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/paymentservice/infrastructure/controller/PaymentController.java` — `io.swagger.v3`: El import io.swagger.v3.oas.annotations.Operation pertenece a io.swagger.v3, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/paymentservice/infrastructure/controller/PaymentController.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/paymentservice/infrastructure/config/OpenApiConfig.java` — `io.swagger.v3`: El import io.swagger.v3.oas.models.Components pertenece a io.swagger.v3, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/paymentservice/infrastructure/exception/GlobalExceptionHandler.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/pragma/paymentservice/infrastructure/controller/PaymentControllerTest.java` — `com.fasterxml.jackson`: El import com.fasterxml.jackson.databind.ObjectMapper pertenece a com.fasterxml.jackson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/paymentservice/application/PaymentService.java` — `PaymentRequest.operationNumber`: Se invoca `operationNumber` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/paymentservice/application/PaymentService.java` — `PaymentRequest.channel`: Se invoca `channel` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/paymentservice/application/PaymentService.java` — `PaymentRequest.createdAt`: Se invoca `createdAt` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/paymentservice/application/PaymentService.java` — `PaymentRequest.amount`: Se invoca `amount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/paymentservice/application/PaymentService.java` — `PaymentRequest.currency`: Se invoca `currency` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/paymentservice/application/PaymentService.java` — `PaymentRequest.customerId`: Se invoca `customerId` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/paymentservice/application/PaymentService.java` — `PaymentRequest.customerName`: Se invoca `customerName` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/paymentservice/application/PaymentService.java` — `PaymentRequest.customerEmail`: Se invoca `customerEmail` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/paymentservice/application/PaymentService.java` — `PaymentRequest.customerPhone`: Se invoca `customerPhone` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/paymentservice/application/PaymentService.java` — `PaymentRequest.description`: Se invoca `description` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRepository.findByIdempotencyKey`: Se invoca `findByIdempotencyKey` sobre `PaymentRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.operationNumber`: Se invoca `operationNumber` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.channel`: Se invoca `channel` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.amount`: Se invoca `amount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.currency`: Se invoca `currency` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.customerId`: Se invoca `customerId` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.merchantId`: Se invoca `merchantId` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.description`: Se invoca `description` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRepository.save`: Se invoca `save` sobre `PaymentRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentResponse.status`: Se invoca `status` sobre `PaymentResponse`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.isEmpty`: Se invoca `isEmpty` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.get`: Se invoca `get` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentResponse.message`: Se invoca `message` sobre `PaymentResponse`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/paymentservice/application/PaymentServiceTest.java` — `PaymentPersistencePort.findByOperationNumberAndChannel`: Se invoca `findByOperationNumberAndChannel` sobre `PaymentPersistencePort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Contexto técnico original
Chapter Backend, Especialidad Desarrollador, Tecnolog�a Java, Advanced � Experiencia comprobable en al menos un marco de trabajo (Framework) en su lenguaje de programaci�n principal (por ejemplo, Spring Framework en Java o ASP .net core en C#) y puede realizar el enrutamiento de solicitudes, controlar las variables de entorno, generar registros de rastros (Logs) y conectarse con bases de datos utilizando un Mapeador Relacional de Objetos (ORM).

### Reto
- Tema: Experiencia en Frameworks
- Seniority: advanced-l1
- Tipo: practical
- Título: Implementación de Servicio de Pagos con Framework
- Tiempo estimado: 8 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Configuración del Entorno — objetivo: Establecer el entorno de desarrollo y configurar las variables de entorno necesarias para el servicio. — entregable (NO resolver): Entorno de desarrollo configurado con variables de entorno listas para el servicio de pagos.
- Fase 2: Implementación del Servicio de Pagos — objetivo: Implementar el servicio de pagos que enrute las solicitudes, controle las variables de entorno y genere registros de trazas. — entregable (NO resolver): Servicio de pagos implementado que enruta las solicitudes, controla las variables de entorno y genera registros de trazas.
- Fase 3: Conexión a la Base de Datos — objetivo: Conectar el servicio de pagos a una base de datos utilizando un Mapeador Relacional de Objetos (ORM). — entregable (NO resolver): Servicio de pagos conectado a una base de datos utilizando un ORM.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.pragma</groupId>
    <artifactId>payment-service</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>payment-service</name>
    <description>Servicio de pagos con manejo de idempotencia y conexión a base de datos</description>

    <properties>
        <java.version>21</java.version>
        <mapstruct.version>1.5.5.Final</mapstruct.version>
        <resilience4j.version>2.2.0</resilience4j.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>

        <!-- SpringDoc OpenAPI -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>2.5.0</version>
        </dependency>

        <!-- Database -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <scope>provided</scope>
        </dependency>

        <!-- Resilience4j -->
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot3</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>

        <!-- MapStruct -->
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct</artifactId>
            <version>${mapstruct.version}</version>
        </dependency>

        <!-- Test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                    <annotationProcessorPaths>
                        <path>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                            <version>${lombok.version}</version>
                        </path>
                        <path>
                            <groupId>org.mapstruct</groupId>
                            <artifactId>mapstruct-processor</artifactId>
                            <version>${mapstruct.version}</version>
                        </path>
                    </annotationProcessorPaths>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/pragma/paymentservice/Application.java ===
package com.pragma.paymentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Clase principal de la aplicación Spring Boot.
 * Configura y arranca el contexto de Spring con todas las capas definidas.
 */
@SpringBootApplication
@EnableAsync
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Configuración de executor para operaciones asíncronas.
     * @return Executor configurado con pool de threads.
     */
    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(25);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsyncThread-");
        executor.initialize();
        return executor;
    }
}

// === ARCHIVO: src/main/resources/application.yml ===
server:
  port: 8080
  servlet:
    context-path: /api/payments

spring:
  application:
    name: payment-service

  # Configuración de datasource para PostgreSQL
  datasource:
    url: jdbc:postgresql://localhost:5432/payment_db
    username: payment_user
    password: payment_password
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 20
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000

  # Configuración de JPA
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
        jdbc:
          batch_size: 50
          order_inserts: true
          order_updates: true

  # Configuración para H2 (usado en tests)
  h2:
    console:
      enabled: true
      path: /h2-console

# Configuración de Actuator
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always

# Configuración de Resilience4j
resilience4j:
  circuitbreaker:
    configs:
      default:
        slidingWindowType: COUNT_BASED
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        permittedNumberOfCallsInHalfOpenState: 3
        automaticTransitionFromOpenToHalfOpenEnabled: true
        waitDurationInOpenState: 5s
        failureRateThreshold: 50
        eventConsumerBufferSize: 10
    instances:
      paymentService:
        baseConfig: default

# Configuración de idempotencia
payment:
  idempotency:
    window-hours: 24
    cache-name: paymentIdempotencyCache
    max-size: 10000
    ttl-seconds: 86400

// === ARCHIVO: src/main/java/com/pragma/paymentservice/domain/model/PaymentRequest.java ===
package com.pragma.paymentservice.domain.model;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa una solicitud de pago emitida por un cliente o sistema.
 * Contiene toda la información necesaria para procesar el pago y garantizar su idempotencia.
 */
public record PaymentRequest(
    @NotNull(message = "El ID de la operación es obligatorio")
    @Size(min = 1, max = 64, message = "El ID de la operación debe tener entre 1 y 64 caracteres")
    String operationNumber,

    @NotNull(message = "El canal es obligatorio")
    @Size(min = 1, max = 32, message = "El canal debe tener entre 1 y 32 caracteres")
    String channel,

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    BigDecimal amount,

    @NotNull(message = "La moneda es obligatoria")
    @Size(min = 3, max = 3, message = "La moneda debe ser un código de 3 caracteres")
    String currency,

    @NotNull(message = "El ID del cliente es obligatorio")
    UUID customerId,

    @NotNull(message = "El ID de la cuenta origen es obligatorio")
    UUID sourceAccountId,

    @NotNull(message = "El ID de la cuenta destino es obligatorio")
    UUID destinationAccountId,

    @NotBlank(message = "La referencia es obligatoria")
    @Size(max = 255, message = "La referencia no puede exceder 255 caracteres")
    String reference,

    @NotNull(message = "La marca de tiempo es obligatoria")
    LocalDateTime timestamp,

    @Size(max = 255, message = "Los metadatos no pueden exceder 255 caracteres")
    String metadata
) {
    /**
     * Genera una clave de idempotencia única para esta solicitud.
     * La clave se compone del número de operación y el canal, separados por un guión.
     * @return Clave de idempotencia en formato "operationNumber-channel"
     */
    public String generateIdempotencyKey() {
        return operationNumber + "-" + channel;
    }

    /**
     * Valida que la solicitud cumpla con las reglas de negocio básicas.
     * @throws IllegalArgumentException si alguna validación falla
     */
    public void validate() {
        if (sourceAccountId.equals(destinationAccountId)) {
            throw new IllegalArgumentException("La cuenta origen y destino no pueden ser la misma");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        if (timestamp.isAfter(LocalDateTime.now().plusMinutes(5))) {
            throw new IllegalArgumentException("La marca de tiempo no puede ser futura");
        }
        if (timestamp.isBefore(LocalDateTime.now().minusDays(1))) {
            throw new IllegalArgumentException("La marca de tiempo no puede tener más de 24 horas");
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/paymentservice/domain/model/PaymentResponse.java ===
package com.pragma.paymentservice.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa la respuesta del procesamiento de una solicitud de pago.
 * Contiene información sobre el estado del pago y los detalles resultantes.
 */
public record PaymentResponse(
    UUID paymentId,

    String operationNumber,

    String channel,

    PaymentStatus status,

    BigDecimal amount,

    String currency,

    LocalDateTime processedAt,

    String reference,

    String message,

    String transactionId,

    String idempotencyKey
) {
    /**
     * Enumeración que representa los posibles estados de un pago.
     */
    public enum PaymentStatus {
        PENDING,
        COMPLETED,
        FAILED,
        REJECTED,
        TIMEOUT
    }

    /**
     * Constructor para respuestas exitosas.
     * @param paymentId ID único del pago generado
     * @param operationNumber Número de operación original
     * @param channel Canal original
     * @param amount Monto procesado
     * @param currency Moneda procesada
     * @param reference Referencia original
     * @param transactionId ID de transacción generado por el core bancario
     * @param idempotencyKey Clave de idempotencia
     * @return PaymentResponse con estado COMPLETED
     */
    public static PaymentResponse success(
        UUID paymentId,
        String operationNumber,
        String channel,
        BigDecimal amount,
        String currency,
        String reference,
        String transactionId,
        String idempotencyKey
    ) {
        return new PaymentResponse(
            paymentId,
            operationNumber,
            channel,
            PaymentStatus.COMPLETED,
            amount,
            currency,
            LocalDateTime.now(),
            reference,
            "Pago procesado exitosamente",
            transactionId,
            idempotencyKey
        );
    }

    /**
     * Constructor para respuestas fallidas.
     * @param operationNumber Número de operación original
     * @param channel Canal original
     * @param status Estado del pago (FAILED, REJECTED, TIMEOUT)
     * @param message Mensaje descriptivo del error
     * @param idempotencyKey Clave de idempotencia
     * @return PaymentResponse con el estado especificado
     */
    public static PaymentResponse failure(
        String operationNumber,
        String channel,
        PaymentStatus status,
        String message,
        String idempotencyKey
    ) {
        return new PaymentResponse(
            null,
            operationNumber,
            channel,
            status,
            null,
            null,
            LocalDateTime.now(),
            null,
            message,
            null,
            idempotencyKey
        );
    }
}

// === ARCHIVO: src/main/java/com/pragma/paymentservice/domain/port/in/PaymentServicePort.java ===
package com.pragma.paymentservice.domain.port.in;

import com.pragma.paymentservice.domain.model.PaymentRequest;
import com.pragma.paymentservice.domain.model.PaymentResponse;
import jakarta.validation.Valid;
import java.util.concurrent.CompletableFuture;

/**
 * Puerto de entrada que define las operaciones disponibles para el servicio de pagos.
 * Esta interfaz actúa como contrato entre la capa de aplicación y la capa de dominio.
 */
public interface PaymentServicePort {

    /**
     * Procesa una solicitud de pago verificando su idempotencia y aplicando las reglas de negocio.
     * @param paymentRequest Solicitud de pago validada
     * @return CompletableFuture con la respuesta del procesamiento
     * @throws jakarta.validation.ConstraintViolationException si la solicitud no es válida
     * @throws com.pragma.paymentservice.infrastructure.exception.IdempotencyException si ya existe un pago con la misma clave de idempotencia
     * @throws com.pragma.paymentservice.infrastructure.exception.PaymentProcessingException si ocurre un error durante el procesamiento
     */
    CompletableFuture<PaymentResponse> processPayment(@Valid PaymentRequest paymentRequest);

    /**
     * Verifica el estado de un pago previamente procesado.
     * @param operationNumber Número de operación
     * @param channel Canal de origen
     * @return CompletableFuture con la respuesta del pago si existe, o null si no existe
     */
    CompletableFuture<PaymentResponse> checkPaymentStatus(String operationNumber, String channel);
}

// === ARCHIVO: src/main/java/com/pragma/paymentservice/domain/port/out/PaymentPersistencePort.java ===
package com.pragma.paymentservice.domain.port.out;

import com.pragma.paymentservice.domain.model.Payment;
import java.util.Optional;

/**
 * Puerto de salida que define el contrato para la persistencia de pagos.
 * Esta interfaz pertenece al dominio y es implementada por la capa de infraestructura.
 */
public interface PaymentPersistencePort {
    
    /**
     * Guarda un pago en el sistema de persistencia.
     * @param payment la entidad de dominio con los datos del pago
     * @return el pago persistido con su identificador asignado
     */
    Payment save(Payment payment);
    
    /**
     * Busca un pago por su clave de idempotencia.
     * @param operationNumber número de operación único
     * @param channel canal por el cual se realizó la operación
     * @return un Optional con el pago encontrado o vacío si no existe
     */
    Optional<Payment> findByIdempotencyKey(String operationNumber, String channel);
    
    /**
     * Busca un pago por su identificador interno.
     * @param id identificador del pago
     * @return un Optional con el pago encontrado o vacío si no existe
     */
    Optional<Payment> findById(Long id);
    
    /**
     * Actualiza el estado de un pago existente.
     * @param payment el pago con los datos actualizados
     * @return el pago actualizado
     */
    Payment update(Payment payment);
    
    /**
     * Verifica si existe un pago con la clave de idempotencia proporcionada.
     * @param operationNumber número de operación
     * @param channel canal de la operación
     * @return true si existe un pago con esa clave, false en caso contrario
     */
    boolean existsByIdempotencyKey(String operationNumber, String channel);
}

// === ARCHIVO: src/main/java/com/pragma/paymentservice/infrastructure/adapter/repository/PaymentRepository.java ===
package com.pragma.paymentservice.infrastructure.adapter.repository;

import com.pragma.paymentservice.infrastructure.adapter.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio Spring Data JPA para operaciones CRUD sobre la entidad PaymentEntity.
 * Proporciona métodos de acceso a datos persistidos en la base de datos.
 */
@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    
    /**
     * Busca un pago por su clave de idempotencia compuesta.
     * @param operationNumber número de operación único
     * @param channel canal de la operación
     * @return Optional con la entidad encontrada o vacío
     */
    @Query("SELECT p FROM PaymentEntity p WHERE p.operationNumber = :operationNumber AND p.channel = :channel")
    Optional<PaymentEntity> findByOperationNumberAndChannel(
            @Param("operationNumber") String operationNumber,
            @Param("channel") String channel
    );
    
    /**
     * Verifica la existencia de un pago por su clave de idempotencia.
     * @param operationNumber número de operación
     * @param channel canal
     * @return true si existe, false en caso contrario
     */
    boolean existsByOperationNumberAndChannel(String operationNumber, String channel);
    
    /**
     * Busca pagos por estado para auditoría o monitoreo.
     * @param status estado del pago a buscar
     * @return lista de pagos con el estado especificado
     */
    @Query("SELECT p FROM PaymentEntity p WHERE p.status = :status ORDER BY p.createdAt DESC")
    java.util.List<PaymentEntity> findByStatus(@Param("status") String status);
    
    /**
     * Busca pagos realizados en un rango de fechas.
     * @param startDate fecha de inicio del rango
     * @param endDate fecha de fin del rango
     * @return lista de pagos dentro del rango
     */
    @Query("SELECT p FROM PaymentEntity p WHERE p.createdAt BETWEEN :startDate AND :endDate ORDER BY p.createdAt DESC")
    java.util.List<PaymentEntity> findByDateRange(
            @Param("startDate") java.time.LocalDateTime startDate,
            @Param("endDate") java.time.LocalDateTime endDate
    );
}

// === ARCHIVO: src/main/java/com/pragma/paymentservice/application/PaymentService.java ===
package com.pragma.paymentservice.application;


import com.pragma.paymentservice.domain.model.PaymentStatus;
import com.pragma.paymentservice.domain.model.Payment;
import com.pragma.paymentservice.domain.model.PaymentRequest;
import com.pragma.paymentservice.domain.model.PaymentResponse;
import com.pragma.paymentservice.domain.port.in.PaymentServicePort;
import com.pragma.paymentservice.domain.port.out.PaymentPersistencePort;
import com.pragma.paymentservice.infrastructure.exception.IdempotencyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Implementación del caso de uso que orquesta la lógica de negocio del servicio de pagos.
 * Coordina la validación de idempotencia, la persistencia y la construcción de respuestas.
 */
@Service
public class PaymentService implements PaymentServicePort {
    
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private static final int IDEMPOTENCY_WINDOW_HOURS = 24;
    
    private final PaymentPersistencePort persistencePort;
    
    public PaymentService(PaymentPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }
    
    @Override
    @Transactional
    public CompletableFuture<PaymentResponse> processPayment(PaymentRequest paymentRequest) {
        logger.info("Iniciando procesamiento de pago con clave de idempotencia: {}", 
                paymentRequest.generateIdempotencyKey());
        
        paymentRequest.validate();
        
        String operationNumber = paymentRequest.operationNumber();
        String channel = paymentRequest.channel();
        
        Optional<Payment> existingPayment = persistencePort.findByIdempotencyKey(operationNumber, channel);
        
        if (existingPayment.isPresent()) {
            logger.info("Pago encontrado para clave de idempotencia: {}, retornando respuesta existente",
                    paymentRequest.generateIdempotencyKey());
            return CompletableFuture.completedFuture(buildResponseFromPayment(existingPayment.get()));
        }
        
        validateIdempotencyWindow(paymentRequest.createdAt());
        
        try {
            Payment payment = buildPaymentFromRequest(paymentRequest);
            payment.setStatus(PaymentResponse.PaymentStatus.PROCESSING.name());
            
            Payment savedPayment = persistencePort.save(payment);
            logger.info("Pago guardado exitosamente con ID: {}", savedPayment.getId());
            
            Payment completedPayment = processPaymentWithExternalSystems(savedPayment);
            
            Payment finalPayment = persistencePort.update(completedPayment);
            
            logger.info("Pago completado exitosamente con número de operación: {}", 
                    finalPayment.getOperationNumber());
            
            return CompletableFuture.completedFuture(buildResponseFromPayment(finalPayment));
            
        } catch (Exception e) {
            logger.error("Error al procesar pago para operación: {}", operationNumber, e);
            return CompletableFuture.completedFuture(
                    PaymentResponse.failure("Error al procesar el pago: " + e.getMessage())
            );
        }
    }
    
    @Override
    public CompletableFuture<PaymentResponse> checkPaymentStatus(String operationNumber, String channel) {
        logger.info("Consultando estado del pago para operación: {}, canal: {}", operationNumber, channel);
        
        Optional<Payment> payment = persistencePort.findByIdempotencyKey(operationNumber, channel);
        
        if (payment.isEmpty()) {
            logger.warn("No se encontró pago para operación: {}, canal: {}", operationNumber, channel);
            return CompletableFuture.completedFuture(
                    PaymentResponse.failure("Pago no encontrado para los parámetros proporcionados")
            );
        }
        
        return CompletableFuture.completedFuture(buildResponseFromPayment(payment.get()));
    }
    
    private void validateIdempotencyWindow(LocalDateTime createdAt) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime windowStart = now.minusHours(IDEMPOTENCY_WINDOW_HOURS);
        
        if (createdAt.isBefore(windowStart)) {
            throw new IdempotencyException(
                    "La clave de idempotencia ha expirado. El máximo es de " + IDEMPOTENCY_WINDOW_HOURS + " horas"
            );
        }
    }
    
    private Payment buildPaymentFromRequest(PaymentRequest request) {
        return Payment.builder()
                .operationNumber(request.operationNumber())
                .channel(request.channel())
                .amount(request.amount())
                .currency(request.currency())
                .customerId(request.customerId())
                .customerName(request.customerName())
                .customerEmail(request.customerEmail())
                .customerPhone(request.customerPhone())
                .description(request.description())
                .status(PaymentResponse.PaymentStatus.PENDING.name())
                .createdAt(request.createdAt())
                .build();
    }
    
    private PaymentResponse buildResponseFromPayment(Payment payment) {
        return PaymentResponse.success(
                payment.getId(),
                payment.getOperationNumber(),
                payment.getChannel(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getStatus(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );
    }
    
    private Payment processPaymentWithExternalSystems(Payment payment) {
        logger.debug("Procesando pago con sistemas externos - ID: {}", payment.getId());
        
        boolean fraudCheckPassed = performFraudCheck(payment);
        
        if (!fraudCheckPassed) {
            payment.setStatus(PaymentResponse.PaymentStatus.REJECTED.name());
            payment.setFailureReason("Transacción rechazada por motor antifraude");
            return payment;
        }
        
        boolean coreBankingApproved = routeToCoreBanking(payment);
        
        if (!coreBankingApproved) {
            payment.setStatus(PaymentResponse.PaymentStatus.FAILED.name());
            payment.setFailureReason("Rechazo del core bancario");
            return payment;
        }
        
        payment.setStatus(PaymentResponse.PaymentStatus.APPROVED.name());
        payment.setAuthorizationCode(generateAuthorizationCode());
        
        return payment;
    }
    
    private boolean performFraudCheck(Payment payment) {
        logger.info("Ejecutando verificación antifraude para cliente: {}", payment.getCustomerId());
        return true;
    }
    
    private boolean routeToCoreBanking(Payment payment) {
        logger.info("Enviando solicitud al core bancario para operación: {}", payment.getOperationNumber());
        return true;
    }
    
    private String generateAuthorizationCode() {
        return "AUTH-" + System.currentTimeMillis();
    }
}

// === ARCHIVO: src/main/java/com/pragma/paymentservice/infrastructure/adapter/entity/PaymentEntity.java ===
package com.pragma.paymentservice.infrastructure.adapter.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments", indexes = {
    @Index(name = "idx_idempotency_key", columnList = "idempotency_key"),
    @Index(name = "idx_operation_number", columnList = "operation_number"),
    @Index(name = "idx_channel", columnList = "channel"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_created_at", columnList = "created_at")
})
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "operation_number", nullable = false, length = 50)
    private String operationNumber;

    @Column(name = "channel", nullable = false, length = 30)
    private String channel;

    @Column(name = "idempotency_key", nullable = false, unique = true, length = 100)
    private String idempotencyKey;

    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "response_data", columnDefinition = "TEXT")
    private String responseData;

    @Column(name = "customer_id", length = 50)
    private String customerId;

    @Column(name = "merchant_id", length = 50)
    private String merchantId;

    @Column(name = "description", length = 255)
    private String description;

    public PaymentEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperationNumber() {
        return operationNumber;
    }

    public void setOperationNumber(String operationNumber) {
        this.operationNumber = operationNumber;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

// === ARCHIVO: src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java ===
package com.pragma.paymentservice.infrastructure.adapter;


import com.pragma.paymentservice.domain.model.PaymentStatus;
import com.pragma.paymentservice.domain.model.PaymentRequest;
import com.pragma.paymentservice.domain.model.PaymentResponse;
import com.pragma.paymentservice.domain.port.out.PaymentPersistencePort;
import com.pragma.paymentservice.infrastructure.adapter.entity.PaymentEntity;
import com.pragma.paymentservice.infrastructure.adapter.repository.PaymentRepository;
import com.pragma.paymentservice.infrastructure.exception.IdempotencyException;
import com.pragma.paymentservice.infrastructure.exception.PaymentProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class PaymentJpaAdapter implements PaymentPersistencePort {

    private static final Logger log = LoggerFactory.getLogger(PaymentJpaAdapter.class);
    private static final String STATUS_PENDING = "PENDING";
    private static final String STATUS_PROCESSING = "PROCESSING";
    private static final String STATUS_COMPLETED = "COMPLETED";
    private static final String STATUS_FAILED = "FAILED";
    private static final String STATUS_DUPLICATE = "DUPLICATE";

    private final PaymentRepository paymentRepository;

    public PaymentJpaAdapter(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional
    public CompletableFuture<PaymentResponse> processPayment(PaymentRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String idempotencyKey = request.generateIdempotencyKey();
                log.info("Procesando pago con clave de idempotencia: {}", idempotencyKey);

                Optional<PaymentEntity> existingPayment = paymentRepository.findByIdempotencyKey(idempotencyKey);
                if (existingPayment.isPresent()) {
                    log.info("Pago duplicado detectado para clave: {}", idempotencyKey);
                    return buildResponseFromEntity(existingPayment.get(), true);
                }

                PaymentEntity entity = new PaymentEntity();
                entity.setIdempotencyKey(idempotencyKey);
                entity.setOperationNumber(request.operationNumber());
                entity.setChannel(request.channel());
                entity.setAmount(request.amount());
                entity.setCurrency(request.currency());
                entity.setStatus(STATUS_PROCESSING);
                entity.setCustomerId(request.customerId());
                entity.setMerchantId(request.merchantId());
                entity.setDescription(request.description());

                PaymentEntity savedEntity = paymentRepository.save(entity);
                log.info("Pago guardado con ID: {}", savedEntity.getId());

                PaymentResponse response = simulatePaymentProcessing(savedEntity);

                savedEntity.setStatus(response.status().name());
                savedEntity.setResponseData(serializeResponse(response));
                paymentRepository.save(savedEntity);

                log.info("Pago procesado exitosamente. OperationNumber: {}, Status: {}", 
                        savedEntity.getOperationNumber(), savedEntity.getStatus());

                return response;
            } catch (IdempotencyException e) {
                log.error("Error de idempotencia: {}", e.getMessage());
                throw e;
            } catch (Exception e) {
                log.error("Error al procesar pago: {}", e.getMessage(), e);
                throw new PaymentProcessingException("Error al procesar el pago: " + e.getMessage(), e);
            }
        });
    }

    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<PaymentResponse> checkPaymentStatus(String operationNumber, String channel) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Consultando estado del pago. OperationNumber: {}, Channel: {}", operationNumber, channel);

            Optional<PaymentEntity> entity = paymentRepository
                    .findByOperationNumberAndChannel(operationNumber, channel);

            if (entity.isEmpty()) {
                log.warn("Pago no encontrado. OperationNumber: {}, Channel: {}", operationNumber, channel);
                return PaymentResponse.failure("PAGO_NO_ENCONTRADO", 
                        "No se encontró el pago para el número de operación y canal especificado");
            }

            PaymentEntity payment = entity.get();
            log.info("Estado del pago encontrado. ID: {}, Status: {}", payment.getId(), payment.getStatus());

            return buildResponseFromEntity(payment, false);
        });
    }

    private PaymentResponse simulatePaymentProcessing(PaymentEntity entity) {
        boolean isHighAmount = entity.getAmount().compareTo(new BigDecimal("10000")) > 0;
        boolean isFraudRisk = entity.getAmount().compareTo(new BigDecimal("50000")) > 0;

        if (isFraudRisk) {
            log.warn("Pago marcado como riesgo de fraude. Amount: {}", entity.getAmount());
            return PaymentResponse.failure("RIESGO_FRAUDE", 
                    "El pago ha sido flagged por el motor antifraude");
        }

        if (isHighAmount) {
            log.info("Monto alto detectado, requiere aprobación manual. Amount: {}", entity.getAmount());
            return PaymentResponse.builder()
                    .operationNumber(entity.getOperationNumber())
                    .status(PaymentResponse.PaymentStatus.PENDING)
                    .message("Pago en espera de aprobación por monto alto")
                    .idempotencyKey(entity.getIdempotencyKey())
                    .build();
        }

        return PaymentResponse.success(
                entity.getOperationNumber(),
                entity.getAmount(),
                entity.getCurrency(),
                "Pago procesado exitosamente"
        );
    }

    private PaymentResponse buildResponseFromEntity(PaymentEntity entity, boolean isDuplicate) {
        if (isDuplicate || STATUS_DUPLICATE.equals(entity.getStatus())) {
            return PaymentResponse.builder()
                    .operationNumber(entity.getOperationNumber())
                    .status(PaymentResponse.PaymentStatus.DUPLICATE)
                    .message("Pago duplicado - se retorna la respuesta original")
                    .idempotencyKey(entity.getIdempotencyKey())
                    .createdAt(entity.getCreatedAt())
                    .build();
        }

        PaymentResponse.PaymentStatus status = PaymentResponse.PaymentStatus.valueOf(entity.getStatus());
        return PaymentResponse.builder()
                .operationNumber(entity.getOperationNumber())
                .status(status)
                .message(getStatusMessage(status))
                .idempotencyKey(entity.getIdempotencyKey())
                .amount(entity.getAmount())
                .currency(entity.getCurrency())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    private String getStatusMessage(PaymentResponse.PaymentStatus status) {
        return switch (status) {
            case COMPLETED -> "Pago completado exitosamente";
            case FAILED -> "Pago fallido";
            case PENDING -> "Pago en pendiente";
            case PROCESSING -> "Pago en procesamiento";
            case DUPLICATE -> "Pago duplicado";
            default -> "Estado desconocido";
        };
    }

    private String serializeResponse(PaymentResponse response) {
        return String.format("{\"status\":\"%s\",\"message\":\"%s\"}", 
                response.status(), response.message());
    }
}

// === ARCHIVO: src/main/java/com/pragma/paymentservice/infrastructure/config/IdempotencyConfig.java ===
package com.pragma.paymentservice.infrastructure.config;

import com.pragma.paymentservice.infrastructure.adapter.entity.IdempotencyEntity;
import com.pragma.paymentservice.infrastructure.adapter.repository.IdempotencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class IdempotencyConfig {

    private static final Logger log = LoggerFactory.getLogger(IdempotencyConfig.class);
    private static final int DEFAULT_WINDOW_HOURS = 24;

    private final IdempotencyRepository idempotencyRepository;
    private final int windowHours;

    public IdempotencyConfig(
            IdempotencyRepository idempotencyRepository,
            @Value("${payment.idempotency.window-hours:24}") int windowHours) {
        this.idempotencyRepository = idempotencyRepository;
        this.windowHours = windowHours > 0 ? windowHours : DEFAULT_WINDOW_HOURS;
    }

    @Transactional
    public boolean isRequestIdempotent(String idempotencyKey) {
        log.debug("Verificando idempotencia para clave: {}", idempotencyKey);

        Optional<IdempotencyEntity> existing = idempotencyRepository.findByIdempotencyKey(idempotencyKey);

        if (existing.isEmpty()) {
            log.debug("Clave de idempotencia no encontrada, es nueva: {}", idempotencyKey);
            return false;
        }

        IdempotencyEntity record = existing.get();

        if (isExpired(record)) {
            log.info("Clave de idempotencia expirada, permite nueva solicitud: {}", idempotencyKey);
            idempotencyRepository.delete(record);
            return false;
        }

        log.info("Clave de idempotencia válida encontrada. Clave: {}, Expira en: {} horas", 
                idempotencyKey, calculateRemainingHours(record));
        return true;
    }

    @Transactional
    public void recordIdempotentRequest(String idempotencyKey, String operationNumber, String channel) {
        log.info("Registrando clave de idempotencia: {} para operación: {}", 
                idempotencyKey, operationNumber);

        if (idempotencyRepository.existsByIdempotencyKey(idempotencyKey)) {
            log.warn("La clave de idempotencia ya existe: {}", idempotencyKey);
            return;
        }

        IdempotencyEntity entity = new IdempotencyEntity();
        entity.setIdempotencyKey(idempotencyKey);
        entity.setOperationNumber(operationNumber);
        entity.setChannel(channel);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setExpiresAt(LocalDateTime.now().plusHours(windowHours));

        idempotencyRepository.save(entity);
        log.info("Clave de idempotencia registrada exitosamente. Expira en {} horas", windowHours);
    }

    @Transactional
    public Optional<IdempotencyEntity> getIdempotencyRecord(String idempotencyKey) {
        return idempotencyRepository.findByIdempotencyKey(idempotencyKey);
    }

    @Transactional
    public void cleanupExpiredRecords() {
        log.info("Iniciando limpieza de registros de idempotencia expirados");
        int deleted = idempotencyRepository.deleteByExpiresAtBefore(LocalDateTime.now());
        log.info("Registros de idempotencia expirados eliminados: {}", deleted);
    }

    public int getWindowHours() {
        return windowHours;
    }

    private boolean isExpired(IdempotencyEntity record) {
        return LocalDateTime.now().isAfter(record.getExpiresAt());
    }

    private long calculateRemainingHours(IdempotencyEntity record) {
        Duration duration = Duration.between(LocalDateTime.now(), record.getExpiresAt());
        return Math.max(0, duration.toHours());
    }
}

// === ARCHIVO: src/main/java/com/pragma/paymentservice/infrastructure/controller/PaymentController.java ===
package com.pragma.paymentservice.infrastructure.controller;

import com.pragma.paymentservice.domain.model.PaymentRequest;
import com.pragma.paymentservice.domain.model.PaymentResponse;
import com.pragma.paymentservice.domain.port.in.PaymentServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@Tag(name = "Pagos", description = "Endpoints para la gestión de solicitudes de pago")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentServicePort paymentServicePort;

    public PaymentController(PaymentServicePort paymentServicePort) {
        this.paymentServicePort = paymentServicePort;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Procesar pago", description = "Procesa una solicitud de pago con soporte para idempotencia")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago procesado exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = PaymentResponse.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content),
        @ApiResponse(responseCode = "409", description = "Conflicto de idempotencia - operación duplicada", content = @Content),
        @ApiResponse(responseCode = "422", description = "Error de procesamiento del pago", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    public ResponseEntity<PaymentResponse> processPayment(
            @Valid @RequestBody PaymentRequest paymentRequest) {
        logger.info("Recibida solicitud de pago con idempotencyKey: {}", 
            paymentRequest.generateIdempotencyKey());
        paymentRequest.validate();
        
        PaymentResponse response = paymentServicePort.processPayment(paymentRequest)
            .whenComplete((result, error) -> {
                if (error != null) {
                    logger.error("Error al procesar pago: {}", error.getMessage());
                } else {
                    logger.info("Pago procesado exitosamente: {}", result);
                }
            })
            .join();
        
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/status/{operationNumber}/{channel}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Consultar estado del pago", description = "Consulta el estado de una operación de pago existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado del pago consultado exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = PaymentResponse.class))),
        @ApiResponse(responseCode = "404", description = "Operación no encontrada", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    public ResponseEntity<PaymentResponse> checkPaymentStatus(
            @Parameter(description = "Número de operación única", required = true)
            @PathVariable String operationNumber,
            @Parameter(description = "Canal de la operación", required = true)
            @PathVariable String channel) {
        logger.info("Consultando estado del pago: operationNumber={}, channel={}", operationNumber, channel);
        
        PaymentResponse response = paymentServicePort.checkPaymentStatus(operationNumber, channel)
            .whenComplete((result, error) -> {
                if (error != null) {
                    logger.error("Error al consultar estado: {}", error.getMessage());
                } else {
                    logger.info("Estado consultado: {}", result);
                }
            })
            .join();
        
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Health check", description = "Verifica la disponibilidad del servicio")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Servicio disponible",
            content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "payment-service"));
    }
}

// === ARCHIVO: src/main/java/com/pragma/paymentservice/infrastructure/config/OpenApiConfig.java ===
package com.pragma.paymentservice.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Payment Service API")
                .version("1.0.0")
                .description("""
                    API REST para la gestión de solicitudes de pago.
                    
                    Este servicio proporciona funcionalidades para:
                    - Procesar solicitudes de pago con soporte de idempotencia
                    - Consultar el estado de operaciones de pago existentes
                    - Manejar múltiples canales de operación
                    
                    La idempotencia se garantiza mediante claves únicas compuestas por
                    número de operación y canal, asegurando que dos invocaciones con
                    la misma clave produzcan un solo registro.
                    """)
                .contact(new Contact()
                    .name("Equipo de Pagos")
                    .email("pagos@pragma.com")
                    .url("https://www.pragma.com"))
                .license(new License()
                    .name("Licencia Proprietaria")
                    .url("https://www.pragma.com/licencia")))
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(new Components()
                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .description("Token de autenticación JWT")));
    }
}

// === ARCHIVO: src/main/java/com/pragma/paymentservice/infrastructure/exception/GlobalExceptionHandler.java ===
package com.pragma.paymentservice.infrastructure.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String ERROR_INTERNO = "Error interno del servidor";

    @ExceptionHandler(IdempotencyException.class)
    public ResponseEntity<Map<String, Object>> handleIdempotencyException(IdempotencyException ex) {
        logger.warn("Conflicto de idempotencia detectado: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.CONFLICT, "CONFLICT", ex.getMessage());
    }

    @ExceptionHandler(PaymentProcessingException.class)
    public ResponseEntity<Map<String, Object>> handlePaymentProcessingException(PaymentProcessingException ex) {
        logger.error("Error al procesar el pago: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "PAYMENT_PROCESSING_ERROR", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.warn("Error de validación en la solicitud: {}", ex.getMessage());
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", Instant.now().toString());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "VALIDATION_ERROR");
        response.put("message", "Los datos de la solicitud no son válidos");
        response.put("fieldErrors", fieldErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        logger.warn("Tipo de argumento inválido: {}", ex.getMessage());
        String message = String.format("El parámetro '%s' tiene un valor inválido: '%s'", 
            ex.getName(), ex.getValue());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "INVALID_ARGUMENT", message);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        logger.warn("Argumento ilegal: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "INVALID_ARGUMENT", ex.getMessage());
    }

    @ExceptionHandler(CompletionException.class)
    public ResponseEntity<Map<String, Object>> handleCompletionException(CompletionException ex) {
        Throwable cause = ex.getCause();
        logger.error("Excepción completada con causa: {}", cause != null ? cause.getMessage() : "unknown");
        
        if (cause instanceof IdempotencyException) {
            return handleIdempotencyException((IdempotencyException) cause);
        } else if (cause instanceof PaymentProcessingException) {
            return handlePaymentProcessingException((PaymentProcessingException) cause);
        } else if (cause instanceof IllegalArgumentException) {
            return handleIllegalArgument((IllegalArgumentException) cause);
        }
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", ERROR_INTERNO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        logger.error("Excepción no manejada: ", ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", ERROR_INTERNO);
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String error, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", Instant.now().toString());
        response.put("status", status.value());
        response.put("error", error);
        response.put("message", message);
        return ResponseEntity.status(status).body(response);
    }
}

// === ARCHIVO: src/main/java/com/pragma/paymentservice/infrastructure/exception/IdempotencyException.java ===
package com.pragma.paymentservice.infrastructure.exception;

import java.time.Instant;

public class IdempotencyException extends RuntimeException {
    
    private final String operationNumber;
    private final String channel;
    private final Instant attemptTime;
    private final String reasonCode;

    public IdempotencyException(String message) {
        super(message);
        this.operationNumber = null;
        this.channel = null;
        this.attemptTime = Instant.now();
        this.reasonCode = "IDEMP_GENERIC";
    }

    public IdempotencyException(String message, Throwable cause) {
        super(message, cause);
        this.operationNumber = null;
        this.channel = null;
        this.attemptTime = Instant.now();
        this.reasonCode = "IDEMP_GENERIC";
    }

    public IdempotencyException(String operationNumber, String channel, String message) {
        super(message);
        this.operationNumber = operationNumber;
        this.channel = channel;
        this.attemptTime = Instant.now();
        this.reasonCode = "IDEMP_DUPLICATE_KEY";
    }

    public IdempotencyException(String operationNumber, String channel, String message, Throwable cause) {
        super(message, cause);
        this.operationNumber = operationNumber;
        this.channel = null;
        this.attemptTime = Instant.now();
        this.reasonCode = "IDEMP_DUPLICATE_KEY";
    }

    public IdempotencyException(String operationNumber, String channel, String message, String reasonCode) {
        super(message);
        this.operationNumber = operationNumber;
        this.channel = channel;
        this.attemptTime = Instant.now();
        this.reasonCode = reasonCode;
    }

    public IdempotencyException(String operationNumber, String channel, String message, String reasonCode, Throwable cause) {
        super(message, cause);
        this.operationNumber = operationNumber;
        this.channel = channel;
        this.attemptTime = Instant.now();
        this.reasonCode = reasonCode;
    }

    public String getOperationNumber() {
        return operationNumber;
    }

    public String getChannel() {
        return channel;
    }

    public Instant getAttemptTime() {
        return attemptTime;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public String getIdempotencyKey() {
        if (operationNumber != null && channel != null) {
            return operationNumber + "_" + channel;
        }
        return null;
    }

    public boolean isDuplicateKeyError() {
        return "IDEMP_DUPLICATE_KEY".equals(reasonCode);
    }

    public boolean isExpiredKeyError() {
        return "IDEMP_EXPIRED_KEY".equals(reasonCode);
    }

    public boolean isInvalidKeyError() {
        return "IDEMP_INVALID_KEY".equals(reasonCode);
    }

    @Override
    public String toString() {
        return String.format("IdempotencyException{operationNumber='%s', channel='%s', reasonCode='%s', attemptTime=%s, message='%s'}",
                operationNumber, channel, reasonCode, attemptTime, getMessage());
    }
}

// === ARCHIVO: src/main/java/com/pragma/paymentservice/infrastructure/exception/PaymentProcessingException.java ===
package com.pragma.paymentservice.infrastructure.exception;

import java.math.BigDecimal;
import java.time.Instant;

public class PaymentProcessingException extends RuntimeException {

    private final String operationNumber;
    private final String channel;
    private final BigDecimal amount;
    private final Instant failureTime;
    private final String errorCode;
    private final String severity;
    private final boolean retryable;

    public PaymentProcessingException(String message) {
        super(message);
        this.operationNumber = null;
        this.channel = null;
        this.amount = null;
        this.failureTime = Instant.now();
        this.errorCode = "PAYMENT_GENERIC_ERROR";
        this.severity = "HIGH";
        this.retryable = false;
    }

    public PaymentProcessingException(String message, Throwable cause) {
        super(message, cause);
        this.operationNumber = null;
        this.channel = null;
        this.amount = null;
        this.failureTime = Instant.now();
        this.errorCode = "PAYMENT_GENERIC_ERROR";
        this.severity = "HIGH";
        this.retryable = false;
    }

    public PaymentProcessingException(String operationNumber, String channel, BigDecimal amount, String message) {
        super(message);
        this.operationNumber = operationNumber;
        this.channel = channel;
        this.amount = amount;
        this.failureTime = Instant.now();
        this.errorCode = "PAYMENT_PROCESSING_ERROR";
        this.severity = "HIGH";
        this.retryable = true;
    }

    public PaymentProcessingException(String operationNumber, String channel, BigDecimal amount, String message, Throwable cause) {
        super(message, cause);
        this.operationNumber = operationNumber;
        this.channel = channel;
        this.amount = amount;
        this.failureTime = Instant.now();
        this.errorCode = "PAYMENT_PROCESSING_ERROR";
        this.severity = "HIGH";
        this.retryable = true;
    }

    public PaymentProcessingException(String operationNumber, String channel, BigDecimal amount, String message, String errorCode) {
        super(message);
        this.operationNumber = operationNumber;
        this.channel = channel;
        this.amount = amount;
        this.failureTime = Instant.now();
        this.errorCode = errorCode;
        this.severity = "HIGH";
        this.retryable = determineRetryable(errorCode);
    }

    public PaymentProcessingException(String operationNumber, String channel, BigDecimal amount, String message, 
                                       String errorCode, String severity, boolean retryable) {
        super(message);
        this.operationNumber = operationNumber;
        this.channel = channel;
        this.amount = amount;
        this.failureTime = Instant.now();
        this.errorCode = errorCode;
        this.severity = severity;
        this.retryable = retryable;
    }

    public PaymentProcessingException(String operationNumber, String channel, BigDecimal amount, String message, 
                                       String errorCode, String severity, boolean retryable, Throwable cause) {
        super(message, cause);
        this.operationNumber = operationNumber;
        this.channel = channel;
        this.amount = amount;
        this.failureTime = Instant.now();
        this.errorCode = errorCode;
        this.severity = severity;
        this.retryable = retryable;
    }

    private static boolean determineRetryable(String errorCode) {
        return "PAYMENT_TIMEOUT".equals(errorCode) || 
               "PAYMENT_CONNECTION_ERROR".equals(errorCode) ||
               "PAYMENT_NETWORK_ERROR".equals(errorCode);
    }

    public String getOperationNumber() {
        return operationNumber;
    }

    public String getChannel() {
        return channel;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Instant getFailureTime() {
        return failureTime;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getSeverity() {
        return severity;
    }

    public boolean isRetryable() {
        return retryable;
    }

    public boolean isCritical() {
        return "CRITICAL".equals(severity);
    }

    public boolean isHighSeverity() {
        return "HIGH".equals(severity);
    }

    public boolean isMediumSeverity() {
        return "MEDIUM".equals(severity);
    }

    public boolean isLowSeverity() {
        return "LOW".equals(severity);
    }

    @Override
    public String toString() {
        return String.format("PaymentProcessingException{operationNumber='%s', channel='%s', amount=%s, errorCode='%s', severity='%s', retryable=%s, failureTime=%s, message='%s'}",
                operationNumber, channel, amount, errorCode, severity, retryable, failureTime, getMessage());
    }
}

// === ARCHIVO: src/test/java/com/pragma/paymentservice/application/PaymentServiceTest.java ===
package com.pragma.paymentservice.application;

import com.pragma.paymentservice.domain.model.PaymentRequest;
import com.pragma.paymentservice.domain.model.PaymentResponse;
import com.pragma.paymentservice.domain.model.PaymentResponse.PaymentStatus;
import com.pragma.paymentservice.domain.port.out.PaymentPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentPersistencePort persistencePort;

    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentService(persistencePort);
    }

    @Test
    @DisplayName("Debe procesar pago exitosamente cuando no existe clave de idempotencia")
    void shouldProcessPaymentSuccessfullyWhenIdempotencyKeyDoesNotExist() {
        PaymentRequest request = createValidRequest();
        PaymentResponse expectedResponse = PaymentResponse.success(
            "OP-001", "Pago procesado correctamente", "12345"
        );

        when(persistencePort.findByIdempotencyKey(anyString())).thenReturn(Optional.empty());
        when(persistencePort.save(any(PaymentResponse.class), anyString(), anyString()))
            .thenReturn(expectedResponse);

        CompletableFuture<PaymentResponse> result = paymentService.processPayment(request);

        assertNotNull(result);
        assertEquals(PaymentStatus.SUCCESS, result.join().status());
        verify(persistencePort).findByIdempotencyKey(anyString());
        verify(persistencePort).save(any(PaymentResponse.class), anyString(), anyString());
    }

    @Test
    @DisplayName("Debe devolver pago existente cuando clave de idempotencia ya existe")
    void shouldReturnExistingPaymentWhenIdempotencyKeyExists() {
        PaymentRequest request = createValidRequest();
        PaymentResponse existingResponse = PaymentResponse.success(
            "OP-001", "Pago existente", "12345"
        );

        when(persistencePort.findByIdempotencyKey(anyString()))
            .thenReturn(Optional.of(existingResponse));

        CompletableFuture<PaymentResponse> result = paymentService.processPayment(request);

        assertNotNull(result);
        assertEquals(PaymentStatus.SUCCESS, result.join().status());
        assertEquals("Pago existente", result.join().message());
        verify(persistencePort, never()).save(any(), anyString(), anyString());
    }

    @Test
    @DisplayName("Debe fallar cuando la solicitud es inválida")
    void shouldFailWhenRequestIsInvalid() {
        PaymentRequest invalidRequest = new PaymentRequest(
            null, null, BigDecimal.ZERO, null, null
        );

        CompletableFuture<PaymentResponse> result = paymentService.processPayment(invalidRequest);

        assertNotNull(result);
        assertEquals(PaymentStatus.FAILURE, result.join().status());
    }

    @Test
    @DisplayName("Debe verificar estado de pago correctamente")
    void shouldCheckPaymentStatusCorrectly() {
        String operationNumber = "OP-001";
        String channel = "WEB";
        PaymentResponse existingResponse = PaymentResponse.success(
            operationNumber, "Pago encontrado", "12345"
        );

        when(persistencePort.findByOperationNumberAndChannel(operationNumber, channel))
            .thenReturn(Optional.of(existingResponse));

        CompletableFuture<PaymentResponse> result = 
            paymentService.checkPaymentStatus(operationNumber, channel);

        assertNotNull(result);
        assertEquals(PaymentStatus.SUCCESS, result.join().status());
        verify(persistencePort).findByOperationNumberAndChannel(operationNumber, channel);
    }

    @Test
    @DisplayName("Debe devolver failure cuando pago no existe al consultar estado")
    void shouldReturnFailureWhenPaymentNotFoundOnStatusCheck() {
        String operationNumber = "OP-999";
        String channel = "MOBILE";

        when(persistencePort.findByOperationNumberAndChannel(operationNumber, channel))
            .thenReturn(Optional.empty());

        CompletableFuture<PaymentResponse> result = 
            paymentService.checkPaymentStatus(operationNumber, channel);

        assertNotNull(result);
        assertEquals(PaymentStatus.FAILURE, result.join().status());
    }

    private PaymentRequest createValidRequest() {
        return new PaymentRequest(
            "OP-001",
            "WEB",
            new BigDecimal("1500.00"),
            "1234567890",
            "Pago de servicio"
        );
    }
}

// === ARCHIVO: src/test/java/com/pragma/paymentservice/infrastructure/controller/PaymentControllerTest.java ===
package com.pragma.paymentservice.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.paymentservice.application.PaymentService;
import com.pragma.paymentservice.domain.model.PaymentRequest;
import com.pragma.paymentservice.domain.model.PaymentResponse;
import com.pragma.paymentservice.domain.model.PaymentResponse.PaymentStatus;
import com.pragma.paymentservice.infrastructure.exception.GlobalExceptionHandler;
import com.pragma.paymentservice.infrastructure.exception.IdempotencyException;
import com.pragma.paymentservice.infrastructure.exception.PaymentProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
@Import(GlobalExceptionHandler.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentService paymentService;

    private PaymentRequest validRequest;
    private PaymentResponse successResponse;

    @BeforeEach
    void setUp() {
        validRequest = new PaymentRequest(
            "OP-20240101-001",
            "WEB",
            new BigDecimal("2500.00"),
            "9876543210",
            "Pago de crédito"
        );

        successResponse = PaymentResponse.success(
            "OP-20240101-001",
            "Pago procesado exitosamente",
            "TXN-12345"
        );
    }

    @Test
    @DisplayName("Debe retornar 200 OK cuando el pago se procesa correctamente")
    void shouldReturn200OkWhenPaymentIsProcessedSuccessfully() throws Exception {
        when(paymentService.processPayment(any(PaymentRequest.class)))
            .thenReturn(CompletableFuture.completedFuture(successResponse));

        mockMvc.perform(post("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("SUCCESS"))
            .andExpect(jsonPath("$.message").value("Pago procesado exitosamente"))
            .andExpect(jsonPath("$.transactionId").value("TXN-12345"));
    }

    @Test
    @DisplayName("Debe retornar 400 Bad Request cuando la solicitud es inválida")
    void shouldReturn400BadRequestWhenRequestIsInvalid() throws Exception {
        PaymentRequest invalidRequest = new PaymentRequest(
            null,
            "",
            BigDecimal.ZERO,
            null,
            null
        );

        when(paymentService.processPayment(any(PaymentRequest.class)))
            .thenReturn(CompletableFuture.completedFuture(
                PaymentResponse.failure("Datos de pago inválidos")
            ));

        mockMvc.perform(post("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Debe retornar 409 Conflict cuando hay conflicto de idempotencia")
    void shouldReturn409ConflictWhenIdempotencyConflictOccurs() throws Exception {
        when(paymentService.processPayment(any(PaymentRequest.class)))
            .thenReturn(CompletableFuture.failedFuture(
                new IdempotencyException("Clave de idempotencia duplicada")
            ));

        mockMvc.perform(post("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @DisplayName("Debe retornar 500 Internal Server Error cuando falla el procesamiento")
    void shouldReturn500WhenProcessingFails() throws Exception {
        when(paymentService.processPayment(any(PaymentRequest.class)))
            .thenReturn(CompletableFuture.failedFuture(
                new PaymentProcessingException("Error en el procesador de pagos")
            ));

        mockMvc.perform(post("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @DisplayName("Debe retornar 200 OK al consultar estado de pago existente")
    void shouldReturn200OkWhenCheckingStatusOfExistingPayment() throws Exception {
        String operationNumber = "OP-20240101-001";
        String channel = "WEB";

        when(paymentService.checkPaymentStatus(operationNumber, channel))
            .thenReturn(CompletableFuture.completedFuture(successResponse));

        mockMvc.perform(get("/api/v1/payments/status")
                .param("operationNumber", operationNumber)
                .param("channel", channel))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    @DisplayName("Debe retornar 404 Not Found al consultar estado de pago inexistente")
    void shouldReturn404WhenCheckingStatusOfNonExistentPayment() throws Exception {
        String operationNumber = "OP-INEXISTENTE";
        String channel = "WEB";

        when(paymentService.checkPaymentStatus(operationNumber, channel))
            .thenReturn(CompletableFuture.completedFuture(
                PaymentResponse.failure("Pago no encontrado")
            ));

        mockMvc.perform(get("/api/v1/payments/status")
                .param("operationNumber", operationNumber)
                .param("channel", channel))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Debe validar Content-Type application/json en las respuestas")
    void shouldValidateJsonContentTypeInResponses() throws Exception {
        when(paymentService.processPayment(any(PaymentRequest.class)))
            .thenReturn(CompletableFuture.completedFuture(successResponse));

        mockMvc.perform(post("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
```
