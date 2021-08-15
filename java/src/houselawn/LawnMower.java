package houselawn;

class LawnMower {
    private final static long MINUTES_PER_WEEK = 10_080L;

    private final String name;
    private final int price;
    private final int cuttingRate;
    private final int cuttingTime;
    private final int rechargeTime;

    private LawnMower(String name, int price, int cuttingRate, int cuttingTime, int rechargeTime) {
        this.name = name;
        this.price = price;
        this.cuttingRate = cuttingRate;
        this.cuttingTime = cuttingTime;
        this.rechargeTime = rechargeTime;
    }

    private static int parseInt(String s, String name, int lowerBound, int upperBound) throws LawnMowerParseException {
        try {
            int r = Integer.parseInt(s);
            if (r < lowerBound || r > upperBound) {
                throw new LawnMowerParseException(String.format(
                        "%s is out of bounds. Got %d, expected value to be within [%d, %d].", name, r, lowerBound,
                        upperBound));
            }
            return r;
        } catch (NumberFormatException e) {
            throw new LawnMowerParseException("Couldn't parse " + name + " as an integer. Value is '" + s + "'.");
        }
    }

    static LawnMower fromLine(String line) throws LawnMowerParseException {
        String[] s = line.split(",");
        if (s.length != 5) {
            throw new LawnMowerParseException("Wrong number of parameters.");
        }
        return new LawnMower(s[0],
                parseInt(s[1], "price", 1, 100_000),
                parseInt(s[2], "cutting rate", 1, 100),
                parseInt(s[3], "cutting time", 1, 10_080),
                parseInt(s[4], "recharge time", 1, 10_080));
    }

    @Override
    public String toString() {
        return "LawnMower{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", cuttingRate=" + cuttingRate +
                ", cuttingTime=" + cuttingTime +
                ", rechargeTime=" + rechargeTime +
                '}';
    }

    private long cutAreaPerCycle() {
        return ((long) cuttingRate) * ((long) cuttingTime);
    }

    private long cycleDuration() {
        return ((long) cuttingTime) + ((long) rechargeTime);
    }

    boolean willCutLawnFasterThanOneWeek(long lawnSize) {
        // cutAreaPerCycle() / cycleDuration() is the average sqm cut per minute.
        // cutAreaPerCycle() / cycleDuration() * MINUTES_PER_WEEK is the average sqm cut per week.
        // We want to check cutAreaPerCycle() / cycleDuration() * MINUTES_PER_WEEK >= lawnSize.
        // To avoid rounding problems we can multiply both sides with cycleDuration() (note that cycleDuration() is
        // always strictly positive).
        //
        // We use longs in this calculation to avoid a potential integer overflow.
        return cutAreaPerCycle() * MINUTES_PER_WEEK >= lawnSize * cycleDuration();
    }

    int price() {
        return price;
    }

    String name() {
        return name;
    }
}
