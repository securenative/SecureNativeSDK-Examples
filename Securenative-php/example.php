<?php

ini_set("display_errors", 1);

require __DIR__ . '/vendor/autoload.php';

use SecureNative\sdk\SecureNative;
use SecureNative\sdk\SecureNativeOptions;
use SecureNative\sdk\EventTypes;

$API_KEY = 'YOUR_API_KEY';

// Initialize SDK
SecureNative::init($API_KEY, new SecureNativeOptions());

// Sample of track function
SecureNative::track(array(
    'eventType' => EventTypes::LOG_IN,
    'ip' => '103.234.220.197',
    'userAgent' => 'Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405',
    'user' => (object)[
        'id' => '12345',
        'name' => '',
        'email' => 'test@test.com'
    ],
    'params' => ["param_1" => "param value"]
));

// Sample of verify function
$ver = SecureNative::verify(array(
    'eventType' => EventTypes::VERIFY,
    'ip' => '103.234.220.197',
    'userAgent' => 'Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405',
    'user' => (object)[
        'id' => '12345',
        'name' => '',
        'email' => 'test@test.com'
    ],
    'params' => ["param_1" => "param value"]
));

echo "Verify risk level: " . $ver->riskLevel;