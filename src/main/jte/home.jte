@param String errorMessage = null

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>MoviesBank26 - Welcome</title>
    <style>
        body, input, select, button {font-family: 'Courier New', Courier, monospace;}
        .search-box { margin-top: 30px; }
        .errorMessage {
            color: #ff0000;
            font-weight: bold;
            background-color: #fff0f0;
            padding: 10px;
            display: inline-block;
            border-radius: 5px;
            border: 1px solid #ffcccc;
            margin-top: 15px;
        }
    </style>
</head>
<body>
<div style="text-align: center; margin-top: 50px;">
    <h1>Welcome to PuduMovies</h1>
    <p>Your favorite pududatabase of movies</p>

    <div class="search-box">
        <form action="/movies/search" method="GET">

            <select name="searchType" style="padding: 10px; font-size: 16px; cursor: pointer;">
                <option value="title">Title</option>
                <option value="director">Director</option>
                <option value="year">Year</option>
            </select>

            <input type="text"
                   name="query"
                   placeholder="Search here..."
                   style="width: 300px; padding: 10px; font-size: 16px;"
                   required>

            <button type="submit" style="padding: 10px; cursor: pointer;">🔍</button>
        </form>

        @if(errorMessage != null)
            <div class="errorMessage">
                ⚠ ${errorMessage}
            </div>
        @endif
    </div>

    <div style="margin-top: 20px;">
        <a href="/movies/list">Check all the movies</a>
    </div>

    <div style="margin-top: 350px;">
        <p>I know you are wondering what a pudu is 😛</p>
    </div>

    <div style="text-align: center; margin-top: 10px;">
        <p style="cursor: pointer; color: blue; text-decoration: underline;"
           onclick="document.getElementById('pudu').style.display='block'">
            Click here and find out 💕
        </p>

        <div id="pudu" style="display: none; margin-top: 20px; padding-bottom: 50px;">
            <img src="/pudu.jpg"
                 alt="Pudú tierno"
                 style="width: 300px; border-radius: 15px; border: 5px solid white; box-shadow: 0 4px 10px rgba(0,0,0,0.3);">
        </div>
    </div>
</div>
</body>
</html>
