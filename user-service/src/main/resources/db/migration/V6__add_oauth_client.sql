INSERT INTO oauth_client_details(client_id, resource_ids, client_secret, scope,
                                 authorized_grant_types, access_token_validity, refresh_token_validity)
VALUES ('zEQBLzpCEJVD', 'oauth2-resource', '$2a$10$RK9EBZEeUL5iY1wesztKXezLEkENb67QQhmJPmsz1CQpnY.xJXSuS',
        'read,write', 'password,refresh_token', 600, 900)
ON CONFLICT DO NOTHING;
