from pathlib import Path

from reportlab.lib import colors
from reportlab.lib.enums import TA_CENTER, TA_LEFT
from reportlab.lib.pagesizes import A4
from reportlab.lib.styles import ParagraphStyle, getSampleStyleSheet
from reportlab.lib.units import mm
from reportlab.pdfbase import pdfmetrics
from reportlab.pdfbase.ttfonts import TTFont
from reportlab.platypus import (
    BaseDocTemplate,
    Frame,
    PageBreak,
    PageTemplate,
    Paragraph,
    Spacer,
    Table,
    TableStyle,
    KeepTogether,
    Flowable,
)


ROOT = Path(__file__).resolve().parents[2]
OUTPUT = ROOT / "output" / "pdf" / "Guia_Multitenancy_LeveyQC.pdf"
OUTPUT.parent.mkdir(parents=True, exist_ok=True)

PURPLE = colors.HexColor("#6854C7")
PURPLE_DARK = colors.HexColor("#30256F")
PURPLE_LIGHT = colors.HexColor("#F0EDFF")
INK = colors.HexColor("#1D2433")
MUTED = colors.HexColor("#667085")
LINE = colors.HexColor("#E5E7EB")
GREEN = colors.HexColor("#16835A")
GREEN_LIGHT = colors.HexColor("#EAF8F2")
AMBER = colors.HexColor("#B35C00")
AMBER_LIGHT = colors.HexColor("#FFF4E5")
RED = colors.HexColor("#B42318")
RED_LIGHT = colors.HexColor("#FEECEB")
BLUE = colors.HexColor("#175CD3")
BLUE_LIGHT = colors.HexColor("#EFF8FF")

FONT_DIR = Path("/System/Library/Fonts/Supplemental")
pdfmetrics.registerFont(TTFont("Arial", str(FONT_DIR / "Arial.ttf")))
pdfmetrics.registerFont(TTFont("Arial-Bold", str(FONT_DIR / "Arial Bold.ttf")))
pdfmetrics.registerFont(TTFont("Arial-Italic", str(FONT_DIR / "Arial Italic.ttf")))


class NumberedDocTemplate(BaseDocTemplate):
    def __init__(self, filename, **kwargs):
        super().__init__(filename, **kwargs)
        self.section_name = "Guía educativa"


class ArchitectureDiagram(Flowable):
    def __init__(self, width=170 * mm, height=102 * mm):
        super().__init__()
        self.width = width
        self.height = height

    def draw_box(self, canvas, x, y, w, h, title, subtitle, fill, stroke=PURPLE):
        canvas.setFillColor(fill)
        canvas.setStrokeColor(stroke)
        canvas.roundRect(x, y, w, h, 7, fill=1, stroke=1)
        canvas.setFillColor(INK)
        canvas.setFont("Arial-Bold", 9)
        canvas.drawCentredString(x + w / 2, y + h - 13, title)
        canvas.setFillColor(MUTED)
        canvas.setFont("Arial", 7.2)
        canvas.drawCentredString(x + w / 2, y + 9, subtitle)

    def arrow(self, canvas, x1, y1, x2, y2):
        canvas.setStrokeColor(PURPLE)
        canvas.setFillColor(PURPLE)
        canvas.setLineWidth(1.3)
        canvas.line(x1, y1, x2, y2)
        if abs(x2 - x1) > abs(y2 - y1):
            direction = 1 if x2 > x1 else -1
            canvas.line(x2, y2, x2 - 5 * direction, y2 + 3)
            canvas.line(x2, y2, x2 - 5 * direction, y2 - 3)
        else:
            direction = 1 if y2 > y1 else -1
            canvas.line(x2, y2, x2 - 3, y2 - 5 * direction)
            canvas.line(x2, y2, x2 + 3, y2 - 5 * direction)

    def draw(self):
        c = self.canv
        w = self.width
        self.draw_box(c, 6, 240, 120, 44, "Frontend + Clerk", "Bearer JWT", BLUE_LIGHT, BLUE)
        self.draw_box(c, 188, 240, 120, 44, "Filtro de seguridad", "resuelve actor", PURPLE_LIGHT)
        self.draw_box(c, 370, 240, 120, 44, "LaboratorioContext", "ThreadLocal<Long>", AMBER_LIGHT, AMBER)
        self.arrow(c, 126, 262, 188, 262)
        self.arrow(c, 308, 262, 370, 262)

        self.draw_box(c, 98, 150, 135, 50, "Persistencia central", "usuarios + catálogo DB", GREEN_LIGHT, GREEN)
        self.draw_box(c, 290, 150, 135, 50, "Persistencia tenant", "entidades del laboratorio", PURPLE_LIGHT)
        self.arrow(c, 248, 240, 175, 200)
        self.arrow(c, 430, 240, 358, 200)

        self.draw_box(c, 18, 57, 135, 48, "LeveyCentral", "BaseDatosLaboratorio", GREEN_LIGHT, GREEN)
        self.draw_box(c, 194, 57, 135, 48, "Gestor + Registro", "pool por laboratorio", AMBER_LIGHT, AMBER)
        self.draw_box(c, 370, 57, 135, 48, "BD laboratorio", "datos clínicos aislados", BLUE_LIGHT, BLUE)
        self.arrow(c, 165, 150, 86, 105)
        self.arrow(c, 358, 150, 262, 105)
        self.arrow(c, 153, 81, 194, 81)
        self.arrow(c, 329, 81, 370, 81)

        c.setFillColor(MUTED)
        c.setFont("Arial-Italic", 7)
        c.drawString(6, 19, "La identidad del tenant viaja en contexto; las credenciales no viajan en el JWT.")


class LifecycleDiagram(Flowable):
    def __init__(self, width=170 * mm, height=87 * mm):
        super().__init__()
        self.width = width
        self.height = height

    def draw(self):
        c = self.canv
        steps = [
            ("1", "JWT", "Clerk autentica"),
            ("2", "Actor", "consulta central"),
            ("3", "Contexto", "guarda labId"),
            ("4", "Resolver", "lee tenant"),
            ("5", "Provider", "pide conexión"),
            ("6", "Pool", "reutiliza JDBC"),
            ("7", "finally", "limpia contexto"),
        ]
        y = 180
        for index, (number, title, subtitle) in enumerate(steps):
            x = 3 + index * 74
            c.setFillColor(PURPLE if index < 6 else GREEN)
            c.circle(x + 25, y + 30, 14, fill=1, stroke=0)
            c.setFillColor(colors.white)
            c.setFont("Arial-Bold", 9)
            c.drawCentredString(x + 25, y + 27, number)
            c.setFillColor(INK)
            c.setFont("Arial-Bold", 7.5)
            c.drawCentredString(x + 25, y, title)
            c.setFillColor(MUTED)
            c.setFont("Arial", 6.4)
            c.drawCentredString(x + 25, y - 12, subtitle)
            if index < len(steps) - 1:
                c.setStrokeColor(LINE)
                c.setLineWidth(2)
                c.line(x + 40, y + 30, x + 72, y + 30)
        c.setFillColor(PURPLE_LIGHT)
        c.roundRect(20, 38, 470, 72, 8, fill=1, stroke=0)
        c.setFillColor(PURPLE_DARK)
        c.setFont("Arial-Bold", 9)
        c.drawString(34, 84, "Invariante de aislamiento")
        c.setFont("Arial", 8)
        c.drawString(34, 65, "La consulta tenant solo ocurre después de identificar al usuario y fijar su laboratorio.")
        c.drawString(34, 48, "Al terminar la petición, el ThreadLocal se elimina aunque exista una excepción.")


