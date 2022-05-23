# App de Peliculas/series
#### entrevista tecnica

## Developer: Ulises Martínez Elías

Se usó la API indicada: https://developers.themoviedb.org/



Bibliotecas
- Volley para la conexion a la API
- [android-youtube-player](https://github.com/PierfrancescoSoffritti/Android-YouTube-Player) para reproducir los trailers de cada película

Estructura:

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