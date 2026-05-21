# ⚽ Liga Colombia — Spring Data JPA Relations

Proyecto educativo de Spring Boot que demuestra las **4 relaciones JPA** entre entidades usando equipos del fútbol colombiano.

---

## 🗂️ Estructura del Proyecto

```
src/main/java/com/quiz/relaciones/
├── entidades/
│   ├── Club.java          ← entidad central (4 relaciones)
│   ├── Jugador.java
│   ├── Entrenador.java
│   ├── Asociacion.java
│   └── Competicion.java
├── repositorios/
│   ├── ClubRepository.java
│   ├── JugadorRepository.java
│   ├── EntrenadorRepository.java
│   ├── AsociacionRepository.java
│   └── CompeticionRepository.java
├── controladores/
│   ├── HomeController.java
│   ├── ClubController.java
│   ├── JugadorController.java
│   ├── EntrenadorController.java
│   ├── CompeticionController.java
│   └── AsociacionController.java
└── RelacionesApplication.java
```

---

## 🔗 Relaciones JPA

| Anotación | Entidades | FK |
|---|---|---|
| `@OneToOne` | Club ↔ Entrenador | `entrenador_id` en `clubes` |
| `@OneToMany` | Club → Jugadores | `id_club` en `jugadores` |
| `@ManyToOne` | Club → Asociacion | `asociacion_id` en `clubes` |
| `@ManyToMany` | Club ↔ Competicion | tabla `clubes_competiciones` |

---

## ⚙️ Tecnologías

- **Java 17**
- **Spring Boot 3.2**
- **Spring Data JPA** (JpaRepository — sin capa Service)
- **H2 Database** (en memoria)
- **Thymeleaf** + **Bootstrap 5**
- **Spring Validation**

---

## 🚀 Cómo ejecutar

1. Importar en Eclipse: `File → Import → Maven → Existing Maven Projects`
2. Seleccionar la carpeta del proyecto y el `pom.xml`
3. Clic derecho sobre el proyecto → `Run As → Spring Boot App`
4. Abrir `http://localhost:8085`

### Consola H2
- URL: `http://localhost:8085/h2-console`
- JDBC URL: `jdbc:h2:mem:relaciones_db`
- Usuario: `sa` | Contraseña: *(vacía)*

---

## 📄 Páginas disponibles

| URL | Descripción |
|---|---|
| `/` | Dashboard con estadísticas |
| `/clubes` | Lista de clubes |
| `/clubes/nuevo` | Crear club (asigna entrenador, asociación, competiciones) |
| `/clubes/{id}` | Detalle del club + agregar/eliminar jugadores |
| `/clubes/editar/{id}` | Editar club |
| `/jugadores` | Lista de jugadores |
| `/entrenadores` | Lista de entrenadores |
| `/entrenadores/nuevo` | Crear entrenador |
| `/competiciones` | Lista de competiciones |
| `/asociaciones` | Lista de asociaciones |

---

## 🗄️ Consultas SQL útiles (H2 Console)

```sql
-- Ver todas las relaciones de un club
SELECT c.NOMBRE AS club, e.APELLIDO AS entrenador,
       a.NOMBRE AS asociacion,
       COUNT(DISTINCT j.ID) AS jugadores,
       COUNT(DISTINCT cc.COMPETICION_ID) AS competiciones
FROM CLUBES c
LEFT JOIN ENTRENADORES e ON c.ENTRENADOR_ID = e.ID
LEFT JOIN ASOCIACIONES a ON c.ASOCIACION_ID = a.ID
LEFT JOIN JUGADORES j ON j.ID_CLUB = c.ID
LEFT JOIN CLUBES_COMPETICIONES cc ON cc.CLUB_ID = c.ID
GROUP BY c.ID, c.NOMBRE, e.APELLIDO, a.NOMBRE;
```

---

## 👨‍💻 Autor

Proyecto desarrollado con Spring Tools for Eclipse (STS 4).
