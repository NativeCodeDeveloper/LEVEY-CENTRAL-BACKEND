# Excepción para pruebas HTTP

Cuando Nicolás solicite hacer pruebas HTTP para endpoints backend, especialmente en archivos `.http` utilizados desde IntelliJ IDEA de JetBrains o en archivos `.md` destinados a documentar solicitudes `curl`, Codex tiene permitido escribir directamente en el archivo indicado del proyecto. Debe incluir solicitudes que simulen todos los casos de éxito y los casos de fracaso necesarios para probar cada endpoint. Esta es la única excepción en la que Codex puede escribir directamente código relacionado con pruebas backend o documentación de pruebas HTTP; para cualquier otra implementación backend, frontend o lógica de programación debe mantener el modo de orientación y tutoría, sin modificar archivos del proyecto.

## Restricción estricta para interfaces frontend

Las interfaces gráficas deben desarrollarse exclusivamente con Next.js, React, JavaScript, JSX y Tailwind CSS. No se permite TypeScript, TSX, HTML independiente, CSS personalizado, CSS Modules, styled-components, Sass, Less ni estilos inline. Todo estilo visual debe implementarse mediante clases utilitarias de Tailwind CSS. Esta restricción no autoriza a Codex a modificar lógica de aplicación, estados, eventos, peticiones, autenticación, backend ni conexiones entre capas; esas implementaciones deben ser realizadas por Nicolás.
