<?php

// Add required imports
require_once __DIR__ . '/vendor/autoload.php';

use SecureNative\sdk\SecureNative;
use SecureNative\sdk\SecureNativeContext;
use SecureNative\sdk\SecureNativeOptions;
use SecureNative\sdk\EventTypes;

// Pass required options, using default params when not passed
$options = new SecureNativeOptions();
$options->setTimeout(3000)
    ->setDisable(false)
    ->setInterval(1000)
    ->setAutoSend(true)
    ->setMaxEvents(10)
    ->setLogLevel('fatal');


// FILL MISSING PARAMETERS:
$API_KEY = "[API_KEY]"; // Mandatory
$TOKEN = "[YOUR_TOKEN]";
$APP_ID = "[YOUR_APP_ID (SN-XXXXXXXX)]";

// Passing `$options` is optional, will use default params
SecureNative::init($API_KEY, $options);


if (isset($_GET["action"])) {
    switch ($_GET["action"]) {
        case "login":
            $ctx = new SecureNativeContext($TOKEN, "99.179.77.150", null, (object)["User-Agent" => "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36"]);

            SecureNative::track(array(
                'event' => EventTypes::LOG_IN,
                'context' => $ctx, // Using custom context
                'userId' => '1234',
                'userTraits' => (object)[
                    'name' => 'Your Name',
                    'email' => 'name@gmail.com'
                ],
                // Custom properties
                'properties' => (object)[
                    "custom_param1" => "CUSTOM_PARAM_VALUE",
                    "custom_param2" => true
                ]
            ));

            echo "Track login event was sent";
            break;
        case "logout":
            SecureNative::track(array(
                'event' => EventTypes::LOG_OUT,
                'context' => SecureNativeContext::fromRequest(), // Using request context
                'userId' => '1234',
                'userTraits' => (object)[
                    'name' => 'Your Name',
                    'email' => 'name@gmail.com'
                ],
                // Custom properties
                'properties' => (object)[
                    "custom_param1" => "CUSTOM_PARAM_VALUE",
                    "custom_param2" => true
                ]
            ));

            echo "Track logout event was sent";
            break;
        case "failure":
            SecureNative::track(array(
                'event' => EventTypes::LOG_IN_FAILURE,
                'context' => SecureNativeContext::fromRequest(), // Using request context
                'userId' => '1234',
                'userTraits' => (object)[
                    'name' => 'Your Name',
                    'email' => 'name@gmail.com'
                ]
            ));

            echo "Track login-failure event was sent";
            break;
        case "verify":
            $ver = SecureNative::verify(array(
                'event' => EventTypes::VERIFY,
                'userId' => 'sample-id',
                'userTraits' => (object)[
                    'name' => 'Sample Name',
                    'email' => 'demo@test.com'
                ],
                'context' => SecureNativeContext::fromRequest()
            ));

            echo "Verify response: ";
            print_r($ver);
            break;
        case "webhook":
            $verified = SecureNative::getMiddleware()->verifySignature();

            if ($verified) {
                echo "Request is trusted (coming from SecureNative)";
            } else {
                echo "Request is not trusted! (not coming from SecureNative)";
            }
            break;
    }
}

echo '
<html>
<head>
    <title>PHP demo application</title>

    <script type="text/javascript">
        window.securenative = window.securenative || [], securenative.load = function (a) {
            if (!("SecureNative" in window || window.securenative.loading)) {
                window.securenative.loading = !0, window.securenative.options = a;
                var b = document.createElement("script");
                b.src = "https://cdn.securenative.com/1.0.0/sn-client.min.js",
                    b.async = 1;
                var c = document.getElementsByTagName("script")[0];
                c.parentNode.insertBefore(b, c)
            }
        };
        securenative.load({"appId": "' . $APP_ID . '"});
    </script>
</head>
<body>
    <h1>Welcome to SecureNative demo app</h1>

    <form action="test.php" method="get">
      <input type="hidden" name="action" value="login" />
      <button type="submit">Login</button>
    </form>
    
    <form action="test.php" method="get">
      <input type="hidden" name="action" value="failure" />
      <button type="submit">Login Failure</button>
    </form>

    <form action="test.php">
      <input type="hidden" name="action" value="logout" />
      <button type="submit">Logout</button>
    </form>

    <form action="test.php" method="get">
      <input type="hidden" name="action" value="verify" />
      <button type="submit">Verify</button>
    </form>

    <form action="test.php" method="get">
      <input type="hidden" name="action" value="webhook" />
      <button type="submit">Webhook</button>
    </form>
</body>
</html>';