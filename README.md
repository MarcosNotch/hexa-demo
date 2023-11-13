# Repositorio de ejemplo
### Arquitectura hexagonal, event sourcing, CQS, DDD

Proyecto realizado con java 11 utilizando Spring Framework, 
utilizando una arquitectura hexagonal siguiendo algunos principios de DDD,
CQS y event sourcing.


La estructura del proyecto esta divida en paquetes intentando diferenciar los "bounded-context" 
y adentro de cada uno de estos paquetes se encuentran las diferentes capas.
ej:
> 1. producto
>    - application
>    - domain
>    - infra
>    - presentation
> 2. stock
>    - application
>    - domain
>    - infra
>    - presentation
> 3. shared
>    - application
>    - domain
>    - infra
>    - presentation
---
#### capa application
En esta capa se encuentran los casos de uso. 
Se utilizan CommandHandlers y QueryHandlers utilizando el patrón CQS, sirviendo de orquestadores.
Se intenta que tanto esta capa como la capa domain sean lo mas agnósticas 
posible al lenguaje y framework utilizado.
Es posible encontrar lógica de negocio que no sea facilmente atribuible a un Dominio.

#### capa domain
En esta capa se encuentran los objetos y clases que representan al dominio
y es donde generalmente se aplica la lógica de negocio.
Se pueden encontrar diferentes objetos como eventos, value objects, puertos (ports and adapters pattern),
commands, querys, etc.
Se intenta evitar el modelo anémico

#### capa infra
En esta capa se encuentran las implementaciones de las interfaces que corresponde a las demas capas
y otro tipo de funcionalidades. NO APLICA LOGICA DE NEGOCIO
Podemos encontrar adaptadores de conexiones a bases de datos, mensajeria, mapeo de entidades, 
comunicaciones a otros servicios, etc.
Esta capa esta acoplada al framework para poder dotar de funcionalidad y aprovechar las ventajas
para poder desacoplar las capas domain y application.

#### capa presentation
En esta capa se encuentran los controladores expuestos a los clientes, 
los listeners para consumir eventos externos y schedulers.
Puede formar parte de la capa infra pero se decide separar para poder diferenciar 
los "input ports" de los "output ports".
---
###### Se recomienda revisar todo el codigo para una mejor comprension del proyecto
_Seguir leyendo:_
- [Implementaciones de infra](./docs/implementacion-infra.md)
- [Eventos de integracion](./docs/eventos-integracion.md)
- [Transacciones distribuidas](./docs/transacciones-distribuidas.md)

