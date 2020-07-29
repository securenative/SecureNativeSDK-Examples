from flask import Flask


app = Flask(__name__)
app.config['DEBUG'] = True

# Import views here
import views
