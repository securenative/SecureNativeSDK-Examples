import os

from demo import app

app.config['DEBUG'] = os.environ.get('FLASK_DEBUG', True)
host = os.environ.get('FLASK_HOST', '0.0.0.0')
port = os.environ.get('FLASK_PORT', 5000)


if __name__ == "__main__":
    app.run(host=host, port=port)