styles = getSampleStyleSheet()
styles.add(ParagraphStyle(
    name="BodyA", fontName="Arial", fontSize=9.2, leading=13.4,
    textColor=INK, spaceAfter=7,
))
styles.add(ParagraphStyle(
    name="SmallA", fontName="Arial", fontSize=7.8, leading=11,
    textColor=MUTED, spaceAfter=4,
))
styles.add(ParagraphStyle(
    name="TitleA", fontName="Arial-Bold", fontSize=24, leading=27,
    textColor=PURPLE_DARK, spaceAfter=10,
))
styles.add(ParagraphStyle(
    name="H2A", fontName="Arial-Bold", fontSize=13.5, leading=17,
    textColor=INK, spaceBefore=6, spaceAfter=6,
))
styles.add(ParagraphStyle(
    name="KickerA", fontName="Arial-Bold", fontSize=7.7, leading=10,
    textColor=PURPLE, spaceAfter=7,
))
styles.add(ParagraphStyle(
    name="BulletA", parent=styles["BodyA"], leftIndent=13, firstLineIndent=-7,
    bulletIndent=0, spaceAfter=4,
))
styles.add(ParagraphStyle(
    name="CodeA", fontName="Courier", fontSize=7.2, leading=9.5,
    textColor=colors.HexColor("#D6E4FF"), backColor=colors.HexColor("#121A2B"),
    borderPadding=8, spaceBefore=5, spaceAfter=8,
))
styles.add(ParagraphStyle(
    name="CalloutA", fontName="Arial", fontSize=8.5, leading=12.5,
    textColor=INK, leftIndent=8, rightIndent=8, borderPadding=8,
    borderWidth=0.8, borderColor=LINE, backColor=colors.HexColor("#FAFAFC"),
    spaceBefore=4, spaceAfter=8,
))
styles.add(ParagraphStyle(
    name="CoverTitle", fontName="Arial-Bold", fontSize=34, leading=37,
    textColor=colors.white, alignment=TA_LEFT,
))
styles.add(ParagraphStyle(
    name="CoverSub", fontName="Arial", fontSize=13, leading=18,
    textColor=colors.HexColor("#E7E2FF"), alignment=TA_LEFT,
))
styles.add(ParagraphStyle(
    name="QuoteA", fontName="Arial-Italic", fontSize=11, leading=16,
    textColor=PURPLE_DARK, leftIndent=12, rightIndent=12,
    borderColor=PURPLE, borderWidth=0, borderLeftWidth=3,
    borderPadding=8, spaceBefore=8, spaceAfter=10,
))


def P(text, style="BodyA"):
    return Paragraph(text, styles[style])


def bullets(items):
    return [P(f"• {item}", "BulletA") for item in items]


def code(text):
    escaped = text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
    return P(escaped.replace("\n", "<br/>"), "CodeA")


def callout(label, text, tone="neutral"):
    palette = {
        "neutral": (PURPLE_LIGHT, PURPLE_DARK),
        "success": (GREEN_LIGHT, GREEN),
        "warning": (AMBER_LIGHT, AMBER),
        "danger": (RED_LIGHT, RED),
        "info": (BLUE_LIGHT, BLUE),
    }
    background, accent = palette[tone]
    style = ParagraphStyle(
        f"Callout-{label}-{tone}", parent=styles["CalloutA"],
        backColor=background, borderColor=accent, textColor=INK,
    )
    return Paragraph(f"<b>{label}</b><br/>{text}", style)


def page_heading(kicker, title, lead=None):
    parts = [P(kicker.upper(), "KickerA"), P(title, "TitleA")]
    if lead:
        parts.append(P(lead, "QuoteA"))
    return parts


def table(rows, widths, header=True, font_size=7.6):
    data = [[P(str(cell), "SmallA") for cell in row] for row in rows]
    t = Table(data, colWidths=widths, repeatRows=1 if header else 0, hAlign="LEFT")
    commands = [
        ("VALIGN", (0, 0), (-1, -1), "TOP"),
        ("GRID", (0, 0), (-1, -1), 0.45, LINE),
        ("LEFTPADDING", (0, 0), (-1, -1), 6),
        ("RIGHTPADDING", (0, 0), (-1, -1), 6),
        ("TOPPADDING", (0, 0), (-1, -1), 5),
        ("BOTTOMPADDING", (0, 0), (-1, -1), 5),
        ("BACKGROUND", (0, 0), (-1, 0), PURPLE_DARK if header else colors.white),
        ("TEXTCOLOR", (0, 0), (-1, 0), colors.white if header else INK),
        ("FONTNAME", (0, 0), (-1, 0), "Arial-Bold"),
        ("ROWBACKGROUNDS", (0, 1 if header else 0), (-1, -1), [colors.white, colors.HexColor("#FAFAFC")]),
    ]
    t.setStyle(TableStyle(commands))
    return t


def exercise(number, title, objective, setup, tasks, observations, criteria, questions, hint):
    content = page_heading(f"Laboratorio {number}", title, objective)
    content += [P("Preparación", "H2A"), P(setup)]
    content += [P("Trabajo de Nicolás", "H2A")]
    content += bullets([f"<b>{i + 1}.</b> {task}" for i, task in enumerate(tasks)])
    content += [P("Qué deberías observar", "H2A")]
    content += bullets(observations)
    content += [callout("Criterio de aprobación", criteria, "success")]
    content += [P("Preguntas para razonar", "H2A")]
    content += bullets(questions)
    content += [callout("Pista graduada", hint, "info")]
    return content


def on_page(canvas, doc):
    page = canvas.getPageNumber()
    canvas.saveState()
    if page == 1:
        canvas.restoreState()
        draw_cover(canvas, doc)
        return
    canvas.setStrokeColor(LINE)
    canvas.setLineWidth(0.6)
    canvas.line(20 * mm, A4[1] - 15 * mm, A4[0] - 20 * mm, A4[1] - 15 * mm)
    canvas.setFont("Arial-Bold", 7.2)
    canvas.setFillColor(PURPLE)
    canvas.drawString(20 * mm, A4[1] - 11.5 * mm, "LEVEYQC · MULTITENANCY EDUCATIVO")
    canvas.setFont("Arial", 7.2)
    canvas.setFillColor(MUTED)
    canvas.drawRightString(A4[0] - 20 * mm, A4[1] - 11.5 * mm, "Arquitectura actual + laboratorio aislado")
    canvas.line(20 * mm, 14 * mm, A4[0] - 20 * mm, 14 * mm)
    canvas.setFont("Arial", 7)
    canvas.drawString(20 * mm, 9.5 * mm, "Guía de estudio · septiembre de 2026")
    canvas.setFont("Arial-Bold", 7)
    canvas.setFillColor(PURPLE_DARK)
    canvas.drawRightString(A4[0] - 20 * mm, 9.5 * mm, f"{page}")
    canvas.restoreState()


def draw_cover(canvas, doc):
    w, h = A4
    canvas.saveState()
    canvas.setFillColor(PURPLE_DARK)
    canvas.rect(0, 0, w, h, fill=1, stroke=0)
    canvas.setFillColor(PURPLE)
    canvas.circle(w - 18 * mm, h - 20 * mm, 55 * mm, fill=1, stroke=0)
    canvas.setFillColor(colors.HexColor("#7A67D7"))
    canvas.circle(w + 10 * mm, 25 * mm, 72 * mm, fill=1, stroke=0)
    canvas.setFillColor(colors.HexColor("#42358D"))
    canvas.roundRect(18 * mm, h - 54 * mm, 45 * mm, 9 * mm, 4 * mm, fill=1, stroke=0)
    canvas.setFillColor(colors.white)
    canvas.setFont("Arial-Bold", 8)
    canvas.drawCentredString(40.5 * mm, h - 50.8 * mm, "GUÍA TÉCNICA · 2026")
    canvas.setFont("Arial-Bold", 34)
    canvas.drawString(18 * mm, h - 92 * mm, "Multitenancy")
    canvas.drawString(18 * mm, h - 107 * mm, "en LeveyQC")
    canvas.setFont("Arial", 13)
    canvas.setFillColor(colors.HexColor("#E7E2FF"))
    canvas.drawString(18 * mm, h - 126 * mm, "De una petición autenticada a una conexión")
    canvas.drawString(18 * mm, h - 134 * mm, "del pool correcto para cada laboratorio")
    canvas.setFillColor(colors.white)
    canvas.setFont("Arial-Bold", 10)
    canvas.drawString(18 * mm, 48 * mm, "Arquitectura observada · razonamiento · ejercicios progresivos")
    canvas.setFont("Arial", 8.5)
    canvas.setFillColor(colors.HexColor("#D3CCFF"))
    canvas.drawString(18 * mm, 39 * mm, "Preparada para Nicolás · uso educativo y replicación aislada")
    canvas.drawString(18 * mm, 32 * mm, "No contiene contraseñas ni secretos del entorno")
    canvas.restoreState()


