package filters;

import twitter4j.Status;

import java.util.List;

public class OrFilter implements Filter {

    private final Filter firstChildren;
    private final Filter secondChildren;

    public OrFilter(Filter firstChildren, Filter secondChildren){
        this.firstChildren = firstChildren;
        this.secondChildren = secondChildren;
    }

    @Override
    public boolean matches(Status s) {
        return firstChildren.matches(s) || secondChildren.matches(s);
    }

    @Override
    public List<String> terms() {
        List<String> terms = firstChildren.terms();
        terms.addAll(secondChildren.terms());
        return terms;
    }

    @Override
    public String toString() {
        return "(" + firstChildren.toString() + " or " + secondChildren.toString() + ")";
    }
}
