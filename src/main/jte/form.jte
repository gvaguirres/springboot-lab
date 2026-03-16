@param org.example.springbootlab.form.MovieForm movieForm

<!DOCTYPE html>
<html>
<head>
    <style>
        body, input, textarea, button { font-family: 'Courier New', Courier, monospace; }
        .input { width: 400px; padding: 8px; margin-bottom: 10px; display: block; }
        textarea { width: 400px; height: 100px; padding: 8px; display: block; margin-bottom: 10px; resize: none; }
    </style>
</head>
<body>

<div style="display: flex; flex-direction: column; align-items: center; justify-content: center; margin-top: 50px;">
    <h2>Add new PuduMovie</h2>

    <form action="/movies/create" method="POST" style="display: flex; flex-direction: column; align-items: center;">

        <input type="text" name="title" class="input" placeholder="Title" value="${movieForm.title()}">
        <input type="text" name="director" class="input" placeholder="Director" value="${movieForm.director()}">
        <input type="number" name="year" class="input" placeholder="Year" value="${movieForm.year()}">
        <input type="number" name="durationMinutes" class="input" placeholder="Duration (min)" value="${movieForm.durationMinutes()}">
        <textarea name="description" class="input" placeholder="Description">${movieForm.description()}</textarea>

        <button type="submit" style="margin-top: 10px; padding: 10px 10px;">Save Movie</button>
    </form>

    <div style="margin-top: 20px;">
        <a href="/movies/list">Back</a>
    </div>
</div>

</body>
</html>
