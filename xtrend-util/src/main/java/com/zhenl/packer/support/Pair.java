package com.zhenl.packer.support;

final class Pair<A, B> {
    private final A f;
    private final B s;

    private Pair(A first, B second) {
        this.f = first;
        this.s = second;
    }

    public static <A, B> Pair<A, B> of(A first, B second) {
        return new Pair<>(first, second);
    }

    public A getFirst() {
        return this.f;
    }

    public B getSecond() {
        return this.s;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pair<?, ?> pair = (Pair) o;
        if (this.f == null ? pair.f != null : !this.f.equals(pair.f)) {
            return false;
        }
        if (this.s != null) {
            return this.s.equals(pair.s);
        }
        return pair.s == null;
    }

    public int hashCode() {
        int result;
        int i = 0;
        if (this.f != null) {
            result = this.f.hashCode();
        } else {
            result = 0;
        }
        int i2 = result * 31;
        if (this.s != null) {
            i = this.s.hashCode();
        }
        return i2 + i;
    }
}
