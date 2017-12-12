// some data (essentially JSON)
var data = {
  movies: [
    {
      movieId: "001",
      movieTitle: "Ender's Game",
      movieDirector: "Gavin Hood",
      movieImage: "https://s3-us-west-2.amazonaws.com/s.cdpn.io/3/movie-endersgame.jpg"
    }, {
      movieId: "002",
      movieTitle: "The Fifth Estate",
      movieDirector: "Bill Condon",
      movieImage: "https://s3-us-west-2.amazonaws.com/s.cdpn.io/3/movie-thefifthestate.jpg"
    }, {
      movieId: "003",
      movieTitle: "Escape Plan",
      movieDirector: "Mikael Håfström",
      movieImage: "https://s3-us-west-2.amazonaws.com/s.cdpn.io/3/movie-escapeplan.jpg"
    }
  ]
};

for (var i = 0; i < data.movies.length; i++) {
  
  // create a new <div> to hold our movie list
  $("<div />", {
    // apply class
    class: "module module-movie",
    // business logic
    click: function(){
      alert("added to movie list");
    },
    // apply CSS
    css: { 
      "border": "1px solid black",
      "background-image": "url("+data.movies[i].movieImage+")"
    },
    // the HTML template
    html: 
    
      "<div class='movie-info'>" +
        "<h3 class='movie-title'>" +
          data.movies[i].movieTitle +
        "</h3>" +
        "<p>" +
          data.movies[i].movieDirector +
        "</p>" +
      "</div>"
    
  }).appendTo("#movies");  // append to #movies section
}