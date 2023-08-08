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