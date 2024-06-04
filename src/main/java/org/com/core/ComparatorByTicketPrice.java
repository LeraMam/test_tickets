package org.com.core;

import java.util.Comparator;

class ComparatorByTicketPrice implements Comparator<Ticket> {
    @Override
    public int compare(Ticket obj1, Ticket obj2) {
        return Double.compare(obj1.getPrice(), obj2.getPrice());
    }
}
