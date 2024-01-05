package org.example.pattern.singleton;

import org.example.db.ConnectionManager;

public final class ConnectionManagerSingleton {
    private static volatile ConnectionManager instance;
    private static final Object lock = new Object();



}
