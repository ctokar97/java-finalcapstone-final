BEGIN TRANSACTION;

INSERT INTO users (username, password_hash, role) VALUES
                                                      ('user1', 'password123', 'admin'),
                                                      ('user2', 'password234', 'user'),
                                                      ('user3', 'password345', 'user'),
                                                      ('user4', 'password456', 'admin'),
                                                      ('user5', 'password567', 'user');

INSERT INTO party (party_name) VALUES
                                   ('party1'),
                                   ('party2'),
                                   ('party3'),
                                   ('party4'),
                                   ('party5');

INSERT INTO song (song_name, artist, genre, user_genre) VALUES
                                                            ('song1', 'artist1', 'genre1', 'user_genre1'),
                                                            ('song2', 'artist2', 'genre2', 'user_genre2'),
                                                            ('song3', 'artist3', 'genre3', 'user_genre3'),
                                                            ('song4', 'artist4', 'genre4', 'user_genre4'),
                                                            ('song5', 'artist5', 'genre5', 'user_genre5');

INSERT INTO playlist (playlist_name, party_id) VALUES
                                                   ('playlist1', 1),
                                                   ('playlist2', 2),
                                                   ('playlist3', 3),
                                                   ('playlist4', 4),
                                                   ('playlist5', 5);

INSERT INTO user_party (user_id, party_id) VALUES
                                               (1, 1),
                                               (2, 2),
                                               (3, 3),
                                               (4, 4),
                                               (5, 5);

INSERT INTO playlist_song (playlist_id, song_id) VALUES
                                                     (1, 1),
                                                     (2, 2),
                                                     (3, 3),
                                                     (4, 4),
                                                     (5, 5);

COMMIT;

-- BEGIN TRANSACTION;
-- DO
-- $$
--     DECLARE
--         i INTEGER;
--         j INTEGER;
--         k INTEGER;
--         current_party_id INTEGER;
--         current_user_id INTEGER;
--         current_playlist_id INTEGER;
--         current_song_id INTEGER;
--     BEGIN
--         FOR i IN 1..15 LOOP
--                 INSERT INTO party (party_name) VALUES ('party' || i)
--                 RETURNING party_id INTO current_party_id;
--
--                 INSERT INTO playlist (playlist_name, party_id)
--                 VALUES ('playlist' || i, current_party_id)
--                 RETURNING playlist_id INTO current_playlist_id;
--
--                 FOR j IN 1..15 LOOP
--                         INSERT INTO users (username, password_hash, role)
--                         VALUES ('user' || ((i - 1) * 15 + j), 'password' || ((i - 1) * 15 + j), 'user')
--                         RETURNING user_id INTO current_user_id;
--
--                         INSERT INTO user_party(user_id, party_id) VALUES (current_user_id, current_party_id);
--                     END LOOP;
--
--                 FOR k IN 1..10 LOOP
--                         INSERT INTO song (song_name, artist, genre, user_genre)
--                         VALUES ('song' || ((i - 1) * 10 + k), 'artist' || ((i - 1) * 10 + k), 'genre' || ((i - 1) * 10 + k),
--                                 'user_genre' || ((i - 1) * 10 + k))
--                         RETURNING song_id INTO current_song_id;
--
--                         INSERT INTO playlist_song (playlist_id, song_id)
--                         VALUES (current_playlist_id, current_song_id);
--                     END LOOP;
--             END LOOP;
--     END;
-- $$;
-- COMMIT;

