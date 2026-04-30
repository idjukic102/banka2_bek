-- ============================================================
-- Banka 2025 — Seed Data
-- ============================================================
-- Lozinke za testiranje:
--   Admin korisnici (users tabela):    Admin12345
--   Obicni klijenti (users tabela):    Klijent12345
--   Zaposleni (employees tabela):      Zaposleni12
-- ============================================================

-- Sacekaj da Hibernate kreira tabele (ddl-auto=update)
-- Ovaj fajl se izvrsava posle kreiranja baze

-- ============================================================
-- USERS (klijenti i admini koji se loguju kroz /auth/login)
-- ============================================================

INSERT INTO users (first_name, last_name, email, password, username, phone, address, active, role)
VALUES
  -- ADMIN korisnici
  ('Marko', 'Petrović', 'marko.petrovic@banka.rs',
   '$2b$10$2o//nneiTVurujS8ou5Snu3qNbF3Q20CbPnLc9ag2q0YIO1R3SyZG',
   'marko.petrovic', '+381 63 111 2233', 'Knez Mihailova 15, Beograd', 1, 'ADMIN'),

  ('Jelena', 'Đorđević', 'jelena.djordjevic@banka.rs',
   '$2b$10$2o//nneiTVurujS8ou5Snu3qNbF3Q20CbPnLc9ag2q0YIO1R3SyZG',
   'jelena.djordjevic', '+381 64 222 3344', 'Bulevar Oslobođenja 42, Novi Sad', 1, 'ADMIN'),

  -- Obicni klijenti
  ('Stefan', 'Jovanović', 'stefan.jovanovic@gmail.com',
   '$2b$10$FUjcSzK7CZKeX53YVU4JjeOIXLt5axbipO85OlQqw5Dopg47zfgRG',
   'stefan.jovanovic', '+381 65 333 4455', 'Cara Dušana 8, Niš', 1, 'CLIENT'),

  ('Milica', 'Nikolić', 'milica.nikolic@gmail.com',
   '$2b$10$FUjcSzK7CZKeX53YVU4JjeOIXLt5axbipO85OlQqw5Dopg47zfgRG',
   'milica.nikolic', '+381 66 444 5566', 'Vojvode Stepe 23, Beograd', 1, 'CLIENT'),

  ('Lazar', 'Ilić', 'lazar.ilic@yahoo.com',
   '$2b$10$FUjcSzK7CZKeX53YVU4JjeOIXLt5axbipO85OlQqw5Dopg47zfgRG',
   'lazar.ilic', '+381 60 555 6677', 'Bulevar Kralja Petra 71, Kragujevac', 1, 'CLIENT'),

  ('Ana', 'Stojanović', 'ana.stojanovic@hotmail.com',
   '$2b$10$FUjcSzK7CZKeX53YVU4JjeOIXLt5axbipO85OlQqw5Dopg47zfgRG',
   'ana.stojanovic', '+381 69 666 7788', 'Đorđa Stanojevića 12, Beograd', 1, 'CLIENT'),

  -- Neaktivan klijent (za testiranje)
  ('Nemanja', 'Savić', 'nemanja.savic@gmail.com',
   '$2b$10$FUjcSzK7CZKeX53YVU4JjeOIXLt5axbipO85OlQqw5Dopg47zfgRG',
   'nemanja.savic', '+381 62 777 8899', 'Terazije 5, Beograd', 0, 'CLIENT')

  -- FUND korisnici
    ('Koordinacioni Fond', 'Beograda', 'koordinacioni.fond@banka.rs',
    '$2b$10$2o//nneiTVurujS8ou5Snu3qNbF3Q20CbPnLc9ag2q0YIO1R3SyZG',
    'koordinacioni.fond', '+381 11 123 4567', 'Bulevar Mihaila Pupina 10, Beograd', 1, 'FUND'),
ON DUPLICATE KEY UPDATE email = email;


-- ============================================================
-- EMPLOYEES (zaposleni u banci)
-- ============================================================

