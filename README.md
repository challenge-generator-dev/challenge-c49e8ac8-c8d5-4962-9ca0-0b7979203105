# Implementación de Servicio de Pagos con Framework

En el dominio de la banca, el equipo de desarrollo necesita implementar un servicio que gestione las solicitudes de pago. Este servicio debe enrutar las solicitudes, controlar las variables de entorno, generar registros de trazas y conectarse a una base de datos utilizando un Mapeador Relacional de Objetos (ORM). Los actores involucrados son el 'originador de créditos', el'motor antifraude' y el 'core bancario'. El servicio debe procesar un mínimo de 1 500 solicitudes por segundo en hora pico. Las solicitudes deben ser idempotentes por número de operación y canal, asegurando que dos invocaciones con la misma clave produzcan un solo registro y devuelvan la misma respuesta dentro de una ventana de 24 horas.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | Experiencia en Frameworks |
| **Nivel** | advanced-l1 |
| **Tipo** | practical |
| **Tiempo estimado** | 8 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Configuración del Entorno

**Objetivo:** Establecer el entorno de desarrollo y configurar las variables de entorno necesarias para el servicio.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Identificar y configurar las variables de entorno necesarias para el servicio de pagos.
- Asegurar que el entorno de desarrollo esté preparado para la implementación del servicio.

**Entregable:** Entorno de desarrollo configurado con variables de entorno listas para el servicio de pagos.

<details>
<summary>Pistas de conocimiento</summary>

- Considera las variables de entorno que podrían ser necesarias para la conexión a la base de datos y el enrutamiento de solicitudes.

</details>

### Fase 2: Implementación del Servicio de Pagos

**Objetivo:** Implementar el servicio de pagos que enrute las solicitudes, controle las variables de entorno y genere registros de trazas.

**Tiempo estimado:** 4 horas

**Instrucciones:**

- Implementar el servicio de pagos que enrute las solicitudes recibidas del 'originador de créditos'.
- Asegurar que el servicio controle las variables de entorno y genere registros de trazas adecuados.
- Implementar la idempotencia de las solicitudes por número de operación y canal.

**Entregable:** Servicio de pagos implementado que enruta las solicitudes, controla las variables de entorno y genera registros de trazas.

<details>
<summary>Pistas de conocimiento</summary>

- Considera cómo implementar la idempotencia de las solicitudes y asegurar que dos invocaciones con la misma clave produzcan un solo registro y devuelvan la misma respuesta dentro de una ventana de 24 horas.

</details>

### Fase 3: Conexión a la Base de Datos

**Objetivo:** Conectar el servicio de pagos a una base de datos utilizando un Mapeador Relacional de Objetos (ORM).

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Conectar el servicio de pagos a una base de datos utilizando un ORM.
- Asegurar que el servicio pueda persistir y recuperar datos de la base de datos de manera eficiente.
- Implementar la lógica necesaria para manejar las transacciones y asegurar la consistencia de los datos.

**Entregable:** Servicio de pagos conectado a una base de datos utilizando un ORM.

<details>
<summary>Pistas de conocimiento</summary>

- Considera la mejor forma de manejar las transacciones y asegurar la consistencia de los datos al conectar el servicio a la base de datos.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es un Mapeador Relacional de Objetos (ORM) y para qué sirve en el contexto del servicio de pagos?
- **paraQueSirve**: ¿Para qué sirven las variables de entorno en el servicio de pagos y cómo se usan?
- **comoSeUsa**: ¿Cómo se implementa la idempotencia en el servicio de pagos y por qué es importante?
- **erroresComunes**: ¿Cuáles son los errores comunes al conectar un servicio a una base de datos y cómo se pueden evitar?
- **queDecisionesImplica**: ¿Qué decisiones implica la elección de un ORM para el servicio de pagos y cómo afecta al rendimiento y la consistencia de los datos?

## Criterios de Evaluacion

- Configuración correcta del entorno de desarrollo con variables de entorno.
- Implementación del servicio de pagos que enruta las solicitudes, controla las variables de entorno y genera registros de trazas.
- Conexión eficiente del servicio de pagos a una base de datos utilizando un ORM.
- Implementación correcta de la idempotencia en el servicio de pagos.
- Manejo adecuado de las transacciones y aseguramiento de la consistencia de los datos.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
