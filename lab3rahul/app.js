var express = require('express'),
    bodyParser = require('body-parser'),
    cookieParser = require('cookie-parser'),
    session = require('express-session'),
    Survey = require('./controllers/Survey'),
    Index = require('./controllers/Index'),
    app = express();
    // Match = require('./controllers/Match');

// express middleware
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(cookieParser());
app.use(session({
    secret: 'GeJExxKWSM',    
    resave: true,
    saveUninitialized: true
}));
app.set('views', './views');
app.set('view engine', 'ejs');


// routes
app.get('/', function(req, res) {
    Index.getIndex(req ,res);
});

app.post('/matches', function(req, res) {

    if(req.body.action == "survey"){
        Index.setLogin(req, res);
    }else{
        Survey.setMatches(req, res);
    }
});

app.get('/expired', function(req, res) {
    Index.getExpired(req ,res);
});


app.get('/survey/:id', function(req, res) {
    Survey.getQuestions(req.params.id, req, res);
});
app.post('/survey', function(req, res) {
    Survey.setAnswers(req, res);
});

app.get('/preferences', function(req, res) {
    Survey.getPreferences(req, res);
});
app.post('/preferences', function(req, res) {
    Survey.setPreferences(req, res);
});


app.listen(8008);