doc = NumberedDocTemplate(
    str(OUTPUT), pagesize=A4,
    rightMargin=20 * mm, leftMargin=20 * mm,
    topMargin=21 * mm, bottomMargin=19 * mm,
    title="Multitenancy en LeveyQC",
    author="Guía educativa para Nicolás",
    subject="Arquitectura multi-tenant database-per-tenant con Hibernate y HikariCP",
)
frame = Frame(doc.leftMargin, doc.bottomMargin, doc.width, doc.height, id="normal")
doc.addPageTemplates([
    PageTemplate(id="content", frames=frame, onPage=on_page),
])

story = []

# 1. Portada
story += [Spacer(1, 1), PageBreak()]

# 2. Propósito
story += page_heading("Cómo usar esta guía", "Aprender el mecanismo, no memorizar clases",
                      "El objetivo es que puedas explicar, construir y depurar el recorrido completo sin depender del proyecto original.")
story += [P("Esta guía documenta la arquitectura encontrada en LeveyQC y la convierte en una secuencia de aprendizaje. Primero formarás un modelo mental; después estudiarás cada pieza; finalmente la replicarás en un proyecto nuevo con ejercicios incompletos a propósito.")]
story += bullets([
    "Lee las páginas 3 a 20 antes de programar. Dibuja el recorrido de una petición con tus propias palabras.",
    "Crea un proyecto aislado: no copies paquetes completos. Reproduce una responsabilidad por vez.",
    "En cada laboratorio escribe primero una hipótesis, ejecuta una prueba y registra lo observado.",
    "No avances si el criterio de aprobación de la etapa anterior no se cumple.",
])
story += [callout("Resultado esperado", "Al terminar podrás responder qué identifica al tenant, quién traduce ese identificador a una conexión, cuándo nace un pool, cómo vuelve una conexión al pool y por qué el contexto debe limpiarse siempre.", "success")]
story += [P("Ruta de aprendizaje", "H2A")]
story += [table([
    ["Bloque", "Páginas", "Capacidad"],
    ["Modelo", "3-8", "Explicar arquitectura y flujo"],
    ["Mecanismo", "9-19", "Seguir tenant, provider y pool"],
    ["Escalabilidad", "20-23", "Dar de alta tenants sin redesplegar"],
    ["Práctica", "24-32", "Construir y experimentar"],
    ["Referencia", "33-36", "Depurar y consultar fuentes"],
], [35 * mm, 25 * mm, 105 * mm])]
story += [PageBreak()]

# 3. Modelo mental
story += page_heading("Fundamento", "Un laboratorio es un tenant",
                      "En LeveyQC, el tenant no es una fila más dentro de una tabla compartida: el diseño apunta a una base de datos separada por laboratorio.")
story += [P("El patrón observado es <b>database per tenant</b>. Existe una base central, LeveyCentral, que conoce usuarios, permisos, laboratorios y la configuración necesaria para encontrar la base de cada laboratorio. Los datos operacionales del laboratorio viven en otra base.")]
story += [P("Tres identidades que no debes mezclar", "H2A")]
story += [table([
    ["Identidad", "Ejemplo conceptual", "Responsabilidad"],
    ["Usuario Clerk", "sub del JWT", "Demuestra quién inició sesión"],
    ["Usuario interno", "UsuariosLevey", "Une identidad externa con reglas propias"],
    ["Tenant", "laboratorioId", "Decide qué base recibe la consulta"],
], [38 * mm, 50 * mm, 77 * mm])]
story += [callout("Regla clave", "El token autentica al usuario, pero el backend determina el laboratorio consultando su modelo central. El frontend no debería elegir libremente el identificador de tenant.", "warning")]
story += [P("Esta separación reduce el riesgo de que un usuario manipule un encabezado como X-Tenant-Id. También concentra la relación usuario-laboratorio en la base central, donde puede ser auditada y modificada.")]
story += [PageBreak()]

# 4. Diagrama
story += page_heading("Mapa de arquitectura", "Las piezas y sus límites")
story += [ArchitectureDiagram(), Spacer(1, 5 * mm)]
story += [P("La mitad izquierda representa el <b>plano de control</b>: autenticación, usuarios y catálogo de conexiones. La mitad derecha representa el <b>plano de datos</b>: repositorios y tablas del laboratorio seleccionado.")]
story += bullets([
    "Persistencia central usa el DataSource principal y repositorios centrales.",
    "Persistencia laboratorio usa Hibernate multitenancy, no un DataSource fijo por entidad.",
    "LaboratorioContext transporta únicamente laboratorioId durante la petición.",
    "GestorPoolLaboratorio traduce ese id a una configuración central validada.",
    "RegistroPoolsLaboratorio reutiliza un HikariDataSource por laboratorio.",
])
story += [PageBreak()]

# 5. Dos EMFs
story += page_heading("Separación JPA", "Dos EntityManagerFactory, dos mundos",
                      "La separación de paquetes y repositorios evita que una entidad central termine consultándose contra la base de un tenant, o al revés.")
story += [table([
    ["Aspecto", "Unidad central", "Unidad laboratorio"],
    ["Configuración", "PersistenciaCentralConfiguracion", "PersistenciaLaboratorioConfiguracion"],
    ["Persistence unit", "central", "laboratorio"],
    ["Transaction manager", "transactionManager", "laboratorioTransactionManager"],
    ["Selección DB", "DataSource fijo", "MultiTenantConnectionProvider"],
    ["DDL", "propiedad principal", "hibernate.hbm2ddl.auto = none"],
], [40 * mm, 62 * mm, 63 * mm])]
story += [P("Spring Data necesita saber qué repositorio pertenece a qué EntityManagerFactory. Por eso cada @EnableJpaRepositories indica paquetes concretos y referencias explícitas al entityManagerFactory y al transactionManager correspondientes.")]
story += [callout("Fallo típico", "Si un repositorio tenant queda escaneado por la configuración central, puede funcionar en compilación y fallar semánticamente: consultará el esquema incorrecto. La estructura de paquetes también es una barrera de seguridad.", "danger")]
story += [P("En el proyecto actual, la unidad central es @Primary. Esto resuelve inyecciones ambiguas cuando un componente no declara explícitamente cuál EntityManagerFactory o TransactionManager necesita.")]
story += [PageBreak()]

# 6. Central
story += page_heading("Persistencia central", "El catálogo que gobierna a los tenants")
story += [P("PersistenciaCentralConfiguracion construye el EntityManagerFactory central con el DataSource principal y registra siete áreas de repositorios: administradores, asignaciones de permisos, configuración de bases, laboratorios, acciones, tipos de usuario y usuarios Levey.")]
story += [P("Su responsabilidad no es devolver datos clínicos. Su responsabilidad es responder preguntas de control:", "H2A")]
story += bullets([
    "¿El JWT corresponde a un usuario interno conocido?",
    "¿Ese usuario es administrador o usuario de laboratorio?",
    "¿A qué laboratorio pertenece?",
    "¿Existe una configuración de base para ese laboratorio?",
    "¿La configuración está activa y habilitada para conexión?",
])
story += [callout("Diseño importante", "La configuración central debe estar disponible antes de intentar resolver cualquier conexión tenant. Por eso la persistencia de laboratorio depende del entityManagerFactory central.", "info")]
story += [P("Archivo principal: <font name='Courier'>src/main/java/cl/leveyqc/leveyqc/Configuracion/PersistenciaCentralConfiguracion.java</font>.")]
story += [P("Ejercicio de lectura: enumera en una hoja qué tablas centrales intervienen antes de una consulta a InformacionLaboratorio. No programes todavía; intenta separar datos de identidad, autorización y conexión.")]
story += [PageBreak()]

