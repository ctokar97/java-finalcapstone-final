BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, party, playlist, song, user_party, playlist_song;

CREATE TABLE users (
                       user_id SERIAL,
                       username varchar(50) NOT NULL UNIQUE,
                       password_hash varchar(200) NOT NULL,
                       role varchar(50) NOT NULL,
                       CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE party (
                       party_id SERIAL,
                       party_name varchar(50) NOT NULL UNIQUE,
                       CONSTRAINT PK_party PRIMARY KEY (party_id)
);

CREATE TABLE playlist (
                          playlist_id SERIAL,
                          playlist_name varchar(50) NOT NULL UNIQUE,
                          party_id int NOT NULL,
                          CONSTRAINT PK_playlist PRIMARY KEY (playlist_id),
                          CONSTRAINT FK_playlist_party FOREIGN KEY (party_id) REFERENCES party(party_id)
);

CREATE TABLE song (
                      song_id SERIAL,
                      song_name varchar(100) NOT NULL,
                      artist varchar(100) NOT NULL,
                      genre varchar(50) NOT NULL,
                      user_genre varchar(50),
                      CONSTRAINT PK_song PRIMARY KEY (song_id)
);

CREATE TABLE user_party (
                            user_id int NOT NULL,
                            party_id int NOT NULL,
                            CONSTRAINT PK_user_party PRIMARY KEY (user_id, party_id),
                            CONSTRAINT FK_user_user_party FOREIGN KEY (user_id) REFERENCES users(user_id),
                            CONSTRAINT FK_party_user_party FOREIGN KEY (party_id) REFERENCES party(party_id)
);

CREATE TABLE playlist_song (
                               playlist_id int NOT NULL,
                               song_id int NOT NULL,
                               CONSTRAINT PK_playlist_song PRIMARY KEY (playlist_id, song_id),
                               CONSTRAINT FK_playlist_playlist_song FOREIGN KEY (playlist_id) REFERENCES playlist(playlist_id),
                               CONSTRAINT FK_song_playlist_song FOREIGN KEY (song_id) REFERENCES song(song_id)
);

COMMIT TRANSACTION;