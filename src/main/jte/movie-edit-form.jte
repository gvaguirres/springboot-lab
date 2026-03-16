@param org.example.springbootlab.dto.MovieDTO movie

<style>
    body, input, button {font-family: 'Courier New', Courier, monospace;}
</style>
<style>
    .input { width: 400px; padding: 8px; margin-bottom: 10px; display: block; }
    textarea { width: 400px; height: 100px; padding: 8px; display: block; margin-bottom: 10px; resize: none; }
</style>

<div style="display: flex; flex-direction: column; align-items: center; justify-content: center; margin-top: 50px;">
    <h2>Edit the PuduMovie</h2>


    <form action="/movies/update/${movie.getId()}" method="POST" style="display: flex; flex-direction: column; align-items: center;">
        <input type="hidden" name="id" value="${movie.getId()}">

        <input type="text" name="title" class="input" placeholder="Title" value="${movie.getTitle()}">
        <input type="text" name="director" class="input" placeholder="Director" value="${movie.getDirector()}">
        <input type="number" name="year" class="input" placeholder="Year" value="${movie.getYear()}">
        <input type="number" name="durationMinutes" class="input" placeholder="Duration" value="${movie.getDurationMinutes()}">
        <input type="text" name="description" class="input" placeholder="Description" value="${movie.getDescription()}">

        <button type="submit" style="margin-top: 20px; padding: 10px 10px;">Update Movie</button>
    </form>
    <div style="margin-top: 1px;">
        <a href="/movies/list">Back</a>
    </div>
</div>
