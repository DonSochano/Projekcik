package org.example.models;

public interface Topic<M extends TopicMessage> { // to M jest tu nie uzywane ale konieczne zeby wymusic silne typowanie że Topic ma swój format wiadomosci

    String getName();
}
