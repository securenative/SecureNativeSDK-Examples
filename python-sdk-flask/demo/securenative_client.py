import os

from securenative.context.context_builder import ContextBuilder
from securenative.event_options_builder import EventOptionsBuilder
from securenative.models.user_traits import UserTraits
from securenative.securenative import SecureNative


class SNClient(object):
    def __init__(self):
        self.ip = "113.6.126.40"
        self.remote_ip = "155.88.195.17"
        self.options = SecureNative.config_builder(). \
            with_api_key(os.getenv("SECURENATIVE_API_KEY")). \
            with_log_level("DEBUG")

        self.sn_client = SecureNative.init(os.getenv("CONFIGURATION_PATH"))
        self.user_agent = {"user-agent": os.getenv("USER_AGENT",
                                                   "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")}
        self.user = UserTraits("Micky Mouse", "micky@securenative.com", "+12012673412")

    def auto_track(self, event_type):
        context = ContextBuilder.default_context_builder(). \
            with_client_token(os.getenv("SECURENATIVE_CLIENT_TOKEN")). \
            with_headers(self.user_agent).build()
        event_options = EventOptionsBuilder(event_type). \
            with_user_id("3"). \
            with_user_traits(self.user). \
            with_context(context).build()
        return self.sn_client.track(event_options)

    def auto_verify(self, event_type):
        context = ContextBuilder.default_context_builder(). \
            with_client_token(os.getenv("SECURENATIVE_CLIENT_TOKEN")). \
            with_headers(self.user_agent). \
            with_remote_ip(self.remote_ip). \
            with_ip(self.ip).build()
        event_options = EventOptionsBuilder(event_type). \
            with_user_id("1"). \
            with_user_traits(self.user). \
            with_context(context).build()
        return self.sn_client.verify(event_options)

    def verify(self, event_type, user_id, user_traits, req, properties, timestamp):
        if req:
            context = ContextBuilder.default_context_builder(). \
                with_client_token(req.get("clientToken")). \
                with_headers(req.get("headers")). \
                with_remote_ip(req.get("remoteIp")). \
                with_ip(req.get("ip")).build()
        else:
            context = ContextBuilder.default_context_builder().build()

        event_options = EventOptionsBuilder(event_type). \
            with_user_id(user_id). \
            with_user_traits(user_traits). \
            with_context(context). \
            with_properties(properties). \
            with_timestamp(timestamp). \
            build()
        return self.sn_client.verify(event_options)

    def track(self, event_type, user_id, user_traits, req, properties, timestamp):
        if req:
            context = ContextBuilder.default_context_builder(). \
                with_client_token(req.get("clientToken")). \
                with_headers(req.get("headers")). \
                with_remote_ip(req.get("remoteIp")). \
                with_ip(req.get("ip")).build()
        else:
            context = ContextBuilder.default_context_builder().build()

        event_options = EventOptionsBuilder(event_type). \
            with_user_id(user_id). \
            with_user_traits(user_traits). \
            with_context(context). \
            with_properties(properties). \
            with_timestamp(timestamp). \
            build()
        return self.sn_client.track(event_options)