# 7. Tenant config
story += page_heading("Persistencia tenant", "Hibernate recibe dos colaboradores")
story += [P("PersistenciaLaboratorioConfiguracion construye la segunda unidad y entrega a Hibernate dos objetos mediante MultiTenancySettings:")]
story += [table([
    ["Colaborador", "Pregunta que responde"],
    ["LaboratorioTenantIdentifierResolver", "¿Cuál es el tenant de la operación actual?"],
    ["LaboratorioMultiTenantConnectionProvider", "¿Qué conexión JDBC corresponde a ese tenant?"],
], [73 * mm, 92 * mm])]
story += [code("properties.put(MULTI_TENANT_IDENTIFIER_RESOLVER, resolver)\nproperties.put(MULTI_TENANT_CONNECTION_PROVIDER, provider)\nproperties.put(\"hibernate.hbm2ddl.auto\", \"none\")")]
story += [P("La configuración escanea la entidad InformacionLaboratorio y asocia su repositorio al laboratorioEntityManagerFactory. Cuando Spring Data ejecuta findAll(), Hibernate inicia la resolución del tenant antes de obtener la conexión.")]
story += [callout("Por qué DDL = none", "Con múltiples bases no conviene que el arranque intente crear o alterar automáticamente estructuras sin una estrategia explícita. Las migraciones por tenant deben estar controladas y versionadas.", "warning")]
story += [P("Archivo principal: <font name='Courier'>src/main/java/cl/leveyqc/leveyqc/Configuracion/PersistenciaLaboratorioConfiguracion.java</font>.")]
story += [PageBreak()]

# 8. Lifecycle
story += page_heading("Recorrido completo", "De fetch() a una conexión física")
story += [LifecycleDiagram(), Spacer(1, 3 * mm)]
story += [P("El orden importa más que cualquiera de las clases por separado. Un repository no recibe laboratorioId como parámetro: Hibernate lo pregunta al resolver en el momento de trabajar con la sesión.")]
story += bullets([
    "El frontend envía Authorization: Bearer &lt;token&gt;.",
    "Spring Security valida firma, emisor y vigencia del JWT.",
    "FiltroUsuariosSistema resuelve el actor contra la persistencia central.",
    "Si es usuario de laboratorio, fija LaboratorioContext con su id.",
    "El servicio llama al repositorio tenant.",
    "Hibernate consulta resolver y provider; el pool entrega una conexión.",
    "connection.close() devuelve la conexión a HikariCP; finally elimina el ThreadLocal.",
])
story += [PageBreak()]

# 9. Security
story += page_heading("Etapa 1", "JWT, actor y autoridad",
                      "Autenticación responde quién eres; autorización y multitenancy responden qué puedes hacer y dónde se ejecuta.")
story += [P("FiltroUsuariosSistema extiende OncePerRequestFilter. Obtiene Authentication, lee el JWT y usa ResolutorActorService para localizar al actor interno. Después reemplaza o amplía autoridades de Spring Security.")]
story += [table([
    ["Actor", "Autoridad", "Contexto tenant"],
    ["Administrador", "ROLE_ADMIN", "No fija laboratorio por defecto"],
    ["Usuario Levey", "ROLE_USUARIO_LEVEY", "Fija id desde UsuariosLevey"],
    ["No reconocido", "Sin acceso útil", "No debe consultar tenant"],
], [48 * mm, 58 * mm, 59 * mm])]
story += [callout("Frontera de confianza", "El frontend puede mostrar organization.name, pero la organización visual de Clerk no debe sustituir la asociación interna validada por el backend, salvo que diseñes y audites explícitamente esa equivalencia.", "warning")]
story += [P("La consulta central ocurre antes de poblar el contexto. Así, el tenant nace de datos controlados por el servidor, no de un valor arbitrario enviado por el cliente.")]
story += [PageBreak()]

# 10. ThreadLocal
story += page_heading("Etapa 2", "LaboratorioContext y el alcance de la petición")
story += [P("LaboratorioContext encapsula un ThreadLocal&lt;Long&gt;. Su contrato es pequeño: setLaboratorioId valida un id positivo, getLaboratorioId lo recupera y clear ejecuta remove().")]
story += [code("try {\n    LaboratorioContext.setLaboratorioId(laboratorioId);\n    filterChain.doFilter(request, response);\n} finally {\n    LaboratorioContext.clear();\n}")]
story += [P("Los servidores reutilizan hilos. Si el id queda asociado después de terminar una petición, el mismo hilo podría atender luego a otro usuario conservando el tenant anterior. Esa es una fuga de aislamiento, no solo una pérdida de memoria.")]
story += [callout("Invariante", "Toda ruta que establezca contexto debe limpiarlo en finally. El finally también se ejecuta si el controlador, servicio, repositorio o serialización lanza una excepción.", "danger")]
story += [P("Java documenta que un valor ThreadLocal permanece asociado al hilo hasta que se elimina o el hilo termina. En un pool de hilos, el hilo puede vivir durante toda la aplicación; por eso remove() es obligatorio.")]
story += [P("Archivo: <font name='Courier'>.../BaseDatosLaboratorio/contexto/LaboratorioContext.java</font>.")]
story += [PageBreak()]

# 11. Resolver
story += page_heading("Etapa 3", "CurrentTenantIdentifierResolver")
story += [P("LaboratorioTenantIdentifierResolver adapta el contexto propio al contrato de Hibernate. resolveCurrentTenantIdentifier() lee LaboratorioContext y devuelve el Long. Si no existe, lanza HibernateException.")]
story += [P("Lanzar temprano es preferible a usar un tenant por defecto. Un fallback silencioso podría dirigir una operación hacia la base equivocada y ocultar el error de seguridad.")]
story += [code("resolveCurrentTenantIdentifier():\n    id = LaboratorioContext.getLaboratorioId()\n    si id es null -> error explícito\n    retornar id")]
story += [callout("validateExistingCurrentSessions = true", "Hibernate puede comprobar que una sesión ya asociada a un tenant no sea reutilizada con otro identificador. Refuerza la coherencia entre contexto y sesión.", "info")]
story += [P("Pregunta de diseño: ¿qué debería ocurrir si un administrador llama a un repositorio tenant sin haber seleccionado un laboratorio? En la arquitectura actual, debe fallar por ausencia de contexto. Para soportar administración cruzada necesitarías un flujo explícito y auditable de selección, no un valor implícito.")]
story += [PageBreak()]

# 12. Provider
story += page_heading("Etapa 4", "MultiTenantConnectionProvider")
story += [P("LaboratorioMultiTenantConnectionProvider implementa el punto de extensión JDBC de Hibernate. Recibe un identificador Long, solicita el HikariDataSource correcto al gestor y obtiene una conexión.")]
story += [table([
    ["Método", "Uso en el proyecto"],
    ["getConnection(id)", "Valida id, obtiene pool y llama pool.getConnection()"],
    ["releaseConnection(id, connection)", "Ejecuta close(); Hikari recupera la conexión"],
    ["getAnyConnection()", "Usa DataSource central para metadatos de arranque"],
    ["releaseAnyConnection(connection)", "Cierra/devuelve la conexión de bootstrap"],
    ["supportsAggressiveRelease()", "false; evita liberación agresiva"],
], [62 * mm, 103 * mm])]
story += [callout("close() no significa destruir", "En una conexión envuelta por HikariCP, close() normalmente la devuelve al pool. El pool decide cuándo cerrar la conexión física según salud y ciclo de vida.", "success")]
story += [P("El provider no conoce URLs, contraseñas ni reglas de negocio. Su tarea es traducir tenantId a una Connection usando servicios especializados.")]
story += [PageBreak()]

# 13. Any connection
story += page_heading("Detalle de arranque", "Por qué existe getAnyConnection()")
story += [P("Hibernate puede necesitar una conexión antes de que exista una petición y, por lo tanto, antes de que LaboratorioContext tenga un id. Ejemplos: inspeccionar metadatos JDBC, dialecto o capacidades del motor.")]
story += [P("El provider actual entrega una conexión del DataSource central en getAnyConnection(). Esto permite el bootstrap sin inventar un tenant predeterminado.")]
story += [callout("Precaución", "La conexión de bootstrap no debe usarse para ejecutar repositorios tenant. Es una conexión auxiliar para inicialización. Mantén hibernate.hbm2ddl.auto en none y verifica que dialecto/motor sean compatibles entre central y tenants.", "warning")]
story += [P("Experimento mental", "H2A")]
story += bullets([
    "Si getAnyConnection() llamara a getConnection(null), el provider fallaría porque no hay contexto.",
    "Si escogiera siempre el laboratorio 1, el arranque dependería de que ese tenant estuviera disponible.",
    "Si central y tenants usaran motores incompatibles, los metadatos podrían inducir una configuración errónea.",
])
story += [P("La documentación oficial de Hibernate describe MultiTenantConnectionProvider como el contrato para adquirir conexiones según el identificador de tenant en estrategias de base o esquema por tenant.")]
story += [PageBreak()]

