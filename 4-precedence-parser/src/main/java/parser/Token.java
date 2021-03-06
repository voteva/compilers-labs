package parser;

import exception.SyntaxException;

public class Token<N> {
    protected final Tokind<N> kind;
    protected final Location loc;
    private final N value;

    public Token(Tokind<N> kind, Location loc, N value) {
        this.kind = kind;
        this.loc = new Location(loc);
        this.value = value;
    }

    public Token(Tokind<N> kind, Location loc) {
        this(kind, loc, null);
    }

    @Override
    public String toString() {
        if (getValue() == null) return kind.name();
        return kind.name() + " (" + getValue().toString() + ")";
    }

    public Tokind<N> getKind() {
        return kind;
    }

    public String getName() {
        return kind.name();
    }

    public int getLbp() {
        return kind.lbp();
    }

    public N getValue() {
        return value;
    }

    public Location getLoc() {
        return loc;
    }

    protected N parse() throws SyntaxException {
        // check if already parsed (has value)
        N node = kind.parse(this);
        if (node == null)
            throw new SyntaxException(String.format("unexpected token '%s'", this), loc);
        return node;
    }

    protected N parse(N left) throws SyntaxException {
        N node = kind.parse(this, left);
        if (node == null)
            throw new SyntaxException(String.format("unexpected token '%s'", this), loc);
        return node;
    }
}