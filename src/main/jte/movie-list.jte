@param java.util.List<org.example.springbootlab.dto.MovieDTO> movies
@param String message


@if(message != null)
    <div style="color: black; text-align: center; margin: 20px; font-family: 'Courier New', Courier, monospace;">
        <strong>${message}</strong>
    </div>
@endif

<!DOCTYPE html>
<html>
<head>
    <title>List of PuduMovies</title>
    <style>
        body {font-family: 'Courier New', Courier, monospace;}
    </style>
    <style>
        table {width: 100%; border-collapse: collapse; margin-top: 20px;}
        th, td {padding: 15px 20px; text-align: left; border-bottom: 1px solid #ddd; vertical-align: top;}
        th {background-color: #f2f2f2;}
        .actions-column {
            white-space: nowrap;
        }
    </style>
</head>
<body>
<h1>Puducatalog</h1>

<table>
    <thead>
    <tr>
        <th>Title</th>
        <th>Director</th>
        <th>Year</th>
        <th>Duration (min)</th>
        <th>Description</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    @for(var movie : movies)
        <tr style="border-bottom: 1px solid #ddd;">
            <td>${movie.getTitle()}</td>
            <td>${movie.getDirector()}</td>
            <td>${movie.getYear()}</td>
            <td>${movie.getDurationMinutes()}</td>
            <td>${movie.getDescription()}</td>

            <td class="actions-column">
                <a href="/movies/update/${movie.getId()}"
                    style="color: blue; text-decoration: none; font-size: 14px;">
                    Update </a>
                <span style="margin: 0 5px;">|</span>

                <form action="/movies/delete/${movie.getId()}" method="POST"
                      style="display: inline;"
                      onsubmit="return confirm('Are you sure you want to delete ${movie.getTitle()} ?');">
                    <button type="submit" style="color: red; background: none; border: none; cursor: pointer;
                    font-family: 'Courier New', Courier, monospace;">Delete</button>
                </form>
            </td>
        </tr>
    @endfor
    </tbody>
</table>

<div style="margin-top: 50px; display: flex; flex-direction: column; align-items: center; gap: 10px;">

    <a href="/movies/new"
       style="padding: 10px 20px; background-color: #007bff; color: white;
              text-decoration: none; font-weight: bold; border-radius: 5px;">
        Add New PuduMovie
    </a>

    <a href="/movies">Back</a>
</div>

</body>
</html>
