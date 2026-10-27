# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Implementación de Servicio de Pagos con Framework**.

| | |
|---|---|
| Tema | Experiencia en Frameworks |
| Nivel | advanced-l1 |
| Chapter | Backend |
| Especialidad | Java |
| Stack | Java 21 / Spring Boot 3.5 |
| Patron arquitectonico | hexagonal/clean |
| Tiempo estimado | 8 horas |

## Receta del stack

Esqueleto obligatorio:

- `pom.xml en la raiz`
- `clase con @SpringBootApplication`
- `application.yml en src/main/resources`
- `capa de dominio con entidades y puertos`
- `capa de aplicacion con casos de uso`
- `capa de infraestructura con adaptadores y @RestController`

Trampas conocidas:

- TODA `<version>` del pom va con tres segmentos: la del parent (ej. `3.5.6`) y la de cada dependencia que la lleve (ej. Resilience4j `2.2.0`). `3.4` y `2.0` no existen como artefacto y el build muere resolviendo dependencias.
- Las dependencias que el parent POM gestiona van SIN `<version>`: `spring-boot-starter-web`, `-data-jpa`, `-validation`, `-test`, etc.
- Resilience4j publica un artefacto por linea de Spring Boot. Con Spring Boot 3 va `resilience4j-spring-boot3` con version de tres segmentos (ej. `2.2.0`, no `2.0`). `resilience4j-spring-boot2` es de Spring Boot 2 y rompe el arranque.
- Si usas anotaciones de validacion (`@NotNull`, `@Size`, `@Positive`) declara `spring-boot-starter-validation`: el starter web no las trae.
- El `spring-boot-maven-plugin` tiene que estar en `<build><plugins>` o no se empaqueta ejecutable.
- Spring Boot 3 usa `jakarta.*`, nunca `javax.*`.
- Cada archivo empieza con su `package` y con un `import` por cada clase del proyecto que viva en otro paquete. Usar `PaymentService` desde `infrastructure` sin `import com.x.application.PaymentService` no compila.

Dependencias:

- org.springframework.boot:spring-boot-starter-web n/a
- org.springframework.boot:spring-boot-starter-data-jpa n/a
- org.springframework.boot:spring-boot-starter-validation n/a
- org.springframework.boot:spring-boot-starter-actuator n/a
- org.springframework.boot:spring-boot-starter-aop n/a
- org.springframework.boot:spring-boot-starter-test n/a
- org.springframework.boot:spring-boot-configuration-processor n/a
- org.springdoc:springdoc-openapi-starter-webmvc-ui 2.5.0
- org.postgresql:postgresql n/a
- com.h2database:h2 n/a
- org.projectlombok:lombok n/a
- io.github.resilience4j:resilience4j-spring-boot3 2.2.0
- org.mapstruct:mapstruct 1.5.5.Final
- org.mapstruct:mapstruct-processor 1.5.5.Final

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Configuración del Entorno**: Entorno de desarrollo configurado con variables de entorno listas para el servicio de pagos.
- **Fase 2 — Implementación del Servicio de Pagos**: Servicio de pagos implementado que enruta las solicitudes, controla las variables de entorno y genera registros de trazas.
- **Fase 3 — Conexión a la Base de Datos**: Servicio de pagos conectado a una base de datos utilizando un ORM.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Lo que falta y tenes que completar

