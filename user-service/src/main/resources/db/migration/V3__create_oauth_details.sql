drop table if exists oauth_client_details;
create table oauth_client_details (
                                      client_id VARCHAR(255) PRIMARY KEY,
                                      resource_ids VARCHAR(255),
                                      client_secret VARCHAR(255),
                                      scope VARCHAR(255),
                                      authorized_grant_types VARCHAR(255),
                                      web_server_redirect_uri VARCHAR(255),
                                      authorities VARCHAR(255),
                                      access_token_validity INTEGER,
                                      refresh_token_validity INTEGER,
                                      additional_information VARCHAR(4096),
                                      autoapprove VARCHAR(255)
);

insert into oauth_client_details(
    client_id, resource_ids, client_secret, scope, authorized_grant_types, access_token_validity, refresh_token_validity
) values(
    'zEQBLzpCEJVD', 'resource', '$2a$10$RK9EBZEeUL5iY1wesztKXezLEkENb67QQhmJPmsz1CQpnY.xJXSuS', 'read,write', 'password,refresh_token', 600, 900
);