# 14. Manager
story += page_heading("Etapa 5", "GestorPoolLaboratorio valida antes de conectar")
story += [P("GestorPoolLaboratorio es el orquestador. No crea mapas ni configura Hikari directamente; coordina RegistroPoolsLaboratorio, BaseDatosLaboratorioService y FabricaPoolLaboratorio.")]
story += [P("Secuencia al pedir un pool", "H2A")]
story += bullets([
    "1. Rechaza laboratorioId nulo, cero o negativo.",
    "2. Pregunta al registro si ya existe un pool para ese id.",
    "3. En el primer acceso consulta BaseDatosLaboratorio en LeveyCentral.",
    "4. Verifica que la configuración corresponda al mismo laboratorio.",
    "5. Exige activo = 1 y estadoConexion = 1.",
    "6. Delega a la fábrica la creación del HikariDataSource.",
    "7. Conserva el pool para siguientes solicitudes.",
])
story += [callout("Defensa en profundidad", "Aunque el id proviene del usuario central, el gestor vuelve a validar la configuración y su estado. No basta con que una fila exista: debe estar habilitada para conexión.", "success")]
story += [P("Este diseño permite invalidar un pool cuando cambia una configuración. Sin invalidación, el mapa conservaría conexiones con credenciales o destino antiguos.")]
story += [PageBreak()]

# 15. Registry
story += page_heading("Etapa 6", "RegistroPoolsLaboratorio: caché concurrente")
story += [P("RegistroPoolsLaboratorio mantiene ConcurrentHashMap&lt;Long, HikariDataSource&gt;. obtenerOCrearPool usa computeIfAbsent: para una clave ausente, calcula y registra el pool de forma atómica.")]
story += [code("pool = pools.computeIfAbsent(laboratorioId, id -> creador.apply(id))")]
story += [P("Por qué esto importa", "H2A")]
story += bullets([
    "Dos peticiones simultáneas del mismo laboratorio no deberían dejar dos pools permanentes.",
    "Laboratorios distintos mantienen pools separados y pueden inicializarse bajo demanda.",
    "No se crean conexiones para laboratorios que todavía no han recibido tráfico.",
    "invalidarPool remueve y cierra un pool específico.",
    "@PreDestroy cierra todos los pools al apagar la aplicación.",
])
story += [callout("Escala y límites", "Un pool por tenant multiplica conexiones potenciales. Con 100 tenants y máximo 5, el techo teórico es 500 conexiones. Debes dimensionar base, aplicación y políticas de inactividad; no asumir que el mapa es gratuito.", "warning")]
story += [P("computeIfAbsent evita una carrera básica, pero la creación debe ser rápida, determinista y no reentrante para esa clave. Los fallos deben propagarse sin almacenar un pool inválido.")]
story += [PageBreak()]

# 16. Factory
story += page_heading("Etapa 7", "FabricaPoolLaboratorio construye HikariCP")
story += [P("La fábrica recibe BaseDatosLaboratorio, valida host, puerto, nombre de base, usuario y clave lógica del secreto. Luego busca la contraseña en Environment y construye la URL JDBC de MySQL.")]
story += [table([
    ["Propiedad actual", "Valor", "Efecto"],
    ["maximumPoolSize", "5", "Hasta cinco conexiones totales por tenant"],
    ["minimumIdle", "1", "Mantiene al menos una conexión inactiva"],
    ["connectionTimeout", "10 s", "Espera máxima por una conexión"],
    ["validationTimeout", "5 s", "Límite para validar salud"],
    ["idleTimeout", "300 s", "Retiro de inactivas sobre minimumIdle"],
    ["maxLifetime", "1.800 s", "Renovación preventiva de conexiones"],
], [55 * mm, 30 * mm, 80 * mm])]
story += [P("El override LEVEY_TENANT_HOST_OVERRIDE permite reemplazar el host registrado, útil cuando el mismo catálogo se ejecuta desde Docker o directamente en macOS y el nombre host.docker.internal cambia de significado.")]
story += [callout("No registres contraseñas", "Loguea laboratorioId, nombre de pool y destino sanitizado. Nunca imprimas password, JWT, variables secretas ni URLs que las contengan.", "danger")]
story += [PageBreak()]

# 17. Transaction lifecycle
story += page_heading("Conexión y transacción", "Qué se presta y qué se devuelve")
story += [P("Un pool no asigna una única conexión permanente a cada laboratorio. Asigna un <b>pool independiente</b> por laboratorio; dentro de ese pool, cada operación toma temporalmente una conexión disponible.")]
story += [table([
    ["Momento", "Objeto", "Estado"],
    ["Primer acceso tenant", "HikariDataSource", "Se crea y queda registrado"],
    ["Inicio operación JPA", "Connection proxy", "Prestada desde el pool"],
    ["Durante transacción", "Connection", "Asociada al EntityManager/transacción"],
    ["Fin/rollback", "Connection.close()", "Devuelta al pool"],
    ["Invalidación/apagado", "HikariDataSource.close()", "Cierra conexiones físicas"],
], [43 * mm, 55 * mm, 67 * mm])]
story += [callout("Distinción", "releaseConnection cierra el préstamo; invalidarPool cierra el pool. Confundir ambos niveles puede provocar fugas o destruir el rendimiento de reutilización.", "info")]
story += [P("El laboratorioTransactionManager debe gobernar operaciones de repositorios tenant. Si una operación combina escrituras central y tenant, no existe atomicidad distribuida automática entre ambas bases. Debes diseñar consistencia, compensaciones o mensajería según el caso.")]
story += [PageBreak()]

# 18. Endpoint
story += page_heading("Ejemplo real", "GET /informacionlaboratorio")
story += [P("El frontend obtiene un token con getToken() y envía Authorization: Bearer. Si Spring Security lo acepta, el filtro fija el contexto; el controlador llama a InformacionLaboratorioService; el servicio usa InformacionLaboratorioRepository.findAll().")]
story += [code("fetch(API + \"/informacionlaboratorio\", {\n  headers: { Authorization: \"Bearer \" + token }\n})")]
story += [P("La llamada parece un CRUD normal porque el multitenancy está debajo del repositorio. Esa transparencia es útil, pero obliga a proteger todas las entradas: un repositorio tenant ejecutado fuera de una petición correctamente contextualizada debe fallar.")]
story += [callout("Detalle en el frontend mostrado", "No puedes leer primero res.text() y después res.json() sobre la misma respuesta; el body se consume una vez. La segunda versión que usa directamente res.json() cuando res.ok es correcta para el caso de éxito.", "info")]
story += [P("También conviene comprobar que NEXT_PUBLIC_API_URL esté definido y que el backend devuelva JSON coherente en éxitos y errores. Estas mejoras no cambian el mecanismo multi-tenant, pero facilitan el diagnóstico.")]
story += [PageBreak()]

# 19. Observed validation
story += page_heading("Estado comprobado", "Qué está listo y qué depende del entorno")
story += [P("Durante la revisión del proyecto, la compilación Maven sin descargar dependencias finalizó correctamente. Las pruebas de contexto Spring inicializaron las dos unidades de persistencia: siete repositorios centrales y un repositorio tenant.")]
story += [table([
    ["Comprobación", "Resultado", "Lectura"],
    ["Compilación", "Correcta", "Clases y configuración son compatibles"],
    ["Arranque contexto", "Correcto", "Dos EntityManagerFactory se crean"],
    ["Endpoint + JWT", "Reportado funcional", "Flujo real llega al tenant"],
    ["Prueba directa macOS", "Host no resuelto", "Diferencia red Docker/host"],
], [50 * mm, 38 * mm, 77 * mm])]
story += [callout("Diagnóstico macOS", "host.docker.internal está pensado para alcanzar el host desde un contenedor. Si la aplicación Java corre directamente en macOS, suele corresponder localhost o la dirección real. LEVEY_TENANT_HOST_OVERRIDE permite probar sin alterar el catálogo.", "warning")]
story += [P("No confundas un fallo de DNS o red con un fallo del resolver de Hibernate. Sigue la cadena: contexto -> configuración encontrada -> pool creado -> host resuelto -> autenticación MySQL -> consulta SQL.")]
story += [PageBreak()]

