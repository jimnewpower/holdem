package com.primalimited.app.view;

import java.awt.*;

public interface Controller {
    void init();
    void removeAll();
    void add(Component component, Object constraints);
    void resize(int width, int height);
}
