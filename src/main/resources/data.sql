INSERT INTO authority (role) VALUES
    ('ROLE_USER'),
    ('ROLE_ADMIN');

INSERT INTO app_user (username, email, password, name, gender, bio, profile_image_url,
                      date_of_birth, registration_date) VALUES
      (
          'admin',
          'admin@roommatcher.hr',
          'admin12345',
          'Admin RoomMatcher',
          'MALE',
          'Administrator sustava za testiranje RoomMatcher aplikacije.',
          '/uploads/avatars/default-admin.png',
          '1998-05-12',
          CURRENT_TIMESTAMP
      ),
      (
          'ana.zg',
          'ana.zg@student.hr',
          'ana12345',
          'Ana Kovač',
          'FEMALE',
          'Studentica FER-a, tražim mirnu cimericu za stan blizu faksa. Volim uredan prostor i dogovor oko kućanskih obaveza.',
          '/uploads/avatars/ana.png',
          '2002-03-18',
          CURRENT_TIMESTAMP
      ),
      (
          'marko.ri',
          'marko.ri@student.hr',
          'marko12345',
          'Marko Horvat',
          'MALE',
          'Student informatike u Rijeci. Tražim cimera, ne pušim i uglavnom učim doma.',
          '/uploads/avatars/marko.png',
          '2001-11-04',
          CURRENT_TIMESTAMP
      ),
      (
          'lara.st',
          'lara.st@student.hr',
          'lara12345',
          'Lara Bilić',
          'FEMALE',
          'Studentica ekonomije u Splitu. Tražim stan blizu kampusa ili dobru autobusnu povezanost.',
          '/uploads/avatars/lara.png',
          '2003-07-21',
          CURRENT_TIMESTAMP
      );

INSERT INTO app_user_authority (app_user_id, authority_id) VALUES
        (1, 2),
        (2, 1),
        (3, 1),
        (4, 1);


INSERT INTO listing (title, app_user_id, address, price, size, description,
                     available_from, is_active) VALUES
      (
          'Soba blizu Studentskog doma Stjepan Radić',
          2,
          'Jarunska ulica 2, Zagreb',
          280.00,
          48,
          'Iznajmljuje se jedna soba u stanu blizu Save i tramvaja. Stan je pogodan za studente, režije se dijele po dogovoru.',
          '2026-09-01',
          true
      ),
      (
          'Cimer u blizini Kampusa Trsat',
          3,
          'Radmile Matejčić 5, Rijeka',
          250.00,
          42,
          'Tražim cimera za stan u blizini Kampusa Trsat. Stan ima dvije sobe, kuhinju, kupaonicu i dobru povezanost autobusom.',
          '2026-08-15',
          true
      ),
      (
          'Soba kod Studentskog doma Bruno Bušić',
          4,
          'Ulica Matice hrvatske 1, Split',
          300.00,
          55,
          'Slobodna soba u studentskom stanu u Splitu. Blizu je dom, menza i autobusne linije prema kampusu.',
          '2026-09-10',
          true
      ),
      (
          'Stan za dvije osobe blizu Filozofskog fakulteta',
          2,
          'Ivana Lučića 3, Zagreb',
          520.00,
          60,
          'Dvosoban stan pogodan za dvije studentice ili studenta. Blizina fakulteta, tramvaja i trgovina.',
          '2026-10-01',
          true
      );

INSERT INTO favorite_listing (app_user_id, listing_id) VALUES
    (2, 1),
    (2, 4),
    (3, 2),
    (4, 3);
