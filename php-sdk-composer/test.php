<?php

require_once __DIR__ . '/vendor/autoload.php';

use SecureNative\sdk\Logger;
use SecureNative\sdk\SecureNative;
use SecureNative\sdk\SecureNativeContext;
use SecureNative\sdk\SecureNativeOptions;
use SecureNative\sdk\EventTypes;

//ini_set("display_errors", 1);

echo '
<html>
<head>
    <title>Php</title>

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
        securenative.load({"appId": "SN-A95E6AEA44"});
    </script>
</head>
<body>';


$options = new SecureNativeOptions();

$options->setMaxEvents(10);
$options->setLogLevel("debug");

SecureNative::init("65B02390B7CEACB5D3C292740CD7293732E1FE93");

$token = "c21e691a3c71dd3bf11178020411ed4fcb324474dcc0452d6a14277da9e6486bc8f7c3852c075a6d96377397efdd93a7e8e7e72ce2704260a0265c155c1c3c941ea48c5232e4de8f902e4e54aa1df8cf140cecf313e443e7193695b8c57dd881d7c54c9b3f122cf11007cfe2226388600dac284b749562b103e0d7e4d519a4bc5b09886c18066014fc72db9819cb82d7534cb97f09b78f2bc8883c9b082408b8633411c902f0005e5f9e1350c26cfcf48ddcc793a66cf7a6c9e39b8413dc5617";
$ctx = new SecureNativeContext($token, "79.179.88.157", null, (object)["user-agent" => "Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us"], null, null, null);

Logger::debug("Context", $ctx->getClientToken(), $ctx->getHeaders());

print_r(SecureNativeContext::fromRequest());

Logger::debug("CTX", SecureNativeContext::fromRequest());

if (isset($_REQUEST["formSent"])) {
    $ver = SecureNative::verify(array(
        'event' => EventTypes::LOG_IN,
        'userId' => '30',
        'context' => SecureNativeContext::fromRequest(),
        'userTraits' => (object)[
            'name' => 'Your Name',
            'email' => 'name@gmail.com'
        ]
    ));
}

echo '
yo
    <form action="test.php" method="post">
        <input type="hidden" name="formSent" value="1" />
        <input type="submit" value="Send" />
    </form>
</body>
</html>';

//echo "Hello ver" . var_dump($ver);