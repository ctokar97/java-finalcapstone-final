BEGIN TRANSACTION;

INSERT INTO party (party_name) VALUES
                                   ('Ellen\'s Party'),
                                   ('Tom\'s Party'),
                                   ('Walt\'s Party'),
                                   ('Tom M\'s Party'),
                                   ('Jennifer\'s Party');

INSERT INTO song (song_name, artist, genre, user_genre) VALUES
                                                            ('Here Comes The Sun', 'The Beatles', 'Folk-pop', ''),
                                                            ('I\'ll Follow the Sun', 'The Beatles', 'Folk-pop', ''),
                                                            ('California Sun', 'The Riveras', 'Rock', ''),
                                                            ('', 'David Shimenko', 'genre4', ''),
                                                            ('', 'David Shimenko', 'genre5', ''),
                                                            ('', 'David Shimenko', 'genre2', ''),
                                                            ('Electric Boogie', 'Marcia Griffiths', 'Funk', ''),
                                                            ('She\'s Electric', 'Oasis', 'Rock', ''),
                                                            ('Electric Avenue', 'Eddy Grant', 'Reggae', '');

INSERT INTO playlist (playlist_name, party_id) VALUES
                                                   ('David Shimenko Experience', 1),
                                                   ('Sunshine Rays', 2),
                                                   ('It\'s Electric!!', 3),


INSERT INTO playlist_song (playlist_id, song_id) VALUES
                                                     (1, )
                                                     (1, )
                                                     (1, )
                                                     (2, 1),
                                                     (2, 2),
                                                     (2, 3),
                                                     (3, 7),
                                                     (3, 8),
                                                     (3, 8);

COMMIT;