# 20. Current onboarding
story += page_heading("Alta de tenant hoy", "Qué debe existir para un laboratorio nuevo")
story += [P("Con la arquitectura actual, un nuevo laboratorio necesita datos de control en LeveyCentral, una base operativa creada y un secreto accesible por la aplicación. El pool no necesita declararse manualmente: se crea bajo demanda.")]
story += [table([
    ["Elemento", "Ubicación", "¿Requiere redespliegue?"],
    ["Laboratorio", "LeveyCentral", "No"],
    ["Relación usuario-lab", "LeveyCentral", "No"],
    ["Host/puerto/base/usuario", "BaseDatosLaboratorio", "No"],
    ["Esquema y datos iniciales", "Nueva BD tenant", "Depende del proceso"],
    ["Password actual", "Variable .env referenciada", "Sí, normalmente"],
], [54 * mm, 64 * mm, 47 * mm])]
story += [P("La columna secretoConexionKey guarda el <b>nombre lógico</b> de la variable, no la contraseña. Por ejemplo, una fila puede señalar DB_PASSWORD_LAB_XX y la aplicación busca ese nombre en Environment.")]
story += [callout("Respuesta a tu preocupación", "Sí: hoy el alta sigue acoplada al entorno porque cada contraseña nueva exige una variable disponible para el proceso. El catálogo ya es dinámico; el almacenamiento de secretos es la parte que falta desacoplar.", "warning")]
story += [PageBreak()]

# 21. Scale secrets
story += page_heading("Evolución escalable", "De .env por tenant a un catálogo seguro")
story += [P("Puedes dejar la configuración operativa en BaseDatosLaboratorio, pero no conviene guardar contraseñas en texto plano. Hay tres niveles de evolución:")]
story += [table([
    ["Opción", "Ventaja", "Riesgo / coste"],
    ["A. .env por tenant", "Simple y actual", "Reinicio, despliegue y crecimiento manual"],
    ["B. secreto cifrado en BD", "Alta dinámica", "Debes custodiar clave maestra y rotación"],
    ["C. gestor de secretos", "Auditoría, rotación, acceso dinámico", "Infraestructura y dependencia externa"],
], [42 * mm, 61 * mm, 62 * mm])]
story += [P("Diseño recomendado: BaseDatosLaboratorio almacena un secretRef estable. Un ServicioSecretos resuelve esa referencia desde Vault, AWS Secrets Manager, Google Secret Manager u otro almacén. La fábrica recibe la contraseña en memoria, configura Hikari y descarta la referencia local.")]
story += [callout("Separación de responsabilidades", "La base central conoce dónde está el secreto; el gestor de secretos conoce su valor; los logs no conocen ninguno de los dos valores sensibles.", "success")]
story += [P("Si eliges cifrado en BD, usa cifrado autenticado, clave maestra fuera de la BD, versionado de claves, rotación y control de acceso. Codificar en Base64 no es cifrar.")]
story += [PageBreak()]

# 22. Dynamic onboarding flow
story += page_heading("Flujo propuesto", "Alta sin editar .env")
story += [P("Esta página describe una evolución, no el estado actual. Implementa cada paso solo después de diseñar permisos, rollback y auditoría.")]
story += bullets([
    "1. Un administrador autorizado registra el laboratorio.",
    "2. Un proceso de aprovisionamiento crea la base y aplica migraciones versionadas.",
    "3. Se crea un usuario de base con privilegios mínimos para ese esquema.",
    "4. La contraseña se guarda en el gestor de secretos y devuelve secretRef.",
    "5. BaseDatosLaboratorio guarda host, puerto, databaseName, username y secretRef.",
    "6. Una prueba de conectividad abre y cierra una conexión sin exponer el secreto.",
    "7. Se marca activo/estadoConexion solo después de la prueba.",
    "8. El primer tráfico crea el pool bajo demanda.",
])
story += [callout("Actualización", "Cuando cambies host, usuario o secreto, invalida el pool del laboratorio. La siguiente petición lo recreará con la configuración nueva.", "info")]
story += [P("Piensa el alta como una transacción de negocio con estados: PENDIENTE, APROVISIONANDO, VALIDANDO, ACTIVO y ERROR. Evita que un tenant parcialmente configurado aparezca como disponible.")]
story += [PageBreak()]

# 23. Risks
story += page_heading("Checklist de producción", "Aislamiento, capacidad y operación")
story += [table([
    ["Área", "Pregunta de control"],
    ["Identidad", "¿laboratorioId proviene del backend y no del cliente?"],
    ["Contexto", "¿clear() corre en finally en todos los caminos?"],
    ["Repositorios", "¿paquetes central/tenant están separados?"],
    ["Conexiones", "¿cada pool tiene nombre, límites y timeouts?"],
    ["Secretos", "¿no aparecen en BD plana, código o logs?"],
    ["Migraciones", "¿todos los tenants conocen su versión de esquema?"],
    ["Observabilidad", "¿métricas etiquetan tenant sin datos sensibles?"],
    ["Capacidad", "¿suma de maximumPoolSize cabe en MySQL?"],
    ["Cambios", "¿actualizar config invalida el pool?"],
], [42 * mm, 123 * mm])]
story += [callout("Riesgo arquitectónico", "database-per-tenant mejora aislamiento, pero multiplica migraciones, monitoreo, backups, pools y recuperación. La automatización operativa es parte del diseño, no un detalle posterior.", "warning")]
story += [P("Antes de producción, añade pruebas negativas: usuario sin laboratorio, laboratorio inactivo, secretRef inexistente, credencial inválida, host caído, pool agotado y excepción que compruebe la limpieza del contexto.")]
story += [PageBreak()]

# 24 exercise 0
story += exercise(
    "0", "Preparar un proyecto laboratorio",
    "Construir un entorno descartable donde cada experimento sea observable y no afecte LeveyQC.",
    "Crea un proyecto Spring Boot nuevo con Java 17, Web, Data JPA, Security, OAuth2 Resource Server, MySQL y HikariCP. Usa Git desde el primer momento. No copies el proyecto original.",
    [
        "Levanta una base central y dos bases tenant con nombres evidentes: tenant_rojo y tenant_azul.",
        "Crea en ambas tenants una tabla mensaje con la misma estructura, pero contenido distinto.",
        "Configura únicamente la persistencia central y confirma que el contexto Spring arranca.",
        "Documenta puertos, usuarios y red en un README; nunca incluyas contraseñas reales.",
    ],
    [
        "Una consulta manual a cada base devuelve mensajes diferentes.",
        "El proyecto arranca aunque todavía no exista multitenancy.",
        "Puedes destruir y reconstruir el entorno sin pasos secretos.",
    ],
    "Tienes tres bases aisladas, un arranque verde y un diagrama propio de una página.",
    ["¿Qué información pertenece a central?", "¿Qué dato demuestra visualmente que no mezclaste tenants?"],
    "Empieza con SQL mínimo. El objetivo no es el dominio clínico, sino hacer visible el enrutamiento.",
)
story += [PageBreak()]

# 25 exercise 1
story += exercise(
    "1", "Modelar el catálogo central",
    "Aprender a convertir laboratorioId en una configuración de conexión sin abrir todavía conexiones dinámicas.",
    "En la base central crea tablas mínimas laboratorio, usuario_interno y base_datos_laboratorio. Usa campos de estado y una referencia de secreto ficticia.",
    [
        "Inserta dos laboratorios activos y uno inactivo.",
        "Asocia dos usuarios ficticios a laboratorios diferentes.",
        "Escribe consultas que, dado un subject externo, devuelvan exactamente un laboratorio activo.",
        "Escribe pruebas para id inexistente, inactivo y configuración perteneciente a otro laboratorio.",
    ],
    [
        "La consulta central nunca toca las tablas mensaje.",
        "Un laboratorio inactivo no produce una configuración utilizable.",
        "La referencia de secreto es un nombre, no una contraseña.",
    ],
    "Las pruebas de catálogo cubren éxito y tres fallos sin construir ningún HikariDataSource.",
    ["¿Dónde validarías que configuración.laboratorio_id coincide con el solicitado?", "¿Qué restricción UNIQUE evitaría ambigüedad?"],
    "Separa el repositorio que lee filas del servicio que decide si una configuración puede usarse.",
)
story += [PageBreak()]

