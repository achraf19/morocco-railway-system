INSERT INTO RAILWAY_LINES VALUES (119, 7081991, '2023-12-26');

INSERT INTO TRAINS VALUES (
    19,
    0912855,
    '2023-12-27',
    'EXPRESS',
    300,
    false
);

INSERT INTO ROUTES VALUES (
    0912800,
    'Rabat Ville',
    'Casa Port',
    '2024-01-03',
    '10:00:00',
    '10:56:00',
    50.00,
    3,
    0,
    JSON_ARRAY('Monday', 'Wednesday'),
    JSON_ARRAY(
        'Rabat Agdal', 'Temara', 'Skhirat'
    ),
    JSON_ARRAY(
        '10:08:00', '10:19:00', '10:28:00'
    ),
    19,
    119
);
