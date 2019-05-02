# bs-md-custom-stepper
Material Design Custom Stepper

Se puede usar un RecyclerView para poder emular un stepper en android
CustomStepperView ->  Clase contenedora de todos los pasos del stepper. Para poder se reutilizable las variables de colores, iconos y atributos
deben poder editables al ser reutilizado.
CustomStepperItemView -> Clase contenedora del contenido de un paso.
ClipOvalFrameLayout -> Clase para mostrar dinamicamente el contador del paso actual.
CustomItemDecoration -> Agregar una separacion para emular la division de los pasos.
IStepperAdapter -> Interfaz a implementar para reutilizar los paso

Layout: custom_stepper_item_view_layout

PD: Me base en la implementacion de https://github.com/fython/MaterialStepperView