# 26 exercise 2
story += exercise(
    "2", "Experimentar con ThreadLocal",
    "Comprobar por ti mismo por qué el contexto debe limpiarse al reutilizar hilos.",
    "Crea una clase TenantContext mínima con set, get y clear. Todavía no uses Spring ni Hibernate. Ejecuta tareas sobre un ExecutorService de un solo hilo.",
    [
        "Tarea A fija tenant 10 y termina sin clear; tarea B solo lee.",
        "Registra qué ve B y explica por qué.",
        "Repite con try/finally y clear.",
        "Agrega una excepción intencional antes de terminar A y verifica que finally limpia.",
    ],
    [
        "Sin clear, B puede heredar 10 porque usa el mismo hilo.",
        "Con clear, B obtiene null.",
        "La excepción no impide la limpieza cuando existe finally.",
    ],
    "Puedes mostrar una prueba roja sin clear y la misma prueba verde con clear.",
    ["¿ThreadLocal identifica usuarios o solo asocia un valor al hilo?", "¿Qué cambia si el executor tiene diez hilos?"],
    "Fuerza un pool de un solo hilo para que la reutilización sea determinista y didáctica.",
)
story += [PageBreak()]

# 27 exercise 3
story += exercise(
    "3", "Construir el registro de pools",
    "Entender lazy loading, concurrencia, invalidación y cierre sin involucrar Hibernate.",
    "Crea un registro basado en ConcurrentHashMap y una fábrica contadora que no abra todavía conexiones reales.",
    [
        "Solicita cinco veces el pool del tenant 1 y cuenta cuántas veces se invoca la fábrica.",
        "Solicita tenants 1 y 2 desde tareas concurrentes.",
        "Invalida tenant 1 y confirma que el recurso anterior se cierra.",
        "Solicita tenant 1 otra vez y confirma que nace una instancia nueva.",
    ],
    [
        "La fábrica corre una vez por tenant mientras la entrada siga vigente.",
        "La invalidación afecta solo a su clave.",
        "El cierre global recorre y cierra todos los recursos.",
    ],
    "Tus pruebas demuestran creación única, separación por clave e invalidación recreable.",
    ["¿Qué sucede si la fábrica lanza una excepción?", "¿Debe quedar una entrada inválida en el mapa?"],
    "Usa un recurso falso con boolean closed. Cuando la semántica esté verde, reemplázalo por HikariDataSource.",
)
story += [PageBreak()]

# 28 exercise 4
story += exercise(
    "4", "Crear pools Hikari reales",
    "Traducir una configuración validada a un pool pequeño y observar su ciclo de vida.",
    "Conecta la fábrica a tenant_rojo y tenant_azul. Limita maximumPoolSize a 2 para que los experimentos sean visibles.",
    [
        "Abre una conexión del pool rojo y consulta el mensaje distintivo.",
        "Cierra el préstamo y vuelve a abrir; observa métricas del pool.",
        "Ocupa dos conexiones y solicita una tercera con connectionTimeout corto.",
        "Cambia una credencial ficticia, invalida el pool y comprueba recreación o fallo controlado.",
    ],
    [
        "close() reduce conexiones activas sin destruir inmediatamente el pool.",
        "La tercera solicitud espera y termina por timeout cuando el pool está agotado.",
        "Un pool inválido no debe quedar disponible silenciosamente.",
    ],
    "Puedes distinguir préstamo, conexión física, pool e invalidación mediante logs sanitizados.",
    ["¿Cuántas conexiones máximas existirían con 50 tenants activos?", "¿Qué timeout ayuda a fallar rápido bajo saturación?"],
    "Consulta HikariPoolMXBean para observar active, idle, total y threadsAwaitingConnection.",
)
story += [PageBreak()]

# 29 exercise 5
story += exercise(
    "5", "Implementar resolver y provider",
    "Conectar el identificador contextual con el registro de pools mediante los contratos de Hibernate.",
    "Crea tus propias implementaciones de CurrentTenantIdentifierResolver<Long> y MultiTenantConnectionProvider<Long>. Usa TODOs; no copies las clases de LeveyQC.",
    [
        "Haz que el resolver falle si el contexto es null.",
        "Haz que getConnection(id) pida el pool al gestor y una conexión al pool.",
        "Haz que releaseConnection devuelva el préstamo.",
        "Diseña getAnyConnection para bootstrap y documenta por qué no es un tenant por defecto.",
    ],
    [
        "Sin contexto, la excepción aparece antes de una consulta SQL tenant.",
        "Con contexto rojo, la conexión proviene solo del pool rojo.",
        "Cambiar el contexto antes de crear una nueva sesión cambia el destino.",
    ],
    "Pruebas unitarias verifican id nulo, selección correcta y devolución de la conexión.",
    ["¿Por qué el provider no debería consultar directamente el JWT?", "¿Qué responsabilidad quedaría mezclada si construye URLs?"],
    "Prueba primero con dobles de GestorPool. Integra MySQL solo cuando el contrato esté claro.",
)
story += [PageBreak()]

# 30 exercise 6
story += exercise(
    "6", "Separar dos unidades JPA",
    "Reproducir el límite central/tenant y evitar el escaneo cruzado de repositorios.",
    "Organiza paquetes central y tenant. Crea una entidad de catálogo central y una entidad Mensaje tenant. Define dos EntityManagerFactory y dos TransactionManager.",
    [
        "Asocia explícitamente cada @EnableJpaRepositories con su fábrica y transacción.",
        "Inyecta las propiedades del resolver y provider solo en la unidad tenant.",
        "Ejecuta repositorio central sin contexto y confirma que funciona.",
        "Ejecuta repositorio tenant sin contexto y confirma que falla; luego con rojo y azul.",
    ],
    [
        "Central no depende de TenantContext.",
        "Tenant devuelve contenidos distintos con el mismo método findAll().",
        "Mover intencionalmente un repositorio al paquete incorrecto reproduce un fallo explicable.",
    ],
    "Tres pruebas verdes: central sin tenant, rojo aislado y azul aislado; una prueba negativa sin contexto.",
    ["¿Qué bean debería ser @Primary?", "¿Qué transactionManager necesita un servicio tenant?"],
    "Dibuja los paquetes antes de crear clases. El escaneo es parte del diseño, no una decoración.",
)
story += [PageBreak()]

# 31 exercise 7
story += exercise(
    "7", "Contextualizar una petición HTTP",
    "Completar el recorrido desde identidad autenticada hasta repositorio y demostrar la limpieza al finalizar.",
    "Usa tokens de prueba o un JwtDecoder controlado. La identidad externa debe resolverse contra tu tabla central antes de fijar el tenant.",
    [
        "Implementa un filtro por petición que limpie al inicio y en finally.",
        "Prueba usuario rojo, usuario azul, usuario inexistente y laboratorio inactivo.",
        "Crea un endpoint que lance una excepción después de acceder al tenant.",
        "En la siguiente petición del mismo hilo, confirma que no quedó el id anterior.",
    ],
    [
        "El cliente nunca decide el tenant mediante un encabezado libre.",
        "Cada token aceptado devuelve exclusivamente el mensaje de su laboratorio.",
        "Un fallo HTTP no contamina la petición siguiente.",
    ],
    "Las pruebas de integración cubren dos éxitos y al menos tres fallos de aislamiento.",
    ["¿Qué cambia entre 401 y 403?", "¿Dónde ubicarías un correlationId para seguir la petición sin registrar el token?"],
    "Para probar fuga, configura el servidor de pruebas con pocos hilos o invoca directamente el filtro en el mismo hilo.",
)
story += [PageBreak()]

# 32 experiments matrix
story += page_heading("Matriz de experimentos", "Romper el sistema de forma controlada")
story += [table([
    ["Experimento", "Predicción", "Evidencia a capturar"],
    ["Contexto null", "Hibernate rechaza la operación", "Tipo y punto de excepción"],
    ["Tenant inactivo", "Gestor no crea pool", "Contador de fábrica = 0"],
    ["secretRef ausente", "Fábrica falla antes de JDBC", "Mensaje sanitizado"],
    ["Password inválida", "Hikari no autentica", "SQLState sin contraseña"],
    ["Host inválido", "Fallo DNS/conexión", "Destino y tiempo"],
    ["Pool agotado", "Timeout controlado", "threadsAwaitingConnection"],
    ["Config actualizada", "Pool viejo persiste", "Necesidad de invalidar"],
    ["Excepción HTTP", "finally limpia", "Contexto null después"],
    ["50 tenants", "crecen pools/conexiones", "métricas totales"],
], [45 * mm, 58 * mm, 62 * mm], font_size=7.2)]
story += [P("Método científico", "H2A")]
story += bullets([
    "Antes de ejecutar, escribe una predicción falsable.",
    "Cambia una sola variable por experimento.",
    "Captura métricas, logs sanitizados y resultado SQL.",
    "Explica el resultado usando el recorrido de siete etapas.",
    "Restaura el entorno y repite para comprobar que no fue casualidad.",
])
story += [callout("Meta pedagógica", "Si solo haces funcionar el caso feliz, conoces la receta. Cuando puedes predecir dónde y cómo falla cada capa, entiendes la arquitectura.", "success")]
story += [PageBreak()]