### 1. Referencias colgando (36)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/com/pragma/paymentservice/domain/port/out/PaymentPersistencePort.java` — `com.pragma.paymentservice.domain.model.Payment`
      El import com.pragma.paymentservice.domain.model.Payment usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/pragma/paymentservice/application/PaymentService.java` — `com.pragma.paymentservice.domain.model.Payment`
      El import com.pragma.paymentservice.domain.model.Payment usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/pragma/paymentservice/infrastructure/config/IdempotencyConfig.java` — `com.pragma.paymentservice.infrastructure.adapter.entity.IdempotencyEntity`
      El import com.pragma.paymentservice.infrastructure.adapter.entity.IdempotencyEntity usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/pragma/paymentservice/infrastructure/config/IdempotencyConfig.java` — `com.pragma.paymentservice.infrastructure.adapter.repository.IdempotencyRepository`
      El import com.pragma.paymentservice.infrastructure.adapter.repository.IdempotencyRepository usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/pragma/paymentservice/application/PaymentService.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/paymentservice/infrastructure/config/IdempotencyConfig.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/paymentservice/infrastructure/controller/PaymentController.java` — `io.swagger.v3`
      El import io.swagger.v3.oas.annotations.Operation pertenece a io.swagger.v3, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/paymentservice/infrastructure/controller/PaymentController.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/paymentservice/infrastructure/config/OpenApiConfig.java` — `io.swagger.v3`
      El import io.swagger.v3.oas.models.Components pertenece a io.swagger.v3, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/paymentservice/infrastructure/exception/GlobalExceptionHandler.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/pragma/paymentservice/infrastructure/controller/PaymentControllerTest.java` — `com.fasterxml.jackson`
      El import com.fasterxml.jackson.databind.ObjectMapper pertenece a com.fasterxml.jackson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/paymentservice/application/PaymentService.java` — `PaymentRequest.operationNumber`
      Se invoca `operationNumber` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/paymentservice/application/PaymentService.java` — `PaymentRequest.channel`
      Se invoca `channel` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/paymentservice/application/PaymentService.java` — `PaymentRequest.createdAt`
      Se invoca `createdAt` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/paymentservice/application/PaymentService.java` — `PaymentRequest.amount`
      Se invoca `amount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/paymentservice/application/PaymentService.java` — `PaymentRequest.currency`
      Se invoca `currency` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/paymentservice/application/PaymentService.java` — `PaymentRequest.customerId`
      Se invoca `customerId` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/paymentservice/application/PaymentService.java` — `PaymentRequest.customerName`
      Se invoca `customerName` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/paymentservice/application/PaymentService.java` — `PaymentRequest.customerEmail`
      Se invoca `customerEmail` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/paymentservice/application/PaymentService.java` — `PaymentRequest.customerPhone`
      Se invoca `customerPhone` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/paymentservice/application/PaymentService.java` — `PaymentRequest.description`
      Se invoca `description` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRepository.findByIdempotencyKey`
      Se invoca `findByIdempotencyKey` sobre `PaymentRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.operationNumber`
      Se invoca `operationNumber` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.channel`
      Se invoca `channel` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.amount`
      Se invoca `amount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.currency`
      Se invoca `currency` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.customerId`
      Se invoca `customerId` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.merchantId`
      Se invoca `merchantId` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.description`
      Se invoca `description` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRepository.save`
      Se invoca `save` sobre `PaymentRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentResponse.status`
      Se invoca `status` sobre `PaymentResponse`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.isEmpty`
      Se invoca `isEmpty` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.get`
      Se invoca `get` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentResponse.message`
      Se invoca `message` sobre `PaymentResponse`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/paymentservice/application/PaymentServiceTest.java` — `PaymentPersistencePort.findByOperationNumberAndChannel`
      Se invoca `findByOperationNumberAndChannel` sobre `PaymentPersistencePort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (19)

- `pom.xml`
- `src/main/java/com/pragma/paymentservice/Application.java`
- `src/main/resources/application.yml`
- `src/main/java/com/pragma/paymentservice/domain/model/PaymentRequest.java`
- `src/main/java/com/pragma/paymentservice/domain/model/PaymentResponse.java`
- `src/main/java/com/pragma/paymentservice/domain/port/in/PaymentServicePort.java`
- `src/main/java/com/pragma/paymentservice/domain/port/out/PaymentPersistencePort.java`
- `src/main/java/com/pragma/paymentservice/infrastructure/adapter/repository/PaymentRepository.java`
- `src/main/java/com/pragma/paymentservice/application/PaymentService.java`
- `src/main/java/com/pragma/paymentservice/infrastructure/adapter/entity/PaymentEntity.java`
- `src/main/java/com/pragma/paymentservice/infrastructure/adapter/PaymentJpaAdapter.java`
- `src/main/java/com/pragma/paymentservice/infrastructure/config/IdempotencyConfig.java`
- `src/main/java/com/pragma/paymentservice/infrastructure/controller/PaymentController.java`
- `src/main/java/com/pragma/paymentservice/infrastructure/config/OpenApiConfig.java`
- `src/main/java/com/pragma/paymentservice/infrastructure/exception/GlobalExceptionHandler.java`
- `src/main/java/com/pragma/paymentservice/infrastructure/exception/IdempotencyException.java`
- `src/main/java/com/pragma/paymentservice/infrastructure/exception/PaymentProcessingException.java`
- `src/test/java/com/pragma/paymentservice/application/PaymentServiceTest.java`
- `src/test/java/com/pragma/paymentservice/infrastructure/controller/PaymentControllerTest.java`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/pragma/paymentservice`
- `src/main/java/com/pragma/paymentservice/application`
- `src/main/java/com/pragma/paymentservice/domain`
- `src/main/java/com/pragma/paymentservice/domain/model`
- `src/main/java/com/pragma/paymentservice/domain/port`
- `src/main/java/com/pragma/paymentservice/infrastructure`
- `src/main/java/com/pragma/paymentservice/infrastructure/adapter`
- `src/main/java/com/pragma/paymentservice/infrastructure/config`
- `src/main/java/com/pragma/paymentservice/infrastructure/controller`
- `src/main/resources`
- `src/test/java/com/pragma/paymentservice`

## Verificacion

```bash
mvn clean compile
```

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **hexagonal/clean**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Brecha que el reto ataca: Chapter Backend, Especialidad Desarrollador, Tecnolog�a Java, Advanced � Experiencia comprobable en al menos un marco de trabajo (Framework) en su lenguaje de programaci�n principal (por ejemplo, Spring Framework en Java o ASP .net core en C#) y puede realizar el enrutamiento de solicitudes, controlar las variables de entorno, generar registros de rastros (Logs) y conectarse con bases de datos utilizando un Mapeador Relacional de Objetos (ORM).

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
