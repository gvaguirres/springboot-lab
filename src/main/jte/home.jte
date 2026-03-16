<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>MoviesBank26 - Welcome</title>
    <style>
        body, input {font-family: 'Courier New', Courier, monospace;}
    </style>
</head>
<body>
<div style="text-align: center; margin-top: 50px;">
    <h1>Welcome to PuduMovies</h1>
    <p>Your favorite pududatabase of movies</p>

    <div style="margin-top: 30px;">
        <form action="/movies/list" method="GET">

            <input type="text"
                   name="query"
                   placeholder="Search by title, director or year"
                   style="width: 400px; padding: 10px; font-size: 16px;">
            <button type="submit">🔍</button>
        </form>
    </div>

    <div style="margin-top: 20px;">
        <a href="/movies/list">Check all the movies</a>
    </div>

    <div style="margin-top: 400px;">
    <p>I know you are wondering what a pudu is 😛</p>
    </div>

    <div style="text-align: center; margin-top: 10px;">
        <p style="cursor: pointer; color: blue; text-decoration: underline;"
           onclick="document.getElementById('pudu').style.display='block'">
            Click here and find out 💕
        </p>

        <div id="pudu" style="display: none; margin-top: 20px;">
            <img src="/pudu.jpg"
                 alt="Pudú tierno"
                 style="width: 300px; border-radius: 15px; border: 5px solid white; box-shadow: 0 4px 10px rgba(0,0,0,0.3);">
        </div>
    </div>
</div>
</body>
</html>