-- BEGIN TRANSACTION;
-- DO
-- $$
--     DECLARE
--         i INTEGER;
--         j INTEGER;
--         k INTEGER;
--         l INTEGER;
--         current_party_id INTEGER;
--         current_user_id INTEGER;
--         current_playlist_id INTEGER;
--         current_song_id INTEGER;
--         current_request_song_id INTEGER;
--     BEGIN
--         FOR i IN 1..15 LOOP
--                 INSERT INTO party (party_name) VALUES ('party' || i)
--                 RETURNING party_id INTO current_party_id;
--
--                 INSERT INTO playlist (playlist_name, party_id)
--                 VALUES ('playlist' || i, current_party_id)
--                 RETURNING playlist_id INTO current_playlist_id;
--
--                 FOR j IN 1..15 LOOP
--                         INSERT INTO users (username, password_hash, role)
--                         VALUES ('user' || ((i - 1) * 15 + j), 'password' || ((i - 1) * 15 + j), 'user')
--                         RETURNING user_id INTO current_user_id;
--
--                         INSERT INTO user_party(user_id, party_id) VALUES (current_user_id, current_party_id);
--                     END LOOP;
--
--                 FOR k IN 1..10 LOOP
--                         INSERT INTO song (song_name, artist, genre, user_genre)
--                         VALUES ('Take Forever', 'Colony Collapse', 'genre' || ((i - 1) * 10 + k),
--                                 'user_genre' || ((i - 1) * 10 + k))
--                         RETURNING song_id INTO current_song_id;
--
--                         INSERT INTO playlist_song (playlist_id, song_id)
--                         VALUES (current_playlist_id, current_song_id);
--                     END LOOP;
--
--                 FOR l IN 1..10 LOOP
--                         INSERT INTO song (song_name, artist, genre, user_genre)
--                         VALUES ('Take Forever', 'Colony Collapse', 'genre' || ((i - 1) * 10 + l),
--                                 'user_genre' || ((i - 1) * 10 + l))
--                         RETURNING song_id INTO current_request_song_id;
--
--                         INSERT INTO request_list (party_id, song_id)
--                         VALUES (current_party_id, current_request_song_id);
--                     END LOOP;
--             END LOOP;
--     END;
-- $$;
-- COMMIT;

BEGIN TRANSACTION;
DO
$$
    DECLARE
        i INTEGER;
        j INTEGER;
        k INTEGER;
        l INTEGER;
        current_party_id INTEGER;
        current_user_id INTEGER;
        current_playlist_id INTEGER;
        current_song_id INTEGER;
        current_request_song_id INTEGER;
        artist_names TEXT[] := ARRAY['Taylor Swift', 'Ed Sheeran', 'The Weeknd', 'Bruno Mars', 'Justin Bieber', 'Ariana Grande', 'Beyonce', 'Rihanna', 'Lady Gaga', 'Maroon 5'];
        song_names TEXT[] := ARRAY['Love Story', 'Shape of You', 'Blinding Lights', 'Just The Way You Are', 'Sorry', 'Thank You, Next', 'Halo', 'Diamonds', 'Poker Face', 'Sugar'];
        party_names TEXT[] := ARRAY['ThunderNight', 'ElectroFest', 'JazzyEvening', 'IndieGathering', 'MetalMania', 'PopParty', 'RetroRevival', 'ReggaeRumble', 'ClassicCarnival', 'RockRave', 'BluesBash', 'FiestaLatina', 'CountryCraze', 'SoulSession', 'HipHopHoopla'];
        user_names TEXT[] := ARRAY['SwiftFan123', 'EdHead', 'WeekndWarrior', 'Martian', 'Belieber', 'AriArmy', 'BeyHive', 'RiriNavy', 'Monster', 'Marooner', 'DiamondHeart', 'PokerAce', 'SugarCoated', 'LoveStruck', 'StarShaped'];
    BEGIN
        FOR i IN 1..15 LOOP
                INSERT INTO party (party_name) VALUES (party_names[i])
                RETURNING party_id INTO current_party_id;

                INSERT INTO playlist (playlist_name, party_id)
                VALUES ('Playlist_for_' || party_names[i], current_party_id)
                RETURNING playlist_id INTO current_playlist_id;

                FOR j IN 1..15 LOOP
                        INSERT INTO users (username, password_hash, role)
                        VALUES (user_names[j] || i, 'password' || ((i - 1) * 15 + j), 'user')
                        RETURNING user_id INTO current_user_id;

                        INSERT INTO user_party(user_id, party_id) VALUES (current_user_id, current_party_id);
                    END LOOP;

                FOR k IN 1..10 LOOP
                        INSERT INTO song (song_name, artist, genre, user_genre)
                        VALUES (song_names[k], artist_names[k], 'Pop', 'user_genre' || ((i - 1) * 10 + k))
                        RETURNING song_id INTO current_song_id;

                        INSERT INTO playlist_song (playlist_id, song_id)
                        VALUES (current_playlist_id, current_song_id);
                    END LOOP;

                FOR l IN 1..10 LOOP
                        INSERT INTO song (song_name, artist, genre, user_genre)
                        VALUES (song_names[l], artist_names[l], 'Pop', 'user_genre' || ((i - 1) * 10 + l))
                        RETURNING song_id INTO current_request_song_id;

                        INSERT INTO request_list (party_id, song_id)
                        VALUES (current_party_id, current_request_song_id);
                    END LOOP;
            END LOOP;
    END;
$$;
COMMIT;
ROLLBACK