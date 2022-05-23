# App de Peliculas/series
#### entrevista tecnica

## Developer: Ulises Martínez Elías
### ulises.mtz.el@gmail.com

Se usó la API indicada: https://developers.themoviedb.org/

## Cosas que faltaron

- Revisar la lista de Populares y Playing now, me parece que ambas son la misma. Cambiaría por una categoria diferente
- El idioma quedo en inglés. Revisaría la información que provee la API, pues el formato es diferente
- El RecyclerView de los videos en MovieDetailAcitvity no tiene el mejor comportamiento, ya que no ajusta el video al layout como lo haría un ViewPager. Yo optaría por ajustar el adapter para que se comporte como el viewPager, pero manteniendo el recyclerview para un mejor rendimiento.

## Bibliotecas
- Volley para la conexion a la API
- Glide para manejar los imageView
- [android-youtube-player](https://github.com/PierfrancescoSoffritti/Android-YouTube-Player) para reproducir los trailers de cada película

## Estructura

    appdepeliculas/
        +-- data/
        |     `-- network/
        |           `-- Repository.kt
        |
        +-- model /
        |     +-- Movie.kt
        |     +-- MovieDetail.kt
        |     `-- VideoData.kt
        |
        +-- view/
        |     +-- adapters/
        |     |      +-- MainAdapter.kt
        |     |      +-- MovieVideoAdapter.kt
        |     |      `-- TextViewAdapter.kt
        |     |
        |     +-- MainActivity.kt
        |     `-- MovieDetailActivity.kt
        |
        `-- viewmodel/
              +-- MainViewModel.kt
              `-- MovieDetailViewModel.kt