# 33 debugging
story += page_heading("Playbook de depuración", "Localizar el fallo por capas")
story += [table([
    ["Síntoma", "Primera comprobación", "Capa probable"],
    ["401", "firma, issuer, expiración", "Autenticación JWT"],
    ["403", "autoridades y actor", "Autorización"],
    ["No tenant identifier", "LaboratorioContext", "Filtro/resolver"],
    ["Config no encontrada", "fila central y estados", "Catálogo/gestor"],
    ["UnknownHost", "host desde runtime actual", "DNS/red"],
    ["Access denied MySQL", "usuario/secretRef", "Credenciales"],
    ["Connection timeout", "active/idle/awaiting", "Capacidad pool"],
    ["Table not found", "migración/esquema", "Provisionamiento"],
    ["Datos de otro lab", "origen id + limpieza", "Aislamiento crítico"],
], [50 * mm, 66 * mm, 49 * mm])]
story += [P("Orden de investigación: token aceptado -> actor encontrado -> laboratorioId fijado -> resolver devuelve id -> gestor encuentra configuración -> fábrica resuelve secreto -> red conecta -> SQL usa esquema esperado -> contexto se limpia.")]
story += [callout("Ante mezcla de datos", "Detén la prueba y trata el incidente como seguridad. Captura correlationId, usuario interno, laboratorio esperado, pool seleccionado y transacción, sin registrar secretos ni datos clínicos.", "danger")]
story += [PageBreak()]

# 34 capstone
story += page_heading("Proyecto final", "Aprovisionamiento dinámico de tres tenants")
story += [P("Construye una demostración completa en la que un administrador registra un tercer tenant sin reiniciar la aplicación. Hazlo por etapas y conserva un historial de decisiones.")]
story += [P("Requisitos", "H2A")]
story += bullets([
    "Dos persistencias JPA separadas y paquetes no superpuestos.",
    "Tenant derivado de identidad interna, nunca de un header libre.",
    "Pool Hikari bajo demanda, invalidable y observable.",
    "Referencia de secreto desacoplada del .env por tenant.",
    "Migración versionada aplicada antes de activar el tenant.",
    "Pruebas de aislamiento paralelas para tres usuarios.",
    "Prueba de rotación de secreto que invalida y recrea el pool.",
    "Documento de recuperación ante base tenant no disponible.",
])
story += [callout("Defensa oral", "Explícale a otra persona el sistema sin abrir el código. Si puedes dibujar el flujo, justificar cada límite y anticipar cinco fallos, la comprensión ya es transferible.", "success")]
story += [P("Entrega sugerida: diagrama, README reproducible, colección HTTP, pruebas automatizadas, tabla de riesgos y una página de retrospectiva con decisiones que cambiarías.")]
story += [PageBreak()]

# 35 glossary and code map
story += page_heading("Mapa de referencia", "Clases actuales y responsabilidad única")
story += [table([
    ["Clase", "Responsabilidad"],
    ["PersistenciaCentralConfiguracion", "EntityManagerFactory y repos centrales"],
    ["PersistenciaLaboratorioConfiguracion", "Unidad JPA tenant y propiedades Hibernate"],
    ["FiltroUsuariosSistema", "Actor, autoridad, contexto y limpieza"],
    ["LaboratorioContext", "ThreadLocal del laboratorio actual"],
    ["LaboratorioTenantIdentifierResolver", "Expone tenantId a Hibernate"],
    ["LaboratorioMultiTenantConnectionProvider", "Entrega Connection por tenant"],
    ["GestorPoolLaboratorio", "Valida y coordina lookup/creación"],
    ["RegistroPoolsLaboratorio", "Caché concurrente e invalidación"],
    ["FabricaPoolLaboratorio", "Construye y configura HikariDataSource"],
    ["BaseDatosLaboratorio", "Metadatos de conexión en central"],
], [72 * mm, 93 * mm], font_size=7.1)]
story += [callout("Deuda técnica visible", "El paquete InfomracionLaboratorio contiene un error tipográfico. No rompe por sí mismo si todo referencia el mismo paquete, pero dificulta búsquedas, convenciones y mantenimiento; conviene corregirlo mediante refactor seguro.", "warning")]
story += [P("Glosario: <b>tenant</b>, cliente lógico aislado; <b>DataSource</b>, fábrica de conexiones; <b>pool</b>, conjunto reutilizable; <b>EntityManagerFactory</b>, creador de contextos JPA; <b>bootstrap</b>, inicialización; <b>secretRef</b>, referencia a un secreto externo; <b>lazy</b>, creación al primer uso.")]
story += [PageBreak()]

# 36 references
story += page_heading("Fuentes y siguientes pasos", "Referencias primarias")
story += [P("Código del proyecto revisado", "H2A")]
story += bullets([
    "src/main/java/cl/leveyqc/leveyqc/Configuracion/PersistenciaCentralConfiguracion.java",
    "src/main/java/cl/leveyqc/leveyqc/Configuracion/PersistenciaLaboratorioConfiguracion.java",
    "src/main/java/cl/leveyqc/leveyqc/BaseDatosLaboratorio/hibernate/",
    "src/main/java/cl/leveyqc/leveyqc/BaseDatosLaboratorio/pool/",
    "src/main/java/cl/leveyqc/leveyqc/BaseDatosLaboratorio/contexto/LaboratorioContext.java",
    "src/main/java/cl/leveyqc/leveyqc/Configuracion/FiltroUsuariosSistema.java",
    "src/test/java/.../InformacionLaboratorioRepositoryTest.java",
])
story += [P("Documentación oficial consultada", "H2A")]
story += bullets([
    "Hibernate ORM User Guide: <link href='https://docs.hibernate.org/orm/current/userguide/html_single/' color='#175CD3'>docs.hibernate.org/orm/current/userguide</link>",
    "Hibernate Introduction: <link href='https://docs.hibernate.org/stable/orm/introduction/html_single/' color='#175CD3'>docs.hibernate.org/stable/orm/introduction</link>",
    "Spring Boot EntityManagerFactoryBuilder: <link href='https://docs.spring.io/spring-boot/api/java/org/springframework/boot/jpa/EntityManagerFactoryBuilder.html' color='#175CD3'>docs.spring.io/spring-boot/api</link>",
    "HikariCP README: <link href='https://github.com/brettwooldridge/HikariCP' color='#175CD3'>github.com/brettwooldridge/HikariCP</link>",
    "Java ThreadLocal: <link href='https://docs.oracle.com/en/java/javase/25/core/java-core-libraries-developer-guide.pdf' color='#175CD3'>Oracle Java Core Libraries Guide</link>",
    "OWASP Secrets Management Cheat Sheet: <link href='https://cheatsheetseries.owasp.org/cheatsheets/Secrets_Management_Cheat_Sheet.html' color='#175CD3'>cheatsheetseries.owasp.org</link>",
    "HashiCorp Vault database secrets: <link href='https://developer.hashicorp.com/vault/docs/secrets/databases' color='#175CD3'>developer.hashicorp.com/vault</link>",
])
story += [callout("Cierre", "La arquitectura base ya resuelve la selección dinámica y el pool por laboratorio. El salto de escalabilidad pendiente es automatizar aprovisionamiento, migraciones y secretos para que el alta no dependa de editar el entorno y redesplegar.", "success")]
story += [P("Versión documental: 1.0 · septiembre de 2026. Este material evita exponer valores sensibles y distingue explícitamente arquitectura observada de propuestas de evolución.", "SmallA")]


doc.build(story)
print(OUTPUT)
