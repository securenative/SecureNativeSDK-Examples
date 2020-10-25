import os

from flask import request
from securenative.config.securenative_options import SecureNativeOptions
from securenative.context.securenative_context import SecureNativeContext
from securenative.enums.event_types import EventTypes
from securenative.models.event_options import EventOptions
from securenative.models.user_traits import UserTraits
from securenative.securenative import SecureNative

from demo import app

# SecureNative.init("/python-sdk-flask/securenative.ini")
options = SecureNativeOptions(api_key=os.getenv("SECURENATIVE_API_KEY"), proxy_headers=["CF-Random-IP"])
SecureNative.init_with_options(options)


@app.route('/')
def index():
    return "SecureNative Python Agent integration website"


@app.route('/track')
def track():
    securenative = SecureNative.get_instance()

    # context = SecureNativeContext(client_token=os.getenv("SECURENATIVE_CLIENT_TOKEN"),
    #                               ip="127.0.0.1",
    #                               headers={
    #                                   "user-agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36"})

    context = securenative.from_http_request(request)

    event_options = EventOptions(event=EventTypes.LOG_IN,
                                 user_id="3",
                                 user_traits=UserTraits("track3", "track@gmail.com", "+1234567890"),
                                 context=context,
                                 properties={"custom_param1": "CUSTOM_PARAM_VALUE", "custom_param2": True,
                                             "custom_param3": 3})

    securenative.track(event_options)
    return "tracked"


@app.route('/verify')
def verify():
    securenative = SecureNative.get_instance()

    # context = SecureNativeContext(client_token=os.getenv("SECURENATIVE_CLIENT_TOKEN"),
    #                               ip="127.0.0.1",
    #                               headers={
    #                                   "user-agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36"})

    context = securenative.from_http_request(request)

    event_options = EventOptions(event=EventTypes.LOG_IN,
                                 user_id="4",
                                 user_traits=UserTraits("verify4", "verify@gmail.com", "+1234567890"),
                                 context=context,
                                 properties={"custom_param1": "CUSTOM_PARAM_VALUE", "custom_param2": True,
                                             "custom_param3": 3})

    verify_result = securenative.verify(event_options)
    return "Risk Level: {}; Score: {}; Triggers: {}".format(verify_result.risk_level,
                                                            verify_result.score, verify_result.triggers)
