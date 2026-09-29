

USING itp-identity-service
INSERT INTO roles (name)
VALUES ('SUPER_ADMIN'),
       ('ADMIN'),
       ('USER'),
       ('GUEST');

INSERT INTO permissions (name)
VALUES ('user:read:all'),
       ('user:read:own'),
       ('user:write:own'),
       ('user:write:all'),
       ('user:delete:all'),
       ('role:read:all'),
       ('role:manage:all'),
       ('permission:read:all'),
       ('permission:manage:all');

INSERT INTO roles_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.name = 'SUPER_ADMIN';

INSERT INTO roles_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.name = 'USER'
  AND p.name IN ('user:read:own', 'user:write:own');
