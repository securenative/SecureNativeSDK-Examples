import json

from flask import request
from securenative.enums.event_types import EventTypes
from securenative.models.user_traits import UserTraits

from demo import app
from demo.securenative_client import SNClient

sn_client = SNClient()


@app.route('/')
def index():
    return "SecureNative Python Agent integration website"


@app.route('/login')
def login():
    app.logger.debug("Performing Login event")
    sn_client.auto_track(EventTypes.LOG_IN)
    return "Performing Login event"


@app.route('/signup')
def signup():
    app.logger.debug("Performing Signup event")
    sn_client.auto_track(EventTypes.SIGN_UP)
    return "Performing Signup event"


@app.route('/logout')
def logout():
    app.logger.debug("Performing Logout event")
    sn_client.auto_track(EventTypes.LOG_OUT)
    return "Performing Logout event"


@app.route('/failed_login')
def fail_login():
    app.logger.debug("Performing Failed Login event")
    sn_client.auto_track(EventTypes.LOG_IN_FAILURE)
    return "Performing Failed Login event"


@app.route('/auto_verify')
def auto_verify():
    app.logger.debug("Performing Verify event")
    v = sn_client.auto_verify(EventTypes.VERIFY)
    return "Performing Verify event; {}".format(v)


@app.route('/verify')
def verify():
    data = dict()
    if request.data:
        data = json.loads(request.data)

    event_type = data.get("eventType", "")
    user_id = data.get("userId", "")
    traits = data.get("userTraits", "")
    user_traits = UserTraits()
    if traits:
        t = json.loads(traits)
        user_traits = UserTraits(t.get("name", ""), t.get("email"), t.get("phone"), t.get("createdAt"))
    req = data.get("request", None)
    properties = data.get("properties", None)
    timestamp = data.get("timestamp", "")

    app.logger.debug("Performing Verify event")
    v = sn_client.verify(event_type, user_id, user_traits, req, properties, timestamp)
    return "Performing Verify event; {}".format(v)


@app.route('/track')
def track():
    data = dict()
    if request.data:
        data = json.loads(request.data)

    event_type = data.get("eventType", "")
    user_id = data.get("userId", "")
    traits = data.get("userTraits", "")
    user_traits = UserTraits()
    if traits:
        t = json.loads(traits)
        user_traits = UserTraits(t.get("name", ""), t.get("email"), t.get("phone"), t.get("createdAt"))
    req = data.get("request", None)
    properties = data.get("properties", None)
    timestamp = data.get("timestamp", "")

    app.logger.debug("Performing Track event")
    v = sn_client.track(event_type, user_id, user_traits, req, properties, timestamp)
    return "Performing Track event; {}".format(v)