INSERT INTO employees (first_name, last_name, date_of_birth, gender, email, phone, address, username, password, salt_password, position, department, active)
VALUES
  ('Nikola', 'Milenković', '1988-03-15', 'M', 'nikola.milenkovic@banka.rs',
   '+381 63 100 2000', 'Nemanjina 4, Beograd', 'nikola.milenkovic',
   '$2b$10$lqAByD7N8elcbkNzut14L.dsZTHrWGL5r3qrp9KvzPw58.AzE4eHG',
   'c2VlZF9zYWx0XzAwMDFfXw==',
   'Team Lead', 'IT', 1),

  ('Tamara', 'Pavlović', '1992-07-22', 'F', 'tamara.pavlovic@banka.rs',
   '+381 64 200 3000', 'Kneza Miloša 32, Beograd', 'tamara.pavlovic',
   '$2b$10$727ZuuF8vHqGZyMrUTagCOZzhnvcV6Egf9198l5wEzyo07quVYkwq',
   'c2VlZF9zYWx0XzAwMDJfXw==',
   'Software Developer', 'IT', 1),

  ('Đorđe', 'Janković', '1985-11-03', 'M', 'djordje.jankovic@banka.rs',
   '+381 65 300 4000', 'Bulevar Mihajla Pupina 10, Novi Sad', 'djordje.jankovic',
   '$2b$10$xV3rnn442L9OG/tW6cz1TeR.hHwCamR/bO9Am3PFcrkMqG9PMiiYe',
   'c2VlZF9zYWx0XzAwMDNfXw==',
   'HR Manager', 'HR', 1),

  ('Maja', 'Ristić', '1995-01-18', 'F', 'maja.ristic@banka.rs',
   '+381 66 400 5000', 'Trg Republike 3, Beograd', 'maja.ristic',
   '$2b$10$00rB0B.rYUHcyAJc61hh2e3TjxI7zpQ.BUIR2ZchOjyaVE0AXkQYG',
   'c2VlZF9zYWx0XzAwMDRfXw==',
   'Accountant', 'Finance', 1),

  ('Vuk', 'Obradović', '1990-09-07', 'M', 'vuk.obradovic@banka.rs',
   '+381 60 500 6000', 'Železnička 15, Niš', 'vuk.obradovic',
   '$2b$10$g8WmJQ5QRHkJy59X5wxYf.Cfn5K9904fSiLY5QHUvCKfgOBLsDlAS',
   'c2VlZF9zYWx0XzAwMDVfXw==',
   'Supervisor', 'Operations', 0)
ON DUPLICATE KEY UPDATE email = email;


-- ============================================================
-- EMPLOYEE PERMISSIONS
-- ============================================================

-- Nikola Milenković — Team Lead (sve permisije)
INSERT INTO employee_permissions (employee_id, permission)
SELECT e.id, p.permission
FROM employees e
CROSS JOIN (
  SELECT 'ADMIN' AS permission UNION ALL
  SELECT 'TRADE_STOCKS' UNION ALL
  SELECT 'VIEW_STOCKS' UNION ALL
  SELECT 'CREATE_CONTRACTS' UNION ALL
  SELECT 'CREATE_INSURANCE' UNION ALL
  SELECT 'SUPERVISOR' UNION ALL
  SELECT 'AGENT'
) p
WHERE e.email = 'nikola.milenkovic@banka.rs'
AND NOT EXISTS (
  SELECT 1 FROM employee_permissions ep WHERE ep.employee_id = e.id AND ep.permission = p.permission
);

-- Tamara Pavlović — Developer (stocks + contracts)
INSERT INTO employee_permissions (employee_id, permission)
SELECT e.id, p.permission
FROM employees e
CROSS JOIN (
  SELECT 'VIEW_STOCKS' AS permission UNION ALL
  SELECT 'TRADE_STOCKS' UNION ALL
  SELECT 'CREATE_CONTRACTS'
) p
WHERE e.email = 'tamara.pavlovic@banka.rs'
AND NOT EXISTS (
  SELECT 1 FROM employee_permissions ep WHERE ep.employee_id = e.id AND ep.permission = p.permission
);

-- Đorđe Janković — HR Manager (supervisor + agent)
INSERT INTO employee_permissions (employee_id, permission)
SELECT e.id, p.permission
FROM employees e
CROSS JOIN (
  SELECT 'SUPERVISOR' AS permission UNION ALL
  SELECT 'AGENT'
) p
WHERE e.email = 'djordje.jankovic@banka.rs'
AND NOT EXISTS (
  SELECT 1 FROM employee_permissions ep WHERE ep.employee_id = e.id AND ep.permission = p.permission
);

-- Maja Ristić — Accountant (insurance + contracts + view stocks)
INSERT INTO employee_permissions (employee_id, permission)
SELECT e.id, p.permission
FROM employees e
CROSS JOIN (
  SELECT 'CREATE_INSURANCE' AS permission UNION ALL
  SELECT 'CREATE_CONTRACTS' UNION ALL
  SELECT 'VIEW_STOCKS'
) p
WHERE e.email = 'maja.ristic@banka.rs'
AND NOT EXISTS (
  SELECT 1 FROM employee_permissions ep WHERE ep.employee_id = e.id AND ep.permission = p.permission
);

-- Vuk Obradović — Supervisor (neaktivan, ima supervisor + view stocks)
INSERT INTO employee_permissions (employee_id, permission)
SELECT e.id, p.permission
FROM employees e
CROSS JOIN (
  SELECT 'SUPERVISOR' AS permission UNION ALL
  SELECT 'VIEW_STOCKS'
) p
WHERE e.email = 'vuk.obradovic@banka.rs'
AND NOT EXISTS (
  SELECT 1 FROM employee_permissions ep WHERE ep.employee_id = e.id AND ep.permission = p.permission
);
