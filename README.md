# 4 - Implementación de una lista posicional enlazada (LinkedPositionalList)

Implementar una **lista posicional** (`PositionalList<E>`) usando una **lista doblemente enlazada con nodos centinela** (*header* y *trailer*). Una lista posicional es una estructura de datos genérica que permite acceder e insertar elementos relativos a una **posición** (`Position<E>`) en lugar de a un índice numérico, lo que hace que las operaciones de inserción/eliminación en cualquier punto de la lista sean de coste O(1) una vez se tiene la posición.

En `src/main/java` tienes las interfaces `Position<E>` y `PositionalList<E>` (no hay que tocarlas) y el esqueleto de `LinkedPositionalList<E>`, que ya implementa varios métodos a modo de referencia (`size()`, `isEmpty()`, `first()`, `before()`, `addFirst()`, `set()`, `remove()`, `toString()`...). El objetivo del ejercicio es completar los métodos que faltan siguiendo el mismo estilo y los mismos invariantes que ya usan los métodos implementados.

No modifiques las interfaces (`Position`, `PositionalList`) ni la firma de los métodos de `LinkedPositionalList`. Tampoco uses `java.util.LinkedList` ni ninguna otra estructura de `java.util` como almacenamiento interno: la idea es que construyas tú mismo los nodos enlazados.

## Estructura del repositorio

```
src/
  main/java/es.urjc.grafo.EDA/
    Position.java             <- interfaz de una posición (referencia, no tocar)
    PositionalList.java       <- interfaz de la lista posicional (referencia, no tocar)
    LinkedPositionalList.java <- ejercicio: completar
  test/java/
    TestLinkedPositionalList.java
pom.xml
```

Cada método pendiente contiene, como marcador, esta línea:

```java
throw new UnsupportedOperationException("Not supported yet.");
```

Sustitúyela por la implementación real, siguiendo el javadoc del método. Al terminar no debe quedar ningún `UnsupportedOperationException` en `LinkedPositionalList`.

## Los nodos centinela (`header` / `trailer`)

La lista nunca está realmente "vacía" a nivel de nodos: siempre existen un nodo `header` y un nodo `trailer`, y los elementos que añade el usuario se insertan entre ambos. Así, `addFirst`, `addLast`, `addBefore` y `addAfter` pueden compartir un único método privado, `addBetween(e, predecessor, successor)`, sin tener que distinguir casos especiales para "la lista está vacía" o "insertar al principio/al final".

Dos métodos privados de ayuda ya resueltos merecen que los entiendas bien antes de tocar el resto:

- `validate(Position<E> p)`: comprueba que la posición recibida es realmente un `Node` de *esta* lista y que sigue formando parte de ella, y te devuelve el `Node` ya convertido, listo para manipular sus enlaces.
- `position(Node<E> node)`: hace el camino inverso — traduce un `Node` interno a la `Position<E>` que se expone hacia fuera, devolviendo `null` cuando el nodo es uno de los centinelas (porque `header` y `trailer` no son posiciones válidas para el usuario de la lista).

Todos los métodos que tienes que completar deberían apoyarse en estos dos métodos en vez de acceder a los nodos "a pelo".

## Qué falta por implementar

A grandes rasgos, los huecos del ejercicio son:

- **Navegación:** `after(p)` — el análogo simétrico de `before(p)`, que ya está resuelto.
- **Inserción interna:** `addBetween(e, predecessor, successor)` — el método privado del que dependen `addFirst`, `addLast`, `addBefore` y `addAfter`. Fíjate en que `addFirst` y `addBefore` ya están implementados apoyándose en él: eso te dice exactamente qué contrato tiene que cumplir.
- **Inserción pública:** `addLast(e)` y `addAfter(p, e)`, construidos sobre `addBetween`, igual que ya lo están `addFirst` y `addBefore`.
- **El nodo (`Node<E>`):** su constructor, que debe dejar el nodo enlazado con el elemento y los nodos anterior/siguiente que recibe.
- **El iterador de elementos (`ElementIterator`):** `hasNext()` y `next()`. Ojo: no hace falta que recorras la lista de nodos a mano — la clase ya cuenta con `PositionIterator`, que sabe moverse por las posiciones de la lista, y `ElementIterator` solo necesita apoyarse en él para devolver el *elemento* de cada posición en lugar de la posición en sí.


## Comprobando tu solución

Los tests de `src/test/java/TestLinkedPositionalList.java` cubren la lista vacía, inserciones (`addFirst`, `addLast`, `addBefore`, `addAfter`), navegación (`before`/`after`), `set`, `remove` y los dos mecanismos de iteración (`positionIterator()` y el `for-each` sobre elementos/posiciones). Ejecuta `mvn test` para comprobar tu implementación.

### Información del Proyecto

| Descripción   | Detalles                           |
|---------------|------------------------------------|
| Profesores    | Sergio Cavero, Javier Yuste y María Teresa González de Lena   |
| Asignatura    | Estructuras de Datos Avanzadas     |
| Universidad   | Universidad Rey Juan Carlos        |
| Licencia      | CC BY-NC-SA 4.0                    
