<?php

require_once __DIR__ . '/vendor/autoload.php';

use SecureNative\sdk\SecureNative;
use SecureNative\sdk\SecureNativeOptions;
use SecureNative\sdk\EventTypes;


$options =  new SecureNativeOptions();

$options->setMaxEvents(10);
$options->setLogLevel("error");

SecureNative::init("YOUR_API_KEY", $options);


$context = SecureNative::contextFromContext();

// Sample of track function
SecureNative::track(array(
    'event' => EventTypes::LOG_IN,
    'context' => $context,
    'userId' => '556595',
    'userTraits' => (object)[
        'name' => 'Your name',
        'email' => 'test@test.com'
    ],
    // Custom properties
    'properties' => (object)[
        "prop1" => "test",
        "prop2" => 3
    ]
));

// Sample of verify function
SecureNative::verify(array(
    'event' => EventTypes::VERIFY,
    'context' => $context,
    'userId' => '556595',
    'userTraits' => (object)[
        'name' => 'Your name',
        'email' => 'test@test.com'
    ],
    // Custom properties
    'properties' => (object)[
        "prop1" => "test",
        "prop2" => 3
    ]
));